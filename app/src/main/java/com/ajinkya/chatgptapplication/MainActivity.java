package com.ajinkya.chatgptapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

ImageView back_arrow,menu_arrow;
TextView tv_toolbar;
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;

    List<Message> messageList;
MessageAdapter messageAdapter;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageList = new ArrayList<>();

        //Toolbar id
        back_arrow = findViewById(R.id.back_arrow);
        menu_arrow = findViewById(R.id.menu_arrow);
        tv_toolbar = findViewById(R.id.tv_toolbar);

        if (back_arrow != null) {
            back_arrow.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        }

        //chatgpt id
        recyclerView = findViewById(R.id.recycle_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);

        if (recyclerView == null || welcomeTextView == null || messageEditText == null || sendButton == null) {
            Toast.makeText(this, "Layout initialization failed.", Toast.LENGTH_LONG).show();
            return;
        }

        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);

        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

    sendButton.setOnClickListener((v)->
        {

            String question = messageEditText.getText().toString().trim();
            if (question.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                return;
            }
            addTOchat(question,Message.SENT_BY_ME);
            messageEditText.setText("");
            callAPI(question);
            welcomeTextView.setVisibility(View.GONE);
        });

    }
    void appendMessage(String message, String sentBy) {
        String safeMessage = message == null ? "" : message;
        if (messageList == null || messageAdapter == null) {
            return;
        }
        messageList.add(new Message(safeMessage, sentBy));
        messageAdapter.notifyDataSetChanged();
        if (recyclerView != null && messageAdapter.getItemCount() > 0) {
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        }
    }

    void addTOchat(String message,String sentBy)
    {
        if (Looper.getMainLooper().isCurrentThread()) {
            appendMessage(message, sentBy);
        } else {
            runOnUiThread(() -> appendMessage(message, sentBy));
        }

    }

    void  addResponse(String response)
    {
        runOnUiThread(() -> {
            if (!messageList.isEmpty()) {
                messageList.remove(messageList.size() - 1);
            }
            appendMessage(response, Message.SENT_BY_BOT);
        });

    }

    void callAPI(String question)
    {
        if (messageList == null) {
            return;
        }
        messageList.add(new Message("Typing....",Message.SENT_BY_BOT));
        JSONObject jsonBody = new JSONObject();

        try {
            JSONArray contents = new JSONArray();
            JSONObject content = new JSONObject();
            JSONArray parts = new JSONArray();
            JSONObject part = new JSONObject();

            part.put("text", question);
            parts.put(part);
            content.put("parts", parts);
            contents.put(content);
            jsonBody.put("contents", contents);
        } catch (JSONException e) {
          e.printStackTrace();
        }
        finally {

        }




        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key="
                + BuildConfig.GEMINI_API_KEY;
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type","application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to Load response due to "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject jsonObject = null;
                    try {
                        if (response.body() == null) {
                            addResponse("Empty response from server.");
                            return;
                        }
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray candidates = jsonObject.getJSONArray("candidates");
                        JSONObject firstCandidate = candidates.getJSONObject(0);
                        JSONObject content = firstCandidate.getJSONObject("content");
                        JSONArray parts = content.getJSONArray("parts");
                        String result = parts.length() > 0 ? parts.getJSONObject(0).optString("text", "") : "";
                        if (result == null || result.trim().isEmpty()) {
                            addResponse("No text response from Gemini.");
                        } else {
                            addResponse(result.trim());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        addResponse("Failed to parse Gemini response.");
                    }

                }else{
                    addResponse("Failed to response due to HTTP " + response.code());
                }
            }
        });
    }


}