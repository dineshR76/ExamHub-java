package com.ExamHum.ExamHum.service.examService;

import com.ExamHum.ExamHum.dto.examRequestDto.ExamRequestDto;
import com.ExamHum.ExamHum.entity.examEntity.Exam;

import java.text.ParseException;

public interface ExamService {

    Exam createExam(ExamRequestDto examRequestDto) throws ParseException;
}
