package io.drogue.motion.capture;

import java.nio.charset.Charset;
import java.util.List;

import io.drogue.motion.PositionState;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

public class PositionDecoder extends ByteToMessageDecoder {

    public PositionDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        //System.err.println("decode " + in.toString(Charset.forName("UTF8")));
        try {
            byte[] buf = new byte[in.readableBytes()];
            in.readBytes(buf);
            Position position = Position.parse(buf);
            if (position != null) {
                System.err.println(position);
                out.add(position);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
