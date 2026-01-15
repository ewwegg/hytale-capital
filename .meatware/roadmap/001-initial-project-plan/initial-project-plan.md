# Capital - Repository Structure

> A comprehensive land claiming and protection mod for Hytale
>
> This document serves as both a repository map and development roadmap.

---

## Table of Contents

1. [Repository Overview](#repository-overview)
2. [Directory Structure](#directory-structure)
3. [File Descriptions](#file-descriptions)
4. [Implementation Milestones](#implementation-milestones)
5. [Contributing Guidelines](#contributing-guidelines)

---

## Repository Overview

```
Capital/
├── 📁 .github/                    # GitHub-specific files
├── 📁 api/                        # Public API module (for other mods)
├── 📁 core/                       # Main mod implementation
├── 📁 docs/                       # Documentation
├── 📁 resources/                  # Assets (UI, textures, lang)
├── 📁 scripts/                    # Build and utility scripts
├── 📄 build.gradle                # Root build configuration
├── 📄 settings.gradle             # Multi-module settings
├── 📄 gradle.properties           # Shared properties
├── 📄 LICENSE                     # License file
├── 📄 README.md                   # Project overview
├── 📄 CHANGELOG.md                # Version history
└── 📄 ROADMAP.md                  # Feature roadmap
```

---

## Directory Structure

### 📁 .github/ — GitHub Configuration

```
.github/
├── 📁 ISSUE_TEMPLATE/
│   ├── 📄 bug_report.yml              # Bug report template
│   ├── 📄 feature_request.yml         # Feature request template
│   └── 📄 config.yml                   # Issue template config
├── 📁 workflows/
│   ├── 📄 build.yml                    # CI build pipeline
│   ├── 📄 release.yml                  # Release automation
│   ├── 📄 test.yml                     # Test runner
│   └── 📄 codeql.yml                   # Security scanning
├── 📄 CONTRIBUTING.md                  # Contribution guidelines
├── 📄 PULL_REQUEST_TEMPLATE.md         # PR template
├── 📄 CODE_OF_CONDUCT.md               # Community guidelines
├── 📄 SECURITY.md                      # Security policy
└── 📄 FUNDING.yml                      # Sponsorship info
```

---

### 📁 api/ — Public API Module

```
api/
├── 📄 build.gradle
└── 📁 src/main/java/dev/ewwegg/capital/api/
    │
    ├── 📄 CapitalAPI.java                    # Main API interface
    ├── 📄 CapitalProvider.java               # API instance provider
    │
    ├── 📁 claim/
    │   ├── 📄 IClaim.java                         # Claim interface
    │   ├── 📄 IClaimManager.java                  # Claim management interface
    │   ├── 📄 ClaimPosition.java                  # Immutable position record
    │   └── 📄 ClaimBounds.java                    # Claim boundary definition
    │
    ├── 📁 party/
    │   ├── 📄 IParty.java                         # Party interface
    │   ├── 📄 IPartyManager.java                  # Party management interface
    │   ├── 📄 PartyRole.java                      # Role enum (OWNER, OFFICER, MEMBER, TRUSTED, VISITOR)
    │   └── 📄 PartyRelation.java                  # Relation enum (ALLY, NEUTRAL, ENEMY)
    │
    ├── 📁 permission/
    │   ├── 📄 ITrustLevel.java                    # Trust level interface
    │   ├── 📄 TrustType.java                      # Trust type enum
    │   ├── 📄 IClaimFlag.java                     # Flag interface
    │   ├── 📄 FlagState.java                      # Flag state enum (ALLOW, DENY, DEFAULT)
    │   └── 📄 IPermissionQuery.java               # Permission check interface
    │
    ├── 📁 event/
    │   ├── 📄 ClaimEvent.java                     # Base claim event
    │   ├── 📄 ClaimCreateEvent.java               # Fired when claim created
    │   ├── 📄 ClaimDeleteEvent.java               # Fired when claim deleted
    │   ├── 📄 ClaimResizeEvent.java               # Fired when claim resized
    │   ├── 📄 ClaimTransferEvent.java             # Fired when ownership changes
    │   ├── 📄 ClaimEnterEvent.java                # Fired when player enters claim
    │   ├── 📄 ClaimExitEvent.java                 # Fired when player exits claim
    │   ├── 📄 PartyEvent.java                     # Base party event
    │   ├── 📄 PartyCreateEvent.java               # Fired when party created
    │   ├── 📄 PartyDisbandEvent.java              # Fired when party disbanded
    │   ├── 📄 PartyMemberJoinEvent.java           # Fired when member joins
    │   ├── 📄 PartyMemberLeaveEvent.java          # Fired when member leaves
    │   └── 📄 TrustChangeEvent.java               # Fired when trust modified
    │
    ├── 📁 economy/
    │   ├── 📄 IEconomyProvider.java               # Economy integration interface
    │   ├── 📄 IClaimTransaction.java              # Transaction interface
    │   └── 📄 ClaimCurrency.java                  # Claim blocks/currency types
    │
    ├── 📁 integration/
    │   ├── 📄 IMapIntegration.java                # World map integration
    │   ├── 📄 IPermissionIntegration.java         # Permission plugin integration
    │   └── 📄 ILoggingIntegration.java            # Block logging integration
    │
    └── 📁 exception/
        ├── 📄 ClaimException.java                 # Base exception
        ├── 📄 ClaimOverlapException.java          # Overlapping claims
        ├── 📄 InsufficientClaimBlocksException.java # Not enough blocks
        └── 📄 NoPermissionException.java          # Permission denied
```

---

### 📁 core/ — Main Implementation

```
core/
├── 📄 build.gradle
└── 📁 src/
    ├── 📁 main/java/dev/ewwegg/capital/
    │   │
    │   ├── 📄 Capital.java                   # Main mod class, entry point
    │   ├── 📄 CapitalAPIImpl.java            # API implementation
    │   │
    │   ├── 📁 claim/
    │   │   ├── 📄 ClaimManager.java               # Central claim management
    │   │   ├── 📄 Claim.java                      # Claim implementation
    │   │   ├── 📄 ClaimCache.java                 # Spatial lookup cache
    │   │   │
    │   │   ├── 📁 chunk/
    │   │   │   ├── 📄 ChunkClaim.java             # Chunk-based claim type
    │   │   │   ├── 📄 ChunkClaimStorage.java      # Chunk claim persistence
    │   │   │   └── 📄 ChunkCoordinate.java        # Chunk coordinate helper
    │   │   │
    │   │   ├── 📁 region/
    │   │   │   ├── 📄 RegionClaim.java            # Rectangular region claim [FUTURE]
    │   │   │   ├── 📄 RegionClaimStorage.java     # Region claim persistence [FUTURE]
    │   │   │   └── 📄 CuboidRegion.java           # 3D region definition [FUTURE]
    │   │   │
    │   │   ├── 📁 subclaim/
    │   │   │   ├── 📄 Subclaim.java               # Subdivision within claim
    │   │   │   ├── 📄 SubclaimManager.java        # Subclaim management
    │   │   │   └── 📄 SubclaimStorage.java        # Subclaim persistence
    │   │   │
    │   │   └── 📁 expiration/
    │   │       ├── 📄 ClaimExpirationManager.java # Handles inactive claim cleanup
    │   │       ├── 📄 ExpirationConfig.java       # Expiration timing settings
    │   │       └── 📄 ExpirationWarning.java      # Warning notification logic
    │   │
    │   ├── 📁 party/
    │   │   ├── 📄 PartyManager.java               # Central party management
    │   │   ├── 📄 Party.java                      # Party implementation
    │   │   ├── 📄 PartyStorage.java               # Party persistence
    │   │   │
    │   │   ├── 📁 member/
    │   │   │   ├── 📄 PartyMember.java            # Member data (UUID, role, joinDate)
    │   │   │   ├── 📄 MemberPermissions.java      # Per-member permission overrides
    │   │   │   └── 📄 MemberActivity.java         # Activity tracking for members
    │   │   │
    │   │   ├── 📁 invite/
    │   │   │   ├── 📄 PartyInvite.java            # Invitation data
    │   │   │   ├── 📄 InviteManager.java          # Invitation lifecycle
    │   │   │   └── 📄 InviteExpiration.java       # Auto-expire invitations
    │   │   │
    │   │   ├── 📁 relation/
    │   │   │   ├── 📄 PartyRelationship.java      # Ally/Neutral/Enemy state
    │   │   │   ├── 📄 RelationshipManager.java    # Manage inter-party relations
    │   │   │   └── 📄 AllianceRequest.java        # Alliance request flow
    │   │   │
    │   │   ├── 📁 chat/
    │   │   │   ├── 📄 PartyChatChannel.java       # Private party chat
    │   │   │   ├── 📄 AllyChatChannel.java        # Allied parties chat
    │   │   │   └── 📄 ChatManager.java            # Chat channel routing
    │   │   │
    │   │   └── 📁 bank/
    │   │       ├── 📄 PartyBank.java              # Shared party storage [FUTURE]
    │   │       └── 📄 BankTransaction.java        # Bank transaction log [FUTURE]
    │   │
    │   ├── 📁 permission/
    │   │   ├── 📄 PermissionManager.java          # Central permission checks
    │   │   ├── 📄 PermissionResolver.java         # Resolves effective permissions
    │   │   │
    │   │   ├── 📁 trust/
    │   │   │   ├── 📄 TrustLevel.java             # Trust level implementation
    │   │   │   ├── 📄 TrustManager.java           # Trust assignment/lookup
    │   │   │   ├── 📄 TrustStorage.java           # Trust persistence
    │   │   │   │
    │   │   │   └── 📁 types/
    │   │   │       ├── 📄 AccessTrust.java        # Interact with buttons, doors
    │   │   │       ├── 📄 ContainerTrust.java     # Open chests, furnaces
    │   │   │       ├── 📄 BuildTrust.java         # Place/break blocks
    │   │   │       └── 📄 PermissionTrust.java    # Can grant trust to others
    │   │   │
    │   │   └── 📁 flag/
    │   │       ├── 📄 ClaimFlag.java              # Flag implementation
    │   │       ├── 📄 FlagManager.java            # Flag registration & lookup
    │   │       ├── 📄 FlagStorage.java            # Flag persistence
    │   │       ├── 📄 FlagRegistry.java           # Plugin-registered flags
    │   │       │
    │   │       └── 📁 builtin/
    │   │           │
    │   │           ├── 📁 block/
    │   │           │   ├── 📄 BlockPlaceFlag.java          # Allow/deny block placement
    │   │           │   ├── 📄 BlockBreakFlag.java          # Allow/deny block breaking
    │   │           │   ├── 📄 BlockInteractFlag.java       # Allow/deny block interaction
    │   │           │   └── 📄 BlockGrowthFlag.java         # Allow/deny crop growth
    │   │           │
    │   │           ├── 📁 entity/
    │   │           │   ├── 📄 PvpFlag.java                 # Allow/deny PVP
    │   │           │   ├── 📄 MobSpawnFlag.java            # Allow/deny mob spawning
    │   │           │   ├── 📄 MobDamageFlag.java           # Allow/deny mob damage
    │   │           │   ├── 📄 AnimalBreedingFlag.java      # Allow/deny breeding
    │   │           │   ├── 📄 LeashBreakFlag.java          # Prevent leash breaking
    │   │           │   └── 📄 VehicleUseFlag.java          # Allow/deny vehicle use
    │   │           │
    │   │           ├── 📁 environment/
    │   │           │   ├── 📄 FireSpreadFlag.java          # Allow/deny fire spread
    │   │           │   ├── 📄 ExplosionFlag.java           # Allow/deny explosions
    │   │           │   ├── 📄 LavaFlowFlag.java            # Allow/deny lava flow
    │   │           │   ├── 📄 WaterFlowFlag.java           # Allow/deny water flow
    │   │           │   ├── 📄 IceMeltFlag.java             # Allow/deny ice melting
    │   │           │   ├── 📄 SnowFormFlag.java            # Allow/deny snow formation
    │   │           │   ├── 📄 LeafDecayFlag.java           # Allow/deny leaf decay
    │   │           │   └── 📄 CropTrampleFlag.java         # Prevent farmland trampling
    │   │           │
    │   │           ├── 📁 player/
    │   │           │   ├── 📄 EntryFlag.java               # Allow/deny entry
    │   │           │   ├── 📄 TeleportFlag.java            # Allow/deny teleporting in
    │   │           │   ├── 📄 EnderPearlFlag.java          # Allow/deny ender pearl entry
    │   │           │   ├── 📄 FlightFlag.java              # Allow/deny flight
    │   │           │   ├── 📄 ItemDropFlag.java            # Allow/deny item drops
    │   │           │   ├── 📄 ItemPickupFlag.java          # Allow/deny item pickup
    │   │           │   └── 📄 XpPickupFlag.java            # Allow/deny XP pickup
    │   │           │
    │   │           └── 📁 redstone/
    │   │               ├── 📄 PistonFlag.java              # Protect against pistons
    │   │               └── 📄 RedstoneCrossFlag.java       # Redstone across boundaries
    │   │
    │   ├── 📁 protection/
    │   │   ├── 📄 ProtectionManager.java          # Central protection logic
    │   │   ├── 📄 ProtectionResult.java           # Result of protection check
    │   │   │
    │   │   └── 📁 listener/
    │   │       ├── 📄 BlockBreakListener.java     # Intercepts block breaks
    │   │       ├── 📄 BlockPlaceListener.java     # Intercepts block placements
    │   │       ├── 📄 BlockInteractListener.java  # Intercepts interactions
    │   │       ├── 📄 ContainerListener.java      # Intercepts container access
    │   │       ├── 📄 EntityDamageListener.java   # Intercepts entity damage
    │   │       ├── 📄 EntitySpawnListener.java    # Intercepts mob spawns
    │   │       ├── 📄 PlayerMoveListener.java     # Tracks claim entry/exit
    │   │       ├── 📄 ExplosionListener.java      # Handles explosions
    │   │       ├── 📄 PistonListener.java         # Handles piston protection
    │   │       ├── 📄 RedstoneListener.java       # Handles redstone crossing
    │   │       ├── 📄 ItemListener.java           # Handles item drop/pickup
    │   │       └── 📄 VehicleListener.java        # Handles vehicle interactions
    │   │
    │   ├── 📁 earning/
    │   │   ├── 📄 ClaimBlockManager.java          # Tracks earned claim blocks
    │   │   ├── 📄 ClaimBlockStorage.java          # Claim block persistence
    │   │   │
    │   │   ├── 📁 source/
    │   │   │   ├── 📄 IClaimBlockSource.java      # Interface for earning sources
    │   │   │   ├── 📄 PlaytimeSource.java         # Earn blocks over time played
    │   │   │   ├── 📄 AchievementSource.java      # Earn blocks from achievements
    │   │   │   ├── 📄 VoteSource.java             # Earn blocks from voting
    │   │   │   └── 📄 PurchaseSource.java         # Buy blocks with currency
    │   │   │
    │   │   └── 📁 bonus/
    │   │       ├── 📄 BonusClaimBlocks.java       # Admin-granted bonus blocks
    │   │       └── 📄 PermissionBonus.java        # Bonus based on permissions
    │   │
    │   ├── 📁 visualization/
    │   │   ├── 📄 VisualizationManager.java       # Central visualization control
    │   │   │
    │   │   ├── 📁 boundary/
    │   │   │   ├── 📄 BoundaryRenderer.java       # Renders claim boundaries
    │   │   │   ├── 📄 ParticleRenderer.java       # Particle-based boundaries
    │   │   │   ├── 📄 BlockRenderer.java          # Temporary block visualization
    │   │   │   └── 📄 BeaconRenderer.java         # Corner beacon beams
    │   │   │
    │   │   ├── 📁 map/
    │   │   │   ├── 📄 WorldMapProvider.java       # Hytale map integration
    │   │   │   ├── 📄 ClaimMapRenderer.java       # Renders claims on map
    │   │   │   ├── 📄 MapColorPalette.java        # Color scheme for map
    │   │   │   └── 📄 DynmapIntegration.java      # External map support [FUTURE]
    │   │   │
    │   │   └── 📁 inspection/
    │   │       ├── 📄 ClaimInspector.java         # Inspection tool logic
    │   │       ├── 📄 InspectionResult.java       # Result data structure
    │   │       └── 📄 InspectionDisplay.java      # UI for showing info
    │   │
    │   ├── 📁 teleport/
    │   │   ├── 📄 TeleportManager.java            # Central teleport handling
    │   │   ├── 📄 HomeLocation.java               # Home position data
    │   │   ├── 📄 HomeStorage.java                # Home persistence
    │   │   │
    │   │   ├── 📁 command/
    │   │   │   ├── 📄 HomeCommand.java            # /home command
    │   │   │   ├── 📄 SetHomeCommand.java         # /sethome command
    │   │   │   ├── 📄 PartyHomeCommand.java       # /partyhome command
    │   │   │   └── 📄 VisitCommand.java           # /visit <player> command
    │   │   │
    │   │   └── 📁 warmup/
    │   │       ├── 📄 TeleportWarmup.java         # Warmup timer before teleport
    │   │       ├── 📄 WarmupConfig.java           # Warmup configuration
    │   │       └── 📄 CooldownManager.java        # Cooldown between teleports
    │   │
    │   ├── 📁 logging/
    │   │   ├── 📄 ActivityLogger.java             # Central activity logging
    │   │   ├── 📄 LogEntry.java                   # Log entry structure
    │   │   ├── 📄 LogStorage.java                 # Log persistence (SQLite)
    │   │   │
    │   │   ├── 📁 action/
    │   │   │   ├── 📄 LogAction.java              # Action type enum
    │   │   │   ├── 📄 BlockChangeAction.java      # Block place/break log
    │   │   │   ├── 📄 ContainerAccessAction.java  # Container access log
    │   │   │   ├── 📄 EntityKillAction.java       # Entity kill log
    │   │   │   └── 📄 TrustChangeAction.java      # Trust modification log
    │   │   │
    │   │   ├── 📁 query/
    │   │   │   ├── 📄 LogQuery.java               # Query builder
    │   │   │   ├── 📄 LogQueryResult.java         # Query result set
    │   │   │   └── 📄 LookupCommand.java          # /scp lookup command
    │   │   │
    │   │   └── 📁 rollback/
    │   │       ├── 📄 RollbackManager.java        # Rollback execution
    │   │       ├── 📄 RollbackPreview.java        # Preview before rollback
    │   │       └── 📄 RollbackCommand.java        # /scp rollback command
    │   │
    │   ├── 📁 economy/
    │   │   ├── 📄 EconomyManager.java             # Economy integration hub
    │   │   │
    │   │   ├── 📁 shop/
    │   │   │   ├── 📄 ClaimShop.java              # Buy/sell claim blocks
    │   │   │   ├── 📄 ShopConfig.java             # Pricing configuration
    │   │   │   └── 📄 ShopCommand.java            # /scp shop command
    │   │   │
    │   │   ├── 📁 rental/
    │   │   │   ├── 📄 RentalManager.java          # Land rental system [FUTURE]
    │   │   │   ├── 📄 RentalAgreement.java        # Rental contract [FUTURE]
    │   │   │   └── 📄 RentalPayment.java          # Automatic payments [FUTURE]
    │   │   │
    │   │   └── 📁 tax/
    │   │       ├── 📄 TaxManager.java             # Claim upkeep system [FUTURE]
    │   │       ├── 📄 TaxConfig.java              # Tax rates [FUTURE]
    │   │       └── 📄 TaxCollector.java           # Automatic collection [FUTURE]
    │   │
    │   ├── 📁 command/
    │   │   ├── 📄 CommandManager.java             # Command registration
    │   │   ├── 📄 CommandMessages.java            # Localized messages
    │   │   │
    │   │   ├── 📁 claim/
    │   │   │   ├── 📄 ClaimCommand.java           # /claim - create claim
    │   │   │   ├── 📄 UnclaimCommand.java         # /unclaim - remove claim
    │   │   │   ├── 📄 ClaimListCommand.java       # /claimlist - list your claims
    │   │   │   ├── 📄 ClaimInfoCommand.java       # /claiminfo - inspect claim
    │   │   │   ├── 📄 ClaimShowCommand.java       # /claimshow - visualize boundaries
    │   │   │   ├── 📄 ClaimExpandCommand.java     # /claimexpand - expand claim
    │   │   │   ├── 📄 ClaimShrinkCommand.java     # /claimshrink - shrink claim
    │   │   │   └── 📄 ClaimTransferCommand.java   # /claimtransfer - change owner
    │   │   │
    │   │   ├── 📁 trust/
    │   │   │   ├── 📄 TrustCommand.java           # /trust <player> - build trust
    │   │   │   ├── 📄 ContainerTrustCommand.java  # /containertrust - container access
    │   │   │   ├── 📄 AccessTrustCommand.java     # /accesstrust - interact access
    │   │   │   ├── 📄 PermissionTrustCommand.java # /permissiontrust - can trust others
    │   │   │   ├── 📄 UntrustCommand.java         # /untrust <player> - remove trust
    │   │   │   └── 📄 TrustListCommand.java       # /trustlist - show trusted players
    │   │   │
    │   │   ├── 📁 party/
    │   │   │   ├── 📄 PartyCommand.java           # /party - base party command
    │   │   │   ├── 📄 PartyCreateCommand.java     # /party create <name>
    │   │   │   ├── 📄 PartyDisbandCommand.java    # /party disband
    │   │   │   ├── 📄 PartyInviteCommand.java     # /party invite <player>
    │   │   │   ├── 📄 PartyAcceptCommand.java     # /party accept
    │   │   │   ├── 📄 PartyDenyCommand.java       # /party deny
    │   │   │   ├── 📄 PartyKickCommand.java       # /party kick <player>
    │   │   │   ├── 📄 PartyLeaveCommand.java      # /party leave
    │   │   │   ├── 📄 PartyPromoteCommand.java    # /party promote <player>
    │   │   │   ├── 📄 PartyDemoteCommand.java     # /party demote <player>
    │   │   │   ├── 📄 PartyInfoCommand.java       # /party info
    │   │   │   ├── 📄 PartyListCommand.java       # /party list
    │   │   │   ├── 📄 PartyChatCommand.java       # /party chat <message>
    │   │   │   ├── 📄 PartyAllyCommand.java       # /party ally <party>
    │   │   │   ├── 📄 PartyNeutralCommand.java    # /party neutral <party>
    │   │   │   └── 📄 PartyEnemyCommand.java      # /party enemy <party>
    │   │   │
    │   │   ├── 📁 flag/
    │   │   │   ├── 📄 FlagCommand.java            # /claimflag <flag> <value>
    │   │   │   └── 📄 FlagListCommand.java        # /claimflags - list all flags
    │   │   │
    │   │   ├── 📁 subclaim/
    │   │   │   ├── 📄 SubclaimCommand.java        # /subclaim - create subdivision
    │   │   │   ├── 📄 SubclaimTrustCommand.java   # /subclaimtrust - trust in subclaim
    │   │   │   └── 📄 SubclaimRemoveCommand.java  # /subclaimremove - delete subclaim
    │   │   │
    │   │   └── 📁 admin/
    │   │       ├── 📄 AdminClaimCommand.java      # /scadmin claim - admin claim
    │   │       ├── 📄 AdminUnclaimCommand.java    # /scadmin unclaim - force unclaim
    │   │       ├── 📄 AdminDeleteAllCommand.java  # /scadmin deleteall <player>
    │   │       ├── 📄 AdminTransferCommand.java   # /scadmin transfer
    │   │       ├── 📄 AdminBypassCommand.java     # /scadmin bypass - toggle bypass
    │   │       ├── 📄 AdminBlocksCommand.java     # /scadmin blocks <player> <amount>
    │   │       ├── 📄 AdminPartyListCommand.java  # /scadmin parties - list all
    │   │       ├── 📄 AdminStatsCommand.java      # /scadmin stats - server stats
    │   │       ├── 📄 AdminReloadCommand.java     # /scadmin reload - reload config
    │   │       ├── 📄 AdminExpireCommand.java     # /scadmin expire - force expiration
    │   │       └── 📄 AdminDebugCommand.java      # /scadmin debug - debug info
    │   │
    │   ├── 📁 gui/
    │   │   ├── 📄 GuiManager.java                 # GUI registration
    │   │   │
    │   │   ├── 📁 claim/
    │   │   │   ├── 📄 ClaimListGui.java           # List of player's claims
    │   │   │   ├── 📄 ClaimEditGui.java           # Edit claim settings
    │   │   │   ├── 📄 ClaimFlagsGui.java          # Edit claim flags
    │   │   │   ├── 📄 ClaimTrustGui.java          # Manage trusted players
    │   │   │   └── 📄 ClaimMapGui.java            # Visual chunk map
    │   │   │
    │   │   ├── 📁 party/
    │   │   │   ├── 📄 PartyInfoGui.java           # Party information screen
    │   │   │   ├── 📄 PartyMembersGui.java        # Member management
    │   │   │   ├── 📄 PartySettingsGui.java       # Party settings
    │   │   │   ├── 📄 PartyRelationsGui.java      # Ally/enemy management
    │   │   │   └── 📄 PartyColorPickerGui.java    # Claim color selection
    │   │   │
    │   │   ├── 📁 admin/
    │   │   │   ├── 📄 AdminDashboardGui.java      # Admin overview
    │   │   │   ├── 📄 AdminPartyListGui.java      # All parties list
    │   │   │   ├── 📄 AdminClaimListGui.java      # All claims list
    │   │   │   └── 📄 AdminStatsGui.java          # Server statistics
    │   │   │
    │   │   └── 📁 common/
    │   │       ├── 📄 ConfirmationGui.java        # Yes/No confirmation dialog
    │   │       ├── 📄 PaginatedGui.java           # Base for paginated lists
    │   │       └── 📄 PlayerSelectorGui.java      # Player selection interface
    │   │
    │   ├── 📁 config/
    │   │   ├── 📄 CapitalConfig.java         # Main configuration
    │   │   ├── 📄 ConfigManager.java              # Config loading/saving
    │   │   │
    │   │   └── 📁 section/
    │   │       ├── 📄 ClaimConfig.java            # Claim-related settings
    │   │       ├── 📄 PartyConfig.java            # Party-related settings
    │   │       ├── 📄 EarningConfig.java          # Claim block earning rates
    │   │       ├── 📄 ExpirationConfig.java       # Expiration settings
    │   │       ├── 📄 ProtectionConfig.java       # Default protection flags
    │   │       ├── 📄 VisualizationConfig.java    # Visualization settings
    │   │       ├── 📄 TeleportConfig.java         # Teleport/home settings
    │   │       ├── 📄 EconomyConfig.java          # Economy integration settings
    │   │       ├── 📄 LoggingConfig.java          # Activity logging settings
    │   │       └── 📄 MessageConfig.java          # Customizable messages
    │   │
    │   ├── 📁 storage/
    │   │   ├── 📄 StorageManager.java             # Storage backend management
    │   │   ├── 📄 IStorage.java                   # Storage interface
    │   │   │
    │   │   ├── 📁 file/
    │   │   │   ├── 📄 JsonStorage.java            # JSON file storage
    │   │   │   ├── 📄 ClaimFileStorage.java       # Claim data files
    │   │   │   ├── 📄 PartyFileStorage.java       # Party data files
    │   │   │   └── 📄 PlayerFileStorage.java      # Player data files
    │   │   │
    │   │   ├── 📁 database/
    │   │   │   ├── 📄 DatabaseStorage.java        # Database storage [FUTURE]
    │   │   │   ├── 📄 SQLiteStorage.java          # SQLite backend [FUTURE]
    │   │   │   ├── 📄 MySQLStorage.java           # MySQL backend [FUTURE]
    │   │   │   └── 📄 DatabaseMigrations.java     # Schema migrations [FUTURE]
    │   │   │
    │   │   └── 📁 cache/
    │   │       ├── 📄 CacheManager.java           # In-memory cache
    │   │       ├── 📄 ClaimCache.java             # Spatial claim lookup
    │   │       └── 📄 PlayerCache.java            # Player data cache
    │   │
    │   ├── 📁 integration/
    │   │   ├── 📄 IntegrationManager.java         # Integration loading
    │   │   │
    │   │   ├── 📁 map/
    │   │   │   ├── 📄 HytaleMapIntegration.java   # Built-in map support
    │   │   │   └── 📄 WebMapIntegration.java      # Web-based map [FUTURE]
    │   │   │
    │   │   ├── 📁 permission/
    │   │   │   └── 📄 HytalePermissions.java      # Hytale permission integration
    │   │   │
    │   │   └── 📁 economy/
    │   │       └── 📄 HytaleEconomyIntegration.java # Hytale economy [FUTURE]
    │   │
    │   ├── 📁 util/
    │   │   ├── 📄 FileUtils.java                  # File operations
    │   │   ├── 📄 MessageHelper.java              # Message formatting
    │   │   ├── 📄 TextUtils.java                  # Text manipulation
    │   │   ├── 📄 ColorUtils.java                 # Color conversion
    │   │   ├── 📄 TimeUtils.java                  # Time formatting
    │   │   ├── 📄 MathUtils.java                  # Math helpers
    │   │   ├── 📄 ValidationUtils.java            # Input validation
    │   │   ├── 📄 UUIDUtils.java                  # UUID handling
    │   │   └── 📄 SchedulerUtils.java             # Async task helpers
    │   │
    │   └── 📁 metrics/
    │       ├── 📄 MetricsManager.java             # Metrics collection
    │       └── 📄 ClaimMetrics.java               # Claim statistics
    │
    └── 📁 test/java/dev/ewwegg/capital/
        │
        ├── 📁 claim/
        │   ├── 📄 ClaimManagerTest.java
        │   ├── 📄 ClaimCacheTest.java
        │   └── 📄 ChunkCoordinateTest.java
        │
        ├── 📁 party/
        │   ├── 📄 PartyManagerTest.java
        │   ├── 📄 PartyMemberTest.java
        │   └── 📄 InviteManagerTest.java
        │
        ├── 📁 permission/
        │   ├── 📄 PermissionResolverTest.java
        │   ├── 📄 TrustManagerTest.java
        │   └── 📄 FlagManagerTest.java
        │
        ├── 📁 protection/
        │   └── 📄 ProtectionManagerTest.java
        │
        ├── 📁 storage/
        │   ├── 📄 JsonStorageTest.java
        │   └── 📄 CacheManagerTest.java
        │
        └── 📁 util/
            ├── 📄 ValidationUtilsTest.java
            └── 📄 TimeUtilsTest.java
```

---

### 📁 resources/ — Assets & Resources

```
resources/
├── 📁 Common/
│   ├── 📁 UI/
│   │   ├── 📁 Custom/
│   │   │   ├── 📄 Capital.png                # Main texture atlas
│   │   │   ├── 📄 Capital_Icons.png          # Icon sprites
│   │   │   │
│   │   │   └── 📁 Pages/
│   │   │       │
│   │   │       ├── 📁 claim/
│   │   │       │   ├── 📄 ClaimList.ui            # Claim list screen
│   │   │       │   ├── 📄 ClaimListEntry.ui       # Single claim entry
│   │   │       │   ├── 📄 ClaimEdit.ui            # Edit claim screen
│   │   │       │   ├── 📄 ClaimFlags.ui           # Flags editor
│   │   │       │   ├── 📄 ClaimFlagEntry.ui       # Single flag toggle
│   │   │       │   ├── 📄 ClaimTrust.ui           # Trust management
│   │   │       │   ├── 📄 ClaimTrustEntry.ui      # Single trusted player
│   │   │       │   ├── 📄 ClaimMap.ui             # Visual chunk map
│   │   │       │   ├── 📄 ClaimMapChunk.ui        # Single chunk on map
│   │   │       │   └── 📄 ClaimInfo.ui            # Claim info popup
│   │   │       │
│   │   │       ├── 📁 party/
│   │   │       │   ├── 📄 PartyInfo.ui            # Party information
│   │   │       │   ├── 📄 PartyMembers.ui         # Member list
│   │   │       │   ├── 📄 PartyMemberEntry.ui     # Single member entry
│   │   │       │   ├── 📄 PartySettings.ui        # Party settings
│   │   │       │   ├── 📄 PartyRelations.ui       # Relations screen
│   │   │       │   ├── 📄 PartyRelationEntry.ui   # Single relation entry
│   │   │       │   ├── 📄 PartyColorPicker.ui     # Color picker
│   │   │       │   └── 📄 PartyInvite.ui          # Invite popup
│   │   │       │
│   │   │       ├── 📁 admin/
│   │   │       │   ├── 📄 AdminDashboard.ui       # Admin overview
│   │   │       │   ├── 📄 AdminPartyList.ui       # All parties
│   │   │       │   ├── 📄 AdminPartyEntry.ui      # Single party entry
│   │   │       │   ├── 📄 AdminClaimList.ui       # All claims
│   │   │       │   ├── 📄 AdminClaimEntry.ui      # Single claim entry
│   │   │       │   └── 📄 AdminStats.ui           # Statistics
│   │   │       │
│   │   │       └── 📁 common/
│   │   │           ├── 📄 Confirmation.ui         # Confirmation dialog
│   │   │           ├── 📄 PlayerSelector.ui       # Player picker
│   │   │           ├── 📄 PlayerSelectorEntry.ui  # Single player entry
│   │   │           └── 📄 Pagination.ui           # Pagination controls
│   │   │
│   │   └── 📁 Styles/
│   │       └── 📄 Capital.style              # Custom style definitions
│   │
│   └── 📁 Sounds/
│       ├── 📄 claim_create.ogg                    # Claim created sound
│       ├── 📄 claim_delete.ogg                    # Claim removed sound
│       ├── 📄 claim_denied.ogg                    # Action denied sound
│       ├── 📄 claim_enter.ogg                     # Enter claim sound
│       ├── 📄 claim_exit.ogg                      # Exit claim sound
│       ├── 📄 trust_granted.ogg                   # Trust granted sound
│       ├── 📄 party_invite.ogg                    # Invitation sound
│       └── 📄 party_join.ogg                      # Party joined sound
│
└── 📁 Server/
    ├── 📁 Languages/
    │   ├── 📁 en-US/
    │   │   ├── 📄 messages.lang                   # General messages
    │   │   ├── 📄 commands.lang                   # Command messages
    │   │   ├── 📄 errors.lang                     # Error messages
    │   │   ├── 📄 flags.lang                      # Flag descriptions
    │   │   └── 📄 gui.lang                        # GUI labels
    │   │
    │   ├── 📁 es-ES/                              # Spanish
    │   │   └── ... (same structure)
    │   │
    │   ├── 📁 de-DE/                              # German
    │   │   └── ... (same structure)
    │   │
    │   ├── 📁 fr-FR/                              # French
    │   │   └── ... (same structure)
    │   │
    │   ├── 📁 pt-BR/                              # Portuguese
    │   │   └── ... (same structure)
    │   │
    │   ├── 📁 zh-CN/                              # Chinese (Simplified)
    │   │   └── ... (same structure)
    │   │
    │   ├── 📁 ja-JP/                              # Japanese
    │   │   └── ... (same structure)
    │   │
    │   └── 📁 ru-RU/                              # Russian
    │       └── ... (same structure)
    │
    ├── 📁 Audio/
    │   └── 📁 SoundEvents/
    │       └── 📄 capital.json               # Sound event definitions
    │
    └── 📁 Config/
        └── 📄 capital-default.json           # Default configuration
```

---

### 📁 docs/ — Documentation

```
docs/
├── 📁 user/
│   ├── 📄 getting-started.md                      # New user guide
│   ├── 📄 claiming-land.md                        # How to claim
│   ├── 📄 trusting-players.md                     # Trust system guide
│   ├── 📄 parties.md                              # Party system guide
│   ├── 📄 flags.md                                # Protection flags guide
│   ├── 📄 subclaims.md                            # Subdivision guide
│   ├── 📄 commands.md                             # Full command reference
│   └── 📄 faq.md                                  # Frequently asked questions
│
├── 📁 admin/
│   ├── 📄 installation.md                         # Installation guide
│   ├── 📄 configuration.md                        # Config reference
│   ├── 📄 permissions.md                          # Permission nodes
│   ├── 📄 admin-commands.md                       # Admin command reference
│   ├── 📄 claim-expiration.md                     # Expiration system
│   ├── 📄 economy-setup.md                        # Economy integration
│   ├── 📄 logging-rollback.md                     # Logging & rollback
│   ├── 📄 migration.md                            # Migrating from v1
│   └── 📄 troubleshooting.md                      # Common issues
│
├── 📁 developer/
│   ├── 📄 api-overview.md                         # API introduction
│   ├── 📄 api-reference.md                        # Full API docs
│   ├── 📄 events.md                               # Event reference
│   ├── 📄 custom-flags.md                         # Creating custom flags
│   ├── 📄 integrations.md                         # Integration guide
│   ├── 📄 building.md                             # Build instructions
│   └── 📄 contributing.md                         # Contribution guide
│
├── 📁 diagrams/
│   ├── 📄 architecture.md                         # System architecture
│   ├── 📄 permission-flow.md                      # Permission check flow
│   ├── 📄 data-model.md                           # Data structures
│   └── 📁 images/
│       ├── 📄 architecture.png
│       ├── 📄 permission-flow.png
│       └── 📄 data-model.png
│
└── 📄 index.md                                    # Documentation home
```

---

### 📁 scripts/ — Build & Utility Scripts

```
scripts/
├── 📄 build.sh                                    # Build script (Unix)
├── 📄 build.bat                                   # Build script (Windows)
├── 📄 release.sh                                  # Release preparation
├── 📄 generate-docs.sh                            # Generate documentation
├── 📄 run-tests.sh                                # Run test suite
└── 📄 setup-dev.sh                                # Developer environment setup
```

---

## File Descriptions

### Root Configuration Files

| File                | Purpose                                                              |
| ------------------- | -------------------------------------------------------------------- |
| `build.gradle`      | Root Gradle build configuration with common dependencies and plugins |
| `settings.gradle`   | Defines the multi-module project structure (api, core)               |
| `gradle.properties` | Shared properties: mod version, Hytale SDK version, Java version     |
| `LICENSE`           | Open source license (recommend MIT or Apache 2.0)                    |
| `README.md`         | Project overview with badges, features, installation, quick start    |
| `CHANGELOG.md`      | Detailed version history following Keep a Changelog format           |
| `ROADMAP.md`        | Feature roadmap with planned versions and milestones                 |

---

## Implementation Milestones

### Milestone 1: Core Foundation (v0.0.0)

> Priority: 🔴 Critical

| Component         | Files                                                        | Status         |
| ----------------- | ------------------------------------------------------------ | -------------- |
| API Module        | `api/` entire directory                                      | ⬜ Not Started |
| Core Claim System | `core/.../claim/ClaimManager.java`, `Claim.java`, `chunk/*`  | ⬜ Not Started |
| Party System      | `core/.../party/PartyManager.java`, `Party.java`, `member/*` | ⬜ Not Started |
| Basic Protection  | `core/.../protection/*`                                      | ⬜ Not Started |
| File Storage      | `core/.../storage/file/*`                                    | ⬜ Not Started |
| Basic Commands    | `core/.../command/claim/*`, `command/party/*`                | ⬜ Not Started |
| Configuration     | `core/.../config/*`                                          | ⬜ Not Started |

### Milestone 2: Trust System (v0.1.0)

> Priority: 🔴 Critical

| Component           | Files                                         | Status         |
| ------------------- | --------------------------------------------- | -------------- |
| Trust Levels        | `core/.../permission/trust/*`                 | ⬜ Not Started |
| Trust Commands      | `core/.../command/trust/*`                    | ⬜ Not Started |
| Permission Resolver | `core/.../permission/PermissionResolver.java` | ⬜ Not Started |

### Milestone 3: Protection Flags (v0.2.0)

> Priority: 🔴 Critical

| Component      | Files                                   | Status         |
| -------------- | --------------------------------------- | -------------- |
| Flag System    | `core/.../permission/flag/*`            | ⬜ Not Started |
| Built-in Flags | `core/.../permission/flag/builtin/*`    | ⬜ Not Started |
| Flag Commands  | `core/.../command/flag/*`               | ⬜ Not Started |
| Flag GUI       | `core/.../gui/claim/ClaimFlagsGui.java` | ⬜ Not Started |

### Milestone 4: Visualization (v0.3.0)

> Priority: 🔴 Critical

| Component          | Files                                 | Status         |
| ------------------ | ------------------------------------- | -------------- |
| Boundary Rendering | `core/.../visualization/boundary/*`   | ⬜ Not Started |
| Claim Inspector    | `core/.../visualization/inspection/*` | ⬜ Not Started |
| World Map          | `core/.../visualization/map/*`        | ⬜ Not Started |

### Milestone 5: Claim Earning (v0.4.0)

> Priority: 🟡 Medium

| Component       | Files                                     | Status         |
| --------------- | ----------------------------------------- | -------------- |
| Block Manager   | `core/.../earning/ClaimBlockManager.java` | ⬜ Not Started |
| Earning Sources | `core/.../earning/source/*`               | ⬜ Not Started |
| Bonus System    | `core/.../earning/bonus/*`                | ⬜ Not Started |

### Milestone 6: Claim Expiration (v0.5.0)

> Priority: 🟡 Medium

| Component            | Files                                            | Status         |
| -------------------- | ------------------------------------------------ | -------------- |
| Expiration Manager   | `core/.../claim/expiration/*`                    | ⬜ Not Started |
| Warning System       | Part of expiration                               | ⬜ Not Started |
| Admin Expire Command | `core/.../command/admin/AdminExpireCommand.java` | ⬜ Not Started |

### Milestone 7: Teleportation (v0.6.0)

> Priority: 🟡 Medium

| Component         | Files                         | Status         |
| ----------------- | ----------------------------- | -------------- |
| Home System       | `core/.../teleport/*`         | ⬜ Not Started |
| Warmup/Cooldown   | `core/.../teleport/warmup/*`  | ⬜ Not Started |
| Teleport Commands | `core/.../teleport/command/*` | ⬜ Not Started |

### Milestone 8: Subclaims (v0.7.0)

> Priority: 🟡 Medium

| Component         | Files                         | Status         |
| ----------------- | ----------------------------- | -------------- |
| Subclaim System   | `core/.../claim/subclaim/*`   | ⬜ Not Started |
| Subclaim Commands | `core/.../command/subclaim/*` | ⬜ Not Started |

### Milestone 9: Party Relations (v0.8.0)

> Priority: 🟡 Medium

| Component         | Files                                    | Status         |
| ----------------- | ---------------------------------------- | -------------- |
| Relations System  | `core/.../party/relation/*`              | ⬜ Not Started |
| Party Chat        | `core/.../party/chat/*`                  | ⬜ Not Started |
| Relation Commands | `core/.../command/party/PartyAlly*.java` | ⬜ Not Started |

### Milestone 10: Activity Logging (v0.9.0)

> Priority: 🟢 Low

| Component       | Files                         | Status         |
| --------------- | ----------------------------- | -------------- |
| Logger System   | `core/.../logging/*`          | ⬜ Not Started |
| Query System    | `core/.../logging/query/*`    | ⬜ Not Started |
| Rollback System | `core/.../logging/rollback/*` | ⬜ Not Started |

### Milestone 11: Economy Integration (v0.10.0)

> Priority: 🟢 Low

| Component       | Files                     | Status         |
| --------------- | ------------------------- | -------------- |
| Economy Manager | `core/.../economy/*`      | ⬜ Not Started |
| Claim Shop      | `core/.../economy/shop/*` | ⬜ Not Started |

### Milestone 12: Advanced Features (v1.0.0)

> Priority: 🟢 Future

| Component        | Files                         | Status         |
| ---------------- | ----------------------------- | -------------- |
| Database Storage | `core/.../storage/database/*` | ⬜ Not Started |
| Region Claims    | `core/.../claim/region/*`     | ⬜ Not Started |
| Rental System    | `core/.../economy/rental/*`   | ⬜ Not Started |
| Tax System       | `core/.../economy/tax/*`      | ⬜ Not Started |
| Party Bank       | `core/.../party/bank/*`       | ⬜ Not Started |

---

## Contributing Guidelines

### Branch Naming

```
feature/SC-123-trust-levels        # New features
bugfix/SC-456-fix-claim-overlap    # Bug fixes
docs/update-api-reference          # Documentation
refactor/cleanup-permission-code   # Code cleanup
```

### Commit Message Format

```
type(scope): description

[optional body]

[optional footer]
```

**Types:** `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`

**Examples:**

```
feat(trust): add container trust level
fix(protection): prevent piston griefing across boundaries
docs(api): add event documentation
```

### Pull Request Checklist

- [ ] Code follows project style guidelines
- [ ] Unit tests added/updated
- [ ] Documentation updated
- [ ] Changelog updated
- [ ] No breaking API changes (or documented)
- [ ] Tested in-game

### Issue Labels

| Label                                  | Description               |
| -------------------------------------- | ------------------------- |
| `priority: critical`                   | Must be fixed immediately |
| `priority: high`                       | Important, fix soon       |
| `priority: medium`                     | Should be done            |
| `priority: low`                        | Nice to have              |
| `type: bug`                            | Something isn't working   |
| `type: feature`                        | New feature request       |
| `type: enhancement`                    | Improvement to existing   |
| `type: documentation`                  | Documentation only        |
| `status: help wanted`                  | Open for contribution     |
| `status: good first issue`             | Good for newcomers        |
| `milestone: 1` through `milestone: 12` | Implementation milestone  |

---

## Permission Nodes

```yaml
# User Permissions
capital.claim.create          # Create claims
capital.claim.delete          # Delete own claims
capital.claim.trust           # Trust other players
capital.claim.flags           # Modify flags
capital.claim.subclaim        # Create subclaims
capital.party.create          # Create parties
capital.party.invite          # Invite to party
capital.home.use              # Use home teleport
capital.home.multiple.<n>     # Multiple homes

# Admin Permissions
capital.admin.bypass          # Bypass all protection
capital.admin.claim.other     # Manage others' claims
capital.admin.blocks          # Modify claim blocks
capital.admin.party           # Manage all parties
capital.admin.reload          # Reload configuration
capital.admin.debug           # Debug commands

# Bonus Permissions
capital.bonus.blocks.<n>      # Bonus claim blocks
capital.bonus.claims.<n>      # Bonus claim count
capital.bonus.expiration      # Extended expiration
```

---

## Quick Reference

### Key Interfaces

```java
// Check if player can build at location
CapitalAPI.get().canBuild(player, location);

// Get claim at position
Optional<IClaim> claim = CapitalAPI.get().getClaimAt(location);

// Check player's trust level
TrustType trust = claim.getTrustLevel(playerUUID);

// Listen for claim events
EventRegistry.register(ClaimCreateEvent.class, event -> {
    // Handle claim creation
});
```

### Default Configuration

```json
{
  "claim": {
    "defaultClaimBlocks": 100,
    "blocksPerHour": 100,
    "maxClaims": 10,
    "minClaimSize": 100,
    "maxClaimSize": 10000
  },
  "expiration": {
    "enabled": true,
    "daysUntilExpire": 60,
    "warningDays": 7,
    "minClaimSizeToExpire": 100
  },
  "protection": {
    "defaultFlags": {
      "pvp": false,
      "mobSpawn": true,
      "explosions": false,
      "fireSpread": false
    }
  }
}
```

---

_This document serves as the master plan for development. Update status markers as implementation progresses._
