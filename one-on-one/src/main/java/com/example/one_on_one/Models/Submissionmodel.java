package com.example.one_on_one.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "submission")
public class Submissionmodel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "room_code", nullable = false)
    private String roomCode;

    @ManyToOne
    @JoinColumn(name = "user_name", nullable = false)  // if your DB column is user_name referencing UserModels.id or username
    private UserModels user;

    @Column(name="testcase_passed", nullable = false)
    private int testcasePassed;

    @Column(name = "total_testcase", nullable = false)
    private int totalTestcase;

    @Column(name = "score", nullable = false)
    private int score;

    // Constructors
    public Submissionmodel() {}

    // Getters and setters

    public Long getId() {
        return id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public UserModels getUser() {
        return user;
    }

    public void setUser(UserModels user) {
        this.user = user;
    }

    public int getTestcasePassed() {
        return testcasePassed;
    }

    public void setTestcasePassed(int testcasePassed) {
        this.testcasePassed = testcasePassed;
    }

    public int getTotalTestcase() {
        return totalTestcase;
    }

    public void setTotalTestcase(int totalTestcase) {
        this.totalTestcase = totalTestcase;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
