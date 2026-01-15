---
description: Analyze a milestone to clarify scope, classify work items, map dependencies, and identify risks
argument-hint: [project-folder] [milestone-folder]
model: claude-opus-4-5
allowed-tools: Read, Write, Bash, Glob, Grep
---

# Milestone Analysis

Execute the Analysis phase of the planning workflow for the specified milestone.

## Variables

PROJECT_FOLDER: $1
MILESTONE_FOLDER: $2
ROADMAP_BASE: .meatware/roadmap
MILESTONE_PATH: $ROADMAP_BASE/$PROJECT_FOLDER/$MILESTONE_FOLDER
OUTPUT_FILE: $MILESTONE_PATH/milestone-analysis.md
PLANNING_WORKFLOW: .meatware/references/planning-workflow.md
TEMPLATE_FILE: .meatware/references/templates/milestone-analysis-template.md
DEVELOPMENT_PHILOSOPHY: .meatware/references/development-philosophy.md
WRITING_SPEC: .meatware/references/writing-spec.md

## Terminology

Use consistent terminology when describing people interacting with the system:

| Term       | When to Use                                                                      |
| ---------- | -------------------------------------------------------------------------------- |
| Visitor    | Anonymous people viewing public-facing content (no authentication)               |
| User       | Authenticated people using the product (has account, logged in)                  |
| Editor     | People managing content via admin interface (content operations)                 |
| Admin      | People configuring system-level settings (infrastructure, integrations, domains) |
| API client | External applications consuming APIs                                             |

Never use generic "user" for anonymous visitors. Identify which roles are relevant to each milestone during scope clarification.

## Core Responsibilities

- Read PLANNING_WORKFLOW for full process context before starting
- Follow DEVELOPMENT_PHILOSOPHY principles when evaluating scope
- Use TEMPLATE_FILE structure for output
- Apply WRITING_SPEC brevity requirements throughout
- Classify every line item—leave nothing ambiguous
- Document assumptions explicitly
- Flag items requiring human decision before Resolution phase
- Reference other milestones by name, not number: `(Milestone: Trust System)` not `(Milestone 2)`

## Workflow

### Step 1: Validate & Load Context

**Verify inputs exist:**

- Check PROJECT_FOLDER and MILESTONE_FOLDER provided
- Verify MILESTONE_PATH exists: `ls -la $MILESTONE_PATH`
- If missing: Report error and exit

**Load reference documents:**

- Read PLANNING_WORKFLOW for process understanding
- Read DEVELOPMENT_PHILOSOPHY for evaluation criteria
- Read TEMPLATE_FILE for output structure

**Load milestone content:**

- Read all files in PROJECT_PATH (not in subfolders unless specified) and MILESTONE_PATH
- Identify milestone definition source
- List all line items requiring analysis

### Step 2: Clarify Scope

**Identify relevant roles:**

Review the milestone line items and determine which roles benefit from this work. Reference the Terminology table. Common patterns:

- Public-facing features → Visitor outcomes
- Authenticated product features → User outcomes
- Content management features → Editor outcomes
- System configuration features → Admin outcomes

**Define outcomes for each relevant role:**

For each identified role, complete: "When this milestone is done, a [role] can \_\_\_"

At minimum, one outcome must describe observable value for visitors or users (the people the product serves). If no visitor/user outcome exists, flag for scope reduction—the milestone may be infrastructure without user value.

**Review against development philosophy:**

- Does milestone deliver observable value to visitors or users?
- Is scope minimal for that value?
- Are we building through the stack narrowly?

**Surface ambiguity:**

- List terms lacking precise definitions
- Identify "essential" or "basic" masking undefined requirements
- Flag line items that are decisions disguised as issues

**Identify gaps:**

- What's assumed but not listed?
- What adjacent concerns are unaddressed?
- What does validation checkpoint require that isn't tasked?

**Required Deliverable:** Populate template Scope section with outcomes for each relevant role, boundaries, ambiguity resolutions, and gaps table.

### Step 3: Separate Decisions from Issues

**Classify each line item:**

| Type     | Characteristics                                        | Action                          |
| -------- | ------------------------------------------------------ | ------------------------------- |
| Decision | Multiple valid options, requires tradeoff evaluation   | Assign to ADR process           |
| Issue    | Clear implementation path, no significant alternatives | Add to Issues table             |
| Hybrid   | Contains embedded decision                             | Extract decision, issue remains |

**For each decision, identify:**

- Available options
- Information needed to decide
- Who should contribute (reference role table in PLANNING_WORKFLOW)
- Who has authority to finalise

**For each issue, identify:**

- Type: feature, chore, spike, docs, bug
- Brief acceptance criteria (what's observable when done)

**Required Deliverable:** Populate template Classification section with Decisions, Issues, and Hybrids tables.

### Step 4: Map Dependencies

**Build dependency graph:**

- List all issues and decisions from Step 3
- For each item ask: "What must exist before this can start?"
- Create a text-based directed graph using the arrow notation format specified in TEMPLATE_FILE

**Arrow notation format:**

```
[Item A]
    ↓
[Item B] → parallel: [Item C], [Item D]
              ↓
         [Item E] (sync point)
```

- Use `↓` for sequential dependencies (top to bottom)
- Use `→ parallel:` to indicate items that can run simultaneously
- Use `(sync point)` to mark where parallel branches must rejoin
- Indent to show hierarchy and flow

**Identify critical path:**

- Find longest chain of sequential dependencies
- This determines minimum milestone duration

**Mark parallelisation points:**

- Where does graph branch?
- What items can start simultaneously?
- Where must branches synchronise?

**Required Deliverable:** Populate template Dependencies section with text-based dependency graph and parallelisation table.

### Step 5: Identify Risks

**Flag technical uncertainties:**

- Unproven integrations
- Unfamiliar technology
- External dependencies
- Performance unknowns

**Assess each risk:**

- Likelihood: High/Medium/Low
- Impact: High/Medium/Low
- Investigation cost: Hours estimate

**Assign response:**

| Risk Level                     | Response                        |
| ------------------------------ | ------------------------------- |
| High likelihood OR high impact | Spike required                  |
| Medium                         | Spike if investigation cost low |
| Low                            | Accept and proceed              |

**Define spikes:**

- Specific question to answer
- Timebox (2-4 hours typical)
- Assigned role

**Required Deliverable:** Populate template Risks section with uncertainties table, spike assignments table, and accepted risks table.

### Step 6: Generate Analysis Document

**Write analysis document:**

- Follow TEMPLATE_FILE structure exactly
- Apply WRITING_SPEC brevity requirements
- No preambles, conclusions, or redundancy
- Front-load critical information
- Use tables for structured data

**Save to OUTPUT_FILE**

## Report

```
Milestone Analysis Complete

**Milestone**: $PROJECT_FOLDER / $MILESTONE_FOLDER
**Output**: $OUTPUT_FILE

**Analysis Summary:**
- User Outcome: [one sentence]
- Decisions Identified: [count] (require ADR before Resolution)
- Issues Identified: [count]
- Spikes Required: [count]

**Next Step**: Run `/meatware:milestone-resolution` after decisions are resolved via ADR process
```
