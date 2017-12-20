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
        final JsonObject fsfddsfsdObject = new JsonObject();
        fsfddsfsdObject.addProperty("name", "fsfddsfsd");
        fsfddsfsdObject.addProperty("href", "/fsfddsfsd/");
        PATHS.add(fsfddsfsdObject);

        final JsonObject cxvxvvxObject = new JsonObject();
        cxvxvvxObject.addProperty("name", "cxvxvvx");
        cxvxvvxObject.addProperty("href", "/cxvxvvx/");
        PATHS.add(cxvxvvxObject);
}

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("title", "DB - UI demo");
        model.addAttribute("paths", PATHS);
        return "index";
    }
}
