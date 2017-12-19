package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import java.time.*;
import java.util.*;
import javax.persistence.*;
import org.thymeleaf.util.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "fdsadSet" })
public class Sdfsff extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate ccc;
    private double ddd;
    @OneToMany(mappedBy = "fdsad", cascade = CascadeType.ALL)
    private Set<Fdsad> fdsadSet;
    
    public String getName() {
        return StringUtils.toString(this.ddd);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
