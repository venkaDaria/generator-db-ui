package com.example.venka.lab5.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteController {
    private final JsonArray paths = new JsonArray();

    static {[paths]}

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("title", "[title]");
        model.addAttribute("paths", paths);
        return "index";
    }
}
