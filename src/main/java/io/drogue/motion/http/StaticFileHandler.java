package io.drogue.motion.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class StaticFileHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        String path = msg.uri();
        System.err.println("---> " + path);
        if (path.equals("/")) {
            path = "/index.html";
        }

        File root = new File(System.getProperty("user.dir" ) );
        path = path.substring(1);
        File dir = new File(root, "src/main/resources");
        File source = new File(dir, path );
        if ( ! source.exists() ) {
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                                                                           HttpResponseStatus.NOT_FOUND);
            response.headers().add( HttpHeaderNames.CONTENT_LENGTH, 0);
            ctx.writeAndFlush(
                    response
            );
            return;
        }
        System.err.println( " -------------> " + source );
        try (InputStream resource = new FileInputStream(source)) {
            if (resource == null) {
                ctx.writeAndFlush(
                        new DefaultFullHttpResponse( HttpVersion.HTTP_1_1,
                                                     HttpResponseStatus.NOT_FOUND )

                );
            } else {
                byte[] bytes = resource.readAllBytes();
                ByteBuf buf = ctx.alloc().buffer(bytes.length);
                buf.writeBytes(bytes);
                DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1,
                        HttpResponseStatus.OK,
                        buf
                );
                response.headers().add(HttpHeaderNames.CONTENT_LENGTH, bytes.length);
                response.headers().add(HttpHeaderNames.CONTENT_TYPE, contentType(path));

                ctx.writeAndFlush(response);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    protected String contentType(String path) {
        if (path.endsWith(".js")) {
            return "text/javascript";
        }
        if (path.endsWith(".css")) {
            return "text/css";
        }

        if (path.endsWith(".html")) {
            return "text/html";
        }

        return "text/plain";
    }


}
