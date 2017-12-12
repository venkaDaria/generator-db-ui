package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;
import java.lang.annotation.*;

@Data
@Entity
public class Fdsffsdfs extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String ferw;
    
    public String getName() {
        return this.ferw;
    }
    
    @Override
    public String toString() {
        return this.ferw;
    }
}
