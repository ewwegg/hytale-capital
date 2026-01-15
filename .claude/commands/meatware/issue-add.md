---
description: Create GitHub issues from milestone files with Projects V2 integration
argument-hint: [project-number] [milestone-number] [issue-number?]
model: claude-sonnet-4-5
allowed-tools: Read, Bash(gh:*), Bash(yq:*), Bash(which:*), Bash(find:*), Bash(ls:*), Glob
disable-model-invocation: true
---

# Add Issues to GitHub

Create GitHub issues from milestone markdown files, assign to milestone, link dependencies, and add to Projects V2 kanban board.

## Variables

PROJECT_NUMBER: $1
MILESTONE_NUMBER: $2
ISSUE_NUMBER: $3
ROADMAP_BASE: `.meatware/roadmap`
ROADMAP_INDEX: `${ROADMAP_BASE}/index.yml`

## Context

- Repository: !`gh repo view --json nameWithOwner -q .nameWithOwner`
- Current branch: !`git branch --show-current`

## Core Responsibilities

- Parse all frontmatter fields (issue, type, label, blocked-by, responsibility, model)
- Handle variable file naming formats (numbers, hyphens, spacing)
- Create issues with GitHub issue type and metadata section in body
- Match milestone by title (not number) and require exact match before proceeding
- Match project by title (not config number) and require exact match before proceeding
- Link dependencies using blocked-by references
- Add to Projects V2 board in configured status
- Report clear success/failure for each issue

## Workflow

1. **Validate inputs** - Check prerequisites

   - Verify PROJECT_NUMBER and MILESTONE_NUMBER provided
   - Check yq installed: `which yq || which ~/.local/bin/yq`
   - Check gh CLI authenticated: `gh auth status`
   - Check gh CLI has project scope: `gh auth status | grep -q 'project'` (if missing, run: `gh auth refresh -h github.com -s project`)
   - If validation fails, report error and exit

2. **Load configuration** - Get milestone and project details

   - Read ROADMAP_INDEX using yq
   - Find project by PROJECT_NUMBER pattern: `yq ".projects[] | select(.id == \"${PROJECT_NUMBER}*\") | .name"`
   - Find milestone by MILESTONE_NUMBER pattern: `yq ".projects[] | select(.id == \"${PROJECT_NUMBER}*\") | .milestones[] | select(.id == \"${MILESTONE_NUMBER}*\") | .name"`
   - Load Projects V2 config from github section (project_owner, status_field, backlog_value)
   - Look up actual GitHub project by title instead of using config project_number:
     - List organization projects: use MCP tool `mcp__github__list_projects` with owner_type=org and owner from config
     - Match project title from roadmap to GitHub project title
     - Store matched project number for later use
     - If project not found, ask user for approval to create new project (out of scope - require manual creation)
   - If milestone or project not found in config, exit with error

3. **Find issues directory** - Locate issue files

   - Search for directory matching pattern: `${ROADMAP_BASE}/*${PROJECT_NUMBER}*/*${MILESTONE_NUMBER}*/issues`
   - Use Glob or Bash find command
   - If ISSUE_NUMBER provided, find single file: `${ISSUES_DIR}/${ISSUE_NUMBER}-*.md`
   - Otherwise, find all .md files in directory
   - Sort files by numeric prefix for consistent processing
   - If no files found, exit with message

4. **Get GitHub milestone** - Resolve milestone by title

   - List milestones: `gh api repos/{owner}/{repo}/milestones`
   - Search for exact title match with milestone name from config
   - Store milestone title (not number) for issue creation
   - If not found, ask user for approval to create new milestone (out of scope - require manual creation first)
   - CRITICAL: Always use milestone title when creating issues with `gh issue create --milestone "{title}"`, not milestone number

5. **Verify Projects V2** - Check board configuration (if configured)

   - Use the matched project number from step 2 (not from config):
     - Verify project exists: `gh project view {matched_number} --owner {owner} --format json`
     - Get Status field ID: use MCP tool `mcp__github__list_project_fields`
     - Find field matching status_field name (default "Status")
     - Get Backlog option ID from field options matching backlog_value (default "Backlog")
     - If project or field not found, report warning and continue without Projects V2

6. **Build file mapping** - Map issue files to track creation order

   - List all issue files in sorted order (by numeric prefix)
   - Create mapping: file_number (from filename like "5-implement-media.md") → position in sorted list
   - Example: file "1-init.md"=position 0, "2-setup.md"=position 1, "5-media.md"=position 2
   - Initialize mapping table to track: file_number → github_issue_number (populated during creation)

7. **Create issues** - Process each issue file in sorted order

   For each file in sorted order:

   - Extract file number from filename (e.g., "5" from "5-implement-media.md")
   - Read file using Read tool
   - Extract frontmatter between `---` delimiters using sed/yq
   - Parse fields: issue (title), type, label, blocked-by, responsibility, model
   - Extract body content (everything after second `---`)
   - Build full issue body from entire markdown file including frontmatter
   - CRITICAL: Use MCP tool `mcp__github__issue_write` with method='create':
     - Parameters: owner, repo, title, body (full_body), type, labels (array), milestone (numeric ID from step 4)
     - Type parameter sets GitHub issue type (Human/Agent)
     - Milestone must be numeric ID, not title string
   - Capture created GitHub issue number from response
   - **Store in mapping table: file_number → github_issue_number**
   - Echo: "✓ Created #{github_issue_number}: {title} (from file {file_number})"
   - On error, log error and continue with next file

8. **Link dependencies** - Map file numbers to GitHub issue numbers

   Issue files use file numbers (e.g., `blocked-by: [4]` = file 4-*.md). Must convert to GitHub issue numbers using mapping table from step 7 and update BOTH frontmatter and Dependencies section.

   For each created issue with blocked-by field:

   - Parse blocked-by array from frontmatter (contains file numbers)
   - Map each file number to GitHub issue number (example: file 2→#3, file 4→#5)
   - Read current issue body: `mcp__github__issue_read` with method='get'
   - Replace frontmatter `blocked-by: [2, 4]` with `blocked-by: [3, 5]`
   - Insert "**Dependencies:** Blocked by #3, #5" after Context heading
   - Update issue body: `gh issue edit {github_issue_number} --body "{updated_body}"`
   - Echo: "✓ Linked dependencies for #{github_issue_number} (file {file_numbers} → issues {github_numbers})"
   - If file number not in mapping table, log warning and skip

9. **Add to Projects V2** - Add issues to kanban board (if configured)

   If Projects V2 configured:

   For each created issue:

   - Add to project: `gh project item-add {project_number} --owner {owner} --url {issue_url} --format json`
   - Extract item ID and project ID from JSON response
   - Set status to Backlog: `gh project item-edit --id {item_id} --project-id {project_id} --field-id {status_field_id} --single-select-option-id {backlog_option_id}`
   - Echo: "✓ Added #{issue_number} to project board in Backlog status"
   - On error, log warning and continue

10. **Generate report** - Summarize results

   Present completion status with statistics

## Report

✅ GitHub Issues Created

**Repository:** {owner}/{repo}
**Milestone:** {milestone_name} (#{milestone_id})
**Project Board:** {project_number} (or "Not configured")

**Summary:**

- Created: {count}
- Dependencies Linked: {count}
- Added to Projects V2: {count} (or "Skipped - not configured")

**Issues:**

1. #{42}: Initialize monorepo structure
2. #{43}: Create Supabase project
3. #{44}: Install Payload CMS

**Errors:** {count} (or "None")

- {error details if any}

**Next Steps:**

- Review issues in GitHub milestone
- Update project board status as work progresses
- Manually adjust if Projects V2 was not configured
