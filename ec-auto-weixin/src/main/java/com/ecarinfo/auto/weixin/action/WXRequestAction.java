package com.ecarinfo.auto.weixin.action;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ecarinfo.auto.po.WxUserInfo;
import com.ecarinfo.auto.rm.WxUserInfoRM;
import com.ecarinfo.auto.weixin.dto.enums.WXClickMenus;
import com.ecarinfo.auto.weixin.dto.request.FullWXRequest;
import com.ecarinfo.auto.weixin.dto.response.WXResponseTemplates;
import com.ecarinfo.frame.httpserver.core.annotation.Customized;
import com.ecarinfo.frame.httpserver.core.annotation.MessageModule;
import com.ecarinfo.frame.httpserver.core.annotation.RequestURI;
import com.ecarinfo.frame.httpserver.core.http.bean.ParamValue;
import com.ecarinfo.frame.httpserver.core.http.util.HttpParamsUtils;
import com.ecarinfo.frame.httpserver.core.type.RequestMethod;

@Component
@MessageModule("/")
public class WXRequestAction extends BaseAction{
	private static Logger logger = Logger.getLogger(WXRequestAction.class);
	
	@Customized
	@RequestURI(value = "/weixin", method = RequestMethod.POST)
	public void dispatcher(HttpRequest request, HttpResponse response) {
		FullWXRequest wxrequest = getFullWXRequest(request);
		
		if (wxrequest.isCheck()) {			
			sayYesToWeixin(wxrequest, response);		
		}

		logger.info("[RECV_MSG]:" + wxrequest);
		Map<String, String> model = null;
		if (wxrequest.getEvent()!=null) {
			if (wxrequest.getEvent().equals("CLICK") && wxrequest.getEventKey()!=null) {			
				switch (WXClickMenus.getFromMenuKey(wxrequest.getEventKey())) {				
				case VIEWPOINT:
					model = wxRequestService.clickViewpoint(wxrequest);
					break;
				case SEVEN_DAY_ATTENTION_TREND:
					model = wxRequestService.clickSevenDayAttentionTrend(wxrequest);
					break;
				case BAD_INDUSTRY_ARTICLE:
					model = wxRequestService.clickBadArticle(wxrequest);
					break;
				case INDUSTRY_ARTICLE:
					model = wxRequestService.clickIndustryArticle(wxrequest);
					break;
				case SETTING:
					model = wxRequestService.clickSetting(wxrequest);
					break;
				default:
					break;
				}	
			} else if (wxrequest.getEvent().equals("subscribe")) {
				model = wxRequestService.subscribe(wxrequest);
			} else if (wxrequest.getEvent().equals("unsubscribe")) {
				wxRequestService.unsubscribe(wxrequest);
			}
		} else if(wxrequest.getMsgType().equals("text") || wxrequest.getMsgType().equals("voice")){
			
		} else if (wxrequest.getRecognition()!=null){ 
			
		} else { 
			
		}	
		
		if (model!=null ) {
			String xml = WXResponseTemplates.parseTemplate(model.get("msgType"), model);
			logger.info("[XML]:" + xml);
			response.setContent(ChannelBuffers.copiedBuffer(xml, CharsetUtil.UTF_8));
			response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
		}
	}
	
	private FullWXRequest getFullWXRequest(HttpRequest request){
		Map<String, ParamValue> headerParams = HttpParamsUtils.getGetParamsMap(request);
		Map<String, String> params= readWXRequestContents(request);
		FullWXRequest r = new FullWXRequest();
		if (headerParams.containsKey("org_code")) {
			r.setOrgCode(headerParams.get("org_code").getVal());
		} 
		if (headerParams.containsKey("signature")) {
			r.setSignature(headerParams.get("signature").getVal());
		}  
		if (headerParams.containsKey("timestamp")) {
			r.setTimestamp(headerParams.get("timestamp").getVal());
		} 
		if (headerParams.containsKey("nonce")) {
			r.setNonce(headerParams.get("nonce").getVal());
		} 
		if (headerParams.containsKey("echostr")) {
			r.setEchostr(headerParams.get("echostr").getVal());
		} 
		try {
			for (Entry<String, String> e : params.entrySet()) {
				BeanUtils.setProperty(r, StringUtils.uncapitalize(e.getKey()), e.getValue());
			}
		} catch (Exception e) {
			logger.error("error.", e);
		}	
		if (params.containsKey("check")) {
			r.setCheck(true);
		}
		
		Integer userId = getUserIdByOpenId(r.getFromUserName());
		r.setUserId(userId);
		
		return r;
	}
	
	public void sayYesToWeixin(FullWXRequest r, HttpResponse response) {
		response.setContent(ChannelBuffers.copiedBuffer(r.getEchostr(), CharsetUtil.UTF_8));
		response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
	}
	
	private boolean isFromWXPlatform(FullWXRequest r, String token) {
		String[] params = new String[]{token, r.getTimestamp(), r.getNonce()};
		Arrays.sort(params);
		if (r.getSignature().equals(DigestUtils.sha1Hex(params[0]+params[1]+params[2]))) {
			return true;
		}
		return false;		
	}
	
	private Map<String, String> readWXRequestContents(HttpRequest request) {
		Map<String, String> kvs = new HashMap<String, String>();
		SAXReader saxReader = new SAXReader();
		String postData = null;
		try {
			postData = HttpParamsUtils.getPostData(request);
			logger.info("[WXREQUEST_DATA]:" + postData);
			if (postData!=null && postData.trim().length()>0) {
				saxReader.setEncoding("UTF-8");
				Document document = saxReader.read(new ByteArrayInputStream(postData.getBytes(Charset.forName("UTF-8"))));
				Element root = document.getRootElement();
				
				for (Object o : root.elements()) {
					Element e = (Element)o;
					String name = e.getName();
					String value = e.getTextTrim();
					kvs.put(name, value);		
				}
			} else {
				kvs.put("check", "true");
			}		
		} catch (DocumentException e) {
			logger.error("readWXRequestContents ERROR FOR POST_DATA:" + postData,e);
		}
		return kvs;
	}	
	
	private Integer getUserIdByOpenId(String openId) {
		WxUserInfo wxUser = baseService.findOneByAttr(WxUserInfo.class, WxUserInfoRM.openId, openId);
		if (wxUser==null) {
			return null;
		} else {
			return wxUser.getUserId();
		}
	}
}
