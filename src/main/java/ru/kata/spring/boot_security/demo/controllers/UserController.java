package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.db.models.User;
import ru.kata.spring.boot_security.demo.db.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/getAllUsers")
    public List<User> getAll() {
        if (!service.getAllUsers().equals(new ArrayList<User>())) {
            return (service.getAllUsers());
        } else {
            return (null);
        }
    }

    @GetMapping(value = "/getUserByID")
    public User getUser(@RequestParam long id) {
        return (service.getUserByID(id));
    }

    @PostMapping(value = "/addUser")
    public String addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (bindingResult.getAllErrors().toString());
        }
        service.addUser(user);
        return ("Пользователь " + user.getName() + " успешно добавлен");
    }

    @PostMapping(value = "/deleteUser")
    public String deleteUser(@Valid @RequestBody User user) {
        service.deleteUser(user.getId());
        return ("Пользователь удален");
    }

    @PostMapping(value = "/changeUser")
    public String changeUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (bindingResult.getAllErrors().toString());
        }
        service.changeUser(user);
        return ("Пользователь успешно заменён");
    }
}