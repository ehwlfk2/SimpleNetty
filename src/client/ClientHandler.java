package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Object> {
	
	@Override
	// 서버로 문자열을 던지면 서버는 문자를 좀 더 붙여서 클라이언트로 던져준다. 클라이언트는 서버로부터 문자열을 받아 channelRead0() 메소드를 호출한다.
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 받아온 문자열을 화면에 출력
		System.out.println((String)msg);
	}

	@Override
	// 예외가 발생했을 때 호출하는 메소드
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		
		cause.printStackTrace();
		ctx.close();
	}
}
