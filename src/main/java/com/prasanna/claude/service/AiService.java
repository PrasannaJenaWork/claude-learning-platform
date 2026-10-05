package com.prasanna.claude.service;

import com.prasanna.claude.config.OllamaProperties;
import com.prasanna.claude.model.OllamaChatRequest;
import com.prasanna.claude.model.OllamaChatResponse;
import com.prasanna.claude.model.OllamaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AiService {

    private final RestClient restClient;
    private final OllamaProperties properties;
    private static final Logger log =
            LoggerFactory.getLogger(AiService.class);
    private final List<OllamaMessage> conversation = new ArrayList<>();

    public AiService(OllamaProperties ollamaProperties) {
        this.properties = ollamaProperties;
        this.restClient = RestClient.builder()
                .baseUrl(ollamaProperties.baseUrl())
                .build();

        conversation.add(
                new OllamaMessage(
                        "system",
                        """
                        You are a senior cloud and software architecture assistant.
                        Give technically accurate and concise answers.
                        When explaining architecture decisions, explain the trade-offs.
                        """
                )
        );
    }

    public String chat(String prompt) {
        conversation.add(
                new OllamaMessage("user", prompt)
        );

        var request = new OllamaChatRequest(
                properties.model(),
                List.copyOf(conversation),
                false
        );

        log.info("Sending request to Ollama: model={}, messages={}",
                properties.model(),
                request.messages());

        var response = restClient.post()
                .uri("/api/chat")
                .body(request)
                .retrieve()
                .body(OllamaChatResponse.class);

        if (response == null || response.message() == null) {
            throw new IllegalStateException(
                    "No response received from Ollama"
            );
        }

        conversation.add(
                new OllamaMessage(
                        "assistant",
                        response.message().content()
                )
        );

        return response.message().content();
    }
}