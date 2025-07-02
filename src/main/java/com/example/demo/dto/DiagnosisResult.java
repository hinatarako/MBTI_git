package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor         // ← 引数なしコンストラクタを生成
@AllArgsConstructor
public class DiagnosisResult {
    private String resultType; // 例："ENFP"
    private Double overallEPercent;
    private Double overallIPercent;
    private Double overallSPercent;
    private Double overallNPercent;
    private Double overallTPercent;
    private Double overallFPercent;
    private Double overallJPercent;
    private Double overallPPercent;
    
    // getters/setters およびコンストラクタ

    //コンストラクタ
    public DiagnosisResult(String resultType, double overallEPercent, double overallIPercent,
                           double overallSPercent, double overallNPercent,
                           double overallTPercent, double overallFPercent,
                           double overallJPercent, double overallPPercent) {
        this.resultType = resultType;
        this.overallEPercent = overallEPercent;
        this.overallIPercent = overallIPercent;
        this.overallSPercent = overallSPercent;
        this.overallNPercent = overallNPercent;
        this.overallTPercent = overallTPercent;
        this.overallFPercent = overallFPercent;
        this.overallJPercent = overallJPercent;
        this.overallPPercent = overallPPercent;
    }
}