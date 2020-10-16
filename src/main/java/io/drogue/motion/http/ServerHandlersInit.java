package io.drogue.motion.http;

import java.nio.charset.Charset;

import io.drogue.motion.DebugHandler;
import io.drogue.motion.PositionState;
import io.drogue.motion.capture.PositionDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;


/**
 * Created by maanadev on 5/18/17.
 */
public class ServerHandlersInit extends ChannelInitializer<SocketChannel> {


    public ServerHandlersInit(PositionState state) {
        this.state = state;
    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ByteBuf delim = socketChannel.alloc().buffer();
        delim.writeChar('\n');
        socketChannel.pipeline().addLast(
                //new DebugHandler("head"),
                new HttpServerCodec(),
                //new DebugHandler("codec"),
                new HttpObjectAggregator(1048576),
                //new DebugHandler("agg"),
                new SSEHandler(this.state),
                new StaticFileHandler()
                /*
                new SimpleChannelInboundHandler<FullHttpRequest>() {
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
                        ByteBuf content = channelHandlerContext.alloc().buffer();
                        content.writeCharSequence("the password is always sw0rdf1sh", Charset.forName("UTF-8"));
                        DefaultHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                        channelHandlerContext.writeAndFlush(response);
                        channelHandlerContext.channel().close();
                    }
                }
                 */

        );
        //sslHandler,
        //new HttpServerCodec(),
        //new HttpObjectAggregator(1048576),
        //new SimpleChannelInboundHandler<FullHttpRequest>() {
        //protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        //ByteBuf content = channelHandlerContext.alloc().buffer();
        //content.writeCharSequence("the password is always sw0rdf1sh", Charset.forName("UTF-8"));
        //DefaultHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        //channelHandlerContext.writeAndFlush(response);
        //channelHandlerContext.channel().close();
        //}
        //});
    }

    private final PositionState state;
}
