---
date: 2026-01-15
status: Complete
question: Does Hytale provide cancellable events for block break, block place, container open, entity damage?
---

# Spike: Hytale Event API

_Timeboxed investigation to reduce uncertainty before dependent work proceeds._

## Question

Does Hytale provide cancellable events for block break, block place, container open, entity damage?

## Timebox

4 hours

## Context

- Related risk: Hytale event API lacks required events (Medium likelihood, High impact)
- Dependent decisions: Protection system architecture
- Dependent issues: All protection listeners (BlockBreakListener, BlockPlaceListener, ContainerListener, EntityDamageListener)

## Investigation Approach

1. Search official and community documentation for event system details
2. Identify available block, container, and entity events
3. Verify cancellation mechanism exists
4. Document any gaps between requirements and available events

## Findings

### Hytale Event System Confirmed

Hytale provides an extensive event system with both global events and ECS (Entity Component System) events.

**Source:** [HytaleDocs - Events](https://hytale-docs.com/docs/api/server-internals/events)

### Block Events Available and Cancellable

The following cancellable ECS events exist for block protection:

| Required Event | Hytale Event        | Cancellable |
| -------------- | ------------------- | ----------- |
| Block break    | `BreakBlockEvent`   | Yes         |
| Block place    | `PlaceBlockEvent`   | Yes         |
| Block damage   | `DamageBlockEvent`  | Yes         |
| Block use      | `UseBlockEvent.Pre` | Yes         |

**Source:** [HytaleDocs - Events](https://hytale-docs.com/docs/api/server-internals/events)

### Container/Inventory Events Partially Available

Direct container open event not documented. Related events that may be usable:

- `DropItemEvent.PlayerRequest` - Cancellable
- `DropItemEvent.Drop` - Cancellable
- `InteractivelyPickupItemEvent` - Cancellable
- `LivingEntityInventoryChangeEvent` - Synchronous (not explicitly cancellable)

Container access protection may need to use `UseBlockEvent.Pre` for container blocks (chests, etc.) rather than a dedicated container event.

**Source:** [HytaleDocs - Events](https://hytale-docs.com/docs/api/server-internals/events)

### Entity Damage Events

No explicit `EntityDamageEvent` documented. Available related events:

- `KillFeedEvent` - Tracks eliminations (post-damage, likely not cancellable)
- `EntityRemoveEvent` - Entity removal tracking

PvP damage cancellation may require either:

1. An undocumented damage event that exists but wasn't in community docs
2. ECS-based damage system interception
3. Alternative approach using entity flags/components

**Source:** [HytaleDocs - Events](https://hytale-docs.com/docs/api/server-internals/events)

### Cancellation Mechanism Confirmed

Events implement `ICancellable` interface:

```java
boolean isCancelled()
void setCancelled(boolean cancelled)
```

Example usage:

```java
if (shouldCancel) {
    event.setCancelled(true);
}
```

**Source:** [HytaleDocs - Events](https://hytale-docs.com/docs/api/server-internals/events)

### ECS Event Registration Pattern

ECS events require extending `EntityEventSystem` and registering via:

```java
getEntityStoreRegistry().registerSystem(new MyEventSystem());
```

**Source:** [HytaleModding.dev - Creating Events](https://hytalemodding.dev/en/docs/guides/plugin/creating-events)

## Outcome

**Status:** Confirmed

Block break, block place, and block use events are available and cancellable. Container protection can be achieved via `UseBlockEvent.Pre` on container blocks. Entity damage events are not explicitly documented but the ECS architecture suggests damage interception is possible.

## Impact

### On Decisions

| Decision                       | Impact                                                                   |
| ------------------------------ | ------------------------------------------------------------------------ |
| Protection system architecture | Use ECS event system for block events; investigate damage system for PvP |

### On Issues

| Issue                | Impact                                                                       |
| -------------------- | ---------------------------------------------------------------------------- |
| BlockBreakListener   | Use `BreakBlockEvent` - confirmed viable                                     |
| BlockPlaceListener   | Use `PlaceBlockEvent` - confirmed viable                                     |
| ContainerListener    | Use `UseBlockEvent.Pre` for container block types                            |
| EntityDamageListener | May require deeper investigation; implement last after testing damage system |

### New Risks Discovered

| Risk                                       | Likelihood | Impact | Recommended Response                                                                     |
| ------------------------------------------ | ---------- | ------ | ---------------------------------------------------------------------------------------- |
| Entity damage API differs from expectation | Medium     | Medium | Accept; implement other listeners first, investigate damage system during implementation |

## Recommendations

- Proceed with block protection listeners using confirmed events
- Implement ContainerListener using `UseBlockEvent.Pre` for container block types
- Defer EntityDamageListener investigation to implementation phase
- Add fallback plan: if PvP protection proves difficult, document as known limitation for v0
