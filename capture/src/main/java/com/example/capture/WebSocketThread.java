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
	public synchronized void run(){ // 스레드 하나로 돌아가게해줌
		System.out.println("Start");
			try {
				while(!sessionMap.isEmpty()) {
					String captureData = webSocketService.capture();
					for(Integer i : sessionMap.keySet()) { // sessionMap size만큼 반복 
						WebSocketSession wss = sessionMap.get(i);
						wss.sendMessage(new TextMessage(captureData)); // sessionMap 에이있는 모든 사용자에게 값 주기
						Thread.sleep(10); // 0.01초
					}
				}
				if(sessionMap.isEmpty()) {
					currentThread().wait();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
				}
			
			System.out.println("Stop");
		}
	
	}
