# Milestone Decisions: Core Foundation

Date: 2026-01-15

## Spike Results

_Investigations completed to reduce uncertainty._

| Spike | Question | Outcome | Impact |
| --- | --- | --- | --- |
| [spike-hytale-event-api.md](./spikes/spike-hytale-event-api.md) | Does Hytale provide cancellable events for block break, block place, container open, entity damage? | Confirmed | Block events available; container via UseBlockEvent.Pre; entity damage needs implementation-phase investigation |
| [spike-hytale-command-registration.md](./spikes/spike-hytale-command-registration.md) | How does Hytale register commands? What's the argument parsing API? | Confirmed | Standard AbstractPlayerCommand pattern with typed arguments via getCommandRegistry() |
| [spike-plugin-lifecycle-hooks.md](./spikes/spike-plugin-lifecycle-hooks.md) | Does Hytale provide onEnable/onDisable lifecycle hooks for plugins? | Confirmed | Full lifecycle: preLoad(), setup(), start(), shutdown() |

## ADRs Pending Review

_Decisions requiring human confirmation before issue decomposition._

| ADR | Decision | Recommended Option | Blocks |
| --- | --- | --- | --- |
| [ADR-0001](../../../adr/0001-claim-storage-format.md) | Claim storage format | Single claims.json file | Implement ClaimFileStorage |
| [ADR-0002](../../../adr/0002-chunk-coordinate-handling.md) | Chunk coordinate handling | Custom ChunkCoordinate record | ChunkCoordinate, ClaimCache, ClaimManager |
| [ADR-0003](../../../adr/0003-party-claim-relationship.md) | Party-claim relationship | Claims linked to player | Claim data class, Party data class |
| [ADR-0004](../../../adr/0004-api-versioning-strategy.md) | API versioning strategy | Semantic versioning only | API interfaces, CapitalAPI facade |

## Blocking Relationships

_Which issues cannot proceed until which ADRs are accepted._

```
[ADR-0001: Claim Storage Format]
    | blocks
    v
[Implement ClaimFileStorage]

[ADR-0002: Chunk Coordinate Handling]
    | blocks
    v
[Implement ChunkCoordinate record], [Implement ClaimCache], [Implement ClaimManager]

[ADR-0003: Party-Claim Relationship]
    | blocks
    v
[Implement Claim data class], [Implement Party data class]

[ADR-0004: API Versioning Strategy]
    | blocks
    v
[Create API interfaces], [Create CapitalAPI facade]
```

## Human Review Process

**To accept a decision:**

1. Open the ADR file in `.meatware/adr/`
2. Review the Context, Options, and Recommended Decision
3. If you agree: Change `Status: Proposed` to `Status: Accepted` (in body and frontmatter)
4. Update frontmatter `decision:` field with brief summary
5. If you disagree: Modify the Decision section, then change status to Accepted
6. Commit the change

**To reject a decision:**

1. Add comments explaining why no option is acceptable
2. Change `Status: Proposed` to `Status: Rejected`
3. New options must be identified before proceeding

**After all ADRs are accepted:**

Run `/meatware:milestone-resolution` to decompose issues and distribute work.

## Decision Log

_Track status changes as decisions are reviewed._

| ADR      | Original Status | Current Status | Reviewer | Date       |
| -------- | --------------- | -------------- | -------- | ---------- |
| ADR-0001 | Proposed        | Accepted       | Human    | 2026-01-15 |
| ADR-0002 | Proposed        | Accepted       | Human    | 2026-01-15 |
| ADR-0003 | Proposed        | Accepted       | Human    | 2026-01-15 |
| ADR-0004 | Proposed        | Accepted       | Human    | 2026-01-15 |
