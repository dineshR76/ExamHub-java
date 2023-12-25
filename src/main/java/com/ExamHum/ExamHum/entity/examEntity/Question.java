package com.ExamHum.ExamHum.entity.examEntity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int questionId;


    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam examId;

    @Column(name = "question_test")
    private String questionText;

    @Column(name = "option_1")
    private String option1;

    @Column(name = "option_2")
    private String option2;

    @Column(name = "option_3")
    private String option3;

    @Column(name = "option_4")
    private String option4;

    @Column(name = "correct_answer")
    private String correctAnswer;
}




