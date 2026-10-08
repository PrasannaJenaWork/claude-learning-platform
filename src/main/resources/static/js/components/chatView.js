const messagesContainer = document.getElementById("messages");
const sendButton = document.getElementById("send");

function renderMarkdown(element, content) {
    const html = marked.parse(content);
    element.innerHTML = DOMPurify.sanitize(html);
}

export function appendMessage(content, role) {
    const element = document.createElement("div");
    element.className = `message ${role}`;

    if (role === "assistant") {
        renderMarkdown(element, content);
    } else {
        element.textContent = content;
    }

    messagesContainer.appendChild(element);
    scrollToBottom();

    return element;
}

export function updateMessage(element, content, role) {
    element.className = `message ${role}`;

    if (role === "assistant") {
        renderMarkdown(element, content);
    } else {
        element.textContent = content;
    }

    scrollToBottom();
}

export function clearMessages() {
    messagesContainer.replaceChildren();
}

export function setLoading(loading) {
    sendButton.disabled = loading;
    sendButton.textContent = loading ? "Thinking..." : "Send";
}

function scrollToBottom() {
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
}