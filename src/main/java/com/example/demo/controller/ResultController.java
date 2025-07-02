package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.DiagnosisResult;
import com.example.demo.entity.Personality;
import com.example.demo.service.PersonalityService;
import com.example.demo.service.ResultService;

@Controller
public class ResultController {

    private final PersonalityService personalityService;
    private final ResultService resultService;
    
    public ResultController(PersonalityService personalityService, ResultService resultService) {
        this.personalityService = personalityService;
        this.resultService     = resultService;
    }

    @GetMapping("/result")
    public String showResult(Model model) {
        DiagnosisResult chartData = (DiagnosisResult) model.asMap().get("chartData");
        String resultType         = (String)    model.asMap().get("resultType");

        // resultType が null または 空文字 のときにもダミーを入れる
        if (chartData == null || resultType == null || resultType.isBlank()) {
            chartData  = new DiagnosisResult();
            chartData.setOverallEPercent(80.0);
            chartData.setOverallIPercent(20.0);
            chartData.setOverallSPercent(35.0);
            chartData.setOverallNPercent(65.0);
            chartData.setOverallTPercent(51.0);
            chartData.setOverallFPercent(49.0);
            chartData.setOverallJPercent(40.0);
            chartData.setOverallPPercent(60.0);

            resultType = "ENFP";
        }

        // ここで初めてサービス呼び出し
        Personality p = personalityService.getByType(resultType);

        String baseName = p.getImageName();         // 例: "ENFP" または "ENFP.png"
        baseName = baseName.replaceAll("\\.png$", ""); 
        model.addAttribute("imageName", baseName + ".jpg");
        
        model.addAttribute("chartData",   chartData);
        model.addAttribute("resultType",  resultType);
        model.addAttribute("personality", p);
        return "result";
    }


}
