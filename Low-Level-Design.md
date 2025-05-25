# Low-Level Design (LLD) for AI-Enhanced Real-Time Occupancy Planning System

## 1. Overview

This document describes the detailed class-level architecture and interactions for implementing the intelligent occupancy planning system. It expands upon the HLD and includes class responsibilities, relationships, design patterns used, and UML-like descriptions.

---

## 2. Key Classes and Responsibilities

### a. `DeskPlanner`

- Central orchestrator for desk recommendation.
- Injects:
  - `SpaceRepository`, `DeskRepository`, `SensorRepository`
  - `OccupancyRepository`, `PreferenceRepository`
  - `PolicyModule`, `MetricsModule`
- Methods:
  - `List<Desk> findDesks(StructuredQuery query)`

### b. `StructuredQuery`

- Holds parsed data from NL query
  - `Integer floor`
  - `String deskType`
  - `String teamZone`
  - `TimeWindow timeWindow`
  - `String employeeId`

### c. `PolicyModule`

- Contains rules that implement `PolicyRule` interface
- Uses Strategy pattern
- Rules:
  - `SanitizationRule`
  - `CapacityLimitRule`
  - `StandingDeskRule`
  - `DualMonitorRule`
  - `AdjacencyRule`

### d. `MetricsModule`

- Calculates:
  - Occupancy trend for a given time window and area
  - Utilization rate from historical metrics
- Uses `OccupancyForecast` and `AreaMetric`

### e. `PreferenceRepository`

- Loads and exposes employee preferences
- Used for filtering desks by:
  - Desk type
  - Equipment needs
  - Adjacency preferences

### f. `NlpModule`

- Uses OpenAI API to convert NL query to `StructuredQuery`

---

## 3. Class Diagram (Text-Based UML)

```plaintext
+----------------------+
|     StructuredQuery  |
+----------------------+
| - floor: Integer      |
| - deskType: String    |
| - teamZone: String    |
| - timeWindow: TimeWindow |
| - employeeId: String  |
+----------------------+

+-------------------+          +-------------------+
|   DeskPlanner     |<>------->|  PolicyModule     |
+-------------------+          +-------------------+
| +findDesks()      |          | +isDeskValid()    |
+-------------------+          +-------------------+
        |                            ^
        v                            |
+-------------------+        +---------------------+
| MetricsModule     |        |  PolicyRule (IF)    |
+-------------------+        +---------------------+
| +getTrend()       |        | +validate(...)      |
| +getUtilRate()    |        +---------------------+
        |                            ^      ^       ^      ^      ^
        v                            |      |       |      |      |
+-------------------+     +-----------------------------+ (various rule classes)
| PreferenceRepo     |     | SanitizationRule, ... etc. |
+-------------------+     +-----------------------------+
```

---

## 4. Design Patterns Used

| Pattern    | Usage                                              |
| ---------- | -------------------------------------------------- |
| Strategy   | `PolicyRule` interface with different rule classes |
| Repository | For static data access (`*Repository.java`)        |
| Singleton  | `DiskDataLoader` for loading JSON only once        |
| Facade     | `DeskPlanner` hides complexity of coordination     |
| DTO        | `StructuredQuery` as a DTO from NLP                |

---

## 5. Interaction Flow

```plaintext
User → POST /query
  → QueryHandler
    → NlpModule.parseQuery(nl_query)
    → StructuredQuery
    → DeskPlanner.findDesks(query)
        → Load desk candidates
        → Filter by sensor health
        → Filter by employee preferences
        → Apply PolicyModule (rules)
        → Rank using MetricsModule
        → Return top 3 desks
```

---

## 6. Extensibility

- Add new rules: implement `PolicyRule`, plug into `PolicyModule`
- Add support for new desk types or areas by updating `desks.json`, `spaces.json`
- Replace static data with database-backed repositories
- Swap NLP engine if needed (keep returning `StructuredQuery`)

---
