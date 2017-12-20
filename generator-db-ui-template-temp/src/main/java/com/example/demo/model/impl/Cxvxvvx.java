package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import java.time.*;
import javax.persistence.*;
import org.thymeleaf.util.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "fsfddsfsd" })
public class Cxvxvvx extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double fsdf;
    private LocalDateTime fsdfvxcx;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fsfddsfsd_id")
    private Fsfddsfsd fsfddsfsd;
    
    public String getName() {
        return StringUtils.toString(this.fsdfvxcx);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
