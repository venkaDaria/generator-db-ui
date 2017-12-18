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
import static com.example.venka.demo.utils.Paths.OLD_PACKAGE_POINT;

@Service
public class ControllerService implements ServiceExecutor {

    @SuppressWarnings("unchecked")
    @Override
    public void execute(final File directory, final Map<java.lang.String, Object> body) throws IOException {
        final Path exampleController = Paths.get(directory.getPath() + EXAMPLE_CONTROLLER);

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

                changeController(newController, filterFields(body, entityName), filterBounds(body, entityName));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        Files.deleteIfExists(exampleController);
    }

    private void changeController(
            final Path newController,
            final List<LinkedTreeMap<String, Object>> fields,
            final List<LinkedTreeMap<String, Object>> bounds
    ) throws IOException {
        final String fileName = newController.getFileName().toString();

        replace(newController, Replaces.FIELDS, getFields(fileName, bounds));
        replace(newController, Replaces.CONSTRUCTOR, getConstructor(fileName, bounds));
        replace(newController, Replaces.CONSTRUCTOR_FIELDS, getConstructorFields(fileName, bounds));
        replace(newController, Replaces.PARAMS, getParams(fields));
        replace(newController, Replaces.PARAMS_CREATE, getParamsCreate(fields));
        replace(newController, Replaces.CREATE, getCreate(fileName, fields));
        replace(newController, Replaces.DEPS_STREAM, getDepsStream(bounds));
        replace(newController, Replaces.DEPS, getDeps(fileName, bounds));
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
        return applyOption(className.toLowerCase(), bound) + "Repository";
    }

    private static String getStringWithConsumer(
            final List<LinkedTreeMap<String, Object>> linkedTreeMapList,
            final BiConsumer<StringBuilder, LinkedTreeMap<String, Object>> consumer
    ) {
        final StringBuilder sb = new StringBuilder();
        linkedTreeMapList.forEach(bound -> consumer.accept(sb, bound));
        return sb.toString();
    }

    private String getDeps(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append(", ").append(option));
    }

    private String getDepsStream(final List<LinkedTreeMap<String, Object>> bounds) {
        return null;
    }

    private String getCreate(final String className, final List<LinkedTreeMap<String, Object>> fields) {
        final StringBuilder sb = new StringBuilder();
        sb.append("private ").append(className).append(" create(").append(getParams(fields)).append(") {")
                .append(System.lineSeparator());
        sb.append(Replaces.TAB).append(className).append(Replaces.SPACE).append(className.toLowerCase()).append(Replaces.SPACE)
                .append(Replaces.EQUAL).append("new ").append(className).append("()").append(Replaces.STOP)
                .append(System.lineSeparator());
        sb.append(System.lineSeparator());

        fields.forEach(field -> sb.append(Replaces.TAB).append(className).append(Replaces.POINT)
                .append("set").append(StringUtils.capitalize(field.get("name").toString()))
                .append("(").append(field.get("name").toString())
                .append(")").append(Replaces.STOP).append(System.lineSeparator()));

        sb.append(System.lineSeparator());
        sb.append(Replaces.TAB).append("return ").append(className.toLowerCase()).append(Replaces.STOP)
                .append(System.lineSeparator());
        sb.append("}");
        return sb.toString();
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
                .append(Replaces.EQUAL).append(option).append(option).append(Replaces.STOP).append(System.lineSeparator()));
    }

    private String getConstructor(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append(Replaces.FINAL)
                .append(StringUtils.capitalize(option)).append(option));
    }

    private String getFields(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append("private final ")
                .append(StringUtils.capitalize(option)).append(option).append(";").append(System.lineSeparator()));
    }
}
