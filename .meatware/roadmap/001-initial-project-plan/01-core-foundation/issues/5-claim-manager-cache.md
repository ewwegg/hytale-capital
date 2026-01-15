---
issue: Implement ClaimManager and ClaimCache
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Senior Engineer]
model: claude-opus-4-5
blocked-by: [4]
---

# Implement ClaimManager and ClaimCache

## Context

Central claim management with O(1) chunk lookup. ClaimManager handles CRUD operations; ClaimCache provides fast spatial queries. Together they form the core claim system.

## Dependencies

- Blocked by 4-claim-data-class — Manages Claim objects

## Acceptance Criteria

- [ ] `ClaimManager` provides: `createClaim()`, `deleteClaim()`, `getClaim(UUID)`, `getClaimsByOwner(UUID)`, `getAllClaims()`
- [ ] `ClaimCache` provides: `getClaimAt(ChunkCoordinate)` with O(1) lookup
- [ ] Cache invalidated automatically on claim create/delete/modify
- [ ] Overlap check on create prevents claiming already-claimed chunks
- [ ] Unit tests for all CRUD operations
- [ ] Unit tests verify cache consistency after modifications

## Technical Constraints

- **ADR-0002**: Use ChunkCoordinate as cache key. See [ADR-0002](.meatware/adr/0002-chunk-coordinate-handling.md)
- **ADR-0003**: Claims linked to player UUID. See [ADR-0003](.meatware/adr/0003-party-claim-relationship.md)
- **Thread Safety**: Methods should be safe for concurrent access (consider synchronization or concurrent collections)

## Workflow

1. Create `ClaimCache.java` with `Map<ChunkCoordinate, UUID>` (chunk to claim ID)
2. Implement cache methods: `put()`, `remove()`, `getClaimAt()`
3. Create `ClaimManager.java` with claim storage map
4. Wire ClaimManager to update ClaimCache on all mutations
5. Implement overlap checking in `createClaim()`
6. Write comprehensive unit tests

## Out of Scope

- Persistence (handled by ClaimFileStorage)
- Permission checks (handled by ProtectionManager)

## Resources

- [ADR-0002: Chunk Coordinate Handling](.meatware/adr/0002-chunk-coordinate-handling.md)
- [ADR-0003: Party-Claim Relationship](.meatware/adr/0003-party-claim-relationship.md)
- [Milestone Analysis](.meatware/roadmap/001-initial-project-plan/01-core-foundation/milestone-analysis.md)
- [HytaleDocs - World API](https://hytale-docs.com/docs/api/server-internals) — World and chunk systems
