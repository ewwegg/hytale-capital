---
date: 2026-01-15
status: Accepted
decision: Claims linked to player UUID; party access via separate lookup
---

# 0003. Party-Claim Relationship

_Architecture Decision Record following [MADR](https://adr.github.io/madr/) format, extended from [Michael Nygard's template](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions)._

## Status

Accepted

_Status lifecycle: Proposed → Accepted → [Deprecated | Superseded by ADR-NNNN]_

## Context

The milestone includes both claims and parties. The relationship between them affects:

- Who can interact with claimed land
- How ownership transfers work
- Data model complexity
- Future trust system integration

Scope boundaries from analysis:

- v0: Claims owned by individual players, not parties
- Trust system deferred to later milestone
- Party claim ownership deferred

The question is how to structure the data model now to support v0 requirements while not blocking future features.

## Options Considered

### Option 1: Claims Linked to Party

Claims belong to parties; solo players create single-member parties.

```java
public class Claim {
    UUID partyId;  // Always references a party
    // ...
}
```

**Pros:**

- Uniform model (everything is party-owned)
- Natural party-wide permissions later
- No migration when adding party ownership

**Cons:**

- Forces party creation for solo players
- Over-engineered for v0 scope
- More complex for simple use case
- Party lifecycle tied to claim lifecycle

### Option 2: Claims Linked to Player with Party Access

Claims owned by individual players; party membership checked separately for access.

```java
public class Claim {
    UUID ownerId;  // Player UUID
    // ...
}
```

Access check:

```java
boolean canAccess(Player player, Claim claim) {
    if (player.getUuid().equals(claim.getOwnerId())) return true;
    // Future: check if player is in owner's party with sufficient role
    return false;
}
```

**Pros:**

- Simple v0 implementation
- No forced party creation
- Clear ownership model
- Matches v0 scope exactly

**Cons:**

- Party access requires lookup in two places (claim + party)
- May need field additions for party ownership later

### Option 3: Hybrid (Owner + Optional Party)

Claims have owner UUID and optional party UUID:

```java
public class Claim {
    UUID ownerId;       // Always set
    UUID partyId;       // Optional, null for personal claims
    // ...
}
```

**Pros:**

- Supports both personal and party claims
- Flexible for future features

**Cons:**

- Two ownership concepts to manage
- Ambiguous semantics (what if owner leaves party?)
- Over-engineered for v0
- Complex permission logic

## Decision

We will use **Option 2: Claims linked to player with party access** because it matches v0 scope exactly and keeps the model simple.

_Evaluation against development philosophy:_

| Principle                 | Assessment                                       |
| ------------------------- | ------------------------------------------------ |
| Working beats perfect     | Player ownership works immediately for v0        |
| Simple beats flexible     | Single owner field; no optional party complexity |
| Conventional beats clever | Standard ownership pattern                       |
| Explicit beats abstract   | Owner is always a player UUID, unambiguous       |
| Reversible beats optimal  | Can add partyId field later if needed            |

Future migration path:

- Trust System milestone adds trust levels for party members
- If party ownership needed, add optional `partyId` field (hybrid becomes viable)
- No breaking changes required

## Consequences

**Positive:**

- Minimal v0 implementation
- Clear ownership semantics
- Party system remains independent
- Simple protection checks

**Negative:**

- Party members don't automatically access claims in v0
- Trust system required for party-based access

**Neutral:**

- Trust system milestone already planned; this defers to that scope

## Related

- Blocks: Implement Claim data class, Implement Party data class
- Related ADRs: None
- Spike findings: None
