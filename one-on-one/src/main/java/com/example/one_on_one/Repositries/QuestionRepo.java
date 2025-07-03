package com.example.one_on_one.Repositries;

import com.example.one_on_one.Models.Questionsmodels;
import com.example.one_on_one.Models.RoomModels;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepo extends JpaRepository<Questionsmodels, Long> {
    Optional<Questionsmodels> findBytitle(String title);
//    @Query(value = "SELECT * FROM questions WHERE difficulty = :difficulty LIMIT :count", nativeQuery = true)
//    List<Questionsmodels> findQuestionsByDifficultyWithLimit(@Param("difficulty") Questionsmodels.Difficulty difficulty, @Param("count") int count);
    @Query(value = "SELECT * FROM questions WHERE difficulty = :difficulty ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Questionsmodels> findQuestionsByDifficultyWithLimit(@Param("difficulty") Questionsmodels.Difficulty difficulty, @Param("count") int count);

}
