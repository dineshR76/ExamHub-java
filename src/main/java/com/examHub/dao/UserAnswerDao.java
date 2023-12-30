package com.examHub.dao;

import com.examHub.entity.UserAnswer;
import com.examHub.repository.UserAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAnswerDao {

    @Autowired
    UserAnswerRepository userAnswerRepository;
    public UserAnswer saveUserAnswer(UserAnswer userAnswer) {
        return userAnswerRepository.save(userAnswer);
    }
}
