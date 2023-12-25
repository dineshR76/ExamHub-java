package com.ExamHub.ExamHub.dao.examDao;

import com.ExamHub.ExamHub.repository.examRepository.ExamRepository;
import com.ExamHub.ExamHub.entity.examEntity.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ExamDao {

    @Autowired
    private ExamRepository examRepository;
    public Exam createExam(Exam exam) {
       return examRepository.save(exam);
    }

    public Exam getExamById(int examId) {
        return examRepository.findById(examId).get();
    }

    public Exam updateExam(Exam exam) {
        return examRepository.save(exam);
    }

    public Page<Exam> getAllExam(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
       return examRepository.findAll(pageable);
    }

    public void deleteExam(Exam examId) {
        examRepository.delete(examId);
    }
}
