package com.example.venka.lab5.controllers.impl;

import com.example.venka.lab5.controllers.BaseController;
import com.example.venka.lab5.model.impl.Gallery;
import com.example.venka.lab5.repositories.ExhibitionRepository;
import com.example.venka.lab5.repositories.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/gallery")
public class GalleryController extends BaseController<Gallery> {

    private final GalleryRepository galleryRepository;
    private final ExhibitionRepository exhibitionRepository;

    @Autowired
    public GalleryController(final GalleryRepository galleryRepository, final ExhibitionRepository exhibitionRepository) {
        this.galleryRepository = galleryRepository;
        this.exhibitionRepository = exhibitionRepository;
    }

    @GetMapping("/")
    public String getAllGalleries(final Model model) {
        setAttributes(model, galleryRepository, Gallery.class);
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String saveGallery(final Model model, @PathVariable final Optional<Long> id,
                              @RequestParam final String name, @RequestParam final String owner,
                              @RequestParam final long... exhibitions) {
        final Gallery gallery = new Gallery();
        gallery.setName(name);
        gallery.setOwner(owner);

        id.ifPresent(gallery::setId);

        Arrays.stream(exhibitions).forEach(exhibitionId -> exhibitionRepository.findById(exhibitionId)
                .ifPresent(exhibition -> {
                            gallery.getExhibitions().add(exhibition);
                            exhibition.setGallery(gallery);
                        }
                ));

        galleryRepository.save(gallery);
        setAttributes(model, galleryRepository, Gallery.class);
        return "redirect:/gallery/";
    }

    @PostMapping("/rm/{id}")
    public String deleteGallery(final Model model, @PathVariable final long id) {
        galleryRepository.deleteById(id);

        setAttributes(model, galleryRepository, Gallery.class);
        return "redirect:/gallery/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, galleryRepository);
    }

    @GetMapping({"/save/{id}", "/save"})
    public String save(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, Gallery.class, galleryRepository,
                new String[]{"exhibitions"}, exhibitionRepository);
    }
}