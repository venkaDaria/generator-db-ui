package com.example.venka.lab5.repositories;

import com.example.venka.lab5.model.impl.Painting;
import org.springframework.data.repository.CrudRepository;

public interface PaintingRepository extends CrudRepository<Painting, Long> {
}