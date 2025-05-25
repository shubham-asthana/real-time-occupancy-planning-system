# AI-Enhanced Real-Time Occupancy Planning System

## Overview

This Java-based project implements an intelligent desk recommendation system that uses real-time sensor data, employee preferences, forecasted occupancy, and organizational policies to recommend optimal desks for employees.

The system consists of:

- Embedded HTTP API (using NanoHTTPD)
- JSON-based data storage (static input)
- Modular service-oriented architecture
- Optional OpenAI integration for natural language query parsing

---

## Features

- Desk recommendations based on:
  - Floor, team zone, and desk type
  - Real-time sensor activity
  - Forecasted occupancy trends
  - Employee preferences (equipment, adjacency)
  - Organizational policies (e.g. sanitization, capacity)
- Modular rule engine with extensible policy strategies
- Built-in NLP integration using OpenAI API

---

## Project Structure

```plaintext
src/main/java/
├── com.example.occupancy
│ ├── api # HTTP server and handler
│ ├── model # Data models (Desk, Space, Preference, etc.)
│ ├── repository # JSON-based repositories
│ ├── service # Core business logic modules
│ └── util # Utility classes (e.g., JSON handling)
resources/data/ # Static JSON files (inputs)
```

---

## Dependencies

- Java 8+
- [NanoHTTPD](https://github.com/NanoHttpd/nanohttpd)
- Jackson (for JSON parsing)
- Optional: OpenAI Java SDK (for NLP)

### Maven Dependencies

```xml
<dependencies>
  <dependency>
    <groupId>org.nanohttpd</groupId>
    <artifactId>nanohttpd</artifactId>
    <version>2.3.1</version>
  </dependency>
  <dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.0</version>
  </dependency>
  <dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
    <version>2.15.0</version>
  </dependency>
</dependencies>
```

## Configuration

Place all input JSON files under src/main/resources/data/, including:

- desks.json
- spaces.json
- preferences.json
- sensors.json
- policies.json
- metrics.json
- occupancyRecords.json
- occupancyForecast.json

## Set your OpenAI API key (if using NLP) as an environment variable:

```plaintext
export OPENAI_API_KEY=sk-xxxxxxxxxxxxxxxxx
```

## How to Run

1. Compile
   If using Maven:

```plaintext
mvn clean compile
```

2. Run the Application

```plaintext
java -cp target/classes com.example.occupancy.api.Main
```

This starts the HTTP server at http://localhost:8080.

3. Test the API

- Send a POST request to

```plaintext
/query:
  {
  "nl_query": "Find me an available standing desk on 3rd floor near the marketing team for tomorrow afternoon.",
  "employee_id": "EMP-1001"
  }
```

- You’ll receive a response like:

```plaintext
  {
  "query": { ... },
  "recommendations": [ { ... }, { ... } ]
  }
```

### Extending the System

- Add new policy rules: implement PolicyRule, register in PolicyModule
- Swap static JSON files with a database backend
- Use better NLP prompts or fine-tuning for OpenAI
- Integrate with employee calendar for availability
