package com.example.venka.lab5.controllers.impl;

import com.example.venka.lab5.controllers.BaseController;
import com.example.venka.lab5.model.impl.Painting;
import com.example.venka.lab5.repositories.ArtistRepository;
import com.example.venka.lab5.repositories.ExhibitionRepository;
import com.example.venka.lab5.repositories.PaintingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/painting")
public class PaintingController extends BaseController<Painting> {

    private final PaintingRepository paintingRepository;
    private final ArtistRepository artistRepository;
    private final ExhibitionRepository exhibitionRepository;

    @Autowired
    public PaintingController(
            final PaintingRepository paintingRepository,
            final ArtistRepository artistRepository,
            final ExhibitionRepository exhibitionRepository) {
        this.paintingRepository = paintingRepository;
        this.artistRepository = artistRepository;
        this.exhibitionRepository = exhibitionRepository;
    }

    @GetMapping("/")
    public String getAllPaintings(final Model model) {
        setAttributes(model, paintingRepository, Painting.class);
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePainting(final Model model, @PathVariable final Optional<Long> id,
                               @RequestParam final String name, @RequestParam final String sizes,
                               @RequestParam final String genre, @RequestParam final String surface,
                               @RequestParam final String technique, @RequestParam final long artist,
                               @RequestParam final long... exhibitions) {
        final Painting painting = new Painting();
        painting.setName(name);
        painting.setSizes(sizes);
        painting.setGenre(genre);
        painting.setSurface(surface);
        painting.setTechnique(technique);

        id.ifPresent(painting::setId);

        artistRepository.findById(artist).ifPresent(entity -> {
            model.addAttribute("isOkay");
            painting.setArtist(entity);
            entity.getPaintings().add(painting);
            artistRepository.save(entity);
        });

        Arrays.stream(exhibitions).forEach(exhibitionId -> exhibitionRepository.findById(exhibitionId)
                .ifPresent(exhibition -> {
                            painting.getExhibitions().add(exhibition);
                            exhibition.getPaintings().add(painting);
                        }
                ));

        paintingRepository.save(painting);
        setAttributes(model, paintingRepository, Painting.class);
        return "redirect:/painting/";
    }

    @PostMapping("/rm/{id}")
    public String deletePainting(final Model model, @PathVariable final long id) {
        paintingRepository.deleteById(id);

        setAttributes(model, paintingRepository, Painting.class);
        return "redirect:/painting/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, paintingRepository);
    }

    @GetMapping({"/save/{id}", "/save"})
    public String save(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, Painting.class, paintingRepository,
                new String[]{"exhibitions", "artist"}, exhibitionRepository, artistRepository);
    }
}