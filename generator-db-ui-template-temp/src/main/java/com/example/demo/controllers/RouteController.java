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
        final JsonObject sdfsObject = new JsonObject();
        sdfsObject.addProperty("name", "sdfs");
        sdfsObject.addProperty("href", "/sdfs/");
        PATHS.add(sdfsObject);

        final JsonObject sdfsfsfObject = new JsonObject();
        sdfsfsfObject.addProperty("name", "sdfsfsf");
        sdfsfsfObject.addProperty("href", "/sdfsfsf/");
        PATHS.add(sdfsfsfObject);
}

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("title", "DB - UI demo");
        model.addAttribute("paths", PATHS);
        return "index";
    }
}
