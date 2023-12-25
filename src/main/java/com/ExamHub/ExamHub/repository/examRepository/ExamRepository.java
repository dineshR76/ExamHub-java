package com.ExamHub.ExamHub.repository.examRepository;

import com.ExamHub.ExamHub.entity.examEntity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Integer> {
}
