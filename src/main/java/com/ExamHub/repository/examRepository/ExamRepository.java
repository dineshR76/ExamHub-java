package com.ExamHub.repository.examRepository;

import com.ExamHub.entity.examEntity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Integer> {
}
