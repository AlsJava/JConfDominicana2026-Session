# Mantenibilidad y Documentación en Software

## El comienzo del caos

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   CÓDIGO SIN DOCUMENTACIÓN                                  │
│                                                             │
│        ╔═══════════════════════════════════════╗             │
│        ║                                     ║             │
│        ║   ZONA OSCURA                       ║             │
│        ║   ┌─────────────────────┐           ║             │
│        ║   │  "No toques esto"  │           ║             │
│        ║   │  "Si funciona,     │           ║             │
│        ║   │   no lo arregles"  │           ║             │
│        ║   └─────────────────────┘           ║             │
│        ║                                     ║             │
│        ║   Nadie modifica → El negocio      ║             │
│        ║   depende de código que nadie       ║             │
│        ║   entiende                          ║             │
│        ║                                     ║             │
│        ╚═══════════════════════════════════════╝             │
│                                                             │
│   Resultado: CAOS                                           │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

> **La causa raíz no es la complejidad del código, es la ausencia de documentación.**
> El código con demasiada ingeniería y sin documentación correspondiente es
> terreno fértil para el caos.

La pregunta correcta no es *"¿documentamos?"* — es **"¿documentamos correctamente?"**.

---

## Los 5 niveles de documentación

```
                    ┌──────────────────────┐
                    │   DOCUMENTACIÓN      │
                    │   DE ARQUITECTURA    │
                    │   (Nivel 1)          │
                    │   "El bosque"        │
                    └──────────┬───────────┘
                               │
                    ┌──────────▼───────────┐
                    │   DOCUMENTACIÓN      │
                    │   DE DESARROLLO      │
                    │   (Nivel 2)          │
                    │   "Los árboles"      │
                    └──────────┬───────────┘
                               │
                    ┌──────────▼───────────┐
                    │   DOCUMENTACIÓN      │
                    │   DE USUARIO         │
                    │   (Nivel 3)          │
                    │   "El manual"        │
                    └──────────┬───────────┘
                               │
                    ┌──────────▼───────────┐
                    │   DOCUMENTACIÓN      │
                    │   DE API             │
                    │   (Nivel 4)          │
                    │   "El puente"        │
                    └──────────┬───────────┘
                               │
                    ┌──────────▼───────────┐
                    │   DOCUMENTACIÓN      │
                    │   DE CÓDIGO          │
                    │   (Nivel 5)          │
                    │   "Las raíces"      │
                    └──────────────────────┘
```

### Flujo de conocimiento entre niveles

```
  Negocio ──→ [PO / Product Owner] ──→ Documentación de Desarrollo
       │                                      │
       │                                      ▼
       │                              Casos de uso, features, interacciones
       │
       ▼
  Desarrolladores ──→ [Arquitecto] ──→ Documentación de Arquitectura
       │                                    │
       │                                    ▼
       │                            Diagramas de flujo, colas, BDs, servicios
       │
       ▼
  Integradores ──→ [Herramientas] ──→ Documentación de API (Swagger, etc.)
       │
       ▼
  Mantenedores ──→ [Desarrolladores] ──→ Documentación de Código (Javadoc,
                                              comentarios, MD junto al código)
```

---

## Desglose de cada nivel

### Nivel 1 — Documentación de Arquitectura

**¿Qué es?** Una representación de alto nivel de cómo está diseñada la aplicación.

```
                    ┌──────────┐
                    │  CLIENTE │
                    └────┬─────┘
                         │ HTTP
                         ▼
                ┌────────────────┐
                │   API GATEWAY  │
                └────────┬───────┘
                         │
              ┌──────────┼──────────┐
              ▼                     ▼
     ┌────────────────┐    ┌────────────────┐
     │   SERVICIO A   │    │   SERVICIO B   │
     └───────┬────────┘    └───────┬────────┘
             │                     │
             ▼                     ▼
     ┌──────────────┐      ┌──────────────┐
     │   BD A       │      │   BD B       │
     └──────────────┘      └──────────────┘

     ┌──────────────────────────────────────┐
     │           COLA (Kafka)                │
     │   Servicio A ──→ Cola ──→ Servicio B  │
     └──────────────────────────────────────┘
```

**Contenido típico:**

- Diagramas de componentes y sus interacciones
- Representación visual de colas (Kafka, RabbitMQ)
- Esquema de base de datos a nivel de entidades
- Flujo de solicitud desde el cliente hasta el backend
- **Ignora** detalles de bajo nivel como redes, hardware — pero puede incluirlos si se considera útil para el onboarding de nuevos desarrolladores

**Audiencia:** Arquitectos, Tech Leads, nuevos desarrolladores (vista de gran alcance).

---

### Nivel 2 — Documentación de Desarrollo

**¿Quién la genera?** El Product Owner / Analista de Negocio.

```
┌─────────────────────────────────────────────────────────────┐
│                  CASO DE USO: "Crear Pedido"                │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Actor: Cliente                                             │
│  Precondición: Usuario autenticado                          │
│                                                             │
│  Flujo principal:                                           │
│  1. Cliente navega el catálogo                              │
│  2. Selecciona productos y los añade al carrito             │
│  3. Revisa el resumen del pedido                            │
│  4. Confirma la compra                                      │
│  5. El sistema crea el pedido y envía notificación         │
│                                                             │
│  Flujo alternativo:                                         │
│  2a. Producto sin stock → mostrar mensaje de disponibilidad │
│  4a. Pago rechazado → mostrar error y permitir reintentar   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Contenido típico:**

- Casos de uso con actores y flujos
- Interacciones del sistema
- Features y comportamientos de negocio
- **No contiene detalle técnico** — es la acumulación de escenarios

**Audiencia:** Product Owners, Stakeholders, QA, Analistas de Negocio.

---

### Nivel 3 — Documentación de Usuario

**¿Qué es?** El manual semi-técnico de cómo un usuario opera la aplicación.

```
┌─────────────────────────────────────────────────────────────┐
│  PASO 1: Iniciar sesión                                     │
│  ┌─────────────────────────────────────────────────────┐    │
│  │                                                     │    │
│  │   ┌───────────────────────────────────────┐         │    │
│  │   │  [ Usuario: user@example.com ]        │         │    │
│  │   │  [ Contraseña: ******              ]  │         │    │
│  │   │  [   INICIAR SESIÓN            ]      │         │    │
│  │   └───────────────────────────────────────┘         │    │
│  │                                                     │    │
│  └─────────────────────────────────────────────────────┘    │
│                                                             │
│  PASO 2: Habilitar notificaciones                           │
│  Configuración → Perfil → Notificaciones → Activar ON       │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

**Contenido típico:**

- Instrucciones paso a paso (como las instrucciones de una botella de shampoo)
- Capturas de pantalla con anotaciones
- Cómo habilitar configuraciones y elementos de uso diario
- Solución básica de problemas

**Audiencia:** Usuarios finales, equipo de soporte, clientes.

---

### Nivel 4 — Documentación de API

**¿Qué es?** Documentación técnica auto-generada, de desarrollador a desarrollador.

```
GET /api/v1/orders/{id}
┌─────────────────────────────────────────────────────────────┐
│  Headers:                                                   │
│    Authorization: Bearer <token>                            │
│    Content-Type: application/json                           │
│                                                             │
│  Respuesta 200 OK:                                          │
│  ┌─────────────────────────────────────────────────────┐   │
│  │ {                                                    │   │
│  │   "id": 12345,                                       │   │
│  │   "customer": "John Doe",                            │   │
│  │   "status": "CONFIRMED",                             │   │
│  │   "total": 1500.00,                                  │   │
│  │   "items": [ ... ]                                   │   │
│  │ }                                                    │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘

Herramientas: Swagger / OpenAPI, Postman Collections, Insomnia
```

**Contenido típico:**

- Endpoints, métodos HTTP, parámetros
- Esquemas de solicitud/respuesta
- Códigos de error y sus significados
- Ejemplos de llamadas a la API

**Audiencia:** Desarrolladores externos, equipos de integración, frontend consumiendo backend.

---

### Nivel 5 — Documentación de Código

**¿Qué es?** La documentación de nivel más bajo. La que permite que un sistema sea mantenido a lo largo del tiempo.

```java
/**
 * Calcula el total del pedido aplicando descuentos por volumen.
 *
 * <p>Reglas de negocio:
 * <ul>
 *   <li>5+ unidades del mismo SKU → 5% de descuento</li>
 *   <li>20+ unidades → 10% de descuento</li>
 *   <li>50+ unidades → 15% de descuento (requiere aprobación de gerente)</li>
 * </ul>
 *
 * @param items lista de items del pedido (nunca null, nunca vacía)
 * @return total calculado con descuentos aplicados
 * @throws IllegalArgumentException si items es null o vacío
 * @see DiscountService#requiresApproval(int)
 */
public BigDecimal calculateTotal(List<OrderItem> items) {
    // ... implementación
}
```

**Contenido típico:**

- Javadoc con reglas de negocio explicadas
- Comentarios en lógica compleja (el "por qué", no el "qué")
- Archivos Markdown/HTML junto al código fuente
- Ejemplos de llamadas a la API
- Pruebas y resultados concretos en reportes estructurados

**Audiencia:** Desarrolladores que mantienen el código, nuevos miembros del equipo.

---

## Tabla comparativa

```
┌──────────────────────┬──────────────┬───────────────┬──────────────────────┐
│ Tipo                 │ Nivel        │ Generado por  │ Audiencia principal  │
├──────────────────────┼──────────────┼───────────────┼──────────────────────┤
│ Arquitectura         │ Alto (1)     │ Arquitecto    │ Devs, Tech Leads     │
│ Desarrollo           │ Medio (2)    │ Product Owner │ PO, QA, Stakeholders │
│ Usuario              │ Medio (3)    │ Redactor Tech │ Usuarios finales     │
│ API                  │ Bajo (4)     │ Herramientas  │ Desarrolladores ext. │
│ Código               │ Más bajo (5) │ Desarrollador │ Mantenedores de código│
└──────────────────────┴──────────────┴───────────────┴──────────────────────┘
```

---

## El problema de la dispersión

```
  Documentación dispersa:

  Wiki (Confluence)  ────→ Links a diagramas en Drive
                           │
  Repo README          ────→ Instrucciones básicas
                           │
  Javadoc en código      ────→ Detalles de implementación
                           │
  Swagger              ────→ Documentación de API
                           │
  Wiki interna         ────→ Decisiones técnicas (ADR)
                           │
  ┌──────────────────────────────────────────────┐
  │  RESULTADO: Links rotos, archivos perdidos,   │
  │  conocimiento que se evapora con el tiempo    │
  └──────────────────────────────────────────────┘
```

### La solución: documentación como código

```
  Documentación dentro del repositorio:

  resource/
  ├── MAINTAINABILITY.md          ← Esta guía
  ├── STRUCTURE.md                ← Estructura del proyecto
  ├── README.md                   │─→ Inicio rápido
  └── DEVELOPMENT.md              ← Guía de desarrollo

  docs/
  ├── architecture/               ← Diagramas y decisiones de diseño
  │   ├── system-overview.md
  │   └── data-flow.md
  ├── decisions/                  ← ADRs (Architecture Decision Records)
  │   └── 001-use-fastjson2.md
  └── api/                        ← Guías de integración
      └── getting-started.md

  Código fuente/
  ├── **/*.java                   ← Javadoc + comentarios
  └── **/README.md                ← Explicaciones a nivel de módulo

  VENTAJA: Todo vive en el repo → trazable, versionado, recuperable
```

---

## Documentación diseñada para IA

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   HERRAMIENTAS DE IA (Claude, Copilot, etc.)                │
│          │                                                  │
│          ▼                                                  │
│   LEE:                                                      │
│   ├── CLAUDE.md / AGENTS.md        ← Reglas y convenciones │
│   ├── Javadoc                          ← Lógica del código  │
│   ├── comentarios                       │─→ Decisiones     │
│   │                                    │  técnicas          │
│   ├── Archivos Markdown junto al     │─→ Contexto         │
│   │    código fuente                    adicional          │
│   └── Archivos de documentación        ← Arquitectura y    │
│                                            decisiones        │
│                                                             │
│   NECESITA:                                                 │
│   ├── Código limpio y bien comentado                        │
│   ├── Documentación consistente y actualizada               │
│   └── Estructura de repositorio predecible                  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

> **La documentación orientada a IA es real y será esencial.**
> Un esquema funcional donde la documentación del código es limpia y confiable
> permite que las herramientas de IA asistan efectivamente en el proceso de
> desarrollo y mantenimiento.

---

## Principios clave

```
  ┌─────────────────────────────────────────────────────────────┐
  │                                                             │
  │  1. DOCUMENTAR COMO CÓDIGO                                  │
  │     → Versionado, trazable, dentro del repositorio          │
  │                                                             │
  │  2. EL "POR QUÉ" > EL "QUÉ"                                │
  │     → El código dice qué hace; la documentación dice por qué│
  │                                                             │
  │  3. MANTENERLA ACTUALIZADA                                  │
  │     → La documentación desactualizada es peor que ninguna   │
  │                                                             │
  │  4. MULTI-AUDIENCIA                                         │
  │     → Diferentes niveles para diferentes lectores           │
  │                                                             │
  │  5. DISEÑAR PARA IA DESDE EL INICIO                         │
  │     → Código limpio + docs ordenados = mejor asistencia IA  │
  │                                                             │
  └─────────────────────────────────────────────────────────────┘
```
