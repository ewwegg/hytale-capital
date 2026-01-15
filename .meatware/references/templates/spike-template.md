---
date: YYYY-MM-DD # Date spike was conducted
status: Complete / In Progress # Current state of investigation
question: [The specific question this spike answers] # Copied from Question section
---

# Spike: [Topic in Sentence Case]

_Timeboxed investigation to reduce uncertainty before dependent work proceeds._

## Question

_The specific question this spike must answer. Should be answerable with Confirmed / Blocked / Inconclusive._

[Question text]

## Timebox

[N] hours

## Context

_Why this investigation is needed. Reference the risk from milestone analysis._

- Related risk: [Risk description from analysis]
- Dependent decisions: [ADR titles that need this finding]
- Dependent issues: [Issue names blocked by this uncertainty]

## Investigation Approach

_How the question will be answered._

1. [Investigation step]
2. [Investigation step]
3. [Investigation step]

## Findings

_Evidence discovered during investigation. Document as you go. Include as many findings as needed—may be one, may be several._

### [Finding Title]

[Description of what was discovered]

**Source:** [Documentation link, test result, or other evidence]

_Add additional findings as needed using the same format above._

## Outcome

**Status:** Confirmed / Blocked / Inconclusive

_Select one:_

- **Confirmed**: The approach is viable. [Brief explanation of why]
- **Blocked**: A blocker was found. [Description of blocker and potential mitigations]
- **Inconclusive**: Unable to determine within timebox. [What additional investigation is needed]

## Impact

_How these findings affect the milestone._

### On Decisions

| Decision    | Impact                              |
| ----------- | ----------------------------------- |
| [ADR title] | [How findings affect this decision] |

### On Issues

| Issue        | Impact                                                 |
| ------------ | ------------------------------------------------------ |
| [Issue name] | [Revised scope, new acceptance criteria, or no change] |

### New Risks Discovered

_Include only if new risks were found. Remove section if none._

| Risk       | Likelihood      | Impact          | Recommended Response |
| ---------- | --------------- | --------------- | -------------------- |
| [New risk] | High/Medium/Low | High/Medium/Low | Spike / Accept       |

## Recommendations

_Suggested next steps based on findings._

- [Recommendation]
