package com.examHub.service;

import com.examHub.dto.examHubRequestDto.ExamRequestDto;
import com.examHub.entity.Exam;
import org.springframework.data.domain.Page;

import java.text.ParseException;

public interface ExamService {

    Exam createExam(ExamRequestDto examRequestDto) throws ParseException;

    Exam updateExam(ExamRequestDto examRequestDto) throws ParseException;

    Page<Exam> getAllExam(int page, int size);

    Exam getExamById(int courseId);

    void deleteExam(int examId);
}
