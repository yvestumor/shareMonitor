package com.example.capture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket //웹소켓 사용 어노테이션
public class WebSocketConfig implements WebSocketConfigurer{
	
	@Autowired 
	 SocketHandler socketHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) { //웹소켓 등록
		registry.addHandler(socketHandler, "/streaming");
	}
}