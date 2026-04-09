package com.ajinkya.chatgptapplication;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatManager {

    private String rememberedName = "";
    private String rememberedPreference = "";
    private String lastUserMessage = "";

    @NonNull
    public String getBotReply(@NonNull String input, @NonNull List<Message> history) {
        String msg = input.trim().toLowerCase(Locale.US);
        updateLongTermMemory(msg);

        if (msg.isEmpty()) {
            return "Please type a message.";
        }

        if (msg.contains("hi") || msg.contains("hello")) {
            return greetingReply();
        }
        if (msg.contains("how are you")) {
            return "I am doing great. Thanks for asking!";
        }
        if (msg.contains("your name")) {
            return "I am your assistant.";
        }
        if (msg.contains("my name is")) {
            return rememberedName.isEmpty()
                    ? "Nice to meet you!"
                    : "Nice to meet you, " + rememberedName + ". I will remember your name.";
        }
        if (msg.contains("what is my name") || msg.contains("who am i")) {
            return rememberedName.isEmpty()
                    ? "You have not told me your name yet."
                    : "Your name is " + rememberedName + ".";
        }
        if (msg.contains("remember this")) {
            rememberedPreference = input.replaceFirst("(?i)remember this", "").trim();
            return rememberedPreference.isEmpty()
                    ? "Tell me what I should remember."
                    : "Got it. I will remember: " + rememberedPreference;
        }
        if (msg.contains("what did i tell you") || msg.contains("what do you remember")) {
            if (!rememberedPreference.isEmpty()) {
                return "You asked me to remember: " + rememberedPreference;
            }
            if (!rememberedName.isEmpty()) {
                return "I remember your name is " + rememberedName + ".";
            }
            return "I do not have much saved yet. Tell me your name or say 'remember this ...'.";
        }
        if (msg.contains("what did i just say") || msg.contains("repeat my last message")) {
            String fromHistory = extractPreviousUserMessage(history);
            return fromHistory.isEmpty()
                    ? "This is your first message right now."
                    : "You just said: " + fromHistory;
        }
        if (msg.contains("help")) {
            return "Try asking: your name, my name is ..., what is my name, remember this ..., what did I just say, a math expression like 12+8, or coding questions.";
        }
        if (msg.contains("time")) {
            return "I cannot read live device time here, but your phone clock will show it.";
        }
        if (msg.contains("date")) {
            return "I cannot read live date here, but I can help with planning your day.";
        }
        if (msg.contains("thanks") || msg.contains("thank you")) {
            return "You are welcome!";
        }
        if (msg.contains("who made you")) {
            return "I am built into your Android app as an offline chatbot.";
        }
        if (msg.contains("joke")) {
            return "Why do programmers prefer dark mode? Because light attracts bugs.";
        }
        if (msg.contains("weather")) {
            return "I am offline, so I cannot fetch live weather. I can suggest what to check in a forecast app.";
        }
        if (msg.contains("android")) {
            return "Android tip: keep logic outside Activity, use RecyclerView for lists, and use ViewModel for state.";
        }
        if (msg.contains("java")) {
            return "Java is object-oriented and great for Android app logic, models, and adapters.";
        }
        if (msg.contains("kotlin")) {
            return "Kotlin is concise and null-safe. It works very well with modern Android APIs.";
        }
        if (msg.contains("motivate") || msg.contains("motivation")) {
            return "Small progress every day beats perfect plans. Keep going.";
        }
        if (msg.contains("bye")) {
            return "Goodbye!";
        }

        String mathReply = trySolveMath(msg);
        if (!mathReply.isEmpty()) {
            return mathReply;
        }

        if (msg.endsWith("?")) {
            return "Good question. I am offline, but I can still help with app development, Java/Kotlin basics, and your previous context.";
        }

        lastUserMessage = input.trim();
        return "Sorry, I didn't understand that.";
    }

    @NonNull
    private String greetingReply() {
        if (!rememberedName.isEmpty()) {
            return "Hello " + rememberedName + "! How can I help you today?";
        }
        return "Hello! How can I help you?";
    }

    private void updateLongTermMemory(@NonNull String msg) {
        // Capture "my name is <name>"
        Matcher nameMatcher = Pattern.compile("\\bmy name is\\s+([a-zA-Z]+)\\b").matcher(msg);
        if (nameMatcher.find()) {
            rememberedName = capitalize(nameMatcher.group(1));
        }
    }

    @NonNull
    private String extractPreviousUserMessage(@NonNull List<Message> history) {
        for (int i = history.size() - 1; i >= 0; i--) {
            Message m = history.get(i);
            if (m.isUser()) {
                String t = m.getText() == null ? "" : m.getText().trim();
                if (!t.isEmpty() && !t.equalsIgnoreCase(lastUserMessage)) {
                    return t;
                }
            }
        }
        return "";
    }

    @NonNull
    private String trySolveMath(@NonNull String msg) {
        Matcher math = Pattern.compile("^\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*$").matcher(msg);
        if (!math.find()) {
            return "";
        }
        double a = Double.parseDouble(math.group(1));
        double b = Double.parseDouble(math.group(3));
        String op = math.group(2);
        if ("/".equals(op) && b == 0d) {
            return "Cannot divide by zero.";
        }
        double out;
        switch (op) {
            case "+":
                out = a + b;
                break;
            case "-":
                out = a - b;
                break;
            case "*":
                out = a * b;
                break;
            default:
                out = a / b;
                break;
        }
        if (out == Math.rint(out)) {
            return "Answer: " + (long) out;
        }
        return "Answer: " + out;
    }

    @NonNull
    private String capitalize(@NonNull String s) {
        if (s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase(Locale.US) + s.substring(1).toLowerCase(Locale.US);
    }
}
