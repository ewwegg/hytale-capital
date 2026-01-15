---
issue: Implement Claim data class
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Mid-level Engineer]
model: claude-sonnet-4-5
blocked-by: [3]
---

# Implement Claim data class

## Context

Core data model for land claims. Stores ownership, chunk set, and metadata. Designed for JSON serialization and in-memory operations.

## Dependencies

- Blocked by 3-chunk-coordinate-record — Uses ChunkCoordinate for chunk storage

## Acceptance Criteria

- [ ] `Claim` class with fields: `UUID id`, `UUID ownerId`, `String name`, `Set<ChunkCoordinate> chunks`, `Instant createdAt`
- [ ] Immutable chunk set (defensive copy on construction)
- [ ] Methods: `addChunk()`, `removeChunk()`, `containsChunk()`, `getChunkCount()`
- [ ] Builder or factory method for construction
- [ ] Unit tests for chunk operations

## Technical Constraints

- **ADR-0003**: Owner is player UUID, not party. See [ADR-0003](.meatware/adr/0003-party-claim-relationship.md)
- **ADR-0002**: Uses ChunkCoordinate for chunk storage. See [ADR-0002](.meatware/adr/0002-chunk-coordinate-handling.md)
- **Immutability**: Chunk set returned from getters must be unmodifiable

## Workflow

1. Create `Claim.java` in `dev.ewwegg.capital.core.model`
2. Define fields with appropriate types
3. Implement chunk management methods
4. Add builder or static factory method
5. Write unit tests for construction and chunk operations

## Out of Scope

- Party access checks (handled by ProtectionManager)
- Trust levels (future milestone)
- Serialization annotations (handled by storage)

## Resources

- [ADR-0003: Party-Claim Relationship](.meatware/adr/0003-party-claim-relationship.md)
- [ADR-0002: Chunk Coordinate Handling](.meatware/adr/0002-chunk-coordinate-handling.md)
- [HytaleDocs - ECS Architecture](https://hytale-docs.com/docs/api/server-internals) — Data class design patterns
