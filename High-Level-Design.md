# High Level Design for AI Enhanced Real Time Occupancy Planning System

## 1. Overview

This document outlines the high-level design for a prototype AI-enhanced real-time occupancy planning system that allows users to make natural language workspace queries, such as:

> "Find me an available standing desk near the marketing team on the 3rd floor for tomorrow afternoon."

The system integrates real-time data, employee preferences, organizational policies, and uses an AI-powered NLP engine to interpret and respond to natural language queries.

---

## 2. Objectives

- Parse natural language queries using AI tools.
- Match workspace requirements with real-time desk availability.
- Apply employee preferences and organizational policies.
- Recommend optimal desks based on constraints and forecasts.

---

## 3. Architecture Diagram

```
Client ──► REST API (Lightweight HTTP Server)
             │
             ▼
         NlpModule (LLM)
             │
             ▼
      StructuredQuery (JSON)
             │
             ▼
     DeskPlanner (Filtering Logic)
     ├── DeskRepository
     ├── SpaceRepository
     ├── OccupancyRepository
     ├── PolicyModule
     └── PreferenceRepository
             │
             ▼
  Recommendation Response (Top-N desks)
```

---

## 4. Key Components

### 4.1 NLP Module

- **Input:** Natural language query
- **Output:** Structured query object (e.g. desk type, floor, zone, time window)
- **Tech:** OpenAI GPT-4 or similar

### 4.2 Occupancy Planner (DeskPlanner)

- Filters desks based on:
  - Floor and desk type
  - Availability and forecasted occupancy
  - Desk assignment rules and policies
  - Adjacency to teams and zones

### 4.3 Policy Module

- Enforces policies such as:
  - Social distancing
  - Desk sanitization delay
  - Capacity limits
  - Accessibility preferences

### 4.4 Repositories

- Load and serve static/mock data:
  - Spaces & Areas (`spaces.json`)
  - Desks (`desks.json`)
  - Occupancy data (`occupancy.json`)
  - Preferences (`employee_preferences.json`)
  - Policies and rules (`policies.json`, `desk_assignment_rules.json`)

---

## 5. Data Flow

1. **User Query** → HTTP POST `/query`
2. **NLP Parser** → Converts NL query to structured fields
3. **Data Repositories** → Fetch desks, spaces, forecasts, preferences
4. **Policy Module** → Apply mandatory constraints
5. **Ranking Logic** → Score desks by relevance and proximity
6. **Return JSON Response** with top matching desks

---

## 6. Key Data Models

- **Desk**
  - id, type, areaId, floor, features, status, lastUsed
- **Space**
  - id, name, type (floor/zone/area), parentId
- **Occupancy**
  - areaId, timestamp, forecast (morning/afternoon/evening)
- **StructuredQuery**
  - floor, deskType, teamZone, timeWindow
- **Policy & DeskAssignmentRule**
  - id, name, active, enforcement level, rule priority

---

## 7. Technology Stack

| Layer       | Technology                |
| ----------- | ------------------------- |
| API Server  | Java + Simple HTTP Server |
| NLP Engine  | OpenAI GPT-4 API          |
| Data Format | JSON (Mock Datasets)      |
| HTTP Client | HttpClient                |
| Build Tool  | Maven                     |
| Runtime     | Java 11                   |

---

## 8. API Contract

**Endpoint:** `POST /query`

**Request Body:**

```json
{
  "nl_query": "Find me an available standing desk near the marketing team on the 3rd floor for tomorrow afternoon."
}
```

**Response:**

```json
{
  "query": {
    "floor": 3,
    "deskType": "standing",
    "teamZone": "marketing-team",
    "timeWindow": {
      "start": "2025-05-08T12:00:00Z",
      "end": "2025-05-08T17:00:00Z"
    }
  },
  "recommendations": [
    {
      "deskId": "D-301",
      "locationDescription": "Near window, east corner",
      "features": ["dual-monitors", "ergonomic-chair", "adjustable-height"]
    }
  ]
}
```

---

## 9. Assumptions

- Spatial layout and distance calculations are approximate (zone/area-level granularity).
- Policies like "6 feet distance" are assumed satisfied if forecast occupancy is < 80%.
- Real-time integration uses mock JSON; in production, this would be via REST API integrations.

---

## 10. Future Enhancements

- Integrate desk reservation & booking system
- Real-time map visualizations
- Expand NLP support for more complex queries
- Persist history of queries and assignments
- Use geospatial coordinates for precise desk adjacency logic

---

## 11. Security Considerations

- API keys (e.g. OpenAI) stored securely in environment variables
- Rate limiting and input validation at the controller layer
- User auth/token-based access for production systems

---

## 12. Conclusion

This design supports real-time, AI-assisted workspace planning with intelligent desk matching based on employee preferences, organizational constraints, and live sensor data.
