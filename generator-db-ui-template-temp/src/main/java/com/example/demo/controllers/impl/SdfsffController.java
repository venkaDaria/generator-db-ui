package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Sdfsff;
import com.example.demo.repositories.SdfsffRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.FdsadRepository;

@Controller
@RequestMapping("/sdfsff")
public class SdfsffController extends BaseController<Sdfsff> {

    private final SdfsffRepository sdfsffRepository;
    private final FdsadRepository fdsadRepository;

    @Autowired
    public SdfsffController(final SdfsffRepository sdfsffRepository, final FdsadRepository fdsadRepository) {
        this.sdfsffRepository = sdfsffRepository;
        this.fdsadRepository = fdsadRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, sdfsffRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, final LocalDate ccc, final Double ddd, final long fdsad) {
        final Sdfsff sdfsff = create(ccc, ddd);

        id.ifPresent(sdfsff::setId);

        fdsadRepository.findById(fdsad).ifPresent(fdsadEntity -> {
            model.addAttribute("isOkay");
            sdfsff.getFdsadSet().add(fdsadEntity);
            fdsadEntity.setSdfsff(sdfsff);
            fdsadRepository.save(fdsadEntity);
        });

        sdfsffRepository.save(sdfsff);
        setAttributes(model, sdfsffRepository, getClass());
        return "redirect:/sdfsff/";
    }

    private Sdfsff create(final LocalDate ccc, final Double ddd) {
        Sdfsff sdfsff  = new Sdfsff();

        try {
            sdfsff.setCcc(ccc);
        } catch (final Exception ignored) {
        }
        try {
            sdfsff.setDdd(Double.valueOf(ddd));
        } catch (final Exception ignored) {
        }

        return sdfsff;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), sdfsffRepository, new String[]{"fdsadSet"}, fdsadRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        sdfsffRepository.deleteById(id);

        setAttributes(model, sdfsffRepository, getClass());
        return "redirect:/sdfsff/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, sdfsffRepository);
    }
}