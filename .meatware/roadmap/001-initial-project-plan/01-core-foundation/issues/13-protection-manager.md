---
issue: Implement ProtectionManager
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Senior Engineer]
model: claude-opus-4-5
blocked-by: [5]
---

# Implement ProtectionManager

## Context

Central permission checking for all protection events. Consolidates the "can this player do this action here" logic used by all protection listeners.

## Dependencies

- Blocked by 5-claim-manager-cache — Uses ClaimCache for chunk-to-claim lookup

## Acceptance Criteria

- [ ] `canBuild(UUID playerId, ChunkCoordinate chunk)` returns true if player can modify blocks
- [ ] `canInteract(UUID playerId, ChunkCoordinate chunk)` returns true if player can interact with blocks
- [ ] `canDamage(UUID attackerId, UUID victimId, ChunkCoordinate chunk)` returns true if PvP allowed
- [ ] Unclaimed chunks return true (no protection)
- [ ] Claim owner always returns true
- [ ] Non-owner in claimed chunk returns false
- [ ] Unit tests for all permission scenarios

## Technical Constraints

- **ADR-0003**: Owner-only access in v0; no party or trust checks. See [ADR-0003](/.meatware/adr/0003-party-claim-relationship.md)
- **ADR-0002**: Uses ChunkCoordinate for location checks. See [ADR-0002](/.meatware/adr/0002-chunk-coordinate-handling.md)
- **Performance**: O(1) lookup via ClaimCache
- **Spike Finding**: Use ICancellable interface to cancel events. See [spike-hytale-event-api](/.meatware/roadmap/001-initial-project-plan/01-core-foundation/spikes/spike-hytale-event-api.md)

## Workflow

1. Create `ProtectionManager.java` in `dev.ewwegg.capital.core.manager`
2. Inject ClaimManager/ClaimCache dependency
3. Implement `canBuild()` with claim lookup and owner check
4. Implement `canInteract()` (same logic as canBuild for v0)
5. Implement `canDamage()` for PvP in claims
6. Write comprehensive unit tests

## Out of Scope

- Trust levels (future milestone)
- Protection flags (future milestone)
- Admin bypass (future enhancement)

## Resources

- [ADR-0003: Party-Claim Relationship](/.meatware/adr/0003-party-claim-relationship.md)
- [ADR-0002: Chunk Coordinate Handling](/.meatware/adr/0002-chunk-coordinate-handling.md)
- [Spike: Hytale Event API](/.meatware/roadmap/001-initial-project-plan/01-core-foundation/spikes/spike-hytale-event-api.md)
- [HytaleDocs - Events](https://hytale-docs.com/docs/api/server-internals/events) — ICancellable interface with isCancelled()/setCancelled()
