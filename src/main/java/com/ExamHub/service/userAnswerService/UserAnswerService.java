package com.ExamHub.service.userAnswerService;

import com.ExamHub.dto.userAnswerRequestDto.UserAnswerRequestDto;
import com.ExamHub.entity.examEntity.UserAnswer;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface UserAnswerService {
    UserAnswer saveUserAnswer(UserAnswerRequestDto userAnswerRequestDto) throws JsonProcessingException;
}
