package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import java.time.*;
import java.util.*;
import javax.persistence.*;
import java.lang.annotation.*;
import java.util.HashSet;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "fdsad" })
public class Sdfsff extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate ccc;
    private double ddd;
    @OneToMany(mappedBy = "fdsad", cascade = CascadeType.ALL)
    private HashSet fdsadSet;
    
    public String getName() {
        return (String)this.ddd;
    }
    
    @Override
    public String toString() {
        return (String)this.ddd;
    }
}
