---
issue: Implement block protection listeners
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Mid-level Engineer]
model: claude-sonnet-4-5
blocked-by: [13]
---

# Implement block protection listeners

## Context

Event listeners that cancel block modifications in protected claims. Grouped together as they share the same pattern and ProtectionManager integration.

## Dependencies

- Blocked by 13-protection-manager — Uses ProtectionManager.canBuild() for permission checks

## Acceptance Criteria

- [ ] `BlockBreakListener` cancels `BreakBlockEvent` if player cannot build
- [ ] `BlockPlaceListener` cancels `PlaceBlockEvent` if player cannot build
- [ ] Both listeners extract player UUID and block coordinates from event
- [ ] Both listeners convert block coordinates to ChunkCoordinate
- [ ] Unclaimed chunks allow modifications
- [ ] Claim owner can modify their claim
- [ ] Non-owner cannot modify protected claim
- [ ] Player receives feedback message when action cancelled
- [ ] Unit tests with mocked events

## Technical Constraints

- **Spike Finding**: Use `BreakBlockEvent` and `PlaceBlockEvent` ECS events. See [spike-hytale-event-api](.meatware/roadmap/001-initial-project-plan/01-core-foundation/spikes/spike-hytale-event-api.md)
- **Cancellation**: Call `event.setCancelled(true)` to prevent action
- **ADR-0002**: Convert block coordinates to ChunkCoordinate via `fromBlock()`. See [ADR-0002](.meatware/adr/0002-chunk-coordinate-handling.md)

## Workflow

1. Create `BlockBreakListener.java` extending appropriate event system
2. Extract player and location from event context
3. Convert to ChunkCoordinate and call ProtectionManager.canBuild()
4. Cancel if not allowed, send feedback message
5. Create `BlockPlaceListener.java` with same pattern
6. Register both in plugin setup()
7. Write unit tests with mocked events

## Out of Scope

- Block damage events (covered by break)
- Different protection for different block types

## Resources

- [Spike: Hytale Event API](.meatware/roadmap/001-initial-project-plan/01-core-foundation/spikes/spike-hytale-event-api.md)
- [ADR-0002: Chunk Coordinate Handling](.meatware/adr/0002-chunk-coordinate-handling.md)
- [HytaleDocs - Events](https://hytale-docs.com/docs/api/server-internals/events) — BreakBlockEvent, PlaceBlockEvent reference
- [HytaleModding.dev - Creating Events](https://hytalemodding.dev/en/docs/guides/plugin/creating-events) — ECS event system patterns
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — Event listener tutorials
