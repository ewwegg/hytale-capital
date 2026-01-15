# Milestone Resolution Template

_Summary of decomposed issues ready for execution._

---

# Milestone Resolution: [Milestone Name]

Date: YYYY-MM-DD

## Milestone Outcome

_The user value this milestone delivers, from analysis._

**Visitor/User:** [Outcome statement from milestone-analysis.md]

## Decision Outcomes Applied

_Accepted ADRs incorporated into issue definitions. Always include the full path/link to each ADR._

| ADR                                    | Decision        | Affected Issues |
| -------------------------------------- | --------------- | --------------- |
| [ADR-NNNN](../../../adr/NNNN-title.md) | [Chosen option] | [Issue numbers] |

## Spike Findings Applied

_Technical findings incorporated into issue definitions. Always include the full path/link to each spike._

| Spike                                  | Outcome       | Affected Issues |
| -------------------------------------- | ------------- | --------------- |
| [spike-topic](./spikes/spike-topic.md) | [Key finding] | [Issue numbers] |

---

## Issue List

_All issues in execution order._

| #   | Issue                                   | Type    | Assignment      | Model           | Blocked By |
| --- | --------------------------------------- | ------- | --------------- | --------------- | ---------- |
| 1   | [Issue title](./issues/1-issue-name.md) | feature | Senior Engineer | claude-opus-4-5 | —          |
| 2   | [Issue title](./issues/2-issue-name.md) | chore   | DevOps          | claude-opus-4-5 | 1          |

---

## Execution Order

_Dependency graph showing execution sequence. Issues with same number can run in parallel._

```
[1: Issue Name]
    ↓
[2: Issue Name] → parallel: [3: Issue Name]
                     ↓
                [4: Issue Name]
```

---

## Human-Only Issues

_Issues requiring human action—cannot be delegated to AI._

| #   | Issue        | Reason                                              |
| --- | ------------ | --------------------------------------------------- |
| [N] | [Issue name] | [Why human required: vendor, external, legal, etc.] |

_None identified_ (remove section if no human-only issues)

---

## Labels Reference

_Standard labels for GitHub Issues._

| Label         | Colour           | Description                              |
| ------------- | ---------------- | ---------------------------------------- |
| `feature`     | Green `#0e8a16`  | New functionality                        |
| `chore`       | Grey `#cfd3d7`   | Maintenance, refactoring, no user impact |
| `docs`        | Yellow `#fef2c0` | Documentation only                       |
| `bug`         | Red `#d73a4a`    | Something isn't working                  |
| `enhancement` | Blue `#a2eeef`   | Improvement to existing functionality    |
| `blocked`     | Red `#b60205`    | Waiting on dependency or external factor |
