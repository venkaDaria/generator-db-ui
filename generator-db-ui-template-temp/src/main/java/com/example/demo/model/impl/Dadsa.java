package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;
import org.thymeleaf.util.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "dadsadasd" })
public class Dadsa extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String dada;
    @OneToOne(mappedBy = "dadsa", cascade = CascadeType.ALL)
    private Dadsadasd dadsadasd;
    
    public String getName() {
        return StringUtils.toString(this.dada);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
