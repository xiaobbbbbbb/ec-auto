package com.ecarinfo.auto.backend.web.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CommonExceptionResolver implements HandlerExceptionResolver {		
	private Logger logger = Logger.getLogger(getClass());
	

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error("-------------[EXCEPTION FORM URL]: " + getCurrentFullURL(request) + " [MSG]: " + ex.getMessage());
		
		 StringPrintWriter stringPrintWriter = new StringPrintWriter();  
	        ex.printStackTrace(stringPrintWriter);  
	    request.setAttribute("errorMsg", stringPrintWriter.getString());    
		logger.error("-------------[EXCEPTION FORM URL]: " + getCurrentFullURL(request) + " [DETAIL]: " + stringPrintWriter.getString());//把漏网的异常信息记入日志  
        return new ModelAndView("/error/exception");  
	}
	
	private String getCurrentFullURL(HttpServletRequest request) {
		String fullUrl = "http://"+request.getServerName()+request.getHeader("realurl");
		return fullUrl;
	}
}
