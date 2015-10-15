package com.simpleNettyServer.uriHandlers;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class NotFoundUriHandler implements UriHandler {

    private static final String ANSWER_NOT_FOUND = "404 NOT FOUND!";

    @Override
    public FullHttpResponse process(String uri) {
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.NOT_FOUND,
                Unpooled.copiedBuffer(ANSWER_NOT_FOUND, CharsetUtil.UTF_8)
        );

        fullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
        return fullHttpResponse;
    }
}
