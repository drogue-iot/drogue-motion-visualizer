package io.drogue.motion.http;

import java.nio.charset.Charset;

import io.drogue.motion.PositionState;
import io.drogue.motion.capture.Position;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class SSEHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    public SSEHandler(PositionState state) {
        this.state = state;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        String uri = msg.uri();
        if ( uri.equals( "/motion" ) ) {
            System.err.println( "SSI INITIATE ****************" );
            DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/event-stream");
            ctx.writeAndFlush(response);
            new Thread(() -> {
                Position lastPosition = null;
                while ( true ) {
                    try {
                        Position cur = this.state.get();
                        if ( cur == null ) {
                            Thread.sleep(1000);
                            continue;
                        }
                        if ( ! cur.equals(lastPosition)) {
                            lastPosition = cur;
                        }
                        ByteBuf line = ctx.alloc().buffer();
                        line.writeCharSequence("data: " + toJSON( cur ) + "\n\n", Charset.forName("UTF8") );
                        DefaultHttpContent chunk = new DefaultHttpContent(line);
                        ctx.writeAndFlush( chunk );
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            ctx.fireChannelRead(msg.retain());
        }
    }

    protected String toJSON(Position position) {
        return toJSON( position.getW(), position.getX(), position.getY(), position.getZ() );
    }

    protected String toJSON(float w, float x, float y, float z) {
        StringBuilder str = new StringBuilder();
        str.append( "{");
        str.append( "\"w\":");
        str.append( w );
        str.append( ",");
        str.append( "\"x\":");
        str.append( x );
        str.append( ",");
        str.append( "\"y\":");
        str.append( y );
        str.append( ",");
        str.append( "\"z\":");
        str.append( z );
        str.append( "}");
        return str.toString();
    }

    private final PositionState state;
}
