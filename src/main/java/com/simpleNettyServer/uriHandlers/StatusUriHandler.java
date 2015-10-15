package com.simpleNettyServer.uriHandlers;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import com.simpleNettyServer.server.StatisticsHandler;
import com.simpleNettyServer.util.ConnectionData;
import com.simpleNettyServer.util.RequestCounter;

import java.util.Map;

public class StatusUriHandler implements UriHandler{

    @Override
    public FullHttpResponse process(String uri) {
        final StringBuilder pageBuff = new StringBuilder();

        pageBuff.append("<!DOCTYPE html><html><head>");
        pageBuff.append("</head>");

        pageBuff.append("<body>");
        pageBuff.append("<h4>Total connections: ").append(StatisticsHandler.getTotalConnectionsCounter()).append("</h4>");

        pageBuff.append("<table border=\"1\">");
        pageBuff.append("<caption><b>Unique requests</b></caption>");
        pageBuff.append("<tr><th>").append(" IP ").append("</th><th>").append(" Requests ").append("</th></tr>");
        for (Map.Entry<String, RequestCounter> pair : StatisticsHandler.getRequestsCounter().entrySet()) {
            pageBuff.append("<tr><td>");
            pageBuff.append(pair.getKey());
            pageBuff.append("</td><td>");
            pageBuff.append(pair.getValue().getCountOfUniqueRequests());
            pageBuff.append("</td></tr>");
        }
        pageBuff.append("</table>");
        pageBuff.append("<br>");

        pageBuff.append("<table border=\"1\">");
        pageBuff.append("<caption><b>Requests counter</b></caption>");
        pageBuff.append("<tr><th>").append(" IP ").append("</th><th>").append(" Requests ")
                .append("</th><th>").append(" Last request ").append("</th></tr>");
        for (Map.Entry<String, RequestCounter> pair : StatisticsHandler.getRequestsCounter().entrySet()) {
            pageBuff.append("<tr><td>");
            pageBuff.append(pair.getKey());
            pageBuff.append("</td><td>");
            pageBuff.append(pair.getValue().getConnectionsCounter());
            pageBuff.append("</td><td>");
            pageBuff.append(pair.getValue().getLastConnectionDate());
            pageBuff.append("</td></tr>");
        }
        pageBuff.append("</table>");
        pageBuff.append("<br>");

        pageBuff.append("<h4>Active connections: ").append(StatisticsHandler.getActiveConnectionsCounter()).append("</h4>");

        pageBuff.append("<table border=\"1\">");
        pageBuff.append("<caption><b>Redirection</b></caption>");
        pageBuff.append("<tr><th > URL ")
                .append("</th><th >").append(" Count ").append("</th></tr>");
        for (Map.Entry<String, Integer> pair : StatisticsHandler.getRedirectionPerURL().entrySet()) {
            pageBuff.append("<tr><td>");
            pageBuff.append(pair.getKey());
            pageBuff.append("</td><td>");
            pageBuff.append(pair.getValue());
            pageBuff.append("</td></tr>");
        }
        pageBuff.append("</table>");
        pageBuff.append("<br>");

        pageBuff.append("<table border=\"1\">");
        pageBuff.append("<caption><b>Connections log</b></caption>");
        pageBuff.append("<tr><th> src_ip ")
                .append("</th><th>").append(" URI ")
                .append("</th><th>").append(" timestamp ")
                .append("</th><th>").append(" sent_bytes ")
                .append("</th><th>").append(" received_bytes ")
                .append("</th><th>").append(" speed (bytes/sec)")
                .append("</th></tr>");
        for (ConnectionData c : StatisticsHandler.getLog()) {
            pageBuff.append("<tr><td>");
            pageBuff.append(c.getIP()).append("</td><td>");
            pageBuff.append(c.getURI()).append("</td><td>");
            pageBuff.append(c.getTimeStamp()).append("</td><td>");
            pageBuff.append(c.getSentBytes()).append("</td><td>");
            pageBuff.append(c.getReceivedBytes()).append("</td><td>");
            pageBuff.append(c.getSpeed()).append("</td></tr>");
        }
        pageBuff.append("</table></div></body></html>");


        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(pageBuff.toString(), CharsetUtil.UTF_8)
        );
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
        return response;
    }
}
