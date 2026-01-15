---
description: Create changelog, stage, and commit.
argument-hint: [commit message or description of changes] [--amend]
model: claude-sonnet-4-5
allowed-tools: Read, Write, Edit, Task, Bash, Glob, Grep
---

# Git Commit

Create a changelog entry based on the changes made since the last git commit, execute git staging, and commit to remote.

## Variables

COMMIT_DESCRIPTION: $ARGUMENTS
CHANGELOG_BASE: .meatware/changelog
TIMESTAMP: `date +%y%m%d%H%M`
DATE: `date +%Y-%m-%d`
MONTH_FOLDER: `date +%y%m`-changelog
CURRENT_BRANCH: !`git branch --show-current`
GIT_COMMENT: [generated in Step 2]
CHANGELOG_CONTENT: [generated in Step 3, written in Step 5]

## Core Responsibilities

- Follow Conventional Commits v1.0.0 specification strictly
- Align changelog sections with Keep a Changelog standard (Added, Changed, Deprecated, Removed, Fixed, Security)
- Acknowledge technical debt for `feat`, `fix`, `refactor`, and `perf` commits
- Never commit without explicit human approval
- Never create files before approval — generate content in memory first
- Preserve existing CHANGELOG.md formatting and link structure
- Use ISO 8601 date format (YYYY-MM-DD) per Keep a Changelog recommendation
- Describe changes without including code blocks in changelog entries
- Skip changelog generation for merge commits (commits with multiple parents)

### Commit Type Reference (Conventional Commits + Angular Convention)

Per [Conventional Commits v1.0.0](https://www.conventionalcommits.org/en/v1.0.0/), only `feat` and `fix` have semantic versioning implications. Extended types from [@commitlint/config-conventional](https://github.com/conventional-changelog/commitlint/tree/master/%40commitlint/config-conventional) (based on Angular convention):

| Type       | Description                                | SemVer Impact |
| ---------- | ------------------------------------------ | ------------- |
| `feat`     | New feature or capability                  | MINOR         |
| `fix`      | Bug fix                                    | PATCH         |
| `docs`     | Documentation only changes                 | None          |
| `style`    | Formatting, whitespace (no logic changes)  | None          |
| `refactor` | Code restructuring without behavior change | None          |
| `perf`     | Performance improvements                   | None          |
| `test`     | Adding or correcting tests                 | None          |
| `build`    | Build system or external dependencies      | None          |
| `ci`       | CI configuration files and scripts         | None          |
| `revert`   | Reverts a previous commit                  | None          |

**Revert commits:** Use format `revert: <header of reverted commit>` with body containing `This reverts commit <hash>.`

**Breaking changes:** Append an exclamation mark after the type (feat!, fix!) — triggers MAJOR version per SemVer. Alternatively, include BREAKING CHANGE: in the commit footer followed by a description.

**Deprecation note:** Per SemVer 2.0.0, marking functionality as deprecated also triggers a MINOR version increment.

**Initial development (0.y.z):** For version 0.y.z, the API is not considered stable. Breaking changes may occur without a MAJOR version increment.

**Version tags:** The `v` prefix (e.g., `v1.2.3`) is conventional for git tags but is not part of the semantic version number itself.

### Commit Message Style Rules

Per Angular and Conventional Commits conventions:

- **Imperative mood:** Use "add" not "added" or "adds"
- **Not capitalized:** First letter of description is lowercase
- **No period:** Do not end description with a period
- **Length limit:** Keep header under 72 characters (max 100)
- **Present tense:** "fix bug" not "fixed bug"

The description should complete the sentence: "If applied, this commit will..."

### Dynamic Changelog Template by Commit Type

Include only relevant sections based on commit type:

| Commit Type | Required Sections                                |
| ----------- | ------------------------------------------------ |
| `feat`      | Added, Technical Debt                            |
| `fix`       | Fixed, Technical Debt (if significant)           |
| `docs`      | Changed                                          |
| `style`     | Changed                                          |
| `refactor`  | Changed, Technical Debt                          |
| `perf`      | Changed, Technical Debt                          |
| `test`      | Changed                                          |
| `build`     | Changed                                          |
| `ci`        | Changed                                          |
| `revert`    | Removed or Changed, reference to original commit |

**Do not include sections that would be empty.** Omit "None" placeholders entirely.

**Yanked releases:** For versions pulled due to serious bugs or security issues, use format: `## [0.0.5] - 2014-12-13 [YANKED]`

## Workflow

### Step 1: Validate & Analyze

**Check for `--amend` flag:**

If COMMIT_DESCRIPTION contains `--amend`:

- Skip changelog creation entirely
- Proceed directly to amend workflow (Step 5 with `git commit --amend`)

**Check for merge commit:**

```bash
git rev-parse HEAD^2 > /dev/null 2>&1
```

If this succeeds, HEAD is a merge commit — skip changelog generation.

**Verify repository state:**

```bash
git rev-parse --git-dir > /dev/null 2>&1
```

- If not a git repository: Report "Not a git repository" and exit

**Check current repository state:**

```bash
git status --porcelain
```

**If no changes:**

- Report: "No changes to commit"
- Exit workflow

**Parse output to identify:**

- Modified files and their types
- New files added
- Files deleted
- Untracked files

**Store file list for approval preview.**

### Step 2: Generate Commit Message

**Analyze changes to determine:**

1. **Primary type** — Select from Commit Type Reference in Core Responsibilities
2. **Scope** — Main component affected (e.g., `auth`, `api`, `readme`)
3. **Breaking change** — Add exclamation mark if change breaks backward compatibility

**Breaking change indicators:**

- API changes that remove functionality
- Database schema changes requiring migration
- Configuration format changes

**Create conventional commit message:**

Format: `<type>(<scope>): <description>` - Keep description under 72 characters

**Process user-provided description:**

- If COMMIT_DESCRIPTION is provided (and not `--amend`): Use it to influence focus area
- If COMMIT_DESCRIPTION is empty: Generate message from file changes automatically
- Ensure message follows conventional format

**Store as GIT_COMMENT variable**

### Step 3: Preview Changelog

**Generate changelog filename:**

```
$TIMESTAMP-[description].md
```

**Extract 2-5 words from commit:**

- Take key nouns and verbs from commit message
- Format with hyphens
- Prioritize clarity over word count
- Examples: `add-oauth-github-auth`, `fix-typo-readme`, `refactor-api-endpoints`

**Generate changelog content in memory:**

Based on commit type, include only relevant sections per Dynamic Changelog Template.

**Frontmatter:**

```markdown
---
file-name: $TIMESTAMP-[description].md
version: [determine from context or mark as unreleased]
date: $DATE
type: [major|minor|patch based on changes]
breaking: [true|false]
git-comment: $GIT_COMMENT
---
```

**Content sections:**

Include only sections specified in the Dynamic Changelog Template table. Each section contains a brief description of the changes. Do not include empty sections.

**Generate CHANGELOG.md index entry:**

```markdown
- [$TIMESTAMP-description](./$MONTH_FOLDER/$TIMESTAMP-description.md)
  - **Git Comment**: $GIT_COMMENT
  - **[Primary Section]**: [8-12 word summary]
```

Add `[BREAKING]` tag if applicable. Include Technical Debt line only if that section exists.

### Step 4: Request Approval

**Present summary to user:**

```
Commit Preview

**Commit Message:**
$GIT_COMMENT

**Files to be staged and committed:**
[Complete list from git status --porcelain, showing status codes]
  M  src/file1.ts
  A  src/file2.ts
  D  old-file.ts
  ?? untracked-file.ts

**Changelog will be created at:**
$CHANGELOG_BASE/$MONTH_FOLDER/$TIMESTAMP-[description].md

**Changelog Preview:**
[Show generated CHANGELOG_CONTENT]

**CHANGELOG.md index entry:**
[Show generated index entry]

**Please review and respond with one of the following:**

- Type **Approve** to proceed with the commit
- Type **Revise** followed by instructions to modify the commit message or changelog
- Type **Cancel** to abort (no files have been created)
```

**Wait for user response:**

- **Approve**: Proceed to Step 5
- **Revise [instructions]**:
  - Parse the instructions after "Revise"
  - Update the commit message and/or changelog content as directed
  - Re-display the preview
  - Wait for new response
- **Cancel**:
  - Report: "Commit cancelled. No files were created."
  - Exit workflow

### Step 5: Create Files, Stage & Commit

**Create month folder if needed:**

```bash
mkdir -p $CHANGELOG_BASE/$MONTH_FOLDER
```

**Write changelog file:**

Write CHANGELOG_CONTENT to `$CHANGELOG_BASE/$MONTH_FOLDER/$TIMESTAMP-[description].md`

**Update CHANGELOG.md index:**

Read `.meatware/changelog/CHANGELOG.md`, add new entry under [Unreleased] or current version, maintaining:

- Reverse chronological order
- Correct month folder paths
- Consistent formatting

**Stage all changes including new changelog:**

```bash
git add -A
```

**Show staged files for confirmation:**

```bash
git diff --staged --stat
```

**Commit:**

For normal commit:

```bash
git commit -m "$GIT_COMMENT"
COMMIT_HASH=$(git rev-parse --short=8 HEAD)
```

For `--amend` (no changelog created):

```bash
git commit --amend -m "$GIT_COMMENT"
COMMIT_HASH=$(git rev-parse --short=8 HEAD)
```

### Step 6: Push to Remote

**Push changes:**

```bash
git push origin $CURRENT_BRANCH
```

**Handle results:**

- **Success:** Confirm remote updated, workflow complete
- **Authentication failure:** Expected in non-interactive environments
  - Commit is safe locally
  - Provide manual command: `git push origin $CURRENT_BRANCH`

**Do not treat authentication failure as an error — the commit succeeded.**

## Report to Output Window

**If push succeeds:**

```
Git Commit and Push Successful

**Commit Details:**
- Message: $GIT_COMMENT
- Hash: $COMMIT_HASH
- Type: [commit type]
- Breaking: [true|false]

**Changelog Created:**
- File: $MONTH_FOLDER/$TIMESTAMP-[description].md

**Push Status:**
- Pushed to remote: Yes
- Branch: $CURRENT_BRANCH
```

**If push requires manual authentication:**

```
Git Commit Successful - Manual Push Required

**Commit Details:**
- Message: $GIT_COMMENT
- Hash: $COMMIT_HASH
- Type: [commit type]
- Breaking: [true|false]

**Changelog Created:**
- File: $MONTH_FOLDER/$TIMESTAMP-[description].md

**Push Status:**
- Requires manual push (authentication needed)
- Branch: $CURRENT_BRANCH
- **Action required:** Run `git push origin $CURRENT_BRANCH` in your terminal

**Note:** Your commit is safe locally. Push when ready - no data loss risk.
```

**If `--amend` was used:**

```
Git Commit Amended Successfully

**Commit Details:**
- Message: $GIT_COMMENT
- Hash: $COMMIT_HASH
- Type: [commit type]

**Changelog:** Skipped (amend mode)

**Push Status:**
- [Success or manual push required message]
- Branch: $CURRENT_BRANCH
- **Note:** You may need `git push --force` if the amended commit was already pushed
```

## References

- [Conventional Commits v1.0.0](https://www.conventionalcommits.org/en/v1.0.0/) — Commit message specification
- [Keep a Changelog v1.1.0](https://keepachangelog.com/en/1.1.0/) — Changelog format standard
- [Semantic Versioning v2.0.0](https://semver.org/spec/v2.0.0.html) — Version numbering specification
- [@commitlint/config-conventional](https://github.com/conventional-changelog/commitlint/tree/master/%40commitlint/config-conventional) — Extended commit types
- [Angular Commit Message Guidelines](https://github.com/angular/angular/blob/main/contributing-docs/commit-message-guidelines.md) — Source convention for extended types
