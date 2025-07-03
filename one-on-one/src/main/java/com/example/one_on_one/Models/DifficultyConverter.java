package com.example.one_on_one.Models;

import com.example.one_on_one.Models.Questionsmodels;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DifficultyConverter implements AttributeConverter<Questionsmodels.Difficulty, String> {

    @Override
    public String convertToDatabaseColumn(Questionsmodels.Difficulty difficulty) {
        if (difficulty == null) {
            return null;
        }
        return difficulty.name();
    }

    @Override
    public Questionsmodels.Difficulty convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return Questionsmodels.Difficulty.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unknown value: " + dbData);
        }
    }
}
