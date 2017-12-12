package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;
import java.lang.annotation.*;

@Data
@Entity
public class Fdsf extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fdsf;
    private String fdsf324;
    
    public String getName() {
        return this.fdsf324;
    }
    
    @Override
    public String toString() {
        return this.fdsf324;
    }
}
