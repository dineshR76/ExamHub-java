package com.examHub.service;

import com.examHub.dto.examHubRequestDto.UserAnswerRequestDto;
import com.examHub.entity.UserAnswer;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface UserAnswerService {
    UserAnswer saveUserAnswer(UserAnswerRequestDto userAnswerRequestDto) throws JsonProcessingException;
}
