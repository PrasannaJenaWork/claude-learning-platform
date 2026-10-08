
import { sendChatMessage } from "./api/chatApi.js";

import {
    getConversationId,
    addToHistory,
    resetConversation
} from "./state/conversationStore.js";

import {
    appendMessage,
    updateMessage,
    clearMessages,
    setLoading
} from "./components/chatView.js";

const form = document.getElementById("chatForm");
const promptInput = document.getElementById("prompt");
const newChatButton = document.getElementById("newChat");

let busy = false;

appendMessage(
    "Hello! I'm your local AI assistant.",
    "assistant"
);

form.addEventListener("submit", async event => {
    event.preventDefault();

    if (busy) return;

    const prompt = promptInput.value.trim();
    if (!prompt) return;

    busy = true;
    setLoading(true);
    newChatButton.disabled = true;
    promptInput.value = "";

    appendMessage(prompt, "user");
    addToHistory("user", prompt);

    const pending = appendMessage("Thinking...", "assistant");

    try {
        const answer = await sendChatMessage(
            getConversationId(),
            prompt
        );

        updateMessage(pending, answer, "assistant");
        addToHistory("assistant", answer);

    } catch (error) {
        updateMessage(
            pending,
            `Error: ${error.message}`,
            "error"
        );
    } finally {
        busy = false;
        setLoading(false);
        newChatButton.disabled = false;
        promptInput.focus();
    }
});

newChatButton.addEventListener("click", () => {
    if (busy) return;

    resetConversation();
    clearMessages();

    appendMessage(
        "New conversation started.",
        "assistant"
    );

    promptInput.focus();
});

promptInput.addEventListener("keydown", event => {
    if (event.key === "Enter" &&
        !event.shiftKey &&
        !event.isComposing) {

        event.preventDefault();
        form.requestSubmit();
    }
});
