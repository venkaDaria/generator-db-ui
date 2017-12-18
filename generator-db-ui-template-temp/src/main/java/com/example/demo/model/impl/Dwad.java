package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "edadwad" })
public class Dwad extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String dsa;
    private String dsadadad;
    @OneToMany(mappedBy = "dwad", cascade = CascadeType.ALL)
    private Edadwad edadwadSet;
    
    public String getName() {
        return this.dsa;
    }
    
    @Override
    public String toString() {
        return this.dsa;
    }
}
