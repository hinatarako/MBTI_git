package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DBの "results" テーブルに対応するエンティティ。
 * 診断結果（MBTI タイプなど）を保存します。
 */
@Entity
@Table(name = "results")
@Data               // Lombok @Data で、getter/setter、toString() 等を自動生成
@NoArgsConstructor  // デフォルトコンストラクタを自動生成
public class Result {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 診断結果の文字列（例："ENFP" など）
    private String resultText;
}