package com.example.capture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MainController {

	@GetMapping("/shareMonitor") // 컨트롤러 
	public String shareMonitor() { 
		return "shareMonitor";	
		
		}
	}
