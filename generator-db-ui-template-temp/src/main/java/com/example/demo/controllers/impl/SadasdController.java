package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Sadasd;
import com.example.demo.repositories.SadasdRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.AsdadRepository;

@Controller
@RequestMapping("/sadasd")
public class SadasdController extends BaseController<Sadasd> {

    private final SadasdRepository sadasdRepository;
    private final AsdadRepository asdadRepository;

    @Autowired
    public SadasdController(final SadasdRepository sadasdRepository, final AsdadRepository asdadRepository) {
        this.sadasdRepository = sadasdRepository;
        this.asdadRepository = asdadRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, sadasdRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, final String dsada, final long asdad) {
        final Sadasd sadasd = create(dsada);

        id.ifPresent(sadasd::setId);

        asdadRepository.findById(asdad).ifPresent(asdadEntity -> {
            model.addAttribute("isOkay");
            sadasd.setAsdad(asdadEntity);
            asdadEntity.getSadasdSet().add(sadasd);
            asdadRepository.save(asdadEntity);
        });

        sadasdRepository.save(sadasd);
        setAttributes(model, sadasdRepository, getClass());
        return "redirect:/sadasd/";
    }

    private Sadasd create(final String dsada) {
        Sadasd sadasd  = new Sadasd();

        sadasd.setDsada(dsada);

        return sadasd;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), sadasdRepository, new String[]{"asdadSet"}, asdadRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        sadasdRepository.deleteById(id);

        setAttributes(model, sadasdRepository, getClass());
        return "redirect:/sadasd/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, sadasdRepository);
    }
}