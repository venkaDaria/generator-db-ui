package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;
import org.thymeleaf.util.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "sdfsfsf" })
public class Sdfs extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String sdf;
    @OneToOne(mappedBy = "sdfs", cascade = CascadeType.ALL)
    private Sdfsfsf sdfsfsf;
    
    public String getName() {
        return StringUtils.toString(this.sdf);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
