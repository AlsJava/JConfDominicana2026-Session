# Resource

This folder contains practice guides for the talk — deep dives into the architectural thinking, engineering decisions, and development practices that shape the codebase. Each file covers a specific aspect of how we work, make decisions, and structure the solution.

---

## Contents

| File | What you'll learn |
|------|-------------------|
| [DEVELOPMENT.md](DEVELOPMENT.md) | Architecture paradigms (Hexagonal, Microservices, Traditional/MVC), how team size drives architectural choices, decision frameworks, and the Command + CQRS pattern used in this project |
| [AI.md](AI.md) | How to use AI as a tool — not a replacement — for development; setting up AGENTS.md, supervision practices, and high-value use cases like commit messages, PR descriptions, and dependency reports |
| [MAINTAINABILITY.md](MAINTAINABILITY.md) | The 5 levels of documentation (architecture → code), why documentation as code matters, how to design documentation that works for both humans and AI tools |
| [SOLUTION_PERFORMANCE.md](SOLUTION_PERFORMANCE.md) | Performance beyond Big-O: database indices, serialization protocols (JSON, Protobuf, Kryo), Jackson Blackbird, and extracting queries via Spring Boot AOT |

---

## Which guide should I read?

| You are... | Start with... |
|------------|---------------|
| New to the project or the talk | [DEVELOPMENT.md](DEVELOPMENT.md) — it covers the architectural thinking behind the codebase |
| Interested in AI-assisted development | [AI.md](AI.md) — practical guidance on using AI without losing control |
| Focused on code quality and long-term maintenance | [MAINTAINABILITY.md](MAINTAINABILITY.md) — documentation strategies that prevent chaos |
| Working on performance optimization | [SOLUTION_PERFORMANCE.md](SOLUTION_PERFORMANCE.md) — concrete techniques from indices to serialization |

---

## Quick Start

- **New to the project?** Start with [DEVELOPMENT.md](DEVELOPMENT.md) — it covers the architectural thinking behind the codebase and how team structure influences design decisions.
- **Working on a feature?** Check the relevant guide above for patterns and conventions that apply to your work.
