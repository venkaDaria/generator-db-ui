package com.example.demo.controllers;

import com.google.gson.JsonArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
public class RouteController {
    private static final Set<JsonObject> PATHS = new HashSet<>();

    static {
        final JsonObject dxfghObject = new JsonObject();
        dxfghObject.addProperty("name", "dxfgh");
        dxfghObject.addProperty("href", "/dxfgh/");
        PATHS.add(dxfghObject);

        final JsonObject kjhObject = new JsonObject();
        kjhObject.addProperty("name", "kjh");
        kjhObject.addProperty("href", "/kjh/");
        PATHS.add(kjhObject);
}

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("title", "DB - UIdemo");
        model.addAttribute("paths", PATHS);
        return "index";
    }
}
