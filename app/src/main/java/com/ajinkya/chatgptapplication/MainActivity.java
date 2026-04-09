package com.ajinkya.chatgptapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_MESSAGES = "messages_state";
    private static final String KEY_PENDING = "pending_user_message";
    private static final String KEY_PENDING_AT = "pending_user_message_at";

    private final List<Message> messageList = new ArrayList<>();
    private final ChatManager chatManager = new ChatManager();
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView;
    private EditText inputMessage;
    private ImageButton sendButton;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable unlockFallback = () -> sendButton.setEnabled(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_messages);
        inputMessage = findViewById(R.id.edit_text_message);
        sendButton = findViewById(R.id.button_send);

        messageAdapter = new MessageAdapter(messageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setHasFixedSize(false);

        sendButton.setOnClickListener(v -> onSendClicked());

        if (savedInstanceState != null) {
            restoreMessages(savedInstanceState.getStringArrayList(KEY_MESSAGES));
            String pending = savedInstanceState.getString(KEY_PENDING);
            if (pending != null && !pending.trim().isEmpty()) {
                sendButton.setEnabled(false);
                scheduleBotReply(pending);
            }
        }
    }

    private void onSendClicked() {
        String userText = inputMessage.getText() != null ? inputMessage.getText().toString().trim() : "";
        if (userText.isEmpty()) {
            return;
        }

        messageList.add(new Message(userText, true));
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        inputMessage.setText("");
        scrollToBottom();

        sendButton.setEnabled(false);
        scheduleBotReply(userText);
    }

    private void scrollToBottom() {
        if (!messageList.isEmpty()) {
            recyclerView.smoothScrollToPosition(messageList.size() - 1);
        }
    }

    private void scheduleBotReply(String userText) {
        handler.removeCallbacks(unlockFallback);
        handler.postDelayed(() -> {
            String botReply = chatManager.getBotReply(userText, messageList);
            messageList.add(new Message(botReply, false));
            messageAdapter.notifyItemInserted(messageList.size() - 1);
            scrollToBottom();
            sendButton.setEnabled(true);
        }, 1000);
        // Safety unlock in case any unexpected runtime issue occurs.
        handler.postDelayed(unlockFallback, 1800);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<String> encoded = new ArrayList<>();
        for (Message m : messageList) {
            encoded.add((m.isUser() ? "u|" : "b|") + (m.getText() == null ? "" : m.getText()));
        }
        outState.putStringArrayList(KEY_MESSAGES, encoded);

        if (!messageList.isEmpty()) {
            Message last = messageList.get(messageList.size() - 1);
            if (last.isUser()) {
                outState.putString(KEY_PENDING, last.getText());
                outState.putLong(KEY_PENDING_AT, System.currentTimeMillis());
            }
        }
    }

    private void restoreMessages(ArrayList<String> encoded) {
        if (encoded == null || encoded.isEmpty()) {
            return;
        }
        messageList.clear();
        for (String row : encoded) {
            if (row == null || row.length() < 2) {
                continue;
            }
            boolean isUser = row.startsWith("u|");
            String text = row.length() > 2 ? row.substring(2) : "";
            messageList.add(new Message(text, isUser));
        }
        messageAdapter.notifyDataSetChanged();
        scrollToBottom();
    }
}