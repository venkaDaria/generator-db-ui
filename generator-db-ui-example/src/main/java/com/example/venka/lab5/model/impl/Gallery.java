package com.example.venka.lab5.model.impl;

import com.example.venka.lab5.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"exhibitions"}, callSuper = false)
@Entity(name = "Galleries")
public class Gallery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String owner;

    @OneToMany(mappedBy = "gallery")
    private Set<Exhibition> exhibitions = new HashSet<>();

    @Override
    public String toString() {
        return name;
    }
}
