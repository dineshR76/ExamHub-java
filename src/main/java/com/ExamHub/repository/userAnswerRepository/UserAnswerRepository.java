package com.ExamHub.repository.userAnswerRepository;

import com.ExamHub.entity.examEntity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer,Integer> {
}
