package com.simpleNettyServer.util;

import java.util.Date;

public class ConnectionData {
    private String IP;
    private String URI;
    private Date timeStamp;
    private int sentBytes;
    private int receivedBytes;
    private long speed;

    public ConnectionData(String IP, String URI, int sentBytes, int receivedBytes, long speed) {
        this.IP = IP;
        this.URI = URI;
        this.timeStamp = new Date();
        this.sentBytes = sentBytes;
        this.receivedBytes = receivedBytes;
        this.speed = speed;
    }

    public String getIP() {
        return IP;
    }

    public String getURI() {
        return URI;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public long getSentBytes() {
        return sentBytes;
    }

    public long getReceivedBytes() {
        return receivedBytes;
    }

    public long getSpeed() {
        return speed;
    }

}
