package com.sanket.airesearcher.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sanket.airesearcher.Message;
import com.sanket.airesearcher.data.remote.ChatBackendException;
import com.sanket.airesearcher.data.repository.ChatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatViewModel extends AndroidViewModel {

    private final ChatRepository chatRepository;
    private final ExecutorService io = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<Message>> messages = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorEvent = new MutableLiveData<>();
    private final MutableLiveData<Boolean> sendEnabled = new MutableLiveData<>(true);
    private volatile boolean initialLoaded = false;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        this.chatRepository = new ChatRepository(application.getApplicationContext());
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<String> getErrorEvent() {
        return errorEvent;
    }

    public LiveData<Boolean> getSendEnabled() {
        return sendEnabled;
    }

    public void loadInitialMessages() {
        if (initialLoaded) {
            return;
        }
        io.execute(() -> {
            try {
                List<Message> list = chatRepository.loadRecentMessagesForUi();
                messages.postValue(list);
                initialLoaded = true;
            } catch (Exception e) {
                errorEvent.postValue(e.getMessage() != null ? e.getMessage() : "Failed to load messages.");
            }
        });
    }

    public void sendMessage(@NonNull String text) {
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            return;
        }
        io.execute(() -> {
            loading.postValue(true);
            sendEnabled.postValue(false);
            List<Message> current = messages.getValue();
            if (current == null) {
                current = new ArrayList<>();
            }
            ArrayList<Message> optimistic = new ArrayList<>(current);
            optimistic.add(new Message(trimmed, Message.SENT_BY_ME));
            optimistic.add(new Message("Typing...", Message.SENT_BY_BOT));
            messages.postValue(optimistic);

            try {
                String reply = chatRepository.sendUserMessageAndGetAssistantReply(trimmed);
                ArrayList<Message> updated = new ArrayList<>(optimistic);
                if (!updated.isEmpty() && Message.SENT_BY_BOT.equals(updated.get(updated.size() - 1).getSentBy())) {
                    updated.remove(updated.size() - 1);
                }
                updated.add(new Message(reply, Message.SENT_BY_BOT));
                messages.postValue(updated);
            } catch (ChatBackendException e) {
                ArrayList<Message> fallback = new ArrayList<>(optimistic);
                if (!fallback.isEmpty() && Message.SENT_BY_BOT.equals(fallback.get(fallback.size() - 1).getSentBy())) {
                    fallback.remove(fallback.size() - 1);
                }
                fallback.add(new Message("Sorry, I couldn't get a response right now.", Message.SENT_BY_BOT));
                messages.postValue(fallback);
                errorEvent.postValue(e.userFriendlyMessage);
            } catch (Exception e) {
                ArrayList<Message> fallback = new ArrayList<>(optimistic);
                if (!fallback.isEmpty() && Message.SENT_BY_BOT.equals(fallback.get(fallback.size() - 1).getSentBy())) {
                    fallback.remove(fallback.size() - 1);
                }
                fallback.add(new Message("Network issue. Please try again.", Message.SENT_BY_BOT));
                messages.postValue(fallback);
                errorEvent.postValue(e.getMessage() != null ? e.getMessage() : "Something went wrong.");
            } finally {
                loading.postValue(false);
                sendEnabled.postValue(true);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        io.shutdown();
    }
}
