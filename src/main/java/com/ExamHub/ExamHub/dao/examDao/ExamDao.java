package com.ExamHub.ExamHub.dao.examDao;

import com.ExamHub.ExamHub.repository.examRepository.ExamRepository;
import com.ExamHub.ExamHub.entity.examEntity.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamDao {

    @Autowired
    private ExamRepository examRepository;
    public Exam createExam(Exam exam) {
       return examRepository.save(exam);
    }
}
