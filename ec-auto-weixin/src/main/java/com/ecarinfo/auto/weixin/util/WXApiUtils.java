package com.ecarinfo.auto.weixin.util;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.ecarinfo.frame.httpserver.core.http.util.JsonUtils;

public class WXApiUtils {
	private static Logger logger = Logger.getLogger(WXApiUtils.class);
	
	public static boolean sendCustomTextMsg(String openId, String token, String text) {

		StringBuffer buf = new StringBuffer();
		buf.append("{\"touser\":\"").append(openId).append("\",\"msgtype\":\"text\",\"text\":{\"content\":\"");
		buf.append(text);
		buf.append("\"}}");
		String respStr = null;
		boolean success = false;
		try {
			logger.info("=========send wcx user msg:" + buf.toString());
			respStr = HttpClientUtils.httpPostTextForHtml("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token, null, buf.toString());
			int errcode = JsonUtils.getObjectMapper().readTree(respStr.trim()).get("errcode").asInt();
			logger.info("=========send wcx msg with return msg:" + respStr);
			if (errcode==0) {
				success = true;
			}
		} catch (IOException e1) {
			success = false;
			logger.error("==============error in send custom msg openId:"+openId, e1);
		}
		return success;
	}
	
	public static String getAccessToken(String appKey, String appSecret) {
		String getAccessTokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appKey, appSecret);
		String accessToken = null;
		try {
			String respStr = HttpClientUtils.httpGetForHtml(getAccessTokenUrl, null, null);
			//accessToken = JSON.parseObject(respStr.trim()).getString("access_token");
			accessToken = JsonUtils.getObjectMapper().readTree(respStr.trim()).get("access_token").asText();
			return accessToken;
		} catch (IOException e) {
			logger.error("getAccessTokenUrl error.", e);
		}
		return accessToken;
	}
}
