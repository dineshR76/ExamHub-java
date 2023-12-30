package com.examHub.ExamHub.dto.examRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailRequest {

    private String to;

    private String subject;

    private String message;
}
