package com.ecarinfo.auto.weixin.dto.response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WXResponseTemplates {
	
	public static Map<String, WXResponseTemplate> templates = new HashMap<String, WXResponseTemplate>();
	static {
		loadWXResponseTemplates();
	}
	
	public static String parseTemplate(String templateKey, Map<String, String> params) {
		WXResponseTemplate template = templates.get(templateKey);
		if (template!=null) {
			return template.parse(params);
		} 
		return null;
	}
	
	private static void loadWXResponseTemplates() {
		InputStream is = WXResponseTemplates.class.getResourceAsStream("/wx_response_templates.properties");
		Scanner scanner = new Scanner(is);
		StringBuffer buf = new StringBuffer();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if (!line.isEmpty()) {
				buf.append(line);
			}		
			if (line.startsWith("</xml>")) {
				buf.append(line);
				WXResponseTemplate template = new WXResponseTemplate(buf.toString());
				templates.put(template.getTemplateKey(), template);
				buf = new StringBuffer();
			}
		}
	}
	
	public static class WXResponseTemplate {
		static Pattern expressionPattern = Pattern.compile("\\$\\{\\w+\\}");
		private String templateKey;
		List<WXResponseTemplatePart> parts = new ArrayList<WXResponseTemplatePart>();
		Map<String, WXResponseTemplatePart> expressionParts = new HashMap<String, WXResponseTemplatePart>();
		public WXResponseTemplate(String templateText) {
			parseTemplateText(templateText);
		}
		
		private void parseTemplateText(String templateText) {
			System.out.println("==========" + templateText);
			templateKey = getKey(templateText);
			 Matcher matcher = expressionPattern.matcher(templateText);
			 int preEnd = 0;
			 while (matcher.find()) {
				 String group = matcher.group(0);
	 				
				 int start = matcher.start();
				 WXResponseTemplatePart part = new WXResponseTemplatePart();
				 String partValue = templateText.substring(preEnd, start);
				 if (partValue.contains("<MsgType><![CDATA[news1]]></MsgType>")) {
					 partValue = partValue.replace("<MsgType><![CDATA[news1]]></MsgType>", "<MsgType><![CDATA[news]]></MsgType>");
				 } else if (partValue.contains("<MsgType><![CDATA[news2]]></MsgType>")) {
					 partValue = partValue.replace("<MsgType><![CDATA[news2]]></MsgType>", "<MsgType><![CDATA[news]]></MsgType>");
				 } else if (partValue.contains("<MsgType><![CDATA[news3]]></MsgType>")) {
					 partValue = partValue.replace("<MsgType><![CDATA[news3]]></MsgType>", "<MsgType><![CDATA[news]]></MsgType>");
				 } else if (partValue.contains("<MsgType><![CDATA[news4]]></MsgType>")) {
					 partValue = partValue.replace("<MsgType><![CDATA[news4]]></MsgType>", "<MsgType><![CDATA[news]]></MsgType>");
				 } else if (partValue.contains("<MsgType><![CDATA[news5]]></MsgType>")) {
					 partValue = partValue.replace("<MsgType><![CDATA[news5]]></MsgType>", "<MsgType><![CDATA[news]]></MsgType>");
				 }else if (partValue.contains("<MsgType><![CDATA[news6]]></MsgType>")) {
					 partValue = partValue.replace("<MsgType><![CDATA[news6]]></MsgType>", "<MsgType><![CDATA[news]]></MsgType>");
				 }else if (partValue.contains("<MsgType><![CDATA[news7]]></MsgType>")) {
					 partValue = partValue.replace("<MsgType><![CDATA[news7]]></MsgType>", "<MsgType><![CDATA[news]]></MsgType>");
				 }else if (partValue.contains("<MsgType><![CDATA[news8]]></MsgType>")) {
					 partValue = partValue.replace("<MsgType><![CDATA[news8]]></MsgType>", "<MsgType><![CDATA[news]]></MsgType>");
				 }else if (partValue.contains("<MsgType><![CDATA[news9]]></MsgType>")) {
					 partValue = partValue.replace("<MsgType><![CDATA[news9]]></MsgType>", "<MsgType><![CDATA[news]]></MsgType>");
				 }else if (partValue.contains("<MsgType><![CDATA[news10]]></MsgType>")) {
					 partValue = partValue.replace("<MsgType><![CDATA[news10]]></MsgType>", "<MsgType><![CDATA[news]]></MsgType>");
				 }
				 part.setValue(partValue);
				 parts.add(part);
				 preEnd = matcher.end();
				 
				 WXResponseTemplatePart expressionpart = new WXResponseTemplatePart();
				 expressionpart.setExpression(group);
				 expressionParts.put(group, expressionpart);
				 parts.add(expressionpart);
			 }
			 
			 
			 WXResponseTemplatePart lastPart = new WXResponseTemplatePart();
			 lastPart.setValue(templateText.substring(preEnd));
			 parts.add(lastPart);		
		}
		
		public String parse(Map<String, String> params) {
			StringBuffer buf = new StringBuffer();
			for (WXResponseTemplatePart part : parts) {
				if (!part.isExpressionPart()) {
					buf.append(part.getValue());
				} else {
					String value = params.get(part.getExpression());
					if (value!=null) {
						buf.append(value);
					}
				}
			}
			return buf.toString();
		}
		
		//<MsgType><![CDATA[video]]></MsgType>
		private static final String MSG_TYPE_START_PART = "<MsgType><![CDATA[";
		private static final String MSG_TYPE_END_PART = "]]></MsgType>";
		private String getKey(String templateText) {
			int pos = templateText.indexOf(MSG_TYPE_START_PART);
			int start = pos + MSG_TYPE_START_PART.length();
			int end = templateText.indexOf(MSG_TYPE_END_PART);
			return templateText.substring(start, end);
		}
		
		public String getTemplateKey() {
			return templateKey;
		}

		@Override
		public String toString() {
			return "WXResponseTemplate [expressionPattern=" + expressionPattern
					+ ", templateKey=" + templateKey + ", parts=" + parts
					+ ", expressionParts=" + expressionParts + "]";
		}
		
	}
	
	public static class WXResponseTemplatePart {
		private String expression;
		private String value;
		public String getExpression() {
			return expression;
		}
		public void setExpression(String expression) {
			this.expression = expression;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}	
		public boolean isExpressionPart() {
			return expression==null?false:true;
		}
		@Override
		public String toString() {
			return "WXResponseTemplatePart [expression=" + expression
					+ ", value=" + value + "]";
		}
		
	}
	
	public static void main(String[] args) {
		//^\d(\d{3,11})\d$
		//((?<=^)|(?<=\\s)|(?<=[^\\w]))\\$[0-9]*(\\.[0-9]{1,2})*
		//String text = "$.2 $34.567,$56.78";
//		Pattern expressionPattern = Pattern.compile("((?<=^)|(?<=\\s)|(?<=[^\\w]))");
//		Matcher matcher = expressionPattern.matcher("<![CDATA[#toUserName#]]></ToUserName>");
//		 while (matcher.find()) {
//			 System.out.println(matcher.group());
//		 }
		//System.out.println(expressionPattern.matcher("<![CDATA[#toUserName#]]></ToUserName>").group());
		
		for (Entry<String, WXResponseTemplate> e : WXResponseTemplates.templates.entrySet()) {
			WXResponseTemplate t = e.getValue();
			StringBuffer buf = new StringBuffer();
			for (WXResponseTemplatePart p : t.parts) {
				buf.append("-----------");
				if (p.isExpressionPart()) {
					buf.append(p.getExpression());
				} else {
					buf.append(p.getValue());
				}
			}
			System.out.println(buf.toString());
		}
		
	}
}
