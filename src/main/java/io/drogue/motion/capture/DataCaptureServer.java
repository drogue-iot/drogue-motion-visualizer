package io.drogue.motion.capture;

import io.drogue.motion.PositionState;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public class DataCaptureServer {


    static final int LOCAL_PORT = Integer.parseInt(System.getProperty("localPort", "4111"));

    public DataCaptureServer(PositionState state) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            this.channel = b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ServerHandlersInit(state))
                    .childOption(ChannelOption.AUTO_READ, true)
                    .bind(LOCAL_PORT).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //bossGroup.shutdownGracefully();
            //workerGroup.shutdownGracefully();
        }
    }

    public void await() throws InterruptedException {
        System.err.println( "await close");
        this.channel.closeFuture().sync();
        System.err.println( "await close done");
    }

    private Channel channel;
}
