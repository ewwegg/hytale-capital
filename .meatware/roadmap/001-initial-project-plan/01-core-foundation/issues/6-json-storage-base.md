---
issue: Implement JsonStorage base class
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Senior Engineer]
model: claude-opus-4-5
blocked-by: [2]
---

# Implement JsonStorage base class

## Context

Reusable foundation for all JSON file persistence. Provides file I/O, Gson serialization, backup creation, and error handling used by ClaimFileStorage, PartyFileStorage, and PlayerFileStorage.

## Dependencies

- Blocked by 2-plugin-entry-point — Requires plugin data directory access

## Acceptance Criteria

- [ ] Abstract `JsonStorage<T>` base class with generic type for stored data
- [ ] `load()` method reads JSON file, returns deserialized object or default
- [ ] `save(T data)` method serializes and writes to file
- [ ] Backup created before each save (`.bak` extension)
- [ ] Graceful handling of missing files (return default)
- [ ] Graceful handling of corrupt files (log error, return default, preserve corrupt file)
- [ ] Unit tests for load/save cycle

## Technical Constraints

- **Spike Finding**: Initialize in `setup()`, persist final state in `shutdown()` per [spike-plugin-lifecycle-hooks](/.meatware/roadmap/001-initial-project-plan/01-core-foundation/spikes/spike-plugin-lifecycle-hooks.md)
- **ADR-0001**: Single file format for claims; same pattern for other storage. See [ADR-0001](/.meatware/adr/0001-claim-storage-format.md)
- **Gson**: Use Gson 2.10.1 for JSON serialization with pretty printing for human readability
- **Data Directory**: Store files in plugin data directory (obtain via plugin API)

## Workflow

1. Create `JsonStorage.java` in `dev.ewwegg.capital.core.storage`
2. Define abstract methods for type token and default value
3. Implement `load()` with file existence check and Gson deserialization
4. Implement `save()` with backup creation and atomic write
5. Add error handling with logging
6. Write unit tests using temp directory

## Out of Scope

- Write debouncing/batching (optimization for later)
- Background thread I/O (optimization for later)
- File locking (acceptable risk for v0)

## Resources

- [Spike: Plugin Lifecycle Hooks](/.meatware/roadmap/001-initial-project-plan/01-core-foundation/spikes/spike-plugin-lifecycle-hooks.md)
- [ADR-0001: Claim Storage Format](/.meatware/adr/0001-claim-storage-format.md)
- [Britakee's GitBook](https://britakee-studios.gitbook.io/hytale-modding-documentation) — File I/O patterns and plugin data storage
- [Tech Stack Reference](/.meatware/references/tech-stack.md) — Plugin file locations (`%appdata%/Hytale/UserData/Mods/`)
