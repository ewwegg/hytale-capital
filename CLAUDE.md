# CLAUDE.md

## Project

Capital - land claiming and protection plugin for Hytale servers

**Status**: Planning phase, no source code implemented

## Tech Stack

- **Language**: Java 25 LTS (OpenJDK Temurin 25)
- **Build System**: Gradle 9.2.0 with Kotlin DSL
- **Testing**: JUnit Jupiter 5.10.x
- **Target Platform**: Hytale Server (com.hypixel.hytale:Server)

## Build Commands

```bash
./gradlew shadowJar      # Build plugin JAR
./gradlew runServer      # Build and run test server
./gradlew test           # Run unit tests
./gradlew clean build    # Clean rebuild
```

## Structure

Multi-module Gradle project:
- `api/` - Public API for other mods
- `core/` - Main implementation
- `resources/` - UI assets, textures, sounds, localization
- `docs/` - Documentation
- `.meatware/` - Planning workflow (ADRs, milestones, issues)
- `.claude/` - Claude Code configuration and commands

Package: `dev.ewwegg.capital`

## Philosophy

Principles (priority order):
1. **Working beats perfect** - Functional today over flawless eventually
2. **Simple beats flexible** - Solve current problems, not hypothetical ones
3. **Conventional beats clever** - Standard patterns, obvious code
4. **Explicit beats abstract** - Clear duplication beats obscure abstraction
5. **Reversible beats optimal** - Easy to undo beats difficult to change

AI agent rules:
- Implement minimal interpretation when scope is uncertain
- Match existing conventions before introducing new patterns
- Ask before building when requirements are ambiguous
- State tradeoffs when simplicity conflicts with completeness
- Build what's requested, not what might be useful

Full philosophy: `.meatware/references/development-philosophy.md`

## Planning Workflow

Structure:
- **Project**: 1-2 months (4-8 milestones)
- **Milestone**: 1 week (10-20 issues)
- **Issue**: 2-4 hours

## Commands

Planning:
- `/meatware:milestone-analysis [project] [milestone]` - Analyze milestone scope
- `/meatware:milestone-decisions [project] [milestone]` - Execute spikes, create ADRs
- `/meatware:milestone-resolution [project] [milestone]` - Decompose issues, distribute work
- `/meatware:milestone-issues [project] [milestone]` - Review and refine issues

Other:
- `/git:commit [message] [--amend]` - Create changelog, stage, commit (Conventional Commits)
- `/prime:codebase` - Gain general codebase understanding

## Commits

Conventional Commits v1.0.0:
- Format: `type(scope): description`
- Types: `feat`, `fix`, `docs`, `style`, `refactor`, `perf`, `test`, `build`, `ci`, `revert`
- Description: imperative, lowercase, no period, max 72 chars

## References

- `.meatware/references/tech-stack.md` - Technology stack
- `.meatware/references/development-philosophy.md` - Development principles
- `.meatware/references/planning-workflow.md` - Planning process
- `.meatware/references/writing-spec.md` - Documentation style
- `.meatware/roadmap/001-initial-project-plan/initial-project-plan.md` - Project plan
- `.meatware/adr/` - Architecture Decision Records

## Architecture

Core systems:
- **ClaimManager** - Claim management with chunk-based spatial lookup
- **PartyManager** - Player groups with roles
- **PermissionManager** - Trust levels and flag-based protection
- **ProtectionManager** - Event listeners for protection
- **StorageManager** - JSON file storage (database planned for v1.0)

Patterns:
- Server-side only (Hytale auto-downloads assets to clients)
- ECS-aligned design
- Event-driven protection
