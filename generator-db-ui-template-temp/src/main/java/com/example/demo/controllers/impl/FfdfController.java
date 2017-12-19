package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Ffdf;
import com.example.demo.repositories.FfdfRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.FfdffsdfsRepository;

@Controller
@RequestMapping("/ffdf")
public class FfdfController extends BaseController<Ffdf> {

    private final FfdfRepository ffdfRepository;
    private final FfdffsdfsRepository ffdffsdfsRepository;

    @Autowired
    public FfdfController(final FfdfRepository ffdfRepository, final FfdffsdfsRepository ffdffsdfsRepository) {
        this.ffdfRepository = ffdfRepository;
        this.ffdffsdfsRepository = ffdffsdfsRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, ffdfRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, final String fdsf, final long... ffdffsdfsSet) {
        final Ffdf ffdf = create(fdsf);

        id.ifPresent(ffdf::setId);

        Arrays.stream(ffdffsdfss).forEach(ffdffsdfsId -> ffdffsdfsRepository.findById(ffdffsdfsId)
        .ifPresent(ffdffsdfs -> {
            ffdf.getFfdffsdfsSet().add(ffdffsdfs);;
            ffdffsdfs.setFfdfSet(ffdf);
        }
        ));

        ffdfRepository.save(ffdf);
        setAttributes(model, ffdfRepository, getClass());
        return "redirect:/ffdf/";
    }

    private Ffdf create(final String fdsf) {
        Ffdf ffdf  = new Ffdf();

        ffdf.setFdsf(fdsf);

        return ffdf;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), ffdfRepository, new String[]{"ffdffsdfsSet"}, ffdffsdfsRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        ffdfRepository.deleteById(id);

        setAttributes(model, ffdfRepository, getClass());
        return "redirect:/ffdf/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, ffdfRepository);
    }
}