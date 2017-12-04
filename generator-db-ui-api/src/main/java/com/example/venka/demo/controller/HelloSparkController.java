package com.example.venka.demo.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

@Controller
public class HelloSparkController {

    private static final Gson GSON = new Gson();

    private HelloSparkController() {
        get("/step1", (request, response) -> "step1", GSON::toJson);
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