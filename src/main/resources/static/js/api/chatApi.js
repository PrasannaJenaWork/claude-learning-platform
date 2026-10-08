
const CHAT_ENDPOINT = "/api/ai/chat";

export async function sendChatMessage(conversationId, prompt) {
    const response = await fetch(CHAT_ENDPOINT, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            conversationId,
            prompt
        })
    });

    if (!response.ok) {
        throw new Error(`Server returned HTTP ${response.status}`);
    }

    const data = await response.json();

    if (typeof data.answer !== "string") {
        throw new Error("Invalid response from chat API");
    }

    return data.answer;
}
