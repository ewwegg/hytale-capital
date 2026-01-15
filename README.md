# Capital

Land claiming and protection plugin for Hytale servers

## Status

**Planning Phase** - No source code implemented yet

Current milestone: [Core Foundation](/.meatware/roadmap/001-initial-project-plan/01-core-foundation/milestone-analysis.md)

## Features

### Planned for v1.0

- **Chunk-based claims** - Claim land to protect from griefing
- **Party system** - Group with friends, share claims
- **Trust levels** - Grant access (build, container, interact, permission)
- **Protection flags** - Customize behavior per claim (PVP, explosions, fire spread, etc.)
- **Claim blocks** - Earn over time, purchase, or receive as bonuses
- **Visualization** - Boundary rendering, world map integration
- **Subclaims** - Subdivisions with different permissions
- **Teleportation** - Home and party homes with warmup/cooldown
- **Activity logging** - Track actions, rollback griefing
- **Economy integration** - Buy/sell claim blocks

## Tech Stack

- **Language**: Java 25 LTS
- **Build**: Gradle 9.2.0 (Kotlin DSL)
- **Testing**: JUnit Jupiter 5.10.x
- **Platform**: Hytale Server

## Installation

Not yet available - plugin is in planning phase

## Documentation

- [User Guide](/.meatware/roadmap/001-initial-project-plan/initial-project-plan.md#user-documentation)
- [Admin Guide](/.meatware/roadmap/001-initial-project-plan/initial-project-plan.md#admin-documentation)
- [Developer API](/.meatware/roadmap/001-initial-project-plan/initial-project-plan.md#developer-documentation)
- [Architecture Decisions](/.meatware/adr/)

## Development

### Build Commands

```bash
./gradlew shadowJar      # Build plugin JAR
./gradlew runServer      # Build and run test server
./gradlew test           # Run unit tests
./gradlew clean build    # Clean rebuild
```

### Philosophy

1. Working beats perfect
2. Simple beats flexible
3. Conventional beats clever
4. Explicit beats abstract
5. Reversible beats optimal

Full details: [Development Philosophy](/.meatware/references/development-philosophy.md)

### Project Structure

```
capital/
├── api/           Public API module
├── core/          Main implementation
├── resources/     UI assets, sounds, localization
├── docs/          Documentation
├── .meatware/     Planning workflow
└── .claude/       Claude Code configuration
```

## Contributing

Not yet accepting contributions - project is in planning phase

When ready:
- Follow [Development Philosophy](/.meatware/references/development-philosophy.md)
- Use [Conventional Commits](https://www.conventionalcommits.org/)
- Reference [Planning Workflow](/.meatware/references/planning-workflow.md)

## License

MIT License - see [LICENSE](/LICENSE)

## Roadmap

### Milestone 1: Core Foundation (Current)
- Gradle multi-module setup
- Plugin entry point
- Claim data structures and storage
- Party system basics
- Protection listeners
- Basic commands

### Milestone 2-4: Core Features
- Trust system
- Protection flags
- Visualization

### Milestone 5-8: Extended Features
- Claim earning
- Expiration
- Teleportation
- Subclaims

### Milestone 9-12: Advanced Features
- Party relations
- Activity logging
- Economy integration
- Database storage

Full roadmap: [Initial Project Plan](/.meatware/roadmap/001-initial-project-plan/initial-project-plan.md)

## Credits

Created by ewwegg

Built with [Claude Code](https://claude.ai/code)
