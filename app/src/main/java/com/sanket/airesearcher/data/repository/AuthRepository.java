package com.sanket.airesearcher.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {

    private final FirebaseAuth auth;

    public AuthRepository() {
        this.auth = FirebaseAuth.getInstance();
    }

    @Nullable
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    public Task<AuthResult> signInWithEmail(@NonNull String email, @NonNull String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> signUpWithEmail(@NonNull String email, @NonNull String password) {
        return auth.createUserWithEmailAndPassword(email, password);
    }

    public void signOut() {
        auth.signOut();
    }
}
