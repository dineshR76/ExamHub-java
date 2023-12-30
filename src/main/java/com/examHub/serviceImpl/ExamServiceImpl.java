package com.examHub.serviceImpl;

import com.examHub.dao.ExamDao;
import com.examHub.dto.examHubRequestDto.ExamRequestDto;
import com.examHub.entity.Exam;
import com.examHub.service.ExamService;
import com.examHub.utils.constants.CommonConstants;
import com.examHub.utils.dateTimeUtils.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Override
    public Exam updateExam(ExamRequestDto examRequestDto) throws ParseException {

        if(examRequestDto.getExamId() == null){
            throw new NullPointerException("examId required");
        }

        Exam exam = convertDtoToEntity(examRequestDto);
        return examDao.updateExam(exam);
    }

    @Override
    public Page<Exam> getAllExam(int page, int size) {
        return examDao.getAllExam(page,size);
    }

    @Override
    public Exam getExamById(int examId) {
        return examDao.getExamById(examId);
    }

    @Override
    public void deleteExam(int examId) {
       Exam exam = examDao.getExamById(examId);
        examDao.deleteExam(exam);
    }

    private Exam convertDtoToEntity(ExamRequestDto examRequestDto) throws ParseException {
        Exam exam = (examRequestDto.getExamId() != null) ? examDao.getExamById(examRequestDto.getExamId()) : new Exam();

        if(StringUtils.isNotEmpty(examRequestDto.getTitle()) ){
            exam.setTitle(examRequestDto.getTitle());
        }else{
            throw  new NullPointerException("title is required");
        }

        if(StringUtils.isNotEmpty(examRequestDto.getStartTime()) ){
            exam.setStartTime(DateTimeUtils.convertStringToLocalDateTime(examRequestDto.getStartTime(), CommonConstants.DATETIMEFORMET));
        }else{
            throw  new NullPointerException("startTime is required");
        }

        if(StringUtils.isNotEmpty(examRequestDto.getEndTime()) ){
            exam.setEndTime(DateTimeUtils.convertStringToLocalDateTime(examRequestDto.getEndTime(),CommonConstants.DATETIMEFORMET ));
        }else{
            throw  new NullPointerException("endTime is required");
        }
        return exam;
    }
}
