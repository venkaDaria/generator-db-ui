package com.example.demo.controllers;

import com.google.gson.JsonArray;
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
        final JsonObject sadasdObject = new JsonObject();
        sadasdObject.addProperty("name", "sadasd");
        sadasdObject.addProperty("href", "/sadasd/");
        PATHS.add(sadasdObject);

        final JsonObject asdadObject = new JsonObject();
        asdadObject.addProperty("name", "asdad");
        asdadObject.addProperty("href", "/asdad/");
        PATHS.add(asdadObject);
}

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("title", "DB - UIdemo");
        model.addAttribute("paths", PATHS);
        return "index";
    }
}
