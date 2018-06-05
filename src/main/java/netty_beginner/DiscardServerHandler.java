package netty_beginner;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;


/**
 * Created by moon on 2017/4/5.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)
	
	{
		System.out.println("DiscardServerHandler create");
	}
	public static Channel channel = null;
	
    @Override  
    public void channelActive(ChannelHandlerContext ctx){  
        System.out.println("--------------------------------handler channelActive------------");  
        if(channel!=null) {
        	System.out.println(channel.equals(ctx.channel()));
        }
        channel=ctx.channel();
//      for(int i = 0; i<10; i++){  
//          SubscribeReq req = new SubscribeReq();  
//          req.setAddress("深圳JJYY");  
//          req.setPhoneNumber("13888886666");  
//          req.setProductName("Netty Book");  
//          req.setSubReqID(i);  
//          req.setUserName("XXYY");  
//          ctx.write(req);  
//      }  
//      ctx.flush();  
    } 
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { // (2)
//        super.channelRead(ctx, msg);
        //((ByteBuf) msg).release(); // (3)
       /* ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }*/
        System.out.println("接收到的数据: [  " + msg.toString() + "   ]");  
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception { // (5)
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}