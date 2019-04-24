package hellowrold;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * channel注册后会执行初始化方法 initChannel()
 */
public class HelloServerInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        //1、取得对应的管道
        ChannelPipeline pipeline = channel.pipeline();
        //2、往管道添加handler
        //可理解为netty提供的编解码拦截器
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());
        //自定义处理器
        pipeline.addLast("CustomHandler",new CustomHandler());
    }
}
