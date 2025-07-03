package com.example.one_on_one.Repositries;

import com.example.one_on_one.Models.UserModels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepo  extends JpaRepository<UserModels, Long> {
    Optional<UserModels> findByemail(String email);
    Optional<UserModels> findByUsername(String username);
}
