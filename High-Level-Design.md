# High-Level Design (HLD) for AI-Enhanced Real-Time Occupancy Planning System

## Overview

The system is designed to intelligently recommend desks for employees based on real-time occupancy data, employee preferences, organizational policies, and forecasted occupancy trends. It ingests structured space data, sensor input, employee preferences, historical metrics, and policies to generate context-aware recommendations.

## Key Components

### 1. **Data Ingestion Layer**

- **Static Data Loaders**
  - Load from JSON: `spaces.json`, `desks.json`, `sensors.json`, `preferences.json`, `policies.json`, `deskRules.json`, `metrics.json`, `occupancyRecords.json`, `occupancyForecast.json`
- **Repositories**
  - Singleton repositories exposing CRUD-like access for each entity type.

### 2. **Core Services**

#### A. **DeskPlanner**

- Orchestrates the filtering and ranking of desks based on:
  - Floor/team/desk type query
  - Forecast data
  - Real-time sensor health
  - Employee preferences
  - Policy compliance
  - Metrics-based ranking (trend + utilization)

#### B. **PolicyModule**

- Applies organizational policies as strategy pattern rules:
  - `SanitizationRule`
  - `CapacityLimitRule`
  - `StandingDeskRule`
  - `DualMonitorRule`
  - `AdjacencyRule`

#### C. **MetricsModule**

- Computes area-wise occupancy trend using `TimeWindow` and forecast.
- Extracts historical utilization rate from `metrics.json`.

#### D. **NlpModule**

- Uses OpenAI API to convert natural language queries into `StructuredQuery` objects.

### 3. **API Layer**

- Built on NanoHTTPD (embedded HTTP server)
- Endpoint: `POST /query`
  - Payload: `{ "nl_query": "...", "employee_id": "EMP-1001" }`
  - Response: `{ "query": ..., "recommendations": [...] }`

## Data Model Summary

- **Space**: floors > zones > areas
- **Desk**: linked to areaId, has type, features, lastUsed
- **Sensor**: active/inactive per area
- **OccupancyForecast**: next-day crowd predictions by area and time-bucket
- **AreaMetric**: historical peak/avg utilization
- **EmployeePreference**: equipment, desk, adjacency, preferred days
- **Policy**: definitions of organization rules
- **DeskAssignmentRule**: codified logic e.g. standing desks only if requested

## Key Algorithms

- **Forecast Mapping**: `TimeWindow` → bucket (morning/afternoon/evening)
- **Policy Checks**: `PolicyModule.isDeskValid()` with all strategy rules
- **Preference Matching**: Checks if desk matches window/adjacency/equipment
- **Sensor Health**: Discard desks in areas with inactive sensors
- **Ranking**: Desks are sorted by:
  - Occupancy trend (lower is better)
  - Utilization rate (lower is better)
  - Last used timestamp (older is better)

## Deployment & Runtime

- Self-contained Java app
- No Spring Boot or external frameworks
- Serves HTTP on configurable port
- JSON files loaded from `resources/data/`

## Extensibility

- Add new policy rules by implementing `PolicyRule` interface
- Support more time buckets by expanding `MetricsModule`
- Replace JSON loading with DB-based loaders in future
- Extend NLP mapping with richer OpenAI prompt engineering

## System Architecture Diagram

```plaintext
+------------------+       +----------------+       +----------------+
|  API Server      | <---> | DeskPlanner    | <---> | Repositories   |
|  (NanoHTTPD)     |       | (Service Layer)|       | (In-Memory)    |
+------------------+       +----------------+       +----------------+
         |                         |                        |
         v                         v                        v
  +----------------+      +-------------------+     +------------------+
  |  NlpModule     |      |  PolicyModule     |     |  MetricsModule   |
  | (OpenAI-based) |      | (Rules: strategy) |     | (Forecast, Util) |
  +----------------+      +-------------------+     +------------------+
```

---
