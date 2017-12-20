package com.example.venka.demo.service.res.sub;

import com.example.venka.demo.utils.Replaces;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

import static com.example.venka.demo.service.asm.AsmBoundService.applyOption;
import static com.example.venka.demo.utils.FileUtils.getJavaName;
import static com.example.venka.demo.utils.FileUtils.replace;
import static com.example.venka.demo.utils.JsonMapper.filterBounds;
import static com.example.venka.demo.utils.JsonMapper.filterFields;
import static com.example.venka.demo.utils.JsonMapper.toCode;
import static com.example.venka.demo.utils.Paths.CONTROLLERS;
import static com.example.venka.demo.utils.Paths.CONTROLLERS_IMPL;
import static com.example.venka.demo.utils.Paths.EXAMPLE_CONTROLLER;
import static com.example.venka.demo.utils.Paths.OLD_PACKAGE_POINT;
import static com.example.venka.demo.utils.Paths.toHref;
import static com.example.venka.demo.utils.Replaces.PATHS;
import static com.example.venka.demo.utils.Replaces.TITLE;

@Service
public class ControllerService implements ServiceExecutor {

    private static final String MANY_BOUND = "%s.get%sSet().add(%s)";
    private static final String ONE_BOUND = "%s.set%s(%s)";

    private static String getStringWithConsumer(
            final String className,
            final List<LinkedTreeMap<String, Object>> bounds,
            final BiConsumer<StringBuilder, String> consumer
    ) {
        return getStringWithConsumer(bounds, (StringBuilder sb, LinkedTreeMap<String, Object> bound) -> {
            final String option = getRepository(className, bound);
            consumer.accept(sb, option);
        });
    }

    private static String getRepository(final String className, final LinkedTreeMap<String, Object> bound) {
        return applyOption(className.toLowerCase(), bound) + "Repository";
    }

    private static String getStringWithConsumer(
            final List<LinkedTreeMap<String, Object>> linkedTreeMapList,
            final BiConsumer<StringBuilder, LinkedTreeMap<String, Object>> consumer
    ) {
        final StringBuilder sb = new StringBuilder();
        linkedTreeMapList.forEach(linkedTreeMap -> consumer.accept(sb, linkedTreeMap));
        return sb.toString();
    }

    @Override
    public void execute(final File directory, final Map<java.lang.String, Object> body) throws IOException {
        final Path exampleController = Paths.get(directory.getPath() + EXAMPLE_CONTROLLER);

        @SuppressWarnings("unchecked") final List<LinkedTreeMap<String, Object>> entities =
                (ArrayList<LinkedTreeMap<String, Object>>) body.get("entities");

        final Set<JsonObject> paths = new HashSet<>();
        entities.forEach(entity -> {
            final String entityName = entity.get("name").toString();
            final String packageName = body.get("packageName").toString();
            final String className = StringUtils.capitalize(entityName);
            final String fullClassName = directory.getPath() + CONTROLLERS_IMPL + getJavaName(className + "Controller");

            final String jsonString = String.format("{\"name\": %s, \"href\": %s}", entityName, toHref(entityName));
            final JsonObject asJsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

            paths.add(asJsonObject);

            try {
                final Path newController = new File(fullClassName).toPath();
                Files.copy(exampleController, newController);

                replace(newController, OLD_PACKAGE_POINT, packageName);
                replace(newController, "(?<!\\.)example", entityName);
                replace(newController, "(?<=this.)example", entityName);
                replace(newController, "Example", className);

                changeController(newController, packageName, filterFields(body, entityName), filterBounds(body, entityName));
            } catch (final IOException ex) {
                System.out.println(ex.getMessage());
            }
        });


        final Path routeController = new File(directory.getPath() + CONTROLLERS +
                getJavaName("RouteController")).toPath();
        replace(routeController, TITLE, "DB - UI " + body.get("name"));
        replace(routeController, PATHS, toCode(paths));

        Files.deleteIfExists(exampleController);
    }

    private void changeController(
            final Path newController,
            final String packageName,
            final List<LinkedTreeMap<String, Object>> fields,
            final List<LinkedTreeMap<String, Object>> bounds
    ) throws IOException {
        final String fileName = newController.getFileName().toString()
                .replace(".java", "").replace("Controller", "");

        replace(newController, Replaces.FIELDS, getFields(fileName, bounds));
        replace(newController, Replaces.CONSTRUCTOR, getConstructor(fileName, bounds));
        replace(newController, Replaces.CONSTRUCTOR_FIELDS, getConstructorFields(fileName, bounds));
        replace(newController, Replaces.PARAMS, getParams(fields));
        replace(newController, Replaces.PARAMS_CREATE, getParamsCreate(fields));
        replace(newController, Replaces.DEPS_PARAMS, getDepsParams(fileName, bounds));
        replace(newController, Replaces.CREATE, getCreate(fileName, fields));
        replace(newController, Replaces.DEPS_STREAM, getDepsStream(fileName, bounds));
        replace(newController, Replaces.DEPS, getDeps(fileName, bounds));
        replace(newController, Replaces.IMPORT, getImport(fileName, packageName, bounds));
        replace(newController, Replaces.IMPORT_2, getImport2(fields));
    }

    private String getDepsParams(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        final StringBuilder sb = new StringBuilder();
        final String classNameLowerCase = className.toLowerCase();

        bounds.forEach(bound -> {
            String bind = bound.get("bind").toString();

            if (Objects.equals(bound.get("option2"), classNameLowerCase)) {
                bind = bind.equals("1") ? "2" : bind.equals("2") ? "1" : bind;
            }

            final String option = applyOption(className.toLowerCase(), bound);

            sb.append(", @RequestParam(required = false) final Long");

            switch (bind) {
                case "0":
                case "2":
                    sb.append(Replaces.SPACE).append(option);
                    break;
                case "1":
                case "3":
                    sb.append("... ").append(option).append("Set");
                    break;
            }
        });
        return sb.toString();
    }

    private String getImport(final String className, final String packageName, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append("import ").append(packageName)
                .append(".repositories.").append(StringUtils.capitalize(option)).append(";"));
    }

    private String getImport2(final List<LinkedTreeMap<String, Object>> fields) {
        final StringBuilder sb = new StringBuilder();
        fields.forEach(field -> createFieldImport(sb, field));
        return sb.toString();
    }

    private void createFieldImport(final StringBuilder sb, final LinkedTreeMap<String, Object> field) {
        final String dataType = field.get("dataType").toString();
        switch (dataType) {
            case "LocalDateTime":
                sb.append("import java.time.LocalDate;").append(System.lineSeparator());
            case "DateTime":
                sb.append("import java.time.LocalDateTime;").append(System.lineSeparator());
                break;
        }
    }

    private String getDeps(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return ", new String[]{" +
                getStringWithConsumer(className, bounds, (stb, option) ->
                        stb.append(", \"").append(option.replace("Repository", "")).append("\""))
                        .substring(2) + "}, " +
                getStringWithConsumer(className, bounds, (stb, option) -> stb.append(", ").append(option)).substring(2);
    }

    private String getDepsStream(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        final StringBuilder sb = new StringBuilder();
        bounds.forEach(bound -> {
            final String classNameLowerCase = className.toLowerCase();
            final String option = applyOption(classNameLowerCase, bound);
            final String capitalizeOption = StringUtils.capitalize(option);

            String bind = bound.get("bind").toString();

            if (Objects.equals(bound.get("option2"), classNameLowerCase)) {
                bind = bind.equals("1") ? "2" : bind.equals("2") ? "1" : bind;
            }

            switch (bind) {
                case "0":
                    setOneToOneBound(sb, classNameLowerCase, className, option, capitalizeOption);
                    break;
                case "1":
                    setOneToManyBound(sb, classNameLowerCase, className, option, capitalizeOption);
                    break;
                case "2":
                    setManyToOneBound(sb, classNameLowerCase, className, option, capitalizeOption);
                    break;
                case "3":
                    setManyToManyBound(sb, classNameLowerCase, className, option, capitalizeOption);
                    break;
            }
        });
        return sb.toString();
    }

    private void setOneToOneBound(final StringBuilder sb, final String classNameLowerCase,
                                  final String className, final String option, final String capitalizeOption) {
        setToOneBound(sb, classNameLowerCase, className, option, capitalizeOption, false);
    }

    private void setOneToManyBound(final StringBuilder sb, final String classNameLowerCase,
                                  final String className, final String option, final String capitalizeOption) {
        setToManyBound(sb, classNameLowerCase, className, option, capitalizeOption, false);
    }

    private void setManyToOneBound(final StringBuilder sb, final String classNameLowerCase,
                                  final String className, final String option, final String capitalizeOption) {
        setToOneBound(sb, classNameLowerCase, className, option, capitalizeOption, true);
    }

    private void setManyToManyBound(final StringBuilder sb, final String classNameLowerCase,
                                  final String className, final String option, final String capitalizeOption) {
        setToManyBound(sb, classNameLowerCase, className, option, capitalizeOption, true);
    }

    private void setToManyBound(final StringBuilder sb, final String classNameLowerCase,
                                final String className, final String option, final String capitalizeOption,
                                final boolean isFirstMany) {
        sb.append(String.format("if (%s != null) {", option))
                .append(System.lineSeparator());

        sb.append(Replaces.START_TAB_2).append(
                String.format("Arrays.stream(%1$sSet).forEach(%1$sId -> %1$sRepository.findById(%1$sId)", option)
        ).append(System.lineSeparator());
        sb.append(Replaces.START_TAB_2).append(
                String.format(".ifPresent(%s -> {", option)
        ).append(System.lineSeparator());
        sb.append(Replaces.START_TAB_2).append(Replaces.TAB).append(
                String.format(isFirstMany ? MANY_BOUND : ONE_BOUND, classNameLowerCase, capitalizeOption, option)
        ).append(Replaces.STOP).append(System.lineSeparator());
        sb.append(Replaces.START_TAB_2).append(Replaces.TAB).append(
                String.format(MANY_BOUND, option, className, classNameLowerCase)
        ).append(Replaces.STOP).append(System.lineSeparator());
        sb.append(Replaces.START_TAB_2).append("}").append(System.lineSeparator());
        sb.append(Replaces.START_TAB_2).append("));").append(System.lineSeparator());
        sb.append(Replaces.START_TAB_2).append("}");
    }

    private void setToOneBound(final StringBuilder sb, final String classNameLowerCase,
                               final String className, final String option, final String capitalizeOption,
                               final boolean isFirstMany) {
        sb.append(String.format("if (%s != null) {", option))
                .append(System.lineSeparator());

        sb.append(Replaces.START_TAB_2).append(
                String.format("%1$sRepository.findById(%1$s).ifPresent(%1$sEntity -> {", option)
        ).append(System.lineSeparator());
        sb.append(Replaces.START_TAB_2).append(Replaces.TAB).append("model.addAttribute(\"isOkay\");")
                .append(System.lineSeparator());

        final String optionEntity = option + "Entity";

        sb.append(Replaces.START_TAB_2).append(Replaces.TAB).append(
                String.format(isFirstMany ? MANY_BOUND : ONE_BOUND, classNameLowerCase, capitalizeOption, optionEntity)
        ).append(Replaces.STOP).append(System.lineSeparator());
        sb.append(Replaces.START_TAB_2).append(Replaces.TAB).append(
                String.format(ONE_BOUND, optionEntity, className, classNameLowerCase)
        ).append(Replaces.STOP).append(System.lineSeparator());
        sb.append(Replaces.START_TAB_2).append("});").append(System.lineSeparator());

        sb.append(Replaces.START_TAB_2).append("}");
    }

    private String getCreate(final String className, final List<LinkedTreeMap<String, Object>> fields) {
        final StringBuilder sb = new StringBuilder();
        sb.append("private ").append(className).append(" create(").append(getParams(fields).substring(2)).append(") {")
                .append(System.lineSeparator());
        sb.append(Replaces.START_TAB).append("final ").append(className).append(Replaces.SPACE)
                .append(className.toLowerCase()).append(Replaces.SPACE)
                .append(Replaces.EQUAL).append("new ").append(className).append("()").append(Replaces.STOP)
                .append(System.lineSeparator());
        sb.append(System.lineSeparator());

        fields.forEach(field -> createFieldSetter(className.toLowerCase(), sb, field));

        sb.append(System.lineSeparator());
        sb.append(Replaces.START_TAB).append("return ").append(className.toLowerCase()).append(Replaces.STOP)
                .append(System.lineSeparator());
        sb.append("}");
        return sb.toString();
    }

    private void createFieldSetter(final String className, final StringBuilder sb, final LinkedTreeMap<String, Object> field) {
        String newTab = Replaces.START_TAB;
        String value = field.get("name").toString();

        final String dataType = field.get("dataType").toString();
        switch (dataType) {
            case "Integer":
            case "Double":
                value = dataType + ".valueOf(" + value + ")";
                break;
            case "LocalDateTime":
            case "DateTime":
                value = "DateTransformer.parse(" + value + ")";
                break;
        }

        final boolean throwException = !dataType.equals("String");
        if (throwException) {
            sb.append(Replaces.START_TAB).append("try {").append(System.lineSeparator());
            newTab += Replaces.TAB;
        }

        sb.append(newTab).append(className).append(Replaces.POINT)
                .append("set").append(StringUtils.capitalize(field.get("name").toString()))
                .append("(").append(value).append(")").append(Replaces.STOP)
                .append(System.lineSeparator());

        if (throwException) {
            sb.append(Replaces.START_TAB).append("} catch (final Exception ignored) {").append(System.lineSeparator());
            sb.append(Replaces.START_TAB).append("}").append(System.lineSeparator());
        }
    }

    private String getParamsCreate(final List<LinkedTreeMap<String, Object>> fields) {
        return getStringWithConsumer(fields, (sb, field) -> sb.append(", ").append(field.get("name").toString())).substring(2);
    }

    private String getParams(final List<LinkedTreeMap<String, Object>> fields) {
        return getStringWithConsumer(fields, (sb, field) -> sb.append(", @RequestParam ").append("final ")
                .append(field.get("dataType").toString()).append(" ").append(field.get("name").toString()));
    }

    private String getConstructorFields(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append("this.").append(option)
                .append(Replaces.EQUAL).append(option).append(Replaces.STOP));
    }

    private String getConstructor(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append(", final ")
                .append(StringUtils.capitalize(option)).append(Replaces.SPACE).append(option));
    }

    private String getFields(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append("private final ")
                .append(StringUtils.capitalize(option)).append(Replaces.SPACE).append(option).append(Replaces.STOP));
    }
}
