package com.example.one_on_one.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "questions") public class Questionsmodels {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",nullable = false,unique = true) private Long Id;
    @Column(name = "title",nullable = false,length = 255) private String title;
    @Column(name = "description",columnDefinition = "TEXT",nullable = false)
    @Lob
    private String description;
    @Column(name = "constraints",columnDefinition = "TEXT",nullable = false)
    @Lob
    private String constraints;
    @Column(name = "input_format",columnDefinition = "TEXT",nullable = false)
    @Lob
    private String input_format;
    @Column(name = "output_format",columnDefinition = "TEXT",nullable = false)
    @Lob
    private String output_format;
    @Column(name = "testcases", columnDefinition = "TEXT", nullable = false)
    @Lob
    private String testcases;
    @Column(name = "tags",nullable = false,columnDefinition = "TEXT")
    private String tags;
    @Column(name = "difficulty",nullable = false)
    @Convert(converter = DifficultyConverter.class)
    private Difficulty difficulty;
    public enum Difficulty {
        EASY, HARD, MEDIUM, BEGINNER, MIXED
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public String getInput_format() {
        return input_format;
    }

    public void setInput_format(String input_format) {
        this.input_format = input_format;
    }

    public String getOutput_format() {
        return output_format;
    }

    public void setOutput_format(String output_format) {
        this.output_format = output_format;
    }

    public String getTestcases() {
        return testcases;
    }

    public void setTestcases(String testcases) {
        this.testcases = testcases;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Questionsmodels() {
    }

    public Questionsmodels(String title, String description, String constraints, String input_format, String output_format, String testcases, String tags, Difficulty difficulty) {
        this.title = title;
        this.description = description;
        this.constraints = constraints;
        this.input_format = input_format;
        this.output_format = output_format;
        this.testcases = testcases;
        this.tags = tags;
        this.difficulty = difficulty;
    }
}
