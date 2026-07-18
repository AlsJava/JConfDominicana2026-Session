# Guía de Desarrollo — Arquitectura y Dinámica de Equipos

> Cómo organizas tu equipo determina cómo se organiza tu código. La arquitectura no es solo estructura de código — es
> el flujo de trabajo, el ritmo de colaboración y las decisiones que emergen de él.

---

## 1. Tamaño del equipo y sus implicaciones arquitecturales

El tamaño de un equipo de desarrollo es uno de los parámetros más consecuentes en las decisiones de arquitectura — sin
embargo casi siempre se pasa por alto. Los equipos se clasifican en tres categorías:

```
┌─────────────────────────────────────────────────────────────┐
│                  CLASIFICACIÓN POR TAMAÑO DE EQUIPO          │
├────────────┬────────────┬───────────────────────────────────┤
│  PEQUEÑO   │  MEDIANO   │  GRANDE                           │
│  ────────  │  ────────  │  ────────                         │
│  1–2 devs  │  3–5 devs  │  6+ devs                          │
│            │            │                                   │
│  · Program.  │  · Equipos │  · Múltiples squads               │
│    solitario │    pequeños│  · Equipos cross-funcionales      │
│  · Contexto  │  · Equipos │  · Límites basados en dominio     │
│    compartido│    cross-  │  · Contratos e interfaces       │
│  · Comunic.  │    funct.  │    formales                       │
│    informal│    equipos   │                                   │
└────────────┴────────────┴───────────────────────────────────┘

  (Excluye QA, DevOps, Producto y otros roles adyacentes)
```

### Qué demanda cada tamaño

| Dimensión           | Pequeño (1–2)             | Mediano (3–5)                          | Grande (6+)                             |
|---------------------|---------------------------|----------------------------------------|-----------------------------------------|
| **Comunicación**    | Verbal, sincrónica        | Mixta asíncrona/sincrónica, docs livianos| Estructurada, documentada, formalizada  |
| **Límites de código**| Difusos, propiedad compartida| Límites claros de módulo, convenciones| Interfaces estrictas, contratos         |
| **Velocidad de decisión**| Instantánea          | Rápida pero necesita alineación        | Más lenta, requiere gobernanza          |
| **Riesgo**          | Factor bus = 1            | Moderado                               | Alta sobrecarga de coordinación         |

> **Idea clave:** La arquitectura debe coincidir con la topología del equipo. Una arquitectura para equipo grande
> (contratos estrictos, límites modulares) funciona para un equipo pequeño también — pero lo contrario rara vez es cierto.

---

## 2. Los tres paradigmas de arquitectura

Aunque existen innumerables sub-categorías y patrones híbridos, todas las arquitecturas de aplicaciones se pueden agrupar
en tres paradigmas primarios:

```
                        ┌──────────────────────┐
                        │   DECISIONES DE      │
                        │   ARQUITECTURA       │
                        └──────────┬───────────┘
                                   │
              ┌────────────────────┼────────────────────┐
              │                    │                    │
     ┌────────▼────────┐  ┌───────▼────────┐  ┌───────▼────────┐
     │  HEXAGONAL      │  │  MICROSERVICIOS│  │  TRADICIONAL   │
     │  (Puertos y     │  │  (Servicios    │  │  (MVC / MVP /  │
     │   Adaptadores)  │  │   Distribuidos)│  │   Capas)       │
     └────────┬────────┘  └───────┬────────┘  └───────┬────────┘
              │                    │                    │
   Monolito    │                    │  Monolítico        │
   modular     │                    │  (o por capas)     │
              │                    │                    │
   · Dominio-  │  · Servicios      │  · Controller →    │
     centrado  │    desplegables   │    Service →       │
   · Aislamiento│  · Duplicación   │    Repository      │
     de interfaces│  por red       │  · DB compartida   │
   · Testeable  │  · Alto costo de │  · Deploy compartido│
     en          │  operaciones     │                    │
     aislamiento│  · Experiencia   │                    │
              │    del equipo      │                    │
   Ejemplo:    │  Ejemplo:          │  Ejemplo:          │
   ERP, Gestión │  E-commerce,       │  Herramientas      │
   deportiva    │  Plataformas SaaS  │  internas, apps CRUD│
```

### ¿Por qué estos tres?

Toda arquitectura existe en un espectro entre **acoplamiento** e **independencia de despliegue**. Estos tres paradigmas
representan las posiciones dominantes en ese espectro:

```
  Acoplamiento ──────────────────────────────────────────────► Acoplamiento flojo
       │                                                    │
       ▼                                                    ▼
  ┌──────────┐                                      ┌──────────────┐
  │ TRADICIONAL│                                    │ MICROSERVICIOS│
  │ MVC/Capas │                                    │ Distribuidos  │
  └──────────┘                                      └──────────────┘
       │                                                    │
       └──────────┬──────────────────────┬──────────────────┘
                  │                      │
                  ▼                      ▼
            ┌───────────┐        ┌───────────────┐
            │ HEXAGONAL │        │ MICROSERVICIOS│
            │ (Monolito  │        │ (Servicios    │
            │  Modular)  │        │  Distribuidos)│
            └───────────┘        └───────────────┘
                  │
                  │  Hexagonal es la versión "consciente" de
                  │  un monolito — mismo deploy, pero con
                  │  límites tipo microservicio internamente.
                  │
                  ▼
            Monolito Modular
            (desplegable como una unidad,
             estructurado como muchos)
```

---

## 3. Marco de decisión — Los parámetros ocultos

La mayoría de las decisiones de arquitectura se toman basándose en tendencias tecnológicas, preferencia personal o lo
que el equipo ha usado antes. Pero hay parámetros que deberían dirigir la decisión — parámetros que a menudo se omiten
de la conversación:

```
                    ┌─────────────────────────┐
                    │   QUÉ DIRIGE TU        │
                    │  ELEGENCIA ARQUITECT.  │
                    └────────────┬────────────┘
                                 │
              ┌──────────────────┼──────────────────┐
              │                  │                  │
              ▼                  ▼                  ▼
     ┌─────────────────┐ ┌──────────────┐ ┌─────────────────┐
     │ FACTORES DE    │ │ FACTORES     │ │ FACTORES        │
     │ EQUIPO         │ │ DE NEGOCIO   │ │ OPERACIONALES   │
     ├─────────────────┤ ├──────────────┤ ├─────────────────┤
     │ · Tamaño del    │ │ · Complej. │ │ · Frecuencia de │
     │   equipo        │ │   del dominio│ │   despliegue    │
     │ · Experiencia   │ │ · Creci-    │ │ · Trayectoria   │
     │ · Patrones de   │ │   miento    │ │   del despliegue│
     │   comunicación  │ │ · Tiempo al │ │ · Necesidades  │
     │ · Roles         │ │   mercado   │ │   de observab.  │
     │   disponibles   │ │              │ │ · Madurez DevOps│
     │   (QA, DevOps)  │ │              │ │   del equipo    │
     └─────────────────┘ └──────────────┘ └─────────────────┘
```

### Los parámetros que la mayoría de equipos ignora

| Parámetro                 | Qué preguntar                              | Por qué importa                                     |
|---------------------------|--------------------------------------------|-----------------------------------------------------|
| **Tamaño y estabilidad**  | ¿Cuántos devs? ¿Se quedarán?               | El factor bus determina la rigidez de los límites   |
| **Volumen de transacciones** | ¿Cuántas transacciones/día?              | El overhead de microservicios solo se justifica a escala |
| **Frecuencia de despliegue** | ¿Con qué frecuencia despliegas?          | La desplegabilidad independiente es el valor de microservicios |
| **Complejidad del dominio** | ¿El dominio está bien entendido?           | Los dominios complejos se benefician de límites claros |
| **Experiencia del equipo** | ¿El equipo ha hecho sistemas distribuidos? | Los microservicios requieren madurez ops tipo SRE    |
| **Tiempo al mercado**     | ¿Qué tan rápido necesita el negocio features? | Tradicional es más rápido para empezar              |

---

## 4. Arquitectura Hexagonal (Puertos y Adaptadores)

### Cuándo usarla

> **Usa Hexagonal cuando:** Tienes un equipo bien estructurado, los cambios llegan como escenarios específicos, y la
> aplicación contiene módulos funcionales aislados que podrían *algún día* convertirse en servicios independientes.

### Concepto central

La arquitectura hexagonal (también llamada Puertos y Adaptadores) coloca el **dominio en el centro** y empuja todas las
dependencias hacia afuera:

```
                    ┌───────────────────────────┐
                    │                           │
        ┌───────────┤    ADAPTADORES DE        ├───────────┐
        │           │    PRESENTACIÓN           │           │
        │           │  (REST, CLI, gRPC)        │           │
        │           │                           │           │
        │           └───────────┬───────────────┘           │
        │                       │                           │
        │          ┌────────────▼────────────┐             │
        │          │     PUERTOS             │             │
        │          │  (Interfaces / Contratos)│            │
        │          └────────────┬────────────┘             │
        │                       │                           │
        │           ┌───────────▼───────────┐              │
        │           │                       │              │
        │           │    NÚCLEO DE DOMINIO  │              │
        │           │  (Entidades,          │              │
        │           │   Valores de Valor,   │              │
        │           │   Servicios de Dominio)│             │
        │           │                       │              │
        │           └───────────┬───────────┘              │
        │                       │                           │
        │           ┌───────────▼───────────┐              │
        │           │     ADAPTADORES       │              │
        │           │  (Infraestructura)     │              │
        │           │  (BD, MQ, APIs        │              │
        │           │   externas)            │              │
        │           └───────────┬───────────┘              │
        │                       │                           │
        └───────────────────────┴───────────────────────────┘
```

### Características clave

- **Regla de dependencia:** Las dependencias apuntan hacia adentro. El dominio no sabe nada de infraestructura.
- **Testeabilidad:** La lógica de dominio se puede probar sin framework, base de datos ni red.
- **Modularidad:** Cada puerto es un contrato — los módulos se pueden desarrollar independientemente.
- **Camino de extrapolación:** Un módulo hexagonal puede convertirse en microservicio con mínimo refactor (solo añade un adaptador de red).

### Ideal para

- **Sistemas de gestión empresarial (ERP)** — dominios funcionales aislados (inventario, facturación, RRHH)
- **Gestión de equipos deportivos** — módulos distintos (programación, roster, análisis)
- **Cualquier dominio donde las features están naturalmente aisladas** y el equipo valora la testeabilidad

### Ejemplo real en este proyecto

Este repositorio implementa una arquitectura basada en CQRS que se alinea estrechamente con los principios hexagonales:

```
  src/main/java/org/alsjava/sessions/
  ├── command/                          ← Capa de aplicación (puertos)
  │   ├── Sum2NumbersCommand.java       ← Comando (puerto de entrada)
  │   └── handler/
  │       └── Sum2NumbersCommandHandler.java  ← Handler (adaptador)
  ├── pattern/command/                  ← Framework (definiciones de puerto)
  │   ├── Command.java                  ← Comando abstracto
  │   ├── CommandBus.java               ← Despachador central
  │   └── CommandHandler.java           ← Interfaz de handler
  ├── model/                            ← Capa de dominio (núcleo)
  │   ├── DataDto.java
  │   └── exception/                    ← Excepciones de dominio
  └── controller/                       ← Adaptador de presentación
      └── ExampleController.java        ← Adaptador REST
```

---

## 5. Arquitectura de Microservicios

### Cuándo usarla

> **Usa Microservicios cuando:** Tu aplicación necesita escalar masivamente, manejar millones de transacciones por día, y
> tu equipo tiene la madurez operativa para gestionar sistemas distribuidos. Si no estás alcanzando 1–2 millones de
> transacciones diarias, los microservicios son probablemente contraproducentes.

### Concepto central

Los microservicios descomponen una aplicación en servicios desplegables de forma independiente y débilmente acoplados:

```
                          ┌─────────────────────────────┐
                          │       API GATEWAY            │
                          │     (Enrutamiento, Auth)     │
                          └─────────────┬───────────────┘
                                        │
              ┌─────────────────────────┼─────────────────────────┐
              │                         │                         │
              ▼                         ▼                         ▼
     ┌────────────────┐        ┌────────────────┐        ┌────────────────┐
     │  SERVICIO DE   │        │  SERVICIO DE   │        │  SERV. DE      │
     │  USUARIOS      │        │  PEDIDOS       │        │  PAGO          │
     │                │        │                │        │                │
     │  ┌──────────┐  │        │  ┌──────────┐  │        │  ┌──────────┐  │
     │  │  Dominio │  │        │  │  Dominio │  │        │  │  Dominio │  │
     │  └────┬─────┘  │        │  └────┬─────┘  │        │  └────┬─────┘  │
     │       │        │        │       │        │        │       │        │
     │  ┌────▼─────┐  │        │  ┌────▼─────┐  │        │  ┌────▼─────┐  │
     │  │   BD     │  │        │  │   BD     │  │        │  │   BD     │  │
     │  └──────────┘  │        │  └──────────┘  │        │  └──────────┘  │
     └────────────────┘        └────────────────┘        └────────────────┘
          :8081                    :8082                     :8083
```

### Los costos ocultos

```
  ┌──────────────────────────────────────────────────────────────┐
  │           LOS MICROSERVICIOS: EL PRECIO DE LA FLEXIBILIDAD    │
  ├──────────────────┬───────────────────────────────────────────┤
  │ Costo            │ Impacto                                   │
  ├──────────────────┼───────────────────────────────────────────┤
  │ Latencia de red  │ Cada llamada inter-servicio es una llamada│
  │                  │ de red                                    │
  │ Consistencia de  │ Sin transacciones distribuidas (eventual) │
  │ datos            │                                           │
  │ Ops de despliegue│ N servicios × M instancias = N×M deploys  │
  │ Observabilidad   │ Trazabilidad distribuida, agregación de   │
  │                  │ logs                                      │
  │ Testing          │ Testing de integración entre servicios    │
  │ Sobrecarga de    │ Contratos de servicio, versionado,        │
  │ equipo           │ compatibilidad                            │
  └──────────────────┴───────────────────────────────────────────┘
```

### El umbral de escala

```
  Transacciones/Día
       │
   10M │                              ╱ Microservicios
       │                            ╱
   1M  │──────────────────────────╯──────────────────────►
       │              ╱ Hexagonal  │
       │            ╱  (monolito   │
     1K│──────────╯    modular)   │
       │        ╱                 │
       │      ╱                  │
       │    ╱                    │
     10│  ╱                     │
       │╱ Tradicional (MVC)      │
       └──────────────────────────────────────────────►
            1    5    10    50    100    500   Tamaño de equipo
                                   (desarrolladores)
```

> **Regla general:** Si tu aplicación no manejará 1–2 millones de transacciones por día, los microservicios añaden
> complejidad sin beneficio proporcional.

---

## 6. Arquitectura Tradicional (MVC / Capas)

### Cuándo usarla

> **Usa Tradicional cuando:** Necesitas velocidad al mercado, el dominio es sencillo, y tu equipo valora la simplicidad
> sobre la pureza teórica. MVC es la arquitectura más subestimada para monolitos — y puede ser la opción más escalable,
> mantenible y amigable para desarrolladores en equipos grandes.

### Concepto central

El enfoque clásico por capas separa las preocupaciones en capas verticales:

```
  ┌─────────────────────────────────────────────┐
  │            CAPA DE PRESENTACIÓN             │
  │      (Controladores, Vistas, Plantillas)     │
  ├─────────────────────────────────────────────┤
  │           SERVICIO / NEGOCIO                 │
  │        (Lógica de negocio, orquestación)     │
  ├─────────────────────────────────────────────┤
  │            CAPA DE ACCESO A DATOS            │
  │       (Repositorios, ORM, Consultas)         │
  ├─────────────────────────────────────────────┤
  │              BASE DE DATOS                   │
  └─────────────────────────────────────────────┘
```

### Por qué MVC puede ser la mejor opción para equipos grandes

```
  ┌─────────────────────────────────────────────────────────────┐
  │            POR QUÉ MVC FUNCIONA PARA EQUIPOS GRANDES         │
  ├─────────────────────────────────────────────────────────────┤
  │                                                             │
  │  1. BAJO CARGA COGNITIVA                                   │
  │     Todo desarrollador conoce MVC. Sin fricción de onboarding│
  │                                                             │
  │  2. LÍMITES DE PROPIEDAD CLAROS                             │
  │     Equipo frontend → Controladores/Vistas                  │
  │     Equipo backend  → Servicios                             │
  │     Equipo de datos → Repositorios                          │
  │                                                             │
  │  3. PATRONES AÑADEN FLEXIBILIDAD                            │
  │     Patrón Command → asignación de tareas y aislamiento     │
  │     CQRS           → separación lectura/escritura           │
  │     Repository     → abstracción de acceso a datos          │
  │                                                             │
  │  4. SIMPLICIDAD DE DESPLIEGUE                               │
  │     Un artifact, un deploy, un rollback.                    │
  │                                                             │
  │  5. LA ESCALABILIDAD ES SOBRE CALIDAD DE CÓDIGO, NO TOPOLOGÍA│
  │     Una app MVC bien estructurada escala mejor que una      │
  │     app de microservicios mal estructurada.                 │
  │                                                             │
  └─────────────────────────────────────────────────────────────┘
```

### MVC + Patrón Command + CQRS = Lo mejor de ambos mundos

La arquitectura monolítica más poderosa combina la simplicidad de MVC con los beneficios estructurales del patrón Command
y CQRS:

```
  ┌─────────────────────────────────────────────────────────────┐
  │                    FLUJO DE SOLICITUD                        │
  │                                                             │
  │  Solicitud HTTP                                             │
  │       │                                                     │
  │       ▼                                                     │
  │  ┌─────────────┐     ┌──────────────────┐                  │
  │  │ Controlador  │────▶│  Objeto Comando  │                  │
  │  │ (MVC)        │     │  (Command<T>)    │                  │
  │  └─────────────┘     └────────┬─────────┘                  │
  │                               │                             │
  │                               ▼                             │
  │                        ┌──────────────┐                     │
  │                        │  Bus de       │                     │
  │                        │  Comandos     │                     │
  │                        │  (Despachador)│                     │
  │                        └──────┬───────┘                     │
  │                               │                             │
  │              ┌────────────────┼────────────────┐            │
  │              │                │                │            │
  │              ▼                ▼                ▼            │
  │     ┌──────────────┐  ┌──────────────┐  ┌──────────────┐   │
  │     │ Handler A    │  │ Handler B    │  │ Handler C    │   │
  │     │ (Escritura)  │  │ (Escritura)  │  │ (Lectura)    │   │
  │     └──────┬───────┘  └──────┬───────┘  └──────┬───────┘   │
  │            │                 │                 │            │
  │            ▼                 ▼                 ▼            │
  │     ┌──────────────┐  ┌──────────────┐  ┌──────────────┐   │
  │     │ Repositorio  │  │ Repositorio  │  │ Modelo de    │   │
  │     │ (BD Escrit.) │  │ (BD Escrit.) │  │ Lectura      │   │
  │     │ (BD Escrit.) │  │ (BD Escrit.) │  │ (BD Lectura) │   │
  │     └──────────────┘  └──────────────┘  └──────────────┘   │
  │                                                             │
  └─────────────────────────────────────────────────────────────┘

  Beneficios:
  · Cada comando es una unidad de trabajo autocontenida
  · Fácil asignar tareas a desarrolladores individuales
  · Los modelos de lectura/escritura pueden evolucionar independientemente
  · La lógica de dominio se mantiene aislada de las preocupaciones HTTP
  · Las pruebas son sencillas (prueba el handler, no el controlador)
```

### Ejemplo real en este proyecto

Este repositorio ya implementa Command + CQRS dentro de una aplicación Spring Boot estilo MVC:

```
  Spring MVC Controller
       │
       ▼
  Sum2NumbersCommand (Objeto comando)
       │
       ▼
  CommandBus → Sum2NumbersCommandHandler
       │
       ▼
  Sum2NumbersResponse
       │
       ▼
  Respuesta HTTP (JSON)
```

Esto demuestra que no necesitas microservicios para obtener los beneficios de una arquitectura basada en comandos. Los mismos patrones que
hacen que los microservicios sean testeables y mantenibles son alcanzables dentro de una única unidad desplegable.

---

## 7. Matriz de decisión

Usa esta matriz como punto de partida (no como manual):

```
┌─────────────────────┬──────────────┬──────────────┬──────────────┐
│                     │  EQUIPO      │  EQUIPO      │  EQUIPO      │
│  Arquitectura       │  PEQUEÑO     │  MEDIANO     │  GRANDE      │
│                     │  (1–2 devs)  │  (3–5 devs)  │  (6+ devs)   │
├─────────────────────┼──────────────┼──────────────┼──────────────┤
│ Hexagonal           │  ✓ Si quieres│  ✓ Ideal    │  ✓ Excelente│
│                     │   aprenderla │             │             │
├─────────────────────┼──────────────┼──────────────┼──────────────┤
│ Microservicios       │  ✗ Excesivo │  ⚠ Solo si   │  ✓ Si lo    │
│                     │              │  el escalado │  necesitas   │
│                     │              │  es crítico  │             │
├─────────────────────┼──────────────┼──────────────┼──────────────┤
│ Tradicional (MVC)   │  ✓ Por       │  ✓ Buena    │  ✓ La mejor │
│ + Command/CQRS      │  defecto     │  opción     │  para la    │
│                     │              │              │  mayoría de │
│                     │              │              │  equipos    │
└─────────────────────┴──────────────┴──────────────┴──────────────┘

  ✓ Ideal    · Fuerte recomendación
  ⚠ Condicional  · Usar con precaución
  ✗ En contra  · Generalmente no recomendado
```

---

## 8. El mito de la arquitectura "correcta"

```
                    ┌─────────────────────┐
                    │                     │
                    │   "No existe        │
                    │    la arquitectura  │
                    │    perfecta."        │
                    │                     │
                    └──────────┬──────────┘
                               │
              ┌────────────────┼────────────────┐
              │                │                │
              ▼                ▼                ▼
     ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
     │ YAGNI         │  │ SOLID        │  │ KISS         │
     │               │  │              │  │              │
     │ Usa la        │  │ Los principios│  │ Manténlo    │
     │ cosa más      │  │ guían las    │  │ simple — la │
     │ sencilla que  │  │ decisiones   │  │ complejidad  │
     │ funciona para │  │ arquitecturas│  │ es el real   │
     │ tu equipo     │  │              │  │ enemigo      │
     └──────────────┘  └──────────────┘  └──────────────┘
```

La mejor arquitectura es la que tu equipo puede realmente mantener. Una app MVC simple con buenos patrones superará a
una arquitectura de microservicios mal implementada cada vez. Elige basándote en la realidad de tu equipo, no en las
charlas de conferencias.

---

## 9. Resumen

| Factor                    | Hexagonal             | Microservicios        | Tradicional + CQRS      |
|---------------------------|-----------------------|----------------------|-------------------------|
| **Mejor tamaño de equipo** | Cualquier (crece con el equipo) | 6+ con madurez SRE | Cualquier, especialmente grande |
| **Despliegue**            | Unidad única          | Servicios independientes | Unidad única        |
| **Curva de aprendizaje**  | Media                 | Empinada             | Baja                    |
| **Testeabilidad**         | Excelente             | Buena (si se hace bien) | Buena                |
| **Camino de escalabilidad**| → Microservicios     | Nativa               | Modular → Microservicios|
| **Sobrecarga operativa**  | Baja                  | Alta                 | Baja                    |
| **Tiempo a primera feature** | Media              | Lento                | Rápido                  |

> **Conclusión:** La arquitectura es un medio para un fin — permitir que tu equipo entregue valor consistentemente.
> Ajusta la arquitectura al tamaño, experiencia y requisitos de escala del negocio de tu equipo. En caso de duda,
> empieza con un monolito bien estructurado (MVC + Command/CQRS) y evoluciona desde ahí.
