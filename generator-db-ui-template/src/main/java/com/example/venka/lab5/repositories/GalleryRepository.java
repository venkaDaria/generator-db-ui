package com.example.venka.lab5.repositories;

import com.example.venka.lab5.model.impl.Gallery;
import org.springframework.data.repository.CrudRepository;

public interface GalleryRepository extends CrudRepository<Gallery, Long> {
}