package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Ffdffsdfs;
import com.example.demo.repositories.FfdffsdfsRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.FfdfRepository;

@Controller
@RequestMapping("/ffdffsdfs")
public class FfdffsdfsController extends BaseController<Ffdffsdfs> {

    private final FfdffsdfsRepository ffdffsdfsRepository;
    private final FfdfRepository ffdfRepository;

    @Autowired
    public FfdffsdfsController(final FfdffsdfsRepository ffdffsdfsRepository, final FfdfRepository ffdfRepository) {
        this.ffdffsdfsRepository = ffdffsdfsRepository;
        this.ffdfRepository = ffdfRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, ffdffsdfsRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, final String scdfdsf, final long... ffdfSet) {
        final Ffdffsdfs ffdffsdfs = create(scdfdsf);

        id.ifPresent(ffdffsdfs::setId);

        Arrays.stream(ffdfs).forEach(ffdfId -> ffdfRepository.findById(ffdfId)
        .ifPresent(ffdf -> {
            ffdffsdfs.getFfdfSet().add(ffdf);;
            ffdf.getFfdffsdfsSet().add(ffdffsdfs);
        }
        ));

        ffdffsdfsRepository.save(ffdffsdfs);
        setAttributes(model, ffdffsdfsRepository, getClass());
        return "redirect:/ffdffsdfs/";
    }

    private Ffdffsdfs create(final String scdfdsf) {
        Ffdffsdfs ffdffsdfs  = new Ffdffsdfs();

        ffdffsdfs.setScdfdsf(scdfdsf);

        return ffdffsdfs;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), ffdffsdfsRepository, new String[]{"ffdfSet"}, ffdfRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        ffdffsdfsRepository.deleteById(id);

        setAttributes(model, ffdffsdfsRepository, getClass());
        return "redirect:/ffdffsdfs/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, ffdffsdfsRepository);
    }
}