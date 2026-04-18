package com.sanket.airesearcher;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ChatManager — offline AI knowledge engine.
 *
 * Features:
 * - Multi-operand math (e.g. 4*5*7, 10+3-2, (2+3)*4)
 * - Deep knowledge: Programming languages, Blockchain, IoT, AI/ML, Data Structures,
 *   Algorithms, Networking, OS, Databases, Cloud, Cybersecurity, Web Dev, Mobile Dev,
 *   Software Engineering, Computer Architecture, and general CS concepts.
 * - Conversational memory (name, preferences, last message).
 */
public class ChatManager {

    // ─── Long-term memory ───────────────────────────────────────────────────────
    private String rememberedName = "";
    private String rememberedPreference = "";
    private String lastUserMessage = "";

    // ─── Knowledge Base Map ─────────────────────────────────────────────────────
    // key = trigger keyword(s) → value = detailed explanation
    private static final Map<String, String> KB = new HashMap<>();

    static {

        // ════════════════════════════════════════════════════════════════════
        //  PROGRAMMING LANGUAGES
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is python",
            "🐍 Python\n\n" +
            "Python is a high-level, interpreted, general-purpose programming language created by Guido van Rossum in 1991.\n\n" +
            "✅ Key Features:\n" +
            "• Simple, readable syntax (uses indentation instead of braces)\n" +
            "• Dynamically typed — no need to declare variable types\n" +
            "• Interpreted — code runs line by line\n" +
            "• Multi-paradigm: supports OOP, functional, and procedural programming\n" +
            "• Massive standard library + huge ecosystem (pip packages)\n" +
            "• Cross-platform: runs on Windows, macOS, Linux\n\n" +
            "📌 Use Cases:\n" +
            "• Web Development (Django, Flask, FastAPI)\n" +
            "• Data Science & Analytics (Pandas, NumPy, Matplotlib)\n" +
            "• Machine Learning & AI (TensorFlow, PyTorch, Scikit-learn)\n" +
            "• Automation & Scripting\n" +
            "• Cybersecurity & Ethical Hacking\n" +
            "• Game Development (Pygame)\n" +
            "• Scientific Computing\n\n" +
            "📝 Example:\n" +
            "  name = 'Alice'\n" +
            "  print(f'Hello, {name}!')  # Output: Hello, Alice!\n\n" +
            "⚡ Python is currently the most popular language in AI/ML research worldwide.");

        KB.put("what is java",
            "☕ Java\n\n" +
            "Java is a class-based, object-oriented, platform-independent language developed by Sun Microsystems (now Oracle) in 1995.\n\n" +
            "✅ Key Features:\n" +
            "• Write Once, Run Anywhere (WORA) — via JVM (Java Virtual Machine)\n" +
            "• Strongly statically typed\n" +
            "• Compiled to bytecode, then interpreted/JIT-compiled at runtime\n" +
            "• Robust exception handling & garbage collection\n" +
            "• Multi-threading support built-in\n" +
            "• Huge enterprise ecosystem (Spring, Hibernate, Maven)\n\n" +
            "📌 Use Cases:\n" +
            "• Android App Development\n" +
            "• Enterprise Backend Systems (Spring Boot)\n" +
            "• Big Data (Hadoop, Spark)\n" +
            "• Web Applications\n" +
            "• Embedded Systems\n\n" +
            "📝 Example:\n" +
            "  public class Hello {\n" +
            "    public static void main(String[] args) {\n" +
            "      System.out.println(\"Hello, World!\");\n" +
            "    }\n" +
            "  }");

        KB.put("what is kotlin",
            "🎯 Kotlin\n\n" +
            "Kotlin is a modern, statically typed, cross-platform language developed by JetBrains, officially adopted by Google as the preferred Android language in 2017.\n\n" +
            "✅ Key Features:\n" +
            "• 100% interoperable with Java\n" +
            "• Null Safety — eliminates NullPointerExceptions at compile time\n" +
            "• Concise syntax — ~40% less boilerplate than Java\n" +
            "• Coroutines for asynchronous programming\n" +
            "• Data classes, extension functions, lambdas\n" +
            "• Kotlin Multiplatform: share code across Android, iOS, Web\n\n" +
            "📌 Use Cases:\n" +
            "• Android Development (Jetpack Compose + Kotlin)\n" +
            "• Server-Side (Ktor framework)\n" +
            "• Cross-Platform Mobile\n\n" +
            "📝 Example:\n" +
            "  fun greet(name: String) = \"Hello, $name!\"\n" +
            "  println(greet(\"World\"))  // Hello, World!");

        KB.put("what is javascript",
            "🌐 JavaScript (JS)\n\n" +
            "JavaScript is the scripting language of the Web, created by Brendan Eich in 1995. It runs natively in all browsers.\n\n" +
            "✅ Key Features:\n" +
            "• Dynamically typed, interpreted\n" +
            "• Event-driven, non-blocking I/O (Node.js)\n" +
            "• First-class functions, closures, prototype-based OOP\n" +
            "• ES6+ features: arrow functions, async/await, destructuring, modules\n" +
            "• Runs on both client-side (browser) and server-side (Node.js)\n\n" +
            "📌 Use Cases:\n" +
            "• Frontend Web (React, Vue, Angular)\n" +
            "• Backend (Node.js, Express)\n" +
            "• Mobile Apps (React Native)\n" +
            "• Desktop Apps (Electron)\n" +
            "• APIs & Microservices\n\n" +
            "📝 Example:\n" +
            "  const add = (a, b) => a + b;\n" +
            "  console.log(add(3, 4));  // 7");

        KB.put("what is c programming",
            "⚙️ C Programming\n\n" +
            "C is a general-purpose, procedural language created by Dennis Ritchie at Bell Labs in 1972. It is the 'mother of all languages'.\n\n" +
            "✅ Key Features:\n" +
            "• Low-level memory management (malloc, free, pointers)\n" +
            "• Extremely fast — compiled to native machine code\n" +
            "• Portable across platforms\n" +
            "• No built-in OOP (but can simulate it with structs)\n" +
            "• Direct hardware access\n\n" +
            "📌 Use Cases:\n" +
            "• Operating System Kernels (Linux, Windows)\n" +
            "• Embedded & IoT Systems\n" +
            "• System Programming & Drivers\n" +
            "• Compilers & Interpreters\n" +
            "• Game Engines\n\n" +
            "📝 Example:\n" +
            "  #include <stdio.h>\n" +
            "  int main() {\n" +
            "    printf(\"Hello, World!\\n\");\n" +
            "    return 0;\n" +
            "  }");

        KB.put("what is c++",
            "⚡ C++\n\n" +
            "C++ is a high-performance, multi-paradigm language created by Bjarne Stroustrup in 1985 as an extension of C.\n\n" +
            "✅ Key Features:\n" +
            "• Object-Oriented Programming with classes, inheritance, polymorphism\n" +
            "• Low-level memory control (pointers, references)\n" +
            "• Templates & STL (Standard Template Library)\n" +
            "• RAII (Resource Acquisition Is Initialization)\n" +
            "• Modern C++20 features: concepts, ranges, coroutines\n\n" +
            "📌 Use Cases:\n" +
            "• Game Development (Unreal Engine)\n" +
            "• System Software & Drivers\n" +
            "• High-Performance Applications (HFT, simulations)\n" +
            "• AI Inference Engines\n" +
            "• Embedded Systems\n\n" +
            "📝 Example:\n" +
            "  #include <iostream>\n" +
            "  int main() {\n" +
            "    std::cout << \"Hello, C++!\" << std::endl;\n" +
            "  }");

        KB.put("what is rust",
            "🦀 Rust\n\n" +
            "Rust is a systems programming language focused on safety, performance, and concurrency, developed by Mozilla (2010) and now maintained by the Rust Foundation.\n\n" +
            "✅ Key Features:\n" +
            "• Memory safety without garbage collection — ownership system\n" +
            "• Zero-cost abstractions\n" +
            "• Concurrency without data races\n" +
            "• Pattern matching, algebraic types (Option, Result)\n" +
            "• Excellent tooling (cargo, rustfmt, clippy)\n\n" +
            "📌 Use Cases:\n" +
            "• WebAssembly\n" +
            "• Operating Systems (Redox OS)\n" +
            "• Game Engines\n" +
            "• Networking & CLI tools\n" +
            "• Blockchain (Solana is written in Rust)\n\n" +
            "📝 Example:\n" +
            "  fn main() {\n" +
            "    println!(\"Hello, Rust!\");\n" +
            "  }");

        KB.put("what is go",
            "🐹 Go (Golang)\n\n" +
            "Go is a statically typed, compiled language designed by Google engineers (Robert Griesemer, Rob Pike, Ken Thompson) in 2009.\n\n" +
            "✅ Key Features:\n" +
            "• Simple syntax — easy to learn\n" +
            "• Built-in concurrency with goroutines & channels\n" +
            "• Fast compilation\n" +
            "• Strong standard library\n" +
            "• Garbage collected but still very fast\n\n" +
            "📌 Use Cases:\n" +
            "• Cloud & Microservices (Docker and Kubernetes are written in Go)\n" +
            "• REST APIs & backend services\n" +
            "• DevOps & CLI tools\n" +
            "• Distributed systems\n\n" +
            "📝 Example:\n" +
            "  package main\n" +
            "  import \"fmt\"\n" +
            "  func main() {\n" +
            "    fmt.Println(\"Hello, Go!\")\n" +
            "  }");

        KB.put("what is swift",
            "🍎 Swift\n\n" +
            "Swift is a powerful, intuitive language developed by Apple in 2014 for iOS, macOS, watchOS, and tvOS development.\n\n" +
            "✅ Key Features:\n" +
            "• Modern, safe — optional types prevent null crashes\n" +
            "• Protocol-Oriented Programming\n" +
            "• Memory management via ARC (Automatic Reference Counting)\n" +
            "• Interactive coding with Playgrounds\n" +
            "• SwiftUI for declarative UI building\n\n" +
            "📌 Use Cases:\n" +
            "• iOS & macOS App Development\n" +
            "• Server-Side Swift (Vapor framework)\n" +
            "• System-level programming\n\n" +
            "📝 Example:\n" +
            "  let name = \"Swift\"\n" +
            "  print(\"Hello, \\(name)!\")");

        KB.put("what is php",
            "🐘 PHP\n\n" +
            "PHP (Hypertext Preprocessor) is a server-side scripting language designed primarily for web development, created by Rasmus Lerdorf in 1993.\n\n" +
            "✅ Key Features:\n" +
            "• Embeds directly into HTML\n" +
            "• Huge web hosting support\n" +
            "• MySQL/MariaDB integration\n" +
            "• Frameworks: Laravel, Symfony, CodeIgniter\n\n" +
            "📌 Use Cases:\n" +
            "• CMS platforms (WordPress, Drupal, Joomla)\n" +
            "• Dynamic websites\n" +
            "• REST APIs (Laravel)\n\n" +
            "📝 Example:\n" +
            "  <?php\n" +
            "    $name = 'World';\n" +
            "    echo \"Hello, $name!\";\n" +
            "  ?>");

        KB.put("what is typescript",
            "📘 TypeScript\n\n" +
            "TypeScript is a strongly typed superset of JavaScript developed by Microsoft (2012). It compiles to plain JavaScript.\n\n" +
            "✅ Key Features:\n" +
            "• Static type checking at compile time\n" +
            "• Interfaces, generics, enums\n" +
            "• Works with all JS frameworks\n" +
            "• Improves IDE support & catches bugs early\n\n" +
            "📌 Use Cases:\n" +
            "• Large-scale frontend apps (Angular is built with TS)\n" +
            "• Node.js backends\n" +
            "• Any JavaScript project needing type safety");

        KB.put("what is r language",
            "📊 R Language\n\n" +
            "R is a language and environment for statistical computing and graphics, widely used in academia and data science.\n\n" +
            "✅ Key Features:\n" +
            "• Designed for statistics and data analysis\n" +
            "• Rich ecosystem: ggplot2, dplyr, tidyr, caret\n" +
            "• Excellent for data visualization\n" +
            "• RStudio IDE\n\n" +
            "📌 Use Cases:\n" +
            "• Statistical Modeling\n" +
            "• Bioinformatics\n" +
            "• Data Visualization\n" +
            "• Machine Learning");

        KB.put("what is dart",
            "🎯 Dart\n\n" +
            "Dart is a client-optimized, object-oriented language developed by Google, primarily used with the Flutter framework.\n\n" +
            "✅ Key Features:\n" +
            "• Strong typing with type inference\n" +
            "• Asynchronous programming with async/await\n" +
            "• Compiles to native, JS, or runs in VM\n\n" +
            "📌 Use Cases:\n" +
            "• Flutter apps (iOS, Android, Web, Desktop)\n" +
            "• Server-side (dart:io)\n\n" +
            "📝 Example:\n" +
            "  void main() {\n" +
            "    print('Hello, Dart!');\n" +
            "  }");

        KB.put("what is sql",
            "🗄️ SQL (Structured Query Language)\n\n" +
            "SQL is a domain-specific language for managing and querying relational databases.\n\n" +
            "✅ Key Commands:\n" +
            "• SELECT — retrieve data\n" +
            "• INSERT — add records\n" +
            "• UPDATE — modify records\n" +
            "• DELETE — remove records\n" +
            "• CREATE TABLE — define table structure\n" +
            "• JOIN — combine rows from multiple tables\n\n" +
            "📌 Databases that use SQL:\n" +
            "MySQL, PostgreSQL, SQLite, Microsoft SQL Server, Oracle\n\n" +
            "📝 Example:\n" +
            "  SELECT name, age FROM users WHERE age > 18 ORDER BY name;");

        // ════════════════════════════════════════════════════════════════════
        //  BLOCKCHAIN
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is blockchain",
            "⛓️ Blockchain\n\n" +
            "A blockchain is a distributed, decentralized, immutable ledger that records transactions across a peer-to-peer network.\n\n" +
            "✅ Core Concepts:\n" +
            "• Block — a container holding a batch of transactions + a cryptographic hash of the previous block\n" +
            "• Chain — blocks linked together in chronological order\n" +
            "• Decentralization — no single point of control or failure\n" +
            "• Immutability — once recorded, data cannot be altered without network consensus\n" +
            "• Consensus Mechanisms: Proof of Work (PoW), Proof of Stake (PoS), DPoS\n\n" +
            "📌 Use Cases:\n" +
            "• Cryptocurrency (Bitcoin, Ethereum)\n" +
            "• Smart Contracts (self-executing code on blockchain)\n" +
            "• Supply Chain Management (transparent tracking)\n" +
            "• Healthcare (secure patient records)\n" +
            "• Voting Systems\n" +
            "• NFTs (Non-Fungible Tokens)\n" +
            "• DeFi (Decentralized Finance)\n\n" +
            "🔐 Why is it secure?\n" +
            "Each block contains the previous block's hash. Changing any block would invalidate all subsequent blocks, requiring recomputing the entire chain — computationally infeasible.\n\n" +
            "🌐 Popular Blockchains: Bitcoin, Ethereum, Solana, Polkadot, Cardano, Hyperledger Fabric.");

        KB.put("what is cryptocurrency",
            "💰 Cryptocurrency\n\n" +
            "A cryptocurrency is a digital or virtual currency secured by cryptography, operating on a blockchain network.\n\n" +
            "✅ Key Examples:\n" +
            "• Bitcoin (BTC) — the first cryptocurrency, created by Satoshi Nakamoto in 2009\n" +
            "• Ethereum (ETH) — smart contracts platform\n" +
            "• Solana (SOL) — high speed, low fees\n" +
            "• Binance Coin (BNB), Cardano (ADA), Ripple (XRP)\n\n" +
            "📌 Key Concepts:\n" +
            "• Wallet — stores your private/public keys\n" +
            "• Mining — validating transactions using computation (PoW)\n" +
            "• Staking — locking crypto to validate transactions (PoS)\n" +
            "• Exchange — platforms to buy/sell crypto (Binance, Coinbase)\n\n" +
            "⚠️ Note: Crypto is highly volatile and unregulated in many countries. Always do your own research (DYOR).");

        KB.put("what is smart contract",
            "📝 Smart Contracts\n\n" +
            "A smart contract is self-executing code stored on a blockchain that automatically enforces the terms of an agreement when predefined conditions are met.\n\n" +
            "✅ How it works:\n" +
            "1. Code is written (usually in Solidity for Ethereum)\n" +
            "2. Deployed to blockchain — it becomes immutable\n" +
            "3. When trigger conditions are met, the contract executes automatically\n" +
            "4. No middleman needed (no bank, lawyer, or escrow)\n\n" +
            "📌 Use Cases:\n" +
            "• DeFi lending and borrowing\n" +
            "• NFT minting and trading\n" +
            "• DAO governance\n" +
            "• Insurance automation\n" +
            "• Token ICOs\n\n" +
            "📝 Solidity Example:\n" +
            "  contract HelloWorld {\n" +
            "    string public message = \"Hello, Blockchain!\";\n" +
            "  }");

        KB.put("what is nft",
            "🖼️ NFT (Non-Fungible Token)\n\n" +
            "An NFT is a unique digital asset verified on the blockchain. Unlike cryptocurrencies, NFTs are NOT interchangeable — each one is unique.\n\n" +
            "✅ Key Points:\n" +
            "• Fungible = identical and interchangeable (1 BTC = 1 BTC)\n" +
            "• Non-Fungible = unique, cannot be replicated\n" +
            "• NFTs use blockchain to prove ownership of digital items\n\n" +
            "📌 What can be an NFT?\n" +
            "• Digital Art, Music, Videos\n" +
            "• In-game items (skins, weapons)\n" +
            "• Virtual land (Metaverse)\n" +
            "• Collectibles, domain names\n\n" +
            "🌐 Platforms: OpenSea, Rarible, Foundation, Blur");

        KB.put("what is defi",
            "💳 DeFi (Decentralized Finance)\n\n" +
            "DeFi refers to financial services — lending, borrowing, trading, and earning interest — built on blockchain without banks or intermediaries.\n\n" +
            "✅ Core DeFi Applications:\n" +
            "• DEX (Decentralized Exchange) — trade crypto without a centralized exchange (e.g., Uniswap)\n" +
            "• Lending Protocols — earn interest or borrow against collateral (Aave, Compound)\n" +
            "• Stablecoins — crypto pegged to a stable asset like USD (USDT, USDC, DAI)\n" +
            "• Yield Farming — maximize returns by providing liquidity\n" +
            "• Liquidity Pools — supply assets in exchange for fees");

        // ════════════════════════════════════════════════════════════════════
        //  IoT
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is iot",
            "📡 IoT (Internet of Things)\n\n" +
            "IoT is the network of physical objects (things) embedded with sensors, software, and connectivity to exchange data over the internet.\n\n" +
            "✅ Key Components:\n" +
            "• Sensors/Actuators — collect data (temperature, motion, pressure)\n" +
            "• Connectivity — WiFi, Bluetooth, Zigbee, LoRa, 5G, MQTT\n" +
            "• Data Processing — Edge computing or Cloud (AWS IoT, Google Cloud IoT)\n" +
            "• User Interface — dashboards, apps\n\n" +
            "📌 IoT Use Cases:\n" +
            "• Smart Home (Alexa, Google Home, smart bulbs, thermostats)\n" +
            "• Industrial IoT / Industry 4.0 (predictive maintenance)\n" +
            "• Healthcare (wearables, remote patient monitoring)\n" +
            "• Smart Agriculture (soil sensors, automated irrigation)\n" +
            "• Smart Cities (traffic management, waste management)\n" +
            "• Supply Chain & Logistics (GPS trackers, RFID)\n" +
            "• Automotive (connected & self-driving cars)\n\n" +
            "🔧 Popular Platforms & Hardware:\n" +
            "• Arduino, Raspberry Pi, ESP8266, ESP32\n" +
            "• Platforms: AWS IoT, Azure IoT Hub, ThingSpeak, IBM Watson IoT\n\n" +
            "📡 Key Protocols:\n" +
            "• MQTT (Message Queuing Telemetry Transport) — lightweight pub/sub\n" +
            "• HTTP/HTTPS, CoAP, AMQP, WebSocket");

        KB.put("what is mqtt",
            "📨 MQTT\n\n" +
            "MQTT (Message Queuing Telemetry Transport) is a lightweight publish-subscribe messaging protocol designed for IoT devices with limited bandwidth.\n\n" +
            "✅ How it works:\n" +
            "• Publisher sends messages to a Topic on a Broker\n" +
            "• Subscriber listens to a Topic on the same Broker\n" +
            "• Broker: Mosquitto, HiveMQ, AWS IoT Core\n\n" +
            "📌 Why MQTT for IoT?\n" +
            "• Extremely lightweight — runs on microcontrollers\n" +
            "• Works on low-bandwidth, high-latency, unreliable networks\n" +
            "• QoS levels: 0 (fire-and-forget), 1 (at least once), 2 (exactly once)");

        KB.put("what is arduino",
            "🔌 Arduino\n\n" +
            "Arduino is an open-source electronics platform combining hardware (microcontroller boards) and software (Arduino IDE) for prototyping IoT and embedded systems.\n\n" +
            "✅ Key Features:\n" +
            "• Simple C/C++-like programming language\n" +
            "• Huge library ecosystem\n" +
            "• Easy to use for beginners\n" +
            "• Popular boards: Arduino Uno, Nano, Mega, MKR\n\n" +
            "📌 Common Projects:\n" +
            "• LED control, sensor reading\n" +
            "• Robot cars, drones\n" +
            "• Home automation\n" +
            "• Weather stations");

        KB.put("what is raspberry pi",
            "🍓 Raspberry Pi\n\n" +
            "Raspberry Pi is a tiny, affordable single-board computer (SBC) used for learning programming and building projects.\n\n" +
            "✅ Key Features:\n" +
            "• Full Linux OS (Raspberry Pi OS)\n" +
            "• GPIO pins for hardware interfacing\n" +
            "• Supports Python, Java, C, and more\n" +
            "• Models: Pi Zero, Pi 3, Pi 4, Pi 5\n\n" +
            "📌 Use Cases:\n" +
            "• Media Center (Kodi)\n" +
            "• Retro Gaming Console\n" +
            "• Home Automation Hub\n" +
            "• Web Server\n" +
            "• Learning Linux & Programming");

        // ════════════════════════════════════════════════════════════════════
        //  AI & MACHINE LEARNING
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is artificial intelligence",
            "🤖 Artificial Intelligence (AI)\n\n" +
            "AI is the simulation of human intelligence processes by computer systems — including learning, reasoning, problem solving, perception, and language understanding.\n\n" +
            "✅ Major Branches:\n" +
            "• Machine Learning (ML) — algorithms that learn from data\n" +
            "• Deep Learning (DL) — neural networks with many layers\n" +
            "• Natural Language Processing (NLP) — understanding human language\n" +
            "• Computer Vision — enabling machines to see and interpret images\n" +
            "• Robotics — physical AI agents\n" +
            "• Expert Systems — rule-based decision making\n\n" +
            "📌 Real-world Applications:\n" +
            "• ChatGPT, Google Gemini (conversational AI)\n" +
            "• Self-driving cars (Tesla Autopilot, Waymo)\n" +
            "• Recommendation Systems (Netflix, YouTube, Spotify)\n" +
            "• Fraud Detection in banking\n" +
            "• Medical Diagnosis (detecting cancer from scans)\n" +
            "• Face recognition, Voice assistants (Siri, Alexa)\n\n" +
            "🧠 AI Types:\n" +
            "• Narrow AI (ANI) — designed for one specific task\n" +
            "• General AI (AGI) — human-level reasoning across all tasks (not yet achieved)\n" +
            "• Super AI (ASI) — surpasses human intelligence (theoretical)");

        KB.put("what is machine learning",
            "📈 Machine Learning (ML)\n\n" +
            "ML is a subset of AI that enables systems to learn from data and improve from experience without being explicitly programmed.\n\n" +
            "✅ Types of ML:\n" +
            "• Supervised Learning — trained on labeled data (e.g., spam detection)\n" +
            "  Algorithms: Linear Regression, Decision Trees, SVM, Random Forest\n" +
            "• Unsupervised Learning — finds patterns in unlabeled data (e.g., clustering)\n" +
            "  Algorithms: K-Means, DBSCAN, PCA\n" +
            "• Reinforcement Learning — learns by reward/punishment (e.g., game-playing AI)\n" +
            "  Algorithms: Q-Learning, PPO, A3C\n\n" +
            "📌 Key Concepts:\n" +
            "• Training & Test sets\n" +
            "• Features & Labels\n" +
            "• Overfitting & Underfitting\n" +
            "• Loss Function, Gradient Descent, Backpropagation\n\n" +
            "🛠️ Popular Libraries: Scikit-learn, TensorFlow, PyTorch, Keras, XGBoost");

        KB.put("what is deep learning",
            "🧠 Deep Learning\n\n" +
            "Deep Learning is a subset of ML using multi-layered artificial neural networks (ANNs) that automatically learn representations from raw data.\n\n" +
            "✅ Key Architectures:\n" +
            "• CNN (Convolutional Neural Networks) — image recognition, object detection\n" +
            "• RNN (Recurrent Neural Networks) — sequential data, time series\n" +
            "• LSTM — Long Short-Term Memory, better RNN for long sequences\n" +
            "• Transformer — attention mechanism (powers GPT, BERT, Gemini)\n" +
            "• GAN (Generative Adversarial Networks) — generate realistic images, deepfakes\n\n" +
            "📌 Applications:\n" +
            "• Face recognition, Autonomous driving\n" +
            "• Speech-to-text, language translation\n" +
            "• Medical imaging, Drug discovery\n" +
            "• ChatGPT, DALL-E, Midjourney");

        KB.put("what is nlp",
            "💬 Natural Language Processing (NLP)\n\n" +
            "NLP is the branch of AI that helps computers understand, interpret, and generate human language.\n\n" +
            "✅ Core NLP Tasks:\n" +
            "• Tokenization — splitting text into words/sentences\n" +
            "• POS Tagging — identifying nouns, verbs, adjectives\n" +
            "• Named Entity Recognition (NER) — detecting names, places, dates\n" +
            "• Sentiment Analysis — positive/negative tone detection\n" +
            "• Text Summarization\n" +
            "• Machine Translation (Google Translate)\n" +
            "• Question Answering\n" +
            "• Text Generation (ChatGPT)\n\n" +
            "🛠️ Libraries: NLTK, spaCy, HuggingFace Transformers\n\n" +
            "🌐 Key Models: BERT, GPT-4, T5, LLaMA, Claude, Gemini");

        KB.put("what is data science",
            "📊 Data Science\n\n" +
            "Data Science is an interdisciplinary field combining statistics, programming, and domain knowledge to extract insights and knowledge from structured/unstructured data.\n\n" +
            "✅ Data Science Workflow:\n" +
            "1. Data Collection (web scraping, APIs, databases)\n" +
            "2. Data Cleaning & Preprocessing (handling missing values, normalization)\n" +
            "3. Exploratory Data Analysis (EDA) — understanding patterns\n" +
            "4. Feature Engineering — creating useful features for models\n" +
            "5. Model Building & Training\n" +
            "6. Evaluation & Tuning (cross-validation, hyperparameter tuning)\n" +
            "7. Deployment & Monitoring\n\n" +
            "🛠️ Tools: Python, R, Jupyter Notebooks, Pandas, NumPy, Matplotlib, Seaborn, Scikit-learn, SQL\n\n" +
            "📌 Key roles: Data Analyst, Data Scientist, ML Engineer, Data Engineer");

        // ════════════════════════════════════════════════════════════════════
        //  DATA STRUCTURES
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is data structure",
            "📦 Data Structures\n\n" +
            "A data structure is a way of organizing and storing data so it can be accessed and modified efficiently.\n\n" +
            "✅ Common Data Structures:\n\n" +
            "• Array — fixed-size, indexed collection. O(1) access.\n" +
            "• Linked List — nodes connected by pointers. O(n) access. Good for insertions.\n" +
            "• Stack — LIFO (Last In First Out). Used in undo operations, call stack.\n" +
            "• Queue — FIFO (First In First Out). Used in scheduling, BFS.\n" +
            "• Hash Map — key-value pairs with O(1) average access.\n" +
            "• Binary Tree — hierarchical structure with at most 2 children per node.\n" +
            "• Binary Search Tree (BST) — ordered tree for fast O(log n) search.\n" +
            "• Heap — complete binary tree for priority queue operations.\n" +
            "• Graph — nodes (vertices) and connections (edges), directed or undirected.\n" +
            "• Trie — tree for prefix-based string searching.\n\n" +
            "📌 Choosing a data structure depends on the operations needed: search, insert, delete, traverse.");

        KB.put("what is algorithm",
            "🔢 Algorithms\n\n" +
            "An algorithm is a step-by-step procedure for solving a problem in finite time.\n\n" +
            "✅ Algorithm Categories:\n\n" +
            "• Sorting: Bubble Sort O(n²), Merge Sort O(n log n), Quick Sort O(n log n), Heap Sort\n" +
            "• Searching: Linear Search O(n), Binary Search O(log n)\n" +
            "• Graph Algorithms: BFS, DFS, Dijkstra (shortest path), Bellman-Ford, A*\n" +
            "• Dynamic Programming: Memoization and tabulation for overlapping subproblems (Fibonacci, Knapsack)\n" +
            "• Greedy Algorithms: Makes locally optimal choices (Huffman coding, Prim's MST)\n" +
            "• Divide & Conquer: Splits problems (Merge Sort, Binary Search)\n" +
            "• Backtracking: Explores all possibilities (N-Queens, Sudoku)\n\n" +
            "📌 Big-O Notation — measures time/space complexity:\n" +
            "O(1) < O(log n) < O(n) < O(n log n) < O(n²) < O(2ⁿ) < O(n!)");

        // ════════════════════════════════════════════════════════════════════
        //  NETWORKING
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is computer network",
            "🌐 Computer Networks\n\n" +
            "A computer network is a collection of interconnected devices that can share resources and information.\n\n" +
            "✅ Network Types:\n" +
            "• LAN (Local Area Network) — within a building\n" +
            "• WAN (Wide Area Network) — cities/countries (Internet is the largest WAN)\n" +
            "• MAN (Metropolitan Area Network) — city-wide\n" +
            "• PAN (Personal Area Network) — Bluetooth devices\n\n" +
            "✅ OSI Model (7 Layers):\n" +
            "7. Application — HTTP, FTP, DNS, SMTP\n" +
            "6. Presentation — encryption, compression\n" +
            "5. Session — session management\n" +
            "4. Transport — TCP, UDP\n" +
            "3. Network — IP, routing\n" +
            "2. Data Link — MAC address, Ethernet\n" +
            "1. Physical — cables, signals\n\n" +
            "✅ TCP vs UDP:\n" +
            "• TCP — reliable, ordered delivery (web, email)\n" +
            "• UDP — fast, unreliable (gaming, streaming, DNS)\n\n" +
            "✅ Key Protocols:\n" +
            "HTTP, HTTPS, FTP, SMTP, DNS, DHCP, SSL/TLS, SSH, WebSocket");

        KB.put("what is http",
            "🌐 HTTP & HTTPS\n\n" +
            "• HTTP (HyperText Transfer Protocol) — the foundation of data communication on the Web\n" +
            "• HTTPS = HTTP + TLS/SSL encryption\n\n" +
            "✅ HTTP Methods:\n" +
            "• GET — retrieve a resource\n" +
            "• POST — send data to a server\n" +
            "• PUT — update a resource\n" +
            "• DELETE — remove a resource\n" +
            "• PATCH — partial update\n\n" +
            "✅ HTTP Status Codes:\n" +
            "• 200 OK, 201 Created\n" +
            "• 301 Redirect, 304 Not Modified\n" +
            "• 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found\n" +
            "• 500 Internal Server Error, 503 Service Unavailable\n\n" +
            "📌 HTTP/2 multiplexes requests. HTTP/3 uses QUIC (UDP-based) for speed.");

        KB.put("what is dns",
            "🔍 DNS (Domain Name System)\n\n" +
            "DNS is the phone book of the internet — it translates human-readable domain names (google.com) into IP addresses (142.250.80.46).\n\n" +
            "✅ How DNS resolution works:\n" +
            "1. Browser checks local cache\n" +
            "2. Queries OS resolver\n" +
            "3. Recursive resolver contacts Root Nameserver\n" +
            "4. Root directs to TLD Nameserver (.com, .org)\n" +
            "5. TLD directs to Authoritative Nameserver\n" +
            "6. IP address returned and cached\n\n" +
            "📌 DNS Record Types:\n" +
            "• A — IPv4 address\n" +
            "• AAAA — IPv6 address\n" +
            "• CNAME — alias\n" +
            "• MX — mail server\n" +
            "• TXT — verification strings");

        // ════════════════════════════════════════════════════════════════════
        //  OPERATING SYSTEMS
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is operating system",
            "💻 Operating System (OS)\n\n" +
            "An OS is system software that manages hardware resources and provides services to applications.\n\n" +
            "✅ Core OS Functions:\n" +
            "• Process Management — CPU scheduling (FCFS, Round Robin, SJF, Priority)\n" +
            "• Memory Management — RAM allocation, virtual memory, paging, segmentation\n" +
            "• File System Management — organizing files (FAT32, NTFS, ext4)\n" +
            "• Device Management — I/O drivers and interrupts\n" +
            "• Security & Access Control\n\n" +
            "✅ Types of OS:\n" +
            "• Batch, Time-Sharing, Distributed, Real-Time (RTOS), Mobile\n\n" +
            "📌 Examples:\n" +
            "• Desktop: Windows, macOS, Linux (Ubuntu, Fedora)\n" +
            "• Mobile: Android (Linux kernel), iOS (XNU kernel)\n" +
            "• Server: Ubuntu Server, CentOS, Windows Server\n" +
            "• Embedded/RTOS: FreeRTOS, VxWorks\n\n" +
            "📌 Key Concepts: Deadlock, Semaphore, Mutex, Context Switch, Thread, Process");

        // ════════════════════════════════════════════════════════════════════
        //  DATABASES
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is database",
            "🗄️ Databases\n\n" +
            "A database is an organized collection of structured information or data stored electronically.\n\n" +
            "✅ Types:\n" +
            "• Relational (RDBMS) — tables, SQL (MySQL, PostgreSQL, SQLite, Oracle)\n" +
            "• Document Store — JSON documents (MongoDB, CouchDB)\n" +
            "• Key-Value Store — simple pairs (Redis, DynamoDB)\n" +
            "• Column Store — columnar data (Cassandra, HBase)\n" +
            "• Graph DB — relationships as edges (Neo4j)\n" +
            "• Time-Series DB — sensor/metric data (InfluxDB, TimescaleDB)\n\n" +
            "✅ RDBMS Core Concepts:\n" +
            "• ACID: Atomicity, Consistency, Isolation, Durability\n" +
            "• Normalization — reducing data redundancy (1NF, 2NF, 3NF, BCNF)\n" +
            "• Indexes — speeds up SELECT queries\n" +
            "• Transactions — group of SQL statements executed atomically\n" +
            "• Foreign Key, Primary Key, Joins (INNER, LEFT, RIGHT, FULL)\n\n" +
            "✅ NoSQL Advantages:\n" +
            "• Flexible schema, horizontal scaling, handles big unstructured data\n\n" +
            "📌 CAP Theorem: A distributed system can only guarantee Consistency, Availability, and Partition Tolerance — pick any two.");

        KB.put("what is mongodb",
            "🍃 MongoDB\n\n" +
            "MongoDB is a NoSQL, document-oriented database that stores data as flexible JSON-like BSON documents.\n\n" +
            "✅ Key Features:\n" +
            "• Schema-less — each document can have different fields\n" +
            "• Horizontal scaling (sharding)\n" +
            "• Rich query language\n" +
            "• Aggregation pipeline\n" +
            "• Replication sets for high availability\n\n" +
            "📌 Common commands:\n" +
            "  db.users.find({ age: { $gt: 18 } })\n" +
            "  db.users.insertOne({ name: 'Alice', age: 25 })\n" +
            "  db.users.updateOne({ name: 'Alice' }, { $set: { age: 26 } })\n" +
            "  db.users.deleteOne({ name: 'Alice' })");

        // ════════════════════════════════════════════════════════════════════
        //  CLOUD COMPUTING
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is cloud computing",
            "☁️ Cloud Computing\n\n" +
            "Cloud computing delivers computing services (servers, storage, databases, networking, software, AI) over the internet ('the cloud') on-demand.\n\n" +
            "✅ Service Models:\n" +
            "• IaaS (Infrastructure as a Service) — virtual machines, storage (AWS EC2, Google Compute)\n" +
            "• PaaS (Platform as a Service) — deployment platforms (Heroku, App Engine, Azure App Service)\n" +
            "• SaaS (Software as a Service) — fully managed apps (Gmail, Dropbox, Zoom, Salesforce)\n" +
            "• FaaS (Function as a Service) — serverless (AWS Lambda, Google Cloud Functions)\n\n" +
            "✅ Deployment Models:\n" +
            "• Public Cloud, Private Cloud, Hybrid Cloud, Multi-Cloud\n\n" +
            "📌 Major Providers:\n" +
            "• AWS (Amazon Web Services) — market leader\n" +
            "• Microsoft Azure\n" +
            "• Google Cloud Platform (GCP)\n" +
            "• IBM Cloud, Oracle Cloud, Alibaba Cloud\n\n" +
            "📌 Benefits: Scalability, cost efficiency, global reach, disaster recovery, managed security");

        KB.put("what is docker",
            "🐳 Docker\n\n" +
            "Docker is an open-source platform for containerization — packaging applications and their dependencies into portable, isolated containers.\n\n" +
            "✅ Key Concepts:\n" +
            "• Image — blueprint for a container (like a class)\n" +
            "• Container — running instance of an image (like an object)\n" +
            "• Dockerfile — instructions to build an image\n" +
            "• Docker Hub — registry to share images\n" +
            "• Docker Compose — manage multi-container applications\n\n" +
            "📌 Why Docker?\n" +
            "• 'Works on my machine' problem solved\n" +
            "• Faster deployment, consistent environments\n" +
            "• Much lighter than virtual machines\n\n" +
            "📝 Quick start:\n" +
            "  docker pull python:3.11\n" +
            "  docker run -it python:3.11 bash");

        KB.put("what is kubernetes",
            "⚓ Kubernetes (K8s)\n\n" +
            "Kubernetes is an open-source container orchestration platform that automates deployment, scaling, and management of containerized applications.\n\n" +
            "✅ Key Concepts:\n" +
            "• Pod — smallest deployable unit (one or more containers)\n" +
            "• Node — a worker machine\n" +
            "• Cluster — collection of nodes\n" +
            "• Deployment — manages desired state of pods\n" +
            "• Service — stable network endpoint for pods\n" +
            "• Ingress — external HTTP routing\n" +
            "• ConfigMap & Secret — configuration management\n\n" +
            "📌 Managed K8s Services: Amazon EKS, Google GKE, Azure AKS");

        // ════════════════════════════════════════════════════════════════════
        //  CYBERSECURITY
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is cybersecurity",
            "🔐 Cybersecurity\n\n" +
            "Cybersecurity is the practice of protecting systems, networks, and programs from digital attacks, damage, or unauthorized access.\n\n" +
            "✅ Core Domains:\n" +
            "• Network Security — firewalls, IDS/IPS, VPN\n" +
            "• Application Security — secure coding, OWASP Top 10\n" +
            "• Cryptography — encryption (AES, RSA, ECC), hashing (SHA-256, bcrypt)\n" +
            "• Identity & Access Management (IAM) — MFA, RBAC\n" +
            "• Cloud Security\n" +
            "• Incident Response & Forensics\n\n" +
            "✅ Common Attack Types:\n" +
            "• Phishing — social engineering via fake emails/sites\n" +
            "• SQL Injection — inserting malicious SQL into input fields\n" +
            "• XSS (Cross-Site Scripting) — injecting scripts into web pages\n" +
            "• CSRF — Cross-Site Request Forgery\n" +
            "• DDoS — Distributed Denial of Service attack\n" +
            "• Ransomware — encrypts victim's data for ransom\n" +
            "• Man-in-the-Middle (MITM) — intercepting communication\n" +
            "• Buffer Overflow — overwriting memory\n\n" +
            "🛡️ Defense: HTTPS, input validation, rate limiting, WAF, security audits, patching");

        KB.put("what is encryption",
            "🔒 Encryption\n\n" +
            "Encryption converts plaintext data into unreadable ciphertext using an algorithm and key.\n\n" +
            "✅ Types:\n" +
            "• Symmetric Encryption — same key to encrypt & decrypt (AES, DES, Blowfish)\n" +
            "  Fast, but key distribution is challenging.\n" +
            "• Asymmetric Encryption — public key encrypts, private key decrypts (RSA, ECC)\n" +
            "  Used in HTTPS, digital signatures, SSL/TLS\n\n" +
            "✅ Hashing (one-way, not reversible):\n" +
            "• MD5 (broken), SHA-1 (weak), SHA-256, SHA-3, bcrypt, Argon2\n" +
            "• Used for passwords, file integrity verification\n\n" +
            "📌 HTTPS uses TLS which combines asymmetric (key exchange) + symmetric (data transfer) encryption.");

        // ════════════════════════════════════════════════════════════════════
        //  WEB DEVELOPMENT
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is web development",
            "🌐 Web Development\n\n" +
            "Web development is the process of creating websites and web applications.\n\n" +
            "✅ Three Layers:\n" +
            "• Frontend (Client-side): What users see and interact with\n" +
            "  Technologies: HTML, CSS, JavaScript, React, Vue, Angular, Next.js\n\n" +
            "• Backend (Server-side): Business logic, APIs, databases\n" +
            "  Technologies: Node.js/Express, Django (Python), Spring Boot (Java), Laravel (PHP), ASP.NET\n\n" +
            "• Database: Data persistence\n" +
            "  MySQL, PostgreSQL, MongoDB, Firebase, Redis\n\n" +
            "✅ Modern Stack Examples:\n" +
            "• MERN: MongoDB + Express + React + Node.js\n" +
            "• MEAN: MongoDB + Express + Angular + Node.js\n" +
            "• LAMP: Linux + Apache + MySQL + PHP\n" +
            "• JAMstack: JavaScript + APIs + Markup\n\n" +
            "📌 Key Concepts: REST API, GraphQL, WebSocket, CDN, SSR, SSG, SPA");

        KB.put("what is react",
            "⚛️ React\n\n" +
            "React is an open-source JavaScript library for building user interfaces, developed by Meta (Facebook).\n\n" +
            "✅ Key Concepts:\n" +
            "• Component-based architecture — UI is divided into reusable components\n" +
            "• JSX — JavaScript + HTML in same file\n" +
            "• Virtual DOM — React diffs and updates only changed parts efficiently\n" +
            "• State & Props — managing data within and between components\n" +
            "• Hooks: useState, useEffect, useContext, useRef, useMemo\n" +
            "• React Router — navigation between pages\n" +
            "• Redux / Zustand / Context API — global state management\n\n" +
            "📌 Next.js = React + SSR + file-based routing (full-stack framework)");

        KB.put("what is rest api",
            "🔗 REST API\n\n" +
            "REST (Representational State Transfer) is an architectural style for building web services that are simple, scalable, and stateless.\n\n" +
            "✅ REST Principles:\n" +
            "• Stateless — each request is self-contained\n" +
            "• Client-Server architecture\n" +
            "• Cacheable responses\n" +
            "• Uniform Interface — standard HTTP methods\n\n" +
            "✅ HTTP Methods in REST:\n" +
            "• GET /users → list users\n" +
            "• GET /users/1 → get user 1\n" +
            "• POST /users → create new user\n" +
            "• PUT /users/1 → update user 1\n" +
            "• DELETE /users/1 → delete user 1\n\n" +
            "📌 Response Format: Usually JSON\n" +
            "📌 Tools: Postman, curl, Insomnia for testing APIs");

        // ════════════════════════════════════════════════════════════════════
        //  SOFTWARE ENGINEERING
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is oop",
            "🏗️ OOP (Object-Oriented Programming)\n\n" +
            "OOP is a programming paradigm based on the concept of 'objects' that contain data (attributes) and behavior (methods).\n\n" +
            "✅ Four Pillars:\n" +
            "• Encapsulation — bundling data and methods; hiding internal state using access modifiers (private, protected, public)\n" +
            "• Abstraction — hiding complex implementation, exposing only necessary functionality (abstract classes, interfaces)\n" +
            "• Inheritance — a child class inherits properties and methods from a parent class\n" +
            "• Polymorphism — same method name behaves differently (overloading & overriding)\n\n" +
            "📌 OOP Languages: Java, C++, Python, Kotlin, Swift, C#, Ruby\n\n" +
            "📌 Key terms: Class, Object, Constructor, Interface, Abstract Class, Method, Attribute");

        KB.put("what is design pattern",
            "🎨 Design Patterns\n\n" +
            "Design patterns are reusable solutions to commonly occurring software design problems.\n\n" +
            "✅ Creational Patterns (Object creation):\n" +
            "• Singleton — only one instance of a class\n" +
            "• Factory Method — creates objects without specifying exact class\n" +
            "• Builder — builds complex objects step by step\n" +
            "• Prototype — clone existing objects\n\n" +
            "✅ Structural Patterns (Object composition):\n" +
            "• Adapter — makes incompatible interfaces work together\n" +
            "• Decorator — adds behavior to objects dynamically\n" +
            "• Facade — simplified interface to complex subsystem\n" +
            "• Proxy — wraps object to control access\n\n" +
            "✅ Behavioral Patterns (Object interaction):\n" +
            "• Observer — notifies multiple objects of state changes (Event Listeners)\n" +
            "• Strategy — interchangeable algorithms\n" +
            "• Command — encapsulates a request as an object\n" +
            "• Iterator — traverse collection without exposing internals\n" +
            "• MVVM, MVP, MVC — UI architecture patterns");

        KB.put("what is agile",
            "🔄 Agile Methodology\n\n" +
            "Agile is an iterative software development approach that delivers working software in short cycles called Sprints (1-4 weeks).\n\n" +
            "✅ Agile Principles (from Agile Manifesto):\n" +
            "• Individuals and interactions over processes and tools\n" +
            "• Working software over comprehensive documentation\n" +
            "• Customer collaboration over contract negotiation\n" +
            "• Responding to change over following a plan\n\n" +
            "✅ Agile Frameworks:\n" +
            "• Scrum — sprints, daily standups, backlog, retrospectives\n" +
            "• Kanban — visual workflow board (To Do, In Progress, Done)\n" +
            "• SAFe — Scaled Agile Framework for enterprise\n" +
            "• XP (Extreme Programming) — TDD, pair programming\n\n" +
            "📌 Key Roles in Scrum: Product Owner, Scrum Master, Development Team");

        KB.put("what is git",
            "🌿 Git\n\n" +
            "Git is a distributed version control system (VCS) for tracking changes in source code during development.\n\n" +
            "✅ Core Git Commands:\n" +
            "  git init — initialize a repo\n" +
            "  git clone <url> — copy remote repo\n" +
            "  git add . — stage changes\n" +
            "  git commit -m 'message' — save snapshot\n" +
            "  git push — upload to remote\n" +
            "  git pull — download & merge changes\n" +
            "  git branch — list/create branches\n" +
            "  git merge <branch> — merge branches\n" +
            "  git rebase — integrate changes cleanly\n" +
            "  git log — view commit history\n" +
            "  git stash — save uncommitted work temporarily\n\n" +
            "📌 Platforms: GitHub, GitLab, Bitbucket\n" +
            "📌 Branching Model: Gitflow (main → develop → feature branches)");

        KB.put("what is devops",
            "🔧 DevOps\n\n" +
            "DevOps is a culture and set of practices that bridges software Development and IT Operations to shorten the development lifecycle and deliver high-quality software continuously.\n\n" +
            "✅ Key Practices:\n" +
            "• CI/CD — Continuous Integration & Continuous Delivery/Deployment\n" +
            "• Infrastructure as Code (IaC) — Terraform, Ansible\n" +
            "• Containerization — Docker\n" +
            "• Container Orchestration — Kubernetes\n" +
            "• Monitoring & Logging — Prometheus, Grafana, ELK Stack\n" +
            "• Configuration Management — Ansible, Puppet, Chef\n\n" +
            "📌 CI/CD Tools: Jenkins, GitHub Actions, GitLab CI, CircleCI, ArgoCD");

        // ════════════════════════════════════════════════════════════════════
        //  COMPUTER SCIENCE FUNDAMENTALS
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is recursion",
            "🔁 Recursion\n\n" +
            "Recursion is when a function calls itself to solve a smaller version of the same problem until a base case is reached.\n\n" +
            "✅ Key Elements:\n" +
            "• Base Case — condition to stop recursion\n" +
            "• Recursive Case — calls itself with a simpler input\n\n" +
            "📝 Example — Factorial:\n" +
            "  int factorial(int n) {\n" +
            "    if (n == 0) return 1;  // base case\n" +
            "    return n * factorial(n - 1);  // recursive call\n" +
            "  }\n" +
            "  factorial(5) = 5 * 4 * 3 * 2 * 1 = 120\n\n" +
            "📌 Use Cases: Tree traversal, DFS/BFS, Divide & Conquer, Dynamic Programming\n" +
            "⚠️ Too deep recursion → StackOverflowError. Use iterative approach or tail recursion.");

        KB.put("what is fibonacci",
            "🌀 Fibonacci Sequence\n\n" +
            "Fibonacci is a sequence where each number is the sum of the two preceding numbers:\n" +
            "0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144...\n\n" +
            "Formula: F(n) = F(n-1) + F(n-2), with F(0)=0, F(1)=1\n\n" +
            "📝 Recursive (O(2ⁿ) time):\n" +
            "  int fib(int n) {\n" +
            "    if (n <= 1) return n;\n" +
            "    return fib(n-1) + fib(n-2);\n" +
            "  }\n\n" +
            "📝 Dynamic Programming (O(n) time):\n" +
            "  int fib(int n) {\n" +
            "    int[] dp = new int[n+1];\n" +
            "    dp[0]=0; dp[1]=1;\n" +
            "    for(int i=2; i<=n; i++) dp[i] = dp[i-1]+dp[i-2];\n" +
            "    return dp[n];\n" +
            "  }");

        KB.put("what is api",
            "🔌 API (Application Programming Interface)\n\n" +
            "An API is a set of rules and protocols that allows different software applications to communicate with each other.\n\n" +
            "✅ Types of APIs:\n" +
            "• REST API — uses HTTP, returns JSON (most common)\n" +
            "• GraphQL — query-based, client specifies exact data needed\n" +
            "• SOAP — XML-based, strict protocol\n" +
            "• gRPC — Google's high-performance RPC framework\n" +
            "• WebSocket API — real-time bidirectional communication\n\n" +
            "📌 Real Examples:\n" +
            "• Twitter/X API — post tweets, fetch timelines\n" +
            "• Google Maps API — embed maps\n" +
            "• OpenAI API — use ChatGPT in your app\n" +
            "• Firebase API — auth, database, storage");

        KB.put("what is compiler",
            "⚙️ Compiler vs Interpreter\n\n" +
            "• Compiler: Translates entire source code to machine/bytecode before execution.\n" +
            "  Examples: GCC (C/C++), javac (Java), Kotlin compiler\n" +
            "  Pros: Faster execution; catches errors at compile time\n\n" +
            "• Interpreter: Executes code line by line at runtime.\n" +
            "  Examples: Python, Ruby, JavaScript (V8 JIT compiles it)\n" +
            "  Pros: Easier debugging and interactive use\n\n" +
            "• JIT (Just-In-Time) Compiler: Hybrid approach — compiles at runtime\n" +
            "  Used by: JVM (Java), .NET CLR, V8 (JavaScript)\n\n" +
            "Compilation Process (C):\n" +
            "Source Code → Preprocessor → Compiler → Assembler → Linker → Executable");

        KB.put("what is virtualization",
            "🖥️ Virtualization\n\n" +
            "Virtualization creates a virtual (software-based) version of a resource including hardware, OS, network, or storage.\n\n" +
            "✅ Types:\n" +
            "• Hardware/Server Virtualization — multiple VMs on one physical server (VMware, VirtualBox, KVM)\n" +
            "• OS-Level Virtualization — containers (Docker, LXC)\n" +
            "• Network Virtualization — SDN (Software Defined Networking)\n" +
            "• Storage Virtualization — SAN, NAS\n\n" +
            "📌 VM vs Container:\n" +
            "• VM — full OS, heavyweight (GBs), slower startup\n" +
            "• Container — shares host OS kernel, lightweight (MBs), fast startup\n" +
            "• Hypervisor types: Type 1 (bare-metal: VMware ESXi), Type 2 (hosted: VirtualBox)");

        KB.put("what is microservices",
            "🔧 Microservices Architecture\n\n" +
            "Microservices is an architectural style that structures an application as a collection of small, independent services that communicate via APIs.\n\n" +
            "✅ Characteristics:\n" +
            "• Each service has a single responsibility\n" +
            "• Services are independently deployable\n" +
            "• Services communicate via REST, gRPC, or message queues (Kafka, RabbitMQ)\n" +
            "• Each service can use its own database\n\n" +
            "✅ Benefits vs Monolith:\n" +
            "• Independent scaling\n" +
            "• Technology diversity\n" +
            "• Fault isolation\n" +
            "• Faster deployments\n\n" +
            "✅ Challenges:\n" +
            "• Distributed system complexity\n" +
            "• Network latency, service discovery\n" +
            "• Distributed tracing (Jaeger, Zipkin)\n\n" +
            "📌 Companies using microservices: Netflix, Amazon, Uber, Airbnb");

        KB.put("what is big data",
            "📊 Big Data\n\n" +
            "Big Data refers to extremely large datasets that are difficult to process using traditional tools.\n\n" +
            "✅ The 5 Vs of Big Data:\n" +
            "• Volume — massive amounts of data (petabytes)\n" +
            "• Velocity — data generated at high speed (real-time streams)\n" +
            "• Variety — structured, semi-structured, unstructured data\n" +
            "• Veracity — data quality and accuracy\n" +
            "• Value — usefulness of data\n\n" +
            "✅ Big Data Technologies:\n" +
            "• Hadoop — distributed storage (HDFS) + processing (MapReduce)\n" +
            "• Apache Spark — in-memory distributed computing (100x faster than Hadoop)\n" +
            "• Kafka — distributed message streaming\n" +
            "• Hive — SQL-like interface over Hadoop\n" +
            "• Elasticsearch — full-text search at scale\n\n" +
            "📌 Use Cases: Social media analytics, recommendation engines, fraud detection, genomics");

        KB.put("what is metaverse",
            "🌐 Metaverse\n\n" +
            "The Metaverse is a collective virtual shared space, created by the convergence of physical and digital reality, enabling immersive 3D experiences.\n\n" +
            "✅ Key Technologies:\n" +
            "• VR (Virtual Reality) — fully immersive digital world (Meta Quest, Valve Index)\n" +
            "• AR (Augmented Reality) — overlaying digital content on real world (HoloLens, Apple Vision Pro)\n" +
            "• XR (Extended Reality) — umbrella for VR+AR+MR\n" +
            "• Blockchain — decentralized economies, NFTs for digital ownership\n" +
            "• Avatar — digital representation of users\n\n" +
            "📌 Metaverse Platforms: Decentraland, The Sandbox, Roblox, Fortnite, VRChat\n" +
            "📌 Companies investing: Meta, Microsoft, Epic Games, NVIDIA");

        KB.put("what is quantum computing",
            "⚛️ Quantum Computing\n\n" +
            "Quantum computing uses quantum-mechanical phenomena (superposition, entanglement, interference) to perform computations that classical computers cannot do efficiently.\n\n" +
            "✅ Key Concepts:\n" +
            "• Qubit — quantum bit, can be 0, 1, or both simultaneously (superposition)\n" +
            "• Superposition — qubit exists in multiple states at once until measured\n" +
            "• Entanglement — qubits correlated such that the state of one instantly affects another\n" +
            "• Quantum Gate — operation on qubits (like logic gates in classical computing)\n" +
            "• Quantum Supremacy — quantum computer solves a problem faster than any classical computer\n\n" +
            "📌 Quantum Advantage Use Cases:\n" +
            "• Breaking current encryption (threat to RSA)\n" +
            "• Drug discovery and molecular simulation\n" +
            "• Optimization problems\n" +
            "• Cryptography (Quantum Key Distribution)\n\n" +
            "📌 Companies: IBM Quantum, Google (Sycamore), IonQ, Microsoft, D-Wave");

        KB.put("what is augmented reality",
            "📱 AR (Augmented Reality)\n\n" +
            "AR overlays digital content (images, 3D objects, text) onto the real world through a camera or AR glasses.\n\n" +
            "✅ Examples:\n" +
            "• Pokémon Go — AR gaming\n" +
            "• Google Maps Live View — AR navigation\n" +
            "• IKEA Place — see furniture in your room\n" +
            "• Snapchat/Instagram filters\n" +
            "• Surgical AR overlays in medicine\n\n" +
            "📌 Development: ARCore (Android/Google), ARKit (iOS/Apple), Unity with AR Foundation");

        KB.put("what is virtual reality",
            "🥽 VR (Virtual Reality)\n\n" +
            "VR immerses users in a fully computer-generated 3D environment, blocking out the real world.\n\n" +
            "✅ How it works:\n" +
            "• Head-Mounted Display (HMD) — headset with screens for each eye\n" +
            "• Head tracking — adjusts view as you move\n" +
            "• Hand controllers or gloves for interaction\n\n" +
            "📌 Hardware: Meta Quest 3, PlayStation VR2, HTC Vive, Valve Index, Apple Vision Pro\n\n" +
            "📌 Applications:\n" +
            "• Gaming & Entertainment\n" +
            "• VR Training (military, surgery, flight simulation)\n" +
            "• Virtual tourism\n" +
            "• Architecture & design visualization\n" +
            "• VR therapy (phobia treatment, PTSD)");

        KB.put("what is computer vision",
            "👁️ Computer Vision\n\n" +
            "Computer Vision is the field of AI that trains computers to interpret and understand visual information from images and videos.\n\n" +
            "✅ Core Tasks:\n" +
            "• Image Classification — what object is in the image\n" +
            "• Object Detection — where are objects in the image (YOLO, SSD)\n" +
            "• Image Segmentation — pixel-level labeling\n" +
            "• Face Recognition\n" +
            "• Optical Character Recognition (OCR)\n" +
            "• Pose Estimation — body keypoints detection\n\n" +
            "📌 Libraries: OpenCV, TensorFlow, PyTorch, YOLO\n\n" +
            "📌 Applications: Self-driving cars, medical imaging, surveillance, AR, photo apps");

        KB.put("what is android development",
            "🤖 Android Development\n\n" +
            "Android is the world's most popular mobile OS, running on billions of devices and developed by Google.\n\n" +
            "✅ Core Technologies:\n" +
            "• Language: Kotlin (preferred) or Java\n" +
            "• UI: XML layouts or Jetpack Compose (declarative UI)\n" +
            "• Architecture: MVVM (Model-View-ViewModel) with LiveData/StateFlow\n" +
            "• Jetpack Libraries: ViewModel, Room, Navigation, DataStore, WorkManager, Hilt\n" +
            "• Async: Coroutines, Flow\n" +
            "• Networking: Retrofit + OkHttp\n" +
            "• Dependency Injection: Hilt (Dagger)\n\n" +
            "✅ Android App Components:\n" +
            "• Activity — a screen\n" +
            "• Fragment — reusable UI portion\n" +
            "• Service — background task\n" +
            "• BroadcastReceiver — system event listener\n" +
            "• ContentProvider — data sharing between apps\n\n" +
            "📌 Distribution: Google Play Store\n" +
            "📌 Tools: Android Studio, Gradle, ADB, Logcat, Firebase");

        // ════════════════════════════════════════════════════════════════════
        //  MISC / GENERAL TECH
        // ════════════════════════════════════════════════════════════════════

        KB.put("what is open source",
            "🌍 Open Source Software\n\n" +
            "Open source software is software with publicly available source code that anyone can inspect, modify, and distribute.\n\n" +
            "📌 Famous Open Source Projects:\n" +
            "• Linux kernel — powers Android, servers, supercomputers\n" +
            "• Android, Firefox, VLC, LibreOffice\n" +
            "• TensorFlow, PyTorch (AI)\n" +
            "• VS Code (code editor)\n" +
            "• Apache, Nginx (web servers)\n" +
            "• MySQL, PostgreSQL (databases)\n" +
            "• Kubernetes, Docker (cloud infrastructure)\n\n" +
            "📌 Licenses: MIT, Apache 2.0, GPL, BSD, MPL\n" +
            "📌 Host platforms: GitHub, GitLab, SourceForge");

        KB.put("what is operating system kernel",
            "🧠 OS Kernel\n\n" +
            "The kernel is the core component of an OS that manages communication between hardware and software.\n\n" +
            "✅ Kernel Responsibilities:\n" +
            "• Process scheduling and management\n" +
            "• Memory management\n" +
            "• Device drivers\n" +
            "• System calls (interface between user apps and hardware)\n" +
            "• File system management\n\n" +
            "✅ Kernel Types:\n" +
            "• Monolithic — all OS services run in kernel space (Linux)\n" +
            "• Microkernel — minimal kernel, services in user space (QNX, Mach)\n" +
            "• Hybrid — blend of both (Windows NT, macOS XNU)");

        KB.put("what is linux",
            "🐧 Linux\n\n" +
            "Linux is a free, open-source Unix-like OS kernel created by Linus Torvalds in 1991.\n\n" +
            "✅ Key Facts:\n" +
            "• Powers 96%+ of world's top 500 supercomputers\n" +
            "• Foundation of Android\n" +
            "• Dominant in cloud & server environments\n" +
            "• Used in embedded systems and IoT devices\n\n" +
            "✅ Popular Distributions:\n" +
            "• Ubuntu — beginner-friendly, GNOME desktop\n" +
            "• Debian — stability-focused\n" +
            "• Fedora — cutting-edge, Red Hat upstream\n" +
            "• CentOS/RHEL — enterprise servers\n" +
            "• Arch Linux — DIY, rolling release\n" +
            "• Kali Linux — cybersecurity and penetration testing\n\n" +
            "📌 Essential Commands:\n" +
            "  ls, cd, pwd, mkdir, rm, cp, mv, cat, grep, chmod, sudo, apt, top, ps, kill, ssh");
    }

    // ─── Public API ─────────────────────────────────────────────────────────────

    @NonNull
    public String getBotReply(@NonNull String input, @NonNull List<Message> history) {
        String msg = input.trim().toLowerCase(Locale.US);
        updateLongTermMemory(msg);

        if (msg.isEmpty()) return "Please type a message.";

        // ── Conversational shortcuts ─────────────────────────────────────────
        if (containsAny(msg, "hi", "hello", "hey")) return greetingReply();
        if (msg.contains("how are you")) return "I am doing great! Ask me anything about programming, AI, blockchain, IoT, or any CS topic. 😊";
        if (msg.contains("your name")) return "I am AI Researcher — your offline knowledge assistant for all things Computer Science! 🤖";
        if (msg.contains("who made you") || msg.contains("who built you")) return "I was built into this Android app as a powerful offline AI knowledge bot. 🤖";
        if (msg.contains("what can you do") || msg.contains("what do you know")) {
            return "I can answer questions on:\n\n" +
                   "• Programming Languages: Python, Java, Kotlin, C, C++, JavaScript, Rust, Go, Swift, PHP, Dart, SQL, R...\n" +
                   "• Blockchain, Cryptocurrency, NFT, DeFi, Smart Contracts\n" +
                   "• IoT, Arduino, Raspberry Pi, MQTT\n" +
                   "• AI, Machine Learning, Deep Learning, NLP, Computer Vision\n" +
                   "• Data Structures & Algorithms\n" +
                   "• Computer Networks, HTTP, DNS, Security\n" +
                   "• Operating Systems, Databases, Cloud, DevOps\n" +
                   "• Web/Android/Software Development\n" +
                   "• Math Expressions: e.g. 4*5*7, (10+5)*3, 100/4";
        }

        if (msg.contains("my name is")) {
            return rememberedName.isEmpty()
                ? "Nice to meet you!"
                : "Nice to meet you, " + rememberedName + "! I will remember your name! 🙂";
        }
        if (msg.contains("what is my name") || msg.contains("who am i")) {
            return rememberedName.isEmpty()
                ? "You have not told me your name yet. Say 'My name is ...'"
                : "Your name is " + rememberedName + "! 😊";
        }
        if (msg.contains("remember this")) {
            rememberedPreference = input.replaceFirst("(?i)remember this", "").trim();
            return rememberedPreference.isEmpty()
                ? "Tell me what I should remember."
                : "Got it! I will remember: " + rememberedPreference + " ✅";
        }
        if (msg.contains("what did i tell you") || msg.contains("what do you remember")) {
            if (!rememberedPreference.isEmpty()) return "You asked me to remember: " + rememberedPreference;
            if (!rememberedName.isEmpty()) return "I remember your name is " + rememberedName + ".";
            return "I don't have anything saved yet. Tell me your name or say 'remember this ...'.";
        }
        if (msg.contains("what did i just say") || msg.contains("repeat my last message")) {
            String prev = extractPreviousUserMessage(history);
            return prev.isEmpty() ? "This is your first message." : "You just said: " + prev;
        }
        if (containsAny(msg, "thanks", "thank you", "thank u")) return "You're welcome! 😊 Feel free to ask anything!";
        if (msg.contains("joke")) return "Why do Java developers wear glasses? \nBecause they don't C#! 😄\n\n(Also: Why do programmers prefer dark mode? Because light attracts bugs! 🐛)";
        if (msg.contains("bye") || msg.contains("goodbye")) return "Goodbye! 👋 Come back anytime for more knowledge!";
        if (msg.contains("motivat")) return "🚀 Every expert was once a beginner. Consistent small steps lead to massive results. Keep coding!";
        if (msg.contains("help")) {
            return "💡 Ask me questions like:\n" +
                   "  • 'What is Python?'\n" +
                   "  • 'What is blockchain?'\n" +
                   "  • 'What is IoT?'\n" +
                   "  • 'What is machine learning?'\n" +
                   "  • 'What is data structure?'\n" +
                   "  • '4 * 5 * 7', '(10+5)*3', '100/4'\n" +
                   "  • 'My name is ...'";
        }

        // ── Knowledge Base lookup (multi-keyword matching) ───────────────────
        String kbResult = lookupKnowledgeBase(msg);
        if (!kbResult.isEmpty()) return kbResult;

        // ── Math expression solver ────────────────────────────────────────────
        String mathResult = trySolveMath(msg);
        if (!mathResult.isEmpty()) return mathResult;

        // ── Keyword-based fallback answers ───────────────────────────────────
        String fallback = keywordFallback(msg);
        if (!fallback.isEmpty()) return fallback;

        lastUserMessage = input.trim();
        return "🤔 Hmm, I'm not sure about that yet. Try asking about:\n" +
               "Programming languages, Blockchain, IoT, AI/ML, Data Structures, Networking, Databases, Cloud, or type a math expression like '4*5*7'.";
    }

    // ─── Knowledge Base Lookup ──────────────────────────────────────────────────

    @NonNull
    private String lookupKnowledgeBase(@NonNull String msg) {
        // Exact key match first
        for (Map.Entry<String, String> entry : KB.entrySet()) {
            if (msg.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        // Partial / flexible matching
        if (containsAny(msg, "python") && containsAny(msg, "what", "explain", "tell", "describe", "about", "?"))
            return KB.get("what is python");
        if (containsAny(msg, "java") && !containsAny(msg, "javascript", "kotlin") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is java");
        if (containsAny(msg, "javascript", "js") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is javascript");
        if (containsAny(msg, "kotlin") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is kotlin");
        if (containsAny(msg, "c++", "cpp") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is c++");
        if (containsAny(msg, " c ", "c programming", "language c") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is c programming");
        if (containsAny(msg, "rust") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is rust");
        if (containsAny(msg, "golang", "go lang", " go ") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is go");
        if (containsAny(msg, "swift") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is swift");
        if (containsAny(msg, "php") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is php");
        if (containsAny(msg, "typescript") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is typescript");
        if (containsAny(msg, "dart") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is dart");
        if (containsAny(msg, "sql") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is sql");
        if (containsAny(msg, "r language", "r programming") && containsAny(msg, "what", "explain", "tell", "about", "?"))
            return KB.get("what is r language");

        // Blockchain & Crypto
        if (containsAny(msg, "blockchain", "block chain"))
            return KB.get("what is blockchain");
        if (containsAny(msg, "cryptocurrency", "crypto currency", "bitcoin", "ethereum"))
            return KB.get("what is cryptocurrency");
        if (containsAny(msg, "smart contract", "solidity"))
            return KB.get("what is smart contract");
        if (containsAny(msg, "nft", "non-fungible", "non fungible"))
            return KB.get("what is nft");
        if (containsAny(msg, "defi", "decentralized finance"))
            return KB.get("what is defi");

        // IoT
        if (containsAny(msg, "iot", "internet of things"))
            return KB.get("what is iot");
        if (containsAny(msg, "mqtt"))
            return KB.get("what is mqtt");
        if (containsAny(msg, "arduino"))
            return KB.get("what is arduino");
        if (containsAny(msg, "raspberry pi", "raspberrypi"))
            return KB.get("what is raspberry pi");

        // AI/ML
        if (containsAny(msg, "artificial intelligence", " ai ") && !containsAny(msg, "airesearcher"))
            return KB.get("what is artificial intelligence");
        if (containsAny(msg, "machine learning", "ml "))
            return KB.get("what is machine learning");
        if (containsAny(msg, "deep learning", "neural network"))
            return KB.get("what is deep learning");
        if (containsAny(msg, "nlp", "natural language processing", "natural language"))
            return KB.get("what is nlp");
        if (containsAny(msg, "data science", "datascience"))
            return KB.get("what is data science");
        if (containsAny(msg, "computer vision", "object detection", "image recognition"))
            return KB.get("what is computer vision");

        // Data Structures & Algorithms
        if (containsAny(msg, "data structure"))
            return KB.get("what is data structure");
        if (containsAny(msg, "algorithm", "sorting", "searching"))
            return KB.get("what is algorithm");
        if (containsAny(msg, "recursion", "recursive"))
            return KB.get("what is recursion");
        if (containsAny(msg, "fibonacci"))
            return KB.get("what is fibonacci");

        // Networking
        if (containsAny(msg, "computer network", "networking", "osi model"))
            return KB.get("what is computer network");
        if (containsAny(msg, "http", "https", "status code"))
            return KB.get("what is http");
        if (containsAny(msg, "dns", "domain name"))
            return KB.get("what is dns");

        // OS
        if (containsAny(msg, "operating system", " os "))
            return KB.get("what is operating system");
        if (containsAny(msg, "linux", "ubuntu", "debian", "kali"))
            return KB.get("what is linux");
        if (containsAny(msg, "kernel"))
            return KB.get("what is operating system kernel");

        // Databases
        if (containsAny(msg, "database", "dbms", "rdbms"))
            return KB.get("what is database");
        if (containsAny(msg, "mongodb", "mongo db", "nosql"))
            return KB.get("what is mongodb");

        // Cloud & DevOps
        if (containsAny(msg, "cloud computing", "cloud service", "aws", "azure", "gcp", "google cloud"))
            return KB.get("what is cloud computing");
        if (containsAny(msg, "docker", "container", "containerization"))
            return KB.get("what is docker");
        if (containsAny(msg, "kubernetes", "k8s"))
            return KB.get("what is kubernetes");
        if (containsAny(msg, "devops", "ci/cd", "cicd"))
            return KB.get("what is devops");

        // Security
        if (containsAny(msg, "cybersecurity", "cyber security", "hacking", "information security"))
            return KB.get("what is cybersecurity");
        if (containsAny(msg, "encryption", "decrypt", "cryptography"))
            return KB.get("what is encryption");

        // Web Dev
        if (containsAny(msg, "web development", "web dev", "frontend", "backend", "full stack"))
            return KB.get("what is web development");
        if (containsAny(msg, "react", "reactjs"))
            return KB.get("what is react");
        if (containsAny(msg, "rest api", "api", "restful"))
            return KB.get("what is rest api");

        // Software Engineering
        if (containsAny(msg, "object oriented", "oop", "encapsulation", "inheritance", "polymorphism", "abstraction"))
            return KB.get("what is oop");
        if (containsAny(msg, "design pattern", "singleton", "factory", "observer"))
            return KB.get("what is design pattern");
        if (containsAny(msg, "agile", "scrum", "kanban", "sprint"))
            return KB.get("what is agile");
        if (containsAny(msg, "git ", "github", "version control", "commit", "branch"))
            return KB.get("what is git");

        // Emerging Tech
        if (containsAny(msg, "quantum computing", "qubit"))
            return KB.get("what is quantum computing");
        if (containsAny(msg, "metaverse", "virtual world", "digital world"))
            return KB.get("what is metaverse");
        if (containsAny(msg, "augmented reality", " ar "))
            return KB.get("what is augmented reality");
        if (containsAny(msg, "virtual reality", " vr "))
            return KB.get("what is virtual reality");
        if (containsAny(msg, "big data", "hadoop", "spark"))
            return KB.get("what is big data");
        if (containsAny(msg, "microservices", "micro service"))
            return KB.get("what is microservices");
        if (containsAny(msg, "compiler", "interpreter", "jit"))
            return KB.get("what is compiler");
        if (containsAny(msg, "virtualization", "virtual machine", "hypervisor"))
            return KB.get("what is virtualization");
        if (containsAny(msg, "open source"))
            return KB.get("what is open source");
        if (containsAny(msg, "android development", "android app", "android studio"))
            return KB.get("what is android development");

        return "";
    }

    // ─── Keyword Fallback (simple answers) ──────────────────────────────────────

    @NonNull
    private String keywordFallback(@NonNull String msg) {
        if (msg.contains("time")) return "⏰ I cannot read the live device time, but your phone clock will show it!";
        if (msg.contains("date")) return "📅 I cannot read today's date live, but check your phone's calendar!";
        if (msg.contains("weather")) return "🌤️ I am offline so I cannot fetch live weather. Check a weather app for the latest forecast!";
        if (msg.contains("news")) return "📰 I'm offline but I can discuss any tech topic you're curious about!";
        if (msg.contains("android")) return KB.get("what is android development");
        if (msg.contains("java")) return KB.get("what is java");
        if (msg.contains("kotlin")) return KB.get("what is kotlin");
        if (msg.contains("python")) return KB.get("what is python");
        if (msg.contains("database")) return KB.get("what is database");
        return "";
    }

    // ─── Multi-Operand Math Solver ───────────────────────────────────────────────
    // Supports expressions like: 4*5*7  | 10+3-2  | (2+3)*4  | 100/4  | 2^10

    @NonNull
    private String trySolveMath(@NonNull String msg) {
        // Remove spaces
        String expr = msg.replaceAll("\\s+", "");

        // Only process if the string looks like a math expression
        if (!expr.matches("[0-9+\\-*/^().%]+")) return "";
        if (expr.isEmpty()) return "";

        try {
            double result = evaluate(expr);
            if (Double.isNaN(result)) return "That doesn't look like a valid math expression.";
            if (Double.isInfinite(result)) return "❌ Cannot divide by zero.";
            // Return clean integer or decimal
            if (result == Math.rint(result) && Math.abs(result) < 1e15) {
                return "🧮 Answer: " + (long) result;
            } else {
                return "🧮 Answer: " + result;
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Recursive descent expression parser supporting:
     *  +, -, *, /, ^ (power), % (modulo), parentheses, unary minus, decimals.
     */
    private double evaluate(@NonNull String expr) {
        return new ExprParser(expr).parse();
    }

    // ─── Memory Helpers ──────────────────────────────────────────────────────────

    private void updateLongTermMemory(@NonNull String msg) {
        Matcher nameMatcher = Pattern.compile("\\bmy name is\\s+([a-zA-Z]+)\\b").matcher(msg);
        if (nameMatcher.find()) {
            rememberedName = capitalize(nameMatcher.group(1));
        }
    }

    @NonNull
    private String extractPreviousUserMessage(@NonNull List<Message> history) {
        for (int i = history.size() - 1; i >= 0; i--) {
            Message m = history.get(i);
            if (m.isUser()) {
                String t = m.getText() == null ? "" : m.getText().trim();
                if (!t.isEmpty() && !t.equalsIgnoreCase(lastUserMessage)) {
                    return t;
                }
            }
        }
        return "";
    }

    @NonNull
    private String greetingReply() {
        String base = "Hello" + (rememberedName.isEmpty() ? "" : " " + rememberedName) + "! 👋\n\n" +
            "I'm AI Researcher — your CS knowledge assistant! Ask me about:\n" +
            "• Programming languages (Python, Java, C++, Kotlin...)\n" +
            "• Blockchain, IoT, AI/ML, Data Structures\n" +
            "• Cloud, Databases, Networking, Security\n" +
            "• Math: try '4*5*7' or '(10+3)*2'\n\n" +
            "What would you like to learn today? 🚀";
        return base;
    }

    // ─── Utility ─────────────────────────────────────────────────────────────────

    private boolean containsAny(@NonNull String msg, @NonNull String... keywords) {
        for (String kw : keywords) {
            if (msg.contains(kw)) return true;
        }
        return false;
    }

    @NonNull
    private String capitalize(@NonNull String s) {
        if (s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase(Locale.US) + s.substring(1).toLowerCase(Locale.US);
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  EXPRESSION PARSER (inner class)
    //  Handles: +, -, *, /, ^, %, parentheses, unary minus, integers, decimals
    // ════════════════════════════════════════════════════════════════════════════

    private static class ExprParser {
        private final String input;
        private int pos = 0;

        ExprParser(String input) {
            this.input = input;
        }

        double parse() {
            double result = parseExpression();
            if (pos < input.length()) throw new RuntimeException("Unexpected char: " + input.charAt(pos));
            return result;
        }

        private double parseExpression() {
            double left = parseTerm();
            while (pos < input.length()) {
                char op = input.charAt(pos);
                if (op == '+') { pos++; left += parseTerm(); }
                else if (op == '-') { pos++; left -= parseTerm(); }
                else break;
            }
            return left;
        }

        private double parseTerm() {
            double left = parsePower();
            while (pos < input.length()) {
                char op = input.charAt(pos);
                if (op == '*') { pos++; left *= parsePower(); }
                else if (op == '/') { pos++; double r = parsePower(); if (r == 0) return Double.POSITIVE_INFINITY; left /= r; }
                else if (op == '%') { pos++; left %= parsePower(); }
                else break;
            }
            return left;
        }

        private double parsePower() {
            double base = parseUnary();
            if (pos < input.length() && input.charAt(pos) == '^') {
                pos++;
                double exp = parsePower(); // right-associative
                return Math.pow(base, exp);
            }
            return base;
        }

        private double parseUnary() {
            if (pos < input.length() && input.charAt(pos) == '-') {
                pos++;
                return -parsePrimary();
            }
            if (pos < input.length() && input.charAt(pos) == '+') {
                pos++;
            }
            return parsePrimary();
        }

        private double parsePrimary() {
            if (pos < input.length() && input.charAt(pos) == '(') {
                pos++;
                double val = parseExpression();
                if (pos < input.length() && input.charAt(pos) == ')') pos++;
                return val;
            }
            return parseNumber();
        }

        private double parseNumber() {
            int start = pos;
            if (pos < input.length() && input.charAt(pos) == '-') pos++;
            while (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.')) {
                pos++;
            }
            if (pos == start) throw new RuntimeException("Expected number at pos " + pos);
            return Double.parseDouble(input.substring(start, pos));
        }
    }
}
