package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;

@Data
@Entity
public class Bbb extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
