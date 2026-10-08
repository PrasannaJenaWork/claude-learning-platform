package com.prasanna.claude.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OllamaChatResponse(

        OllamaMessage message,

        @JsonProperty("prompt_eval_count")
        Integer promptEvalCount,

        @JsonProperty("eval_count")
        Integer evalCount

) {}