---
issue: Implement PlayerFileStorage
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Mid-level Engineer]
model: claude-sonnet-4-5
blocked-by: [6]
---

# Implement PlayerFileStorage

## Context

Persists per-player data such as claim block balance and settings. Uses the JsonStorage base class for consistent file handling.

## Dependencies

- Blocked by 6-json-storage-base — Extends JsonStorage base class

## Acceptance Criteria

- [ ] `PlayerFileStorage` extends `JsonStorage<PlayerDataMap>`
- [ ] `PlayerData` class contains: UUID playerId, int claimBlocks, settings map
- [ ] `getPlayerData(UUID)` returns player data or creates default
- [ ] `savePlayerData(UUID, PlayerData)` updates and persists
- [ ] File location: `data/players.json`
- [ ] Integration test verifies round-trip persistence

## Technical Constraints

- **Default Values**: New players start with configured claim block amount (from CapitalConfig when available, or hardcoded default)
- **ADR-0001**: Follow single file storage pattern. See [ADR-0001](/.meatware/adr/0001-claim-storage-format.md)
- **UUID Key**: Use player UUID string as map key for consistent lookups

## Workflow

1. Create `PlayerData.java` with player-specific fields
2. Create `PlayerDataMap.java` wrapper with Map<UUID, PlayerData>
3. Create `PlayerFileStorage.java` extending `JsonStorage<PlayerDataMap>`
4. Implement convenience methods for single-player operations
5. Write integration tests

## Out of Scope

- Claim block earning logic (future milestone)
- Per-player settings UI (no GUI in v0)

## Resources

- [ADR-0001: Claim Storage Format](/.meatware/adr/0001-claim-storage-format.md) — Single file storage pattern
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — Player data persistence patterns
- [Tech Stack Reference](/.meatware/references/tech-stack.md) — Plugin file locations and data paths
