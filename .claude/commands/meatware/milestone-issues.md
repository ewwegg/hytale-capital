---
description: Review and refine milestone issues for clarity, alignment, and completeness
argument-hint: [project-folder] [milestone-folder]
model: claude-opus-4-5
allowed-tools: Read, Write, Edit, Bash, Glob, Grep
---

# Milestone Issues

Review issues created during milestone resolution, identify problems or improvements, and revise issue files to ensure clarity, alignment with project outcomes, and completeness for high-quality implementation.

## Variables

PROJECT_FOLDER: $1
MILESTONE_FOLDER: $2
ROADMAP_BASE: .meatware/roadmap
MILESTONE_PATH: $ROADMAP_BASE/$PROJECT_FOLDER/$MILESTONE_FOLDER
ISSUES_DIR: $MILESTONE_PATH/issues
RESOLUTION_FILE: $MILESTONE_PATH/milestone-resolution.md
ANALYSIS_FILE: $MILESTONE_PATH/milestone-analysis.md
PROJECT_PLAN: $ROADMAP_BASE/$PROJECT_FOLDER/\*.md
DEVELOPMENT_PHILOSOPHY: .meatware/references/development-philosophy.md
TECH_STACK: .meatware/references/tech-stack.md
ISSUE_TEMPLATE: .meatware/references/templates/issue-template.md
WRITING_SPEC: .meatware/references/writing-spec.md
ADR_BASE: .meatware/adr
SPIKES_DIR: $MILESTONE_PATH/spikes

## Core Responsibilities

- Verify each issue enables high-quality implementation
- Ensure issues align with milestone outcomes and development philosophy
- Check technical constraints match tech stack and ADR decisions
- Identify missing information that would block or confuse implementation
- Add implementation guidance where approach is non-obvious
- Document explicit scope boundaries to prevent scope creep
- Consolidate resources for easy reference
- Apply WRITING_SPEC brevity requirements throughout revisions

## Workflow

### Step 1: Validate & Load Context

**Verify inputs exist:**

- Check PROJECT_FOLDER and MILESTONE_FOLDER provided
- Verify RESOLUTION_FILE exists
- Verify ISSUES_DIR exists and contains issue files
- If missing: Report "Run /meatware:milestone-resolution first" and exit

**Load reference documents:**

- Read DEVELOPMENT_PHILOSOPHY for evaluation principles
- Read TECH_STACK for technology constraints and versions
- Read ISSUE_TEMPLATE for expected structure
- Read WRITING_SPEC for brevity requirements

**Load milestone context:**

- Read RESOLUTION_FILE for milestone outcomes and issue list
- Read ANALYSIS_FILE for scope boundaries and decisions
- Read project plan file(s) in PROJECT_FOLDER for broader context
- List all ADRs in ADR_BASE referenced by this milestone
- List all spikes in SPIKES_DIR

**Build issue inventory:**

- List all issue files in ISSUES_DIR
- Store list for analysis

### Step 2: Analyze Each Issue

**For each issue file in ISSUES_DIR:**

1. **Parse issue structure:**

   - Extract frontmatter (title, type, responsibility, model, blocked-by)
   - Identify which sections exist vs missing
   - Note section completeness

2. **Check alignment with milestone outcomes:**

   - Does Context explain contribution to user outcome?
   - Do Acceptance Criteria deliver observable value?
   - Is scope appropriately narrow (vertical slice, not horizontal layer)?

3. **Evaluate against development philosophy:**

   | Principle                 | Check                                   |
   | ------------------------- | --------------------------------------- |
   | Working beats perfect     | Does this deliver functional increment? |
   | Simple beats flexible     | Is scope minimal for the value?         |
   | Conventional beats clever | Are standard patterns used?             |
   | Explicit beats abstract   | Is implementation approach clear?       |
   | Reversible beats optimal  | Can decisions be easily changed?        |

4. **Verify technical alignment:**

   - Do Technical Constraints reference correct versions from TECH_STACK?
   - Are relevant ADR decisions cited?
   - Are spike findings incorporated?
   - Do implementation notes match tech stack patterns?

5. **Assess implementation clarity:**

   - Could a developer unfamiliar with codebase implement this?
   - Are non-obvious steps documented?
   - Is order of operations clear?
   - Are edge cases addressed?

6. **Check for common issues:**

   | Issue Type             | What to Check                                |
   | ---------------------- | -------------------------------------------- |
   | Missing metadata       | Page titles, descriptions, OG tags for pages |
   | Missing error handling | Error states, loading states, boundaries     |
   | Missing validation     | Input validation, type constraints           |
   | Missing security       | Access control, authentication checks        |
   | Incomplete CRUD        | All operations verified, not just create     |
   | Performance gaps       | Image optimization, query efficiency         |
   | Configuration gaps     | Environment variables, feature flags         |

7. **Identify scope boundaries:**

   - What related work is explicitly excluded?
   - What might cause scope creep?
   - Are exclusions documented in Out of Scope?

8. **Verify resources:**
   - Are ADR paths correct and complete?
   - Are spike paths correct?
   - Are external documentation links relevant?

**Record findings for each issue:**

- Problems: Issues that would block or degrade implementation
- Improvements: Enhancements that would improve clarity or quality
- Additions: Missing sections or content to add
- Scope notes: Items to add to Out of Scope

### Step 3: Cross-Issue Analysis

**Check consistency across all issues:**

- Naming conventions consistent?
- Similar issues use similar patterns?
- Dependencies correctly specified?
- No gaps in the implementation chain?

**Identify cross-cutting concerns:**

- Error handling patterns needed across multiple issues?
- Common configuration requirements?
- Shared utilities or types?
- Testing patterns?

**Check dependency chain integrity:**

- Every blocked-by reference points to valid issue?
- No circular dependencies?
- Critical path still valid?
- Parallel work correctly identified?

### Step 4: Revise Issue Files

**For each issue requiring revision:**

1. **Update Context section:**

   - Strengthen connection to milestone outcome if weak
   - Clarify contribution to user value

2. **Refine Acceptance Criteria:**

   - Add missing verifiable outcomes
   - Remove vague or uncheckable criteria
   - Ensure criteria are observable by non-experts

3. **Update Workflow:**

   - Provide step-by-step guidance where approach is non-obvious
   - Reference patterns from tech stack
   - Include order of operations
   - Note common pitfalls to avoid
   - Never include code blocks

4. **Update Technical Constraints:**

   - Cite specific versions from TECH_STACK
   - Reference relevant ADRs with full paths
   - Include spike findings with full paths
   - Document hard requirements from external systems

5. **Update Out of Scope:**

   - List explicitly excluded related work
   - Document deferred items
   - Prevent scope creep vectors

6. **Update Resources:**

   - Ensure all ADR references use full paths
   - Ensure all spike references use full paths
   - Add relevant external documentation links
   - Remove broken or irrelevant links

7. **Verify frontmatter:**
   - Correct blocked-by references
   - Appropriate responsibility for complexity
   - Correct type and label

**Apply revisions using Edit tool:**

- Make targeted edits preserving unchanged content
- Maintain consistent formatting
- Follow WRITING_SPEC requirements

### Step 5: Verify Changes

**For each revised issue:**

- Confirm changes applied correctly
- Verify no formatting issues
- Check all sections present and in correct order

## Report

```
Milestone Issues Refined

**Milestone**: $PROJECT_FOLDER / $MILESTONE_FOLDER
**Issues Reviewed**: [total count]
**Issues Revised**: [revised count]
**Issues Unchanged**: [unchanged count]

**Remaining Concerns:**
- [Any issues that couldn't be fully addressed]
- [Recommendations for manual review]
```
