package com.example.one_on_one.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
public class UserModels {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "totalmatch")
    private int totalmatch;

    @Column(name = "wins")
    private int wins;

    @Column(name = "losses")
    private int losses;

    @Column(name = "created_at")
    private LocalDateTime created;

    public UserModels(Long id, String username, String email, String password, int totalmatch, int wins, int losses) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.totalmatch = totalmatch;
        this.wins = wins;
        this.losses = losses;
    }

    public UserModels() {
    }

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTotalmatch() {
        return totalmatch;
    }

    public void setTotalmatch(int totalmatch) {
        this.totalmatch = totalmatch;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}
