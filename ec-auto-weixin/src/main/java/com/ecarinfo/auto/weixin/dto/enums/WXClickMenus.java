package com.ecarinfo.auto.weixin.dto.enums;

/**
 * 微车信 click类型的菜单
 *
 */
public enum WXClickMenus {
	VIEWPOINT, //产品观点
	SEVEN_DAY_ATTENTION_TREND, //7天关注趋势
	
	BAD_INDUSTRY_ARTICLE, //负面情报
	INDUSTRY_ARTICLE, //行业动态
	SETTING	 //设置
	;
	
	public static WXClickMenus getFromMenuKey(String key) {
		return WXClickMenus.valueOf(key.toUpperCase());
	}
}
