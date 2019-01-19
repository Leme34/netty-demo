package hellowrold;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 自定义处理器
 */
public class CustomHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读channel消息
     * @param ctx channel处理器上下文
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 只处理http请求
        if (msg instanceof HttpRequest) {
            //1、获取channel
            Channel channel = ctx.channel();
            //打印客户端远程地址
            System.out.println(channel.remoteAddress());

            //2、定义发送的消息内容
            String content = "Hello Netty";
            ByteBuf contentByteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);

            //3、创建响应体,并设置返回头信息
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    contentByteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, contentByteBuf.readableBytes());  //可读的字节数

            //4、返回体刷到客户端
            ctx.writeAndFlush(response);
        }
    }
}
