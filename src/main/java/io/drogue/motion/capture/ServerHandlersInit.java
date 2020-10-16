package io.drogue.motion.capture;

import io.drogue.motion.PositionState;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.ssl.SslHandler;


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
                new LineBasedFrameDecoder( 1024, true, false ),
                new PositionDecoder(),
                new StateUpdater(this.state)
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
