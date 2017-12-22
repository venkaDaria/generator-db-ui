package com.example.demo.model.impl;

import com.example.demo.model.*;
import lombok.*;
import javax.persistence.*;
import org.thymeleaf.util.*;
import java.lang.annotation.*;
import java.util.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = { "userSet" })
public class Role extends BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<User> userSet;
    
    public String getName() {
        return StringUtils.toString(this.name);
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
    
    public Role() {
        ((Role)this).userSet = new HashSet<User>();
    }
}
