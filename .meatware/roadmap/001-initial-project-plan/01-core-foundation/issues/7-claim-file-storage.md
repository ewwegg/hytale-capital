---
issue: Implement ClaimFileStorage
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Mid-level Engineer]
model: claude-sonnet-4-5
blocked-by: [5, 6]
---

# Implement ClaimFileStorage

## Context

Persists all claims to a single JSON file. Integrates with ClaimManager to load on startup and save on shutdown or claim changes.

## Dependencies

- Blocked by 5-claim-manager-cache — Stores/loads ClaimManager data
- Blocked by 6-json-storage-base — Extends JsonStorage base class

## Acceptance Criteria

- [ ] `ClaimFileStorage` extends `JsonStorage<ClaimData>`
- [ ] `ClaimData` wrapper class contains list of claims
- [ ] Claims serialized with: id, ownerId, name, chunks (as list of {world, x, z}), createdAt
- [ ] `loadAll()` returns all claims for ClaimManager initialization
- [ ] `saveAll(Collection<Claim>)` persists all claims
- [ ] File location: `data/claims.json`
- [ ] Integration test verifies round-trip persistence

## Technical Constraints

- **ADR-0001**: Single claims.json file, not per-world or per-chunk. See [ADR-0001](../../../../adr/0001-claim-storage-format.md)
- **UUID Serialization**: Store UUIDs as strings

## Workflow

1. Create `ClaimData.java` wrapper class with claims list
2. Create `ClaimFileStorage.java` extending `JsonStorage<ClaimData>`
3. Implement type token and default value methods
4. Add `loadAll()` and `saveAll()` convenience methods
5. Configure custom Gson serializers for Instant and UUID if needed
6. Write integration tests with temp files

## Out of Scope

- Incremental saves (full file rewrite is acceptable for v0)
- Migration from other formats

## Resources

- [ADR-0001: Claim Storage Format](../../../../adr/0001-claim-storage-format.md)
- [JsonStorage base implementation](./6-json-storage-base.md)
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — JSON serialization patterns
- [Hytale Server Manual](https://support.hytale.com/hc/en-us/articles/45326769420827-Hytale-Server-Manual) — Server data file locations
