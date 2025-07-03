package com.example.one_on_one.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import com.example.one_on_one.Models.Questionsmodels;
import java.time.LocalDate;

@Entity
@Table(name = "rooms")
public class RoomModels {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",unique = true,nullable = false)
    private Long id;
    @Column(name = "room_code",unique = true,nullable = false)
    private String roomCode;

    @Column(name = "num_questions",nullable = false)private int num_questions;

    public RoomModels() {

    }

    @Column(name = "difficulty",nullable = false)
    @Convert(converter = DifficultyConverter.class)
    private Questionsmodels.Difficulty difficulty;

    public String getRoom_code() {
        return roomCode;
    }

    public void setRoom_code(String room_code) {
        this.roomCode = room_code;
    }

    public int getNum_questions() {
        return num_questions;
    }

    public void setNum_questions(int num_questions) {
        this.num_questions = num_questions;
    }

    public Questionsmodels.Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Questionsmodels.Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public RoomModels.status getStatus() {
        return status;
    }

    public void setStatus(RoomModels.status status) {
        this.status = status;
    }

    public UserModels getHost_user_id() {
        return host_user_id;
    }

    public void setHost_user_id(UserModels host_user_id) {
        this.host_user_id = host_user_id;
    }

    public UserModels getGuest_user_id() {
        return guest_user_id;
    }

    public void setGuest_user_id(UserModels guest_user_id) {
        this.guest_user_id = guest_user_id;
    }

    @Column(name = "timer",nullable = false) private int timer;
    @Column(name = "status",nullable = false)@Enumerated(EnumType.STRING) private status status;
    public enum status {
        WAITING, ONGOING, COMPLETED,READY
    }
    @ManyToOne
    @JoinColumn(name = "host_user_id", nullable = false)private UserModels host_user_id;
    @ManyToOne
    @JoinColumn(name = "guest_user_id") private UserModels guest_user_id;
    @CreationTimestamp
    @Column(name = "createdAt") private LocalDate created_at;
    @PrePersist
    public void auto(){
        this.created_at=LocalDate.now();
    }
}
