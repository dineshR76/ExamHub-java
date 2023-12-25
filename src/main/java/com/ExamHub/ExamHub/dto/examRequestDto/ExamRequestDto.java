package com.ExamHub.ExamHub.dto.examRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class ExamRequestDto {

        private Integer examId;
        private String title;
        private String startTime;
        private String endTime;
}
