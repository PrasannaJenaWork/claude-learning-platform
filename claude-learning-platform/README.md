# Claude Learning Platform

A hands-on AI architecture learning project built with **Java, Spring Boot, Ollama, and local LLMs**.

The goal of this project is to learn modern AI application architecture by implementing the concepts step by step rather than relying immediately on high-level AI frameworks.

## Current Architecture

```text
Client
  |
  | HTTP
  v
Spring Boot
  |
  v
AiController
  |
  v
AiService
  |
  |-- System Prompt
  |-- Conversation History
  |-- Ollama Configuration
  |
  v
Ollama
  |
  v
Qwen3:8b
  |
  v
Local Apple Silicon GPU
```

## Technology Stack

- Java 25
- Spring Boot
- Maven
- Spring `RestClient`
- Ollama
- Qwen3:8b
- Docker
- Git / GitHub

## Implemented Features

### REST API

Spring Boot exposes an AI chat endpoint:

```text
POST /api/ai/chat
```

Example:

```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"prompt":"Explain Kubernetes in one sentence."}'
```

### Local LLM Integration

The application communicates directly with the Ollama REST API.

No paid AI API or API key is required.

Current model:

```text
qwen3:8b
```

### System Prompt

The application supplies a system prompt that instructs the model to behave as a cloud and software architecture assistant.

### Conversation Memory

Previous user and assistant messages are maintained in application memory and supplied to the model with subsequent requests.

This allows follow-up questions to use previous conversational context.

> The current implementation uses simple application-level in-memory conversation state. Conversation isolation and production-grade persistence will be added in later iterations.

### External Configuration

Ollama configuration is kept outside the service implementation.

Example:

```properties
ollama.base-url=http://localhost:11434
ollama.model=qwen3:8b
```

## Running Locally

### Prerequisites

- Java 25
- Maven or Maven Wrapper
- Ollama

Verify Ollama:

```bash
ollama --version
```

Pull the model:

```bash
ollama pull qwen3:8b
```

Verify that Ollama is available:

```bash
curl http://localhost:11434/api/tags
```

### Start the Application

Using the Maven Wrapper:

```bash
./mvnw spring-boot:run
```

The application starts on:

```text
http://localhost:8080
```

### Test the Health Endpoint

```bash
curl http://localhost:8080/health
```

### Test AI Chat

```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"prompt":"What is Kubernetes?"}'
```

Then send a follow-up request to test conversational context:

```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"prompt":"What are its main advantages?"}'
```

## Docker

Build the application:

```bash
./mvnw clean package
```

Build the Docker image:

```bash
docker build -t claude-learning-platform:1.0 .
```

Run the container:

```bash
docker run --name claude-learning \
  -p 8080:8080 \
  claude-learning-platform:1.0
```

> When the application itself runs inside Docker, `localhost` refers to the container. Connecting from the container to Ollama running on the macOS host therefore requires different Ollama host configuration.

## Learning Roadmap

Planned checkpoints include:

- [x] Spring Boot REST API
- [x] Docker containerization
- [x] Local Ollama integration
- [x] Typed Ollama request/response models
- [x] Externalized model configuration
- [x] System prompts
- [x] Basic conversational memory
- [ ] Conversation isolation
- [ ] Context-window management
- [ ] Structured LLM output
- [ ] Error handling and resilience
- [ ] Streaming responses
- [ ] Tool / function calling
- [ ] Embeddings
- [ ] Retrieval-Augmented Generation (RAG)
- [ ] Vector database integration
- [ ] MCP
- [ ] Agent workflows
- [ ] Guardrails and evaluation
- [ ] AI observability
- [ ] Spring AI integration
- [ ] Kubernetes deployment

## Learning Philosophy

The project intentionally starts with direct HTTP communication with the model instead of immediately introducing an AI framework.

This makes the underlying concepts visible:

```text
Java objects
    ↓
JSON serialization
    ↓
HTTP
    ↓
Ollama
    ↓
LLM
    ↓
JSON response
    ↓
Java objects
```

Higher-level frameworks such as Spring AI can then be introduced later and compared against the lower-level implementation.

## Status

**Checkpoint 4A — Basic Conversational AI**

The application currently supports local AI chat with system instructions and in-memory conversational context.