---
issue: [Action-oriented title]
project: [Project name from index.yml]
milestone: [Milestone name from index.yml]
type: Human / Agent
label: feature / chore / docs / bug / enhancement / duplicate / wontfix
responsibility:
  [
    Product Manager / Tech Lead / Senior Engineer / Data Engineer / Data Analyst / DevOps / Security / Design,
  ]
model: claude-opus-4-5 / claude-sonnet-4-5 / claude-haiku-4-5 # Required only if type is Agent
blocked-by: []
---

# [Title]

## Context

_Why this issue exists and how it contributes to the milestone outcome_

## Dependencies

- Blocked by [N]-[issue-name] — [brief reason]

## Acceptance Criteria

- [ ] [Specific, observable outcome]

## Technical Constraints

_Non-negotiable requirements from ADRs, spikes, or external systems._

- **[Source]**: [Constraint]

## Workflow

_Suggested approach and patterns. Remove section if implementation is obvious. Should be instructional guidance, not code blocks with exact implementation details._

1. [Step or guidance]
2. [Step or guidance]

## Out of Scope

_Explicit exclusions to prevent scope creep. Remove section if not applicable._

- [Deferred item]

## Resources

_Reference documentation and related decisions._

- [Link title](path/to/resource)
