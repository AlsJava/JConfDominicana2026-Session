# Performance Solutions — Beyond Algorithmic Complexity

> **Performance is not just Big-O notation.** An O (1) algorithm running against a saturated database, a slow disk, or a
> fat serialized payload will always lose to a well-tuned O (n log n) algorithm running in the right environment.

---

## 1. The Big-O Illusion

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   "If I write an O(1) algorithm, performance is solved."    │
│                                                             │
│        ╔═══════════════════════════════════════╗             │
│        ║                                     ║             │
│        ║   O(1) algorithm                    ║             │
│        ║   ┌─────────────────────┐           ║             │
│        ║   │  Fast in theory     │           ║             │
│        ║   │  ↓                  │           ║             │
│        ║   │  DB query: 2s       │           ║             │
│        ║   │  Disk I/O: 500ms    │           ║             │
│        ║   │  Serialization: 300ms│          ║             │
│        ║   │  Network: 800ms     │           ║             │
│        ║   └─────────────────────┘           ║             │
│        ║                                     ║             │
│        ║   TOTAL: ~3.6s  ← NOT fast          ║             │
│        ║                                     ║             │
│        ╚═══════════════════════════════════════╝             │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

Algorithmic complexity (Big-O) is only **one** factor in total response time. External constraints — database, disk,
network, serialization — often dominate the equation:

```
  Total Latency = Algorithm + DB + Disk + Network + Serialization + External Services
      │              │      │     │        │              │
      │              │      │     │        │              └── Third-party API calls
      │              │      │     │        └── JSON/XML payload size
      │              │      │     └── HDD vs SSD, spindle speed
      │              │      └── Index coverage, lock contention
      │              └── Query plan, full table scans
      └── Theoretical complexity (Big-O) — only part of the story
```

---

## 2. Database Indices — The Goldilocks Problem

```
                    ┌──────────────────────┐
                    │   INDEXES            │
                    │   (The Balance)      │
                    └──────────┬───────────┘
                               │
              ┌────────────────┼────────────────┐
              ▼                ▼                ▼
     ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
     │  TOO FEW     │  │  JUST RIGHT  │  │  TOO MANY    │
     │              │  │              │  │              │
     │  Full scans  │  │  Targeted    │  │  Insert/Update│
     │  Slow reads  │  │  queries     │  │  degradation  │
     │  Missing     │  │  Fast reads  │  │  Wasted disk  │
     │  opportunities│ │  Balanced    │  │  Cost per row │
     └──────────────┘  └──────────────┘  └──────────────┘
```

### Why indices are a cost, not free

```
  Index on column "email"
       │
       ├── ✓ SELECT WHERE email = '...' → O(log n) instead of O(n)
       │
       ├── ✗ INSERT into users table → must update index B-tree
       ├── ✗ UPDATE email field → must rebuild index entry
       ├── ✗ DELETE from users table → must remove index entry
       └── ✗ Disk space consumed → every index is a copy of the data
```

**Every index is a tax on writes.** The more indices, the slower inserts and updates become — and slower writes = higher
infrastructure cost.

### How to know you need an index

```
  ┌──────────────────────────────────────────────────────┐
  │                                                      │
  │  You CANNOT decide on indices in a vacuum.            │
  │                                                      │
  │  You need the ACTUAL queries that will run in PROD.   │
  │                                                      │
  │  Indices must be decided BEFORE deployment —          │
  │  not after, not during, but before.                   │
  │                                                      │
  └──────────────────────────────────────────────────────┘
```

Without concrete queries, adding indices is guesswork. Adding too many wastes resources; adding too few causes slow
reads. The only reliable approach: **extract queries from the codebase, analyze them, then add indices only for what the
application actually needs.**

---

## 3. Complex Algorithms — The Hidden Costs

Threads, file I/O, heavy computation — these are the algorithms that require engineering effort to optimize. But even
here, the hidden costs are often overlooked:

```
  ┌─────────────────────────────────────────────────────────────┐
  │              THE DEVELOPER'S PC vs. PRODUCTION               │
  ├─────────────────────────────────────────────────────────────┤
  │                                                             │
  │   DEVELOPER MACHINE                    PRODUCTION            │
  │   ───────────────                    ────────────            │
  │   SSD (NVMe)                         Shared storage          │
  │   32GB RAM                           Contended RAM           │
  │   No network hops                    Multiple network hops   │
  │   Single tenant                      Multi-tenant            │
  │   No other services competing        Other services on load  │
  │   Stable, warm caches                Cold starts possible    │
  │                                                             │
  │   Result: Your O(1) algorithm     Result: Unpredictable      │
  │   feels instant.                 latency spikes everywhere.  │
  │                                                             │
  └─────────────────────────────────────────────────────────────┘
```

### The production reality checklist

```
  Algorithm passes locally?
       │
       ├── ✓ Network speed — is the service you call within SLA?
       ├── ✓ Disk response — is the DB on SSD? Is it shared?
       ├── ✓ Concurrency — does the downstream service handle your load?
       ├── ✓ Connection pooling — are you fatiguing a connection pool?
       ├── ✓ Serialization cost — what's the payload size?
       └── ✓ Resource contention — what else shares this infrastructure?
```

---

## 4. Serialization — Choose the Right Protocol

Who consumes the data? External? Internal? Is there a shared contract? These questions determine the right serialization
format:

```
  ┌─────────────────────────────────────────────────────────────┐
  │              SERIALIZATION FORMAT COMPARISON                 │
  ├──────────────┬──────────┬─────────┬──────────┬──────────────┤
  │ Format       │ Speed    │ Size    │ Human    │ Use Case     │
  │              │          │         │ Readable │              │
  ├──────────────┼──────────┼─────────┼──────────┼──────────────┤
  │ JSON         │ Slow     │ Large   │ ✓ Yes    │ REST APIs,   │
  │              │          │         │          │ external     │
  ├──────────────┼──────────┼─────────┼──────────┼──────────────┤
  │ JSONB        │ Medium   │ Medium  │ Partial  │ PostgreSQL   │
  │              │          │         │          │ storage      │
  ├──────────────┼──────────┼─────────┼──────────┼──────────────┤
  │ FastJSON2    │ Fast     │ Medium  │ Partial  │ Spring Boot  │
  │              │          │         │          │ default      │
  ├──────────────┼──────────┼─────────┼──────────┼──────────────┤
  │ Protobuf     │ Very fast│ Small   │ ✗ No     │ gRPC, internal│
  │              │          │         │          │ communication│
  ├──────────────┼──────────┼─────────┼──────────┼──────────────┤
  │ Avro         │ Fast     │ Small   │ ✗ No     │ Data pipelines│
  │              │          │         │          │ (Kafka)      │
  ├──────────────┼──────────┼─────────┼──────────┼──────────────┤
  │ Kryo /     │ Very fast│ Small   │ ✗ No     │ In-memory    │
  │ Blackbird    │          │         │          │ caching      │
  └──────────────┴──────────┴─────────┴──────────┴──────────────┘
```

### Decision framework

```
  Who consumes the data?
       │
       ├── External (browsers, third parties)
       │    └── → JSON (universal compatibility)
       │
       ├── Internal (service-to-service)
       │    ├── Contract exists (protobuf schema)
       │    │    └── → Protobuf (fast, small, typed)
       │    └── No contract, high throughput
       │         └── → Avro or Kryo (fast, compact)
       │
       └── Self (same JVM, same process)
            └── → Kryo / Blackbird (fastest, zero schema)
```

**The right serialization format, applied per context, can improve system performance dramatically without major
architectural changes.**

---

## 5. Jackson Blackbird — Native Serialization

By default, Java serialization uses **reflection** — a slow, JIT-unfriendly path. Jackson Blackbird replaces reflection
with **native method handles**, giving you near-Kryo performance with zero code changes:

```
  ┌─────────────────────────────────────────────────────────────┐
  │              SERIALIZATION PATHS                             │
  ├─────────────────────────────────────────────────────────────┤
  │                                                             │
  │   STANDARD JACKSON                                          │
  │   ───────────────                                           │
  │                                                             │
  │   Java Object  →  Reflection  →  Byte Output                │
  │                    (slow)                                     │
  │                    │                                          │
  │                    ▼                                          │
  │              MethodHandle lookup per field                    │
  │              Not JIT-optimized                              │
  │                                                             │
  │                                                             │
  │   JACKSON + BLACKBIRD                                       │
  │   ─────────────────────                                     │
  │                                                             │
  │   Java Object  →  Bytecode-generated  →  Byte Output         │
  │                    (native)                                   │
  │                    │                                          │
  │                    ▼                                          │
  │              Direct field access (no reflection)              │
  │              JIT-optimized, inlinable                         │
  │                                                             │
  └─────────────────────────────────────────────────────────────┘
```

### How to enable it

**Step 1** — Add the dependency to `pom.xml`:

```xml

<dependency>
    <groupId>com.fasterxml.jackson.module</groupId>
    <artifactId>jackson-module-blackbird</artifactId>
    <version>2.18.2</version>
</dependency>
```

**Step 2** — Configure the ObjectMapper bean:

```java

@Bean
public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new BlackbirdModule());
    return mapper;
}
```

That's it. No code changes to your DTOs. No annotations. No migration. Just a bean configuration and a dependency — and
serialization performance improves significantly.

---

## 6. Extracting SQL/JPQL Queries via Spring Boot AOT

Spring Boot's **AOT (Ahead-Of-Time)** processing analyzes your repositories at compile time. As part of this process,
the persistence engine extracts and structures all query metadata (derived methods, native queries, projections) into
JSON files inside the build directory.

### AOT Architecture Flow

```
  [ Java Source Code ]
    ├── UsuarioRepository.java (Interfaces, @Query, Projections)
    └── ...
          │
          ▼ (Compilation / Build Phase)
  [ Command: process-aot ]
          │
          ▼ (Spring Data AOT Processor)
  [ Metadata JSON Files ]
    └── Location: target/classes/META-INF/spring/
          │
          ▼ (Execute the Script)
  [ Clean Query Catalog in Console ]
```

### Example metadata JSON structure

When you run AOT compilation, Spring Data generates descriptor files under `target/classes/META-INF/spring/` (Maven) or
`build/resources/main/META-INF/spring/` (Gradle):

```json
[
  {
    "repository": "com.example.demo.repository.UsuarioRepository",
    "queries": [
      {
        "method": "findByEmail",
        "type": "DERIVED_QUERY_METHOD",
        "queryLanguage": "JPQL",
        "expression": "select u from Usuario u where u.email = ?1",
        "returnType": "com.example.demo.dto.UsuarioProjection"
      },
      {
        "method": "buscarUsuariosActivosNativo",
        "type": "NATIVE_QUERY_METHOD",
        "queryLanguage": "SQL",
        "expression": "SELECT * FROM usuarios WHERE activo = true AND pais_id = :paisId",
        "returnType": "java.util.List"
      }
    ]
  }
]
```

### Automated extraction script: `extraer_consultas_aot.py`

Save this script at the root of your Spring Boot project to consolidate all query information automatically.

```python
import os
import json
import glob

# Standard paths depending on the build tool
MAVEN_PATH = "target/classes/META-INF/spring/"
GRADLE_PATH = "build/resources/main/META-INF/spring/"


def buscar_ruta_metadata():
    if os.path.exists(MAVEN_PATH):
        return MAVEN_PATH
    elif os.path.exists(GRADLE_PATH):
        return GRADLE_PATH
    return None


def generar_reporte():
    ruta_base = buscar_ruta_metadata()
    if not ruta_base:
        print("[-] Error: No META-INF/spring/ folder found. "
              "Make sure to run AOT compilation first.")
        print("    Use: 'mvn spring-boot:process-aot' or './gradlew processAot'")
        return

    # Find all generated metadata JSON files
    archivos_json = glob.glob(os.path.join(ruta_base, "*.json"))

    print("\n" + "=" * 60)
    print("      PRECOMPILED QUERIES REPORT (SPRING AOT)")
    print("=" * 60)

    queries_found = 0

    for ruta_archivo in archivos_json:
        try:
            with open(ruta_archivo, 'r', encoding='utf-8') as archivo:
                datos = json.load(archivo)

                # Adapt to iterate over repository metadata structure
                if isinstance(datos, list):
                    for item in datos:
                        if "repository" in item and "queries" in item:
                            print(f"\n[Repository]: {item['repository']}")
                            print("-" * 50)
                            for query in item["queries"]:
                                queries_found += 1
                                print(f"  • Method:      {query.get('method')}")
                                print(f"    Type:        {query.get('type')}")
                                print(f"    Language:    {query.get('queryLanguage')}")
                                print(f"    Return:      {query.get('returnType')}")
                                print(f"    Query:       {query.get('expression').strip()}")
                                print()
        except (json.JSONDecodeError, KeyError, TypeError):
            # Skip JSON files belonging to other AOT configurations
            continue

    if queries_found == 0:
        print("\n[!] No explicit query mappings found in processed JSONs.")
    else:
        print(f"\n[+] Process complete. {queries_found} queries listed.")


if __name__ == "__main__":
    generar_reporte()
```

### Execution steps

Follow these three commands in your terminal:

**1. Clean the build directory:**

```bash
mvn clean              # Maven
./gradlew clean        # Gradle
```

**2. Run Spring Boot AOT processor:**

```bash
mvn spring-boot:process-aot    # Maven
./gradlew processAot           # Gradle
```

**3. Run the extraction script:**

```bash
python extraer_consultas_aot.py
```

### Expected output

```
============================================================
      PRECOMPILED QUERIES REPORT (SPRING AOT)
============================================================

[Repository]: com.example.demo.repository.UsuarioRepository
--------------------------------------------------
  • Method:      findByEmail
    Type:        DERIVED_QUERY_METHOD
    Language:    JPQL
    Return:      com.example.demo.dto.UsuarioProjection
    Query:       select u from Usuario u where u.email = ?1

  • Method:      buscarUsuariosActivosNativo
    Type:        NATIVE_QUERY_METHOD
    Language:    SQL
    Return:      java.util.List
    Query:       SELECT * FROM usuarios WHERE activo = true AND pais_id = :paisId


[+] Process complete. 2 queries listed.
```

### How this connects to performance

```
  AOT Query Extraction
       │
       ├── 1. Discover all queries in the codebase
       │
       ├── 2. Analyze each query's complexity and access patterns
       │
       ├── 3. Identify missing indices (queries without index support)
       │
       ├── 4. Identify redundant indices (indices with no matching queries)
       │
       └── 5. Deploy with confidence — indices match actual workload
```

This is the bridge between **code** and **database performance**: you can no longer forget about indices because you
"didn't know the queries." They are now visible, auditable, and actionable before production deployment.

---

## 7. Performance Optimization Summary

```
  ┌─────────────────────────────────────────────────────────────┐
  │                    PERFORMANCE HIERARCHY                     │
  ├─────────────────────────────────────────────────────────────┤
  │                                                             │
  │  Level 1: ALGORITHM                                         │
  │  ────────────                                               │
  │  Big-O matters, but it's only the foundation.               │
  │                                                             │
  │  Level 2: DATABASE                                            │
  │  ──────────                                                 │
  │  Indices based on actual queries (AOT extraction).          │
  │  Query plan optimization.                                   │
  │                                                             │
  │  Level 3: SERIALIZATION                                     │
  │  ────────────                                               │
  │  Right format per context (JSON, Protobuf, Blackbird).      │
  │  Jackson Blackbird for zero-effort JVM-internal gains.      │
  │                                                             │
  │  Level 4: INFRASTRUCTURE                                    │
  │  ─────────────                                              │
  │  Network, disk, connection pools, external service SLAs.    │
  │                                                             │
  │  Level 5: OBSERVABILITY                                     │
  │  ──────────────                                             │
  │  Micrometer, AOT metadata, APM — measure before you fix.   │
  │                                                             │
  └─────────────────────────────────────────────────────────────┘
```

> **Bottom line:** Performance is a system property, not an algorithm property. Optimize the whole stack — algorithm,
> database, serialization, infrastructure — and use measurable data (AOT query extraction, benchmarks, APM) to guide
> every
> decision.
