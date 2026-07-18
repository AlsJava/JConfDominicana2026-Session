# Development Guide — Architecture & Team Dynamics

> How you organize your team shapes how your code is organized. Architecture is not just about code structure — it's
> about the flow of work, the rhythm of collaboration, and the decisions that emerge from it.

---

## 1. Team Size & Its Architectural Implications

The size of a development team is one of the most consequential parameters in architecture decisions — yet it is almost
always overlooked. Teams fall into three categories:

```
┌─────────────────────────────────────────────────────────────┐
│                     TEAM SIZE CLASSIFICATION                │
├────────────┬────────────┬───────────────────────────────────┤
│  SMALL     │  MEDIUM    │  LARGE                            │
│  ────────  │  ────────  │  ────────                         │
│  1–2 devs  │  3–5 devs  │  6+ devs                          │
│            │            │                                   │
│  · Solo    │  · Small   │  · Multiple squads                  │
│    coder   │    teams   │                                   │
│  · Shared  │  · Cross-  │  · Cross-functional teams           │
│    context │    functional│  · Domain-driven boundaries        │
│  · Informal│    teams   │  · Formal contracts & interfaces   │
│    comms   │            │                                   │
└────────────┴────────────┴───────────────────────────────────┘

  (Excludes QA, DevOps, Product, and other adjacent roles)
```

### What each size demands

| Dimension           | Small (1–2)             | Medium (3–5)                         | Large (6+)                              |
|---------------------|-------------------------|--------------------------------------|-----------------------------------------|
| **Communication**   | Verbal, synchronous     | Mixed async/sync, lightweight docs   | Structured, documented, formalized      |
| **Code boundaries** | Fuzzy, shared ownership | Clear module boundaries, conventions | Strict interfaces, contracts, contracts |
| **Decision speed**  | Instant                 | Fast but needs alignment             | Slower, requires governance             |
| **Risk**            | Bus factor = 1          | Moderate                             | High coordination overhead              |

> **Key insight:** Architecture should match team topology. A large-team architecture (strict contracts, modular
> boundaries) works for a small team too — but the reverse is rarely true.

---

## 2. The Three Architecture Paradigms

While there are countless sub-categories and hybrid patterns, all application architectures can be grouped into three
primary paradigms:

```
                        ┌──────────────────────┐
                        │   ARCHITECTURE       │
                        │   DECISIONS          │
                        └──────────┬───────────┘
                                   │
              ┌────────────────────┼────────────────────┐
              │                    │                    │
     ┌────────▼────────┐  ┌───────▼────────┐  ┌───────▼────────┐
     │  HEXAGONAL      │  │  MICROSERVICES │  │  TRADITIONAL   │
     │  (Ports &       │  │  (Distributed  │  │  (MVC / MVP /  │
     │   Adapters)      │  │   Services)    │  │   Layered)     │
     └────────┬────────┘  └───────┬────────┘  └───────┬────────┘
              │                    │                    │
   Modular     │                    │  Monolithic        │
   monolith    │                    │  (or layered)      │
              │                    │                    │
   · Domain-   │  · Independent     │  · Controller →    │
     centric   │    deployable      │    Service →       │
   · Interface │    services        │    Repository      │
     isolation │  · Network-bound   │  · Shared DB       │
   · Testable  │    duplication     │  · Shared deploy   │
     in isola- │  · High ops cost   │                    │
     tion      │  · Team experience │                    │
              │                    │                    │
   Example:    │  Example:          │  Example:          │
   ERP, Sports │  E-commerce,       │  Internal tools,   │
   management  │  SaaS platforms    │  CRUD apps         │
```

### Why these three?

Every architecture exists on a spectrum between **coupling** and **deployment independence**. These three paradigms
represent the dominant positions on that spectrum:

```
  Coupling ──────────────────────────────────────────────► Loose coupling
       │                                                    │
       ▼                                                    ▼
  ┌──────────┐                                      ┌──────────────┐
  │ TRADITIONAL│                                    │ MICROSERVICES│
  │ MVC/Layered│                                    │ Distributed  │
  └──────────┘                                      └──────────────┘
       │                                                    │
       └──────────┬──────────────────────┬──────────────────┘
                  │                      │
                  ▼                      ▼
            ┌───────────┐        ┌───────────────┐
            │ HEXAGONAL │        │ MICROSERVICES │
            │ (Modular  │        │ (Distributed  │
            │  Mono-    │        │  Services)    │
            │  lith)    │        │               │
            └───────────┘        └───────────────┘
                  │
                  │  Hexagonal is the "conscious" version of
                  │  a monolith — same deployment, but with
                  │  microservice-like boundaries internally.
                  │
                  ▼
            Modular Monolith
            (deployable as one unit,
             structured as many)
```

---

## 3. Decision Framework — The Hidden Parameters

Most architecture decisions are made based on technology trends, personal preference, or what the team has used before.
But there are parameters that should drive the decision — parameters that are often omitted from the conversation:

```
                    ┌─────────────────────────┐
                    │    WHAT DRIVES YOUR     │
                    │   ARCHITECTURE CHOICE?  │
                    └────────────┬────────────┘
                                 │
              ┌──────────────────┼──────────────────┐
              │                  │                  │
              ▼                  ▼                  ▼
     ┌─────────────────┐ ┌──────────────┐ ┌─────────────────┐
     │ TEAM FACTORS    │ │ BUSINESS     │ │ OPERATIONAL     │
     │                 │ │ FACTORS      │ │ FACTORS         │
     ├─────────────────┤ ├──────────────┤ ├─────────────────┤
     │ · Team size     │ │ · Domain     │ │ · Deployment    │
     │ · Experience    │ │   complexity │ │   frequency     │
     │ · Communication │ │ · Growth     │ │   trajectory    │
     │   patterns      │ │ · Time-to-   │ │ · Observability │
     │ · Available     │ │   market     │ │   needs         │
     │   roles (QA,    │ │              │ │ · Team's DevOps │
     │    DevOps)      │ │              │ │   maturity      │
     └─────────────────┘ └──────────────┘ └─────────────────┘
```

### The parameters most teams ignore

| Parameter                 | What to ask                               | Why it matters                                    |
|---------------------------|-------------------------------------------|---------------------------------------------------|
| **Team size & stability** | How many devs? Will they stay?            | Bus factor drives boundary strictness             |
| **Transaction volume**    | How many transactions/day?                | Microservices overhead is only justified at scale |
| **Deployment frequency**  | How often do you deploy?                  | Independent deployability is microservices' value |
| **Domain complexity**     | Is the domain well-understood?            | Complex domains benefit from clear boundaries     |
| **Team experience**       | Has the team done distributed systems?    | Microservices require SRE-level ops maturity      |
| **Time to market**        | How fast does the business need features? | Traditional is faster to start                    |

---

## 4. Hexagonal Architecture (Ports & Adapters)

### When to use it

> **Use Hexagonal when:** You have a well-structured team, changes come in as specific scenarios, and the application
> contains isolated functional modules that could *one day* become independent services.

### Core concept

Hexagonal architecture (also called Ports & Adapters) places the **domain at the center** and pushes all dependencies
outward:

```
                    ┌───────────────────────────┐
                    │                           │
        ┌───────────┤    PRESENTATION           ├───────────┐
        │           │    ADAPTERS               │           │
        │           │  (REST, CLI, gRPC)        │           │
        │           │                           │           │
        │           └───────────┬───────────────┘           │
        │                       │                           │
        │          ┌────────────▼────────────┐             │
        │          │     PORTS               │             │
        │          │  (Interfaces / Contracts)│            │
        │          └────────────┬────────────┘             │
        │                       │                           │
        │           ┌───────────▼───────────┐              │
        │           │                       │              │
        │           │    DOMAIN CORE        │              │
        │           │  (Entities,           │              │
        │           │   Value Objects,      │              │
        │           │   Domain Services)    │              │
        │           │                       │              │
        │           └───────────┬───────────┘              │
        │                       │                           │
        │           ┌───────────▼───────────┐              │
        │           │     ADAPTERS          │              │
        │           │  (Infrastructure)     │              │
        │           │  (DB, MQ, External    │              │
        │           │   APIs)               │              │
        │           └───────────┬───────────┘              │
        │                       │                           │
        └───────────────────────┴───────────────────────────┘
```

### Key characteristics

- **Dependency rule:** Dependencies point inward. The domain knows nothing about infrastructure.
- **Testability:** Domain logic can be tested without any framework, database, or network.
- **Modularity:** Each port is a contract — modules can be developed independently.
- **Extrapolation path:** A hexagonal module can become a microservice with minimal refactoring (just add a network
  adapter).

### Ideal for

- **Business management systems (ERP)** — isolated functional domains (inventory, billing, HR)
- **Sports team management** — distinct modules (scheduling, roster, analytics)
- **Any domain where features are naturally isolated** and the team values testability

### Real-world example in this project

This repository implements a CQRS-based architecture that aligns closely with hexagonal principles:

```
  src/main/java/org/alsjava/sessions/
  ├── command/                          ← Application layer (ports)
  │   ├── Sum2NumbersCommand.java       ← Command (input port)
  │   └── handler/
  │       └── Sum2NumbersCommandHandler.java  ← Handler (adapter)
  ├── pattern/command/                  ← Framework (port definitions)
  │   ├── Command.java                  ← Abstract command
  │   ├── CommandBus.java               ← Central dispatcher
  │   └── CommandHandler.java           ← Handler interface
  ├── model/                            ← Domain layer (core)
  │   ├── DataDto.java
  │   └── exception/                    ← Domain exceptions
  └── controller/                       ← Presentation adapter
      └── ExampleController.java        ← REST adapter
```

---

## 5. Microservices Architecture

### When to use it

> **Use Microservices when:** Your application needs to scale massively, handle millions of transactions per day, and
> your team has the operational maturity to manage distributed systems. If you're not hitting 1–2 million daily
> transactions, microservices are likely counterproductive.

### Core concept

Microservices decompose an application into independently deployable, loosely coupled services:

```
                          ┌─────────────────────────────┐
                          │       API GATEWAY            │
                          │     (Routing, Auth)          │
                          └─────────────┬───────────────┘
                                        │
              ┌─────────────────────────┼─────────────────────────┐
              │                         │                         │
              ▼                         ▼                         ▼
     ┌────────────────┐        ┌────────────────┐        ┌────────────────┐
     │  USER SERVICE   │        │  ORDER SERVICE  │        │  PAYMENT SVC   │
     │                │        │                │        │                │
     │  ┌──────────┐  │        │  ┌──────────┐  │        │  ┌──────────┐  │
     │  │  Domain  │  │        │  │  Domain  │  │        │  │  Domain  │  │
     │  └────┬─────┘  │        │  └────┬─────┘  │        │  └────┬─────┘  │
     │       │        │        │       │        │        │       │        │
     │  ┌────▼─────┐  │        │  ┌────▼─────┐  │        │  ┌────▼─────┐  │
     │  │   DB     │  │        │  │   DB     │  │        │  │   DB     │  │
     │  └──────────┘  │        │  └──────────┘  │        │  └──────────┘  │
     └────────────────┘        └────────────────┘        └────────────────┘
          :8081                    :8082                     :8083
```

### The hidden costs

```
  ┌──────────────────────────────────────────────────────────────┐
  │              MICROSERVICES: THE PRICE OF FLEXIBILITY          │
  ├──────────────────┬───────────────────────────────────────────┤
  │ Cost             │ Impact                                    │
  ├──────────────────┼───────────────────────────────────────────┤
  │ Network latency  │ Every inter-service call is a network call│
  │ Data consistency │ No distributed transactions (eventually)  │
  │ Deployment ops   │ N services × M instances = N×M deploys    │
  │ Observability    │ Distributed tracing, log aggregation      │
  │ Testing          │ Integration testing across services       │
  │ Team overhead    │ Service contracts, versioning, compatibility│
  └──────────────────┴───────────────────────────────────────────┘
```

### The scale threshold

```
  Transactions/Day
       │
   10M │                              ╱ Microservices
       │                            ╱
   1M  │──────────────────────────╯──────────────────────►
       │              ╱ Hexagonal  │
       │            ╱  (modular   │
     1K│──────────╯    monolith)  │
       │        ╱                 │
       │      ╱                  │
       │    ╱                    │
     10│  ╱                     │
       │╱ Traditional (MVC)      │
       └──────────────────────────────────────────────►
            1    5    10    50    100    500   Team Size
                                   (developers)
```

> **Rule of thumb:** If your application won't handle 1–2 million transactions per day, microservices add complexity
> without proportional benefit.

---

## 6. Traditional Architecture (MVC / Layered)

### When to use it

> **Use Traditional when:** You need speed to market, the domain is straightforward, and your team values simplicity
> over theoretical purity. MVC is the most underrated architecture for monoliths — and it can be the most scalable,
> maintainable, and developer-friendly option for large teams.

### Core concept

The classic layered approach separates concerns into vertical layers:

```
  ┌─────────────────────────────────────────────┐
  │            PRESENTATION LAYER               │
  │      (Controllers, Views, Templates)         │
  ├─────────────────────────────────────────────┤
  │             SERVICE / BUSINESS              │
  │        (Business logic, orchestration)       │
  ├─────────────────────────────────────────────┤
  │            DATA ACCESS LAYER                │
  │       (Repositories, ORM, Queries)           │
  ├─────────────────────────────────────────────┤
  │              DATABASE                        │
  └─────────────────────────────────────────────┘
```

### Why MVC can be the best choice for large teams

```
  ┌─────────────────────────────────────────────────────────────┐
  │            WHY MVC WORKS FOR LARGE TEAMS                     │
  ├─────────────────────────────────────────────────────────────┤
  │                                                             │
  │  1. LOW COGNITIVE LOAD                                     │
  │     Every developer knows MVC. No onboarding friction.      │
  │                                                             │
  │  2. CLEAR OWNERSHIP BOUNDARIES                              │
  │     Frontend team → Controllers/Views                       │
  │     Backend team  → Services                                │
  │     Data team     → Repositories                            │
  │                                                             │
  │  3. PATTERNS ADD FLEXIBILITY                                │
  │     Command pattern → task assignment & isolation           │
  │     CQRS            → read/write separation                 │
  │     Repository      → data access abstraction               │
  │                                                             │
  │  4. DEPLOYMENT SIMPLICITY                                  │
  │     One artifact, one deploy, one rollback.                 │
  │                                                             │
  │  5. SCALABILITY IS ABOUT CODE QUALITY, NOT TOPOLOGY        │
  │     A well-structured MVC app scales better than a          │
  │     poorly structured microservice app.                     │
  │                                                             │
  └─────────────────────────────────────────────────────────────┘
```

### MVC + Command Pattern + CQRS = Best of both worlds

The most powerful monolithic architecture combines MVC's simplicity with the structural benefits of Command pattern and
CQRS:

```
  ┌─────────────────────────────────────────────────────────────┐
  │                    REQUEST FLOW                              │
  │                                                             │
  │  HTTP Request                                               │
  │       │                                                     │
  │       ▼                                                     │
  │  ┌─────────────┐     ┌──────────────────┐                  │
  │  │ Controller   │────▶│  Command Object  │                  │
  │  │ (MVC)        │     │  (Command<T>)    │                  │
  │  └─────────────┘     └────────┬─────────┘                  │
  │                               │                             │
  │                               ▼                             │
  │                        ┌──────────────┐                     │
  │                        │  Command Bus  │                     │
  │                        │  (Dispatcher) │                     │
  │                        └──────┬───────┘                     │
  │                               │                             │
  │              ┌────────────────┼────────────────┐            │
  │              │                │                │            │
  │              ▼                ▼                ▼            │
  │     ┌──────────────┐  ┌──────────────┐  ┌──────────────┐   │
  │     │ Handler A    │  │ Handler B    │  │ Handler C    │   │
  │     │ (Write)      │  │ (Write)      │  │ (Read)       │   │
  │     └──────┬───────┘  └──────┬───────┘  └──────┬───────┘   │
  │            │                 │                 │            │
  │            ▼                 ▼                 ▼            │
  │     ┌──────────────┐  ┌──────────────┐  ┌──────────────┐   │
  │     │ Repository   │  │ Repository   │  │ Query Model  │   │
  │     │ (Write DB)   │  │ (Write DB)   │  │ (Read DB)    │   │
  │     └──────────────┘  └──────────────┘  └──────────────┘   │
  │                                                             │
  └─────────────────────────────────────────────────────────────┘

  Benefits:
  · Each command is a self-contained unit of work
  · Easy to assign tasks to individual developers
  · Read/write models can evolve independently
  · Domain logic stays isolated from HTTP concerns
  · Testing is straightforward (test the handler, not the controller)
```

### Real-world example in this project

This repository already implements Command + CQRS within an MVC-style Spring Boot application:

```
  Spring MVC Controller
       │
       ▼
  Sum2NumbersCommand (Command object)
       │
       ▼
  CommandBus → Sum2NumbersCommandHandler
       │
       ▼
  Sum2NumbersResponse
       │
       ▼
  HTTP Response (JSON)
```

This proves that you don't need microservices to get the benefits of command-driven architecture. The same patterns that
make microservices testable and maintainable are achievable within a single deployable unit.

---

## 7. Decision Matrix

Use this matrix as a starting point (not a rulebook):

```
┌─────────────────────┬──────────────┬──────────────┬──────────────┐
│                     │  SMALL TEAM  │  MEDIUM TEAM │  LARGE TEAM  │
│  Architecture       │  (1–2 devs)  │  (3–5 devs)  │  (6+ devs)   │
├─────────────────────┼──────────────┼──────────────┼──────────────┤
│ Hexagonal           │  ✓ If you   │  ✓ Ideal    │  ✓ Excellent│
│                     │   want to    │             │             │
│                     │   learn it   │             │             │
├─────────────────────┼──────────────┼──────────────┼──────────────┤
│ Microservices       │  ✗ Overkill │  ⚠ Only if  │  ✓ If you   │
│                     │              │  scaling is │  need it    │
│                     │              │  critical   │             │
├─────────────────────┼──────────────┼──────────────┼──────────────┤
│ Traditional (MVC)   │  ✓ Default   │  ✓ Strong   │  ✓ Best     │
│ + Command/CQRS      │              │  choice     │  for most   │
│                     │              │              │  large teams│
└─────────────────────┴──────────────┴──────────────┴──────────────┘

  ✓ Ideal    · Strong recommendation
  ⚠ Conditional  · Use with caution
  ✗ Against  · Generally not recommended
```

---

## 8. The Myth of the "Right" Architecture

```
                    ┌─────────────────────┐
                    │                     │
                    │   "There is no      │
                    │    perfect           │
                    │    architecture."    │
                    │                     │
                    └──────────┬──────────┘
                               │
              ┌────────────────┼────────────────┐
              │                │                │
              ▼                ▼                ▼
     ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
     │ YAGNI         │  │ SOLID        │  │ KISS         │
     │               │  │              │  │              │
     │ Use the       │  │ Principles   │  │ Keep it      │
     │ simplest      │  │ guide the    │  │ simple —     │
     │ thing that    │  │ architecture │  │ complexity   │
     │ works for     │  │ decisions    │  │ is the real   │
     │ your team     │  │              │  │ enemy        │
     └──────────────┘  └──────────────┘  └──────────────┘
```

The best architecture is the one your team can actually maintain. A simple MVC app with good patterns will outperform a
poorly implemented microservice architecture every time. Choose based on your team's reality, not based on conference
talks.

---

## 9. Summary

| Factor                    | Hexagonal             | Microservices        | Traditional + CQRS      |
|---------------------------|-----------------------|----------------------|-------------------------|
| **Best team size**        | Any (grows with team) | 6+ with SRE maturity | Any, especially large   |
| **Deployment**            | Single unit           | Independent services | Single unit             |
| **Learning curve**        | Medium                | Steep                | Low                     |
| **Testability**           | Excellent             | Good (if done right) | Good                    |
| **Scalability path**      | → Microservices       | Native               | Modular → Microservices |
| **Operational overhead**  | Low                   | High                 | Low                     |
| **Time to first feature** | Medium                | Slow                 | Fast                    |

> **Bottom line:** Architecture is a means to an end — enabling your team to deliver value consistently. Match the
> architecture to your team's size, experience, and the business's scale requirements. When in doubt, start with a
> well-structured monolith (MVC + Command/CQRS) and evolve from there.
