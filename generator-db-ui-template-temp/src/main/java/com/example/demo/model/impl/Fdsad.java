package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "sdfsff" })
public class Fdsad extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String aaa;
    private int bbb;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sdfsff_id")
    private Sdfsff sdfsff;
}
