package com.ExamHum.ExamHum.dao.examDao;

import com.ExamHum.ExamHum.entity.examEntity.Exam;
import com.ExamHum.ExamHum.repository.examRepository.ExamRepository;
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
