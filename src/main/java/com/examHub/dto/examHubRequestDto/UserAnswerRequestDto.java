package com.examHub.dto.examHubRequestDto;

import lombok.Data;

@Data
public class UserAnswerRequestDto {
    private Integer userAnswerId;
    private Integer examId;
    private Integer questionId;
    private String selectedAnswer;
}
