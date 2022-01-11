package client;

import java.net.InetSocketAddress;
import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;


/**
 * Echo Server로 문자열을 전송하는 Client를 구현
 */
public class Client {
	private static final int SERVER_PORT = 11011;
	private final String host;
	private final int port;

	private Channel serverChannel;
	private EventLoopGroup eventLoopGroup;

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void connect() throws InterruptedException {
		// NIO를 사용하기 위해 EventLoopGroup을 생성한다.
		eventLoopGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("client"));

		// Bootstrap 생성 및 설정
		Bootstrap bootstrap = new Bootstrap().group(eventLoopGroup);
		bootstrap.channel(NioSocketChannel.class);
		// remoteAddress() 메소드로 접속할 서버 소켓의 주소와 포트를 입력해준다.
		bootstrap.remoteAddress(new InetSocketAddress(host, port));
		// handler() 메소드로 ClientInitializer()를 넘겨준다.
		bootstrap.handler(new ClientInitializer());

		// connect() 메소드로 서버 소켓에 연결을 하고 sync() 메소드로 기다린다.
		serverChannel = bootstrap.connect().sync().channel();
	}

	private void start() throws InterruptedException {
		Scanner scanner = new Scanner(System.in);

		String msg;
		ChannelFuture future;
		
		// Client 시작 : ChannelFuture channelFuture = clientBootstrap.connect().sync();
		while(true) {
			// 사용자 입력
			msg = scanner.nextLine();
			
			// Server로 전송
			future = serverChannel.writeAndFlush(msg.concat("\n"));
			
			if("quit".equals(msg)) {
				// Client 종료 : channelFuture.channel().closeFuture().sync();
				serverChannel.closeFuture().sync();
				break;
			}
		}
		
		// 종료되기 전 모든 메시지가 flush 될때까지 waiting
		if(future != null) {
			scanner.close();
			future.sync();
		}
	}
	
	public void close() {
		eventLoopGroup.shutdownGracefully();
	}
	
	public static void main(String[] args) throws Exception {
		// local host = 127.0.0.1
		Client client = new Client("10.20.10.109", SERVER_PORT);
		
		try {
			client.connect();
			client.start();
		} finally {
			client.close();
		}
	}
}
