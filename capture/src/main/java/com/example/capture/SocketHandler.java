package com.example.capture;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
	static String cap;
	HashMap<Integer, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘 맵
	static int sessionCount = 0;
	@Autowired WebSocketService webSocketService;
	@Override //handleTextMessage는 클라언트가 메시지를 보낸것을 반환 하는역할임
	public void handleTextMessage(WebSocketSession session, TextMessage message) {

	}
	
	@Override
	   public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	      //소켓 연결
	      super.afterConnectionEstablished(session);
	      log.debug(CF.RD+session.getId()+CF.RESET);
	      sessionMap.put(sessionCount, session); // 세션에 들어오는 순서대로 key값을 줌
	      log.debug(CF.RD+sessionCount+CF.RESET);//key 값 디버깅
	      sessionCount++; // sessionCount++
	      log.debug(CF.RD+"가장 낮은 key 값 : "+Collections.min(sessionMap.keySet())+CF.RESET);// 가장 낮은 key값
	      while(sessionMap.get(Collections.min(sessionMap.keySet())).equals(session)) {//HashMap에서 가장낮은 key 값의 value가 현제세션과 같으면 
	          cap = webSocketService.capture();   //base64로 인코딩한 문자 받아오기 
	         Thread.sleep(100); //Thread주기 0.01초마다 쉼
	         session.sendMessage(new TextMessage(cap));
	      		}
	      while(!sessionMap.get(Collections.min(sessionMap.keySet())).equals(session)) { //HashMap에서 가장낮은 key 값의 value가 현제세션과 같지않으면 반복 
	    	  session.sendMessage(new TextMessage(cap)); // cap 에 저장되어있는 문자만 보내기 
	    	  Thread.sleep(100); // Thread주기 0.01초 마다 쉼 
	      }
			} 
		
	 
	@Override //afterCOnnectionClosed는 소켓연결이 끊겼을떄 사용하는 메서드임
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//소켓 종료
		log.debug(CF.RD+"소켓 종료 :"+status+CF.RESET);
		for(Map.Entry<Integer, WebSocketSession> entry : sessionMap.entrySet()) {
			if(entry.getValue().equals(session)) {
				sessionMap.remove(entry.getKey());
				log.debug(CF.RD+entry.getKey()+CF.RESET);
			}
		}
		log.debug(CF.RD+"sessionMap Count : "+sessionMap.size()+CF.RESET);
		super.afterConnectionClosed(session, status);
	}
	}
