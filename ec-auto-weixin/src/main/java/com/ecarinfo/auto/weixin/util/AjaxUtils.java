package com.ecarinfo.auto.weixin.util;

import org.apache.log4j.Logger;

import com.ecarinfo.frame.httpserver.core.http.util.JsonUtils;


public class AjaxUtils {
	private static Logger logger = Logger.getLogger(AjaxUtils.class);

	public static AjaxResponse genError(){

		AjaxResponse ar = new AjaxResponse();
		ar.setSucc("N");
		String c = JsonUtils.Object2JsonString(ar);
		return ar;
	}
	public static AjaxResponse genError(String code,String desc){
		AjaxResponse ar = new AjaxResponse();
		ar.setSucc("N");
		ar.setCode(code);
		ar.setDesc(desc);
		String c = JsonUtils.Object2JsonString(ar);
		return ar;
	}

	public static AjaxResponse genSucc() {
		AjaxResponse ar = new AjaxResponse();
		ar.setSucc("Y");
		String c = JsonUtils.Object2JsonString(ar);
		return ar;
	}

	
	public static AjaxResponse genSucc(Object jsonData) {
		AjaxResponse ar = new AjaxResponse();
		ar.setSucc("Y");
		ar.setJsonData(jsonData);		
		String c = JsonUtils.Object2JsonString(ar);
		return ar;
	}

	public static AjaxResponse genSucc( String html) {
		AjaxResponse ar = new AjaxResponse();
		ar.setSucc("Y");
		ar.setHtml(html);		
		String c = JsonUtils.Object2JsonString(ar);
		return ar;
	}
	
}
