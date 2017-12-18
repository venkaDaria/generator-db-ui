package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Dwad;
import com.example.demo.repositories.DwadRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/dwad")
public class DwadController extends BaseController<Dwad> {

    private final DwadRepository dwadRepository;

    @Autowired
    public DwadController(final DwadRepository dwadRepository) {
        this.dwadRepository = dwadRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, dwadRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id) {
        final Dwad dwad = create();

        id.ifPresent(dwad::setId);

        dwadRepository.save(dwad);
        setAttributes(model, dwadRepository, getClass());
        return "redirect:/dwad/";
    }

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), dwadRepository, new String[]{});
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        dwadRepository.deleteById(id);

        setAttributes(model, dwadRepository, getClass());
        return "redirect:/dwad/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, dwadRepository);
    }

    public Dwad create() {
        return new Dwad();
    }
}