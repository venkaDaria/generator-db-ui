package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Fdsad;
import com.example.demo.repositories.FdsadRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.SdfsffRepository;

@Controller
@RequestMapping("/fdsad")
public class FdsadController extends BaseController<Fdsad> {

    private final FdsadRepository fdsadRepository;
    private final SdfsffRepository sdfsffRepository;

    @Autowired
    public FdsadController(final FdsadRepository fdsadRepository, final SdfsffRepository sdfsffRepository) {
        this.fdsadRepository = fdsadRepository;
        this.sdfsffRepository = sdfsffRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, fdsadRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, final String aaa, final Integer bbb, final long sdfsff) {
        final Fdsad fdsad = create(aaa, bbb);

        id.ifPresent(fdsad::setId);

        sdfsffRepository.findById(sdfsff).ifPresent(sdfsffEntity -> {
            model.addAttribute("isOkay");
            fdsad.setSdfsffSet(sdfsffEntity);
            sdfsffEntity.setFdsad(fdsad);
            sdfsffRepository.save(sdfsffEntity);
        });

        fdsadRepository.save(fdsad);
        setAttributes(model, fdsadRepository, getClass());
        return "redirect:/fdsad/";
    }

    private Fdsad create(final String aaa, final Integer bbb) {
        Fdsad fdsad  = new Fdsad();

        fdsad.setAaa(aaa);
        try {
            fdsad.setBbb(Integer.valueOf(bbb));
        } catch (final Exception ignored) {
        }

        return fdsad;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), fdsadRepository, new String[]{"sdfsffSet"}, sdfsffRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        fdsadRepository.deleteById(id);

        setAttributes(model, fdsadRepository, getClass());
        return "redirect:/fdsad/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, fdsadRepository);
    }
}