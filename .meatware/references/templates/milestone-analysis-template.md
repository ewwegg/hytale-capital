# Milestone Analysis Template

## Scope

### Outcomes

_Define outcomes for each role that benefits from this milestone. At minimum, include visitor or user outcome._

**Visitor** _(or User if authenticated)_**:** When this milestone is done, a visitor can [complete with observable action]

_Add additional role outcomes as relevant (Editor, Admin, API client):_

**[Role]:** When this milestone is done, a [role] can [complete with observable action]

### Scope Boundaries

**Included:**

- [Item explicitly in scope]

**Excluded:**

- [Item consciously deferred]

### Ambiguity Resolution

_Terms or requirements that lacked precise definition, now clarified._

| Term/Item        | Clarification        |
| ---------------- | -------------------- |
| [Ambiguous term] | [Precise definition] |

### Gaps Identified

_Items assumed but not listed, or adjacent concerns discovered during analysis._

| Gap               | Status               | Rationale                    |
| ----------------- | -------------------- | ---------------------------- |
| [Gap description] | Addressed / Deferred | [Why this status was chosen] |

---

## Classification

### Decisions (Require ADR)

_Items with multiple valid options requiring tradeoff evaluation. Must resolve before dependent issues can proceed._

| Decision               | Options              | Assigned To | Blocks        |
| ---------------------- | -------------------- | ----------- | ------------- |
| [Decision description] | [Option A, Option B] | [Role]      | [Issue names] |

### Issues (Ready for Work)

_Items with clear implementation path and no significant alternatives._

| Issue               | Type                           | Acceptance Criteria                |
| ------------------- | ------------------------------ | ---------------------------------- |
| [Issue description] | feature / chore / spike / docs | [Observable outcome when complete] |

### Hybrids (Decision Extracted)

_Original items that contained embedded decisions, now separated._

| Original Item      | Extracted Decision               | Remaining Issue               |
| ------------------ | -------------------------------- | ----------------------------- |
| [Original wording] | [Decision name from table above] | [Issue name from table above] |

---

## Dependencies

### Dependency Graph

_Text-based directed graph showing execution order. Read top-to-bottom. Arrows indicate what must complete before the next item can start._

**Notation:**

- `↓` = sequential dependency (item above must complete first)
- `→ parallel:` = items in brackets can execute simultaneously
- `(sync point)` = where parallel branches must rejoin before continuing

```
[First Item]
    ↓
[Second Item] → parallel: [Item A], [Item B]
                   ↓
              [Sync Point Item]
                   ↓
              [Final Item]
```

### Parallelisation Points

_Opportunities for concurrent work and where they must synchronise._

| Branch Point             | Parallel Items                      | Sync Point                   |
| ------------------------ | ----------------------------------- | ---------------------------- |
| [Item where work splits] | [Items that can run simultaneously] | [Item where branches rejoin] |

---

## Risks

### Technical Uncertainties

_Unknown factors that could cause delays or require rework._

| Risk               | Likelihood          | Impact              | Investigation Cost |
| ------------------ | ------------------- | ------------------- | ------------------ |
| [Risk description] | High / Medium / Low | High / Medium / Low | [Hours estimate]   |

### Spike Assignments

_Timeboxed investigations required before dependent work can proceed._

| Risk                    | Spike Question                | Timebox | Assigned To |
| ----------------------- | ----------------------------- | ------- | ----------- |
| [Risk from table above] | [Specific question to answer] | [Hours] | [Role]      |

### Accepted Risks

_Risks proceeding without investigation, with documented rationale._

| Risk                    | Rationale                      |
| ----------------------- | ------------------------------ |
| [Risk from table above] | [Why proceeding without spike] |
