package com.example.demo.controllers;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RouteController {
    private static final Set<JsonObject> PATHS = new HashSet<>();

    static {
        final JsonObject roleObject = new JsonObject();
        roleObject.addProperty("name", "role");
        roleObject.addProperty("href", "/role/");
        PATHS.add(roleObject);

        final JsonObject userObject = new JsonObject();
        userObject.addProperty("name", "user");
        userObject.addProperty("href", "/user/");
        PATHS.add(userObject);
}

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("title", "DB - UI demo");
        model.addAttribute("paths", PATHS);
        return "index";
    }
}
