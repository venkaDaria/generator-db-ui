package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;
import org.thymeleaf.util.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "sdfs" })
public class Sdfsfsf extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fsdffdsfs;
    @OneToOne
    private Sdfs sdfs;
    
    public String getName() {
        return StringUtils.toString(this.fsdffdsfs);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
