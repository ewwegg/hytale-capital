---
date: 2026-01-15
status: Accepted
decision: Semantic versioning only; no version in package names
---

# 0004. API Versioning Strategy

_Architecture Decision Record following [MADR](https://adr.github.io/madr/) format, extended from [Michael Nygard's template](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions)._

## Status

Accepted

_Status lifecycle: Proposed → Accepted → [Deprecated | Superseded by ADR-NNNN]_

## Context

The API module provides interfaces for other mods to integrate with Capital. API versioning affects:

- Binary compatibility for dependent mods
- How breaking changes are communicated
- Package/class organization
- Release workflow

v0 scope:

- API module contains interfaces only
- Implementations in core module
- Public interfaces: IClaim, IClaimManager, IParty, IPartyManager, CapitalAPI facade

The API will evolve as features are added. Need a strategy that balances stability with development velocity.

## Options Considered

### Option 1: Package Versioning

Version number in package name:

```
dev.ewwegg.capital.api.v1.IClaim
dev.ewwegg.capital.api.v1.IClaimManager
```

New versions create new packages:

```
dev.ewwegg.capital.api.v2.IClaim  // Breaking changes
```

**Pros:**

- Multiple API versions can coexist
- Dependent mods can migrate gradually
- Clear which version is in use

**Cons:**

- Package proliferation over time
- Duplicate interfaces if changes are minor
- Complex to maintain multiple versions
- Over-engineered for v0 with no dependents yet

### Option 2: Interface Versioning

Version marker in interface name or annotation:

```java
@ApiVersion(1)
public interface IClaim { }

// Later:
@ApiVersion(2)
public interface IClaim { }  // Breaks compatibility
```

**Pros:**

- Single package structure
- Explicit version marking

**Cons:**

- Annotation doesn't prevent binary incompatibility
- Still requires maintaining old interfaces
- Complexity without clear benefit

### Option 3: Semantic Versioning Only

Standard semantic versioning (major.minor.patch) on the module:

- Major: Breaking API changes
- Minor: Backwards-compatible additions
- Patch: Bug fixes

No version in package names or interfaces.

```
dev.ewwegg.capital.api.IClaim  // Always current
```

**Pros:**

- Simplest implementation
- Standard industry practice
- No package/interface proliferation
- Breaking changes communicated via version number
- Matches Gradle/Maven conventions

**Cons:**

- Only one API version at runtime
- Dependent mods must update for major versions
- No gradual migration path

## Decision

We will use **Option 3: Semantic versioning only** because it provides adequate versioning with minimal complexity.

_Evaluation against development philosophy:_

| Principle                 | Assessment                                           |
| ------------------------- | ---------------------------------------------------- |
| Working beats perfect     | SemVer works immediately; no infrastructure needed   |
| Simple beats flexible     | Single package structure; version in build file only |
| Conventional beats clever | Industry standard approach                           |
| Explicit beats abstract   | Version number clearly communicates compatibility    |
| Reversible beats optimal  | Can add package versioning later if needed           |

Rationale for v0:

- No external dependents yet; breaking changes have no impact
- Focus on getting API right before stabilizing
- Package versioning adds complexity without current benefit
- Can introduce v2 package if v1 needs long-term support (unlikely for v0)

## Consequences

**Positive:**

- Simple package structure
- No duplicate interfaces
- Standard tooling support (Gradle, Maven)
- Focus on API design over versioning mechanics

**Negative:**

- Breaking changes require dependent mods to update simultaneously
- No coexistence of API versions

**Neutral:**

- Pre-1.0 versions (0.x.y) signal instability per SemVer convention
- API stability expected after 1.0 release

## Related

- Blocks: Create API interfaces, Create CapitalAPI facade, API module structure
- Related ADRs: None
- Spike findings: None
