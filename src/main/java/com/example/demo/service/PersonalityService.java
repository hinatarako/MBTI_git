package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Personality;
import com.example.demo.repository.PersonalityRepository;

@Service
public class PersonalityService {
    private final PersonalityRepository repo;
    public PersonalityService(PersonalityRepository repo) {
        this.repo = repo;
    }
    public Personality getByType(String type) {
        return repo.findById(type).orElseThrow(() ->
            new IllegalArgumentException("Unknown type: " + type));
    }
}
