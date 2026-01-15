---
issue: Implement CapitalAPI facade
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Senior Engineer]
model: claude-opus-4-5
blocked-by: [20]
---

# Implement CapitalAPI facade

## Context

Single entry point for external mods to access Capital functionality. Simplifies integration by providing one class to obtain all managers.

## Dependencies

- Blocked by 20-api-interfaces — Implements the API interfaces

## Acceptance Criteria

- [ ] `CapitalAPI` interface with static `getInstance()` method
- [ ] Methods: `getClaimManager()`, `getPartyManager()`, `getProtectionManager()`
- [ ] `CapitalAPIImpl` in core module implements `CapitalAPI`
- [ ] Implementation wires to actual manager instances
- [ ] API throws clear exception if accessed before plugin initialization
- [ ] Unit tests verify facade provides working managers
- [ ] Integration test demonstrates external mod usage pattern

## Technical Constraints

- **Singleton**: Single API instance for plugin lifecycle
- **Initialization**: Set during plugin setup(), cleared during shutdown()
- **Spike Finding**: Follow lifecycle hooks pattern. See [spike-plugin-lifecycle-hooks](.meatware/roadmap/001-initial-project-plan/01-core-foundation/spikes/spike-plugin-lifecycle-hooks.md)
- **ADR-0004**: Semantic versioning only. See [ADR-0004](.meatware/adr/0004-api-versioning-strategy.md)

## Workflow

1. Create `CapitalAPI.java` interface in api module
2. Add static `getInstance()` method signature
3. Create `CapitalAPIImpl.java` in core module
4. Wire implementation to plugin's manager instances
5. Set static instance during plugin setup()
6. Write integration tests demonstrating usage

## Out of Scope

- Async API methods
- Event listeners for external mods

## Resources

- [ADR-0004: API Versioning Strategy](.meatware/adr/0004-api-versioning-strategy.md)
- [Spike: Plugin Lifecycle Hooks](.meatware/roadmap/001-initial-project-plan/01-core-foundation/spikes/spike-plugin-lifecycle-hooks.md)
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — Plugin API exposure patterns
