package com.example.one_on_one.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "room_questions")
public class roomQuestionsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "roomCode")
    private String room;
    @ManyToOne
    @JoinColumn(name = "question_id",nullable = false)
    private Questionsmodels questions_id;

    public String getRoom() {
        return room;
    }

    public roomQuestionsModel() {
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Questionsmodels getQuestions_id() {
        return questions_id;
    }

    public void setQuestions_id(Questionsmodels questions_id) {
        this.questions_id = questions_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Column(name = "position",nullable = false)
    private int position;


}
