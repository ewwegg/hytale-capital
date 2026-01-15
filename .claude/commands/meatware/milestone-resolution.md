---
description: Decompose issues and distribute work after all ADRs are accepted
argument-hint: [project-folder] [milestone-folder]
model: claude-opus-4-5
allowed-tools: Read, Write, Bash, Glob, Grep
---

# Milestone Resolution

Execute Resolution steps 7-8 of the planning workflow: decompose issues into actionable work items and distribute assignments.

## Variables

PROJECT_FOLDER: $1
MILESTONE_FOLDER: $2
ROADMAP_BASE: .meatware/roadmap
MILESTONE_PATH: $ROADMAP_BASE/$PROJECT_FOLDER/$MILESTONE_FOLDER
ANALYSIS_FILE: $MILESTONE_PATH/milestone-analysis.md
DECISIONS_FILE: $MILESTONE_PATH/milestone-decisions.md
OUTPUT_FILE: $MILESTONE_PATH/milestone-resolution.md
ADR_BASE: .meatware/adr
PLANNING_WORKFLOW: .meatware/references/planning-workflow.md
DEVELOPMENT_PHILOSOPHY: .meatware/references/development-philosophy.md
ISSUE_TEMPLATE: .meatware/references/templates/issue-template.md
RESOLUTION_TEMPLATE: .meatware/references/templates/milestone-resolution-template.md
WRITING_SPEC: .meatware/references/writing-spec.md

## Core Responsibilities

- Verify all ADRs are accepted before proceeding
- Read PLANNING_WORKFLOW for full process context
- Consolidate all issues from analysis, ADRs, and spike findings
- Decompose issues to appropriate size (2 hours – 1 day)
- Group small issues by expertise area (keep groups under 2 hours)
- Define verifiable acceptance criteria for each issue
- Assign work based on complexity and skill requirements
- Identify human-only issues explicitly
- Apply WRITING_SPEC brevity requirements throughout

## Workflow

### Step 1: Validate Prerequisites

**Verify inputs exist:**

- Check PROJECT_FOLDER and MILESTONE_FOLDER provided
- Verify ANALYSIS_FILE exists
- Verify DECISIONS_FILE exists
- If missing: Report "Run /meatware:milestone-analysis and /meatware:milestone-decisions first" and exit

**Load reference documents:**

- Read PLANNING_WORKFLOW for process understanding
- Read DEVELOPMENT_PHILOSOPHY for evaluation criteria
- Read ISSUE_TEMPLATE for issue structure
- Read RESOLUTION_TEMPLATE for output structure

**Load milestone context:**

- Read ANALYSIS_FILE completely for scope, issues, and dependencies
- Read DECISIONS_FILE completely for spike findings and ADR outcomes

**Verify all ADRs are accepted:**

- For each ADR listed in DECISIONS_FILE:
  - Read the ADR file from ADR_BASE
  - Check status field in frontmatter
  - If any ADR has status other than "Accepted": Report which ADRs need review and exit

### Step 2: Consolidate All Issues

**Gather issues from all sources:**

1. **From ANALYSIS_FILE:**

   - Extract all issues from the "Issues (Ready for Work)" table
   - Note dependencies from the dependency graph

2. **From ADR outcomes:**

   - For each accepted ADR, identify any implementation requirements
   - Note constraints or new requirements from consequences sections

3. **From spike findings:**
   - For each spike in DECISIONS_FILE, check for new issues surfaced
   - Note any scope changes or technical requirements discovered

**Create consolidated issue list:**

- Combine all issues into a single working list
- Note the source of each issue (Analysis, ADR, Spike)
- Flag any duplicates or overlapping issues for merging

### Step 3: Decompose Issues

**For each issue in consolidated list:**

1. **Assess duration:**

   - Estimate time to complete
   - Apply sizing rules:

   | Duration        | Action                          |
   | --------------- | ------------------------------- |
   | > 1 day         | Split into smaller issues       |
   | 2 hours – 1 day | Appropriate size                |
   | < 2 hours       | Group with related small issues |

2. **Split large issues:**

   - Identify natural boundaries (setup vs implementation vs verification)
   - Ensure each sub-issue delivers observable progress
   - Maintain vertical alignment—avoid horizontal layer work

3. **Group small issues:**

   - Combine by expertise area (Engineering, DevOps, Data, Design, Docs)
   - Create single issue with multiple acceptance criteria
   - Keep grouped issues under 2 hours total

4. **Define acceptance criteria:**
   - Observable outcome (what exists or works when done)
   - Verifiable by someone without the same expertise
   - No jargon—plain description of what to check
   - 2-5 criteria per issue

### Step 4: Determine Execution Order

**Map dependencies:**

- Identify which issues block others
- Note issues that can proceed in parallel
- Update relationships based on decomposition changes

**Create execution sequence:**

- Order issues by dependency chain
- Issues with no blockers come first
- Parallel issues share the same sequence position
- Number issues 1 through N in execution order

### Step 5: Assign Work

**For each issue, determine assignment:**

| Work Complexity                          | Suited For         | Claude Model      |
| ---------------------------------------- | ------------------ | ----------------- |
| Architecture, technical standards        | Tech Lead          | claude-opus-4-5   |
| Foundational implementation, integration | Senior Engineer    | claude-opus-4-5   |
| Infrastructure, deployment, CI/CD        | DevOps/Platform    | claude-opus-4-5   |
| Standard implementation                  | Mid-level Engineer | claude-sonnet-4-5 |
| Well-defined, bounded tasks              | Junior Engineer    | claude-sonnet-4-5 |
| Documentation                            | Any                | claude-sonnet-4-5 |

**Identify human-only issues:**

- Vendor negotiations or account setup
- External API key provisioning
- Stakeholder communication
- Legal or compliance sign-off
- Third-party integration requiring external communication

### Step 6: Create Resolution Summary

**Create summary document first** using RESOLUTION_TEMPLATE:

- Filename: `milestone-resolution.md`
- Location: $MILESTONE_PATH

_Creating the summary before issue files ensures complete understanding of all issues and their relationships._

**Summary contents:**

- Milestone outcome (from analysis)
- Decision outcomes applied (ADR references with full paths)
- Spike findings applied (with full paths)
- Issue list table (links will point to issue documents created in Step 7)
- Execution order diagram
- Human-only issues highlighted

### Step 7: Create Issue Documents

**Create issues directory:**

```bash
mkdir -p $MILESTONE_PATH/issues
```

**For each decomposed issue, in execution order:**

1. **Determine filename:**

   - Format: `[N]-[kebab-case-title].md`
   - N = sequence number (1, 2, 3, ...)
   - Example: `1-initialise-nextjs-project.md`, `2-configure-supabase-connection.md`

2. **Create issue document** using ISSUE_TEMPLATE:
   - Location: $MILESTONE_PATH/issues/
   - Populate all frontmatter fields
   - Write context explaining contribution to milestone
   - List dependencies by issue number
   - Define acceptance criteria
   - Add technical notes (ADR references with full paths, spike findings with full paths, constraints)

### Step 8: Present for Approval

**Present resolution summary to human:**

```
Milestone Resolution Complete

**Milestone**: $PROJECT_FOLDER / $MILESTONE_FOLDER
**Outcome**: [User outcome from analysis]

**Total Issues**: [count]

**Human-Only Issues**: [count or "None"]
[List if any]

**Issue Documents Created:**
$MILESTONE_PATH/issues/

**Please review and respond with one of:**

- **Approve** — Resolution is complete, ready for execution
- **Revise [instructions]** — Provide adjustments to decomposition or assignments
- **Cancel** — Stop the workflow (documents will remain for reference)
```

**Wait for human response:**

- **Approve**: Proceed to final report
- **Revise [instructions]**:
  - Parse the instructions provided
  - Update issue documents and resolution summary
  - Re-display the summary
  - Wait for new response
- **Cancel**:
  - Report: "Workflow cancelled. Issue documents preserved at $MILESTONE_PATH/issues/"
  - Exit workflow

## Report

```
Milestone Resolution Approved

**Milestone**: $PROJECT_FOLDER / $MILESTONE_FOLDER
**Status**: Ready for execution

**Output Files:**
- Resolution summary: $MILESTONE_PATH/milestone-resolution.md
- Issue documents: $MILESTONE_PATH/issues/

**Next Steps:**
1. Create GitHub Issues from documents (or use as task reference)
2. Begin work on issues with no blockers
3. Track progress via PR references
```
