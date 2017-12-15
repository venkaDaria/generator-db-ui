package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Dasd;
import com.example.demo.repositories.DasdRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/dasd")
public class DasdController extends BaseController<Dasd> {

    private final DasdRepository dasdRepository;

    @Autowired
    public DasdController(final DasdRepository dasdRepository) {
        this.dasdRepository = dasdRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, dasdRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id) {
        final Dasd dasd = create();

        id.ifPresent(dasd::setId);

        dasdRepository.save(dasd);
        setAttributes(model, dasdRepository, getClass());
        return "redirect:/dasd/";
    }

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), dasdRepository, new String[]{});
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        dasdRepository.deleteById(id);

        setAttributes(model, dasdRepository, getClass());
        return "redirect:/dasd/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, dasdRepository);
    }

    public Dasd create() {
        return new Dasd();
    }
}