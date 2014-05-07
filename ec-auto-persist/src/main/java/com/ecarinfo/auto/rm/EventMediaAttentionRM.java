package com.ecarinfo.auto.rm;
public class EventMediaAttentionRM {
	public static final String tableName="event_media_attention";//表名
	public static final String pk="emid";//主键
	public static final String emid="emid";//对应数据库主键,
	public static final String eid="eid";//事件ID
	public static final String esid="esid";//事件关键词ID，对应event_keyword_search表主键
	public static final String mediaType="mediaType";//媒体类别，1：新闻；2：博客；3：论坛；4：微博
	public static final String pubtime="pubtime";//关注日期
	public static final String siteName="siteName";//数据来源站点名称
	public static final String dataNumber="dataNumber";//数据量
	public static final String updateTime="updateTime";//记录更新时间
	public static final String siteid="siteid";//站点ID，便于页面查询。1：百度新闻，2：Google新闻，3：新浪微博，4：腾讯微博
}
