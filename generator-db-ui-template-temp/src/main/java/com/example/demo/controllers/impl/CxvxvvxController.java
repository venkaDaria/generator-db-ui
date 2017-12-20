package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Cxvxvvx;
import com.example.demo.repositories.CxvxvvxRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.FsfddsfsdRepository;

@Controller
@RequestMapping("/cxvxvvx")
public class CxvxvvxController extends BaseController<Cxvxvvx> {

    private final CxvxvvxRepository cxvxvvxRepository;
    private final FsfddsfsdRepository fsfddsfsdRepository;

    @Autowired
    public CxvxvvxController(final CxvxvvxRepository cxvxvvxRepository, final FsfddsfsdRepository fsfddsfsdRepository) {
        this.cxvxvvxRepository = cxvxvvxRepository;
        this.fsfddsfsdRepository = fsfddsfsdRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, cxvxvvxRepository, Cxvxvvx.class);
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, @RequestParam final Double fsdf, @RequestParam final String fsdfvxcx, @RequestParam(required = false) final Long fsfddsfsd) {
        final Cxvxvvx cxvxvvx = create(fsdf, fsdfvxcx);

        id.ifPresent(cxvxvvx::setId);

        if (fsfddsfsd != null) {
            fsfddsfsdRepository.findById(fsfddsfsd).ifPresent(fsfddsfsdEntity -> {
                model.addAttribute("isOkay");
                cxvxvvx.setFsfddsfsd(fsfddsfsdEntity);
                fsfddsfsdEntity.getCxvxvvxSet().add(cxvxvvx);
            });
        }

        cxvxvvxRepository.save(cxvxvvx);
        setAttributes(model, cxvxvvxRepository, Cxvxvvx.class);
        return "redirect:/cxvxvvx/";
    }

    private Cxvxvvx create(@RequestParam final Double fsdf, @RequestParam final String fsdfvxcx) {
        final Cxvxvvx cxvxvvx  = new Cxvxvvx();

        cxvxvvx.setFsdf(fsdf);
        try {
            cxvxvvx.setFsdfvxcx(DateTransformer.parseWithTime(fsdfvxcx));
        } catch (final Exception ignored) {
        }

        return cxvxvvx;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, Cxvxvvx.class, cxvxvvxRepository, new String[]{"fsfddsfsd"}, fsfddsfsdRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        cxvxvvxRepository.deleteById(id);

        setAttributes(model, cxvxvvxRepository, Cxvxvvx.class);
        return "redirect:/cxvxvvx/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, cxvxvvxRepository);
    }
}