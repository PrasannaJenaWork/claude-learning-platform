package com.prasanna.claude.model;

public record ChatRequest(
        String conversationId,
        String prompt
) {}