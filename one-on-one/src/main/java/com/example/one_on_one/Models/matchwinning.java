package com.example.one_on_one.Models;

import jakarta.persistence.*;

@Entity
@Table(name="matchwinning")
public class matchwinning{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",nullable = false,unique = true)
    private Long id;

    @Column(name="roomCode", nullable = false)
    private String room;


    @ManyToOne
    @JoinColumn(name = "User")
    private UserModels User;

    public String getRoom() {
        return room;
    }

    public void setRoom(String Room) {
        this.room = Room;
    }

    public matchwinning() {}


    public UserModels getUser() {
        return User;
    }

    public void setUser(UserModels user) {
        User = user;
    }


    public int getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(int totalScore) {
        TotalScore = totalScore;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    @Column(name = "totalScore")
    int TotalScore;
    @Column(name = "Winner")
    boolean winner;

}
