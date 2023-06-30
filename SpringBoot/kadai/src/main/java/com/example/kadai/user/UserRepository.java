package com.example.kadai.user;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Optional<User> findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "update trainees set name = ?2, email = ?3, image = ?4 where id = ?1", nativeQuery = true)
    void updateUser(int id, String name, String email, String image);
}