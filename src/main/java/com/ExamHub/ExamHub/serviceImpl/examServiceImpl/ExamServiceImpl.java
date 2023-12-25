package com.ExamHub.ExamHub.serviceImpl.examServiceImpl;

import com.ExamHub.ExamHub.dao.examDao.ExamDao;
import com.ExamHub.ExamHub.dto.examRequestDto.ExamRequestDto;
import com.ExamHub.ExamHub.utils.dateTimeUtils.DateTimeUtils;
import com.ExamHub.ExamHub.entity.examEntity.Exam;
import com.ExamHub.ExamHub.service.examService.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDao examDao;


    @Override
    public Exam createExam(ExamRequestDto examRequestDto) throws ParseException {

        Exam exam = convertDtoToEntity(examRequestDto);
        return examDao.createExam(exam);
    }

    private Exam convertDtoToEntity(ExamRequestDto examRequestDto) throws ParseException {

        Exam exam = new Exam();
        exam.setTitle(examRequestDto.getTitle());
        exam.setStartTime(DateTimeUtils.convertStringToLocalDateTime(examRequestDto.getStartTime(), "yyyy-MM-dd'T'HH:mm:ss"));
        exam.setEndTime(DateTimeUtils.convertStringToLocalDateTime(examRequestDto.getEndTime(), "yyyy-MM-dd'T'HH:mm:ss"));
        return exam;
    }

}
