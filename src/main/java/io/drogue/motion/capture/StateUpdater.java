package io.drogue.motion.capture;

import java.util.List;

import io.drogue.motion.PositionState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.MessageToMessageDecoder;

public class StateUpdater extends SimpleChannelInboundHandler<Position> {

    public StateUpdater(PositionState state) {
        this.state = state;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Position msg) throws Exception {
        this.state.set(msg);
    }

    private final PositionState state;
}
