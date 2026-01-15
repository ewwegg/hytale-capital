---
issue: Implement Party and PartyMember data classes
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Mid-level Engineer]
model: claude-sonnet-4-5
blocked-by: [2]
---

# Implement Party and PartyMember data classes

## Context

Data models for the party system. A Party contains members with roles; PartyMember tracks individual membership details.

## Dependencies

- Blocked by 2-plugin-entry-point — Requires project compiles

## Acceptance Criteria

- [ ] `PartyRole` enum with: OWNER, OFFICER, MEMBER
- [ ] `PartyMember` class with: UUID playerId, PartyRole role, Instant joinedAt
- [ ] `Party` class with: UUID id, String name, UUID ownerId, List<PartyMember> members, Instant createdAt
- [ ] `Party.getOwner()` returns owner's PartyMember
- [ ] `Party.getMember(UUID)` finds member by player ID
- [ ] `Party.getMembersByRole(PartyRole)` filters members
- [ ] Unit tests for member lookup operations

## Technical Constraints

- **ADR-0003**: Party does not own claims directly; access is checked separately. See [ADR-0003](.meatware/adr/0003-party-claim-relationship.md)
- **Immutability**: Members list returned from getters should be unmodifiable

## Workflow

1. Create `PartyRole.java` enum
2. Create `PartyMember.java` with fields and getters
3. Create `Party.java` with member management methods
4. Write unit tests for role-based queries

## Out of Scope

- Party-claim relationship (v0 has no party-based claim access)
- Party relations (ally/enemy - future milestone)

## Resources

- [ADR-0003: Party-Claim Relationship](.meatware/adr/0003-party-claim-relationship.md)
- [HytaleDocs - ECS Architecture](https://hytale-docs.com/docs/api/server-internals) — Entity Component System patterns
