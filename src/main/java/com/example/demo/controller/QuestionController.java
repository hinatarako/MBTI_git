package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.DiagnosisResult;
import com.example.demo.entity.Question;
import com.example.demo.service.DiagnosisService;
import com.example.demo.service.PersonalityService;
import com.example.demo.service.QuestionService;
import com.example.demo.service.ResultService;

@Controller
public class QuestionController {

    // サービス層の各コンポーネントをDI（依存性注入）で受け取る
    private final QuestionService questionService;
    private final DiagnosisService diagnosisService;
    private final ResultService resultService;
    private final PersonalityService personalityService;
    
    // コンストラクタで依存性を注入
    public QuestionController(QuestionService questionService,
                              DiagnosisService diagnosisService,
                              ResultService resultService, PersonalityService personalityService) {
        this.questionService = questionService;
        this.diagnosisService = diagnosisService;
        this.resultService = resultService;
        this.personalityService = personalityService;
    }

    // GETリクエストで /question にアクセスすると質問リストを取得して画面に表示
    @GetMapping("/question")
    public String showQuestionPage(Model model) {
        List<Question> questions = questionService.getAllQuestionsOrdered();

        // 25問ごとに分割する
        List<List<Question>> pages = new ArrayList<>();
        int pageSize = 25;
        for (int i = 0; i < questions.size(); i += pageSize) {
            int end = Math.min(i + pageSize, questions.size());
            pages.add(questions.subList(i, end));
        }

        model.addAttribute("pages", pages);  // 分割済み質問リストを渡す
        return "question";
    }


    // POSTリクエストで送信された回答フォームをもとに診断結果を生成し、結果保存後に結果画面へ遷移
 // QuestionController.java
    @PostMapping("/result")
    public String processDiagnosis(
            @RequestParam MultiValueMap<String, String> formData,  // ← ここに @RequestParam を追加
            RedirectAttributes redirectAttributes) {

        // 診断ロジック
        DiagnosisResult dr = diagnosisService.diagnose(formData);
        resultService.saveResult(dr.getResultType());

        // FlashAttribute に載せて GET /result へリダイレクト
        redirectAttributes.addFlashAttribute("chartData", dr);
        redirectAttributes.addFlashAttribute("resultType", dr.getResultType());
        return "redirect:/result";
    }


}