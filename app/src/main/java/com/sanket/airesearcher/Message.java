package com.sanket.airesearcher;

public class Message {
    public static String SENT_BY_ME = "me";
    public static String SENT_BY_BOT = "bot";

    private String text;
    private boolean isUser;

    public Message(String text, boolean isUser) {
        this.text = text;
        this.isUser = isUser;
    }

    // Backward-compatible constructor
    public Message(String message, String sentBy) {
        this.text = message;
        this.isUser = SENT_BY_ME.equals(sentBy);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    // Backward-compatible accessors
    public String getMessage() {
        return text;
    }

    public void setMessage(String message) {
        this.text = message;
    }

    public String getSentBy() {
        return isUser ? SENT_BY_ME : SENT_BY_BOT;
    }

    public void setSentBy(String sentBy) {
        this.isUser = SENT_BY_ME.equals(sentBy);
    }
}
