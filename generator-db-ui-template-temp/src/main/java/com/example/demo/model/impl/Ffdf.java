package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import org.thymeleaf.util.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "ffdffsdfsSet" })
public class Ffdf extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fdsf;
    @OneToMany(mappedBy = "ffdf", cascade = CascadeType.ALL)
    private Set<Ffdffsdfs> ffdffsdfsSet;
    
    public String getName() {
        return StringUtils.toString(this.fdsf);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
