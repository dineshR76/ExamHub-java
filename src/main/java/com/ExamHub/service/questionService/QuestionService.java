package com.ExamHub.service.questionService;

import com.ExamHub.dto.questionRequestDto.QuestionRequestDto;
import com.ExamHub.entity.examEntity.Question;
import org.springframework.data.domain.Page;

public interface QuestionService {
    Question createQuestion(QuestionRequestDto questionRequestDto);

    Question updateQuestion(QuestionRequestDto questionRequestDto);

    Page<Question> getAllQuestion(int page, int size);

    Question getQuestionById(int examId);

    void deleteQuestion(int questionId);
}
