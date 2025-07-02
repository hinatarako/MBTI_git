package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "personalities")
@Data
public class Personality {
    @Id
    private String type;       // "ENFP" など
    private String name;       // "運動家" など
    private String imageName;  // "ENFP.jpg"
    private String description;// 長い説明文
}
