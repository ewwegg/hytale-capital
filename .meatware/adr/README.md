# Architecture Decision Records

This directory contains Architecture Decision Records (ADRs) documenting significant technical decisions.

## Format

ADRs follow the [MADR](https://adr.github.io/madr/) format, extended from [Michael Nygard's original template](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions).

## Conventions

**Numbering:** Sequential four-digit numbers (0001, 0002) that never change, even if deprecated.

**Naming:** `NNNN-kebab-case-title.md` (e.g., `0001-use-postgresql-database.md`)

**Immutability:** ADRs are never deleted or significantly modified after acceptance. Supersede with a new ADR instead.

**Status Lifecycle:**

- `Proposed` — Under discussion, not yet approved
- `Accepted` — Approved and to be implemented
- `Deprecated` — No longer relevant (context changed)
- `Superseded by ADR-NNNN` — Replaced by a newer decision

## Template

See `.meatware/references/templates/adr-template.md` for the standard template.

## Index

_List of all ADRs in this project. Updated by `/meatware:milestone-decisions` command._

| Number | Title                     | Status   | Date       |
| ------ | ------------------------- | -------- | ---------- |
| 0001   | Claim Storage Format      | Accepted | 2026-01-15 |
| 0002   | Chunk Coordinate Handling | Accepted | 2026-01-15 |
| 0003   | Party-Claim Relationship  | Accepted | 2026-01-15 |
| 0004   | API Versioning Strategy   | Accepted | 2026-01-15 |

## References

- [MADR - Markdown ADR](https://adr.github.io/madr/)
- [ADR GitHub Organization](https://adr.github.io/)
- [Documenting Architecture Decisions - Michael Nygard](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions)
- [ADR Tools](https://github.com/npryce/adr-tools)
