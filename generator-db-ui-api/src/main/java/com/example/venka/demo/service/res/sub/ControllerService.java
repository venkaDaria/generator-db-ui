package com.example.venka.demo.service.res.sub;

import com.example.venka.demo.utils.Replaces;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static com.example.venka.demo.service.asm.AsmBoundService.applyOption;
import static com.example.venka.demo.utils.FileUtils.getJavaName;
import static com.example.venka.demo.utils.FileUtils.replace;
import static com.example.venka.demo.utils.JsonMapper.filterBounds;
import static com.example.venka.demo.utils.JsonMapper.filterFields;
import static com.example.venka.demo.utils.Paths.CONTROLLERS;
import static com.example.venka.demo.utils.Paths.EXAMPLE_CONTROLLER;
import static com.example.venka.demo.utils.Paths.MODEL_POINT;
import static com.example.venka.demo.utils.Paths.OLD_PACKAGE_POINT;

@Service
public class ControllerService implements ServiceExecutor {

    private static final String MANY_BOUND = "%s.get%ss().add(%s)";
    private static final String ONE_BOUND = "%s.set%s(%s)";

    @Override
    public void execute(final File directory, final Map<java.lang.String, Object> body) throws IOException {
        final Path exampleController = Paths.get(directory.getPath() + EXAMPLE_CONTROLLER);

        @SuppressWarnings("unchecked")
        final List<LinkedTreeMap<String, Object>> entities =
                (ArrayList<LinkedTreeMap<String, Object>>) body.get("entities");

        entities.forEach(entity -> {
            final String entityName = entity.get("name").toString();
            final String packageName = body.get("packageName").toString();
            final String className = StringUtils.capitalize(entityName);
            final String fullClassName = directory.getPath() + CONTROLLERS + getJavaName(className + "Controller");

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

        Files.deleteIfExists(exampleController);
    }

    private void changeController(
            final Path newController,
            final String packageName,
            final List<LinkedTreeMap<String, Object>> fields,
            final List<LinkedTreeMap<String, Object>> bounds
    ) throws IOException {
        final String fileName = newController.getFileName().toString().replace(".java", "");

        replace(newController, Replaces.FIELDS, getFields(fileName, bounds));
        replace(newController, Replaces.CONSTRUCTOR, getConstructor(fileName, bounds));
        replace(newController, Replaces.CONSTRUCTOR_FIELDS, getConstructorFields(fileName, bounds));
        replace(newController, Replaces.PARAMS, getParams(fields));
        replace(newController, Replaces.PARAMS_CREATE, getParamsCreate(fields));
        replace(newController, Replaces.CREATE, getCreate(fileName, fields));
        replace(newController, Replaces.DEPS_STREAM, getDepsStream(fileName, bounds));
        replace(newController, Replaces.DEPS, getDeps(fileName, bounds));
        replace(newController, Replaces.IMPORT, getImport(fileName, packageName, bounds));
    }

    private String getImport(final String className, final String packageName, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append("import ").append(packageName)
                .append(MODEL_POINT).append(Replaces.POINT).append(StringUtils.capitalize(option))
                .append(";").append(System.lineSeparator()));
    }

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
        return applyOption(className.replace("Controller", "").toLowerCase(), bound) + "Repository";
    }

    private static String getStringWithConsumer(
            final List<LinkedTreeMap<String, Object>> linkedTreeMapList,
            final BiConsumer<StringBuilder, LinkedTreeMap<String, Object>> consumer
    ) {
        final StringBuilder sb = new StringBuilder();
        linkedTreeMapList.forEach(linkedTreeMap -> consumer.accept(sb, linkedTreeMap));
        return sb.toString();
    }

    private String getDeps(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append(", ").append(option));
    }

    private String getDepsStream(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        final StringBuilder sb = new StringBuilder();
        bounds.forEach(bound -> {
            final String bind = bound.get("bind").toString();
            final String classNameLowerCase = className.toLowerCase();
            final String option = applyOption(classNameLowerCase, bound);
            final String capitalizeOption = StringUtils.capitalize(option);

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

    private void setManyToManyBound(final StringBuilder sb, final String classNameLowerCase,
                                    final String className, final String option, final String capitalizeOption) {
        setOneToManyBound(sb, classNameLowerCase, className, option, capitalizeOption, MANY_BOUND);
    }

    private void setOneToManyBound(final StringBuilder sb, final String classNameLowerCase,
                                    final String className, final String option, final String capitalizeOption) {
        setOneToManyBound(sb, classNameLowerCase, className, option, capitalizeOption, ONE_BOUND);
    }

    private void setOneToManyBound(final StringBuilder sb, final String classNameLowerCase,
                                   final String className, final String option, final String capitalizeOption,
                                   final String stringFormat) {
        sb.append(String.format("Arrays.stream(%1$ss).forEach(%1$sId -> %1$sRepository.findById(%1$sId)", option))
                .append(System.lineSeparator());
        sb.append(Replaces.TAB).append(
                String.format(".ifPresent(%s -> {", option)
        ).append(System.lineSeparator());
        sb.append(Replaces.TAB).append(Replaces.TAB).append(
                String.format("%s.get%s().add(%s);", classNameLowerCase, capitalizeOption, option)
        ).append(System.lineSeparator());
        sb.append(Replaces.TAB).append(Replaces.TAB).append(
                String.format(stringFormat, option, className, classNameLowerCase)
        ).append(System.lineSeparator());
        sb.append(Replaces.TAB).append("}").append(System.lineSeparator());
        sb.append("));").append(System.lineSeparator());
    }

    private void setManyToOneBound(final StringBuilder sb, final String classNameLowerCase,
                                   final String className, final String option, final String capitalizeOption) {
        setOneToOneBound(sb, classNameLowerCase, className, option, capitalizeOption, "%s.get%ss().add(%s)");
    }


    private void setOneToOneBound(final StringBuilder sb, final String classNameLowerCase,
                                  final String className, final String option, final String capitalizeOption) {
        setOneToOneBound(sb, classNameLowerCase, className, option, capitalizeOption, "%s.set%s(%s)");
    }

    private void setOneToOneBound(final StringBuilder sb, final String classNameLowerCase,
                                  final String className, final String option, final String capitalizeOption,
                                  final String stringFormat) {
        sb.append(String.format("%1$sRepository.findById(%1$s).ifPresent(%1$sEntity -> {", option))
                .append(System.lineSeparator());
        sb.append(Replaces.TAB).append("model.addAttribute(\"isOkay\");").append(System.lineSeparator());
        sb.append(Replaces.TAB).append(
                String.format(stringFormat, classNameLowerCase, capitalizeOption, option)
        ).append(System.lineSeparator());
        sb.append(Replaces.TAB).append(
                String.format("%s.set%s(%s)", option, className, classNameLowerCase)
        ).append(System.lineSeparator());
        sb.append(Replaces.TAB).append(
                String.format("%1$sRepository.save(%1$s);", option)
        ).append(System.lineSeparator());
        sb.append("});").append(System.lineSeparator());
    }

    private String getCreate(final String className, final List<LinkedTreeMap<String, Object>> fields) {
        final StringBuilder sb = new StringBuilder();
        sb.append("private ").append(className).append(" create(").append(getParams(fields).substring(2)).append(") {")
                .append(System.lineSeparator());
        sb.append(Replaces.TAB).append(className).append(Replaces.SPACE).append(className.toLowerCase()).append(Replaces.SPACE)
                .append(Replaces.EQUAL).append("new ").append(className).append("()").append(Replaces.STOP)
                .append(System.lineSeparator());
        sb.append(System.lineSeparator());

        fields.forEach(field -> createFieldSetter(className, sb, field));

        sb.append(System.lineSeparator());
        sb.append(Replaces.TAB).append("return ").append(className.toLowerCase()).append(Replaces.STOP)
                .append(System.lineSeparator());
        sb.append("}");
        return sb.toString();
    }

    private void createFieldSetter(final String className, final StringBuilder sb, final LinkedTreeMap<String, Object> field) {
        String newTab = Replaces.TAB;
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
            sb.append(Replaces.TAB).append("try {").append(System.lineSeparator());
            newTab += Replaces.TAB;
        }

        sb.append(newTab).append(className).append(Replaces.POINT)
                .append("set").append(StringUtils.capitalize(field.get("name").toString()))
                .append("(").append(value).append(")").append(Replaces.STOP)
                .append(System.lineSeparator());

        if (throwException) {
            sb.append(Replaces.TAB).append("} catch (final Exception ignored) {").append(System.lineSeparator());
            sb.append(Replaces.TAB).append("}").append(System.lineSeparator());
        }
    }

    private String getParamsCreate(final List<LinkedTreeMap<String, Object>> fields) {
        return getStringWithConsumer(fields, (sb, field) -> sb.append(", ").append(field.get("name").toString()));
    }

    private String getParams(final List<LinkedTreeMap<String, Object>> fields) {
        return getStringWithConsumer(fields, (sb, field) -> sb.append(Replaces.FINAL)
                .append(field.get("dataType").toString()).append(" ").append(field.get("name").toString()));
    }

    private String getConstructorFields(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append("this.").append(option)
                .append(Replaces.EQUAL).append(option).append(Replaces.STOP).append(System.lineSeparator()));
    }

    private String getConstructor(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append(Replaces.FINAL)
                .append(StringUtils.capitalize(option)).append(Replaces.SPACE).append(option));
    }

    private String getFields(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append("private final ")
                .append(StringUtils.capitalize(option)).append(Replaces.SPACE).append(option)
                .append(";").append(System.lineSeparator()));
    }
}
