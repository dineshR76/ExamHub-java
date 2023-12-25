package com.ExamHum.ExamHum.repository.examRepository;

import com.ExamHum.ExamHum.entity.examEntity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Integer> {
}
