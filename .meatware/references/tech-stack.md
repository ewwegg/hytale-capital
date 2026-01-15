# Hytale Modding Tech Stack

---

## Core Development Environment

| Technology       | Version    | Purpose                                       |
| ---------------- | ---------- | --------------------------------------------- |
| Java             | 25 LTS     | Primary plugin programming language           |
| OpenJDK/Adoptium | Temurin 25 | Recommended JDK distribution                  |
| IntelliJ IDEA    | 2025.x     | Recommended IDE (Community Edition supported) |
| VS Code          | Latest     | Alternative lightweight IDE option            |

---

## Mod Types

Hytale supports four official modding categories:

| Mod Type                | Language/Format      | Purpose                                                    |
| ----------------------- | -------------------- | ---------------------------------------------------------- |
| **Server Plugins**      | Java (.jar)          | Extend server functionality, custom logic, minigames       |
| **Data Assets (Packs)** | JSON                 | Define blocks, items, NPCs, world generation, behaviors    |
| **Art Assets**          | .blockymodel, .png   | 3D models, textures, sounds, animations                    |
| **Save Files**          | World/Prefab formats | Share complete worlds or prefab structures                 |
| **Bootstrap Plugins**   | Java (.jar)          | Low-level bytecode modifications (advanced, use sparingly) |

---

## Build & Automation

| Technology     | Version | Purpose                                          |
| -------------- | ------- | ------------------------------------------------ |
| Gradle         | 9.2.0   | Build system with Kotlin DSL                     |
| Shadow Plugin  | 8.x     | Fat JAR creation with dependency bundling        |
| GitHub Actions | Latest  | CI/CD pipeline for automated builds and releases |
| JUnit Jupiter  | 5.10.x  | Unit testing framework                           |

### Build Commands

```bash
./gradlew shadowJar      # Build plugin JAR
./gradlew runServer      # Build and run test server
./gradlew test           # Run unit tests
./gradlew clean build    # Clean rebuild
```

---

## Art & Asset Creation

| Technology               | Version  | Purpose                                                        |
| ------------------------ | -------- | -------------------------------------------------------------- |
| Blockbench               | 4.x      | Official 3D modeling and animation tool                        |
| Hytale Blockbench Plugin | Latest   | Export .blockymodel and .blockyanim formats                    |
| Asset Editor             | Built-in | In-game visual editor for Pack settings                        |
| Node Editor              | Built-in | Visual editor for world generation and behaviors (in progress) |

### Supported Art Formats

| Format       | Purpose                       |
| ------------ | ----------------------------- |
| .blockymodel | 3D model files                |
| .blockyanim  | Animation files               |
| .png         | Textures (hand-painted style) |
| .ogg         | Sound effects and music       |
| .bbmodel     | Blockbench project files      |

---

## Configuration & Data

| Technology   | Format | Purpose                                      |
| ------------ | ------ | -------------------------------------------- |
| Manifest     | JSON   | Plugin/Pack metadata and dependencies        |
| Config Files | JSON   | Server and world configuration               |
| Codec System | Java   | Serialization/deserialization of config data |
| Permissions  | JSON   | Player and server permissions system         |

### Key Configuration Files

| File              | Location    | Purpose                           |
| ----------------- | ----------- | --------------------------------- |
| manifest.json     | Plugin root | Plugin metadata and entry point   |
| config.json       | Server root | Server-wide settings              |
| world/config.json | Per-world   | World-specific settings           |
| permissions.json  | Server root | Permission groups and assignments |

---

## Server Infrastructure

| Technology       | Details                     | Purpose                                 |
| ---------------- | --------------------------- | --------------------------------------- |
| HytaleServer.jar | Official server binary      | Game server runtime                     |
| QUIC Protocol    | UDP-based                   | Client-server communication (not TCP)   |
| UDP Port         | 5520 (default)              | Network port for connections            |
| Java Memory      | -Xms4G -Xmx8G (recommended) | JVM heap allocation                     |
| AOT Caching      | HytaleServer.aot            | Faster server startup times             |
| Maven Central    | com.hypixel.hytale:Server   | Server JAR dependency (for development) |

### Server Requirements

| Component    | Minimum         | Recommended                |
| ------------ | --------------- | -------------------------- |
| RAM          | 4 GB            | 6-8 GB                     |
| CPU          | 4 cores @ 4 GHz | Higher single-thread speed |
| Storage      | SSD             | NVMe for best performance  |
| Architecture | x64 or arm64    | 64-bit required            |

---

## Distribution & Community

| Platform         | URL/Details             | Purpose                            |
| ---------------- | ----------------------- | ---------------------------------- |
| CurseForge       | curseforge.com          | Official mod distribution platform |
| Server Discovery | In-game catalogue       | Browse and join servers            |
| GitHub           | Various community repos | Source code and collaboration      |

---

## Plugin Project Structure

```
your-plugin-name/
├── src/main/
│   ├── java/dev/ewwegg/capital/
│   │   └── Capital.java              # Main plugin class
│   └── resources/
│       ├── manifest.json                 # Plugin metadata
│       ├── Common/                       # Shared assets (models, textures)
│       └── Server/                       # Server-side data
├── build.gradle.kts                      # Gradle build configuration
├── settings.gradle.kts                   # Project settings
├── gradle.properties                     # Build properties
└── README.md
```

---

## Pack Project Structure

```
%AppData%/Roaming/Hytale/UserData/Packs/Capital/
├── manifest.json                         # Pack metadata
├── Common/
│   ├── BlockTextures/                    # Block textures
│   ├── Icons/                            # Item/UI icons
│   ├── Models/                           # 3D model files
│   └── Blocks/
│       └── Animations/                   # Block animations
└── Server/
    ├── Item/
    │   ├── Items/                        # Item definitions
    │   └── Category/                     # Creative menu categories
    └── Languages/
        └── en-US/                        # Localization files
```

---

## File Locations

| Content Type  | Path                                                           |
| ------------- | -------------------------------------------------------------- |
| Plugins/Mods  | `%appdata%/Hytale/UserData/Mods/`                              |
| Packs         | `%appdata%/Hytale/UserData/Packs/`                             |
| Early Plugins | `<server>/earlyplugins/`                                       |
| Server Files  | `%appdata%/Hytale/install/release/package/game/latest/Server/` |

---

## Hytale Modding Resources

| Source                    | URL                                                                              | Description                                        |
| ------------------------- | -------------------------------------------------------------------------------- | -------------------------------------------------- |
| **Hytale Server Manual**  | https://support.hytale.com/hc/en-us/articles/45326769420827-Hytale-Server-Manual | Official server documentation from Hypixel Studios |
| **Hytale-Dev**            | https://www.hytale-dev.com                                                       | Community documentation                            |
| **Britakee's Template**   | https://github.com/realBritakee/hytale-template-plugin                           | Ready-to-use plugin template with CI/CD            |
| **Britakee's GitBook**    | https://britakee-studios.gitbook.io/hytale-modding-documentation                 | Comprehensive tested tutorials                     |
| **HytaleDocs**            | https://hytale-docs.com/docs                                                     | Community wiki and API reference                   |
| **HytaleModding.dev**     | https://hytalemodding.dev/en/docs                                                | Community documentation and guides                 |
| **Kaupenjoe**             | YouTube                                                                          | Video tutorial series for Hytale modding           |
| **CurseForge Support**    | https://support.curseforge.com/en/support/solutions/articles/9000273178          | Official CurseForge modding tutorials              |
| **Official Modding Blog** | https://hytale.com/news/2025/11/hytale-modding-strategy-and-status               | Hypixel Studios modding strategy                   |
| **Blockbench**            | https://www.blockbench.net/                                                      | Official 3D modeling tool                          |
| **Blockbench Plugin**     | https://github.com/JannisX11/hytale-blockbench-plugin                            | Hytale model format support                        |

---

## Key Dependencies

### Plugin Development (build.gradle.kts)

```kotlin
dependencies {
    // Hytale API (provided by server)
    compileOnly(files("libs/hytale-server.jar"))

    // Common dependencies (bundled via ShadowJar)
    implementation("com.google.code.gson:gson:2.10.1")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}
```

### Maven Dependency (when available)

```xml
<dependency>
    <groupId>com.hypixel.hytale</groupId>
    <artifactId>Server</artifactId>
</dependency>
```

---

## Architecture Notes

| Concept                  | Description                                                    |
| ------------------------ | -------------------------------------------------------------- |
| Server-Side First        | All mods run on server; clients auto-download assets           |
| ECS Architecture         | Entity Component System for efficient game object management   |
| No Client Mods           | Client remains stable and secure; no client-side modifications |
| Automatic Asset Delivery | Players join modded servers without manual mod installation    |
| Visual Scripting         | Planned (no text-based scripting like Lua)                     |

---

## Coming Soon

| Feature               | Expected    | Description                               |
| --------------------- | ----------- | ----------------------------------------- |
| Server Source Code    | March 2026  | Full server source for deep modifications |
| Official GitBook Docs | In Progress | Comprehensive API documentation           |
| Visual Scripting      | TBD         | Node-based logic without programming      |
| Development Bounties  | Planned     | Community contribution rewards            |

---
