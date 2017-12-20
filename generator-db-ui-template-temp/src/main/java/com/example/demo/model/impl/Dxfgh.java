package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import java.util.*;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "kjhSet" })
public class Dxfgh extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String zdxfcgvh;
    @Column(nullable = false)
    private double hvvbn;
    @ManyToMany
    @JoinTable(name = "dxfgh_kjh", joinColumns = @JoinColumn(name = "kjh_id"), inverseJoinColumns = @JoinColumn)
    private Set<Kjh> kjhSet;
}
