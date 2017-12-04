package com.example.venka.lab5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteController {

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("title", "[title]");
        model.addAttribute("paths", "[paths]");
        return "index";
    }
}
