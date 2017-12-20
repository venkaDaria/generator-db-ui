package com.example.venka.lab5.controllers;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RouteController {
    private static final Set<JsonObject> PATHS = new HashSet<>();

    static {[paths]}

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("title", "[title]");
        model.addAttribute("paths", PATHS);
        return "index";
    }
}
