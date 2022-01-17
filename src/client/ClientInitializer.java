package client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 채널 파이프라인을 생성해주는 Initializer
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) {
		// 서버와 비슷하게 채널 파이프라인을 생성한다.
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast(new LineBasedFrameDecoder(65536));
		pipeline.addLast(new StringDecoder());
		pipeline.addLast(new StringEncoder());
		pipeline.addLast(new ClientHandler());
	}

}
