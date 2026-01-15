---
issue: Implement party commands
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Mid-level Engineer]
model: claude-sonnet-4-5
blocked-by: [10, 11, 12]
---

# Implement party commands

## Context

Player-facing commands for party management. Grouped together as they share common patterns and work with PartyManager and InviteManager.

## Dependencies

- Blocked by 10-party-manager — PartyManager for party operations
- Blocked by 11-invite-manager — InviteManager for invite handling
- Blocked by 12-party-file-storage — Persistence for party changes

## Acceptance Criteria

- [ ] `/partycreate <name>` creates party with player as owner
  - Fails if player already in a party
- [ ] `/partydisband` disbands party
  - Fails if player is not party owner
- [ ] `/partyinvite <player>` sends invite to target player
  - Fails if player not in party or lacks invite permission
  - Fails if target already in a party or has pending invite
- [ ] `/partyaccept` accepts pending party invite
  - Fails if no pending invite
- [ ] `/partydeny` denies pending party invite
  - Fails if no pending invite
- [ ] `/partykick <player>` kicks member from party
  - Fails if player lacks kick permission (owner/officer only)
  - Cannot kick owner
- [ ] `/partyleave` leaves current party
  - If owner leaves, party disbands
- [ ] `/partyinfo` shows current party details
  - Lists members with roles
- [ ] All commands provide appropriate feedback messages

## Technical Constraints

- **Spike Finding**: Use `AbstractPlayerCommand` pattern. Consider `AbstractCommandCollection` for grouping under `/party`. See [spike-hytale-command-registration](../spikes/spike-hytale-command-registration.md)
- **Role Checks**: Officers can invite/kick members; only owner can disband

## Workflow

1. Create party command classes extending `AbstractPlayerCommand`
2. Consider using `AbstractCommandCollection` for `/party` prefix
3. Implement each command with validation and PartyManager calls
4. Wire invite commands to InviteManager
5. Register all commands in plugin setup()
6. Write unit tests for each command

## Out of Scope

- Promote/demote commands (can add later)
- Party chat (future feature)
- Party home/teleport (future feature)

## Resources

- [Spike: Hytale Command Registration](../spikes/spike-hytale-command-registration.md)
- [PartyManager implementation](./10-party-manager.md)
- [InviteManager implementation](./11-invite-manager.md)
- [HytaleDocs - Commands](https://hytale-docs.pages.dev/modding/plugins/commands/) — Command API and AbstractCommandCollection
- [HytaleModding.dev - Creating Commands](https://hytalemodding.dev/en/docs/guides/plugin/creating-commands) — Command patterns and argument types
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — Command implementation tutorials
