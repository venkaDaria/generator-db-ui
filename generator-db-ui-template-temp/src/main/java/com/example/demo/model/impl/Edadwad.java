package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "dwad" })
public class Edadwad extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String das;
    private String dasds;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dwad_id")
    private Dwad dwad;
    
    public String getName() {
        return this.dasds;
    }
    
    @Override
    public String toString() {
        return this.dasds;
    }
}
