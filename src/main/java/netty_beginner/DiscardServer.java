package netty_beginner;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by moon on 2017/4/5.
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
    	EventLoopGroup bossGroup = new NioEventLoopGroup();  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
                  
        try{  
            ServerBootstrap b = new ServerBootstrap();  
            b.group(bossGroup, workerGroup)  
            .channel(NioServerSocketChannel.class)  
            .option(ChannelOption.SO_BACKLOG, 100)  
            .handler(new LoggingHandler(LogLevel.INFO))  
            .childHandler(new ChannelInitializer<SocketChannel>(){  
                @Override  
                public void initChannel(SocketChannel ch) throws Exception{  
                    ch.pipeline()  
                    .addLast(  
                            new ObjectDecoder(1024*1024,  
                                    ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))  
                    .addLast(new ObjectEncoder())  
                    .addLast(new DiscardServerHandler());  
                }  
            });  
              
            ChannelFuture f = b.bind(port).sync();  
            System.out.println("---------------wait for connect");  
            f.channel().closeFuture().sync();  
        }finally {  
            System.out.println("---------------wait for connect  Error!");  
            bossGroup.shutdownGracefully();  
            workerGroup.shutdownGracefully();  
        }  
    }

    public static void main(String[] args) throws InterruptedException {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new DiscardServer(port).run();
    }
}