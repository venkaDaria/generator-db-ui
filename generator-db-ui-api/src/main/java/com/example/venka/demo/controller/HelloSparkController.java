package com.example.venka.demo.controller;

import com.example.venka.demo.utils.GeneratorApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import spark.Request;
import spark.Response;

import static com.example.venka.demo.utils.JsonMapper.toMap;
import static spark.Spark.*;

@Controller
public class HelloSparkController {

    private HelloSparkController(final GeneratorApp app) {
        post("/generate", (request, response) -> {
            response.type("application/zip");
            response.header("Content-Disposition", "attachment; filename=example.zip");
            return app.generate();
            //return app.generate(toMap(request.body()));
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