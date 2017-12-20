package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "asdad" })
public class Sadasd extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String dsada;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asdad_id")
    private Asdad asdad;
}
