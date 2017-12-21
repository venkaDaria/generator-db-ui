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
        final JsonObject dadsadasdObject = new JsonObject();
        dadsadasdObject.addProperty("name", "dadsadasd");
        dadsadasdObject.addProperty("href", "/dadsadasd/");
        PATHS.add(dadsadasdObject);

        final JsonObject dadsaObject = new JsonObject();
        dadsaObject.addProperty("name", "dadsa");
        dadsaObject.addProperty("href", "/dadsa/");
        PATHS.add(dadsaObject);
}

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("title", "DB - UI demo");
        model.addAttribute("paths", PATHS);
        return "index";
    }
}
