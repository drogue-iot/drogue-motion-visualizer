package io.drogue.motion;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class DebugHandler extends ChannelDuplexHandler  {

    public DebugHandler(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.err.println( prefix + " write " + msg );
        super.write(ctx, msg, promise);
    }

    private final String prefix;
}
