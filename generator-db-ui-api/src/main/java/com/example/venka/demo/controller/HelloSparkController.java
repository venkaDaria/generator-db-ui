package com.example.venka.demo.controller;

import com.example.venka.demo.service.GeneratorFiles;
import com.example.venka.demo.utils.zip.ZipManager;
import org.springframework.stereotype.Controller;
import spark.Request;
import spark.Response;

import java.io.File;
import java.util.Map;

import static com.example.venka.demo.utils.JsonMapper.toMap;
import static spark.Spark.before;
import static spark.Spark.options;
import static spark.Spark.post;

@Controller
public class HelloSparkController {

    private HelloSparkController(final ZipManager app, final GeneratorFiles generatorFiles) {
        post("/generate", (request, response) -> {
            response.type("application/zip");
            response.header("Content-Disposition", "attachment; filename=example.zip");

            final Map<String, Object> body = toMap(request.body());
            final File directory = generatorFiles.get(body);

            return app.create(directory);
        });

        enableCORS();
    }

    // Enables CORS on requests. This method is an initialization method and should be called once.
    private void enableCORS() {
        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((Request request, Response response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");

            response.type("application/json");
        });
    }
}