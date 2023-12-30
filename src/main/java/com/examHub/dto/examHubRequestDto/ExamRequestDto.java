package com.examHub.dto.examHubRequestDto;

import lombok.Data;


@Data
public class ExamRequestDto {

        private Integer examId;
        private String title;
        private String startTime;
        private String endTime;
}
