package com.example.venka.lab5.model.impl;

import com.example.venka.lab5.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"paintings", "gallery"}, callSuper = false)
@Entity(name = "Exhibitions")
public class Exhibition extends BaseEntity {

    @ManyToMany(mappedBy = "exhibitions")
    private final Set<Painting> paintings = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_id")
    private Gallery gallery;

    @Override
    public String toString() {
        return name;
    }
}
