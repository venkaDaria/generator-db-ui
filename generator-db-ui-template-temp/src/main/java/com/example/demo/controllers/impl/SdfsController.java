package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Sdfs;
import com.example.demo.repositories.SdfsRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.SdfsfsfRepository;

@Controller
@RequestMapping("/sdfs")
public class SdfsController extends BaseController<Sdfs> {

    private final SdfsRepository sdfsRepository;
    private final SdfsfsfRepository sdfsfsfRepository;

    @Autowired
    public SdfsController(final SdfsRepository sdfsRepository, final SdfsfsfRepository sdfsfsfRepository) {
        this.sdfsRepository = sdfsRepository;
        this.sdfsfsfRepository = sdfsfsfRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, sdfsRepository, Sdfs.class);
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, @RequestParam final String sdf, @RequestParam(required = false) final Long sdfsfsf) {
        final Sdfs sdfs = create(sdf);

        id.ifPresent(sdfs::setId);

        if (sdfsfsf != null) {
            sdfsfsfRepository.findById(sdfsfsf).ifPresent(sdfsfsfEntity -> {
                model.addAttribute("isOkay");
                sdfs.setSdfsfsf(sdfsfsfEntity);
                sdfsfsfEntity.setSdfs(sdfs);
            });
        }

        sdfsRepository.save(sdfs);
        setAttributes(model, sdfsRepository, Sdfs.class);
        return "redirect:/sdfs/";
    }

    private Sdfs create(@RequestParam final String sdf) {
        final Sdfs sdfs  = new Sdfs();

        sdfs.setSdf(sdf);

        return sdfs;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, Sdfs.class, sdfsRepository, new String[]{"sdfsfsf"}, sdfsfsfRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        sdfsRepository.deleteById(id);

        setAttributes(model, sdfsRepository, Sdfs.class);
        return "redirect:/sdfs/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, sdfsRepository);
    }
}