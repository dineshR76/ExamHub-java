package com.examHub.service;


import com.examHub.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User grantAccess(User user);
    Optional<User> getUserById(int id);
    List<User> getAllUser();
    User findByUsername(String username);
}
