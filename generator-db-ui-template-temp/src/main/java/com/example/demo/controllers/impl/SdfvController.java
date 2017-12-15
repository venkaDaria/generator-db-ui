package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Sdfv;
import com.example.demo.repositories.SdfvRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/sdfv")
public class SdfvController extends BaseController<Sdfv> {

    private final SdfvRepository sdfvRepository;

    @Autowired
    public SdfvController(final SdfvRepository sdfvRepository) {
        this.sdfvRepository = sdfvRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, sdfvRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id) {
        final Sdfv sdfv = create();

        id.ifPresent(sdfv::setId);

        sdfvRepository.save(sdfv);
        setAttributes(model, sdfvRepository, getClass());
        return "redirect:/sdfv/";
    }

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), sdfvRepository, new String[]{});
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        sdfvRepository.deleteById(id);

        setAttributes(model, sdfvRepository, getClass());
        return "redirect:/sdfv/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, sdfvRepository);
    }

    public Sdfv create() {
        return new Sdfv();
    }
}