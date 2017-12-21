package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Dadsadasd;
import com.example.demo.repositories.DadsadasdRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.DadsaRepository;

@Controller
@RequestMapping("/dadsadasd")
public class DadsadasdController extends BaseController<Dadsadasd> {

    private final DadsadasdRepository dadsadasdRepository;
    private final DadsaRepository dadsaRepository;

    @Autowired
    public DadsadasdController(final DadsadasdRepository dadsadasdRepository, final DadsaRepository dadsaRepository) {
        this.dadsadasdRepository = dadsadasdRepository;
        this.dadsaRepository = dadsaRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, dadsadasdRepository, Dadsadasd.class);
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, @RequestParam final String dadadasd, @RequestParam final String dada, @RequestParam(required = false) final Long dadsa) {
        final Dadsadasd dadsadasd = create(dadadasd, dada);

        id.ifPresent(dadsadasd::setId);

        if (dadsa != null) {
            dadsaRepository.findById(dadsa).ifPresent(dadsaEntity -> {
                model.addAttribute("isOkay");
                dadsadasd.setDadsa(dadsaEntity);
                dadsaEntity.setDadsadasd(dadsadasd);
            });
        }

        dadsadasdRepository.save(dadsadasd);
        setAttributes(model, dadsadasdRepository, Dadsadasd.class);
        return "redirect:/dadsadasd/";
    }

    private Dadsadasd create(@RequestParam final String dadadasd, @RequestParam final String dada) {
        final Dadsadasd dadsadasd = new Dadsadasd();

        dadsadasd.setDadadasd(dadadasd);
        dadsadasd.setDada(dada);

        return dadsadasd;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, Dadsadasd.class, dadsadasdRepository, new String[]{"dadsa"}, dadsaRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        dadsadasdRepository.deleteById(id);

        setAttributes(model, dadsadasdRepository, Dadsadasd.class);
        return "redirect:/dadsadasd/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, dadsadasdRepository);
    }
}