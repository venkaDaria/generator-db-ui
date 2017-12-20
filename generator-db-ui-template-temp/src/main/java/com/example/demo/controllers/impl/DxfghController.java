package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Dxfgh;
import com.example.demo.repositories.DxfghRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.KjhRepository;

@Controller
@RequestMapping("/dxfgh")
public class DxfghController extends BaseController<Dxfgh> {

    private final DxfghRepository dxfghRepository;
    private final KjhRepository kjhRepository;

    @Autowired
    public DxfghController(final DxfghRepository dxfghRepository, final KjhRepository kjhRepository) {
        this.dxfghRepository = dxfghRepository;
        this.kjhRepository = kjhRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, dxfghRepository, getClass());
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, final String zdxfcgvh, final Double hvvbn, final long... kjhSet) {
        final Dxfgh dxfgh = create(zdxfcgvh, hvvbn);

        id.ifPresent(dxfgh::setId);

        Arrays.stream(kjhSet).forEach(kjhId -> kjhRepository.findById(kjhId)
        .ifPresent(kjh -> {
            dxfgh.getKjhSet().add(kjh);
            kjh.getDxfghSet().add(dxfgh);
        }
        ));

        dxfghRepository.save(dxfgh);
        setAttributes(model, dxfghRepository, getClass());
        return "redirect:/dxfgh/";
    }

    private Dxfgh create(final String zdxfcgvh, final Double hvvbn) {
        Dxfgh dxfgh  = new Dxfgh();

        dxfgh.setZdxfcgvh(zdxfcgvh);
        try {
            dxfgh.setHvvbn(Double.valueOf(hvvbn));
        } catch (final Exception ignored) {
        }

        return dxfgh;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, getClass(), dxfghRepository, new String[]{"kjhSet"}, kjhRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        dxfghRepository.deleteById(id);

        setAttributes(model, dxfghRepository, getClass());
        return "redirect:/dxfgh/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, dxfghRepository);
    }
}