package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Result;
import com.example.demo.repository.ResultRepository;

/**
 * 診断結果の保存や取得のためのサービスクラス。
 */
@Service
public class ResultService {

    private final ResultRepository resultRepository;

    // コンストラクタインジェクション（DI）で repository を受け取ります
    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    /**
     * 指定された診断結果文字列を DB に保存するメソッド
     *
     * @param resultText 保存する診断結果の文字列（例："ENFP"）
     */
    public void saveResult(String resultText) {
        Result result = new Result();
        result.setResultText(resultText);
        resultRepository.save(result);  // DB に保存
    }
}