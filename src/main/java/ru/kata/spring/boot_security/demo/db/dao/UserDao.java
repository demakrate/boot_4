package ru.kata.spring.boot_security.demo.db.dao;


import ru.kata.spring.boot_security.demo.db.models.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    User getUserByID(long id);

    void addUser(User user);

    void deleteUser(long id);

    void changeUser(User user);
}
