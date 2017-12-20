package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import java.util.*;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "sadasdSet" })
public class Asdad extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String adad;
    @OneToMany(mappedBy = "asdad", cascade = CascadeType.ALL)
    private Set<Sadasd> sadasdSet;
}
