# IA en el Desarrollo de Software: Una Guía Pragmática

> La IA está aquí para quedarse. Pero con ella vienen nuevos desafíos que exponen la vacuidad de nuestra documentación.
> La IA es una herramienta, no un reemplazo del desarrollador. Creer que la IA es inteligente,
> que toma decisiones o puede decidir elementos arquitectónicos nos convierte en recursos
> desprovistos de sentido común — y ese camino lleva al desastre.

---

## La Verdad Fundamental

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   DEBES ENTENDER ESTO — SIN EXCEPCIONES:                    │
│                                                             │
│   ┌─────────────────────────────────────────────────────┐   │
│   │                                                     │   │
│   │   "La IA es una HERRAMIENTA.                        │   │
│   │    NO razona.                                       │   │
│   │    NO tiene sentido común.                          │   │
│   │    NO es ordenada.                                  │   │
│   │    NO toma decisiones."                             │   │
│   │                                                     │   │
│   └─────────────────────────────────────────────────────┘   │
│                                                             │
│   Si no internalizas esto, la IA se convertirá en:          │
│                                                             │
│        ╔═══════════════════════════════════════╗             │
│        ║                                     ║             │
│        ║   AGUJERO NEGRO ACELERADO de        ║             │
│        ║   conocimiento — haciendo tu código ║             │
│        ║   INMANTENIBLE                      ║             │
│        ║                                     ║             │
│        ╚═══════════════════════════════════════╝             │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

> **Por qué importa:** La IA es, en pocas palabras, un **modelo probabilístico**. Opera bajo aleatoriedad.
> No tomar decisiones arquitectónicas de forma consciente creará un desastre hermoso y costoso.

---

## Qué es la IA — y qué no es

```
┌─────────────────────────────────────────────────────────────┐
│               QUÉ ES LA IA             │  QUÉ NO ES LA IA  │
├────────────────────────────────────────┼────────────────────┤
│  Un modelo probabilístico              │  Un agente que    │
│                                        │    razona         │
│  Un emparejador de patrones a escala   │  Un arquitecto    │
│  Un generador rápido de documentación  │  Un tomador de    │
│                                        │    decisiones     │
│  Un motor de sugerencias de código     │  Un filtro de     │
│                                        │    calidad        │
│  Un autocompletado contextual ++       │  Un reemplazo de  │
│                                        │    tu juicio      │
└────────────────────────────────────────┴────────────────────┘
```

### La naturaleza probabilística explicada

```
  Entrada del Usuario
       │
       ▼
  ┌─────────────────┐
  │   Motor de      │
  │   Modelo        │
  │   Probabilístico│
  │                 │
  │  Dado el        │
  │  contexto,      │
  │  predecir la    │
  │  respuesta      │
  │  más probable   │
  └────────┬────────┘
           │
           ▼
  ┌─────────────────┐     ┌─────────────────┐
  │  Salida A (85%) │     │  Salida B (10%) │
  │  "Se ve bien"   │     │  "En realidad..."│
  │  pero tiene      │     │  problemas      │
  │  bugs            │     │  sutiles        │
  └────────┬────────┘     └─────────────────┘
           │
           ▼
  ┌─────────────────────────────────────────┐
  │  RESULTADO: Se retorna la salida más    │
  │  probable — no necesariamente la       │
  │  CORRECTA. Sin juicio humano, la        │
  │  basura se ve funcional.                │
  └─────────────────────────────────────────┘
```

---

## Cómo usar la IA correctamente

### La base: qué debes hacer primero

La IA no puede construir sobre nada. Antes de invocarla, debes establecer:

```
┌─────────────────────────────────────────────────────────────┐
│            PREREQUISITOS ANTES DE USAR IA                    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  1. ASPECTOS TÉCNICOS DEL SOFTWARE                          │
│     → Framework, lenguaje, restricciones de versión         │
│                                                             │
│  2. ARQUITECTURA DEFINIDA                                   │
│     → Patrones en uso, límites de capas, convenciones de    │
│       nombrado                                              │
│                                                             │
│  3. PATRONES INICIALES IMPLEMENTADOS                         │
│     → Plantillas que la IA puede seguir consistentemente    │
│                                                             │
│  4. ESTRUCTURA DE DOCUMENTACIÓN ASCENDENTE                  │
│     → Un sistema de docs en capas que la IA pueda leer y    │
│       seguir                                                  │
│                                                             │
│  5. AGENTS.MD CREADO                                        │
│     → Comportamientos, reglas y restricciones que la IA     │
│       debe obedecer                                         │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### El flujo de trabajo: cómo encaja la IA en tu proceso

```
  ┌─────────────────────────────────────────────────────────────┐
  │            EL CICLO DE DESARROLLO ASISTIDO POR IA            │
  ├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌──────────┐     ┌──────────┐     ┌──────────┐            │
│  │  TÚ      │────▶│    IA    │────▶│  TÚ      │            │
│  │  PLANIF. │     │  EJECUTA │     │  REVISAS │            │
│  └──────────┘     └──────────┘     └──────────┘            │
│       │                                  │                  │
│       ▼                                  ▼                  │
│  ┌──────────┐                    ┌──────────┐              │
│  │  DEFINES │                    │  JUZGAS  │              │
│  │  QUÉ     │                    │  SI ES   │              │
│  │  CONSTRUIR│                   │  CORRECTO│              │
│  └──────────┘                    └──────────┘              │
│                                                             │
│  Tú piensas. La IA computa. Tú decides.                     │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Paso a paso: configurar la IA para tu proyecto

```
  Paso 1: Definir Arquitectura ──────────────────────────────►
  Paso 2: Implementar Patrones Iniciales ────────────────────►
  Paso 3: Crear Estructura de Documentación ────────────────►
  Paso 4: Escribir AGENTS.md con reglas ────────────────────►
  Paso 5: La IA asiste con documentación, ordenamiento,      │
          clasificación — de forma incremental              │
  Paso 6: SUPERVISIÓN ESTRICTA — la IA genera mucha basura  │
          que se ve funcional pero no lo es                 │
```

---

## El AGENTS.md: el manual de reglas de tu IA

```
┌─────────────────────────────────────────────────────────────┐
│                  ESTRUCTURA DEL AGENTS.md                   │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  # Guiando agentes de código                                │
│                                                             │
│  ## Reglas de Comportamiento                                │
│  Regla 1 — Pensar antes de codificar                       │
│  Regla 2 — Simplicidad primero                             │
│  Regla 3 — Cambios quirúrgicos                             │
│  Regla 4 — Ejecución orientada a metas                      │
│  ...                                                        │
│                                                             │
│  ## Referencias del Proyecto                                │
│  → Docs de arquitectura                                    │
│  → Guías de estructura                                     │
│  → Registros de decisiones                                 │
│                                                             │
│  ## Elementos Relevantes                                    │
│  → Convenciones de nombrado                                │
│  → Reglas de estructura de paquetes                        │
│  → Guías de estilo de comentarios                          │
│  → Qué evitar                                              │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

> **El AGENTS.md es el contrato entre tú y la IA.**
> Sin él, la IA opera en el vacío — y los vacíos producen basura.

---

## El problema de la supervisión

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   SALIDA DE IA QUE SE VE FUNCIONAL:                         │
│                                                             │
│   ┌─────────────────────────────────────────────────────┐   │
│   │                                                     │   │
│   │   ✅ Compila sin errores                            │   │
│   │   ✅ Pasa las pruebas existentes                    │   │
│   │   ✅ Sigue las convenciones de nombrado             │   │
│   │   ✅ Tiene comentarios Javadoc                      │   │
│   │   ✅ Usa los patrones correctos del framework       │   │
│   │                                                     │   │
│   │   PERO...                                           │   │
│   │                                                     │   │
│   │   ❌ La lógica de negocio es incorrecta             │   │
│   │   ❌ Faltan casos borde                             │   │
│   │   ❌ Se ignoraron consideraciones de seguridad      │   │
│   │   ❌ Se pasaron por alto implicaciones de rendimiento│  │
│   │   ❌ Nunca se consideró el "por qué"                │   │
│   │                                                     │   │
│   └─────────────────────────────────────────────────────┘   │
│                                                             │
│   ESTA ES LA BASURA QUE SE VE FUNCIONAL.                    │
│   La supervisión debe ser ESTRICTA.                         │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## Casos de uso de alto valor para la IA

### 1. Mejores commits

Commits bien organizados con mensajes descriptivos y estandarizados habilitan valor downstream:

```
  Commit malo: "fixed stuff"
       │
       ▼
  Nada útil se puede derivar de esto.

  Commit bueno: "fix(order): resolver null pointer cuando el descuento es cero"
       │
       ▼
  La IA puede generar changelogs, notas de versión e informes de impacto a partir de esto.
```

### 2. Mensajes de Pull Request

Descripciones de PR estandarizadas ayudan a todo el equipo y habilitan automatización:

```
┌─────────────────────────────────────────────────────────────┐
│  Título del PR: feat(payment): añadir integración con Stripe│
│                                                             │
│  ## Qué                                                     │
│  Se añadió el proveedor de pago Stripe al módulo de pagos.  │
│                                                             │
│  ## Por qué                                                 │
│  Requisito de negocio: soportar pagos con tarjeta de crédito│
│                                                             │
│  ## Cómo                                                    │
│  Se creó StripePaymentProvider implementando PaymentGateway.│
│  Se añadieron pruebas de integración para los flujos de     │
│  éxito y fallo.                                             │
│                                                             │
│  ## Impacto                                                 │
│  Nueva dependencia: stripe-java 24.x                        │
│  Módulos afectados: payment-core, payment-api               │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### 3. Reportes de versiones de dependencias

```
┌─────────────────────────────────────────────────────────────┐
│              CHECK DE SALUD DE DEPENDENCIAS                  │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Dependencia          │ Actual │ Última │ Nivel de riesgo   │
│  ────────────────────┼────────┼────────┼────────────────── │
│  Spring Boot          │ 3.2.0  │ 3.3.1  │ ⚠ Actualizar pronto│
│  Jackson              │ 2.16.0 │ 2.17.0 │ ⚠ Nuevas funciones│
│  FastJSON2            │ 2.0.42 │ 2.0.46 │ ✓ Seguro actualizar│
│  Driver PostgreSQL    │ 42.7.1 │ 42.7.3 │ ⚠ Parche seguridad│
│  JUnit 5              │ 5.10.1 │ 5.10.2 │ ✓ Corrección menor│
│                                                             │
│  La IA puede:                                               │
│  · Escanear todas las dependencias rápidamente              │
│  · Marcar las que tienen versiones más nuevas               │
│  · Resumir qué hay nuevo en cada versión                    │
│  · Destacar cambios incompatibles                           │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### 4. Generación de changelog a partir de commits

```
  Commits estandarizados (Conventional Commits)
       │
       ▼
  ┌─────────────────────┐
  │  La IA procesa el   │
  │  historial de       │
  │  commits            │
  └──────────┬──────────┘
             │
             ▼
  ┌─────────────────────────────────────────────┐
  │                                             │
  │  ## v1.2.0 — Expansión del módulo de pagos  │
  │                                             │
  │  ### Nuevas funcionalidades                 │
  │  · Añadido proveedor de pago Stripe         │
  │  · Añadido proveedor de pago PayPal         │
  │                                             │
  │  ### Correcciones                           │
  │  · Resuelto null pointer en cálculo de      │
  │    descuento                                │
  │  · Corregida condición de carrera en sync   │
  │    de pedidos                               │
  │                                             │
  │  ### Dependencias                           │
  │  · Actualizado Spring Boot 3.2.0 → 3.3.1  │
  │  · Añadido stripe-java 24.x                 │
  │                                             │
  └─────────────────────────────────────────────┘
```

---

## En qué destaca la IA vs qué requiere juicio humano

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   LA IA DESTACA EN:                  LOS HUMANO DEBEN HACER:│
│   ─────────────                  ──────────────              │
│                                                             │
│   · Organizar documentación        · Tomar decisiones      │
│   · Generar boilerplate             arquitectónicas         │
│   · Crear changelogs               · Juzgar la corrección  │
│   · Resumir cambios                 del código              │
│   · Encontrar actualizaciones de   · Entender el contexto  │
│     dependencias                    y restricciones de      │
│                                     negocio                 │
│   · Formatear y estructurar        · Detectar bugs sutiles │
│   · Redactar explicaciones         · Tomar decisiones de   │
│   · Clasificar elementos            trade-off               │
│                                    · Definir el "por qué"   │
│                                                             │
│   La IA es un multiplicador de fuerza para la organización  │
│   y la documentación. Pero codificar, calcular y           │
│   tomar decisiones arquitectónicas requieren configuración  │
│   humana y una persona con el conocimiento para juzgar si   │
│   lo que la IA produjo es realmente correcto.               │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## El costo de saltarse la base

```
  Sin base:                                Con base:
       │                                              │
       ▼                                              ▼
  ┌──────────────┐                          ┌──────────────────┐
  │ La IA genera │                          │ Tú defines reglas│
  │ código a      │                          │ en AGENTS.md     │
  │ ciegas        │                          │                  │
  │              │                          │ La IA sigue      │
  │ · Patrones    │                         │ convenciones     │
  │   inconsistent│                        │                  │
  │ · Arquitectura│                       │ El código está   │
  │   incorrecta  │                         │ documentado      │
  │ · Sin docs    │                          │                  │
  │ · Basura que  │                          │ Tú revisas       │
  │   se ve bien  │                          │ críticamente     │
  └──────┬───────┘                          └────────┬─────────┘
         │                                           │
         ▼                                           ▼
  ┌─────────────────────────────────────────────────────────┐
  │  RESULTADO: La deuda técnica se acumula rápidamente. El │
  │  código se vuelve irreconocible. El costo de            │
  │  mantenimiento excede el costo original de desarrollo.  │
  └─────────────────────────────────────────────────────────┘
```

---

## Principios clave

```
  ┌─────────────────────────────────────────────────────────────┐
  │                                                             │
  │  1. LA IA ES UNA HERRAMIENTA, NO UN DESARROLLADOR            │
  │     → Ella computa. Tú piensas.                             │
  │                                                             │
  │  2. BASE ANTES QUE IA                                        │
  │     → Definir arquitectura, patrones, docs ANTES de invocar│
  │       la IA                                                   │
  │                                                             │
  │  3. EL AGENTS.md ES TU CONTRATO                              │
  │     → Definir comportamientos, reglas y restricciones       │
  │       explícitamente                                        │
  │                                                             │
  │  4. LA SUPERVISIÓN ES NO NEGOCIABLE                          │
  │     → La IA genera basura que se ve funcional. Juzgar      │
  │       todo lo que produce                                   │
  │                                                             │
  │  5. ESTANDARIZAR COMMITS Y PRs                               │
  │     → Commits convencionales + PRs descriptivos habilitan  │
  │       changelogs, reportes y automatización                 │
  │                                                             │
  │  6. IA PARA ORGANIZACIÓN, HUMANO PARA DECISIONES             │
  │     → Dejar que la IA maneje documentación, clasificación  │
  │       y formato. Tú manejas arquitectura, corrección y    │
  │       juicio de negocio                                     │
  │                                                             │
  └─────────────────────────────────────────────────────────────┘
```

---

## Resumen

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   La IA en el desarrollo es como una impresora de alta      │
│   velocidad:                                                  │
│                                                             │
│   · Puede producir páginas de texto al instante             │
│   · Sigue la plantilla que le das                           │
│   · No puede decir si el contenido es correcto              │
│   · No puede tomar decisiones de diseño                     │
│   · El valor viene del humano que diseña la                │
│     plantilla y revisa el resultado                         │
│                                                             │
│   Usa la IA como aliada para acelerar tu proceso.           │
│   Nunca como reemplazo de tu juicio.                        │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```
