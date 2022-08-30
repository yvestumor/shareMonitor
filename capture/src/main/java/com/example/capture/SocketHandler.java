package com.example.capture;

import java.util.HashMap;

import javax.swing.text.FlowView.FlowStrategy;

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
	HashMap<Integer, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘 맵
	static int sessionCount = 0;
	@Autowired WebSocketService webSocketService;
	@Override //handleTextMessage는 클라언트가 메시지를 보낸것을 반환 하는역할임
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
	}
	
	@Override //afterConnectionEstablished 는 서버에 클라이언트가 접속했을때 실행됨 
	   public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	      //소켓 연결
	      super.afterConnectionEstablished(session);
	      log.debug(CF.RD+sessionCount+CF.RESET);
	      sessionMap.put(sessionCount, session);
	      sessionCount++;
	      WebSocketThread wst = new WebSocketThread(sessionMap);
	    	  wst.start();
	} 
	
	@Override //afterCOnnectionClosed는 소켓연결이 끊겼을떄 사용하는 메서드임
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//소켓 종료
		log.debug(CF.RD+"소켓 종료 :"+status+CF.RESET); 
		super.afterConnectionClosed(session, status);
		for(Integer key : sessionMap.keySet()) {
			if(sessionMap.get(key).equals(session)) {
				sessionMap.remove(key);
				}
			}
		if(!sessionMap.isEmpty()) {
		  WebSocketThread wst = new WebSocketThread(sessionMap);
    	  wst.start();
			}
		}
	}
