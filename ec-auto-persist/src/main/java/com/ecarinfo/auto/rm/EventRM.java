package com.ecarinfo.auto.rm;
public class EventRM {
	public static final String tableName="event";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String titile="titile";//主题
	public static final String keywords="keywords";//
	public static final String type="type";//类型，1.社会热点，2行业事件，3我的品牌事件，4其它品牌事件
	public static final String startTime="start_time";//起始时间
	public static final String endTime="end_time";//结束时间
	public static final String contents="contents";// 事件描述
	public static final String status="status";//事件开关，0关闭，1开启
	public static final String imgUrl="img_url";//图片地址
	public static final String firstArticleId="first_article_id";//
	public static final String firstArticleTime="first_article_time";//第一次报道
	public static final String ctime="ctime";//
}
