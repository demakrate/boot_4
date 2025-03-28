package ru.kata.spring.boot_security.demo.db.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.db.dao.UserDao;
import ru.kata.spring.boot_security.demo.db.models.Role;
import ru.kata.spring.boot_security.demo.db.models.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserDao userDao, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> getAllUsers() {
        return (userDao.getAllUsers());
    }

    @Override
    public User getUserByID(long id) {
        return (userDao.getUserByID(id));
    }

    @Override
    @Transactional
    public void addUser(User user) {

        Set<Role> managedRoles = user.getRoles().stream()
                .map(role -> roleService.findByName(role.getRoleName())
                )
                .collect(Collectors.toSet());

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setAge(user.getAge());
        newUser.setMail(user.getMail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRoles(managedRoles);

        userDao.addUser(newUser);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public void changeUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.changeUser(user);
    }
}
