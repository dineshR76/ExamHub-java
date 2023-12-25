package com.ExamHub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResultList<T>  {
    private List<T> results;
    private Pageable page;
    private Integer numberOfPages;
    private Long numberOfRecords;
    private Message message;

    public ResultList(List<T> results, Message message) {
        this.results = results;
        this.message = message;
    }
}
