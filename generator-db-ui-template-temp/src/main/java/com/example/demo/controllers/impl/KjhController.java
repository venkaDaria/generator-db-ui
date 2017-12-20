package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Kjh;
import com.example.demo.repositories.KjhRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.DxfghRepository;

@Controller
@RequestMapping("/kjh")
public class KjhController extends BaseController<Kjh> {

    private final KjhRepository kjhRepository;
    private final DxfghRepository dxfghRepository;

    @Autowired
    public KjhController(final KjhRepository kjhRepository, final DxfghRepository dxfghRepository) {
        this.kjhRepository = kjhRepository;
        this.dxfghRepository = dxfghRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, kjhRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, final LocalDate hvvbncgvh, final long... dxfghSet) {
        final Kjh kjh = create(hvvbncgvh);

        id.ifPresent(kjh::setId);

        Arrays.stream(dxfghSet).forEach(dxfghId -> dxfghRepository.findById(dxfghId)
        .ifPresent(dxfgh -> {
            kjh.getDxfghSet().add(dxfgh);
            dxfgh.getKjhSet().add(kjh);
        }
        ));

        kjhRepository.save(kjh);
        setAttributes(model, kjhRepository, getClass());
        return "redirect:/kjh/";
    }

    private Kjh create(final LocalDate hvvbncgvh) {
        Kjh kjh  = new Kjh();

        try {
            kjh.setHvvbncgvh(hvvbncgvh);
        } catch (final Exception ignored) {
        }

        return kjh;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), kjhRepository, new String[]{"dxfghSet"}, dxfghRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        kjhRepository.deleteById(id);

        setAttributes(model, kjhRepository, getClass());
        return "redirect:/kjh/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, kjhRepository);
    }
}