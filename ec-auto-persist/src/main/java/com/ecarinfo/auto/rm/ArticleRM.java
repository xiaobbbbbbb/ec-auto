package com.ecarinfo.auto.rm;
public class ArticleRM {
	public static final String tableName="article";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String title="title";//标题
	public static final String affection="affection";//情感说明 3:正面 2:中性 1：负面'
	public static final String simpleContent="simple_content";//摘要内容
	public static final String grade="grade";//内容等级 1一般 2严重
	public static final String url="url";//原网页地址
	public static final String brandId="brand_id";//品牌id
	public static final String serialId="serial_id";//产品id
	public static final String desTarget="des_target";//描述对象（1主机厂，2:4s店，3：其它）
	public static final String desContent="des_content";//描述内容（1产品、3服务、4其它）
	public static final String hasComment="has_comment";//是否有评论（1有，0无）
	public static final String hasViewpoint="has_viewpoint";//是否有观点
	public static final String mediaType="media_type";//传媒分类(1论坛,2新闻,3博客,4,微博，0其它）
	public static final String mediaId="media_id";//站点ID
	public static final String cityId="city_id";//城市ID
	public static final String provinceId="province_id";//省份ID
	public static final String areaId="area_id";//区域id
	public static final String articleCtime="article_ctime";//文章发布时间
	public static final String articleDate="article_date";//文章创建时间
	public static final String ctime="ctime";//
	public static final String status="status";//
	public static final String originalId="original_id";//
	public static final String questionId="question_id";//
	public static final String eventId="event_id";//事件ID
}
