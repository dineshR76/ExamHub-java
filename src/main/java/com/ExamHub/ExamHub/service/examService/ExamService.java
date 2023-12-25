package com.ExamHub.ExamHub.service.examService;

import com.ExamHub.ExamHub.dto.examRequestDto.ExamRequestDto;
import com.ExamHub.ExamHub.entity.examEntity.Exam;
import org.springframework.data.domain.Page;

import java.text.ParseException;

public interface ExamService {

    Exam createExam(ExamRequestDto examRequestDto) throws ParseException;

    Exam updateExam(ExamRequestDto examRequestDto) throws ParseException;

    Page<Exam> getAllExam(int page, int size);

    Exam getExamById(int courseId);

    void deleteExam(int examId);
}
