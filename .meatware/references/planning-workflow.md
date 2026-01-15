# Planning Workflow

## Purpose

Transform a Milestone definition into distributable work through systematic analysis, decisions and resolution.

A Milestone represents a vertical slice through the stack—a thin, working increment that delivers a specific user outcome. This workflow defines the Issues required to complete that slice and distributes them appropriately. Every Issue must contribute directly to delivering the outcome; work that doesn't is excluded.

---

## Terminology

This document uses GitHub terminology to align with the tooling ecosystem.

| Term             | Description                                                    | Similar To                 |
| ---------------- | -------------------------------------------------------------- | -------------------------- |
| **Project**      | A collection of related Milestones forming a larger initiative | Epic, Initiative           |
| **Milestone**    | A deliverable increment with a defined user outcome            | Phase, Sprint, Story       |
| **Issue**        | A unit of work that contributes to completing a Milestone      | Task, Ticket, Work Item    |
| **Pull Request** | The review and approval mechanism for completing work          | Merge Request, Code Review |

---

## Process Overview

```
Analysis                          Resolution
────────────────────────────────  ────────────────────────────────
1. Clarify Scope                  5. Execute Spikes
2. Separate Decisions from Issues 6. Resolve Decisions (ADRs)
3. Map Dependencies               7. Decompose Issues
4. Identify Risks                 8. Distribute Work
```

Complete analysis before decisions and resolution. Decisions and risks must be resolved before Issue decomposition—otherwise estimates are unreliable.

---

## 1. Analysis: Clarify Scope

Ambiguity discovered later costs more than ambiguity resolved now.

**Review against development philosophy** (`docs/development-philosophy.md`):

- Does this Milestone deliver observable user value?
- Is scope minimal for that value, or does it include speculative work?
- Are we building through the stack narrowly, or a layer broadly?

**Define the user outcome:**

- Complete the sentence: "When this Milestone is done, a user can \_\_\_."
- If the answer is "nothing yet," reduce scope or reframe the Milestone.

**Surface ambiguity:**

- What terms lack precise definitions?
- Where does "essential" or "basic" mask undefined requirements?
- Which line items are decisions disguised as Issues?

**Identify gaps:**

- What's assumed but not listed?
- What adjacent concerns are unaddressed?
- What does the validation checkpoint actually require that isn't explicitly tasked?

**Checklist:**

- [ ] Milestone outcome defined: "User can \_\_\_"
- [ ] Ambiguity surfaced and resolved
- [ ] Gaps identified and addressed (or consciously deferred)

---

## 2. Analysis: Separate Decisions from Issues

Decisions require deliberation and documentation; Issues require execution. Conflating them causes delays.

**Classify each line item:**

| Type     | Characteristics                                                           | Next Step                            |
| -------- | ------------------------------------------------------------------------- | ------------------------------------ |
| Decision | Multiple valid options, requires tradeoff evaluation, affects future work | Route to ADR process                 |
| Issue    | Clear implementation path, no significant alternatives                    | Add to Milestone                     |
| Hybrid   | Contains embedded decision                                                | Extract decision, then Issue remains |

**For each decision, identify:**

- What options exist?
- What information is needed to decide?
- Who should contribute to the decision?
- Who has authority to finalise?

**Team roles for decision input:**

| Role            | Typical Decision Domains                                       |
| --------------- | -------------------------------------------------------------- |
| Product Manager | Scope, priorities, user requirements, feature definition       |
| Tech Lead       | Architecture, patterns, technical standards, tooling           |
| Senior Engineer | Implementation approach, library selection, integration design |
| Data Engineer   | Data models, pipelines, analytics infrastructure               |
| Data Analyst    | Reporting requirements, metric definitions, data validation    |
| DevOps          | Deployment, infrastructure, environments, CI/CD                |
| Security        | Authentication, authorisation, data protection                 |
| Design          | UX patterns, component behaviour, accessibility                |

Assign decisions to appropriate contributors. Document outcomes as ADRs via Pull Request (process defined separately).

**Checklist:**

- [ ] Each line item classified as decision, Issue, or hybrid
- [ ] Decisions assigned to contributors for ADR process

---

## 3. Analysis: Map Dependencies

Sequencing mistakes cause blocked work and wasted context-switching.

**Build the dependency graph:**

- List all Issues and decisions
- For each item, ask: "What must exist before this can start?"
- Draw directed edges from dependencies to dependents

**Identify the critical path:**

- The longest chain of sequential dependencies determines minimum duration
- Delays on critical path delay the Milestone; delays elsewhere may not

**Define parallelisation points:**

- Mark where the graph branches (multiple items can start simultaneously)
- Note the earliest point parallel work can begin
- Identify synchronisation points (where branches must rejoin)

**Document as:**

```
[Issue A]
    ↓
[Issue B] → parallel: [Issue C], [Issue D]
              ↓
         [Issue E] (sync point)
```

**Checklist:**

- [ ] Dependencies mapped with parallelisation points marked

---

## 4. Analysis: Identify Risks

Unknown unknowns derail estimates; known unknowns can be investigated.

**Flag technical uncertainties:**

- Unproven integrations (will these tools work together?)
- Unfamiliar technology (does the team have sufficient experience?)
- External dependencies (APIs, services, third-party behaviour)
- Performance unknowns (will this approach scale?)

**For each risk, determine:**

- Likelihood (how likely is this to cause problems?)
- Impact (if it occurs, how much delay or rework?)
- Investigation cost (how long to reduce uncertainty?)

**Assign spike or accept:**

| Risk Level                     | Response                                      |
| ------------------------------ | --------------------------------------------- |
| High likelihood or high impact | Spike required before dependent Issues        |
| Medium                         | Spike if investigation cost is low            |
| Low                            | Accept and proceed; handle if it materialises |

**Spike definition:**

- Timeboxed investigation (2-4 hours typical)
- Specific question to answer
- Written output via Pull Request: "confirmed viable" or "blocker found with mitigation"

**Checklist:**

- [ ] Risks identified and assessed
- [ ] Spikes assigned for high-risk items

---

## 5. Decision: Execute Spikes

Spikes convert uncertainty into known constraints or confirmed approaches.

**For each required spike:**

- Assign to appropriate team member
- Set explicit timebox
- Define the specific question or hypothesis
- Document findings and submit via Pull Request before timebox expires

**Spike outputs feed back into:**

- Revised Issue definitions
- Updated dependency map
- New risks discovered
- Decisions that need resolving

**Checklist:**

- [ ] Spikes completed with findings merged via Pull Request

---

## 6. Decision: Resolve Decisions

Unresolved decisions block Issue definition and cause rework.

**For each identified decision:**

- Gather required input from assigned contributors
- Incorporate findings from relevant spikes
- Evaluate options against development philosophy
- Document as ADR with: context, options, decision, consequences
- Submit ADR via Pull Request for review and approval
- Communicate decision to affected team members

Decisions that don't depend on spike findings can be resolved earlier in parallel.

Proceed to Issue decomposition only after blocking decisions are resolved.

**Checklist:**

- [ ] Decisions documented as ADRs and merged via Pull Request

---

## 7. Resolution: Decompose Issues

Well-defined Issues enable accurate estimation and clear handoffs.

**Maintain vertical alignment:**

Each Issue must contribute to completing the Milestone's vertical slice. Avoid horizontal Issues that build out a layer without delivering end-to-end functionality toward the user outcome.

**Size appropriately:**

| Duration        | Action                          |
| --------------- | ------------------------------- |
| > 1 day         | Split into smaller Issues       |
| 2 hours – 1 day | Appropriate size                |
| < 2 hours       | Group with related small Issues |

**Group small Issues by expertise area** when individual items are under 2 hours:

- Engineering Issues together
- DevOps Issues together
- Data Issues together

**Define acceptance criteria:**

- Observable outcome (what exists or works when done)
- Verifiable by someone without the same expertise
- No jargon—plain description of what to check

**Issue tracking:**

Use GitHub Issues assigned to the Milestone. Native fields handle metadata; the description body handles context and acceptance criteria.

**Native GitHub fields:**

| Field      | Usage                                                                  |
| ---------- | ---------------------------------------------------------------------- |
| Title      | Action-oriented summary (e.g., "Add authentication middleware")        |
| Assignee   | Single owner responsible for completion                                |
| Labels     | Categorisation - use only from below table                             |
| Milestone  | Links Issue to the current Milestone                                   |
| Projects   | Optional board assignment if using GitHub Projects for status tracking |
| Linked PRs | Automatic when PR uses `Closes #123` - shows completion progress       |

**Labels**

| Label         | Colour           | Description                              |
| ------------- | ---------------- | ---------------------------------------- |
| `bug`         | Red `#d73a4a`    | Something isn't working                  |
| `feature`     | Green `#0e8a16`  | New functionality                        |
| `enhancement` | Blue `#a2eeef`   | Improvement to existing functionality    |
| `chore`       | Grey `#cfd3d7`   | Maintenance, refactoring, no user impact |
| `spike`       | Purple `#d4c5f9` | Timeboxed investigation                  |
| `docs`        | Yellow `#fef2c0` | Documentation only                       |
| `blocked`     | Red `#b60205`    | Waiting on dependency or external factor |
| `wontfix`     | White `#ffffff`  | Acknowledged but not planned             |
| `duplicate`   | Grey `#cfd3d7`   | Duplicate of another Issue               |

**Description template:**

```markdown
## Context

[Why this Issue exists, what it contributes to the Milestone outcome]

## Dependencies

- Blocked by #[number] — [brief reason]
- Blocked by #[number] — [brief reason]

## Acceptance Criteria

- [ ] [Observable, verifiable outcome 1]
- [ ] [Observable, verifiable outcome 2]
- [ ] [Observable, verifiable outcome 3]

## Technical Notes

[Optional implementation guidance, relevant ADR references, or constraints]
```

**Status tracking:**

With GitHub Projects: Use custom Status field (Backlog → Ready → In Progress → In Review → Done) for board visualisation.

Issues are completed via Pull Request. The PR description references the Issue (`Closes #123`), and approval/merge transitions the Issue to Done.

Avoid:

- Elaborate estimation ceremonies
- Extensive labelling or categorisation
- Documentation that duplicates the Issue description, or what the PR will show

**Checklist:**

- [ ] All Issues sized between 2 hours and 1 day
- [ ] Acceptance criteria verifiable by non-experts
- [ ] Issue Tracking details provided for all issues including completion of all fields

---

## 8. Resolution: Distribute Work

Match work to people based on skill and appropriate oversight.

**Assignment model:**

| Work Complexity                                  | Suited For         | Claude Model      |
| ------------------------------------------------ | ------------------ | ----------------- |
| Scope definition, requirements, prioritisation   | Product Manager    | Human Only        |
| Architecture, technical standards, tooling       | Tech Lead          | claude-opus-4-5   |
| Foundational implementation, integration design  | Senior Engineer    | claude-opus-4-5   |
| Data models, pipelines, analytics infrastructure | Data Engineer      | claude-opus-4-5   |
| Infrastructure, deployment, CI/CD                | DevOps/Platform    | claude-opus-4-5   |
| Authentication, authorisation, security reviews  | Security           | claude-opus-4-5   |
| UX patterns, component behaviour, visual design  | Design             | claude-opus-4-5   |
| Reporting, metrics, data validation              | Data Analyst       | claude-sonnet-4-5 |
| Standard implementation                          | Mid-level Engineer | claude-sonnet-4-5 |
| Well-defined, bounded tasks                      | Junior Engineer    | claude-sonnet-4-5 |
| Third-party integration, external communication  | Any                | Human Only        |

**Human-only Issues:**

- Vendor negotiations or account setup
- External API key provisioning
- Stakeholder communication
- Legal or compliance sign-off

**Milestone structure:**

A Milestone delivers the user outcome. Focus on achieving it, not filling a timebox.

- Milestone goal = the answer to "What can a user do when this is done?"
- All Issues contribute directly to that outcome
- Work not required for the outcome is excluded or deferred

**Checklist:**

- [ ] Assignments match skill and oversight model
- [ ] Human-only Issues identified
