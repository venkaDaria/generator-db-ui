package com.example.demo.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteController {
    private final JsonArray paths = new JsonArray();

    static {
        final JsonObject sadasdObject = new JsonObject();
        sadasdObject.addProperty("name", "sadasd");
        sadasdObject.addProperty("href", "/sadasd/");

        final JsonObject asdadObject = new JsonObject();
        asdadObject.addProperty("name", "asdad");
        asdadObject.addProperty("href", "/asdad/");
}

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("title", "DB - UIdemo");
        model.addAttribute("paths", paths);
        return "index";
    }
}
