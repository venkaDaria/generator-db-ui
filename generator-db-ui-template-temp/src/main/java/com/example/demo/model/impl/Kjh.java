package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import java.time.*;
import java.util.*;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "dxfghSet" })
public class Kjh extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate hvvbncgvh;
    @ManyToMany(mappedBy = "kjhSet")
    private Set<Dxfgh> dxfghSet;
}
