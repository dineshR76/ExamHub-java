package com.ExamHum.ExamHum.dto.examRequestDto;

import lombok.Data;

@Data
public class ExamRequestDto {

        private int examId;
        private String title;
        private String startTime;
        private String endTime;
}
