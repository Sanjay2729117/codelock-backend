package com.example.one_on_one.Repositries;
import com.example.one_on_one.Models.UserModels;
import com.example.one_on_one.Models.roomQuestionsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.one_on_one.Models.matchwinning;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface matchwinningrepo extends JpaRepository<matchwinning, Long> {
    List<matchwinning> findByRoom(String room);
    boolean existsByRoom(String room);
}
