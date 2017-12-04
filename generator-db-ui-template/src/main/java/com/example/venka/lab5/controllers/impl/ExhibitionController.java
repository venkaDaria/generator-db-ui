package com.example.venka.lab5.controllers.impl;

import com.example.venka.lab5.controllers.BaseController;
import com.example.venka.lab5.model.impl.Exhibition;
import com.example.venka.lab5.repositories.ExhibitionRepository;
import com.example.venka.lab5.repositories.GalleryRepository;
import com.example.venka.lab5.repositories.PaintingRepository;
import com.example.venka.lab5.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/exhibition")
public class ExhibitionController extends BaseController<Exhibition> {

    private final ExhibitionRepository exhibitionRepository;
    private final GalleryRepository galleryRepository;
    private final PaintingRepository paintingRepository;

    @Autowired
    public ExhibitionController(
            final ExhibitionRepository exhibitionRepository,
            final GalleryRepository galleryRepository,
            final PaintingRepository paintingRepository
    ) {
        this.exhibitionRepository = exhibitionRepository;
        this.galleryRepository = galleryRepository;
        this.paintingRepository = paintingRepository;
    }

    @GetMapping("/")
    public String getAllExhibitions(final Model model) {
        setAttributes(model, exhibitionRepository, Exhibition.class);
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String saveExhibition(final Model model, @PathVariable final Optional<Long> id,
                                 @RequestParam final String name, @RequestParam final String dateStart,
                                 @RequestParam final String dateEnd, @RequestParam final long gallery,
                                 @RequestParam final long... paintings) {
        final Exhibition exhibition = new Exhibition();
        exhibition.setName(name);

        try {
            exhibition.setDateStart(DateTransformer.parse(dateStart));
        } catch (final Exception ignored) {
        }

        try {
            exhibition.setDateEnd(DateTransformer.parse(dateEnd));
        } catch (final Exception ignored) {
        }

        id.ifPresent(exhibition::setId);

        galleryRepository.findById(gallery).ifPresent(entity -> {
            model.addAttribute("isOkay");
            exhibition.setGallery(entity);
            entity.getExhibitions().add(exhibition);
            galleryRepository.save(entity);
        });

        Arrays.stream(paintings).forEach(paintingId -> paintingRepository.findById(paintingId)
                .ifPresent(painting -> {
                            exhibition.getPaintings().add(painting);
                            painting.getExhibitions().add(exhibition);
                        }
                ));

        exhibitionRepository.save(exhibition);
        setAttributes(model, exhibitionRepository, Exhibition.class);
        return "redirect:/exhibition/";
    }

    @PostMapping("/rm/{id}")
    public String deleteExhibition(final Model model, @PathVariable final long id) {
        exhibitionRepository.deleteById(id);

        setAttributes(model, exhibitionRepository, Exhibition.class);
        return "redirect:/exhibition/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, exhibitionRepository);
    }

    @GetMapping({"/save/{id}", "/save"})
    public String save(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, Exhibition.class, exhibitionRepository,
                new String[]{"paintings", "gallery"}, paintingRepository, galleryRepository);
    }
}