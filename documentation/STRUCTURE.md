# Project Structure

## Top-level layout

```
ConfDominicana2026-Session/
├── .claude/ / .env / .env.example / .git* / .github/ / .idea/ / .junie/ / .mcp.json / .mvn/  # Tooling & config
├── AGENTS.md / CLAUDE.md            # AI agent guidelines (identical mirrors)
├── documentation/                   # Deep-dive docs
│   ├── DEPENDENCIES.md              # Dependency version management
│   └── STRUCTURE.md                 # This file
├── logs/                            # Application log files (Logback rolling)
│   └── ConfDominicana2026-Session/
│       └── output.log
├── LICENSE                          # Project license
├── pom.xml                          # Maven build (Spring Boot 4.1.0, Java 25)
├── README.md / README-es.md         # Project overview (English / Spanish)
└── src/
    ├── main/
    │   ├── java/org/alsjava/sessions/
    │   │   ├── JConfDominicana2026.java          # Main application entry point
    │   │   ├── configuration/                   # Spring configuration classes
    │   │   │   ├── ObjectMapperConfiguration.java  # Jackson JSON/XML builder customizers
    │   │   │   ├── RestConfiguration.java          # RestClient bean with redirect handling
    │   │   │   ├── CQRSConfiguration.java          # CQRS pattern configuration
    │   │   │   ├── ForyConfiguration.java          # Apache Fory serialization config
    │   │   │   ├── OpenAPIConfiguration.java       # Swagger/OpenAPI docs setup
    │   │   │   ├── RequestLoggingConfiguration.java # Request logging filter
    │   │   │   └── actuator/
    │   │   │       └── AppInstanceEndpoint.java    # Custom actuator endpoint
    │   │   ├── controller/
    │   │   │   └── ExampleController.java          # Example REST controller
    │   │   ├── service/
    │   │   │   └── ExampleService.java             # Business logic layer
    │   │   ├── command/                            # CQRS Command pattern
    │   │   │   ├── Sum2NumbersCommand.java
    │   │   │   └── handler/
    │   │   │       └── Sum2NumbersCommandHandler.java
    │   │   ├── pattern/
    │   │   │   └── command/                        # CQRS framework
    │   │   │       ├── Command.java
    │   │   │       ├── CommandBus.java
    │   │   │       ├── CommandHandler.java
    │   │   │       ├── CommandBusHandler.java
    │   │   │       ├── CommandEvent.java
    │   │   │       └── CommandProvider.java
    │   │   ├── model/
    │   │   │   ├── DataDto.java
    │   │   │   ├── UserDto.java
    │   │   │   ├── exception/                      # Custom exceptions
    │   │   │   │   ├── CommandTimeoutException.java
    │   │   │   │   ├── CommandHandlerNotFoundException.java
    │   │   │   │   ├── DuplicateCommandException.java
    │   │   │   │   ├── CommandNotInheritException.java
    │   │   │   │   └── InvalidCommandEventException.java
    │   │   │   ├── network/                        # Network request/response models
    │   │   │   │   ├── AbstractNetwork.java
    │   │   │   │   ├── AbstractRequest.java
    │   │   │   │   ├── AbstractResponse.java
    │   │   │   │   ├── APIError.java
    │   │   │   │   ├── request/
    │   │   │   │   │   └── Sum2NumbersRequest.java
    │   │   │   │   └── response/
    │   │   │   │       └── Sum2NumbersResponse.java
    │   │   │   └── fory/                           # Apache Fory models
    │   │   │       ├── DemoEnum.java
    │   │   │       └── Wrapper.java
    │   │   └── util/
    │   │       ├── ExceptionAdviceHandler.java     # Global exception handler
    │   │       └── ExceptionAdviceHandlerHelper.java
    │   └── resources/
    │       ├── application.yml                    # Main config (entry point, imports 7 YAMLs)
    │       └── configurations/
    │           ├── actuator-configuration.yml     # Actuator endpoints, health, loggers, env, configprops
    │           ├── app-configuration.yml          # Application-specific configuration
    │           ├── fory-configuration.yml         # Apache Fory serialization settings
    │           ├── logging-configuration.yml      # Logback rolling policy, log file path
    │           ├── server-configuration.yml       # Server port, compression, HTTP/2, Netty idle timeout
    │           ├── develop/
    │           │   └── dev-configuration.yml      # Development profile settings
    │           └── pattern/
    │               └── cqrs-configuration.yml     # CQRS pattern configuration
    └── test/
        ├── java/org/alsjava/sessions/
        │   ├── ForyTest.java                      # Apache Fory serialization tests
        │   ├── configuration/
        │   │   └── ObjectMapperConfigurationTest.java
        │   └── test/
        │       ├── BaseTest.java                  # Abstract base — shared annotations, tags, profiles
        │       ├── UnitTest.java                  # Non-Spring unit tests (manual JsonMapper setup)
        │       ├── IntegrationTest.java           # Spring context integration tests (@SpringBootTest)
        │       └── extension/
        │           └── TimeExtension.java         # JUnit 5 extension — measures and logs test execution time
        └── resources/
            └── application-test.yaml              # Test profile configuration
```

## Configuration hierarchy

`application.yml` is the entry point; it imports 7 YAML configuration files:

1. **develop/dev-configuration.yml** — Development profile settings
2. **pattern/cqrs-configuration.yml** — CQRS pattern configuration (command bus, handler scanning)
3. **actuator-configuration.yml** — Actuator endpoints, health, loggers, env, configprops, observations
4. **app-configuration.yml** — Application-specific configuration
5. **fory-configuration.yml** — Apache Fory serialization settings
6. **logging-configuration.yml** — Logback rolling policy, log file path
7. **server-configuration.yml** — Server compression, HTTP/2, Netty idle timeout, JSON mapper preference

All values use environment variable placeholders for flexibility.

## Application entry point

`JConfDominicana2026` is the main class. On startup it:

- Generates a random `APP_ID` and sets it as a system property
- Configures a `BufferingApplicationStartup` (size 2048) filtered to `spring.beans.instantiate` steps
- Launches the Spring Boot application context

## Configuration classes

| Class                                  | Purpose                                                                         |
|----------------------------------------|---------------------------------------------------------------------------------|
| `ObjectMapperConfiguration`            | Customizes `JsonMapper` and `XmlMapper` builders (date format, fail-on-unknown) |
| `RestClientConfiguration`                    | Provides a `RestClient` bean with HTTP redirect following enabled               |
| `CQRSConfiguration`                    | Configures the CQRS command bus and handler scanning                            |
| `ForyConfiguration`                    | Configures Apache Fory serialization framework                                  |
| `OpenAPIConfiguration`                 | Configures Swagger/OpenAPI documentation endpoints                              |
| `RequestLoggingConfiguration`          | Configures request logging filter                                               |
| `AppInstanceEndpoint`                  | Custom actuator endpoint for application instance information                   |

## Test architecture

The test package follows a three-tier hierarchy:

- **`BaseTest`** — Abstract base with JUnit 5 tags (`UNIT`, `INTEGRATION`), `@TestMethodOrder`,
  `@ActiveProfiles("test")`, and the `TimeExtension`
- **`UnitTest`** — Non-Spring unit tests; creates its own `JsonMapper` instance with customizers applied manually
- **`IntegrationTest`** — Spring context integration tests (`@SpringBootTest`); autowires the shared `JsonMapper` bean

Integration tests are gated by the property `test.integration.enabled=true` and skipped otherwise.

## CQRS Pattern Implementation

The project implements a Command Query Responsibility Segregation (CQRS) pattern:

- **`Command<T>`** — Base class for all commands
- **`CommandBus`** — Central dispatcher for routing commands to handlers
- **`CommandHandler`** — Interface for handling specific command types
- **`CommandBusHandler`** — Wrapper for command handler beans
- **`CommandProvider`** — Discovers and registers command handlers
- **`CommandEvent`** — Event class for command execution

The pattern separates command execution from query operations, promoting cleaner architecture for write operations.

## Build output

Maven produces a Spring Boot fat JAR in `target/`:

```
target/
└── ConfDominicana2026-Session-1.0.0.jar
```

## Tech Stack

### Runtime

| Component             | Technology  |
|-----------------------|-------------|
| JVM                   | Java 25     |
| Application Framework | Spring Boot |

### Build

| Component       | Technology                                            |
|-----------------|-------------------------------------------------------|
| Build Tool      | Maven 3.9.16                                          |
| Compiler Plugin | Maven Compiler Plugin (Lombok annotation processor)   |
| Boot Plugin     | Spring Boot Maven Plugin (AOT processing, build-info) |
| Wrapper         | Maven Wrapper 3.3.4 (`mvnw` / `mvnw.cmd`)             |

### Web & API

| Component   | Technology                     |
|-------------|--------------------------------|
| Web Runtime | Spring WebMVC                  |
| Validation  | Spring Boot Starter Validation |
| HTTP Client | Spring Boot Starter RestClient |

### JSON & Serialization

| Component        | Technology                    |
|------------------|-------------------------------|
| JSON Processor   | Jackson 3 (`tools.jackson.*`) |
| XML Support      | jackson-dataformat-xml        |
| Blackbird Module | jackson-module-blackbird      |

### Utilities

| Component       | Technology |
|-----------------|------------|
| Code Generation | Lombok     |

### Observability & Infrastructure

| Component | Technology                                                       |
|-----------|------------------------------------------------------------------|
| Metrics   | Spring Boot Starter Micrometer Metrics                           |
| Actuator  | Spring Boot Starter Actuator (health, env, configprops, loggers) |
| Logging   | Logback (via spring-boot-starter-logging)                        |
| AOP       | Spring Boot Starter AspectJ                                      |

### Dependency Management

Version management methodology and update commands in [DEPENDENCIES.md](DEPENDENCIES.md).
