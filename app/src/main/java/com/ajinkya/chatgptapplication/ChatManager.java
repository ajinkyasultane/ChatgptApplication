package com.ajinkya.chatgptapplication;

import androidx.annotation.NonNull;

import java.util.Locale;

public class ChatManager {

    @NonNull
    public String getBotReply(@NonNull String input) {
        String msg = input.trim().toLowerCase(Locale.US);

        if (msg.contains("hi") || msg.contains("hello")) {
            return "Hello! How can I help you?";
        }
        if (msg.contains("how are you")) {
            return "I am doing great. Thanks for asking!";
        }
        if (msg.contains("your name")) {
            return "I am your assistant.";
        }
        if (msg.contains("help")) {
            return "You can ask me about time, date, app tips, motivation, or basic coding questions.";
        }
        if (msg.contains("time")) {
            return "I am an offline bot, but your device clock can show the current time.";
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
        if (msg.contains("bye")) {
            return "Goodbye!";
        }
        return "Sorry, I didn't understand that.";
    }
}
