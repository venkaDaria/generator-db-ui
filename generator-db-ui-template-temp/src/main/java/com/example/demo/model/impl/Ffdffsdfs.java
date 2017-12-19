package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;
import org.thymeleaf.util.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "ffdf" })
public class Ffdffsdfs extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String scdfdsf;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ffdf_id")
    private Ffdf ffdf;
    
    public String getName() {
        return StringUtils.toString(this.scdfdsf);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
