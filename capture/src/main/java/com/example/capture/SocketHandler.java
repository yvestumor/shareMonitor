package com.example.capture;

import java.nio.channels.ClosedChannelException;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.capture.service.WebSocketService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class SocketHandler extends TextWebSocketHandler {
	static { 
        System.setProperty("java.awt.headless", "false");
	}
	HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘 맵
	@Autowired WebSocketService webSocketService;
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
	}
	

	@Override //afterConnectionEstablished는 클라이언트가 접속했을때실행되는 메서드임
	public void afterConnectionEstablished(WebSocketSession session) throws Exception,ClosedChannelException{
		//소켓 연결
		super.afterConnectionEstablished(session);
		while(true) {// 무한 반복문 
		String cap = webSocketService.capture();   // base64로 인코딩한 문자 받아오기 
		session.sendMessage(new TextMessage(cap)); // 문자보내기
		Thread.sleep(150);
			}
		}
		
		
	 
	@Override //afterCOnnectionClosed는 소켓연결이 끊겼을떄 사용하는 메서드임
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//소켓 종료
		log.debug(CF.RD+"소켓 종료 :"+status+CF.RESET);
		super.afterConnectionClosed(session, status);
	}
	}
