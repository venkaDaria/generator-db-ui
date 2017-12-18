package com.example.venka.demo.service.res.sub;

import com.example.venka.demo.utils.Replaces;
import com.google.gson.internal.LinkedTreeMap;
import org.jetbrains.annotations.NotNull;
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
        replace(newController, Replaces.PARAMS, getParams(bounds));
        replace(newController, Replaces.PARAMS_CREATE, getParamsCreate(bounds));
        replace(newController, Replaces.CREATE, getCreate(bounds));
        replace(newController, Replaces.DEPS_STREAM, getDepsStream(bounds));
        replace(newController, Replaces.DEPS, getDeps(fileName, bounds));
    }

    @NotNull
    private static String getStringWithConsumer(
            final String className,
            final List<LinkedTreeMap<String, Object>> bounds,
            final BiConsumer<StringBuilder, String> consumer
    ) {
        final StringBuilder sb = new StringBuilder();

        bounds.forEach(bound -> {
            final String option = getRepository(className, bound);
            consumer.accept(sb, option);
        });

        return sb.toString();
    }

    @NotNull
    private static String getRepository(final String className, final LinkedTreeMap<String, Object> bound) {
        return applyOption(className.toLowerCase(), bound) + "Repository";
    }

    private String getDeps(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append(", ").append(option));
    }

    private String getDepsStream(final List<LinkedTreeMap<String, Object>> bounds) {
        return null;
    }

    private String getCreate(final List<LinkedTreeMap<String, Object>> bounds) {
        return null;
    }

    private String getParamsCreate(final List<LinkedTreeMap<String, Object>> bounds) {
        return null;
    }

    private String getParams(final List<LinkedTreeMap<String, Object>> bounds) {
        return null;
    }

    private String getConstructorFields(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append("this.").append(option)
                .append(" = ").append(option).append(option).append(";").append(System.lineSeparator()));
    }

    private String getConstructor(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append(", final")
                .append(StringUtils.capitalize(option)).append(option));
    }

    private String getFields(final String className, final List<LinkedTreeMap<String, Object>> bounds) {
        return getStringWithConsumer(className, bounds, (sb, option) -> sb.append("private final ")
                .append(StringUtils.capitalize(option)).append(option).append(";").append(System.lineSeparator()));
    }
}
