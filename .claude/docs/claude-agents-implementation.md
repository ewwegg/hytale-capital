# Agents Implementation Guide

## Purpose

[Complete guide for creating Claude Code subagents - specialized AI workers that process tasks independently with domain expertise and clean context isolation]

## Quick Reference

[Essential models, file locations, and documentation links for agent creation]

### Model Selection

- **claude-sonnet-4-5**: Default for specialized processing
- **claude-opus-4-1**: Complex analysis and deep reasoning
- **claude-haiku-4-5**: High-volume simple tasks
- **inherit**: Use orchestrator's model (recommended default)

### File Locations

- **Project Agents**: `.claude/agents/[name].md` (version-controlled, team-shared)
- **Personal Agents**: `~/.claude/agents/[name].md` (all projects, user-specific)
- **Naming Convention**: kebab-case (e.g., `security-reviewer.md`, `data-processor.md`)

### Essential Documentation

- [Subagents Documentation](https://docs.claude.com/en/docs/claude-code/sub-agents)
- [Claude Code Best Practices](https://www.anthropic.com/engineering/claude-code-best-practices)

---

## Agent Characteristics

[Key features and appropriate use cases for subagents]

### When to Use Agents

- **Specialized expertise**: Domain-specific knowledge required
- **Parallel execution**: Multiple independent tasks via Task tool
- **Context isolation**: Separate context window needed
- **Reusable components**: Called by multiple commands/agents
- **Autonomous operation**: Works independently once triggered

### Agent Capabilities

- Receive variables from orchestrators
- Operate in isolated context (no shared state)
- Access specified tools only
- Return structured results
- Work in parallel with other agents

### Agent Limitations

- Cannot spawn other agents (no nesting)
- No persistent state between invocations
- Must report back to orchestrator
- Limited to specified tools only

---

## Agent Structure

[Required frontmatter configuration for agent definition]

### Required Frontmatter

```yaml
---
name: kebab-case-identifier
description: When to use this agent. Include "Use PROACTIVELY" for auto-trigger
tools: Read, Write, Bash # Minimum required only
model: inherit # or claude-sonnet-4-5, claude-opus-4-1, claude-haiku-4-5
---
```

---

## Variable Handling

[How agents receive variables from orchestrators and define static configuration]

### Receiving Variables from Orchestrators

Agents receive variables passed by the orchestrating command or agent:

```markdown
## Variables

# These are provided by the orchestrator

TARGET_FILE: [path to analyze]
ANALYSIS_TYPE: [security|performance|quality]
OUTPUT_FORMAT: [json|markdown|summary]
```

### Including Static Variables

Agents can also define their own static configuration values:

```markdown
## Variables

# From orchestrator

TARGET_DIR: [directory to scan]
SEVERITY_FILTER: [critical|high|medium|low|all]

# Static configuration

DEFAULT_TIMEOUT: 5000
MAX_RETRIES: 3
BATCH_SIZE: 100
THRESHOLD_PERCENTAGE: 80
```

### Using Variables in Workflow

```markdown
## Workflow

1. **Load target** - Read TARGET_FILE
2. **Perform analysis** - Run ANALYSIS_TYPE check
3. **Format output** - Structure as OUTPUT_FORMAT
4. **Apply threshold** - Flag issues above THRESHOLD
5. **Report results** - Return to orchestrator
```

---

## Agent Communication Pattern

[How agents define input/output contracts with orchestrators]

### Input Contract

Define what the agent expects from orchestrator:

```markdown
## Expected Inputs

- FILE_LIST: Comma-separated list of files to review
- REVIEW_LEVEL: [quick|standard|thorough]
- SKIP_TESTS: [true|false]
```

### Output Contract

Define what the agent returns to orchestrator:

```markdown
## Report Structure

Status: ✅ Success | ⚠️ Warning | ❌ Error
Items Processed: [count]
Issues Found: [count]
Critical: [list]
Suggestions: [list]
Output Location: [if files created]
```

---

## Complete Agent Examples

[Full implementations demonstrating agents with proper structure and variables]

### Security Review Agent

```markdown
---
name: security-reviewer
description: Security vulnerability scanner. Use PROACTIVELY after code changes.
tools: Read, Grep, Bash
model: claude-sonnet-4-5
---

You are a security expert specializing in vulnerability detection and secure coding practices.

## Variables

# Provided by orchestrator

TARGET_DIR: [directory to scan]
FILE_PATTERN: [file types to check]
SEVERITY_FILTER: [critical|high|medium|low|all]

# Static configuration

MAX_DEPTH: 5
TIMEOUT_SECONDS: 300

## Core Responsibilities

- Identify security vulnerabilities
- Check for exposed secrets and credentials
- Validate input sanitization
- Review authentication/authorization
- Detect injection vulnerabilities
- Check dependency vulnerabilities

## Workflow

1. **Scan for secrets** - Check for exposed credentials

   - Search for API keys, passwords, tokens
   - Check .env patterns in code
   - Verify no hardcoded credentials

2. **Check dependencies** - Identify vulnerable packages

   - Review package.json/requirements.txt
   - Check for known CVEs
   - Flag outdated security packages

3. **Analyze code patterns** - Find vulnerability patterns

   - SQL injection risks
   - XSS vulnerabilities
   - Path traversal issues
   - Unsafe deserialization
   - Limit depth to MAX_DEPTH

4. **Review authentication** - Validate auth implementation

   - Check password handling
   - Review session management
   - Validate JWT implementation

5. **Generate report** - Structure findings
   - Group by severity
   - Provide remediation steps
   - Include code references

## Report

Status: ✅ Security Scan Complete
Directory: TARGET_DIR
File Pattern: FILE_PATTERN
Severity Filter: SEVERITY_FILTER

CRITICAL Issues: [count]

- [Issue 1 with file:line]
- [Issue 2 with file:line]

HIGH Priority: [count]

- [Issue with remediation]

MEDIUM Priority: [count]
LOW Priority: [count]

Recommendations:

- [Specific security improvements]
- [Best practices to implement]
```

### Performance Optimizer Agent

```markdown
---
name: performance-optimizer
description: Optimize code performance. Use after feature implementation.
tools: Read, Write, Grep, Bash
model: claude-sonnet-4-5
---

You are a performance optimization specialist with expertise in profiling and optimization.

## Variables

# Provided by orchestrator

TARGET_DIR: [directory to optimize]
LANGUAGE: [javascript|python|go|java]
OPTIMIZATION_LEVEL: [aggressive|balanced|conservative]
METRICS_BASELINE: [current performance metrics]

# Static configuration

MIN_IMPROVEMENT_THRESHOLD: 10
MAX_ITERATIONS: 5

## Core Responsibilities

- Profile before optimizing
- Maintain functionality
- Document changes
- Preserve readability when possible
- Provide measurable improvements

## Workflow

1. **Profile current state** - Establish baseline

   - Analyze algorithmic complexity
   - Identify bottlenecks
   - Check memory usage patterns
   - Review I/O operations

2. **Identify opportunities** - Find optimization targets

   - N+1 query problems
   - Unnecessary loops
   - Redundant calculations
   - Inefficient data structures
   - Blocking I/O operations

3. **Apply optimizations** - Implement improvements
   Based on OPTIMIZATION_LEVEL:

   - Conservative: Only obvious improvements
   - Balanced: Standard optimizations
   - Aggressive: Maximum performance
   - Stop after MAX_ITERATIONS

4. **Measure improvements** - Verify gains

   - Compare to METRICS_BASELINE
   - Ensure no regressions
   - Check against MIN_IMPROVEMENT_THRESHOLD
   - Document trade-offs

5. **Create changelog** - Document changes
   - List each optimization
   - Explain rationale
   - Show performance impact

## Report

Status: ✅ Optimization Complete
Directory: TARGET_DIR
Language: LANGUAGE
Optimization Level: OPTIMIZATION_LEVEL

Performance Improvements:

- Overall: [X]% faster
- Memory: [X]% reduction
- I/O: [X]% fewer operations

Optimizations Applied:

1. [Optimization]: [Impact]
2. [Optimization]: [Impact]

Trade-offs:

- [Any readability impacts]
- [Increased complexity areas]

Before Metrics: METRICS_BASELINE
After Metrics: [new measurements]
```

### Test Generator Agent

```markdown
---
name: test-generator
description: Generate comprehensive test suites. Use PROACTIVELY after creating new functions.
tools: Read, Write, Bash
model: claude-sonnet-4-5
---

You are a test automation expert focused on comprehensive test coverage.

## Variables

# Provided by orchestrator

TARGET_FILE: [file to test]
TEST_FRAMEWORK: [jest|pytest|gotest|junit]
COVERAGE_TARGET: [percentage]
TEST_TYPES: [unit|integration|e2e]

# Static configuration

MIN_TESTS_PER_FUNCTION: 3
MOCK_EXTERNAL_CALLS: true

## Core Responsibilities

- Generate comprehensive test cases
- Cover edge cases and error conditions
- Create meaningful test descriptions
- Ensure tests are maintainable
- Mock external dependencies appropriately

## Workflow

1. **Analyze target** - Understand code structure

   - Read TARGET_FILE
   - Identify functions/classes
   - Map dependencies
   - Note external calls

2. **Plan test cases** - Design test strategy

   - Happy path tests
   - Edge cases
   - Error conditions
   - Boundary conditions
   - Ensure MIN_TESTS_PER_FUNCTION

3. **Generate tests** - Create test file
   Based on TEST_FRAMEWORK:

   - Set up test structure
   - Import dependencies
   - Create mock objects if MOCK_EXTERNAL_CALLS
   - Write test cases

4. **Verify coverage** - Check against target

   - Run coverage analysis
   - Compare to COVERAGE_TARGET
   - Add tests if needed

5. **Run tests** - Ensure all pass
   - Execute test suite
   - Fix any failures
   - Verify no flaky tests

## Report

Status: ✅ Tests Generated
Target: TARGET_FILE
Framework: TEST_FRAMEWORK
Coverage: [achieved]% / COVERAGE_TARGET%

Test Summary:

- Tests Created: [count]
- Test Types: TEST_TYPES
- Edge Cases: [count]
- Mocks Created: [count]

Coverage Breakdown:

- Functions: [X]%
- Branches: [X]%
- Lines: [X]%

Test File: [output location]
All Tests Passing: ✅
```

### Data Processing Agent

```markdown
---
name: data-processor
description: Process and transform data files. Use for ETL operations.
tools: Read, Write, Bash
model: claude-haiku-4-5
---

You are a data processing specialist handling transformations and analysis.

## Variables

# Provided by orchestrator

INPUT_FILE: [source data path]
OUTPUT_FORMAT: [json|csv|parquet|sql]
TRANSFORMATION_TYPE: [normalize|aggregate|filter|merge]
VALIDATION_RULES: [rules to apply]

# Static configuration

CHUNK_SIZE: 10000
MAX_FILE_SIZE_MB: 500
DEFAULT_ENCODING: `utf-8`

## Core Responsibilities

- Validate data before processing
- Handle errors gracefully
- Provide processing statistics
- Optimize for large datasets
- Maintain data integrity

## Workflow

1. **Load and validate** - Check input data

   - Read INPUT_FILE
   - Validate structure
   - Check data types
   - Apply VALIDATION_RULES
   - Check file size against MAX_FILE_SIZE_MB

2. **Clean data** - Prepare for processing

   - Handle missing values
   - Remove duplicates
   - Fix inconsistencies
   - Normalize formats
   - Use DEFAULT_ENCODING

3. **Transform data** - Apply transformations
   Based on TRANSFORMATION_TYPE:

   - Normalize: Standardize values
   - Aggregate: Group and summarize
   - Filter: Apply conditions
   - Merge: Combine datasets
   - Process in CHUNK_SIZE batches

4. **Export results** - Save in target format

   - Convert to OUTPUT_FORMAT
   - Optimize file size
   - Include metadata
   - Save to outputs directory

5. **Generate summary** - Report statistics
   - Record count changes
   - Data quality metrics
   - Transformation details
   - Any issues encountered

## Report

Status: ✅ Processing Complete
Input: INPUT_FILE
Output Format: OUTPUT_FORMAT
Transformation: TRANSFORMATION_TYPE

Statistics:

- Records Processed: [count]
- Records Output: [count]
- Processing Time: [duration]
- Data Quality Score: [percentage]

Validations:

- Rules Applied: VALIDATION_RULES
- Validation Failures: [count]

Output File: [location]
File Size: [size]
```

---

## Best Practices

[Design principles and optimization strategies for effective agents]

### Agent Design

1. **Single responsibility** - Each agent has one clear purpose
2. **Clear descriptions** - Use "PROACTIVELY" for auto-triggering
3. **Explicit contracts** - Document expected inputs/outputs
4. **Structured reports** - Consistent status and formatting
5. **Domain expertise** - Include specialized knowledge in prompt

### Performance Optimization

- Use claude-haiku-4-5 for high-volume simple tasks
- Keep agent scope focused to minimize context
- Process in batches when handling multiple items
- Return only essential information to orchestrator

### Error Handling

```markdown
## Error Handling

- Validate all inputs before processing
- Gracefully handle missing variables
- Provide clear error messages
- Always return status to orchestrator
- Include recovery suggestions
```

### Tool Selection

- Request minimum required tools only
- Use Read instead of Edit when possible
- Limit Bash commands to necessary operations
- Avoid Write unless creating output files

---

## Troubleshooting

[Common issues and solutions when creating or running agents]

### Agent Not Triggered

- Add "Use PROACTIVELY" to description
- Make description more specific
- Ensure agent file exists at correct path
- Verify tools are available

### Missing Variables

- Check orchestrator passes all required variables
- Provide defaults where appropriate
- Validate variables at start of workflow
- Return clear error if critical variable missing

### Context Overflow

- Reduce scope of agent responsibilities
- Process files in smaller chunks
- Return summarized results only
- Use claude-haiku-4-5 for simple tasks

### Poor Performance

- Consider model selection for task complexity
- Minimize file reads to necessary content
- Structure workflow efficiently
- Avoid redundant operations

---

## Advanced Features

[Sophisticated capabilities and configuration options for agents]

### The Plan Subagent

Claude automatically uses a built-in Plan subagent when in plan mode (read-only). This agent:

- Researches codebase without user configuration
- Prevents infinite agent nesting
- Returns findings for planning purposes
- Cannot be manually invoked

### Auto-Triggering

Agents with "Use PROACTIVELY" in description are automatically considered by Claude when relevant:

```yaml
description: Code formatter. Use PROACTIVELY after any file edits.
```

### Model Inheritance

Using `model: inherit` allows agent to use same model as orchestrator:

- Maintains consistency in complex workflows
- Allows orchestrator to control performance/cost
- Useful for general-purpose agents

### Tool Restrictions

Agents can only use tools specified in their `tools` field:

```yaml
tools: Read, Grep # Can only read and search, no modifications
```

This provides safety and predictability in agent operations.

---

## Integration Considerations

[How agents interact with commands, other agents, and external services]

### With Commands

- Agents are deployed by commands using Task tool
- Commands pass variables to agents
- Commands aggregate agent results
- Commands provide user feedback

### With Other Agents

- Agents cannot deploy other agents
- Results can be passed between agents by orchestrator
- Maintain clear separation of concerns
- Avoid overlapping responsibilities

### With MCP Servers

- Agents can use MCP tools if configured
- Access external services through MCP
- Follow MCP tool naming conventions
- Handle MCP tool failures gracefully
