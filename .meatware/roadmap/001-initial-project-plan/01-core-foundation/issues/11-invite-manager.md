---
issue: Implement InviteManager
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Mid-level Engineer]
model: claude-sonnet-4-5
blocked-by: [10]
---

# Implement InviteManager

## Context

Manages pending party invitations with automatic expiration. Decoupled from PartyManager to keep invitation logic separate from membership management.

## Dependencies

- Blocked by 10-party-manager — Invites result in PartyManager.addMember calls

## Acceptance Criteria

- [ ] `createInvite(UUID partyId, UUID inviterId, UUID inviteeId)` creates pending invite
- [ ] `acceptInvite(UUID inviteeId)` accepts and removes pending invite
- [ ] `denyInvite(UUID inviteeId)` removes pending invite without joining
- [ ] `getPendingInvite(UUID inviteeId)` returns current invite or null
- [ ] `hasPendingInvite(UUID inviteeId)` returns boolean
- [ ] Invites auto-expire after configurable timeout (default 60 seconds)
- [ ] Only one pending invite per player at a time
- [ ] Unit tests for invite lifecycle

## Technical Constraints

- **Timeout**: Use scheduled task or lazy expiration check
- **Memory**: Invites are transient, not persisted across restarts

## Workflow

1. Create `PartyInvite.java` record with partyId, inviterId, inviteeId, expiresAt
2. Create `InviteManager.java` in `dev.ewwegg.capital.core.manager`
3. Maintain `Map<UUID, PartyInvite>` keyed by invitee ID
4. Implement expiration check in getter methods
5. Write unit tests including expiration behavior

## Out of Scope

- Multiple pending invites per player
- Invite persistence across restarts

## Resources

- [PartyManager implementation](./10-party-manager.md)
- [HytaleDocs - Server Internals](https://hytale-docs.com/docs/api/server-internals) — Scheduled task and timing patterns
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — Plugin architecture patterns
