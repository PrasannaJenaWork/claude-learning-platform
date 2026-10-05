package com.prasanna.claude.controller;

import com.prasanna.claude.service.AiService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {

        String answer = aiService.chat(request.get("prompt"));

        return Map.of("answer", answer);
    }
}