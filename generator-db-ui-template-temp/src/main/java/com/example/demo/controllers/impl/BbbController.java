package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Bbb;
import com.example.demo.repositories.BbbRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

com.example.demo.AaaRepository;


@Controller
@RequestMapping("/bbb")
public class BbbController extends BaseController<Bbb> {

    private final BbbRepository bbbRepository;
    private final AaaRepository aaaRepository;


    @Autowired
    public BbbController(final BbbRepository bbbRepository, final AaaRepository aaaRepository) {
        this.bbbRepository = bbbRepository;
        this.aaaRepository = aaaRepository;

    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, bbbRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, final String ccc, final LocalDate ddd, final Integer eee) {
        final Bbb bbb = create(, ccc, ddd, eee);

        id.ifPresent(bbb::setId);

        Repository.findById().ifPresent(Entity -> {
    model.addAttribute("isOkay");
    bbbcontroller.gets().add()
    .setBbbController(bbbcontroller)
    Repository.save();
});


        bbbRepository.save(bbb);
        setAttributes(model, bbbRepository, getClass());
        return "redirect:/bbb/";
    }

    private BbbController create(final String ccc, final LocalDate ddd, final Integer eee) {
    BbbController bbbcontroller  = new BbbController();

    BbbController.setCcc(ccc);
    try {
        BbbController.setDdd(ddd);
    } catch (final Exception ignored) {
    }
    try {
        BbbController.setEee(Integer.valueOf(eee));
    } catch (final Exception ignored) {
    }

    return bbbcontroller;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), bbbRepository, aaaRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        bbbRepository.deleteById(id);

        setAttributes(model, bbbRepository, getClass());
        return "redirect:/bbb/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, bbbRepository);
    }
}