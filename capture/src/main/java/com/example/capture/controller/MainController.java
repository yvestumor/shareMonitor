package com.example.capture.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

	@RequestMapping("/shareMonitor") // 컨트롤러 
	public ModelAndView shareMonitor() { 
		ModelAndView mv = new ModelAndView();
		mv.setViewName("shareMonitor"); // shareMonitor.jsp 
		return mv;	
	}
}
