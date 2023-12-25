package com.ExamHub.serviceImpl.userAnswerServiseImpl;

import com.ExamHub.dao.userAnswerDao.UserAnswerDao;
import com.ExamHub.dto.userAnswerRequestDto.UserAnswerRequestDto;
import com.ExamHub.entity.examEntity.UserAnswer;
import com.ExamHub.service.examService.ExamService;
import com.ExamHub.service.questionService.QuestionService;
import com.ExamHub.service.userAnswerService.UserAnswerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {

    @Autowired
    private ExamService examService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserAnswerDao userAnswerDao;

    @Override
    public UserAnswer saveUserAnswer(UserAnswerRequestDto userAnswerRequestDto) throws JsonProcessingException {

     return userAnswerDao.saveUserAnswer(this.requestDtoToEntity(userAnswerRequestDto));

    }

    private UserAnswer requestDtoToEntity(UserAnswerRequestDto userAnswerRequestDto) throws JsonProcessingException {
        UserAnswer userAnswer = new UserAnswer();

        if(userAnswerRequestDto.getQuestionId() != null ){
            userAnswer.setQuestionId(questionService.getQuestionById(userAnswerRequestDto.getQuestionId()));
        }else{
            throw  new NullPointerException("questionId is required");
        }

        if(userAnswerRequestDto.getExamId() != null ){
            userAnswer.setUserExamId(examService.getExamById(userAnswerRequestDto.getExamId()));
        }else{
            throw  new NullPointerException("examId is required");
        }

        if(StringUtils.isNotEmpty(userAnswerRequestDto.getSelectedAnswer())){

            ObjectMapper mapper = new ObjectMapper();
            String answer = mapper.writeValueAsString(userAnswerRequestDto.getSelectedAnswer());
            userAnswer.setSelectedAnswer(answer);
        }else{
            throw  new NullPointerException("selectedAnswer is required");
        }
        return userAnswer;
    }
}
