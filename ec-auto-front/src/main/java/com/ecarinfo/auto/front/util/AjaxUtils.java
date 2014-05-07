package com.ecarinfo.auto.front.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class AjaxUtils {
	private static Logger logger = Logger.getLogger(AjaxUtils.class);

	public static void printError(HttpServletResponse response){

		AjaxResponse ar = new AjaxResponse();
		ar.setSucc("N");
		String c = JsonUtils.Object2JsonString(ar);
		printAjax(response,c);
	}
	public static void printError(HttpServletResponse response,String code,String desc){

		AjaxResponse ar = new AjaxResponse();
		ar.setSucc("N");
		ar.setCode(code);
		ar.setDesc(desc);
		String c = JsonUtils.Object2JsonString(ar);
		printAjax(response,c);
	}

	public static void printSucc(HttpServletResponse response) {
		AjaxResponse ar = new AjaxResponse();
		ar.setSucc("Y");
		String c = JsonUtils.Object2JsonString(ar);
		printAjax(response, c);
	}

	
	public static void printSucc(HttpServletResponse response, Object jsonData) {
		AjaxResponse ar = new AjaxResponse();
		ar.setSucc("Y");
		ar.setJsonData(jsonData);		
		String c = JsonUtils.Object2JsonString(ar);
		printAjax(response, c);
	}

	public static void printSucc(HttpServletResponse response, String html) {
		AjaxResponse ar = new AjaxResponse();
		ar.setSucc("Y");
		ar.setHtml(html);		
		String c = JsonUtils.Object2JsonString(ar);
		printAjax(response, c);
	}
	
	private static void printAjax(HttpServletResponse response, String content) {
		try {
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

}
