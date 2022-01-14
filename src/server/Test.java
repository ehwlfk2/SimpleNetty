package server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

@SuppressWarnings("rawtypes")
public class Test {
	ChannelHandlerAdapter cha;
	MessageToByteEncoder mbe;
	ByteToMessageDecoder btd;
	ChannelHandlerContext chc;
}
