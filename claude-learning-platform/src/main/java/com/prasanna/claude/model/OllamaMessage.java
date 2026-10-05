package com.prasanna.claude.model;

public record OllamaMessage(
        String role,
        String content
) {}