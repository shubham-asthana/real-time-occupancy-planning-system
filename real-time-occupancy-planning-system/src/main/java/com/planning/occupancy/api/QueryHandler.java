package com.planning.occupancy.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.planning.occupancy.model.Desk;
import com.planning.occupancy.model.StructuredQuery;
import com.planning.occupancy.service.DeskPlanner;
import com.planning.occupancy.service.NLPService;
import com.planning.occupancy.util.JsonUtil;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.Response.Status;
import fi.iki.elonen.NanoHTTPD.ResponseException;

public class QueryHandler {

    private final NLPService nplService = new NLPService();
    private final DeskPlanner deskPlanner = new DeskPlanner();

    public Response handleQuery(IHTTPSession session) throws IOException, ResponseException {

        Map<String, String> body = new HashMap<>();
        session.parseBody(body);
        String jsonRequest = body.get("postData");

        Map<?, ?> requestMap = JsonUtil.fromJson(jsonRequest, Map.class);
        String nlQuery = (String) requestMap.get("nl_query");

        StructuredQuery query = nplService.parseQuery(nlQuery);

        List<Desk> recommendations = deskPlanner.findDesks(query);

        Map<String, Object> respPayload = new HashMap<>();
        respPayload.put("query", query);
        respPayload.put("recommendations", recommendations);

        String jsonResponse = JsonUtil.toJson(respPayload);

        return NanoHTTPD.newFixedLengthResponse(Status.OK, "application/json", jsonResponse);
    }
}
