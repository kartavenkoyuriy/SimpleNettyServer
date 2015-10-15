package com.simpleNettyServer.uriHandlers;

import io.netty.handler.codec.http.*;
import com.simpleNettyServer.server.StatisticsHandler;

public class RedirectUriHandler implements UriHandler {
    @Override
    public FullHttpResponse process(String uri) {
        String url = uri.substring(uri.indexOf("=") + 1, uri.length());
        StatisticsHandler.addURLRedirection(url);
        if (!url.matches("http://\\S*")) {
            url = "http://" + url;
        }

        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        fullHttpResponse.headers().set(HttpHeaders.Names.LOCATION, url);
        fullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
        return fullHttpResponse;
    }
}
