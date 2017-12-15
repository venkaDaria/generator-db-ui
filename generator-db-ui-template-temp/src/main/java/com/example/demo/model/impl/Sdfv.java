package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "dasd" })
public class Sdfv extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fsfsdf;
    private String fsfsdffsc;
    @OneToOne(mappedBy = "sdfv", cascade = CascadeType.ALL)
    private Dasd dasd;
    
    public String getName() {
        return this.fsfsdffsc;
    }
    
    @Override
    public String toString() {
        return this.fsfsdffsc;
    }
}
