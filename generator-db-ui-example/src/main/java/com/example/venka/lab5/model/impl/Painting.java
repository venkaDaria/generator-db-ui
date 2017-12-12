package com.example.venka.lab5.model.impl;

import com.example.venka.lab5.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"exhibitions", "artist"}, callSuper = false)
@Entity(name = "Paintings")
public class Painting extends BaseEntity {

    @ManyToMany
    @JoinTable(name = "painting_exhibitions", joinColumns = @JoinColumn(name = "painting_id"),
            inverseJoinColumns = @JoinColumn(name = "exhibition_id"))
    private final Set<Exhibition> exhibitions = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String sizes;
    private String genre;
    private String surface;
    private String technique;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @Override
    public String toString() {
        return name;
    }
}
