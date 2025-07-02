package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// 質問情報を表すエンティティ
@Entity
@Table(name = "questions")
public class Question {

    // 主キー。このフィールドが自動的に生成される設定です
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 問題文（質問テキスト）
    private String questionText;

    // 質問に紐づく性格タイプ（例えば 'E', 'I', 'S', 'N', 'T', 'F', 'J', 'P'）
    private char personalityType;

    // デフォルトコンストラクタ（JPA 必須）
    public Question() {}

    // コンストラクタ
    public Question(String questionText, char personalityType) {
        this.questionText = questionText;
        this.personalityType = personalityType;
    }

    // Getter と Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public char getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(char personalityType) {
        this.personalityType = personalityType;
    }
}