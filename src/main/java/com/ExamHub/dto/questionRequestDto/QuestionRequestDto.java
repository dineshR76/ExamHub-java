package com.ExamHub.dto.questionRequestDto;

import lombok.Data;

@Data
public class QuestionRequestDto {

    private  Integer questionId;
    private Integer examId;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;
}
