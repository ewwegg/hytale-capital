---
issue: Implement ContainerListener
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Mid-level Engineer]
model: claude-sonnet-4-5
blocked-by: [13]
---

# Implement ContainerListener

## Context

Protects containers (chests, barrels, etc.) from unauthorized access in claimed chunks. Uses UseBlockEvent.Pre since no dedicated container event exists.

## Dependencies

- Blocked by 13-protection-manager — Uses ProtectionManager.canInteract() for permission checks

## Acceptance Criteria

- [ ] `ContainerListener` handles `UseBlockEvent.Pre` events
- [ ] Identifies container block types (chests, barrels, hoppers, etc.)
- [ ] Cancels interaction if player cannot interact in that claim
- [ ] Non-container blocks are ignored (not cancelled)
- [ ] Player receives feedback message when access denied
- [ ] Unit tests with mocked events

## Technical Constraints

- **Spike Finding**: Container access via `UseBlockEvent.Pre` on container block types. See [spike-hytale-event-api](../spikes/spike-hytale-event-api.md)
- **Block Detection**: Need to identify which blocks are containers (may require block type registry or hardcoded list)

## Workflow

1. Create `ContainerListener.java` extending appropriate event system
2. Define container block type set (chest, barrel, hopper, etc.)
3. Check if interacted block is container type
4. If container, extract player and location, check ProtectionManager.canInteract()
5. Cancel if not allowed, send feedback message
6. Register in plugin setup()
7. Write unit tests

## Out of Scope

- Item frame protection (may use different event)
- Armor stand protection (entity-based)

## Resources

- [Spike: Hytale Event API](../spikes/spike-hytale-event-api.md)
- [ProtectionManager implementation](./13-protection-manager.md)
- [HytaleDocs - Events](https://hytale-docs.com/docs/api/server-internals/events) — UseBlockEvent.Pre reference
- [HytaleModding.dev - Creating Events](https://hytalemodding.dev/en/docs/guides/plugin/creating-events) — ECS event patterns
