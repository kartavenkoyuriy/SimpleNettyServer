package com.simpleNettyServer.uriHandlers;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HelloUriHandler implements UriHandler {
    private static final int WAITING_TIME = 10000;
    private static final String ANSWER_HELLO_WORLD = "Hello World";

    @Override
    public FullHttpResponse process(String uri) {
        try{
            Thread.sleep(WAITING_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(ANSWER_HELLO_WORLD, CharsetUtil.UTF_8));
        fullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
        return fullHttpResponse;
    }
}
