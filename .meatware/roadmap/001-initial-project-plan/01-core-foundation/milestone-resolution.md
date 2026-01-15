# Milestone Resolution: Core Foundation

Date: 2026-01-15

## Milestone Outcome

**Player:** Claim chunks of land, see claims listed, have blocks protected from other players, create/join parties, and have all data persist across server restarts.

**Admin:** Install the plugin, configure basic settings, and observe the claim system functioning.

**API Client:** Query claim ownership and check if a player can build at a location.

## Decision Outcomes Applied

| ADR                                                        | Decision                      | Affected Issues |
| ---------------------------------------------------------- | ----------------------------- | --------------- |
| [ADR-0001](../../../adr/0001-claim-storage-format.md)      | Single claims.json file       | 7, 8            |
| [ADR-0002](../../../adr/0002-chunk-coordinate-handling.md) | Custom ChunkCoordinate record | 3, 4, 5         |
| [ADR-0003](../../../adr/0003-party-claim-relationship.md)  | Claims linked to player UUID  | 4, 9, 10, 11    |
| [ADR-0004](../../../adr/0004-api-versioning-strategy.md)   | Semantic versioning only      | 20, 21          |

## Spike Findings Applied

| Spike                                                                              | Outcome                                                                                    | Affected Issues |
| ---------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------ | --------------- |
| [spike-hytale-event-api](./spikes/spike-hytale-event-api.md)                       | Block events confirmed; container via UseBlockEvent.Pre; entity damage needs investigation | 14, 15, 16      |
| [spike-hytale-command-registration](./spikes/spike-hytale-command-registration.md) | AbstractPlayerCommand pattern with typed arguments                                         | 17, 18          |
| [spike-plugin-lifecycle-hooks](./spikes/spike-plugin-lifecycle-hooks.md)           | Full lifecycle: preLoad(), setup(), start(), shutdown()                                    | 2, 6            |

---

## Issue List

| #   | Issue                                                                             | Type    | Assignment         | Model             | Blocked By |
| --- | --------------------------------------------------------------------------------- | ------- | ------------------ | ----------------- | ---------- |
| 1   | [Set up Gradle multi-module project](./issues/1-gradle-multi-module-setup.md)     | chore   | Tech Lead          | claude-opus-4-5   | —          |
| 2   | [Create plugin entry point](./issues/2-plugin-entry-point.md)                     | feature | Senior Engineer    | claude-opus-4-5   | 1          |
| 3   | [Implement ChunkCoordinate record](./issues/3-chunk-coordinate-record.md)         | feature | Mid-level Engineer | claude-sonnet-4-5 | 2          |
| 4   | [Implement Claim data class](./issues/4-claim-data-class.md)                      | feature | Mid-level Engineer | claude-sonnet-4-5 | 3          |
| 5   | [Implement ClaimManager and ClaimCache](./issues/5-claim-manager-cache.md)        | feature | Senior Engineer    | claude-opus-4-5   | 4          |
| 6   | [Implement JsonStorage base class](./issues/6-json-storage-base.md)               | feature | Senior Engineer    | claude-opus-4-5   | 2          |
| 7   | [Implement ClaimFileStorage](./issues/7-claim-file-storage.md)                    | feature | Mid-level Engineer | claude-sonnet-4-5 | 5, 6       |
| 8   | [Implement PlayerFileStorage](./issues/8-player-file-storage.md)                  | feature | Mid-level Engineer | claude-sonnet-4-5 | 6          |
| 9   | [Implement Party and PartyMember data classes](./issues/9-party-data-classes.md)  | feature | Mid-level Engineer | claude-sonnet-4-5 | 2          |
| 10  | [Implement PartyManager](./issues/10-party-manager.md)                            | feature | Senior Engineer    | claude-opus-4-5   | 9          |
| 11  | [Implement InviteManager](./issues/11-invite-manager.md)                          | feature | Mid-level Engineer | claude-sonnet-4-5 | 10         |
| 12  | [Implement PartyFileStorage](./issues/12-party-file-storage.md)                   | feature | Mid-level Engineer | claude-sonnet-4-5 | 6, 10      |
| 13  | [Implement ProtectionManager](./issues/13-protection-manager.md)                  | feature | Senior Engineer    | claude-opus-4-5   | 5          |
| 14  | [Implement block protection listeners](./issues/14-block-protection-listeners.md) | feature | Mid-level Engineer | claude-sonnet-4-5 | 13         |
| 15  | [Implement ContainerListener](./issues/15-container-listener.md)                  | feature | Mid-level Engineer | claude-sonnet-4-5 | 13         |
| 16  | [Implement EntityDamageListener](./issues/16-entity-damage-listener.md)           | feature | Senior Engineer    | claude-opus-4-5   | 13         |
| 17  | [Implement claim commands](./issues/17-claim-commands.md)                         | feature | Mid-level Engineer | claude-sonnet-4-5 | 5, 7, 13   |
| 18  | [Implement party commands](./issues/18-party-commands.md)                         | feature | Mid-level Engineer | claude-sonnet-4-5 | 10, 11, 12 |
| 19  | [Implement ConfigManager and CapitalConfig](./issues/19-config-manager.md)        | feature | Mid-level Engineer | claude-sonnet-4-5 | 2          |
| 20  | [Create API interfaces](./issues/20-api-interfaces.md)                            | feature | Tech Lead          | claude-opus-4-5   | 5, 10      |
| 21  | [Implement CapitalAPI facade](./issues/21-capital-api-facade.md)                  | feature | Senior Engineer    | claude-opus-4-5   | 20         |

---

## Execution Order

```
[1: Gradle multi-module setup]
    |
    v
[2: Plugin entry point]
    |
    +---> [3: ChunkCoordinate] ---> [4: Claim data] ---> [5: ClaimManager/Cache]
    |                                                           |
    |                                                           +---> [7: ClaimFileStorage]
    |                                                           |
    |                                                           +---> [13: ProtectionManager]
    |                                                                       |
    |                                                                       +---> parallel: [14: Block listeners], [15: Container], [16: Entity]
    |
    +---> [6: JsonStorage base]
    |           |
    |           +---> [7: ClaimFileStorage] (sync with 5)
    |           +---> [8: PlayerFileStorage]
    |           +---> [12: PartyFileStorage] (sync with 10)
    |
    +---> [9: Party data classes] ---> [10: PartyManager] ---> [11: InviteManager]
    |                                         |
    |                                         +---> [12: PartyFileStorage]
    |
    +---> [19: ConfigManager]

[17: Claim commands] (after 5, 7, 13)
[18: Party commands] (after 10, 11, 12)
[20: API interfaces] (after 5, 10)
[21: CapitalAPI facade] (after 20)
```

---

## Human-Only Issues

_None identified_

---

## Labels Reference

| Label         | Colour           | Description                              |
| ------------- | ---------------- | ---------------------------------------- |
| `feature`     | Green `#0e8a16`  | New functionality                        |
| `chore`       | Grey `#cfd3d7`   | Maintenance, refactoring, no user impact |
| `docs`        | Yellow `#fef2c0` | Documentation only                       |
| `bug`         | Red `#d73a4a`    | Something isn't working                  |
| `enhancement` | Blue `#a2eeef`   | Improvement to existing functionality    |
| `blocked`     | Red `#b60205`    | Waiting on dependency or external factor |
