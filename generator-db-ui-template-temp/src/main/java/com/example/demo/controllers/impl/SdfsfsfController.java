package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Sdfsfsf;
import com.example.demo.repositories.SdfsfsfRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.SdfsRepository;

@Controller
@RequestMapping("/sdfsfsf")
public class SdfsfsfController extends BaseController<Sdfsfsf> {

    private final SdfsfsfRepository sdfsfsfRepository;
    private final SdfsRepository sdfsRepository;

    @Autowired
    public SdfsfsfController(final SdfsfsfRepository sdfsfsfRepository, final SdfsRepository sdfsRepository) {
        this.sdfsfsfRepository = sdfsfsfRepository;
        this.sdfsRepository = sdfsRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, sdfsfsfRepository, Sdfsfsf.class);
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, @RequestParam final String fsdffdsfs, @RequestParam(required = false) final Long sdfs) {
        final Sdfsfsf sdfsfsf = create(fsdffdsfs);

        id.ifPresent(sdfsfsf::setId);

        if (sdfs != null) {
            sdfsRepository.findById(sdfs).ifPresent(sdfsEntity -> {
                model.addAttribute("isOkay");
                sdfsfsf.setSdfs(sdfsEntity);
                sdfsEntity.setSdfsfsf(sdfsfsf);
                sdfsRepository.save(sdfsEntity);
            });            }

        sdfsfsfRepository.save(sdfsfsf);
        setAttributes(model, sdfsfsfRepository, Sdfsfsf.class);
        return "redirect:/sdfsfsf/";
    }

    private Sdfsfsf create(@RequestParam final String fsdffdsfs) {
        final Sdfsfsf sdfsfsf  = new Sdfsfsf();

        sdfsfsf.setFsdffdsfs(fsdffdsfs);

        return sdfsfsf;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, Sdfsfsf.class, sdfsfsfRepository, new String[]{"sdfs"}, sdfsRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        sdfsfsfRepository.deleteById(id);

        setAttributes(model, sdfsfsfRepository, Sdfsfsf.class);
        return "redirect:/sdfsfsf/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, sdfsfsfRepository);
    }
}