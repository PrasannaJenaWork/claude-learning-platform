
let conversationId = crypto.randomUUID();
let messages = [];

export function getConversationId() {
    return conversationId;
}

export function getMessages() {
    return [...messages];
}

export function addToHistory(role, content) {
    messages.push({ role, content });
}

export function resetConversation() {
    conversationId = crypto.randomUUID();
    messages = [];
}
