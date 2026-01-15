---
issue: Implement PartyFileStorage
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Mid-level Engineer]
model: claude-sonnet-4-5
blocked-by: [6, 10]
---

# Implement PartyFileStorage

## Context

Persists all parties to a JSON file. Integrates with PartyManager to load on startup and save on changes.

## Dependencies

- Blocked by 6-json-storage-base — Extends JsonStorage base class
- Blocked by 10-party-manager — Stores/loads PartyManager data

## Acceptance Criteria

- [ ] `PartyFileStorage` extends `JsonStorage<PartyData>`
- [ ] `PartyData` wrapper class contains list of parties
- [ ] Parties serialized with: id, name, ownerId, members (with roles), createdAt
- [ ] `loadAll()` returns all parties for PartyManager initialization
- [ ] `saveAll(Collection<Party>)` persists all parties
- [ ] File location: `data/parties.json`
- [ ] Integration test verifies round-trip persistence

## Technical Constraints

- **Member Serialization**: Include playerId, role, joinedAt for each member
- **ADR-0001**: Follow single file storage pattern. See [ADR-0001](.meatware/adr/0001-claim-storage-format.md)

## Workflow

1. Create `PartyData.java` wrapper class with parties list
2. Create `PartyFileStorage.java` extending `JsonStorage<PartyData>`
3. Implement type token and default value methods
4. Add `loadAll()` and `saveAll()` convenience methods
5. Write integration tests with temp files

## Out of Scope

- Incremental saves (full file rewrite is acceptable for v0)

## Resources

- [ADR-0001: Claim Storage Format](.meatware/adr/0001-claim-storage-format.md) — Single file storage pattern (applied to parties)
- [ADR-0003: Party-Claim Relationship](.meatware/adr/0003-party-claim-relationship.md)
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — JSON serialization patterns
- [Hytale Server Manual](https://support.hytale.com/hc/en-us/articles/45326769420827-Hytale-Server-Manual) — Server data file locations
