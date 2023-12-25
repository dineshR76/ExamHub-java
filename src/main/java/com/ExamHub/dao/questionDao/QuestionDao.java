package com.ExamHub.dao.questionDao;

import com.ExamHub.entity.examEntity.Question;
import com.ExamHub.repository.questionRepository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class QuestionDao {

    @Autowired
    private QuestionRepository questionRepository;

    public Question createQuestion(Question question) {
        return  questionRepository.save(question);
    }

    public Question getQuestionById(Integer questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("questionId with ID " + questionId + " not found"));
    }

    public Page<Question> getAllQuestion(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return questionRepository.findAll(pageable);
    }

    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }
}
