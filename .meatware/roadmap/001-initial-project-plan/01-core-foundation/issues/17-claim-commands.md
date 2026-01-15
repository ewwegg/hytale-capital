---
issue: Implement claim commands
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Mid-level Engineer]
model: claude-sonnet-4-5
blocked-by: [5, 7, 13]
---

# Implement claim commands

## Context

Player-facing commands for claim management. Grouped together as they share common patterns and dependencies.

## Dependencies

- Blocked by 5-claim-manager-cache — ClaimManager for claim operations
- Blocked by 7-claim-file-storage — Persistence for claim changes
- Blocked by 13-protection-manager — For overlap/permission validation

## Acceptance Criteria

- [ ] `/claim [name]` claims the chunk player stands in
  - Fails if chunk already claimed
  - Fails if player at claim limit
  - Success creates claim with optional name
- [ ] `/unclaim` unclaims the chunk player stands in
  - Fails if player doesn't own claim
  - Success removes chunk from claim (or entire claim if single chunk)
- [ ] `/claimlist` lists all claims owned by player
  - Shows claim name, chunk count, coordinates
- [ ] `/claiminfo` shows info about claim at player's location
  - Shows owner name, claim name, chunk count
  - Indicates if unclaimed
- [ ] All commands extend `AbstractPlayerCommand`
- [ ] All commands provide appropriate success/error messages
- [ ] Unit tests for command logic

## Technical Constraints

- **Spike Finding**: Use `AbstractPlayerCommand` with typed arguments via `getCommandRegistry()`. See [spike-hytale-command-registration](.meatware/roadmap/001-initial-project-plan/01-core-foundation/spikes/spike-hytale-command-registration.md)
- **Async**: Commands execute async; return `CompletableFuture<Void>`
- **ADR-0002**: Convert player location to ChunkCoordinate. See [ADR-0002](.meatware/adr/0002-chunk-coordinate-handling.md)

## Workflow

1. Create `ClaimCommand.java` extending `AbstractPlayerCommand`
2. Implement claim creation with validation
3. Create `UnclaimCommand.java` with ownership validation
4. Create `ClaimListCommand.java` for listing player's claims
5. Create `ClaimInfoCommand.java` for location-based info
6. Register all commands in plugin setup()
7. Write unit tests for each command

## Out of Scope

- Claim naming/renaming command (use /claim [name] on creation)
- Claim expansion/resize commands

## Resources

- [Spike: Hytale Command Registration](.meatware/roadmap/001-initial-project-plan/01-core-foundation/spikes/spike-hytale-command-registration.md)
- [ADR-0002: Chunk Coordinate Handling](.meatware/adr/0002-chunk-coordinate-handling.md)
- [HytaleDocs - Commands](https://hytale-docs.pages.dev/modding/plugins/commands/) — Command API reference
- [HytaleModding.dev - Creating Commands](https://hytalemodding.dev/en/docs/guides/plugin/creating-commands) — AbstractPlayerCommand patterns
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — Command implementation tutorials
