package com.simpleNettyServer.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();

        channelPipeline.addLast("codec", new HttpServerCodec());
        channelPipeline.addLast("aggregetor", new HttpObjectAggregator(512 * 1024));
        channelPipeline.addLast("statisticsHandler", new StatisticsHandler(0));
        channelPipeline.addLast("httpHandler", new HttpHandler());
    }
}
