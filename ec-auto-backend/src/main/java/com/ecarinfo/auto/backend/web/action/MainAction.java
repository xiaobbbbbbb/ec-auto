package com.ecarinfo.auto.backend.web.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("main")
@Controller
public class MainAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(MainAction.class);
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {
		
		return "/index";
	}
}
