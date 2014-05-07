package com.ecarinfo.auto.backend.web.helper;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class PerformanceAspect {
	private Logger logger = Logger.getLogger("execute");
	
	@Around("execution (* com.ecarinfo.auto.backend.web.action.*.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {  
        StopWatch clock = new StopWatch();
        clock.start();
        Object retVal = pjp.proceed();
        clock.stop();
        logger.info(String.format("[ASPECT-METHOD]:%s ,[EXECUTE COST]:%", pjp.getSignature(), clock.getTotalTimeMillis())); 
        return retVal;  
    }  
}
