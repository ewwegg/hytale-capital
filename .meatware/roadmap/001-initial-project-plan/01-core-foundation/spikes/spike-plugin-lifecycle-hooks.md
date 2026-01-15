---
date: 2026-01-15
status: Complete
question: Does Hytale provide onEnable/onDisable lifecycle hooks for plugins?
---

# Spike: Plugin Lifecycle Hooks

_Timeboxed investigation to reduce uncertainty before dependent work proceeds._

## Question

Does Hytale provide onEnable/onDisable lifecycle hooks for plugins?

## Timebox

2 hours

## Context

- Related risk: Plugin lifecycle hooks unavailable (Low likelihood, High impact)
- Dependent decisions: Plugin initialization architecture
- Dependent issues: Plugin entry point (Capital.java), StorageManager initialization

## Investigation Approach

1. Search documentation for plugin lifecycle methods
2. Identify initialization and shutdown hooks
3. Document loading sequence
4. Verify configuration loading mechanism

## Findings

### Plugin Lifecycle States Confirmed

Plugins transition through six states:

| State    | Description                               |
| -------- | ----------------------------------------- |
| NONE     | Initial state before any lifecycle method |
| SETUP    | During `setup()` execution                |
| START    | During `start()` execution                |
| ENABLED  | Fully operational                         |
| SHUTDOWN | During `shutdown()` execution             |
| DISABLED | Disabled or failed initialization         |

**Source:** [HytaleDocs - Plugin Lifecycle](https://hytale-docs.com/docs/modding/plugins/plugin-lifecycle)

### Lifecycle Methods

Four key lifecycle methods available:

| Method       | Purpose                           | Timing                                          |
| ------------ | --------------------------------- | ----------------------------------------------- |
| `preLoad()`  | Async config loading              | Before setup, returns `CompletableFuture<Void>` |
| `setup()`    | Register commands, events, assets | After all plugins load and configs preload      |
| `start()`    | Cross-plugin interactions         | After ALL plugins complete setup                |
| `shutdown()` | Resource cleanup                  | During disable, before registries cleaned       |

**Source:** [HytaleDocs - Plugin Lifecycle](https://hytale-docs.com/docs/modding/plugins/plugin-lifecycle)

### Loading Sequence

Seven-phase loading process:

1. Discovery (core plugins, builtin directory, classpath, mods folder)
2. Dependency validation
3. Load order calculation based on dependencies
4. Instantiation via reflection
5. PreLoad phase
6. Setup phase
7. Start phase

**Source:** [HytaleDocs - Plugin Lifecycle](https://hytale-docs.com/docs/modding/plugins/plugin-lifecycle)

### Constructor Requirement

All plugins require a constructor accepting `JavaPluginInit`:

```java
public class MyPlugin extends JavaPlugin {
    public MyPlugin(JavaPluginInit init) {
        super(init);
    }
}
```

**Source:** [HytaleDocs - Plugin Lifecycle](https://hytale-docs.com/docs/modding/plugins/plugin-lifecycle)

### Hot Reload Support

Plugins can be reloaded at runtime. For proper hot reload:

- Clean up in `shutdown()` by unregistering listeners and stopping tasks
- Use registries for automatic cleanup
- Avoid static state

**Source:** [HytaleDocs - Plugin Lifecycle](https://hytale-docs.com/docs/modding/plugins/plugin-lifecycle)

### Configuration Loading

Configurations must be registered before `preLoad()` is called. The `preLoad()` method returns a `CompletableFuture` that completes when configs finish loading.

**Source:** [HytaleDocs - Plugin Lifecycle](https://hytale-docs.com/docs/modding/plugins/plugin-lifecycle)

## Outcome

**Status:** Confirmed

Hytale provides comprehensive lifecycle hooks that exceed typical Bukkit/Spigot patterns:

- `preLoad()` for async config loading
- `setup()` for registration (equivalent to `onEnable`)
- `start()` for post-setup cross-plugin work
- `shutdown()` for cleanup (equivalent to `onDisable`)

## Impact

### On Decisions

| Decision              | Impact                                                |
| --------------------- | ----------------------------------------------------- |
| Plugin initialization | Use standard lifecycle methods; no workarounds needed |

### On Issues

| Issue                     | Impact                                                        |
| ------------------------- | ------------------------------------------------------------- |
| Create plugin entry point | Use `JavaPlugin` base class with `JavaPluginInit` constructor |
| Implement ConfigManager   | Register configs before `preLoad()`, load in `preLoad()`      |
| Implement JsonStorage     | Initialize in `setup()`, persist in `shutdown()`              |

## Recommendations

- Extend `JavaPlugin` for plugin entry point
- Register config schema in constructor or static initializer
- Load data in `preLoad()` (async) for large datasets
- Register commands/events in `setup()`
- Initialize cross-plugin dependencies in `start()` if needed
- Save pending data and cleanup in `shutdown()`
- Avoid static state to support hot reload
