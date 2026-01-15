# Milestone Decisions Template

_Summary of spikes executed and ADRs created for human review._

---

# Milestone Decisions: [Milestone Name]

Date: YYYY-MM-DD

## Spike Results

_Investigations completed to reduce uncertainty._

| Spike                                     | Question            | Outcome                            | Impact                 |
| ----------------------------------------- | ------------------- | ---------------------------------- | ---------------------- |
| [spike-topic.md](./spikes/spike-topic.md) | [Question answered] | Confirmed / Blocked / Inconclusive | [Brief impact summary] |

## ADRs Pending Review

_Decisions requiring human confirmation before issue decomposition._

| ADR                                 | Decision         | Recommended Option   | Blocks        |
| ----------------------------------- | ---------------- | -------------------- | ------------- |
| [ADR-NNNN](../../adr/NNNN-title.md) | [Decision title] | [Recommended option] | [Issue names] |

## Blocking Relationships

_Which issues cannot proceed until which ADRs are accepted._

```
[ADR-NNNN: Decision Title]
    ↓ blocks
[Issue Name 1], [Issue Name 2]

[ADR-NNNN: Decision Title]
    ↓ blocks
[Issue Name 3]
```

## Human Review Process

**To accept a decision:**

1. Open the ADR file in `.meatware/adr/`
2. Review the Context, Options, and Recommended Decision
3. If you agree: Change `Status: Proposed` to `Status: Accepted` (in body and frontmatter)
4. Update frontmatter `decision:` field with brief summary
5. If you disagree: Modify the Decision section, then change status to Accepted
6. Commit the change

**To reject a decision:**

1. Add comments explaining why no option is acceptable
2. Change `Status: Proposed` to `Status: Rejected`
3. New options must be identified before proceeding

**After all ADRs are accepted:**

Run `/meatware:milestone-resolution` to decompose issues and distribute work.

## Decision Log

_Track status changes as decisions are reviewed._

| ADR        | Original Status | Current Status | Reviewer | Date |
| ---------- | --------------- | -------------- | -------- | ---- |
| [ADR-NNNN] | Proposed        | Proposed       | —        | —    |
