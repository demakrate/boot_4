package ru.kata.spring.boot_security.demo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.db.models.Role;
import ru.kata.spring.boot_security.demo.db.models.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {


    private final UserRepository userRepository;


    private final RolesRepository roleRepository;


    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RolesRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args) {
        Role userRole = new Role();
        userRole.setName("USER");
        roleRepository.save(userRole);

        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        roleRepository.save(adminRole);


        User user = new User();
        user.setName("user");
        user.setAge(1);
        user.setPassword(passwordEncoder.encode("password"));
        user.setMail("1@mail.ru");
        user.setRoles(Set.of(userRole));
        userRepository.save(user);


        User admin = new User();
        admin.setName("admin");
        admin.setAge(2);
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setMail("2@mail.ru");
        admin.setRoles(Set.of(adminRole, userRole));
        userRepository.save(admin);
    }
}