package ru.kata.spring.boot_security.demo.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.db.models.User;
import ru.kata.spring.boot_security.demo.db.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;


@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
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
    public String addUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            return ("message");
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
    public String changeUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            return ("message");
        }
        service.changeUser(user);
        model.addAttribute("message", "Пользователь " + user.getName() + " успешно заменён");
        return ("message");
    }
}