# Milestone Analysis: Core Foundation

## Scope

### Outcomes

**User (Player):** When this milestone is done, a player can claim chunks of land, see their claims listed, have blocks protected from other players, create/join parties, and have all data persist across server restarts.

**Admin (Server Operator):** When this milestone is done, an admin can install the plugin, configure basic settings, and observe the claim system functioning.

**API Client (Other Mods):** When this milestone is done, an API client can query claim ownership and check if a player can build at a location.

### Scope Boundaries

**Included:**

- Chunk-based claiming (one claim = one or more chunks)
- Basic claim ownership (single player owns claim)
- Party system with roles (Owner, Officer, Member)
- Block break/place protection
- Container access protection
- Entity damage protection (PvP in claims)
- JSON file persistence
- Core commands for claim and party management
- Public API interfaces for external integration
- Configuration with sensible defaults

**Excluded:**

- Trust system (Milestone: Trust System)
- Protection flags beyond basic deny (Milestone: Protection Flags)
- Boundary visualization (Milestone: Visualization)
- Claim block earning/limits (Milestone: Claim Earning)
- Claim expiration (Milestone: Claim Expiration)
- Subclaims (Milestone: Subclaims)
- Party relations (ally/enemy) (Milestone: Party Relations)
- Database storage (Milestone: Advanced Features)
- GUI interfaces
- Localization/i18n

### Ambiguity Resolution

| Term/Item           | Clarification                                                                                                                                                             |
| ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| "Basic Protection"  | Block break, block place, container access, and entity damage (PvP) only                                                                                                  |
| "Party System"      | Create party, invite players, accept/deny invites, kick members, leave party, disband party                                                                               |
| "Chunk-based claim" | Player claims entire 16x16 chunk columns; no arbitrary regions in v0                                                                                                      |
| "API Module"        | Interfaces only in v0; implementations in core module                                                                                                                     |
| "Basic Commands"    | /claim, /unclaim, /claimlist, /claiminfo for claims; /partycreate, /partyinvite, /partyaccept, /partydeny, /partykick, /partyleave, /partydisband, /partyinfo for parties |
| "Configuration"     | Single JSON config file with claim limits, default protection behavior                                                                                                    |

### Gaps Identified

| Gap                            | Status    | Rationale                                                            |
| ------------------------------ | --------- | -------------------------------------------------------------------- |
| Claim size limits              | Addressed | Add to config: max chunks per claim, max claims per player           |
| Starting claim blocks          | Deferred  | Milestone: Claim Earning handles this; v0 uses unlimited or fixed config value |
| Overlap prevention             | Addressed | ClaimManager must check for existing claims before creating          |
| Player offline handling        | Addressed | Claims persist; owner identified by UUID                             |
| Party claim ownership          | Deferred  | v0: claims owned by individual players, not parties                  |
| Permission integration         | Deferred  | v0: owner-only protection; Milestone: Trust System adds trust levels |
| Hytale event API compatibility | Spike     | Need to verify Hytale server provides required events                |
| Claim transfer                 | Deferred  | Not essential for v0 MVP                                             |

---

## Classification

### Decisions (Require ADR)

| Decision                  | Options                                                                   | Assigned To | Blocks                             |
| ------------------------- | ------------------------------------------------------------------------- | ----------- | ---------------------------------- |
| Claim storage format      | Per-world JSON files, Per-chunk JSON files, Single claims.json            | Tech Lead   | File Storage implementation        |
| Chunk coordinate handling | Hytale native coords, Custom ChunkCoordinate record, World-qualified      | Tech Lead   | ClaimCache, ClaimManager           |
| Party-claim relationship  | Claims linked to party, Claims linked to player with party access, Hybrid | Tech Lead   | Claim data model, Party data model |
| API versioning strategy   | Package versioning, Interface versioning, Semantic versioning only        | Tech Lead   | API Module structure               |

### Issues (Ready for Work)

| Issue                                         | Type    | Acceptance Criteria                                                  |
| --------------------------------------------- | ------- | -------------------------------------------------------------------- |
| Set up Gradle multi-module project            | chore   | `./gradlew build` succeeds with api and core modules                 |
| Create plugin entry point (Capital.java)      | feature | Plugin loads, logs startup message, registers with Hytale server     |
| Implement ChunkCoordinate record              | feature | Immutable record with world, x, z; equals/hashCode for map keys      |
| Implement Claim data class                    | feature | Stores owner UUID, chunk set, creation timestamp, name               |
| Implement ClaimManager                        | feature | Create, delete, get-by-chunk, get-by-owner operations                |
| Implement ClaimCache                          | feature | O(1) lookup by chunk coordinate; cache invalidation on claim changes |
| Implement Party data class                    | feature | Stores name, owner UUID, member list with roles, creation timestamp  |
| Implement PartyMember data class              | feature | Stores player UUID, role enum, join timestamp                        |
| Implement PartyManager                        | feature | Create, disband, invite, accept, deny, kick, leave, promote, demote  |
| Implement InviteManager                       | feature | Track pending invites, auto-expire after configurable timeout        |
| Implement JsonStorage base class              | feature | Read/write JSON with Gson, file locking, backup on write             |
| Implement ClaimFileStorage                    | feature | Persist claims per storage format decision                           |
| Implement PartyFileStorage                    | feature | Persist parties to JSON                                              |
| Implement PlayerFileStorage                   | feature | Persist per-player data (claim blocks, settings)                     |
| Implement ProtectionManager                   | feature | Central check: canBuild(player, location) returns allow/deny         |
| Implement BlockBreakListener                  | feature | Cancel block break if player lacks permission                        |
| Implement BlockPlaceListener                  | feature | Cancel block place if player lacks permission                        |
| Implement ContainerListener                   | feature | Cancel container open if player lacks permission                     |
| Implement EntityDamageListener                | feature | Cancel PvP damage in claims (owner-only can damage)                  |
| Implement ClaimCommand (/claim)               | feature | Player claims chunk(s) they stand in; error if already claimed       |
| Implement UnclaimCommand (/unclaim)           | feature | Player unclaims current chunk; error if not owner                    |
| Implement ClaimListCommand (/claimlist)       | feature | Lists player's claims with coordinates                               |
| Implement ClaimInfoCommand (/claiminfo)       | feature | Shows info about claim at current location                           |
| Implement PartyCreateCommand (/partycreate)   | feature | Creates party with player as owner                                   |
| Implement PartyDisbandCommand (/partydisband) | feature | Disbands party if player is owner                                    |
| Implement PartyInviteCommand (/partyinvite)   | feature | Sends invite to target player                                        |
| Implement PartyAcceptCommand (/partyaccept)   | feature | Accepts pending party invite                                         |
| Implement PartyDenyCommand (/partydeny)       | feature | Denies pending party invite                                          |
| Implement PartyKickCommand (/partykick)       | feature | Kicks member if player has authority                                 |
| Implement PartyLeaveCommand (/partyleave)     | feature | Removes player from party                                            |
| Implement PartyInfoCommand (/partyinfo)       | feature | Shows party details and member list                                  |
| Implement ConfigManager                       | feature | Load config.json with defaults, hot-reload support                   |
| Implement CapitalConfig                       | feature | Typed config class with claim limits, protection defaults            |
| Create API interfaces (IClaim, IClaimManager) | feature | Public interfaces in api module for external mod use                 |
| Create API interfaces (IParty, IPartyManager) | feature | Public interfaces in api module for external mod use                 |
| Create CapitalAPI facade                      | feature | Single entry point for API consumers                                 |
| Implement CapitalAPIImpl                      | feature | Core module implementation of API interfaces                         |

### Hybrids (Decision Extracted)

| Original Item              | Extracted Decision        | Remaining Issue            |
| -------------------------- | ------------------------- | -------------------------- |
| Implement ClaimFileStorage | Claim storage format      | Implement ClaimFileStorage |
| Implement ClaimCache       | Chunk coordinate handling | Implement ClaimCache       |
| Implement Claim data class | Party-claim relationship  | Implement Claim data class |

---

## Dependencies

### Dependency Graph

```
[Set up Gradle multi-module project]
    ↓
[Create plugin entry point] → parallel: [ADR: Chunk coordinate handling], [ADR: Claim storage format], [ADR: Party-claim relationship], [ADR: API versioning]
    ↓
[Implement ChunkCoordinate record] (after chunk coord ADR)
    ↓
[Implement Claim data class] (after party-claim ADR)
    ↓
[Implement ClaimManager] → parallel: [Implement ClaimCache]
    ↓
[Implement JsonStorage base class]
    ↓
[Implement ClaimFileStorage] (after storage format ADR) → parallel: [Implement PartyFileStorage], [Implement PlayerFileStorage]
    ↓
(sync point: storage complete)
    ↓
[Implement Party data class] → parallel: [Implement PartyMember data class]
    ↓
[Implement PartyManager] → [Implement InviteManager]
    ↓
(sync point: data layer complete)
    ↓
[Implement ProtectionManager]
    ↓
parallel: [BlockBreakListener], [BlockPlaceListener], [ContainerListener], [EntityDamageListener]
    ↓
(sync point: protection complete)
    ↓
[Implement ConfigManager] → [Implement CapitalConfig]
    ↓
parallel: [Claim commands group], [Party commands group]
    ↓
(sync point: commands complete)
    ↓
[Create API interfaces] → [Create CapitalAPI facade] → [Implement CapitalAPIImpl]
```

### Parallelisation Points

| Branch Point             | Parallel Items                                        | Sync Point             |
| ------------------------ | ----------------------------------------------------- | ---------------------- |
| After plugin entry point | All 4 ADR decisions                                   | Before ChunkCoordinate |
| After JsonStorage base   | ClaimFileStorage, PartyFileStorage, PlayerFileStorage | Storage complete       |
| After Party data class   | PartyMember data class (trivial)                      | Before PartyManager    |
| After ProtectionManager  | All 4 protection listeners                            | Protection complete    |
| After CapitalConfig      | All claim commands, all party commands                | Commands complete      |

---

## Risks

### Technical Uncertainties

| Risk                                              | Likelihood | Impact | Investigation Cost |
| ------------------------------------------------- | ---------- | ------ | ------------------ |
| Hytale event API lacks required events            | Medium     | High   | 4 hours            |
| Hytale command registration differs from expected | Medium     | Medium | 2 hours            |
| Chunk coordinate system differs from expectations | Low        | Medium | 2 hours            |
| JSON serialization performance at scale           | Low        | Low    | 2 hours            |
| Hytale permission system integration              | Medium     | Medium | 2 hours            |
| Plugin lifecycle hooks unavailable                | Low        | High   | 2 hours            |

### Spike Assignments

| Risk                                   | Spike Question                                                                                      | Timebox | Assigned To |
| -------------------------------------- | --------------------------------------------------------------------------------------------------- | ------- | ----------- |
| Hytale event API lacks required events | Does Hytale provide cancellable events for block break, block place, container open, entity damage? | 4 hours | Tech Lead   |
| Hytale command registration            | How does Hytale register commands? What's the argument parsing API?                                 | 2 hours | Tech Lead   |
| Plugin lifecycle hooks                 | Does Hytale provide onEnable/onDisable lifecycle hooks for plugins?                                 | 2 hours | Tech Lead   |

### Accepted Risks

| Risk                                 | Rationale                                                         |
| ------------------------------------ | ----------------------------------------------------------------- |
| Chunk coordinate system              | Can adapt implementation; Hytale uses standard voxel world model  |
| JSON serialization performance       | File I/O is acceptable for v0; database storage planned for v1    |
| Hytale permission system integration | Can implement basic owner-only checks; defer integration to later |
