package com.sanket.airesearcher.ui.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanket.airesearcher.Message;
import com.sanket.airesearcher.MessageAdapter;
import com.sanket.airesearcher.R;
import com.sanket.airesearcher.viewmodel.ChatViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ChatViewModel chatViewModel;
    private RecyclerView recyclerView;
    private View welcomeHolder;
    private TextView welcomeTextView;
    private EditText messageEditText;
    private ImageButton sendButton;
    private ProgressBar loadingBar;

    private final ArrayList<Message> messageItems = new ArrayList<>();
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ImageView backArrow = findViewById(R.id.back_arrow);
        if (backArrow != null) {
            backArrow.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        }

        recyclerView = findViewById(R.id.recycle_view);
        welcomeHolder = findViewById(R.id.welcome_holder);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);
        loadingBar = findViewById(R.id.chat_loading_bar);

        messageAdapter = new MessageAdapter(messageItems);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        chatViewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        ).get(ChatViewModel.class);

        chatViewModel.getMessages().observe(this, this::renderMessages);
        chatViewModel.getLoading().observe(this, loading ->
                loadingBar.setVisibility(Boolean.TRUE.equals(loading) ? View.VISIBLE : View.GONE));
        chatViewModel.getSendEnabled().observe(this, enabled ->
                sendButton.setEnabled(Boolean.TRUE.equals(enabled)));
        chatViewModel.getErrorEvent().observe(this, msg -> {
            if (msg != null && !msg.isEmpty()) {
                Toast.makeText(ChatActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        sendButton.setOnClickListener(v -> {
            String text = messageEditText.getText() != null ? messageEditText.getText().toString() : "";
            if (text.trim().isEmpty()) {
                Toast.makeText(this, R.string.hint_message, Toast.LENGTH_SHORT).show();
                return;
            }
            chatViewModel.sendMessage(text);
            messageEditText.setText("");
        });

        chatViewModel.loadInitialMessages();
    }

    private void renderMessages(@Nullable List<Message> messages) {
        messageItems.clear();
        if (messages != null) {
            messageItems.addAll(messages);
        }
        messageAdapter.notifyDataSetChanged();

        boolean isEmpty = messageItems.isEmpty();
        if (welcomeHolder != null) {
            welcomeHolder.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        } else if (welcomeTextView != null) {
            welcomeTextView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        }

        if (!isEmpty) {
            recyclerView.smoothScrollToPosition(messageItems.size() - 1);
        }
    }
}
