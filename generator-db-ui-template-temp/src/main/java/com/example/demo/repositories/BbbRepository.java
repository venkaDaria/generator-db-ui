package com.example.demo.repositories;

import org.springframework.data.repository.*;
import com.example.demo.model.impl.*;

public interface BbbRepository extends CrudRepository<Bbb, Long>
{
}
