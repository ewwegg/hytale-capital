# Development Philosophy

## Purpose

Deliver working software that solves real problems, as simply as possible.

---

## Principles

Resolve conflicts when making decisions. Higher beats lower.

1. **Working beats perfect** — Functional today defeats flawless eventually
2. **Simple beats flexible** — Solve current problems, not hypothetical ones
3. **Conventional beats clever** — Standard patterns, obvious code, boring technology
4. **Explicit beats abstract** — Clear duplication beats obscure abstraction
5. **Reversible beats optimal** — Easy to undo beats difficult to change

---

## Practice

### Decide What to Build

- Every increment must answer: _"What can a user do when this is done?"_
- If the answer is "nothing yet," reduce scope until it delivers observable value
- Build through the entire stack narrowly before building any layer broadly
- Validates assumptions while they're cheap to correct

### Decide How to Build

- Start with the simplest implementation that works
- Add complexity only when the current approach has failed in practice—not theory
- Use framework defaults; deviation requires documented justification
- Choose tools one person can maintain without becoming an expert

### Decide When It's Done

- Working software is the measure
- Verify with real usage
- If more complex than expected, simplify before moving on
- If a new developer wouldn't understand it, clarify it

---

## Discipline

Sustainable development requires recognising and eliminating three forms of dysfunction.

### Muda — Waste

Work that consumes resources without creating value.

| Waste                 | Example                        | Response                              |
| --------------------- | ------------------------------ | ------------------------------------- |
| Speculative features  | "We might need this later"     | Build for current requirements only   |
| Premature abstraction | Generalising from one example  | Wait for three concrete cases         |
| Unused flexibility    | Configuration nobody changes   | Hardcode until variation is proven    |
| Unvalidated work      | Building without user feedback | Ship smaller increments, learn faster |

### Mura — Inconsistency

Variation that creates friction and cognitive load.

| Inconsistency       | Example                                    | Response                                 |
| ------------------- | ------------------------------------------ | ---------------------------------------- |
| Mixed conventions   | Different patterns for same problem        | Match existing project patterns          |
| Implicit behaviour  | Magic that obscures intent                 | Make behaviour explicit and traceable    |
| Unclear naming      | Generic names like `getData`               | Precise names like `getUserOrderHistory` |
| Scattered knowledge | Same concept documented in multiple places | Single source of truth                   |

### Muri — Overburden

Unreasonable demands on people or systems.

| Overburden            | Example                                 | Response                                  |
| --------------------- | --------------------------------------- | ----------------------------------------- |
| Clever code           | Requires expertise to understand        | Prefer obvious over elegant               |
| Complex dependencies  | Deep framework knowledge required       | Choose tools with shallow learning curves |
| Manual repetition     | Repeated tasks humans shouldn't do      | Automate tests, deploys, formatting       |
| Technical debt denial | Accumulating shortcuts without tracking | Document debt, address consciously        |

---

## AI Collaboration

### Strengths

- Boilerplate generation
- Research and option surveying
- Documentation drafting
- Mechanical refactoring
- Thinking through problems aloud

### Failure Modes

- Over-engineering when simple suffices
- Hallucinated APIs and documentation
- Security and logic errors in generated code
- Unnecessary complexity presented as completeness

### Instructions for AI Agents

- When scope is uncertain, implement the minimal interpretation
- Match existing project conventions before introducing new patterns
- When requirements are ambiguous, ask before building
- When simplicity conflicts with completeness, state the tradeoff
- Build what's requested, not what might be useful

---

When uncertain: _what's the least I can build that proves this works?_
