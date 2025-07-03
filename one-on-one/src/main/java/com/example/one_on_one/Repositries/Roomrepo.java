package com.example.one_on_one.Repositries;

import com.example.one_on_one.Models.RoomModels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Roomrepo extends JpaRepository<RoomModels, Long> {

    Optional<RoomModels> findByRoomCode(String roomCode);

    boolean existsByRoomCode(String room_code);
}
