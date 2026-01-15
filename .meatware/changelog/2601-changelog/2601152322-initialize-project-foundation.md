---
file-name: 2601152322-initialize-project-foundation.md
version: 0.0.1
date: 2026-01-15
type: minor
breaking: false
git-comment: feat(foundation): initialize project structure and build system
---

## Added

- Gradle multi-module project structure (api and core subprojects)
- Root build.gradle.kts with Java 25 toolchain, JUnit Jupiter 5.10.3, and common configuration
- API module (api/build.gradle.kts) with compileOnly Hytale Server dependency
- Core module (core/build.gradle.kts) with Shadow plugin 9.0.0, Gson 2.11.0 bundling, and dependency relocation
- Capital.java main plugin class extending JavaPlugin with setup() and shutdown() lifecycle methods
- Plugin descriptor (core/src/main/resources/manifest.json) with metadata and entryPoint
- Gradle wrapper 9.2.1 with wrapper JAR and properties
- Hytale API stub classes (JavaPlugin, JavaPluginInit) in libs/ for compilation
- Package structure: dev.ewwegg.capital.api and dev.ewwegg.capital.core with package-info.java placeholders
- Complete milestone structure (01-12) to roadmap index (.meatware/roadmap/index.yml)
- Hytale-Dev community documentation reference to tech stack

## Changed

- Reorganized CLAUDE.md for improved clarity and conciseness
- Enhanced GitHub workflow commands (issue-add and issue-ready) with updated field handling for Projects V2
- Core build configuration to output capital.jar (version suffix removed for cleaner deployment)

## Removed

- Deleted hytale-modding-resources.md (consolidated into .meatware/references/tech-stack.md)
- Deleted plan.md (replaced by structured roadmap in .meatware/)

## Technical Debt

- Used Gradle 9.2.1 instead of specified 9.2.0 (version 9.2.0 does not exist on distribution server)
- Used Shadow plugin 9.0.0 instead of template's 9.3.1 (prioritized stability for foundation)
- Used JUnit Jupiter 5.10.3 instead of 5.10.0 (latest patch release for bug fixes)
- Used Gson 2.11.0 instead of 2.10.1 (latest stable version with backward compatibility)
- Created Hytale API stub classes (JavaPlugin, JavaPluginInit) as source files instead of using actual Hytale SDK (SDK not yet publicly available)
- Placeholder libs/hytale-server.jar for compileOnly dependency (to be replaced with official SDK when available)
- Used java.util.logging.Logger for logging (may need replacement when Hytale provides official logging API)
