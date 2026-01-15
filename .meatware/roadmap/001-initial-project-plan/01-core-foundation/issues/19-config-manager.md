---
issue: Implement ConfigManager and CapitalConfig
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Mid-level Engineer]
model: claude-sonnet-4-5
blocked-by: [2]
---

# Implement ConfigManager and CapitalConfig

## Context

Configuration system for server operators to customize plugin behavior. Provides typed access to settings with sensible defaults.

## Dependencies

- Blocked by 2-plugin-entry-point — Requires plugin initialization hooks

## Acceptance Criteria

- [ ] `CapitalConfig` class with typed fields for all settings
- [ ] `ConfigManager` loads config from `config.json`
- [ ] Default values provided for all settings
- [ ] Missing file creates config with defaults
- [ ] Settings include:
  - maxClaimsPerPlayer (default: 10)
  - maxChunksPerClaim (default: 25)
  - startingClaimBlocks (default: 100)
  - partyMaxMembers (default: 20)
  - inviteTimeoutSeconds (default: 60)
- [ ] Config reloaded via method (hot-reload support)
- [ ] Unit tests verify default loading and value access

## Technical Constraints

- **Spike Finding**: Register config before `preLoad()`, load in `preLoad()`. See [spike-plugin-lifecycle-hooks](../spikes/spike-plugin-lifecycle-hooks.md)
- **Format**: JSON file for consistency with other storage

## Workflow

1. Create `CapitalConfig.java` with typed fields and defaults
2. Create `ConfigManager.java` for loading/saving
3. Use Gson for JSON serialization
4. Implement default value creation for missing file
5. Add reload method for runtime updates
6. Wire into plugin preLoad() or setup()
7. Write unit tests

## Out of Scope

- GUI configuration
- Per-world configuration
- Admin commands for config changes

## Resources

- [Spike: Plugin Lifecycle Hooks](../spikes/spike-plugin-lifecycle-hooks.md)
- [Hytale Server Manual](https://support.hytale.com/hc/en-us/articles/45326769420827-Hytale-Server-Manual) — Server configuration patterns
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — Config loading and codec system
- [HytaleDocs - Plugin Lifecycle](https://hytale-docs.com/docs/modding/plugins/plugin-lifecycle) — Config registration in preLoad()
