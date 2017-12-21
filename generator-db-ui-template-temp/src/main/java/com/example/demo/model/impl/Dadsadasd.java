package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;
import org.thymeleaf.util.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "dadsa" })
public class Dadsadasd extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String dadadasd;
    @Column(nullable = false)
    private String dada;
    @OneToOne
    @JoinColumn(name = "dadsa_id")
    private Dadsa dadsa;
    
    public String getName() {
        return StringUtils.toString(this.dada);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
