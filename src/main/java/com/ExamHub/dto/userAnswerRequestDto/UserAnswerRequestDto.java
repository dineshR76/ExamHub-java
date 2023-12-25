package com.ExamHub.dto.userAnswerRequestDto;

import lombok.Data;

@Data
public class UserAnswerRequestDto {
    private Integer userAnswerId;
    private Integer examId;
    private Integer questionId;
    private String selectedAnswer;
}
