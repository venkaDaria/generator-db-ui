package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "bbb" })
public class Aaa extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String aaa;
    private String bbb;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bbb_id")
    private Bbb bbb;
}
