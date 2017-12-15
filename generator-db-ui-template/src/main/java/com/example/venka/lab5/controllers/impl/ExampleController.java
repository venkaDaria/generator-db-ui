package com.example.venka.lab5.controllers.impl;

import com.example.venka.lab5.model.impl.Example;
import com.example.venka.lab5.repositories.ExampleRepository;
import com.example.venka.lab5.controllers.BaseController;
import com.example.venka.lab5.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/example")
public class ExampleController extends BaseController<Example> {

    private final ExampleRepository exampleRepository;

    @Autowired
    public ExampleController(final ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, exampleRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id) {
        final Example example = create();

        id.ifPresent(example::setId);

        exampleRepository.save(example);
        setAttributes(model, exampleRepository, getClass());
        return "redirect:/example/";
    }

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), exampleRepository, new String[]{});
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        exampleRepository.deleteById(id);

        setAttributes(model, exampleRepository, getClass());
        return "redirect:/example/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, exampleRepository);
    }

    public Example create() {
        return new Example();
    }
}