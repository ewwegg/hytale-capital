---
issue: Implement PartyManager
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Senior Engineer]
model: claude-opus-4-5
blocked-by: [9]
---

# Implement PartyManager

## Context

Central management for all party operations. Handles creation, disbanding, membership changes, and role management.

## Dependencies

- Blocked by 9-party-data-classes — Manages Party and PartyMember objects

## Acceptance Criteria

- [ ] `createParty(UUID owner, String name)` creates party with owner as OWNER role
- [ ] `disbandParty(UUID partyId)` removes party entirely
- [ ] `addMember(UUID partyId, UUID playerId, PartyRole role)` adds player to party
- [ ] `removeMember(UUID partyId, UUID playerId)` removes player from party
- [ ] `promoteToOfficer(UUID partyId, UUID playerId)` changes role to OFFICER
- [ ] `demoteToMember(UUID partyId, UUID playerId)` changes role to MEMBER
- [ ] `getParty(UUID partyId)` returns party or null
- [ ] `getPartyByPlayer(UUID playerId)` returns party player belongs to
- [ ] Validation: player can only be in one party at a time
- [ ] Unit tests for all operations

## Technical Constraints

- **Ownership Transfer**: Not implemented in v0; owner leaving disbands party
- **Max Members**: Configurable via CapitalConfig (when available)

## Workflow

1. Create `PartyManager.java` in `dev.ewwegg.capital.core.manager`
2. Maintain `Map<UUID, Party>` for parties
3. Maintain `Map<UUID, UUID>` for player-to-party lookup
4. Implement all CRUD and membership operations
5. Add validation for single-party membership
6. Write comprehensive unit tests

## Out of Scope

- Invite system (handled by InviteManager)
- Persistence (handled by PartyFileStorage)

## Resources

- [Party data classes](./9-party-data-classes.md)
- [HytaleDocs - Server Internals](https://hytale-docs.com/docs/api/server-internals) — Manager patterns and player UUID handling
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — Plugin architecture patterns
