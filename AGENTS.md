# Guiding coding agents

Guidelines for AI agents working in this repository. This file is for **rules and conventions** — what the code should
do, how it should be structured, and what to avoid. How something works (architecture decisions, design choices,
step-by-step procedures) belongs in the `documentation/` directory, not here.

These rules apply to every task in this project unless explicitly overridden.
Bias: caution over speed on non-trivial work. Use judgment on trivial tasks.

---

## Behavioral Rules

### Rule 1 — Think Before Coding

State assumptions explicitly. If uncertain, ask rather than guess.
Push back when a simpler approach exists. Stop when confused.

### Rule 2 — Simplicity First

Minimum code that solves the problem. Nothing speculative.
No features beyond what was asked. No abstractions for single-use code.

### Rule 3 — Surgical Changes

Touch only what you must. Clean up only your own mess.
Don't "improve" adjacent code, comments, or formatting. Match existing style.

### Rule 4 — Goal-Driven Execution

Define success criteria. Loop until verified.
Don't follow steps. Define success and iterate independently.

### Rule 5 — Token Budgets Are Not Advisory

Per-task and per-session token budgets are enforced. If approaching the limit, summarize and start fresh. Surface the
breach.

### Rule 6 — Read Before You Write

Before adding code, read exports, immediate callers, shared utilities.
If unsure why code is structured a certain way, ask.

### Rule 7 — Checkpoint After Every Significant Step

Summarize what was done, what's verified, what's left.
Don't continue from a state you can't describe back. Stop and restate.

### Rule 8 — Fail Loud

"Completed" is wrong if anything was skipped silently.
"Tests pass" is wrong if any fail.
Default to surfacing uncertainty, not hiding it.

---

## Project References

Deep-dive documentation lives in the `documentation/` directory. Consult these before working in the areas they cover.

### Architecture & Structure

- **[Project Structure](documentation/STRUCTURE.md)** — High-level layout, config hierarchy, build output, tech stack
