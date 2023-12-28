package com.ExamHub.ExamHub.serviceImpl.examServiceImpl;

import com.ExamHub.ExamHub.dao.examDao.UserDao;
import com.ExamHub.ExamHub.entity.examEntity.User;
import com.ExamHub.ExamHub.service.examService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User grantAccess(User user) {
        return userDao.createUser(user);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUsers();
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

}
