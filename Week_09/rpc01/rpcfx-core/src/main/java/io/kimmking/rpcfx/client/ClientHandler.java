package io.kimmking.rpcfx.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.Data;

@Data
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private String respStr;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf bb = (ByteBuf)msg;
            byte[] respByte = new byte[bb.readableBytes()];
            bb.readBytes(respByte);
            setRespStr(new String(respByte));
            System.err.println("client--收到响应：" + respStr);
        } finally{
            // 必须释放msg数据
            ReferenceCountUtil.release(msg);
        }
    }
}
