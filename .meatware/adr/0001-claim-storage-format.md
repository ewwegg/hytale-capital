---
date: 2026-01-15
status: Accepted
decision: Use single claims.json file for storage
---

# 0001. Claim Storage Format

_Architecture Decision Record following [MADR](https://adr.github.io/madr/) format, extended from [Michael Nygard's template](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions)._

## Status

Accepted

_Status lifecycle: Proposed → Accepted → [Deprecated | Superseded by ADR-NNNN]_

## Context

The claim system needs persistent storage for claim data. Claims contain:

- Owner UUID
- Set of chunk coordinates
- Creation timestamp
- Claim name

Storage must support:

- Loading all claims on startup
- Saving individual claim changes
- Querying claims by chunk (via in-memory cache)
- Multi-world support

Constraints:

- JSON file storage for v0 (database backends planned for v1)
- Must handle server with 100s of claims reasonably
- Must be human-readable for debugging
- Must support backup/restore workflows

## Options Considered

### Option 1: Single claims.json File

All claims stored in one file at `data/claims.json`.

```json
{
  "claims": [
    {
      "id": "uuid",
      "owner": "player-uuid",
      "name": "My Claim",
      "chunks": [{ "world": "overworld", "x": 0, "z": 0 }],
      "created": 1234567890
    }
  ]
}
```

**Pros:**

- Simplest implementation
- Single file to backup/restore
- Easy to inspect all claims at once
- Atomic save operation

**Cons:**

- Entire file rewritten on every change
- File grows with claim count
- Risk of corruption affects all claims
- No parallelism for large datasets

### Option 2: Per-World JSON Files

Claims stored per world at `data/claims/{world}.json`.

```
data/claims/
  overworld.json
  nether.json
  the_end.json
```

**Pros:**

- Smaller files than single-file approach
- World-scoped backup/restore
- Corruption isolated to one world
- Natural organization for multi-world servers

**Cons:**

- Claims spanning worlds need special handling (unlikely for chunk claims)
- More files to manage
- Slightly more complex loading

### Option 3: Per-Chunk JSON Files

Each chunk's claim data in separate file at `data/claims/{world}/{x}_{z}.json`.

**Pros:**

- Minimal write scope per change
- Maximum parallelism potential
- Corruption isolated to single chunk

**Cons:**

- Thousands of tiny files
- Filesystem overhead
- Complex directory management
- Slow full-load on startup (many file handles)
- Over-engineered for v0 scale

## Decision

We will use **Option 1: Single claims.json file** because it provides the simplest implementation that meets v0 requirements.

_Evaluation against development philosophy:_

| Principle                 | Assessment                                                         |
| ------------------------- | ------------------------------------------------------------------ |
| Working beats perfect     | Single file is immediately functional; optimization can come later |
| Simple beats flexible     | One file, one format, no world/chunk partitioning logic            |
| Conventional beats clever | Standard JSON file pattern; no complex sharding                    |
| Explicit beats abstract   | Direct file path, no dynamic resolution                            |
| Reversible beats optimal  | Easy to migrate to per-world or database later                     |

Performance concern mitigation:

- In-memory cache handles all reads; file only touched on write
- Write-on-change with debouncing (save at most every N seconds)
- Background thread for file I/O
- For v0 scale (100s of claims), single file is negligible

## Consequences

**Positive:**

- Fast implementation
- Simple debugging (one file to inspect)
- Straightforward backup (copy one file)
- No filesystem complexity

**Negative:**

- Will need migration path when scaling to 1000s of claims
- Full file rewrite on each save (mitigated by write batching)

**Neutral:**

- Database migration planned for v1 regardless of v0 file structure

## Related

- Blocks: Implement ClaimFileStorage
- Related ADRs: None
- Spike findings: None (storage format not dependent on Hytale API)
