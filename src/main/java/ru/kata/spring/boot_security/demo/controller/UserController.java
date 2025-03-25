package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.db.models.Role;
import ru.kata.spring.boot_security.demo.db.models.User;
import ru.kata.spring.boot_security.demo.db.service.UserService;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Set;


@Controller
public class UserController {

    private final UserService service;
    private final RolesRepository rolesRepository;

    public UserController(UserService service, RolesRepository rolesRepository) {
        this.service = service;
        this.rolesRepository = rolesRepository;
    }

    @GetMapping(value = "/getAllUsers")
    public String getAll(Model model) {
        if (!service.getAllUsers().equals(new ArrayList<User>())) {
            model.addAttribute("users", service.getAllUsers());
            return ("allUsers");
        } else {
            model.addAttribute("message", "Записи отсутствуют");
            return ("message");
        }
    }

    @GetMapping(value = "/getUserByID")
    public String getUser(@RequestParam long id, Model model) {
        User user = service.getUserByID(id);
        model.addAttribute("message", user);
        return ("message");
    }

    @PostMapping(value = "/addUser")
    public String addUser(@Valid @ModelAttribute User user, @RequestParam(name = "checkedData", required = false)
        String[] roles, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            return ("message");
        }
        if (roles != null) {
            Role[] rolesArray = new Role[roles.length];
            for (int i = 0; i < rolesArray.length; i++) {
                rolesArray[i] = rolesRepository.findByRoleName(roles[i]).orElseThrow(()
                        -> new RuntimeException("Роль USER не найдена"));
            }
            Set<Role> set = Set.of(rolesArray);
            user.setRoles(set);
        }
        service.addUser(user);
        model.addAttribute("message", "Пользователь " + user.getName()
                + " успешно добавлен");

        return ("message");
    }

    @PostMapping(value = "/deleteUser")
    public String deleteUser(@ModelAttribute("id") long id, Model model) {

        service.deleteUser(id);
        model.addAttribute("message", "Пользователь удален");
        return ("message");
    }

    @PostMapping(value = "/changeUser")
    public String changeUser(@Valid @ModelAttribute User user, @RequestParam(name = "checkedData", required = false)
        String[] roles, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            return ("message");
        }
        if (roles != null) {
            Role[] rolesArray = new Role[roles.length];
            for (int i = 0; i < rolesArray.length; i++) {
                rolesArray[i] = rolesRepository.findByRoleName(roles[i]).orElseThrow(()
                        -> new RuntimeException("Роль USER не найдена"));
            }
            Set<Role> set = Set.of(rolesArray);
            user.setRoles(set);
        }
        service.changeUser(user);
        model.addAttribute("message", "Пользователь успешно заменён");
        return ("message");
    }
}