package com.example.demo.repositories;

import org.springframework.data.repository.*;
import com.example.demo.model.impl.*;

public interface RoleRepository extends CrudRepository<Role, Long>
{
}
