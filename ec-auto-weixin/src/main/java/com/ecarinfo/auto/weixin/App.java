package com.ecarinfo.auto.weixin;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.ecarinfo.auto.weixin.util.ConfigUtils;
import com.ecarinfo.frame.httpserver.core.http.ECHttpServer;

public class App {
	static Logger logger = Logger.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	logger.info("Spring container of ECAutoHttpServer init finished...");
    	ConfigUtils config = (ConfigUtils)ctx.getBean("configUtils");
    	int port = config.getPort();
    	new ECHttpServer(ctx, port).run();
    }
}
