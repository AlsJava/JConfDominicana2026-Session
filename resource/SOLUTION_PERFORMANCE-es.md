# Soluciones de Rendimiento — Más allá de la Complejidad Algorítmica

> **El rendimiento no es solo notación Big-O.** Un algoritmo O(1) ejecutándose contra una base de datos saturada, un disco lento, o un
> payload serializado grueso siempre perderá ante un algoritmo O(n log n) bien ajustado ejecutándose en el entorno correcto.

---

## 1. La ilusión del Big-O

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   "Si escribo un algoritmo O(1), el rendimiento está       │
│    resuelto."                                               │
│                                                             │
│        ╔═══════════════════════════════════════╗             │
│        ║                                     ║             │
│        ║   Algoritmo O(1)                    ║             │
│        ║   ┌─────────────────────┐           ║             │
│        ║   │  Rápido en teoría   │           ║             │
│        ║   │  ↓                  │           ║             │
│        ║   │  Consulta DB: 2s    │           ║             │
│        ║   │  E/S de disco: 500ms│           ║             │
│        ║   │  Serialización: 300ms│          ║             │
│        ║   │  Red: 800ms         │           ║             │
│        ║   └─────────────────────┘           ║             │
│        ║                                     ║             │
│        ║   TOTAL: ~3.6s  ← NO es rápido      ║             │
│        ║                                     ║             │
│        ╚═══════════════════════════════════════╝             │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

La complejidad algorítmica (Big-O) es solo **un** factor en el tiempo total de respuesta. Las restricciones externas — base de datos, disco,
red, serialización — a menudo dominan la ecuación:

```
  Latencia Total = Algoritmo + BD + Disco + Red + Serialización + Servicios Externos
      │              │     │     │        │              │
      │              │     │     │        │              └── Llamadas a APIs externas
      │              │     │     │        └── Tamaño de payload JSON/XML
      │              │     │     └── HDD vs SSD, velocidad de giro
      │              │     └── Cobertura de índices, contención de locks
      │              └── Plan de consulta, escaneos completos de tabla
      └── Complejidad teórica (Big-O) — solo parte de la historia
```

---

## 2. Índices de base de datos — El problema de Goldilocks

```
                    ┌──────────────────────┐
                    │   INDICES            │
                    │   (El Equilibrio)    │
                    └──────────┬───────────┘
                               │
              ┌────────────────┼────────────────┐
              ▼                ▼                ▼
     ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
     │  MUY POCOS   │  │  JUSTO LOS    │  │  MUY MUCHOS  │
     │              │  │  SUFICIENTES  │  │              │
     │  Escaneos    │  │              │  │  Degradación  │
     │  completos   │  │  Consultas   │  │  de inserts/  │
     │  Lecturas    │  │  dirigidas   │  │  updates      │
     │  lentas      │  │  Lecturas    │  │  Disco        │
     │  Oportunidades│ │  rápidas     │  │  desperdiciado│
     │  perdidas    │  │  Equilibrado │  │  Costo por    │
     └──────────────┘  └──────────────┘  └──────────────┘
```

### Por qué los índices son un costo, no algo gratis

```
  Índice en columna "email"
       │
       ├── ✓ SELECT WHERE email = '...' → O(log n) en vez de O(n)
       │
       ├── ✗ INSERT en tabla users → debe actualizar el B-tree del índice
       ├── ✗ UPDATE campo email → debe reconstruir entrada del índice
       ├── ✗ DELETE de tabla users → debe remover entrada del índice
       └── ✗ Espacio en disco consumido → cada índice es una copia de los datos
```

**Cada índice es un impuesto sobre las escrituras.** Cuantos más índices, más lentos se vuelven los inserts y updates — y escrituras más lentas = mayor
costo de infraestructura.

### Cómo saber si necesitas un índice

```
  ┌──────────────────────────────────────────────────────┐
  │                                                      │
  │  NO puedes decidir sobre índices en el vacío.         │
  │                                                      │
  │  Necesitas las CONSULTAS REALES que correrán en PROD. │
  │                                                      │
  │  Los índices deben decidirse ANTES del despliegue —   │
  │  no después, no durante, sino antes.                  │
  │                                                      │
  └──────────────────────────────────────────────────────┘
```

Sin consultas concretas, añadir índices es adivinar. Añadir demasiados desperdicia recursos; añadir muy pocos causa lecturas lentas. El único enfoque confiable: **extraer consultas del código fuente, analizarlas, y añadir índices solo para lo que la
aplicación realmente necesita.**

---

## 3. Algoritmos complejos — Los costos ocultos

Hilos, E/S de archivos, computación pesada — estos son los algoritmos que requieren esfuerzo de ingeniería para optimizar. Pero incluso
aquí, los costos ocultos a menudo se pasan por alto:

```
  ┌─────────────────────────────────────────────────────────────┐
  │              EL PC DEL DESARROLLADOR vs. PRODUCCIÓN          │
  ├─────────────────────────────────────────────────────────────┤
  │                                                             │
  │   MÁQUINA DEL DESARROLLADOR              PRODUCCIÓN           │
  │   ──────────────────                  ────────────            │
  │   SSD (NVMe)                          Almacenamiento compartido│
  │   32GB RAM                            RAM contendida          │
  │   Sin saltos de red                   Múltiples saltos de red │
  │   Un solo inquilino                   Multi-inquilino         │
  │   Sin otros servicios compitiendo      Otros servicios con    │
  │                                         carga                   │
  │   Caches estables y calientes           Posibles arranques    │
  │                                         en frío                 │
  │                                                             │
  │   Resultado: Tu algoritmo O(1)       Resultado: Impredecible  │
  │   se siente instantáneo.            picos de latencia       │
  │                                      en todas partes.        │
  │                                                             │
  └─────────────────────────────────────────────────────────────┘
```

### La lista de verificación de la realidad en producción

```
  ¿El algoritmo pasa localmente?
       │
       ├── ✓ Velocidad de red — ¿el servicio que llamas está dentro del SLA?
       ├── ✓ Respuesta del disco — ¿la BD está en SSD? ¿Es compartida?
       ├── ✓ Concurrentia — ¿el servicio downstream maneja tu carga?
       ├── ✓ Pool de conexiones — ¿estás fatigando un pool de conexiones?
       ├── ✓ Costo de serialización — ¿cuál es el tamaño del payload?
       └── ✓ Contención de recursos — ¿qué más comparte esta infraestructura?
```

---

## 4. Serialización — Elige el protocolo correcto

¿Quién consume los datos? ¿Externo? ¿Interno? ¿Existe un contrato compartido? Estas preguntas determinan el formato de serialización
correcto:

```
  ┌─────────────────────────────────────────────────────────────┐
  │              COMPARACIÓN DE FORMATOS DE SERIALIZACIÓN        │
  ├──────────────┬──────────┬─────────┬──────────┬──────────────┤
  │ Formato      │ Veloc.   │ Tamaño  │ Legible  │ Caso de uso  │
  │              │          │         │ humano   │              │
  ├──────────────┼──────────┼─────────┼──────────┼──────────────┤
  │ JSON         │ Lento    │ Grande  │ ✓ Sí     │ APIs REST,   │
  │              │          │         │          │ externo      │
  ├──────────────┼──────────┼─────────┼──────────┼──────────────┤
  │ JSONB        │ Medio    │ Medio   │ Parcial  │ Almacenamiento│
  │              │          │         │          │ en PostgreSQL │
  ├──────────────┼──────────┼─────────┼──────────┼──────────────┤
  │ FastJSON2    │ Rápido   │ Medio   │ Parcial  │ Default de    │
  │              │          │         │          │ Spring Boot   │
  ├──────────────┼──────────┼─────────┼──────────┼──────────────┤
  │ Protobuf     │ Muy rápido│ Pequeño │ ✗ No    │ gRPC,         │
  │              │          │         │          │ comunicación  │
  │              │          │         │          │ interna       │
  ├──────────────┼──────────┼─────────┼──────────┼──────────────┤
  │ Avro         │ Rápido   │ Pequeño │ ✗ No    │ Pipelines de  │
  │              │          │         │          │ datos (Kafka) │
  ├──────────────┼──────────┼─────────┼──────────┼──────────────┤
  │ Kryo /     │ Muy rápido│ Pequeño │ ✗ No    │ Caché en     │
  │ Blackbird    │          │         │          │ memoria       │
  └──────────────┴──────────┴─────────┴──────────┴──────────────┘
```

### Marco de decisión

```
  ¿Quién consume los datos?
       │
       ├── Externo (navegadores, terceros)
       │    └── → JSON (compatibilidad universal)
       │
       ├── Interno (servicio a servicio)
       │    ├── Existe contrato (esquema protobuf)
       │    │    └── → Protobuf (rápido, pequeño, tipado)
       │    └── Sin contrato, alto throughput
       │         └── → Avro o Kryo (rápido, compacto)
       │
       └── Propio (mismo JVM, mismo proceso)
            └── → Kryo / Blackbird (el más rápido, sin esquema)
```

**El formato de serialización correcto, aplicado por contexto, puede mejorar dramáticamente el rendimiento del sistema sin cambios
arquitecturales mayores.**

---

## 5. Jackson Blackbird — Serialización nativa

Por defecto, la serialización de Java usa **reflexión** — un camino lento, no amigable para el JIT. Jackson Blackbird reemplaza la reflexión
con **manipuladores de métodos nativos**, dándote rendimiento cercano a Kryo sin cambios de código:

```
  ┌─────────────────────────────────────────────────────────────┐
  │              RUTAS DE SERIALIZACIÓN                          │
  ├─────────────────────────────────────────────────────────────┤
  │                                                             │
  │   JACKSON ESTÁNDAR                                          │
  │   ───────────────                                           │
  │                                                             │
  │   Objeto Java  →  Reflexión  →  Salida de bytes             │
  │                    (lento)                                    │
  │                    │                                          │
  │                    ▼                                          │
  │              Búsqueda de MethodHandle por campo               │
  │              No optimizado por JIT                            │
  │                                                             │
  │                                                             │
  │   JACKSON + BLACKBIRD                                       │
  │   ─────────────────────                                     │
  │                                                             │
  │   Objeto Java  →  Generado por bytecode → Salida de bytes    │
  │                    (nativo)                                   │
  │                    │                                          │
  │                    ▼                                          │
  │              Acceso directo a campos (sin reflexión)          │
  │              Optimizado por JIT, inlineable                   │
  │                                                             │
  └─────────────────────────────────────────────────────────────┘
```

### Cómo habilitarlo

**Paso 1** — Añadir la dependencia a `pom.xml`:

```xml

<dependency>
    <groupId>com.fasterxml.jackson.module</groupId>
    <artifactId>jackson-module-blackbird</artifactId>
    <version>2.18.2</version>
</dependency>
```

**Paso 2** — Configurar el bean del ObjectMapper:

```java

@Bean
public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new BlackbirdModule());
    return mapper;
}
```

Eso es todo. Sin cambios de código en tus DTOs. Sin anotaciones. Sin migración. Solo una configuración de bean y una dependencia — y
el rendimiento de serialización mejora significativamente.

---

## 6. Extrayendo consultas SQL/JPQL vía Spring Boot AOT

El procesamiento **AOT (Ahead-Of-Time)** de Spring Boot analiza tus repositorios en tiempo de compilación. Como parte de este proceso,
el motor de persistencia extrae y estructura todos los metadatos de consultas (métodos derivados, consultas nativas, proyecciones) en
archivos JSON dentro del directorio de build.

### Flujo de Arquitectura AOT

```
  [ Código Fuente Java ]
    ├── UsuarioRepository.java (Interfaces, @Query, Proyecciones)
    └── ...
          │
          ▼ (Fase de Compilación / Build)
  [ Comando: process-aot ]
          │
          ▼ (Procesador AOT de Spring Data)
  [ Archivos JSON de Metadatos ]
    └── Ubicación: target/classes/META-INF/spring/
          │
          ▼ (Ejecutar el Script)
  [ Catálogo de Consultas Limpio en Consola ]
```

### Estructura de ejemplo del JSON de metadatos

Cuando ejecutas la compilación AOT, Spring Data genera archivos descriptor bajo `target/classes/META-INF/spring/` (Maven) o
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

### Script de extracción automatizada: `extraer_consultas_aot.py`

Guarda este script en la raíz de tu proyecto Spring Boot para consolidar toda la información de consultas automáticamente.

```python
import os
import json
import glob

# Rutas estándar dependiendo de la herramienta de build
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
        print("[-] Error: No se encontró la carpeta META-INF/spring/. "
              "Asegúrate de ejecutar la compilación AOT primero.")
        print("    Usa: 'mvn spring-boot:process-aot' o './gradlew processAot'")
        return

    # Buscar todos los archivos JSON de metadatos generados
    archivos_json = glob.glob(os.path.join(ruta_base, "*.json"))

    print("\n" + "=" * 60)
    print("      REPORTE DE CONSULTAS PRECOMPILADAS (SPRING AOT)")
    print("=" * 60)

    queries_found = 0

    for ruta_archivo in archivos_json:
        try:
            with open(ruta_archivo, 'r', encoding='utf-8') as archivo:
                datos = json.load(archivo)

                # Adaptar para iterar sobre la estructura de metadatos de repositorio
                if isinstance(datos, list):
                    for item in datos:
                        if "repository" in item and "queries" in item:
                            print(f"\n[Repositorio]: {item['repository']}")
                            print("-" * 50)
                            for query in item["queries"]:
                                queries_found += 1
                                print(f"  • Método:      {query.get('method')}")
                                print(f"    Tipo:        {query.get('type')}")
                                print(f"    Lenguaje:    {query.get('queryLanguage')}")
                                print(f"    Retorno:     {query.get('returnType')}")
                                print(f"    Consulta:    {query.get('expression').strip()}")
                                print()
        except (json.JSONDecodeError, KeyError, TypeError):
            # Omitir archivos JSON pertenecientes a otras configuraciones AOT
            continue

    if queries_found == 0:
        print("\n[!] No se encontraron mapeos de consulta explícitos en los JSON procesados.")
    else:
        print(f"\n[+] Proceso completo. {queries_found} consultas listadas.")


if __name__ == "__main__":
    generar_reporte()
```

### Pasos de ejecución

Sigue estos tres comandos en tu terminal:

**1. Limpiar el directorio de build:**

```bash
mvn clean              # Maven
./gradlew clean        # Gradle
```

**2. Ejecutar el procesador AOT de Spring Boot:**

```bash
mvn spring-boot:process-aot    # Maven
./gradlew processAot           # Gradle
```

**3. Ejecutar el script de extracción:**

```bash
python extraer_consultas_aot.py
```

### Resultado esperado

```
============================================================
      REPORTE DE CONSULTAS PRECOMPILADAS (SPRING AOT)
============================================================

[Repositorio]: com.example.demo.repository.UsuarioRepository
--------------------------------------------------
  • Método:      findByEmail
    Tipo:        DERIVED_QUERY_METHOD
    Lenguaje:    JPQL
    Retorno:     com.example.demo.dto.UsuarioProjection
    Consulta:    select u from Usuario u where u.email = ?1

  • Método:      buscarUsuariosActivosNativo
    Tipo:        NATIVE_QUERY_METHOD
    Lenguaje:    SQL
    Retorno:     java.util.List
    Consulta:    SELECT * FROM usuarios WHERE activo = true AND pais_id = :paisId


[+] Proceso completo. 2 consultas listadas.
```

### Cómo se conecta esto con el rendimiento

```
  Extracción de Consultas AOT
       │
       ├── 1. Descubrir todas las consultas en el código fuente
       │
       ├── 2. Analizar la complejidad y patrones de acceso de cada consulta
       │
       ├── 3. Identificar índices faltantes (consultas sin soporte de índice)
       │
       ├── 4. Identificar índices redundantes (índices sin consultas que los usen)
       │
       └── 5. Desplegar con confianza — índices que coinciden con la carga real
```

Este es el puente entre el **código** y el **rendimiento de base de datos**: ya no puedes olvidar sobre índices porque
"no conocías las consultas". Ahora son visibles, auditables y accionables antes del despliegue en producción.

---

## 7. Resumen de optimización de rendimiento

```
  ┌─────────────────────────────────────────────────────────────┐
  │                    JERARQUÍA DE RENDIMIENTO                  │
  ├─────────────────────────────────────────────────────────────┤
  │                                                             │
  │  Nivel 1: ALGORITMO                                         │
  │  ────────────                                               │
  │  Big-O importa, pero es solo la base.                       │
  │                                                             │
  │  Nivel 2: BASE DE DATOS                                       │
  │  ──────────                                                 │
  │  Índices basados en consultas reales (extracción AOT).      │
  │  Optimización del plan de consulta.                         │
  │                                                             │
  │  Nivel 3: SERIALIZACIÓN                                     │
  │  ────────────                                               │
  │  Formato correcto por contexto (JSON, Protobuf, Blackbird). │
  │  Jackson Blackbird para ganancias JVM-internas sin esfuerzo.│
  │                                                             │
  │  Nivel 4: INFRAESTRUCTURA                                   │
  │  ─────────────                                              │
  │  Red, disco, pools de conexiones, SLAs de servicios externos.│
  │                                                             │
  │  Nivel 5: OBSERVABILIDAD                                    │
  │  ──────────────                                             │
  │  Micrometer, metadatos AOT, APM — medir antes de arreglar. │
  │                                                             │
  └─────────────────────────────────────────────────────────────┘
```

> **Conclusión:** El rendimiento es una propiedad del sistema, no una propiedad del algoritmo. Optimiza el stack completo — algoritmo,
> base de datos, serialización, infraestructura — y usa datos medibles (extracción de consultas AOT, benchmarks, APM) para guiar
> cada
> decisión.
