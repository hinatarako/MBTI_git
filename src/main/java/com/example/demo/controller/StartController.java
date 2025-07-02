package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    /**
     * ルート (/) または /start にアクセスしたときに start.html を表示
     */
    @GetMapping({"/", "/start"})
    public String showStartPage() {
        return "start";  // templates/start.html をレンダリング
    }
}
