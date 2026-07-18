# JConf Dominicana 2026 Session

[![Java](https://img.shields.io/badge/Java-25-red.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-green.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9.16-blue.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Charlista

### Aluis Marte

Ingeniero en sistemas y Computación con más de 12+ años de experiencia en sectores como lotería, casino, información
crediticia, aviación, entre otros.

Cat lover con 3 gatos y una hija llamada Zelda. Apasionado por el desarrollo en Java con Spring Boot y la AI — su uso,
manejo y configuración para AI Local.

[LinkedIn](https://www.linkedin.com/in/aluis-marte-5159b267)

---

## Charla

### Arquitectura, Ingeniería y Desarrollo de Microservicios con Spring Boot

---

#### Descripción

Damos por hechas muchas cosas cuando usamos términos como **Ingeniería o Arquitectura**, pero estos tienen propósitos
muy explícitos y visiones del negocio desde un entendimiento diferente. Hablar de una arquitectura de microservicios es
muy ambiguo, porque tenemos múltiples formas de la misma (por ejemplo: **orientada a eventos, Gateway, serverless,
mixta, etc.)**. Esto deja muchas preguntas, pero la información es la pieza fundamental y **te ayudaré a tomar
decisiones efectivas en los 3 niveles.**

Porque la arquitectura de microservicios llegó para quedarse, pero tiene retos importantes y con esta pequeña charla
busco que veas como ingeniero las soluciones de microservicios y no solo como un desarrollador, que solo usa
tecnologías sin un criterio de los elementos de relevancia para el negocio.

Cada solución tiene sus virtudes y problemas. Por lo que una **mala decisión** impacta fuertemente en ámbitos como:

- Desarrollo en equipos grandes o multi-equipo
- Rendimiento de la solución
- Costos operativos de la solución
- Mantenibilidad del código en el tiempo
- Complejidad del código para nuevos desarrolladores
- Y muchos otros aspectos más

Ahora, **¿qué implica la ingeniería en este punto?** Pues es, en pocas palabras, la construcción logística de la
arquitectura. Podemos hablar de microservicios por eventos, pero ¿con qué se hará? ¿Kafka?, ¿RabbitMQ?, ¿Cloud Native
Option? Y encima a esto, también debemos entender qué framework, técnica de implementación y/o patrón usaremos en la
solución afectarán todos los aspectos. Tenemos muchas decisiones técnicas que tomar y cada una de ellas tiene un peso
importante en la dirección que queremos llevar nuestra solución.

## Guías y Referencias

El repositorio incluye guías prácticas en la carpeta [`resource/`](resource/) que profundizan en los temas de la charla:

- [**DEVELOPMENT-es.md**](resource/DEVELOPMENT-es.md) — Paradigmas de arquitectura, dinámica de equipos y marcos de decisión
- [**AI-es.md**](resource/AI-es.md) — Uso pragmático de la IA en el desarrollo de software
- [**MAINTAINABILITY-es.md**](resource/MAINTAINABILITY-es.md) — Niveles de documentación y prácticas de mantenibilidad
- [**SOLUTION_PERFORMANCE-es.md**](resource/SOLUTION_PERFORMANCE-es.md) — Patrones de rendimiento más allá de Big-O

Consulta el [README de resource](resource/README-es.md) para el índice completo y orientación sobre qué guía leer.

---

## Inicio Rápido

### Compilar

```bash
mvn clean package
```

### Ejecutar

```bash
java --add-opens=java.base/java.lang.invoke=ALL-UNNAMED -jar target/ConfDominicana2026-Session-1.0.0.jar
```

> El flag `--add-opens` es necesario al ejecutar el jar directamente. Para `mvn spring-boot:run` y `mvn test`, está configurado en `pom.xml`.

### Ejecutar con Maven

```bash
mvn spring-boot:run
```

### Ejecutar Tests

```bash
mvn test
```
