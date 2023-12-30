package com.examHub.service;

import com.examHub.dto.examHubRequestDto.QuestionRequestDto;
import com.examHub.entity.Question;
import org.springframework.data.domain.Page;

public interface QuestionService {
    Question createQuestion(QuestionRequestDto questionRequestDto);

    Question updateQuestion(QuestionRequestDto questionRequestDto);

    Page<Question> getAllQuestion(int page, int size);

    Question getQuestionById(int examId);

    void deleteQuestion(int questionId);
}
