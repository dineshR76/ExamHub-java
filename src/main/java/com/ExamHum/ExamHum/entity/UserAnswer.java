package com.ExamHum.ExamHum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_answer")
class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_answer_id")
    private int userAnswerId;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam userExamId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question questionId;

    @Column(name = "selected_answer")
    private String selectedAnswer;
}