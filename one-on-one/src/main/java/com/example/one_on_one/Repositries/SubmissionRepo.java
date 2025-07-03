package com.example.one_on_one.Repositries;

import com.example.one_on_one.Models.Submissionmodel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubmissionRepo extends JpaRepository<Submissionmodel,Long> {
    @Query(value = "SELECT COALESCE(SUM(score), 0) FROM submission WHERE room_code = :roomCode AND user_name = :userName", nativeQuery = true)
    Integer getTotalScoreByUserAndRoom(@Param("roomCode") String roomCode, @Param("userName") String userName);

}
