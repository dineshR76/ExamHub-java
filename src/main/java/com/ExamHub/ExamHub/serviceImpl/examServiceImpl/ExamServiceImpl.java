package com.ExamHub.ExamHub.serviceImpl.examServiceImpl;

import com.ExamHub.ExamHub.dao.examDao.ExamDao;
import com.ExamHub.ExamHub.dto.examRequestDto.ExamRequestDto;
import com.ExamHub.ExamHub.entity.examEntity.Exam;
import com.ExamHub.ExamHub.service.examService.ExamService;
import com.ExamHub.ExamHub.utils.dateTimeUtils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;

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
            throw new RuntimeException("examId required");
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

        exam.setTitle(examRequestDto.getTitle());

        LocalDateTime startTime = DateTimeUtils.convertStringToLocalDateTime(examRequestDto.getStartTime(), "yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime endTime = DateTimeUtils.convertStringToLocalDateTime(examRequestDto.getEndTime(), "yyyy-MM-dd'T'HH:mm:ss");

        exam.setStartTime(startTime);
        exam.setEndTime(endTime);

        return exam;
    }
}
