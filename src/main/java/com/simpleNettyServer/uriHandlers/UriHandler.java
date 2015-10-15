package com.simpleNettyServer.uriHandlers;

import io.netty.handler.codec.http.FullHttpResponse;

public interface UriHandler {
    FullHttpResponse process(String uri);
}
