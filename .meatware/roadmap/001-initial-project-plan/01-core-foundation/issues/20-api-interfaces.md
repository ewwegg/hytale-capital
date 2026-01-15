---
issue: Create API interfaces
project: 001-initial-project-plan
milestone: 01-core-foundation
type: Agent
label: feature
responsibility: [Tech Lead]
model: claude-opus-4-5
blocked-by: [5, 10]
---

# Create API interfaces

## Context

Public interfaces in the API module for external mod integration. Allows other mods to query claims and parties without depending on implementation details.

## Dependencies

- Blocked by 5-claim-manager-cache — Interface design based on ClaimManager API
- Blocked by 10-party-manager — Interface design based on PartyManager API

## Acceptance Criteria

- [ ] `IClaim` interface with: getId(), getOwnerId(), getName(), getChunks(), getCreatedAt()
- [ ] `IClaimManager` interface with: getClaim(UUID), getClaimAt(ChunkCoordinate), getClaimsByOwner(UUID)
- [ ] `IParty` interface with: getId(), getName(), getOwnerId(), getMembers()
- [ ] `IPartyManager` interface with: getParty(UUID), getPartyByPlayer(UUID)
- [ ] `IProtectionManager` interface with: canBuild(UUID, ChunkCoordinate)
- [ ] All interfaces in `dev.ewwegg.capital.api` package
- [ ] Javadoc on all public methods
- [ ] No implementation dependencies in API module

## Technical Constraints

- **ADR-0004**: Semantic versioning only; no version in package names. See [ADR-0004](/.meatware/adr/0004-api-versioning-strategy.md)
- **Stability**: API should be minimal and read-only; only expose what external mods need
- **ADR-0002**: API uses ChunkCoordinate for location parameters. See [ADR-0002](/.meatware/adr/0002-chunk-coordinate-handling.md)
- **Module Separation**: API module must have zero dependencies on core module

## Workflow

1. Review ClaimManager and PartyManager public methods
2. Design minimal interface surface for external use
3. Create interfaces in api module
4. Add comprehensive Javadoc
5. Verify api module has no core dependencies
6. Consider read-only wrappers for return types

## Out of Scope

- Mutation methods in API (create/delete claims)
- Event API for claim changes (future enhancement)

## Resources

- [ADR-0004: API Versioning Strategy](/.meatware/adr/0004-api-versioning-strategy.md)
- [ADR-0002: Chunk Coordinate Handling](/.meatware/adr/0002-chunk-coordinate-handling.md)
- [ADR-0003: Party-Claim Relationship](/.meatware/adr/0003-party-claim-relationship.md)
- [HytaleDocs - Server Internals](https://hytale-docs.com/docs/api/server-internals) — Hytale API design conventions
