# Low Level Design for AI Enhanced Real Time Occupancy Planning System

## 1. Introduction

This document details data models, class designs, service interactions, and method-level logic. The system parses natural language queries to recommend desks based on occupancy, preferences, and policies.

---

## 2. Module Overview

### 2.1 NLP Module (`NlpModule`)

- **Purpose:** Parse natural language input into a structured query format using an AI engine.
- **Key Method:**
  ```java
  StructuredQuery parseQuery(String nlQuery);
  ```
- **Dependencies:** OpenAI GPT-4 API or equivalent.

---

### 2.2 Desk Planner (`DeskPlanner`)

- **Purpose:** Core service to apply filtering, enforce policies, and produce ranked desk recommendations.
- **Key Method:**
  ```java
  List<Desk> findDesks(StructuredQuery query);
  ```

---

### 2.3 Policy Module (`PolicyModule`)

- **Purpose:** Apply organizational rules and policies to desk assignments.
- **Key Method:**
  ```java
  boolean isDeskValid(Desk desk, StructuredQuery query, OccupancyForecast forecast, Space floor);
  ```

---

## 3. Data Models

### 3.1 StructuredQuery

```java
class StructuredQuery {
  int floor;
  String deskType;
  String teamZone;
  TimeWindow timeWindow;
}

class TimeWindow {
  Instant start;
  Instant end;
}
```

---

### 3.2 Desk

```java
class Desk {
  String id;
  String type;
  String areaId;
  int floor;
  String zone;
  String locationDescription;
  List<String> features;
  String status;
  Instant lastUsed;
}
```

---

### 3.3 Space

```java
class Space {
  String id;
  String name;
  String type; // floor, zone, area
  int capacity;
  String parentId;
}
```

---

### 3.4 OccupancyForecast

```java
class OccupancyForecast {
  Map<String, NextDayForecast> nextDay;
}

class NextDayForecast {
  int morning;
  int afternoon;
  int evening;
}
```

---

## 4. Class Design

### 4.1 NlpModule

```java
class NlpModule {
  StructuredQuery parseQuery(String query) {
    // Send prompt to LLM and parse JSON response
  }
}
```

---

### 4.2 DeskRepository

```java
class DeskRepository {
  List<Desk> findAll();
  List<Desk> findByFloorAndType(int floor, String type);
}
```

---

### 4.3 SpaceRepository

```java
class SpaceRepository {
  List<Space> findAll();
  List<Space> findByParentId(String parentId);
  Optional<Space> findByName(String name);
}
```

---

### 4.4 OccupancyRepository

```java
class OccupancyRepository {
  List<OccupancyRecord> getCurrentOccupancy();
  OccupancyForecast getForecast();
}
```

---

### 4.5 PolicyModule

```java
class PolicyModule {
  boolean isDeskValid(Desk desk, StructuredQuery query, OccupancyForecast forecast, Space floor) {
    // Enforce policies: capacity, sanitization, etc.
  }
}
```

---

## 5. Sequence Diagram (Textual)

```
Client --> API Handler: sends POST /query
API Handler --> NlpModule: parseQuery(nl_query)
NlpModule --> returns StructuredQuery

API Handler --> DeskPlanner: findDesks(structuredQuery)
DeskPlanner --> SpaceRepository: find floor, zones, areas
DeskPlanner --> DeskRepository: fetch desks
DeskPlanner --> OccupancyRepository: fetch forecasts
DeskPlanner --> PolicyModule: validate desks
DeskPlanner --> returns ranked desk list

API Handler --> Client: returns JSON with recommendations
```

---

## 6. Error Handling

- **Invalid query:** Return 400 with message.
- **No matching desks:** Return 200 with empty list.
- **NLP engine failure:** Return 502 with fallback message.

---

## 7. Configuration

- **OpenAI API Key:** Loaded from environment.
- **Static Data:** Loaded from JSON at startup.

---

## 8. Logging

- Log incoming queries
- Log decisions on desk inclusion/exclusion
- Log errors and NLP results

---

## 9. Security

- Validate and sanitize all user inputs
- Store API credentials securely
- Optionally implement token-based authentication

---

## 10. Testing Strategy

- Unit tests for each module:
  - `NlpModule`: Mocked LLM responses
  - `PolicyModule`: Policy enforcement logic
  - `DeskPlanner`: End-to-end filtering pipeline
- Integration tests for API response consistency

---
