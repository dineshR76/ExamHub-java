package com.ExamHub.ExamHub.dto.examRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequest {
    private String email;
    private String password;
    private String oldPassword;
    private String confirmPassword;

    public PasswordChangeRequest(String email, String password) {
    }
}
