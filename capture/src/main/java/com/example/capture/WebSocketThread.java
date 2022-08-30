package com.example.capture;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.capture.service.WebSocketService;

public class WebSocketThread extends Thread{
	WebSocketService webSocketService = (WebSocketService)BeanUtils.getBean("webSocketService");
	private HashMap<Integer, WebSocketSession> sessionMap;
	public WebSocketThread(HashMap<Integer,WebSocketSession> sessionMap) {
		this.sessionMap = sessionMap;
	}
	@Override
	public synchronized void run() {
			try {
				while(!sessionMap.isEmpty()) { // sessionMap이 null이아니면 
					String captureData = webSocketService.capture();
				for(int i=0; i<sessionMap.size(); i++) { // sessionMap size만큼 반복 
					WebSocketSession wss = sessionMap.get(i);
					wss.sendMessage(new TextMessage(captureData)); // sessionMap 에이있는 모든 사용자에게 값 주기
					Thread.sleep(10); // 0.01초
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
				}
		}
	}
