package com.example.venka.demo.service.res.sub;

import com.example.venka.demo.service.asm.AsmService;
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

import static com.example.venka.demo.service.GeneratorFiles.getPackageDir;
import static com.example.venka.demo.utils.FileUtils.getJavaName;
import static com.example.venka.demo.utils.FileUtils.replace;
import static com.example.venka.demo.utils.FileUtils.writeTo;
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
    ) {
        // fields
        // constructor
        // constructor-fields
        // params
        // params-create
        // create
        // deps-stream
        // deps
    }
}
