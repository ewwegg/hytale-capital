---
description: Execute next ready Agent issue from project after dependency validation
argument-hint: [project-number]
model: claude-opus-4-5
allowed-tools: Read, Bash(gh:*), Bash(git:*), Write, Edit, Glob, Grep, mcp__github__*
disable-model-invocation: true
---

# Execute Ready Issue

Execute next ready Agent issue from project $1 after validating dependencies and reading project documentation.

## Variables

PROJECT_NUMBER: $1
ROADMAP_BASE: `.meatware/roadmap`
ROADMAP_INDEX: `${ROADMAP_BASE}/index.yml`

# Captured during workflow

project_owner: From ROADMAP_INDEX github.project_owner
github_title: From ROADMAP_INDEX projects[].github_title
github_project_number: Matched from GitHub API by title
github_issue_number: Selected from Ready column
issue_title: From issue body
issue_body: Full GitHub issue content

## Context

- Repository: !`gh repo view --json nameWithOwner -q .nameWithOwner`
- Current branch: !`git branch --show-current`

## Core Responsibilities

- Find next Agent issue in "Ready" status on Projects V2 board
- Verify all dependencies in issue body are in "Done" status or archived
- Exit if issue type is "Human" (not "Agent")
- Read project documentation before starting work
- Move issue through board statuses (Ready → In Progress → In Review)
- Execute workflow defined in GitHub issue body
- Update acceptance criteria checkboxes as completed
- Post issue comment starting with "Comment by Agent:" following writing-spec.md (no emojis)
- Handle errors and report blocking issues clearly

## Workflow

1. **Validate inputs and prerequisites** - Check environment

   - Verify PROJECT_NUMBER provided
   - Verify gh CLI authenticated with project scope: `gh auth status` (must show 'project' in scopes)
   - If validation fails, report error and exit

2. **Load configuration** - Get project details

   - Read ROADMAP_INDEX using Read tool
   - Find project by PROJECT_NUMBER (id field): extract `github_title` and GitHub config
   - Load Projects V2 config (project_owner, status_field)
   - Look up GitHub project using `mcp__github__list_projects` for project_owner
   - Match returned projects to github_title field (fuzzy matching acceptable)
   - Store actual project number from GitHub API response
   - If project not found, exit with error

3. **Query Ready issues** - Find candidates in Ready column

   - Get project fields: `mcp__github__list_project_fields`
   - Find Status field ID and option IDs (Ready, Done, In Progress, In Review)
   - Find Type field ID for filtering
   - List project items: `mcp__github__list_project_items` with query `status:Ready is:issue`
   - Filter results to include Type field data
   - If no Ready issues found, exit with message: "No issues in Ready status"

4. **Select Agent issue** - Find next Agent type issue

   - Iterate through Ready issues
   - For each issue, check Type field value
   - Skip issues where Type is not "Agent"
   - Select first Agent issue found
   - If no Agent issues in Ready status, exit with message: "No Agent issues ready. All ready issues are Human type."
   - Store selected issue number and body

5. **Validate issue type** - Confirm Agent assignment

   - Read full issue body: `mcp__github__issue_read` with method='get'
   - Parse frontmatter to extract type field
   - If type != "Agent", exit with message: "Issue #{number} is type {type}. Only Agent issues can be executed."
   - If type == "Agent", continue

6. **Verify dependencies resolved** - Check blocked-by in issue body

   - Parse blocked-by array from frontmatter in issue body
   - If no dependencies, continue
   - For each blocking issue number:
     - Get issue status from project: `mcp__github__list_project_items` with query filtering by issue
     - Verify status is "Done" or issue is archived
     - If any dependency not resolved, exit with message: "Blocked by #{issue}: status {status}. Cannot proceed."
   - If all dependencies resolved, continue

7. **Read project documentation** - Load context before execution

   - Read required files in order:
     - `README.md`
     - `.meatware/references/tech-stack.md`
     - `.meatware/references/development-philosophy.md`
     - `.meatware/references/writing-spec.md`
     - `.meatware/references/development/conventions.md`
     - `.meatware/references/design/components.md`
     - `.meatware/references/design/typography.md`
     - `.meatware/references/design/accessibility.md`
   - Note any ADRs referenced in issue body and read those
   - Report: "Documentation loaded"

8. **Move issue to In Progress** - Update board status

   - Get project item ID for issue: from step 3 results
   - Update status field: `mcp__github__update_project_item` with status="In Progress" option ID
   - Report: "Moved issue #{github_issue_number} to In Progress"

9. **Execute issue workflow** - Complete the work

   - Follow Workflow section from issue body
   - Implement each step sequentially
   - Use type and label to understand issue nature
   - Apply responsibility and model guidance
   - Track completed acceptance criteria
   - Track errors or decisions made
   - On blocking error (cannot proceed):
     - Build error report following writing-spec.md
     - Start with "Comment by Agent:"
     - Describe error, attempted solutions, blocking issue
     - Post comment: `mcp__github__add_issue_comment`
     - Move issue back to Ready: `mcp__github__update_project_item`
     - Exit with error status
   - On recoverable error: document in tracking, continue execution

10. **Update acceptance criteria** - Mark completed items

    - Read current issue body: `mcp__github__issue_read` with method='get'
    - For each completed acceptance criterion:
      - Replace `- [ ]` with `- [x]` in issue body
    - Update issue body: `gh issue edit {github_issue_number} --body "{updated_body}"`
    - Report: "Updated {count} acceptance criteria"

11. **Post completion comment on issue** - Report final status

    - Build completion report following writing-spec.md format:
      - Start with "Comment by Agent:"
      - Work completed (bullet points, no "Summary:" heading)
      - Errors encountered (if any)
      - Decisions made during implementation
      - Scope changes (if any)
      - Files created or modified
      - NO emojis, NO meta-commentary ("I completed...", "In summary...")
      - Front-load critical information
    - Post comment: `mcp__github__add_issue_comment` with report
    - Report: "Posted completion comment on issue"

12. **Move issue to In Review** - Update final status

    - Update status field: `mcp__github__update_project_item` with status="In Review" option ID
    - Report: "Moved issue #{github_issue_number} to In Review"

13. **Present report** - Show Report section below

## Report

Issue Execution Complete

**Repository:** {owner}/{repo}
**Issue:** #{github_issue_number}: {issue_title}
**Type:** Agent
**Status:** In Review (awaiting human approval)

**Work Completed:**

- Acceptance Criteria: {completed}/{total}
- Files Modified: {count}
- Files Created: {count}

**Execution Summary:**

- Errors: {count or "None"}
- Decisions: {count or "None"}
- Scope Changes: {description or "None"}

**GitHub Actions:**

- Status updates: Ready → In Progress → In Review
- Issue comment posted starting with "Comment by Agent:"
- Acceptance criteria updated: {count} items

**Next Steps:**

- Human review required in Projects V2 board
- If approved, move to Done status
- If changes needed, comment on issue and return to In Progress
