package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.example.demo.dto.DiagnosisResult;
import com.example.demo.entity.Question;

@Service
public class DiagnosisService {

    // QuestionService をインジェクトして、質問リストを取得できるようにする
    private final QuestionService questionService;

    public DiagnosisService(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * フォームデータ（各質問の回答）の情報とDB上の質問情報から診断結果を計算し、
     * DiagnosisResult DTO として返すメソッド
     *
     * @param formData ブラウザから送信されたフォームデータ（キー：q0, q1, ...）
     * @return 診断結果を格納した DiagnosisResult DTO
     */
    public DiagnosisResult diagnose(MultiValueMap<String, String> formData) {
        // DBから全質問を取得（ソート済み）
        List<Question> questions = questionService.getAllQuestionsOrdered();
        int totalQuestions = questions.size();

        // 各軸の累積スコアとカウントの初期化
        double totalE = 0.0, totalI = 0.0;
        double totalS = 0.0, totalN = 0.0;
        double totalT = 0.0, totalF = 0.0;
        double totalJ = 0.0, totalP = 0.0;
        int countE_I = 0, countS_N = 0, countT_F = 0, countJ_P = 0;

        // 各質問について、フォームデータから回答値を取得し、計算していく
        for (int i = 0; i < totalQuestions; i++) {
            // DB上の質問オブジェクトを取得
            Question q = questions.get(i);
            // フォームの hidden input により送られた値は "q0", "q1", ... として送信される
            String answerStr = formData.getFirst("q" + i);
            
            int score = Integer.parseInt(answerStr); // 1～5 のスコア
            // スコアを 0～1 の範囲に変換（score 1 → 0.0, score 5 → 1.0）
            double x = (score - 1) / 4.0;

            // DB上の質問の personalityType を取得し、集計する
            char pType = q.getPersonalityType();
            switch (pType) {
                case 'E':
                    totalE += x;
                    totalI += (1 - x);
                    countE_I++;
                    break;
                case 'I':
                    totalI += x;
                    totalE += (1 - x);
                    countE_I++;
                    break;
                case 'S':
                    totalS += x;
                    totalN += (1 - x);
                    countS_N++;
                    break;
                case 'N':
                    totalN += x;
                    totalS += (1 - x);
                    countS_N++;
                    break;
                case 'T':
                    totalT += x;
                    totalF += (1 - x);
                    countT_F++;
                    break;
                case 'F':
                    totalF += x;
                    totalT += (1 - x);
                    countT_F++;
                    break;
                case 'J':
                    totalJ += x;
                    totalP += (1 - x);
                    countJ_P++;
                    break;
                case 'P':
                    totalP += x;
                    totalJ += (1 - x);
                    countJ_P++;
                    break;
                default:
                    // 想定外の personalityType があった場合は、特に何もしない
                    break;
            }
        }

        // パーセンテージの計算（各軸は count で割って100倍する）
        double overallEPercent = (countE_I > 0) ? ((totalE / countE_I) * 100) : 0;
        double overallIPercent = (countE_I > 0) ? ((totalI / countE_I) * 100) : 0;
        double overallSPercent = (countS_N > 0) ? ((totalS / countS_N) * 100) : 0;
        double overallNPercent = (countS_N > 0) ? ((totalN / countS_N) * 100) : 0;
        double overallTPercent = (countT_F > 0) ? ((totalT / countT_F) * 100) : 0;
        double overallFPercent = (countT_F > 0) ? ((totalF / countT_F) * 100) : 0;
        double overallJPercent = (countJ_P > 0) ? ((totalJ / countJ_P) * 100) : 0;
        double overallPPercent = (countJ_P > 0) ? ((totalP / countJ_P) * 100) : 0;

        // 各軸で優勢な方のタイプを組み合わせて MBTI タイプを決定
        String resultType = "";
        resultType += (overallEPercent >= overallIPercent ? "E" : "I");
        resultType += (overallSPercent >= overallNPercent ? "S" : "N");
        resultType += (overallTPercent >= overallFPercent ? "T" : "F");
        resultType += (overallJPercent >= overallPPercent ? "J" : "P");

        // DiagnosisResult オブジェクトとして、結果の MBTI タイプと各軸のパーセンテージを返す
        return new DiagnosisResult(resultType,
                overallEPercent, overallIPercent,
                overallSPercent, overallNPercent,
                overallTPercent, overallFPercent,
                overallJPercent, overallPPercent);
    }
}