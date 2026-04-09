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
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private EditText emailInput;
    private EditText passwordInput;
    private MaterialButton btnLogin;
    private ProgressBar progress;
    private TextView gotoSignup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_login);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        emailInput = findViewById(R.id.et_email);
        passwordInput = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        progress = findViewById(R.id.login_progress);
        gotoSignup = findViewById(R.id.tv_goto_signup);

        btnLogin.setOnClickListener(v -> authViewModel.signInWithEmail(
                emailInput.getText() != null ? emailInput.getText().toString() : "",
                passwordInput.getText() != null ? passwordInput.getText().toString() : ""
        ));
        gotoSignup.setOnClickListener(v -> startActivity(new Intent(this, SignupActivity.class)));

        authViewModel.getBusy().observe(this, busy -> {
            boolean b = Boolean.TRUE.equals(busy);
            progress.setVisibility(b ? View.VISIBLE : View.GONE);
            btnLogin.setEnabled(!b);
        });

        authViewModel.getError().observe(this, msg -> {
            if (msg != null && !msg.isEmpty()) {
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }
        });

        authViewModel.getUserSignedIn().observe(this, user -> {
            if (user != null) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
    }
}
