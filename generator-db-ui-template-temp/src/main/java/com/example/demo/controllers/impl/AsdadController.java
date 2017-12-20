package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Asdad;
import com.example.demo.repositories.AsdadRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.SadasdRepository;

@Controller
@RequestMapping("/asdad")
public class AsdadController extends BaseController<Asdad> {

    private final AsdadRepository asdadRepository;
    private final SadasdRepository sadasdRepository;

    @Autowired
    public AsdadController(final AsdadRepository asdadRepository, final SadasdRepository sadasdRepository) {
        this.asdadRepository = asdadRepository;
        this.sadasdRepository = sadasdRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, asdadRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, final String adad, final long sadasd) {
        final Asdad asdad = create(adad);

        id.ifPresent(asdad::setId);

        sadasdRepository.findById(sadasd).ifPresent(sadasdEntity -> {
            model.addAttribute("isOkay");
            asdad.getSadasdSet().add(sadasdEntity);
            sadasdEntity.setAsdad(asdad);
            sadasdRepository.save(sadasdEntity);
        });

        asdadRepository.save(asdad);
        setAttributes(model, asdadRepository, getClass());
        return "redirect:/asdad/";
    }

    private Asdad create(final String adad) {
        Asdad asdad  = new Asdad();

        asdad.setAdad(adad);

        return asdad;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), asdadRepository, new String[]{"sadasdSet"}, sadasdRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        asdadRepository.deleteById(id);

        setAttributes(model, asdadRepository, getClass());
        return "redirect:/asdad/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, asdadRepository);
    }
}