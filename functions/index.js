const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

/**
 * POST /chat
 * Headers: Authorization: Bearer <Firebase ID token>
 * Body: { "history": [ { "role": "user"|"assistant", "content": "..." } ] }
 * Response: { "reply": "..." }
 */
exports.chat = functions.https.onRequest(async (req, res) => {
  res.set("Access-Control-Allow-Origin", "*");
  res.set("Access-Control-Allow-Headers", "Authorization, Content-Type");
  if (req.method === "OPTIONS") {
    res.status(204).send("");
    return;
  }
  if (req.method !== "POST") {
    res.status(405).json({ error: { message: "Method not allowed" } });
    return;
  }

  const authHeader = req.headers.authorization || "";
  if (!authHeader.startsWith("Bearer ")) {
    res.status(401).json({
      error: { message: "Missing or invalid Authorization header", code: "auth_required" },
    });
    return;
  }
  const idToken = authHeader.slice("Bearer ".length).trim();

  try {
    await admin.auth().verifyIdToken(idToken);
  } catch (e) {
    res.status(401).json({
      error: { message: "Invalid or expired session", code: "invalid_token" },
    });
    return;
  }

  const apiKey =
    process.env.GEMINI_API_KEY ||
    (functions.config().gemini && functions.config().gemini.key);
  if (!apiKey) {
    res.status(500).json({
      error: { message: "Server missing GEMINI_API_KEY", code: "server_config" },
    });
    return;
  }

  const body = req.body || {};
  const history = body.history;
  if (!Array.isArray(history) || history.length === 0) {
    res.status(400).json({
      error: { message: "Field 'history' must be a non-empty array", code: "bad_request" },
    });
    return;
  }

  const contents = history.map((h) => {
    const role = h.role === "assistant" ? "model" : "user";
    return {
      role,
      parts: [{ text: String(h.content || "") }],
    };
  });

  const url = `https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=${encodeURIComponent(
    apiKey
  )}`;

  let geminiRes;
  try {
    geminiRes = await fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ contents }),
    });
  } catch (e) {
    res.status(502).json({
      error: { message: "Upstream request failed", code: "upstream" },
    });
    return;
  }

  const raw = await geminiRes.text();
  if (!geminiRes.ok) {
    res.status(geminiRes.status).type("application/json").send(raw);
    return;
  }

  let data;
  try {
    data = JSON.parse(raw);
  } catch (e) {
    res.status(502).json({
      error: { message: "Invalid JSON from Gemini", code: "bad_upstream" },
    });
    return;
  }

  const reply =
    data.candidates &&
    data.candidates[0] &&
    data.candidates[0].content &&
    data.candidates[0].content.parts &&
    data.candidates[0].content.parts[0] &&
    data.candidates[0].content.parts[0].text
      ? String(data.candidates[0].content.parts[0].text)
      : "";

  if (!reply) {
    res.status(502).json({
      error: { message: "Empty model response", code: "empty_response" },
    });
    return;
  }

  res.json({ reply });
});
