package com.planning.occupancy.api;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class Application {
    public static void main(String[] args) throws IOException {
        OccupancyServer server = new OccupancyServer(8080);
        server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("Server running on http://localhost:8080/");
    }
}
