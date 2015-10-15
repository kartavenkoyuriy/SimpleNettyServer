package com.simpleNettyServer.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;
import com.simpleNettyServer.util.ConnectionData;
import com.simpleNettyServer.util.ConnectQueue;
import com.simpleNettyServer.util.RequestCounter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ChannelHandler.Sharable
public class StatisticsHandler extends ChannelTrafficShapingHandler {

    private static final AtomicInteger totalConnectionsCounter = new AtomicInteger(0);
    private static final AtomicInteger activeConnectionsCounter = new AtomicInteger(0);

    private static final ConcurrentHashMap<String, RequestCounter> requestsCounter = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Integer> redirectionPerURL = new ConcurrentHashMap<>();

    private static final ConnectQueue<ConnectionData> log = new ConnectQueue<>();

    public StatisticsHandler(long checkInterval) {
        super(checkInterval);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        totalConnectionsCounter.getAndIncrement();
        activeConnectionsCounter.getAndIncrement();
        super.channelRead(ctx, msg);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        activeConnectionsCounter.getAndDecrement();
        super.handlerRemoved(ctx);
    }

    public static void addLogUnit(ConnectionData unit) {
        log.add(unit);
    }

    public static void addURLRedirection(String url) {
        synchronized (redirectionPerURL) {
            if (!redirectionPerURL.containsKey(url)) {
                redirectionPerURL.put(url, 1);
            } else {
                redirectionPerURL.put(url, redirectionPerURL.get(url) + 1);
            }
        }
    }

    public static void addRequestsCounter(String requestIP, String URI) {
        RequestCounter c;
        synchronized (requestsCounter) {
            if (!requestsCounter.containsKey(requestIP)) {
                c = new RequestCounter(requestIP, URI);
                requestsCounter.put(requestIP, c);
            } else {
                c = requestsCounter.get(requestIP).addRequest(URI);
                requestsCounter.put(requestIP, c);
            }
        }
    }

    public static int getTotalConnectionsCounter() {
        return totalConnectionsCounter.get();
    }

    public static int getActiveConnectionsCounter() {
        return activeConnectionsCounter.get();
    }

    public static ConcurrentHashMap<String, RequestCounter> getRequestsCounter() {
        return requestsCounter;
    }

    public static ConcurrentHashMap<String, Integer> getRedirectionPerURL() {
        return redirectionPerURL;
    }

    public static ConnectQueue<ConnectionData> getLog() {
        return log;
    }
}
