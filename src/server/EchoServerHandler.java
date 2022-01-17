package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Client로부터 메세지를 받았을 때, 처리할 Handler 클래스.
 * 
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	// 결국 Client에서 메시지가 날라오면 실행되는 Method 
	// 문자열을 전달받아서 채널에 "Response" 문자열과 "received\n" 문자열을 앞뒤에 붙여서 다시 전달해준다.
	public void channelRead(ChannelHandlerContext context, Object message) {
		
		// 형변환 체크 안해도 되나?
		String msg = (String)message;
		
		Channel channel = context.channel();
		channel.writeAndFlush("Response : '" + msg + "' received\n");
		
		if("quit".equals(msg)) {
			context.close();
		}
	}
}
