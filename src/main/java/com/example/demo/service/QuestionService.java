package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Question;
import com.example.demo.repository.QuestionRepository;

@Service
public class QuestionService {

    // QuestionRepository を DI（依存性注入）で受け取る
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * 全質問を id の昇順で取得するメソッド。
     * 診断処理や画面表示に利用します。
     *
     * @return 全ての質問のリスト
     */
    public List<Question> getAllQuestionsOrdered() {
        return questionRepository.findAllByOrderByIdAsc();
    }
}