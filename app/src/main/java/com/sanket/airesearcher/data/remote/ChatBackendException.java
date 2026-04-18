package com.sanket.airesearcher.data.remote;

import androidx.annotation.Nullable;

public class ChatBackendException extends Exception {

    public final int httpCode;
    public final String userFriendlyMessage;

    public ChatBackendException(int httpCode, String userFriendlyMessage, @Nullable Throwable cause) {
        super(userFriendlyMessage, cause);
        this.httpCode = httpCode;
        this.userFriendlyMessage = userFriendlyMessage;
    }
}
