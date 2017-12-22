package com.example.demo.controllers.impl;

import com.example.demo.model.impl.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.controllers.BaseController;
import com.example.demo.utils.DateTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Arrays;
import java.util.Optional;

import com.example.demo.repositories.RoleRepository;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/")
    public String getAll(final Model model) {
        setAttributes(model, userRepository, User.class);
        return "table";
    }

    @PostMapping({"/{id}", "/"})
    public String savePost(final Model model, @PathVariable final Optional<Long> id, @RequestParam final String email, @RequestParam final String name, @RequestParam final String dateBirth, @RequestParam(required = false) final Long role) {
        final User user = create(email, name, dateBirth);

        id.ifPresent(user::setId);

        if (role != null) {
            roleRepository.findById(role).ifPresent(roleEntity -> {
                model.addAttribute("isOkay");
                user.setRole(roleEntity);
                roleEntity.getUserSet().add(user);
            });
        }

        userRepository.save(user);
        setAttributes(model, userRepository, User.class);
        return "redirect:/user/";
    }

    private User create(@RequestParam final String email, @RequestParam final String name, @RequestParam final String dateBirth) {
        final User user = new User();

        user.setEmail(email);
        user.setName(name);
        try {
            user.setDateBirth(DateTransformer.parse(dateBirth));
        } catch (final Exception ignored) {
        }

        return user;
}

    @GetMapping({"/save/{id}", "/save"})
    public String saveGet(final Model model, @PathVariable final Optional<Long> id) {
        return super.save(model, id, User.class, userRepository, new String[]{"role"}, roleRepository);
    }

    @PostMapping("/rm/{id}")
    public String delete(final Model model, @PathVariable final long id) {
        userRepository.deleteById(id);

        setAttributes(model, userRepository, User.class);
        return "redirect:/user/";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(final Model model, @PathVariable final long id) {
        return super.confirm(model, id, userRepository);
    }
}