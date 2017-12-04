package com.example.venka.lab5.repositories;

import com.example.venka.lab5.model.impl.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
}