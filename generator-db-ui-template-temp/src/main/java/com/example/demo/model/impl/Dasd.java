package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "sdfv" })
public class Dasd extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fsfsdf;
    @OneToOne
    private Sdfv sdfv;
    
    public String getName() {
        return this.fsfsdf;
    }
    
    @Override
    public String toString() {
        return this.fsfsdf;
    }
}
