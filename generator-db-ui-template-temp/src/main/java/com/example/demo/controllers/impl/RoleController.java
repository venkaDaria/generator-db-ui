package com.example.demo.controllers.impl;

import com.example.demo.model.impl.Role;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.UserRepository;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController<Role> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleController(final RoleRepository roleRepository, final UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, roleRepository, Role.class);
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, @RequestParam final String name, @RequestParam(required = false) final Long... userSet) {
        final Role role = create(name);

        id.ifPresent(role::setId);

        if (userSet != null) {
            Arrays.stream(userSet).forEach(userId -> userRepository.findById(userId)
            .ifPresent(user -> {
                role.getUserSet().add(user);
                user.setRole(role);
            }));
        }

        roleRepository.save(role);
        setAttributes(model, roleRepository, Role.class);
        return "redirect:/role/";
    }

    private Role create(@RequestParam final String name) {
        final Role role = new Role();

        role.setName(name);

        return role;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, Role.class, roleRepository, new String[]{"userSet"}, userRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        roleRepository.deleteById(id);

        setAttributes(model, roleRepository, Role.class);
        return "redirect:/role/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, roleRepository);
    }
}