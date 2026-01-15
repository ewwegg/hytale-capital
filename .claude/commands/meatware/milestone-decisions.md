---
description: Execute spikes and create ADR documents for milestone decisions requiring human resolution
argument-hint: [project-folder] [milestone-folder]
model: claude-opus-4-5
allowed-tools: Read, Write, Bash, Glob, Grep
---

# Milestone Decisions

Execute Decision steps 5-6 of the planning workflow: run spikes and prepare ADRs for human decision-making.

## Variables

PROJECT_FOLDER: $1
MILESTONE_FOLDER: $2
ROADMAP_BASE: .meatware/roadmap
MILESTONE_PATH: $ROADMAP_BASE/$PROJECT_FOLDER/$MILESTONE_FOLDER
ANALYSIS_FILE: $MILESTONE_PATH/milestone-analysis.md
ADR_BASE: .meatware/adr
ADR_README: $ADR_BASE/README.md
SPIKE_OUTPUT_DIR: $MILESTONE_PATH/spikes
PLANNING_WORKFLOW: .meatware/references/planning-workflow.md
DEVELOPMENT_PHILOSOPHY: .meatware/references/development-philosophy.md
ADR_TEMPLATE: .meatware/references/templates/adr-template.md
SPIKE_TEMPLATE: .meatware/references/templates/spike-template.md
DECISIONS_SUMMARY_TEMPLATE: .meatware/references/templates/milestone-decisions-template.md
WRITING_SPEC: .meatware/references/writing-spec.md

## Core Responsibilities

- Read PLANNING_WORKFLOW for full process context
- Read ANALYSIS_FILE completely for full milestone context
- Execute spike investigations within defined timeboxes
- Create ADR documents with all options evaluated
- Present decisions for human confirmation—never auto-accept
- Follow MADR (Markdown ADR) extended format
- Update ADR_README index when creating new ADRs
- Apply WRITING_SPEC brevity requirements throughout

## Workflow

### Step 1: Validate & Load Context

**Verify inputs exist:**

- Check PROJECT_FOLDER and MILESTONE_FOLDER provided
- Verify ANALYSIS_FILE exists: `ls -la $ANALYSIS_FILE`
- If missing: Report "Run /meatware:milestone-analysis first" and exit

**Load reference documents:**

- Read PLANNING_WORKFLOW for process understanding
- Read DEVELOPMENT_PHILOSOPHY for evaluation criteria
- Read ADR_TEMPLATE for ADR structure
- Read SPIKE_TEMPLATE for spike structure
- Read DECISIONS_SUMMARY_TEMPLATE for summary structure

**Load and understand analysis:**

- Read ANALYSIS_FILE completely to understand full milestone context
- Extract Spike Assignments table entries
- Extract Decisions table entries
- Note all scope, dependencies, and risk context for reference during spike and ADR creation

**Create spike output directory:**

```bash
mkdir -p $SPIKE_OUTPUT_DIR
```

### Step 2: Execute Spikes

**For each spike in Spike Assignments table:**

1. **Create spike document** using SPIKE_TEMPLATE

   - Filename: `spike-[kebab-case-topic].md`
   - Location: $SPIKE_OUTPUT_DIR

2. **Conduct investigation within timebox:**

   - Research the specific question
   - Test assumptions where possible
   - Document findings as discovered
   - Note any new risks or decisions surfaced

3. **Document findings:**

   - Answer the spike question definitively: Confirmed / Blocked / Inconclusive
   - List evidence supporting conclusion
   - Note impact on related decisions
   - Identify any new issues or risks discovered

4. **Update spike document** with complete findings

**Required Deliverable:** Spike documents saved to $SPIKE_OUTPUT_DIR with findings populated.

### Step 3: Request Spike Approval

**Present spike findings to human for review:**

```
Spike Investigation Complete

**Spikes Executed:**
[For each spike:]
- [spike-topic]: [Outcome - Confirmed/Blocked/Inconclusive]
  - Question: [The question investigated]
  - Finding: [Brief summary of key finding]
  - Impact: [How this affects decisions or issues]

**Spike Documents Created:**
- $SPIKE_OUTPUT_DIR/spike-[topic].md

**Please review the spike findings and respond with one of:**

- **Approve** — Findings are accurate, proceed to ADR creation
- **Revise [instructions]** — Provide additional context or corrections to incorporate
- **Cancel** — Stop the workflow (spike documents will remain for reference)
```

**Wait for human response:**

- **Approve**: Proceed to Step 4
- **Revise [instructions]**:
  - Parse the instructions provided
  - Update spike documents with new information or corrections
  - Re-display the spike summary
  - Wait for new response
- **Cancel**:
  - Report: "Workflow cancelled. Spike documents preserved at $SPIKE_OUTPUT_DIR"
  - Exit workflow

### Step 4: Determine Next ADR Number

**Check existing ADRs:**

```bash
ls -1 $ADR_BASE/*.md 2>/dev/null | grep -E '^[0-9]{4}-' | sort -V | tail -1
```

- If no ADRs exist: Start at 0001
- If ADRs exist: Extract highest number, increment by 1
- Store as NEXT_ADR_NUMBER

### Step 5: Create ADR Documents

**For each decision in Decisions table:**

1. **Gather context:**

   - Review related spike findings (if any)
   - Review blocked issues from analysis
   - Identify constraints from DEVELOPMENT_PHILOSOPHY

2. **Research options:**

   - List all viable options from analysis
   - Add any options discovered during spikes
   - For each option, identify pros and cons

3. **Evaluate against development philosophy:**

   - Working beats perfect
   - Simple beats flexible
   - Conventional beats clever
   - Explicit beats abstract
   - Reversible beats optimal

4. **Create ADR document** using ADR_TEMPLATE:

   - Filename: `[NEXT_ADR_NUMBER]-[kebab-case-title].md`
   - Location: $ADR_BASE
   - Status: Proposed
   - Include all options with pros/cons
   - Provide recommended decision with rationale
   - Document expected consequences

5. **Update ADR index:**

   - Add new entry to the Index table in ADR_README
   - Include: Number, Title, Status (Proposed), Date

6. **Increment NEXT_ADR_NUMBER**

**Required Deliverable:** ADR documents saved to $ADR_BASE with Status: Proposed. ADR_README index updated.

### Step 6: Create Decisions Summary

**Create summary document** using DECISIONS_SUMMARY_TEMPLATE:

- Filename: `milestone-decisions.md`
- Location: $MILESTONE_PATH

**Summary contents:**

- List of spikes executed with outcomes
- List of ADRs created with recommended decisions
- Blocking relationships (which issues wait on which ADRs)
- Instructions for human review process
- Decision log table for tracking status changes

**Required Deliverable:** Summary document saved to $MILESTONE_PATH/milestone-decisions.md

### Step 7: Present for Human Review

**Do not proceed to issue decomposition.**

All ADRs require human review and status change from "Proposed" to "Accepted" before the Resolution phase can continue.

## Report

```
Milestone Decisions Prepared

**Milestone**: $PROJECT_FOLDER / $MILESTONE_FOLDER

**Spikes Executed:**
- [spike-name]: [Confirmed/Blocked/Inconclusive]

**ADRs Created (Pending Human Review):**
- [ADR-NNNN]: [Title] — Recommended: [Option]

**Output Files:**
- Spikes: $SPIKE_OUTPUT_DIR/
- ADRs: $ADR_BASE/
- ADR Index: $ADR_README (updated)
- Summary: $MILESTONE_PATH/milestone-decisions.md

**Human Action Required:**
1. Review each ADR in $ADR_BASE/
2. Confirm or modify the recommended decision
3. Change Status from "Proposed" to "Accepted"
4. Run `/meatware:milestone-resolution` after all ADRs accepted
```
