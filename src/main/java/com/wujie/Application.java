package com.wujie;


import com.wujie.app.framework.netty.WebsocketDanmuServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 *************************************************
 * 	功能描述:  应用程序主入口                  
 * @author Mr.Chen
 * @version 1.0                
 * @date    2018年10月19日 创建文件                                 
 * @see                        
 *************************************************
 */
@EnableScheduling
@SpringBootApplication
public class Application   {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		applicationContext.getBean(WebsocketDanmuServer.class).run();
	}

}
