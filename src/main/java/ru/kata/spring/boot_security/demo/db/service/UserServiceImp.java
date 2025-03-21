package ru.kata.spring.boot_security.demo.db.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.db.dao.UserDao;
import ru.kata.spring.boot_security.demo.db.models.User;

import java.util.List;

@Service

public class UserServiceImp implements UserService {
    private final UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
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
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public void changeUser(User user) {
        userDao.changeUser(user);
    }
}
