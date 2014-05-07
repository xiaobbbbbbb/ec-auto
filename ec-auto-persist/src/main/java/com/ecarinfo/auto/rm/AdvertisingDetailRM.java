package com.ecarinfo.auto.rm;
public class AdvertisingDetailRM {
	public static final String tableName="advertising_detail";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String pid="pid";//广告父类ID 关联Advertising
	public static final String title="title";//标题
	public static final String content="content";//广告内容
	public static final String mediaId="media_id";//媒体ID
	public static final String url="url";//投放地址
	public static final String pageId="page_id";//投放版面
	public static final String planStime="plan_stime";//计划投放开始时间
	public static final String planEtime="plan_etime";//计划投放结束时间
	public static final String realStime="real_stime";//实际投放开始时间
	public static final String realEtime="real_etime";//时间投放结束时间
	public static final String status="status";//投放状态 1.未投放，2已投放，3投放完成
	public static final String realUrl="real_url";//原文链接
	public static final String transmitNum="transmit_num";//转发数量
	public static final String commentNum="comment_num";//评论数量
	public static final String isStandard="is_standard";//是否达标
	public static final String ctime="ctime";//
}
