package com.examHub.serviceImpl;

import com.examHub.dao.QuestionDao;
import com.examHub.dto.examHubRequestDto.QuestionRequestDto;
import com.examHub.entity.Question;
import com.examHub.service.ExamService;
import com.examHub.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private ExamService examService;

    @Override
    public Question createQuestion(QuestionRequestDto questionRequestDto) {
        return questionDao.createQuestion(this.convertRequestDtoToEntity(questionRequestDto));
    }

    @Override
    public Question updateQuestion(QuestionRequestDto questionRequestDto) {

        if(questionRequestDto.getQuestionId() == null){
            throw new NullPointerException("questionId is required");
        }
        return questionDao.createQuestion(this.convertRequestDtoToEntity(questionRequestDto));
    }

    @Override
    public Page<Question> getAllQuestion(int page, int pageSize) {
        return questionDao.getAllQuestion(page,pageSize);
    }

    @Override
    public Question getQuestionById(int questionId) {
        return questionDao.getQuestionById(questionId);
    }

    @Override
    public void deleteQuestion(int questionId) {
      Question question = questionDao.getQuestionById(questionId);
      questionDao.deleteQuestion(question);
    }

    private Question convertRequestDtoToEntity(QuestionRequestDto questionRequestDto) {

        Question question = (questionRequestDto.getQuestionId() != null) ? questionDao.getQuestionById(questionRequestDto.getQuestionId()) : new Question();

        if(StringUtils.isNotEmpty(questionRequestDto.getQuestionText()) ){
            question.setQuestionText(questionRequestDto.getQuestionText());
        }else{
            throw  new NullPointerException("Question text is required");
        }

        if(  questionRequestDto.getExamId() != null ){
            question.setExamId(examService.getExamById(questionRequestDto.getExamId()));
        }else{
            throw  new NullPointerException("examId  is required");
        }

        if(StringUtils.isNotEmpty(questionRequestDto.getOption1()) ){
            question.setOption1(questionRequestDto.getOption1());
        }else{
            throw  new NullPointerException("option 1 is required");
        }

        if(StringUtils.isNotEmpty(questionRequestDto.getOption2()) ){
            question.setOption2(questionRequestDto.getOption2());
        }else{
            throw  new NullPointerException("option 2 is required");
        }

        if(StringUtils.isNotEmpty(questionRequestDto.getOption3()) ){
            question.setOption3(questionRequestDto.getOption3());
        }else{
            throw  new NullPointerException("option 3 is required");
        }
        if(StringUtils.isNotEmpty(questionRequestDto.getOption4()) ){
            question.setOption4(questionRequestDto.getOption4());
        }else{
            throw  new NullPointerException("option 4 is required");
        }

        if(StringUtils.isNotEmpty(questionRequestDto.getCorrectAnswer()) ){
            question.setCorrectAnswer(questionRequestDto.getCorrectAnswer());
        }else{
            throw  new NullPointerException("correctAnswer is required");
        }

        return  question;
    }
}
