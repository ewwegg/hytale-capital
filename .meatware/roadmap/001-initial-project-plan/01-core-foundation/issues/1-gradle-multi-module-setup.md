---
issue: Set up Gradle multi-module project
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: chore
responsibility: [Tech Lead]
model: claude-opus-4-5
blocked-by: []
---

# Set up Gradle multi-module project

## Context

Foundation for all subsequent development. Establishes the `api` and `core` module structure, build tooling, and dependency configuration per the tech stack specification.

## Dependencies

None - this is the first issue.

## Acceptance Criteria

- [ ] `./gradlew build` succeeds with zero errors
- [ ] `api/` module exists with `build.gradle.kts`
- [ ] `core/` module exists with `build.gradle.kts` and depends on `api`
- [ ] Root `settings.gradle.kts` includes both subprojects
- [ ] Shadow plugin configured in core module for fat JAR output
- [ ] Java 25 toolchain configured
- [ ] JUnit Jupiter 5.10.x configured for testing
- [ ] Package structure `dev.ewwegg.capital.api` and `dev.ewwegg.capital.core` created

## Technical Constraints

- **Tech Stack**: Java 25 LTS (OpenJDK Temurin 25), Gradle 9.2.0 with Kotlin DSL
- **Hytale Dependency**: `com.hypixel.hytale:Server` (placeholder until official release)

## Workflow

1. Create root `build.gradle.kts` with common configuration
2. Create `settings.gradle.kts` with project name and subproject includes
3. Create `api/build.gradle.kts` for interfaces module
4. Create `core/build.gradle.kts` with Shadow plugin and api dependency
5. Create placeholder source directories and package-info files
6. Verify build succeeds

## Out of Scope

- Plugin manifest/descriptor (covered in issue 2)
- Source code beyond package structure

## Resources

- [Tech Stack Reference](../../../../references/tech-stack.md)
- [Britakee's Template Plugin](https://github.com/realBritakee/hytale-template-plugin) — Ready-to-use plugin template with Gradle setup and CI/CD
- [HytaleModding.dev - Plugin Setup](https://hytalemodding.dev/en/docs/guides/plugin) — Community guide for project structure
- [Gradle Multi-Module Documentation](https://docs.gradle.org/current/userguide/multi_project_builds.html)
