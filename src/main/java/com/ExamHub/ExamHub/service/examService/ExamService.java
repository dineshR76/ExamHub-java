package com.ExamHub.ExamHub.service.examService;

import com.ExamHub.ExamHub.dto.examRequestDto.ExamRequestDto;
import com.ExamHub.ExamHub.entity.examEntity.Exam;

import java.text.ParseException;

public interface ExamService {

    Exam createExam(ExamRequestDto examRequestDto) throws ParseException;
}
