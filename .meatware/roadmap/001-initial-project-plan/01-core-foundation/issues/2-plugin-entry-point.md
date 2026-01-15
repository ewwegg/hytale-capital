---
issue: Create plugin entry point
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Senior Engineer]
model: claude-opus-4-5
blocked-by: [1]
---

# Create plugin entry point

## Context

The main plugin class that Hytale loads. Establishes lifecycle hooks for initialization and shutdown, and serves as the coordination point for all managers.

## Dependencies

- Blocked by 1-gradle-multi-module-setup — Requires project structure

## Acceptance Criteria

- [ ] `Capital.java` extends `JavaPlugin` with `JavaPluginInit` constructor
- [ ] Plugin loads successfully on Hytale server
- [ ] Startup message logged to console
- [ ] `setup()` method registers placeholder for commands and events
- [ ] `shutdown()` method logs shutdown message
- [ ] Plugin descriptor file created (plugin.json or equivalent)

## Technical Constraints

- **Spike Finding**: Use lifecycle methods `preLoad()`, `setup()`, `start()`, `shutdown()` per [spike-plugin-lifecycle-hooks](../spikes/spike-plugin-lifecycle-hooks.md)
- **Constructor**: Must accept `JavaPluginInit` parameter

## Workflow

1. Create `Capital.java` in `dev.ewwegg.capital.core`
2. Extend `JavaPlugin`, implement required constructor
3. Override `setup()` for command/event registration (placeholder)
4. Override `shutdown()` for cleanup
5. Create plugin descriptor with plugin ID, name, version
6. Test plugin loads on server

## Out of Scope

- Actual manager initialization (deferred to individual manager issues)
- Configuration loading (covered in issue 19)

## Resources

- [Spike: Plugin Lifecycle Hooks](../spikes/spike-plugin-lifecycle-hooks.md)
- [HytaleDocs - Plugin Lifecycle](https://hytale-docs.com/docs/modding/plugins/plugin-lifecycle)
- [Britakee's GitBook - Plugin Basics](https://britakee-studios.gitbook.io/hytale-modding-documentation) — Comprehensive plugin tutorials
- [HytaleModding.dev - Creating Plugins](https://hytalemodding.dev/en/docs/guides/plugin) — Community plugin guide
- [Hytale Server Manual](https://support.hytale.com/hc/en-us/articles/45326769420827-Hytale-Server-Manual) — Official server documentation
