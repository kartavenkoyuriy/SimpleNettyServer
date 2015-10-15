package com.simpleNettyServer.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RequestCounter {
    private String IP;
    private Set<String> requests = new HashSet<>();
    private int connectionCounter;
    private Date lastConnection;

    public RequestCounter(String IP, String request) {
        this.IP = IP;
        requests.add(request);
        this.connectionCounter = 1;
        this.lastConnection = new Date();
    }

    public synchronized RequestCounter addRequest(String request){
        requests.add(request);
        this.connectionCounter++;
        lastConnection = new Date();
        return this;
    }

    public int getCountOfUniqueRequests() {
        return requests.size();
    }

    public int getConnectionsCounter() {
        return connectionCounter;
    }

    public Date getLastConnectionDate() {
        return lastConnection;
    }
}
