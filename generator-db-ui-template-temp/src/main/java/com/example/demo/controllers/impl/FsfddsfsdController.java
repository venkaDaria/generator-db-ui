package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Fsfddsfsd;
import com.example.demo.repositories.FsfddsfsdRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.CxvxvvxRepository;

@Controller
@RequestMapping("/fsfddsfsd")
public class FsfddsfsdController extends BaseController<Fsfddsfsd> {

    private final FsfddsfsdRepository fsfddsfsdRepository;
    private final CxvxvvxRepository cxvxvvxRepository;

    @Autowired
    public FsfddsfsdController(final FsfddsfsdRepository fsfddsfsdRepository, final CxvxvvxRepository cxvxvvxRepository) {
        this.fsfddsfsdRepository = fsfddsfsdRepository;
        this.cxvxvvxRepository = cxvxvvxRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, fsfddsfsdRepository, Fsfddsfsd.class);
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, @RequestParam final String fdsfsfs, @RequestParam final Integer fdsfsfs3erwr, @RequestParam final String vvvxcfd, @RequestParam(required = false) final Long... cxvxvvxSet) {
        final Fsfddsfsd fsfddsfsd = create(fdsfsfs, fdsfsfs3erwr, vvvxcfd);

        id.ifPresent(fsfddsfsd::setId);

        if (cxvxvvxSet != null) {
            Arrays.stream(cxvxvvxSet).forEach(cxvxvvxId -> cxvxvvxRepository.findById(cxvxvvxId)
            .ifPresent(cxvxvvx -> {
                fsfddsfsd.getCxvxvvxSet().add(cxvxvvx);
                cxvxvvx.setFsfddsfsd(fsfddsfsd);
            }
            ));
        }

        fsfddsfsdRepository.save(fsfddsfsd);
        setAttributes(model, fsfddsfsdRepository, Fsfddsfsd.class);
        return "redirect:/fsfddsfsd/";
    }

    private Fsfddsfsd create(@RequestParam final String fdsfsfs, @RequestParam final Integer fdsfsfs3erwr, @RequestParam final String vvvxcfd) {
        final Fsfddsfsd fsfddsfsd  = new Fsfddsfsd();

        fsfddsfsd.setFdsfsfs(fdsfsfs);
        fsfddsfsd.setFdsfsfs3erwr(fdsfsfs3erwr);
        try {
            fsfddsfsd.setVvvxcfd(DateTransformer.parse(vvvxcfd));
        } catch (final Exception ignored) {
        }

        return fsfddsfsd;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, Fsfddsfsd.class, fsfddsfsdRepository, new String[]{"cxvxvvxSet"}, cxvxvvxRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        fsfddsfsdRepository.deleteById(id);

        setAttributes(model, fsfddsfsdRepository, Fsfddsfsd.class);
        return "redirect:/fsfddsfsd/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, fsfddsfsdRepository);
    }
}