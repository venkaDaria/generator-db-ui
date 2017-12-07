package com.example.venka.lab5.controllers.impl;

import com.example.venka.lab5.controllers.BaseController;
import com.example.venka.lab5.model.impl.Artist;
import com.example.venka.lab5.repositories.ArtistRepository;
import com.example.venka.lab5.repositories.PaintingRepository;
import com.example.venka.lab5.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/artist")
public class ArtistController extends BaseController<Artist> {

    private final ArtistRepository artistRepository;
    private final PaintingRepository paintingRepository;

    @Autowired
    public ArtistController(final ArtistRepository artistRepository, final PaintingRepository paintingRepository) {
        this.artistRepository = artistRepository;
        this.paintingRepository = paintingRepository;
    }

    @GetMapping("/")
    public String getAllArtists(final Model model) {
        setAttributes(model, artistRepository, Artist.class);
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String saveArtist(
            final Model model, @PathVariable final Optional<Long> id,
            @RequestParam final String name, @RequestParam final String country,
            @RequestParam final String dateBirth, @RequestParam final String dateDeath,
            @RequestParam final long... paintings
    ) {
        final Artist artist = new Artist();
        artist.setName(name);
        artist.setCountry(country);

        try {
            artist.setDateBirth(DateTransformer.parse(dateBirth));
        } catch (final Exception ignored) {
        }

        try {
            artist.setDateDeath(DateTransformer.parse(dateDeath));
        } catch (final Exception ignored) {
        }

        id.ifPresent(artist::setId);

        Arrays.stream(paintings).forEach(paintingId -> paintingRepository.findById(paintingId)
                .ifPresent(painting -> {
                            artist.getPaintings().add(painting);
                            painting.setArtist(artist);
                        }
                ));

        artistRepository.save(artist);
        setAttributes(model, artistRepository, Artist.class);
        return "redirect:/artist/";
    }

    @PostMapping("/rm/{id}")
    public String deleteArtist(final Model model, @PathVariable final long id) {
        artistRepository.deleteById(id);

        setAttributes(model, artistRepository, Artist.class);
        return "redirect:/artist/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, artistRepository);
    }

    @GetMapping({"/save/{id}", "/save"})
    public String save(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, Artist.class, artistRepository,
                new String[]{"paintings"}, paintingRepository);
    }
}