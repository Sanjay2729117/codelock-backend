package com.example.one_on_one.Models;

import jakarta.persistence.*;

@Entity
@Table(name="submission")
public class Submissionmodel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",nullable = false,unique = true)
    private Long id;
    @Column(name="roomCode",nullable = false)
    private String RoomCode;
    @ManyToOne
    @JoinColumn(name = "userName", nullable = false)
    private UserModels user;
    @Column(name="testcase passed",nullable = false)
    private int testcasepassed;
    @Column(name="total testcase",nullable = false)
    private int totaltestcase;


    public Submissionmodel() {
    }

    @Column(name="score",nullable = false)
    private int score;

    public String getRoomCode() {
        return RoomCode;
    }

    public void setRoomCode(String roomCode) {
        RoomCode = roomCode;
    }

    public UserModels getUser() {
        return user;
    }

    public void setUser(UserModels user) {
        this.user = user;
    }

    public int getTestcasepassed() {
        return testcasepassed;
    }

    public void setTestcasepassed(int testcasepassed) {
        this.testcasepassed = testcasepassed;
    }

    public int getTotaltestcase() {
        return totaltestcase;
    }

    public void setTotaltestcase(int totaltestcase) {
        this.totaltestcase = totaltestcase;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
