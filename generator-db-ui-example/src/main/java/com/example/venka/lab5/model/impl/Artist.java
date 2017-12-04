package com.example.venka.lab5.model.impl;

import com.example.venka.lab5.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"paintings"})
@Entity(name = "Artists")
public class Artist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private LocalDate dateBirth;
    private LocalDate dateDeath;
    private String country;

    @OneToMany(mappedBy = "artist")
    private Set<Painting> paintings = new HashSet<>();

    @Override
    public String toString() {
        return name;
    }
}

