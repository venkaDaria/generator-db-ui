package com.example.demo.controllers;

import com.example.demo.model.BaseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.ui.Model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public abstract class BaseController<T> {

    protected final void setAttributes(
            final Model model,
            final CrudRepository<T, Long> repository,
            final Class entity
    ) {
        model.addAttribute("dataSet", repository.findAll());
        model.addAttribute("headers", BaseEntity.getFields(entity, Field::getName));
        model.addAttribute("className", entity.getSimpleName().toLowerCase());
    }

    @NotNull
    protected final String confirm(
            final Model model,
            final long id,
            final CrudRepository<T, Long> repository
    ) {
        model.addAttribute("entity", repository.findById(id).orElse(null));
        return "confirm";
    }

    @NotNull
    protected final String save(
            final Model model,
            final Optional<Long> optionalId,
            final Class entity,
            final CrudRepository<T, Long> repository,
            final String[] entities,
            final CrudRepository... depsRepositories
    ) {
        optionalId.ifPresent(id -> model.addAttribute("entity", repository.findById(id).orElse(null)));

        final Map<String, Iterable<BaseEntity>> choices = new HashMap<>();
        IntStream.range(0, entities.length).forEach(idx -> choices.put(entities[idx], depsRepositories[idx].findAll()));
        model.addAttribute("choices", choices);

        model.addAttribute("headers", BaseEntity.getHeaders(entity));
        return "save";
    }
}
