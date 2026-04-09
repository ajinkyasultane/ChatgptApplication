package com.ajinkya.chatgptapplication.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.ajinkya.chatgptapplication.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatRepository {

    private static final int MAX_UI_MESSAGES = 50;
    private static final int MAX_HISTORY_FOR_BOT = 10;

    private static final String PREFS_NAME = "chat_local_store";
    private static final String KEY_PREFIX = "messages_";
    private static final String LOCAL_UID = "guest";

    private final SharedPreferences prefs;
    private final FirebaseAuth auth;

    public ChatRepository(@NonNull Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.auth = FirebaseAuth.getInstance();
    }

    @NonNull
    public List<Message> loadRecentMessagesForUi() throws Exception {
        return readMessagesForUi(getSessionUid());
    }

    @NonNull
    public String sendUserMessageAndGetAssistantReply(@NonNull String userText) throws Exception {
        String uid = getSessionUid();
        appendStoredMessage(uid, "user", userText);
        JSONArray history = buildLastHistoryForBot(uid);
        String assistantText = generateLocalReply(userText, history);
        appendStoredMessage(uid, "assistant", assistantText);
        return assistantText;
    }

    @NonNull
    private List<Message> readMessagesForUi(@NonNull String uid) throws JSONException {
        JSONArray stored = readStoredArray(uid);
        int start = Math.max(0, stored.length() - MAX_UI_MESSAGES);
        List<Message> out = new ArrayList<>();
        for (int i = start; i < stored.length(); i++) {
            JSONObject item = stored.optJSONObject(i);
            if (item == null) {
                continue;
            }
            String role = item.optString("role", "");
            String text = item.optString("text", "");
            out.add(new Message(text, "user".equals(role) ? Message.SENT_BY_ME : Message.SENT_BY_BOT));
        }
        return out;
    }

    @NonNull
    private JSONArray buildLastHistoryForBot(@NonNull String uid) throws JSONException {
        JSONArray stored = readStoredArray(uid);
        int start = Math.max(0, stored.length() - MAX_HISTORY_FOR_BOT);
        JSONArray history = new JSONArray();
        for (int i = start; i < stored.length(); i++) {
            JSONObject item = stored.optJSONObject(i);
            if (item == null) {
                continue;
            }
            String role = item.optString("role", "");
            if (!"user".equals(role) && !"assistant".equals(role)) {
                continue;
            }
            JSONObject turn = new JSONObject();
            turn.put("role", role);
            turn.put("content", item.optString("text", ""));
            history.put(turn);
        }
        return history;
    }

    @NonNull
    private String generateLocalReply(@NonNull String userText, @NonNull JSONArray history) {
        String input = userText.trim();
        String lower = input.toLowerCase(Locale.US);
        if (lower.isEmpty()) {
            return "Please type a message and I will help.";
        }
        if (lower.contains("hello") || lower.contains("hi") || lower.contains("hey")) {
            return "Hi! I am your offline chatbot. Ask me anything and I will reply instantly.";
        }
        if (lower.contains("name")) {
            return "I am your local chatbot, running fully inside your app without API limits.";
        }
        if (lower.contains("time")) {
            return "I cannot read device time here, but I can still help you with your questions.";
        }
        if (lower.contains("help")) {
            return "Try asking: summarize text, improve sentence, create to-do list, or explain a topic.";
        }
        if (lower.startsWith("summarize ")) {
            String body = input.substring("summarize ".length()).trim();
            if (body.isEmpty()) {
                return "Send text after 'summarize' and I will make it short.";
            }
            return "Summary: " + shorten(body, 120);
        }
        if (lower.startsWith("improve ")) {
            String body = input.substring("improve ".length()).trim();
            if (body.isEmpty()) {
                return "Send a sentence after 'improve' and I will refine it.";
            }
            return "Improved: " + improveSentence(body);
        }
        if (input.endsWith("?")) {
            return "Good question. My local answer: " + fallbackKnowledge(lower);
        }
        String contextual = history.length() > 2
                ? "I remember our last few messages. "
                : "";
        return contextual + "You said: \"" + input + "\". I can help rewrite, summarize, or explain.";
    }

    @NonNull
    private String fallbackKnowledge(@NonNull String lower) {
        if (lower.contains("java")) {
            return "Java is an object-oriented language used heavily in Android and backend systems.";
        }
        if (lower.contains("android")) {
            return "Android apps use Activities, ViewModels, and layouts to build responsive UI.";
        }
        if (lower.contains("api")) {
            return "An API lets one software system communicate with another using defined requests and responses.";
        }
        return "I am an offline assistant, so I answer from built-in logic and local context.";
    }

    @NonNull
    private String shorten(@NonNull String text, int max) {
        if (text.length() <= max) {
            return text;
        }
        return text.substring(0, max).trim() + "...";
    }

    @NonNull
    private String improveSentence(@NonNull String raw) {
        String cleaned = raw.trim().replaceAll("\\s+", " ");
        if (cleaned.isEmpty()) {
            return raw;
        }
        String first = cleaned.substring(0, 1).toUpperCase(Locale.US);
        String rest = cleaned.length() > 1 ? cleaned.substring(1) : "";
        String out = first + rest;
        if (!out.endsWith(".") && !out.endsWith("!") && !out.endsWith("?")) {
            out += ".";
        }
        return out;
    }

    private void appendStoredMessage(@NonNull String uid, @NonNull String role, @NonNull String text) throws JSONException {
        synchronized (this) {
            JSONArray stored = readStoredArray(uid);
            JSONObject obj = new JSONObject();
            obj.put("role", role);
            obj.put("text", text);
            obj.put("timestamp", System.currentTimeMillis());
            stored.put(obj);

            // Keep bounded local history.
            JSONArray trimmed = new JSONArray();
            int keep = Math.max(MAX_UI_MESSAGES, MAX_HISTORY_FOR_BOT) * 4;
            int start = Math.max(0, stored.length() - keep);
            for (int i = start; i < stored.length(); i++) {
                trimmed.put(stored.opt(i));
            }
            prefs.edit().putString(storageKey(uid), trimmed.toString()).apply();
        }
    }

    @NonNull
    private JSONArray readStoredArray(@NonNull String uid) throws JSONException {
        String raw = prefs.getString(storageKey(uid), "[]");
        if (raw == null || raw.trim().isEmpty()) {
            raw = "[]";
        }
        return new JSONArray(raw);
    }

    @NonNull
    private String storageKey(@NonNull String uid) {
        return KEY_PREFIX + uid;
    }

    @NonNull
    private String getSessionUid() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null ? user.getUid() : LOCAL_UID;
    }
}
