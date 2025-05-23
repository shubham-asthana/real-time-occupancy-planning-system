package com.planning.occupancy.api;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class OccupancyServer extends NanoHTTPD {

    private final QueryHandler queryHandler = new QueryHandler();

    public OccupancyServer(int port) throws IOException {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            if (Method.POST.equals(session.getMethod()) && "/query".equals(session.getUri())) {
                return queryHandler.handleQuery(session);
            }
        } catch (IOException | ResponseException ex) {
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "application/json",
                    "{\"error\":\"" + ex.getMessage() + "\"}");
        }
        return newFixedLengthResponse(Response.Status.NOT_FOUND, "application/json",
                "{\"error\":\"Not Found\"}");
    }
}
