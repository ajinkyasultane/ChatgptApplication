package com.ajinkya.chatgptapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ajinkya.chatgptapplication.ui.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    AppCompatImageView iv_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv_logo = findViewById(R.id.iv_logo);

        if (iv_logo != null) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink_in);
            iv_logo.startAnimation(animation);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(
                        SplashActivity.this,
                        FirebaseAuth.getInstance().getCurrentUser() != null ? MainActivity.class : LoginActivity.class
                );
                startActivity(i);
                finish();
            }
        }, 800);

    }
}