package com.ajinkya.chatgptapplication.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ajinkya.chatgptapplication.MainActivity;
import com.ajinkya.chatgptapplication.R;
import com.ajinkya.chatgptapplication.viewmodel.AuthViewModel;
import com.google.android.material.button.MaterialButton;

public class SignupActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private EditText emailInput;
    private EditText passwordInput;
    private MaterialButton btnSignup;
    private ProgressBar progress;
    private TextView gotoLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        emailInput = findViewById(R.id.et_email_signup);
        passwordInput = findViewById(R.id.et_password_signup);
        btnSignup = findViewById(R.id.btn_signup);
        progress = findViewById(R.id.signup_progress);
        gotoLogin = findViewById(R.id.tv_goto_login);

        btnSignup.setOnClickListener(v -> authViewModel.signUpWithEmail(
                emailInput.getText() != null ? emailInput.getText().toString() : "",
                passwordInput.getText() != null ? passwordInput.getText().toString() : ""
        ));
        gotoLogin.setOnClickListener(v -> finish());

        authViewModel.getBusy().observe(this, busy -> {
            boolean b = Boolean.TRUE.equals(busy);
            progress.setVisibility(b ? View.VISIBLE : View.GONE);
            btnSignup.setEnabled(!b);
        });

        authViewModel.getError().observe(this, msg -> {
            if (msg != null && !msg.isEmpty()) {
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }
        });

        authViewModel.getUserSignedIn().observe(this, user -> {
            if (user != null) {
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
            }
        });
    }
}
