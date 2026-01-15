# Writing Patterns Guide

## Prompt Writing Guidelines

[Core principles and techniques for writing effective prompts]

### Core Principles

1. **Direct Address**: Write TO the AI, not about it
2. **Information Density**: Every word should add value
3. **Natural Language**: AI understands context
4. **Consistent References**: Use exact variable names throughout
5. **Action-Oriented**: Focus on what to do, not what to avoid

### Voice and Tone

[How to communicate effectively with AI agents]

#### Command Prompts

- Imperative mood: "Execute", "Create", "Deploy"
- Clear directives: "Validate inputs then proceed"
- Active voice: "The agent processes files"

#### Agent Prompts

- Identity first: "You are a [specialist role]"
- Expertise focus: Domain-specific knowledge
- Clear boundaries: What the agent handles

### Effective Language Patterns

#### Emphasis Techniques

- **MUST, CRITICAL, IMPORTANT** - For absolute requirements
- **ALWAYS, NEVER** - For invariant rules
- **IMMEDIATELY** - For time-sensitive actions
- **PROACTIVELY** - For auto-triggering behavior

#### Clarity Examples

```markdown
# ✅ Good: Clear and direct

Process each file in INPUT_DIR sequentially, validating format before transformation.

# ❌ Bad: Vague and wordy

You should go through the files that are in the input directory and for each one check if it's valid before you do any processing on it.
```

### Variable Usage in Prompts

#### In Commands

```markdown
# ✅ Correct: Direct usage

Fix issue #$1 with priority $2
Deploy version $ARGUMENTS to production

# ❌ Incorrect: Over-complicated

Fix issue number stored in first argument parameter
```

#### In Agents

```markdown
# ✅ Correct: Clear references

Analyze files in TARGET_DIR for ANALYSIS_TYPE issues.
Generate OUTPUT_FORMAT report with findings.

# ❌ Incorrect: Ambiguous

Analyze the files provided for the type of analysis requested.
```

### Scope Definition

#### Commands

- Describe complete workflow
- Include orchestration logic
- Define success criteria
- Specify user feedback points

#### Agents

- Focus on single responsibility
- Specify expertise domain
- Define input/output contracts
- Clarify boundaries

---

## Pattern Library

[Reusable workflow patterns and control structures]

### Control Flow Patterns

[Conditional logic, loops, and execution flow patterns]

#### Conditionals

```markdown
# Simple conditions

- If $ARGUMENTS not provided, request input
- If file exists, create backup first
- If tests fail, skip deployment

# Complex conditions

- If $1 equals "production" AND $2 not specified:
  - Use DEFAULT_VERSION
  - Require confirmation
  - Enable extra logging
```

#### Loops

```markdown
# File processing loop

<process-files>
  - Read next file from INPUT_DIR
  - Validate format matches expected
  - Transform according to rules
  - Save to OUTPUT_DIR
  - Update progress counter
  - Continue until all processed
</process-files>

# Retry loop

<retry-with-backoff>
  - Attempt operation
  - If failed and retries < MAX_RETRIES:
    - Wait exponentially longer
    - Increment retry counter
    - Repeat attempt
</retry-with-backoff>
```

#### Parallel Execution

```markdown
# Deploy multiple agents

**Launch simultaneously via Task tool:**

- Agent A with variables X, Y
- Agent B with variables Y, Z
- Agent C with variables X, Z
  **Wait for all completions**
  **Aggregate all results**
```

### Common Workflow Patterns

#### Validate-Process-Report

```markdown
## Workflow

1. **Validate inputs** - Ensure prerequisites
   - Required: VAR1, VAR2
   - Optional: VAR3 with default
   - Exit if invalid
2. **Process data** - Core transformation
   - Load from source
   - Apply transformations
   - Validate output
3. **Generate report** - Summarize results
   - Save outputs
   - Calculate metrics
   - Report completion
```

#### Research-Plan-Execute

```markdown
## Workflow

1. **Research context** - Understand state
   - Read relevant files
   - Analyze dependencies
   - Identify constraints
2. **Create plan** - Design approach
   - List required changes
   - Define success criteria
   - Estimate complexity
3. **Execute plan** - Implement
   - Make changes
   - Validate each step
   - Report outcomes
```

#### Test-Fix-Verify

```markdown
## Workflow

1. **Run tests** - Identify issues
   - Execute test suite
   - Capture failures
   - Analyze patterns
2. **Fix issues** - Address failures
   - Diagnose root causes
   - Implement fixes
   - Maintain test integrity
3. **Verify fixes** - Confirm resolution
   - Re-run failed tests
   - Check for regressions
   - Document changes
```

### Agent Communication Patterns

#### Command to Agent

```markdown
## Step 3: Deploy specialized agent

**Launch analyzer agent**

- Agent: `.claude/agents/analyzer.md`
- Variables passed:
  TARGET_FILE: from_step_1
  ANALYSIS_TYPE: comprehensive
  OUTPUT_FORMAT: json
- Expected return: Analysis report
```

#### Agent Response Handling

```markdown
## Step 4: Process agent results

**Aggregate findings from agents**

- Collect all reports
- Merge duplicate findings
- Prioritize by severity
- Generate unified summary
```

### Error Handling Patterns

#### Graceful Degradation

```markdown
- Try optimal approach (Method A)
- On failure, try alternative (Method B)
- On failure, try fallback (Method C)
- If all fail, report with detailed context
```

#### Validation Gates

```markdown
# Pre-execution

- Verify all prerequisites
- Check resource availability
- Confirm permissions
- STOP if requirements not met

# Mid-execution

- Monitor progress indicators
- Validate intermediate results
- Adjust approach if needed

# Post-execution

- Confirm expected outcomes
- Validate output integrity
- Report any discrepancies
```

#### Recovery Strategies

```markdown
# On error detection:

1. Log detailed context
2. Attempt automatic recovery
3. If recovery fails:
   - Save current state
   - Rollback if possible
   - Report clear status
   - Suggest manual steps
```

### Bash Execution Patterns

#### Command Output Capture

```markdown
# Include output in context

- Current status: !`git status`
- File count: !`find . -type f | wc -l`
- Dependencies: !`npm list --depth=0`
```

#### Safe Command Execution

```markdown
# With error handling

- Run: `command || echo "Command failed"`
- Check exit code before proceeding
- Capture both stdout and stderr
```

---

## Quick Reference Checklist

[Validation points for command and agent creation]

### Pre-Implementation

- [ ] Clear purpose: Command or Agent?
- [ ] Model selected for task complexity
- [ ] Tools identified (minimum required)
- [ ] Arguments vs Variables understood

### Structure Validation

- [ ] Frontmatter complete and valid
- [ ] Title is action-oriented
- [ ] Purpose is 1-2 sentences
- [ ] Variables in UPPER_SNAKE_CASE
- [ ] Instructions provide context only

### Workflow Quality

- [ ] Step 1 validates prerequisites
- [ ] Progress feedback included
- [ ] Error handling defined
- [ ] Parallel execution where beneficial
- [ ] Clear termination conditions

### Report Clarity

- [ ] Status indicators used (✅⚠️❌)
- [ ] Output location specified
- [ ] Key results summarized
- [ ] Format consistent with type

### Command-Specific

- [ ] Argument hints provided
- [ ] User feedback included
- [ ] Agent deployment documented
- [ ] Results aggregation planned

### Agent-Specific

- [ ] Single responsibility clear
- [ ] Input contract defined
- [ ] Output structure specified
- [ ] Reports to orchestrator

### Language Quality

- [ ] Direct, imperative voice
- [ ] Variables referenced correctly
- [ ] No redundant information
- [ ] Clear action words used

---

## Anti-Patterns to Avoid

[Common mistakes that reduce clarity and effectiveness]

### Don't Repeat Information

```markdown
❌ Bad: Mentioning same requirement in Core Responsibilities and Workflow
Core Responsibilities: Validate all inputs
Workflow: 1. Validate all inputs

✅ Good: Context in Core Responsibilities, execution in Workflow
Core Responsibilities: Use strict schema validation
Workflow: 1. Validate against schema.json
```

### Don't Use Vague Language

```markdown
❌ Bad: "Process the files appropriately"
✅ Good: "Validate JSON schema, transform to CSV, save to OUTPUT_DIR"

❌ Bad: "Handle errors gracefully"
✅ Good: "Log error, attempt retry, rollback on failure"
```

### Don't Mix Variable Formats

```markdown
❌ Bad: Mixed cases and styles
myVariable, MY_VARIABLE, MyVariable, my-variable

✅ Good: Consistent UPPER_SNAKE_CASE
TARGET_DIR, OUTPUT_FORMAT, MAX_RETRIES
```

### Don't Define Arguments as Variables (Unless Assigning)

```markdown
❌ Bad: Defining without purpose

## Variables

ISSUE_NUMBER: $1 # Just repeating

✅ Good: Assigning for reuse

## Variables

ISSUE_PATH: issues/$1.json
COMMIT_MESSAGE: "fix: $ARGUMENTS"
```

### Don't Omit Error Handling

```markdown
❌ Bad: Assuming success

1. Read file
2. Process data
3. Save output

✅ Good: Planning for failure

1. Read file (exit if not found)
2. Process data (rollback on error)
3. Save output (report if fails)
```

### Don't Forget Progress Feedback

```markdown
❌ Bad: Silent operations
Process all files in directory

✅ Good: Informative progress

- Echo "Processing file 1 of N: filename"
- Show progress percentage
- Report completion of each step
```

### Don't Hardcode Sensitive Values

```markdown
❌ Bad: Credentials in file
API_KEY: `sk-actual-key-here`
PASSWORD: `admin123`

✅ Good: External configuration
API_KEY: Read from environment
PASSWORD: Prompt for secure input
```

### Don't Overload Single Agent

```markdown
❌ Bad: Agent doing everything

- Security scanning
- Performance optimization
- Test generation
- Documentation writing

✅ Good: Specialized agents

- security-scanner (single focus)
- performance-optimizer (single focus)
```

### Don't Use Ambiguous Descriptions

```markdown
❌ Bad: Generic description
description: Process files

✅ Good: Specific trigger
description: Convert CSV files to JSON format with validation
```

### Don't Skip Validation

```markdown
❌ Bad: Immediate execution

1. Deploy to production

✅ Good: Validate first

1. Validate deployment prerequisites
2. Run pre-deployment tests
3. Deploy to production
```

---

## Writing Tips

[Best practices for clear and effective documentation]

### Be Specific

- Use exact file paths, not "the file"
- Specify formats: "JSON with 2-space indent"
- Include examples when helpful

### Be Concise

- Remove unnecessary words
- Use bullet points over paragraphs
- One idea per line

### Be Consistent

- Same terminology throughout
- Matching variable names
- Uniform status messages

### Be Actionable

- Start with verbs
- Clear success criteria
- Specific next steps

### Think Like the AI

- Provide context it needs
- Structure for clarity
- Anticipate ambiguities

---

## Advanced Techniques

[Sophisticated patterns for complex scenarios]

### Conditional Agent Deployment

```markdown
## Workflow

3. **Deploy agents based on changes**
   - If .js files modified:
     - Deploy eslint-checker
     - Deploy jest-runner
   - If .py files modified:
     - Deploy pylint-checker
     - Deploy pytest-runner
```

### Dynamic Variable Assignment

```markdown
## Workflow

1. **Determine configuration**
   - If $1 equals "production":
     - Set CONFIRM_REQUIRED=true
     - Set LOG_LEVEL=verbose
   - Else:
     - Set CONFIRM_REQUIRED=false
     - Set LOG_LEVEL=normal
```

### Recursive Processing

```markdown
<process-directory>
  - List all items in current directory
  - For each item:
    - If file: process according to type
    - If directory: recurse into it
  - Aggregate all results
</process-directory>
```

### State Preservation

```markdown
## Workflow

2. **Save state before risky operation**
   - Create checkpoint file
   - Store current configuration
   - Note rollback commands
3. **Execute risky operation**
   - Perform changes
   - If success: delete checkpoint
   - If failure: restore from checkpoint
```

### Multi-Stage Validation

```markdown
## Workflow

1. **Pre-validation** - Basic checks
   - File exists
   - Permissions available
2. **Deep validation** - Content checks
   - Schema compliance
   - Data integrity
   - Business rules
3. **Post-validation** - Result checks
   - Output generated
   - No data loss
   - Performance acceptable
```
