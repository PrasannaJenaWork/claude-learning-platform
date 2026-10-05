package com.prasanna.claude.controller;

import com.prasanna.claude.model.ChatRequest;
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
    public Map<String, String> chat(@RequestBody ChatRequest request) {

        String answer = aiService.chat(
                request.conversationId(),
                request.prompt()
        );

        return Map.of("answer", answer);
    }
}