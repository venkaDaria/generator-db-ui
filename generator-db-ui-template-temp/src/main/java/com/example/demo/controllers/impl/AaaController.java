package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Aaa;
import com.example.demo.repositories.AaaRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.repositories.BbbRepository;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

import com.example.demo.BbbRepository;


@Controller
@RequestMapping("/aaa")
public class AaaController extends BaseController<Aaa> {

    private final AaaRepository aaaRepository;
    private final BbbRepository bbbRepository;


    @Autowired
    public AaaController(final AaaRepository aaaRepository, final BbbRepository bbbRepository) {
        this.aaaRepository = aaaRepository;
        this.bbbRepository = bbbRepository;

    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, aaaRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, final String aaa, final String bbb) {
        final Aaa aaa = create(, aaa, bbb);

        id.ifPresent(aaa::setId);

        Repository.findById().ifPresent(Entity -> {
    model.addAttribute("isOkay");
    aaacontroller.gets().add()
    .setAaaController(aaacontroller)
    Repository.save();
});


        aaaRepository.save(aaa);
        setAttributes(model, aaaRepository, getClass());
        return "redirect:/aaa/";
    }

    private AaaController create(final String aaa, final String bbb) {
    AaaController aaacontroller  = new AaaController();

    AaaController.setAaa(aaa);
    AaaController.setBbb(bbb);

    return aaacontroller;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), aaaRepository, bbbRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        aaaRepository.deleteById(id);

        setAttributes(model, aaaRepository, getClass());
        return "redirect:/aaa/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, aaaRepository);
    }
}