# Firebase + Cloud Functions setup

## 1. Firebase project

1. Create a project in [Firebase Console](https://console.firebase.google.com/).
2. Add an **Android** app with package `com.ajinkya.chatgptapplication`.
3. Download **`google-services.json`** and replace `app/google-services.json` (do not commit real keys to public repos).

## 2. Authentication (Google Sign-In)

1. In Firebase Console → **Authentication** → **Sign-in method** → enable **Google** and set support email.
2. In **Project settings** → **Your apps** → Android app: add **SHA-1** (and SHA-256) from your debug keystore:
   - Windows (debug):  
     `keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android`
3. Download an updated **`google-services.json`** after adding SHA-1.
4. Copy the **Web client ID** (OAuth 2.0 client ID ending in `.apps.googleusercontent.com`) from:
   - Project settings → Your apps → scroll to **Web client** under SDK setup, or  
   - Google Cloud Console → APIs & Services → Credentials → **Web client** for the same Firebase project.
5. Paste it into `app/src/main/res/values/strings.xml` as **`default_web_client_id`** (replace `REPLACE_WEB_CLIENT_ID.apps.googleusercontent.com`).

Without the correct Web client ID and SHA-1, Google Sign-In will fail or return no ID token.

## 3. Firestore

1. Create a Firestore database (production or test mode for dev).
2. Deploy rules:

```bash
firebase deploy --only firestore:rules
```

Rules path: `firestore.rules` (users may only read/write their own `users/{uid}/messages`).

## 4. Cloud Function (`/chat`)

1. Install Firebase CLI and log in: `npm i -g firebase-tools` → `firebase login`.
2. In project root: `firebase init functions` (if not already), select existing project.
3. Set Gemini API key for Functions (pick one):

```bash
firebase functions:config:set gemini.key="YOUR_GEMINI_API_KEY"
```

Or use Secret Manager / environment variables for production.

4. Deploy:

```bash
cd functions && npm install && cd ..
firebase deploy --only functions
```

5. Copy the HTTPS function URL (e.g. `https://us-central1-PROJECT.cloudfunctions.net/chat`).

## 5. Android app URL

In `gradle.properties` set:

```properties
CHAT_BACKEND_URL=https://REGION-PROJECT.cloudfunctions.net/chat
```

Sync Gradle and run.

## Security notes

- Never ship Gemini keys in the APK; only the Cloud Function uses `gemini.key`.
- Rotate keys if exposed.
