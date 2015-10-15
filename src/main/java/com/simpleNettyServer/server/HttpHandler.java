package com.simpleNettyServer.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

import io.netty.handler.codec.http.FullHttpResponse;
import com.simpleNettyServer.uriHandlers.*;
import com.simpleNettyServer.util.ConnectionData;

import java.net.InetSocketAddress;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest httpRequest) {
        long startTime = System.currentTimeMillis();

        String requestIP = (((InetSocketAddress) ctx.channel().remoteAddress()).getHostString());
        String URI = httpRequest.getUri();

        FullHttpResponse response = writeResponse(URI);
        ctx.write(response).addListener(ChannelFutureListener.CLOSE);

        ByteBuf buffer = Unpooled.copiedBuffer(httpRequest.toString().getBytes());
        int receivedBytes = buffer.readableBytes() + httpRequest.content().readableBytes();
        int sentBytes = response.content().writerIndex();
        long endTime = System.currentTimeMillis() - startTime;
        double processingTime = endTime / (double) 1000;
        long speed = Math.round((sentBytes + receivedBytes) / processingTime);

        ConnectionData logUnit = new ConnectionData(requestIP, URI, sentBytes, receivedBytes, speed);

        StatisticsHandler.addRequestsCounter(requestIP, URI);
        StatisticsHandler.addLogUnit(logUnit);
    }

    private FullHttpResponse writeResponse(String uri) {
        if (uri.equals("/hello")) {
            return new HelloUriHandler().process(uri);
        }
        if (uri.matches("/redirect\\?url=\\S*")) {
            return new RedirectUriHandler().process(uri);
        }
        if (uri.equals("/status")) {
            return new StatusUriHandler().process(uri);
        }
        return new NotFoundUriHandler().process(uri);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
