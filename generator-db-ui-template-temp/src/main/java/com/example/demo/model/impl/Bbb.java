package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import java.time.*;
import javax.persistence.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "aaa" })
public class Bbb extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String ccc;
    private LocalDate ddd;
    private int eee;
    @OneToMany(mappedBy = "aaa", cascade = CascadeType.ALL)
    private Aaa aaaSet;
    
    public String getName() {
        return (String)this.eee;
    }
    
    @Override
    public String toString() {
        return (String)this.eee;
    }
}
