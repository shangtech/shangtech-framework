package net.shangtech.framework.util;

import org.springframework.context.ApplicationContext;


public class SpringUtils {
	
	private static ApplicationContext ctx;
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		return (T) ctx.getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz){
		return ctx.getBean(clazz);
	}
	
	public static void setApplicationContext(ApplicationContext ctx){
		SpringUtils.ctx = ctx;
	}
}
