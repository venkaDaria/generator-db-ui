package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Edadwad;
import com.example.demo.repositories.EdadwadRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/edadwad")
public class EdadwadController extends BaseController<Edadwad> {

    private final EdadwadRepository edadwadRepository;

    @Autowired
    public EdadwadController(final EdadwadRepository edadwadRepository) {
        this.edadwadRepository = edadwadRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, edadwadRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id) {
        final Edadwad edadwad = create();

        id.ifPresent(edadwad::setId);

        edadwadRepository.save(edadwad);
        setAttributes(model, edadwadRepository, getClass());
        return "redirect:/edadwad/";
    }

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), edadwadRepository, new String[]{});
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        edadwadRepository.deleteById(id);

        setAttributes(model, edadwadRepository, getClass());
        return "redirect:/edadwad/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, edadwadRepository);
    }

    public Edadwad create() {
        return new Edadwad();
    }
}