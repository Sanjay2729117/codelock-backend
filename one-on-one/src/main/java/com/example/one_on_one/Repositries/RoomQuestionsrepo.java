package com.example.one_on_one.Repositries;

import com.example.one_on_one.Models.RoomModels;
import com.example.one_on_one.Models.roomQuestionsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomQuestionsrepo extends JpaRepository<roomQuestionsModel, Long> {
    List<roomQuestionsModel> findByRoom(String room);

}

