package com.ExamHub.ExamHub.model;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Message{

    private HttpStatus status;
    private String message;
    private String type;


}
