package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Question;

// Question エンティティ用のリポジトリ。
// JpaRepository を継承することで、基本的なCRUD操作が自動で提供されます。
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    // 質問を id の昇順で全件取得するメソッド
    List<Question> findAllByOrderByIdAsc();
}