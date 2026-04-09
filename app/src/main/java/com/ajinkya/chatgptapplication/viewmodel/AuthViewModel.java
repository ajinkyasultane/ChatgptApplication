package com.ajinkya.chatgptapplication.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ajinkya.chatgptapplication.data.repository.AuthRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthViewModel extends ViewModel {

    private final AuthRepository authRepository = new AuthRepository();
    private final ExecutorService io = Executors.newSingleThreadExecutor();

    private final MutableLiveData<Boolean> busy = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> userSignedIn = new MutableLiveData<>();

    public LiveData<Boolean> getBusy() {
        return busy;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<FirebaseUser> getUserSignedIn() {
        return userSignedIn;
    }

    public void signInWithEmail(@Nullable String email, @Nullable String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            error.setValue("Email and password are required.");
            return;
        }
        runAuthTask(authRepository.signInWithEmail(email.trim(), password));
    }

    public void signUpWithEmail(@Nullable String email, @Nullable String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            error.setValue("Email and password are required.");
            return;
        }
        if (password.length() < 6) {
            error.setValue("Password must be at least 6 characters.");
            return;
        }
        runAuthTask(authRepository.signUpWithEmail(email.trim(), password));
    }

    private void runAuthTask(@NonNull Task<AuthResult> task) {
        busy.postValue(true);
        io.execute(() -> {
            try {
                AuthResult result = com.google.android.gms.tasks.Tasks.await(task);
                userSignedIn.postValue(result.getUser());
            } catch (Exception e) {
                error.postValue(mapAuthError(e));
            } finally {
                busy.postValue(false);
            }
        });
    }

    @NonNull
    private String mapAuthError(@NonNull Throwable e) {
        String m = e.getMessage() != null ? e.getMessage() : "Sign-in failed.";
        if (m.contains("network") || m.contains("NetworkError")) {
            return "Network error. Check your connection and try again.";
        }
        if (m.contains("password is invalid") || m.contains("INVALID_LOGIN_CREDENTIALS")) {
            return "Invalid email or password.";
        }
        if (m.contains("already in use")) {
            return "This email is already registered. Please log in.";
        }
        return "Authentication failed. Please try again.";
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        io.shutdown();
    }
}
