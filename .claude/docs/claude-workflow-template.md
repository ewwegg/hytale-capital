# Template Structure Guide

## Universal Structure

Every command and agent MUST follow this exact sequence:

**Metadata → Title → Purpose → Variables → Core Responsibilities → Workflow → Report**

```markdown
---
frontmatter
---

# Title

[Clear action name for the command/agent]

Purpose statement
[Direct 1-2 sentence description of what this accomplishes]

## Variables

[Define dynamic and static variables used throughout]

## Core Responsibilities

[Domain-specific requirements and expectations]

## Workflow

[Sequential numbered steps to complete the task]

## Report

[Structured output format for completion status]
```

---

## Section Specifications

[Detailed requirements for each section of the template]

### Metadata (Frontmatter)

[Configuration and tool settings for commands and agents]

#### Commands Frontmatter

```yaml
---
description: What triggers this command
argument-hint: [expected] [arguments]
model: claude-sonnet-4-5  # or claude-opus-4-1, claude-haiku-4-5
allowed-tools: Read, Write, Bash, Task
---
```

Optional fields:

```yaml
disable-model-invocation: true # Prevent SlashCommand tool usage
```

#### Agents Frontmatter

```yaml
---
name: kebab-case-identifier
description: When to use this agent (include "Use PROACTIVELY" for auto-trigger)
model: claude-sonnet-4-5 # or claude-opus-4-1, claude-haiku-4-5
allowed-tools: Read, Write # Minimum required tools only
---
```

### Title

- **Format**: Single H1 header, action-oriented
- **Pattern**: Verb or Verb-Noun format
- **Examples**:
  - Commands: `# Deploy Application`, `# Fix Issue`, `# Generate Report`
  - Agents: `# Security Reviewer`, `# Test Generator`, `# Data Processor`

### Purpose

- **Length**: 1-2 sentences maximum
- **Voice**: Direct, speaking TO the AI
- **Pattern**: "Execute/Follow/Create [what] to/based on [input] then [output]"

#### Purpose Examples

```markdown
# Commands

Execute the deployment workflow to push changes to production then report status.

Create comprehensive test suite for the module specified in $ARGUMENTS.

# Agents

Process security scan results from TARGET_DIR then generate vulnerability report.

Analyze code complexity in provided files then suggest optimizations.
```

### Variables

[Define dynamic and static variables used throughout the workflow]

#### Commands - Dynamic and Static Variables

```markdown
## Variables

# Using arguments in variables

COMMIT_MESSAGE: $ARGUMENTS
FILE_PATH: src/components/$1
PRIORITY_LEVEL: $2

# Static configuration values (use backticks)

OUTPUT_DIR: `./reports`
MAX_RETRIES: 3
DEFAULT_ENV: `development`
```

#### Agents - Received and Static Variables

```markdown
## Variables

# Provided by orchestrator

TARGET_FILE: [path to analyze]
ANALYSIS_TYPE: [security|performance|quality]
OUTPUT_FORMAT: [json|markdown]

# Static configuration

THRESHOLD: 80
TIMEOUT_SECONDS: 300
BATCH_SIZE: 10
```

### Core Responsibilities

[Domain-specific requirements and expectations that guide the workflow]

**Purpose**: Contextual requirements that support the workflow

#### Content Guidelines

- Domain-specific expectations
- Quality criteria and standards
- Important considerations
- Success metrics
- DO NOT repeat workflow steps

#### Format

```markdown
## Core Responsibilities

- Configuration source: `config.yml`
- Validate inputs against schema
- Include comprehensive error handling
- Generate detailed logs for debugging
- Prioritize security over performance
- Handle edge cases gracefully
```

### Workflow

**MOST IMPORTANT SECTION** - Clear, numbered, sequential steps

#### Required Patterns

##### Step 1: Always Validate

```markdown
## Workflow

1. **Validate prerequisites** - Ensure requirements met
   - Check required variables/arguments
   - Verify file accessibility
   - Confirm tool availability
   - Exit early if validation fails
```

##### Progress Feedback (Commands)

```markdown
2. **Process items** - Handle with feedback
   - Echo "Processing item 1 of N..."
   - Execute the operation
   - Echo "✅ Completed: [item-name]"
   - Track progress for reporting
```

##### Error Recovery

```markdown
3. **Execute with fallback** - Resilient execution
   - Try primary approach
   - On error, attempt alternative
   - If still failing:
     - Log error context
     - Attempt rollback if needed
     - Exit with clear message
```

### Report

**Format**: Structured output for humans

#### Status Indicators

- ✅ Success - Task completed successfully
- ⚠️ Warning - Completed with issues
- ❌ Error - Failed to complete

#### Command Report Template

```markdown
## Report

✅ Success
Task: [What was accomplished]
Output: [Location or result]
Duration: [Time taken if relevant]
Key Results:

- [Result 1]
- [Result 2]
- [Result 3]
  Next Steps: [If applicable]
```

#### Agent Report Template

```markdown
## Report

Status: ✅ Complete
Processed: [count] items
Issues Found: [count]
Output Location: [path if files created]

Details:

- [Key finding 1]
- [Key finding 2]

Recommendations: [If applicable]
```

---

## Complete Examples

[Full implementations demonstrating all template elements]

### Simple Command Example

```markdown
---
description: Format code files with specified extension
argument-hint: [file-extension]
model: claude-sonnet-4-5
allowed-tools: Bash, Read, Write
---

# Format Code

Format all code files with extension: $ARGUMENTS

## Variables

FILE_EXTENSION: $ARGUMENTS
DEFAULT_INDENT: `2`
BACKUP_DIR: `./backups`

## Core Responsibilities

- Use appropriate formatter for file type
- Create backups before modifying
- Skip files in .gitignore
- Preserve file permissions

## Workflow

1. **Validate input** - Check extension

   - Ensure FILE_EXTENSION provided
   - Verify formatter exists for extension
   - Create BACKUP_DIR if needed

2. **Find target files** - Locate files

   - Run: `find . -name "*.${FILE_EXTENSION}" -type f`
   - Exclude node_modules and .git
   - Count total files found

3. **Format each file** - Process files

   - Create backup in BACKUP_DIR
   - Apply appropriate formatter
   - Verify syntax remains valid
   - Track success count

4. **Clean up** - Finalize
   - Remove backups if no errors
   - Report statistics
   - List any failures

## Report

✅ Formatting Complete
Extension: .${FILE_EXTENSION}
Files Processed: [count]
Files Modified: [count]
Formatter Used: [formatter name]
Backups: BACKUP_DIR (preserved on error)
```

### Complex Command Example

```markdown
---
description: Deploy application to specified environment
argument-hint: [environment] [version]
model: claude-opus-4-1
allowed-tools: Bash, Task, Read, Write
---

# Deploy Application

Deploy version $2 to $1 environment with comprehensive validation.

## Variables

ENVIRONMENT: $1
VERSION: $2
DEPLOY_CONFIG: `./deploy/config.yml`
ROLLBACK_LIMIT: 3
DEFAULT_VERSION: `latest`

## Core Responsibilities

- Validate environment exists in config
- Run all pre-deployment checks
- Maintain zero-downtime deployment
- Enable automatic rollback on failure
- Generate deployment audit trail

## Workflow

1. **Validate deployment** - Pre-flight checks

   - Verify ENVIRONMENT is valid
   - Use VERSION or DEFAULT_VERSION
   - Check deployment permissions
   - Validate configuration files

2. **Run pre-deployment tests** - Ensure readiness

   - Execute smoke tests
   - Verify dependencies available
   - Check resource availability
   - Validate database migrations

3. **Deploy infrastructure agents** - Parallel setup

   - Database migrator: `.claude/agents/db-migrate.md`
     Variables: ENV=ENVIRONMENT, VERSION=VERSION
   - Cache warmer: `.claude/agents/cache-warm.md`
     Variables: ENV=ENVIRONMENT
   - Health checker: `.claude/agents/health-check.md`
     Variables: ENV=ENVIRONMENT, EXPECT=healthy

4. **Execute deployment** - Main deployment

   - Tag current version for rollback
   - Deploy VERSION to ENVIRONMENT
   - Run health checks
   - Monitor error rates

5. **Verify deployment** - Post-deployment validation

   - Run integration tests
   - Check application metrics
   - Verify all services responding
   - Monitor for 5 minutes

6. **Finalize or rollback** - Complete process
   - If all checks pass: mark successful
   - If issues detected: automatic rollback
   - Update deployment registry
   - Send notifications

## Report

✅ Deployment Successful
Environment: ENVIRONMENT
Version: VERSION (or DEFAULT_VERSION)
Deployment Time: [duration]
Health Status: All services operational

Services Updated:

- [Service 1]: Running
- [Service 2]: Running

Metrics:

- Error Rate: [percentage]
- Response Time: [ms]
- Availability: [percentage]

Next Steps:

- Monitor dashboard: [url]
- Rollback command: /rollback ENVIRONMENT
```

### Simple Agent Example

```markdown
---
name: code-linter
description: Run linting checks. Use PROACTIVELY after code changes.
tools: Read, Bash
model: claude-haiku-4-5
---

You are a code quality specialist focused on maintaining standards.

## Variables

# From orchestrator

TARGET_DIR: [directory to lint]
FILE_TYPES: [extensions to check]
AUTOFIX: [true|false]

# Static configuration

MAX_ISSUES: 100
SEVERITY_THRESHOLD: `warning`

## Core Responsibilities

- Apply team coding standards
- Focus on meaningful issues
- Skip generated files
- Provide actionable feedback

## Workflow

1. **Detect linters** - Find available tools

   - Check for eslint, pylint, etc.
   - Verify configuration files
   - Note linter versions

2. **Run linting** - Execute checks

   - Process files in TARGET_DIR
   - Filter by FILE_TYPES
   - Apply autofix if AUTOFIX=true
   - Stop if issues exceed MAX_ISSUES

3. **Analyze results** - Process output

   - Group by severity
   - Filter by SEVERITY_THRESHOLD
   - Identify patterns
   - Count issue types

4. **Generate report** - Structure findings
   - List critical issues first
   - Include file locations
   - Suggest fixes

## Report

Status: ⚠️ Issues Found
Directory: TARGET_DIR
File Types: FILE_TYPES

Critical Issues: 0
Warnings: 15
Info: 42

Top Issues:

1. Missing semicolons (12 occurrences)
2. Unused variables (8 occurrences)
3. Line length exceeded (5 occurrences)

Autofix Applied: AUTOFIX
Files Modified: [count if autofix=true]
```

### Complex Agent Example

```markdown
---
name: api-test-generator
description: Generate API tests from OpenAPI spec. Use when API endpoints added.
tools: Read, Write, Bash
model: claude-sonnet-4-5
---

You are an API testing expert specializing in comprehensive test generation.

## Variables

# From orchestrator

SPEC_FILE: [OpenAPI spec path]
TEST_FRAMEWORK: [jest|mocha|pytest]
API_BASE_URL: [API endpoint]
AUTH_TYPE: [none|bearer|oauth]
COVERAGE_TARGET: [percentage]

# Static configuration

DEFAULT_TIMEOUT: 5000
RETRY_COUNT: 3

## Core Responsibilities

- Generate tests for all endpoints
- Include positive and negative cases
- Test authentication scenarios
- Validate response schemas
- Create reusable test utilities
- Mock external dependencies

## Workflow

1. **Parse specification** - Analyze API structure

   - Read SPEC_FILE
   - Extract endpoints
   - Identify parameters
   - Note authentication requirements
   - Document response schemas

2. **Plan test strategy** - Design test cases

   - Group by resource type
   - Identify critical paths
   - Plan data scenarios
   - Design edge cases

3. **Generate test utilities** - Create helpers

   - API client wrapper
   - Authentication helpers with AUTH_TYPE
   - Data factories
   - Response validators
   - Set DEFAULT_TIMEOUT for requests

4. **Create endpoint tests** - Generate test files
   For each endpoint:

   - Happy path tests
   - Parameter validation
   - Error responses
   - Authentication cases
   - Use RETRY_COUNT for flaky tests

5. **Add integration tests** - Cross-endpoint tests

   - Workflow scenarios
   - Data consistency
   - Transaction tests
   - Cleanup verification

6. **Configure test runner** - Setup execution

   - Create test configuration
   - Set up API_BASE_URL
   - Configure AUTH_TYPE
   - Add coverage reporting

7. **Validate tests** - Ensure quality
   - Run generated tests
   - Check coverage against COVERAGE_TARGET
   - Verify no flaky tests
   - Confirm all pass

## Report

Status: ✅ API Tests Generated
Specification: SPEC_FILE
Framework: TEST_FRAMEWORK
Base URL: API_BASE_URL

Test Summary:

- Endpoints Covered: [count]
- Test Cases: [total count]
- Test Files: [count]
- Utilities Created: [count]

Coverage:

- Endpoint Coverage: 100%
- Parameter Coverage: [percentage]%
- Response Coverage: [percentage]%
- Error Coverage: [percentage]%

Test Organization:

- Unit Tests: [path]
- Integration Tests: [path]
- Test Utilities: [path]
- Configuration: [path]

Authentication: AUTH_TYPE configured
All Tests Passing: ✅
Coverage Target: [achieved]% / COVERAGE_TARGET%
```

---

## Structure Validation Checklist

[Key elements to verify when reviewing command or agent structure]

### Required Elements

- [ ] Frontmatter with all required fields
- [ ] Title as H1 header
- [ ] Purpose statement (1-2 sentences)
- [ ] Workflow section with numbered steps
- [ ] Report section with status format

### Optional Elements

- [ ] Variables section (if needed)
- [ ] Core Responsibilities section (if adds value)

### Format Rules

- [ ] Variables in UPPER_SNAKE_CASE
- [ ] Steps numbered and bolded
- [ ] Status indicators used (✅⚠️❌)
- [ ] Backticks for static values
- [ ] Brackets for dynamic values

### Content Quality

- [ ] Purpose speaks TO the AI
- [ ] Core Responsibilities don't repeat workflow
- [ ] Workflow step 1 validates
- [ ] Report provides clear summary
- [ ] Examples show real usage
