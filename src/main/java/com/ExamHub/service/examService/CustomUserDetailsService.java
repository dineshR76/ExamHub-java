package com.ExamHub.service.examService;


import com.ExamHub.ExamHub.utils.constants.MessageConstant;

import com.ExamHub.dao.examDao.UserDao;
import com.ExamHub.entity.examEntity.User;
import com.ExamHub.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        final User user = this.userDao.findByUsername(userName);

        if (user == null) {
            throw new UsernameNotFoundException(("User not found !!"));
        } else {
            return new CustomUserDetails(user, user.getRole().name(), user.getUserId());
        }
    }

    public Optional<User> findOneByEmail(String email) {
        return userDao.findOneByUsername(email);
    }

    public User findByUsername(String email) {
        Optional<User> login = findOneByEmail(email);
        return login.orElse(null);
    }

    public void saveOrUpdate(User user) throws Exception {
        try {
            userDao.createUser(user);
        } catch (Exception e) {
            throw new Exception(MessageConstant.FAILED_TO_SAVE_OR_UPDATE);
        }
    }
}

