package com.ExamHub.dao.userAnswerDao;

import com.ExamHub.entity.examEntity.UserAnswer;
import com.ExamHub.repository.userAnswerRepository.UserAnswerRepository;
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
