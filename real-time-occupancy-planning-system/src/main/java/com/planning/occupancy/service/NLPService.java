package com.planning.occupancy.service;

import java.io.IOException;
import java.time.Instant;

import com.planning.occupancy.model.StructuredQuery;
import com.planning.occupancy.model.TimeWindow;

public class NLPService {

    public StructuredQuery parseQuery(String nlQuery) throws IOException {

        // This requires an OpenAI API key to post the natural language query to a model
        // and get back the structured query. For the sake of the case study we will
        // return a hardcoded mock response.

        StructuredQuery query = new StructuredQuery();
        query.setDeskType("standing");
        query.setFloor(3);
        query.setTeamZone("Marketing Zone");
        TimeWindow timeWindow = new TimeWindow(Instant.parse("2025-05-08T12:00:00Z"),
                Instant.parse("2025-05-08T17:00:00Z"));
        query.setTimeWindow(timeWindow);

        return query;
    }
}
