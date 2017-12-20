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
@EqualsAndHashCode(callSuper = false, exclude = { "cxvxvvxSet" })
public class Fsfddsfsd extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fdsfsfs;
    private int fdsfsfs3erwr;
    private LocalDate vvvxcfd;
    @OneToMany(mappedBy = "fsfddsfsd", cascade = CascadeType.ALL)
    private Set<Cxvxvvx> cxvxvvxSet;
    
    public String getName() {
        return StringUtils.toString(this.fdsfsfs);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
