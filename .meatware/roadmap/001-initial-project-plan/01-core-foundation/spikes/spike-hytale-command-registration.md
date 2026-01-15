---
date: 2026-01-15
status: Complete
question: How does Hytale register commands? What's the argument parsing API?
---

# Spike: Hytale Command Registration

_Timeboxed investigation to reduce uncertainty before dependent work proceeds._

## Question

How does Hytale register commands? What's the argument parsing API?

## Timebox

2 hours

## Context

- Related risk: Hytale command registration differs from expected (Medium likelihood, Medium impact)
- Dependent decisions: Command architecture
- Dependent issues: All command implementations (ClaimCommand, UnclaimCommand, PartyCreateCommand, etc.)

## Investigation Approach

1. Search documentation for command registration patterns
2. Identify base command classes and registration methods
3. Document argument parsing system
4. Verify async execution model

## Findings

### Command Registration Confirmed

Commands are registered via plugin's `setup()` method using the command registry:

```java
@Override
protected void setup() {
    getCommandRegistry().registerCommand(new MyCommand());
}
```

Using `CommandManager.get().register()` directly is incorrect - commands registered that way persist after plugin disable. The `getCommandRegistry()` approach ensures automatic cleanup.

**Source:** [HytaleDocs - Commands](https://hytale-docs.pages.dev/modding/plugins/commands/)

### Command Base Classes

Two primary base classes available:

| Class                   | Use Case                                 |
| ----------------------- | ---------------------------------------- |
| `AbstractCommand`       | General commands (console or player)     |
| `AbstractPlayerCommand` | Player-only commands with player context |

Both extend `AbstractAsyncCommand` - commands execute off the main thread and return `CompletableFuture<Void>`.

**Source:** [HytaleModding.dev - Creating Commands](https://hytalemodding.dev/en/docs/guides/plugin/creating-commands)

### Command Structure

Basic command structure:

```java
public class HelloCommand extends AbstractCommand {
    public HelloCommand() {
        super("hello", "Says hello to a player");
    }

    @Override
    protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
        context.sender().sendMessage(Message.raw("Hello!"));
        return CompletableFuture.completedFuture(null);
    }
}
```

Player-specific commands with full context:

```java
public class ExampleCommand extends AbstractPlayerCommand {
    public ExampleCommand() {
        super("test", "Test command description");
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext,
            @Nonnull Store<EntityStore> store,
            @Nonnull Ref<EntityStore> ref,
            @Nonnull PlayerRef playerRef,
            @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());
        player.sendMessage(Message.raw("Hello!"));
    }
}
```

**Source:** [HytaleModding.dev - Creating Commands](https://hytalemodding.dev/en/docs/guides/plugin/creating-commands)

### Argument Parsing System

Arguments registered via fluent builder in constructor. Four argument types:

| Type          | Description                           |
| ------------- | ------------------------------------- |
| `RequiredArg` | Positional, must be provided          |
| `OptionalArg` | Uses `--name value` syntax            |
| `DefaultArg`  | Falls back to default when unprovided |
| `FlagArg`     | Boolean switches without values       |

Argument types via `ArgTypes` class:

- `STRING` - Text
- `INTEGER` - Whole numbers with optional range
- `DOUBLE` - Decimals
- `BOOLEAN` - true/false
- `ListArgumentType<T>` - Multiple values

Argument values retrieved via `arg.get(context)`.

**Source:** [HytaleDocs - Commands](https://hytale-docs.pages.dev/modding/plugins/commands/)

### Subcommand Support

`AbstractCommandCollection` groups related subcommands:

```java
public class AdminCommands extends AbstractCommandCollection {
    // Contains /admin kick, /admin ban, /admin mute
}
```

**Source:** [HytaleDocs - Commands](https://hytale-docs.pages.dev/modding/plugins/commands/)

## Outcome

**Status:** Confirmed

Hytale provides a well-documented command registration system with:

- Plugin registry for automatic cleanup
- Base classes for player and general commands
- Typed argument parsing with multiple modes
- Subcommand grouping support
- Async execution by default

## Impact

### On Decisions

| Decision             | Impact                                                         |
| -------------------- | -------------------------------------------------------------- |
| Command architecture | Use standard AbstractPlayerCommand; no custom framework needed |

### On Issues

| Issue                       | Impact                                   |
| --------------------------- | ---------------------------------------- |
| All command implementations | No change; proceed with standard pattern |

## Recommendations

- Use `AbstractPlayerCommand` for all claim and party commands (all are player-initiated)
- Consider `AbstractCommandCollection` for grouping party commands under `/party`
- Return `CompletableFuture.completedFuture(null)` for synchronous operations
- Register all commands in plugin `setup()` using `getCommandRegistry()`
