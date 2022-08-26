package com.example.capture.service;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
@Service 
public class WebSocketService {
	public String capture() {
		Robot r; // 캡쳐할 robot클래스 
		byte[] bytes = null; 
		try {
			// 캡쳐할 환경?? 화면 ?
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			r = new Robot(gd); 
			Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); // 캡쳐할 화면 지정하기 
			BufferedImage img = r.createScreenCapture(rec); // bufferedImage에 캡처한화면 저장 
			ByteArrayOutputStream baos = new ByteArrayOutputStream();	// ByteArray로 가져오기위해서 
			ImageIO.write(img, "png", baos); // bufferedImage 확장자를 png파일로 바꾸고 BytreArray로 가져오기
			  bytes = baos.toByteArray(); // byte배열에 저장
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Base64.encodeBase64String(bytes); // base64로 인코딩해서 return함
	}
}
