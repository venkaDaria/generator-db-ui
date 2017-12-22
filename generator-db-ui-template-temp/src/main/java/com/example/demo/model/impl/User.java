package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import java.time.*;
import javax.persistence.*;
import org.thymeleaf.util.*;
import java.lang.annotation.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "role" })
public class User extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    @Column(nullable = false)
    private String name;
    private LocalDate dateBirth;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
    
    public String getName() {
        return StringUtils.toString(this.name);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}
