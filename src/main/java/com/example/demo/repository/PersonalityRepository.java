package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Personality;

public interface PersonalityRepository extends JpaRepository<Personality, String> {
    // findById(String type) が使える
}
