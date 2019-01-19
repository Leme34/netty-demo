package hellowrold;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {

    public static void main(String[] args) throws InterruptedException {

        //1、定义一对主从线程组
        //主线程组接受客户端的连接 交给 从线程组处理
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        NioEventLoopGroup subGroup = new NioEventLoopGroup();

        //2、创建netty服务器启动类
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(mainGroup, subGroup)          //设置主从线程组
                    .channel(NioServerSocketChannel.class)  //设置nio通道
                    .childHandler(new HelloServerInitializer()); //管道初始化器,指定从线程组处理所需要的子处理器

            //3、启动server，绑定8088端口，同步（监听等待）的方式启动
            //调用sync()会等待前面的方法执行完毕
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

            //4、同步（监听）关闭的channel
            channelFuture.channel().closeFuture().sync();
        } finally {
            //5、关闭线程池
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }

}
