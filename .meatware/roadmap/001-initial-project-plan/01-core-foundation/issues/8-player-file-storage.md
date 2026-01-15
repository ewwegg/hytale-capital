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

- **Default Values**: New players start with configured claim block amount (from CapitalConfig when available)

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

- [JsonStorage base implementation](./6-json-storage-base.md)
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — Player data persistence patterns
- [Hytale Server Manual](https://support.hytale.com/hc/en-us/articles/45326769420827-Hytale-Server-Manual) — Server data file locations
