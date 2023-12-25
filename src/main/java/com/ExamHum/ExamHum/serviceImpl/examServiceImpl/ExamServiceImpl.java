package com.ExamHum.ExamHum.serviceImpl.examServiceImpl;

import com.ExamHum.ExamHum.dao.examDao.ExamDao;
import com.ExamHum.ExamHum.dto.examRequestDto.ExamRequestDto;
import com.ExamHum.ExamHum.entity.examEntity.Exam;
import com.ExamHum.ExamHum.service.examService.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import static com.ExamHum.ExamHum.utils.dateTimeUtils.DateTimeUtils.convertStringToLocalDateTime;

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
        exam.setStartTime(convertStringToLocalDateTime(examRequestDto.getStartTime(), "yyyy-MM-dd'T'HH:mm:ss"));
        exam.setEndTime(convertStringToLocalDateTime(examRequestDto.getEndTime(), "yyyy-MM-dd'T'HH:mm:ss"));
        return exam;
    }

}
