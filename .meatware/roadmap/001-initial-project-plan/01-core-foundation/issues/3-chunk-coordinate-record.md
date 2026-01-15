---
issue: Implement ChunkCoordinate record
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Mid-level Engineer]
model: claude-sonnet-4-5
blocked-by: [2]
---

# Implement ChunkCoordinate record

## Context

Immutable identifier for chunk positions used as map keys in ClaimCache. Provides consistent hashing and world-aware chunk identification.

## Dependencies

- Blocked by 2-plugin-entry-point — Requires project compiles

## Acceptance Criteria

- [ ] `ChunkCoordinate` is a Java record with fields: `String world`, `int x`, `int z`
- [ ] `fromBlock(String world, int blockX, int blockZ)` factory method converts block coords to chunk coords
- [ ] Record works correctly as HashMap key (equals/hashCode verified)
- [ ] Unit tests verify coordinate conversion and map key behavior

## Technical Constraints

- **ADR-0002**: Use custom record, not Hytale native types. See [ADR-0002](../../../../adr/0002-chunk-coordinate-handling.md)
- **Conversion**: Chunk X = blockX >> 4, Chunk Z = blockZ >> 4

## Workflow

1. Create `ChunkCoordinate.java` in `dev.ewwegg.capital.core.model`
2. Implement as Java record with world, x, z
3. Add `fromBlock` static factory method
4. Write unit tests for coordinate conversion
5. Write unit tests verifying HashMap/HashSet compatibility

## Out of Scope

- Serialization (handled by storage classes)
- Hytale API conversion utilities (added when needed)

## Resources

- [ADR-0002: Chunk Coordinate Handling](../../../../adr/0002-chunk-coordinate-handling.md)
- [HytaleDocs - World API](https://hytale-docs.com/docs/api/server-internals) — World and chunk coordinate systems
