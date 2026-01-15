---
date: 2026-01-15
status: Accepted
decision: Use custom ChunkCoordinate record with world, x, z
---

# 0002. Chunk Coordinate Handling

_Architecture Decision Record following [MADR](https://adr.github.io/madr/) format, extended from [Michael Nygard's template](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions)._

## Status

Accepted

_Status lifecycle: Proposed → Accepted → [Deprecated | Superseded by ADR-NNNN]_

## Context

Claims are chunk-based. The system needs to:
- Identify chunks uniquely (including across worlds)
- Use chunks as map keys for O(1) cache lookup
- Convert between block coordinates and chunk coordinates
- Serialize chunks to JSON storage

Hytale uses a voxel world model similar to Minecraft with 16x16 chunk columns. The exact API for chunk coordinates is not fully documented but follows standard patterns.

## Options Considered

### Option 1: Hytale Native Coordinates Only

Use whatever chunk coordinate class Hytale provides directly throughout the codebase.

**Pros:**

- No wrapper overhead
- Direct API compatibility
- Less code to maintain

**Cons:**

- Couples entire codebase to Hytale internals
- May not include world identifier
- Hytale class may not have stable equals/hashCode for map keys
- Harder to test without Hytale runtime

### Option 2: Custom ChunkCoordinate Record

Create our own immutable record with world, x, z:

```java
public record ChunkCoordinate(String world, int x, int z) {
    public static ChunkCoordinate fromBlock(String world, int blockX, int blockZ) {
        return new ChunkCoordinate(world, blockX >> 4, blockZ >> 4);
    }
}
```

**Pros:**

- Guaranteed stable equals/hashCode (Java records)
- Includes world identifier by design
- Testable without Hytale runtime
- Clean API boundary
- JSON serialization straightforward

**Cons:**

- Requires conversion to/from Hytale types
- Additional class to maintain
- Slight overhead (negligible)

### Option 3: World-Qualified Wrapper

Wrapper class around Hytale's chunk coordinate that adds world context:

```java
public class WorldChunk {
    private final String world;
    private final HytaleChunkCoord chunk;
}
```

**Pros:**

- Preserves Hytale type internally
- Adds world qualification

**Cons:**

- Still coupled to Hytale internals
- Hybrid approach complexity
- Testing still requires Hytale types

## Decision

We will use **Option 2: Custom ChunkCoordinate record** because it provides a clean abstraction boundary and guaranteed map-key behavior.

_Evaluation against development philosophy:_

| Principle | Assessment |
| --- | --- |
| Working beats perfect | Record is simple, works immediately |
| Simple beats flexible | Single type for all chunk references |
| Conventional beats clever | Java record is idiomatic; standard pattern |
| Explicit beats abstract | World included explicitly, not implied |
| Reversible beats optimal | Easy to add fields or change implementation |

## Consequences

**Positive:**

- Testable without Hytale server running
- Guaranteed HashMap/HashSet compatibility
- World always explicit (no bugs from missing world context)
- Clean JSON serialization

**Negative:**

- Requires conversion at Hytale API boundary
- One more type to understand

**Neutral:**

- Conversion overhead is negligible (chunk lookups are in-memory)

## Related

- Blocks: Implement ChunkCoordinate record, Implement ClaimCache, Implement ClaimManager
- Related ADRs: None
- Spike findings: spike-hytale-event-api.md (confirms standard voxel model)
