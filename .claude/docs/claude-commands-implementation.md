# Commands Implementation Guide

## Purpose

[Complete guide for creating Claude Code slash commands that extend capabilities through reusable, project-specific automation workflows]

## Quick Reference

[Essential models, file locations, and documentation links for command creation]

### Model Selection

- **claude-sonnet-4-5**: Default for standard engineering tasks
- **claude-opus-4-1**: Complex orchestration, multi-agent coordination
- **claude-haiku-4-5**: Simple, deterministic tasks, high-volume operations

### File Locations

- **Project Commands**: `.claude/commands/[name].md` (version-controlled, team-shared)
- **Personal Commands**: `~/.claude/commands/[name].md` (all projects, user-specific)
- **Naming Convention**: kebab-case (e.g., `create-plan.md`, `fix-issue.md`)

### Essential Documentation

- [Official Slash Commands Documentation](https://docs.claude.com/en/docs/claude-code/slash-commands)
- [Claude Code Best Practices](https://www.anthropic.com/engineering/claude-code-best-practices)

---

## Command Characteristics

[Key features and appropriate use cases for slash commands]

### When to Use Commands

- **Direct user trigger**: Invoked explicitly via `/command-name`
- **Sequential workflows**: Step-by-step processes with clear order
- **Orchestration tasks**: Coordinating multiple agents or tools
- **Argument processing**: Accepting user input via `$ARGUMENTS` or `$1`, `$2`, etc.
- **One-time operations**: Setup, initialization, configuration tasks

### Command Capabilities

- Accept arguments from user input
- Execute bash commands with `!` prefix
- Deploy parallel agents via Task tool
- Access all Claude Code tools
- Maintain conversation context throughout execution

---

## Command Structure

[Required frontmatter and optional configuration fields for commands]

### Required Frontmatter

```yaml
---
description: Clear trigger description for this command
argument-hint: [expected] [arguments]
model: claude-sonnet-4-5  # or claude-opus-4-1, claude-haiku-4-5
allowed-tools: Read, Write, Bash, Task
---
```

### Optional Frontmatter Fields

```yaml
disable-model-invocation: true # Prevent SlashCommand tool usage
```

---

## Argument Handling

[How to accept and use arguments passed from user invocation]

### How Arguments Work

When a command is invoked with arguments, placeholders are replaced with actual values and stored as variables:

- **`$ARGUMENTS`**: Full argument string passed to command
- **`$1`, `$2`, `$3`**: Individual positional arguments

### Usage in Content

```markdown
# Direct usage in command text:

Fix GitHub issue #$1 with priority $2
Deploy to $1 environment with config $2
Search the codebase for: $ARGUMENTS
```

### Using Arguments in Variables

Arguments can be assigned to variables for reuse throughout the command:

```markdown
## Variables

# Assign arguments to descriptive variables

ISSUE_NUMBER: $1
PRIORITY_LEVEL: $2
SEARCH_QUERY: $ARGUMENTS
FILE_PATH: src/components/$1
COMMIT_MESSAGE: "fix: $ARGUMENTS"

# Static configuration values

OUTPUT_DIR: `./reports`
MAX_RETRIES: 3
```

### Example Invocation

```bash
/fix-issue 123 high
# Result: $1 = "123", $2 = "high"
# ISSUE_NUMBER = "123", PRIORITY_LEVEL = "high"
# $ARGUMENTS = "123 high"
```

---

## Bash Command Execution

[How to execute shell commands and include their output in command context]

Execute bash commands and include output using `!` prefix:

```markdown
---
description: Create git commit with full context
allowed-tools: Bash(git status:*), Bash(git diff:*), Bash(git log:*)
---

# Commit Changes

## Current Context

- Status: !`git status --short`
- Staged changes: !`git diff --cached --stat`
- Recent commits: !`git log --oneline -5`
- Current branch: !`git branch --show-current`

Create a conventional commit message based on the above changes.
If $ARGUMENTS provided, use as commit message base.
```

---

## Deploying Agents

[How to launch and coordinate specialized agents from commands]

Commands can orchestrate multiple agents in parallel using the Task tool:

```markdown
## Step N: Deploy Specialist Agents

**Launch parallel agents via Task tool**

- Security reviewer: `.claude/agents/security-reviewer.md`
  Variables: FILE_LIST=$1, SEVERITY_LEVEL=high
- Performance analyzer: `.claude/agents/performance-analyzer.md`
  Variables: TARGET_DIR=$2, METRICS=detailed
- Test validator: `.claude/agents/test-validator.md`
  Variables: TEST_SUITE=all, COVERAGE_THRESHOLD=80

**Important**:

- Maximum 10 parallel agents
- All agents launch SIMULTANEOUSLY
- Wait for all completions before proceeding
- Aggregate results for final report
```

---

## Agent Orchestration Patterns

[How commands deploy and coordinate multiple agents for complex tasks]

### Parallel Processing

Deploy multiple agents simultaneously for independent tasks:

```markdown
## Step 3: Deploy parallel agents

**Launch all agents simultaneously via Task tool:**

- data-processor for FILE_A: `.claude/agents/data-processor.md`
  Variables: INPUT_FILE=file_a.csv, OUTPUT_FORMAT=json
- data-processor for FILE_B: `.claude/agents/data-processor.md`
  Variables: INPUT_FILE=file_b.csv, OUTPUT_FORMAT=json
- data-processor for FILE_C: `.claude/agents/data-processor.md`
  Variables: INPUT_FILE=file_c.csv, OUTPUT_FORMAT=json

**Wait for all completions**
**Aggregate results into unified report**
```

### Sequential Pipeline

Pass results between agents in sequence:

```markdown
## Workflow

1. **Deploy analyzer** - Initial analysis

   - Launch: `.claude/agents/code-analyzer.md`
   - Variables: TARGET_DIR=./src
   - Returns: FILE_LIST with issues

2. **Deploy optimizer** - Process analysis results

   - Launch: `.claude/agents/optimizer.md`
   - Variables: FILES=FILE_LIST from step 1
   - Returns: Optimized code

3. **Deploy test-generator** - Create tests
   - Launch: `.claude/agents/test-generator.md`
   - Variables: OPTIMIZED_FILES from step 2
   - Returns: Test suite
```

### Conditional Deployment

Deploy agents based on runtime conditions:

```markdown
## Step 2: Deploy appropriate agents

**Analyze changes and deploy specialists:**

- If .py files changed:
  - Deploy python-linter: `.claude/agents/python-linter.md`
  - Deploy pytest-runner: `.claude/agents/pytest-runner.md`
- If .js files changed:
  - Deploy eslint-checker: `.claude/agents/eslint-checker.md`
  - Deploy jest-runner: `.claude/agents/jest-runner.md`
- If .go files changed:
  - Deploy go-formatter: `.claude/agents/go-formatter.md`
  - Deploy go-test: `.claude/agents/go-test.md`
```

### Agent Result Aggregation

Combine outputs from multiple agents:

```markdown
## Step 4: Aggregate agent results

**Collect all agent reports:**

- Security findings from security-reviewer
- Performance metrics from performance-analyzer
- Test results from test-runner

**Process combined results:**

- Remove duplicate findings
- Prioritize by severity
- Generate unified report
- Calculate overall status
```

---

## Complete Examples

[Full implementations demonstrating commands with proper structure]

### Simple Utility Command

```markdown
---
description: Run project tests with optional pattern
argument-hint: [test-pattern]
model: claude-sonnet-4-5
allowed-tools: Bash, Read
---

# Run Tests

Execute test suite with pattern: $ARGUMENTS

## Variables

TEST_PATTERN: $ARGUMENTS
DEFAULT_TIMEOUT: 30

## Core Responsibilities

- Detect test framework automatically
- Run all tests if no pattern provided
- Provide clear output formatting
- Exit early on critical failures

## Workflow

1. **Detect framework** - Identify test runner
   - Check package.json for jest/vitest
   - Check requirements.txt for pytest
   - Check go.mod for go test
2. **Execute tests**
   - With pattern: `npm test -- ${TEST_PATTERN}`
   - Without pattern: `npm test`
   - Apply DEFAULT_TIMEOUT
3. **Handle failures** - Process results
   - If tests fail, show error summary
   - Identify failing test files
   - Suggest next steps

## Report

✅ Test Results
Pattern: TEST_PATTERN (or "all tests")
Tests Run: [count]
Passed: [count]
Failed: [count]
Duration: [time]
```

### Orchestrator Command

```markdown
---
description: Comprehensive PR review using specialized agents
argument-hint: [pr-number] [review-depth]
model: claude-opus-4-1
allowed-tools: Task, Bash(gh pr:*), Read, Grep
---

# Review Pull Request

Perform comprehensive review of PR #$1 with depth: $2

## Variables

PR_NUMBER: $1
REVIEW_DEPTH: $2
DEFAULT_DEPTH: `standard`

## Core Responsibilities

- Use REVIEW_DEPTH if provided, otherwise use DEFAULT_DEPTH
- Deploy agents based on review depth
- Aggregate all findings into unified report
- Prioritize critical issues

## Workflow

1. **Fetch PR details** - Get changes from PR
   - Run: `gh pr view ${PR_NUMBER} --json files,additions,deletions`
   - Run: `gh pr diff ${PR_NUMBER} --name-only`
   - Store file list for agents
2. **Deploy review agents** - Launch specialists
   - Security reviewer: `.claude/agents/security-reviewer.md`
     Variables: PR_NUMBER=PR_NUMBER, FILES=from_step_1
   - Code quality: `.claude/agents/code-quality.md`
     Variables: PR_NUMBER=PR_NUMBER, STANDARD=team-guidelines
   - If REVIEW_DEPTH is "thorough", also deploy:
     - Performance analyzer: `.claude/agents/performance.md`
     - Test coverage: `.claude/agents/test-coverage.md`
3. **Compile results** - Aggregate findings

   - Collect all agent reports
   - Prioritize by severity
   - Remove duplicates
   - Generate actionable feedback

4. **Post review** - Share results
   - Format as GitHub comment
   - Include specific line references
   - Provide approval recommendation

## Report

✅ PR #${PR_NUMBER} Review Complete
Review Depth: REVIEW_DEPTH (or DEFAULT_DEPTH)
Critical Issues: [count]
Suggestions: [count]
Security Concerns: [list]
Recommendation: [Approve/Request Changes/Comment]
```

### Complex Workflow Command

```markdown
---
description: Create and configure new microservice from template
argument-hint: [service-name] [template-type]
model: claude-sonnet-4-5
allowed-tools: Bash, Read, Write, Edit, Task
---

# Create Microservice

Create new microservice: $1 using template: $2

## Variables

SERVICE_NAME: $1
TEMPLATE_TYPE: $2
TEMPLATES_DIR: `./templates`
SERVICES_DIR: `./services`
DEFAULT_TEMPLATE: `rest-api`

## Context

- Available templates: !`ls -1 ./templates`
- Existing services: !`ls -1 ./services`

## Core Responsibilities

- Validate service name follows naming convention
- Use DEFAULT_TEMPLATE if TEMPLATE_TYPE not provided
- Set up complete service structure
- Initialize git repository
- Create initial documentation

## Workflow

1. **Validate inputs**

   - Check SERVICE_NAME follows kebab-case
   - Verify SERVICE_NAME not already exists
   - Confirm TEMPLATE_TYPE exists (or use DEFAULT_TEMPLATE)

2. **Copy template** - Create service structure

   - Copy from TEMPLATES_DIR/TEMPLATE_TYPE to SERVICES_DIR/SERVICE_NAME
   - Update all placeholder values
   - Rename files as needed

3. **Configure service** - Customize for new service

   - Update package.json/go.mod with SERVICE_NAME
   - Configure environment variables
   - Set up database connections
   - Update Docker configuration

4. **Deploy setup agents** - Parallel configuration

   - CI/CD setup: `.claude/agents/setup-pipeline.md`
     Variables: SERVICE=SERVICE_NAME, TEMPLATE=TEMPLATE_TYPE
   - Documentation: `.claude/agents/create-docs.md`
     Variables: SERVICE=SERVICE_NAME, TYPE=microservice
   - Testing: `.claude/agents/generate-tests.md`
     Variables: SERVICE=SERVICE_NAME, COVERAGE=80

5. **Initialize repository**

   - Run: `cd ${SERVICES_DIR}/${SERVICE_NAME} && git init`
   - Create initial commit
   - Set up git hooks
   - Configure .gitignore

6. **Verify setup** - Ensure everything works
   - Run: `cd ${SERVICES_DIR}/${SERVICE_NAME} && npm install`
   - Run: `npm test`
   - Run: `npm run lint`

## Report

✅ Microservice Created Successfully
Name: SERVICE_NAME
Template: TEMPLATE_TYPE (or DEFAULT_TEMPLATE)
Location: SERVICES_DIR/SERVICE_NAME
Next Steps:

1. Review generated documentation
2. Configure production secrets
3. Deploy to development environment
4. Set up monitoring

Git Repository: Initialized
CI/CD Pipeline: Configured
Documentation: Generated
Tests: Created with 80% coverage target
```

---

## Best Practices

[Design principles and optimization strategies for effective commands]

### Command Design

1. **Always validate in Step 1** - Check prerequisites before proceeding
2. **Provide clear argument hints** - Help users understand expected format
3. **Use descriptive names** - Command name should indicate action
4. **Include progress feedback** - Echo status during long operations
5. **Handle missing arguments** - Provide defaults or request input

### Performance Optimization

- Use claude-haiku-4-5 for simple, repetitive tasks
- Deploy agents in parallel when tasks are independent
- Clear context with `/clear` between unrelated command sequences
- Limit file reads to necessary content only

### Error Handling

```markdown
## Step N: Execute with Recovery

- Try primary approach
- If error occurs:
  - Log detailed context
  - Attempt alternative method
  - If still failing:
    - Report specific error
    - Suggest manual intervention
    - Exit gracefully
```

### Security Considerations

- Never hardcode sensitive values
- Validate all user inputs
- Use minimum required tool permissions
- Be explicit about allowed bash commands

---

## Troubleshooting

[Common issues and solutions when creating or running commands]

### Command Not Found

- Verify file exists: `ls -la .claude/commands/`
- Check file extension is `.md`
- Ensure frontmatter has `description` field

### Arguments Not Working

- Confirm using `$ARGUMENTS` or `$1` directly in content
- Don't define arguments in Variables section
- Test with simple echo first

### Task Tool Issues

- Maximum 10 parallel agents
- Verify agent files exist before deploying
- Pass all required variables to agents
- Check agent file paths are correct

### Performance Problems

- Consider using claude-haiku-4-5 for simple tasks
- Break complex workflows into smaller commands
- Use parallel agents for independent work
- Monitor token usage in long workflows

---

## Command Patterns

[Common command types and their typical implementations]

### Git Workflow Commands

```markdown
/commit - Smart commit with generated message
/pr - Create PR with description
/merge - Merge with checks and cleanup
/release - Full release workflow
```

### Testing Commands

```markdown
/test - Run tests with pattern
/coverage - Generate coverage report
/e2e - Run end-to-end tests
/fix-tests - Auto-fix failing tests
```

### Deployment Commands

```markdown
/deploy - Deploy to environment
/rollback - Revert last deployment
/status - Check deployment status
/scale - Adjust service scaling
```

### Utility Commands

```markdown
/format - Format code files
/lint - Run linters and fix
/clean - Clean build artifacts
/setup - Initial project setup
```

---

## Advanced Features

[Sophisticated patterns for complex command requirements]

### Conditional Logic

```markdown
- If $1 equals "production", require confirmation
- If tests fail, skip deployment
- If file exists, create backup first
```

### Dynamic Agent Deployment

```markdown
## Step 3: Deploy Agents Based on Changes

- If JavaScript files changed:
  - Deploy eslint-checker agent
  - Deploy jest-runner agent
- If Python files changed:
  - Deploy pylint-checker agent
  - Deploy pytest-runner agent
```

### Command Chaining

While commands can't directly call other commands, you can:

1. Structure workflows to be modular
2. Use agents for reusable components
3. Document command sequences for users

### Integration with MCP

Commands can interact with MCP servers if configured:

```markdown
- Access database via MCP
- Call external APIs through MCP tools
- Integrate with third-party services
```
