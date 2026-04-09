# Layout (production-oriented)

```
app/
  src/main/
    AndroidManifest.xml
    java/com/ajinkya/chatgptapplication/
      SplashActivity.java
      Message.java
      MessageAdapter.java
      data/
        remote/ChatBackendException.java
        repository/AuthRepository.java
        repository/ChatRepository.java
      viewmodel/
        AuthViewModel.java
        ChatViewModel.java
      ui/auth/
        LoginActivity.java
      ui/chat/
        ChatActivity.java
    res/layout/
      activity_splash.xml
      activity_login.xml
      activity_chat.xml
      chat_item.xml
      toolbar.xml
  google-services.json   ← replace with your Firebase file
functions/
  index.js               ← HTTPS /chat Cloud Function (Gemini + Auth)
  package.json
firebase.json
firestore.rules
docs/FIREBASE_SETUP.md
```
