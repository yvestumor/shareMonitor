package com.example.capture;

import org.springframework.context.ApplicationContext;

public class BeanUtils {
	public static Object getBean(String bean) {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		return applicationContext.getBean(bean);
	}
}