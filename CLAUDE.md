# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Capital is a land claiming and protection plugin for Hytale servers. It's currently in the planning phase with no source code yet implemented.

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

## Project Structure

This is a multi-module Gradle project:
- `api/` - Public API module for other mods to integrate
- `core/` - Main plugin implementation
- `resources/` - UI assets, textures, sounds, localization
- `docs/` - User, admin, and developer documentation

Plugin package: `dev.ewwegg.capital`

## Development Philosophy

Read `.meatware/references/development-philosophy.md` for the full philosophy. Key principles in priority order:

1. **Working beats perfect** - Functional today over flawless eventually
2. **Simple beats flexible** - Solve current problems, not hypothetical ones
3. **Conventional beats clever** - Standard patterns, obvious code
4. **Explicit beats abstract** - Clear duplication beats obscure abstraction
5. **Reversible beats optimal** - Easy to undo beats difficult to change

### AI Agent Instructions

- When scope is uncertain, implement the minimal interpretation
- Match existing project conventions before introducing new patterns
- When requirements are ambiguous, ask before building
- When simplicity conflicts with completeness, state the tradeoff
- Build what's requested, not what might be useful

## Planning Workflow (Meatware)

This project uses a structured planning workflow documented in `.meatware/`:

- **Project**: 1-2 months (4-8 milestones)
- **Milestone**: 1 week (10-20 issues)
- **Issue**: 2-4 hours

### Claude Commands for Planning

- `/meatware:milestone-analysis [project] [milestone]` - Analyze milestone scope
- `/meatware:milestone-decisions [project] [milestone]` - Execute spikes and create ADRs
- `/meatware:milestone-resolution [project] [milestone]` - Decompose issues and distribute work
- `/meatware:milestone-issues [project] [milestone]` - Review and refine issues

### Other Commands

- `/git:commit [message] [--amend]` - Create changelog, stage, and commit with Conventional Commits format
- `/prime:codebase` - Gain general understanding of the codebase

## Commit Convention

Follow Conventional Commits v1.0.0:
- Format: `type(scope): description`
- Types: `feat`, `fix`, `docs`, `style`, `refactor`, `perf`, `test`, `build`, `ci`, `revert`
- Description: imperative mood, lowercase, no period, under 72 characters

## Key Reference Documents

- `.meatware/references/tech-stack.md` - Full technology stack details
- `.meatware/references/development-philosophy.md` - Development principles
- `.meatware/references/planning-workflow.md` - Planning process documentation
- `.meatware/roadmap/001-initial-project-plan/initial-project-plan.md` - Detailed project structure and milestones
- `plan.md` - Repository structure and implementation phases

## Architecture (Planned)

Core systems:
- **ClaimManager** - Central claim management with chunk-based spatial lookup
- **PartyManager** - Player groups with roles (Owner, Officer, Member, Trusted, Visitor)
- **PermissionManager** - Trust levels and flag-based protection
- **ProtectionManager** - Event listeners for block/entity/player protection
- **StorageManager** - JSON file storage (database backends planned for v1.0)

Key patterns:
- Server-side only (Hytale downloads assets to clients automatically)
- ECS-aligned design for game object management
- Event-driven protection with listeners for all interaction types
