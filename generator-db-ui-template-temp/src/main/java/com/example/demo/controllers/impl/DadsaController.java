package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Dadsa;
import com.example.demo.repositories.DadsaRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.DadsadasdRepository;

@Controller
@RequestMapping("/dadsa")
public class DadsaController extends BaseController<Dadsa> {

    private final DadsaRepository dadsaRepository;
    private final DadsadasdRepository dadsadasdRepository;

    @Autowired
    public DadsaController(final DadsaRepository dadsaRepository, final DadsadasdRepository dadsadasdRepository) {
        this.dadsaRepository = dadsaRepository;
        this.dadsadasdRepository = dadsadasdRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, dadsaRepository, Dadsa.class);
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, @RequestParam final String dada, @RequestParam(required = false) final Long dadsadasd) {
        final Dadsa dadsa = create(dada);

        id.ifPresent(dadsa::setId);

        if (dadsadasd != null) {
            dadsadasdRepository.findById(dadsadasd).ifPresent(dadsadasdEntity -> {
                model.addAttribute("isOkay");
                dadsa.setDadsadasd(dadsadasdEntity);
                dadsadasdEntity.setDadsa(dadsa);
            });
        }

        dadsaRepository.save(dadsa);
        setAttributes(model, dadsaRepository, Dadsa.class);
        return "redirect:/dadsa/";
    }

    private Dadsa create(@RequestParam final String dada) {
        final Dadsa dadsa = new Dadsa();

        dadsa.setDada(dada);

        return dadsa;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, Dadsa.class, dadsaRepository, new String[]{"dadsadasd"}, dadsadasdRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        dadsaRepository.deleteById(id);

        setAttributes(model, dadsaRepository, Dadsa.class);
        return "redirect:/dadsa/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, dadsaRepository);
    }
}