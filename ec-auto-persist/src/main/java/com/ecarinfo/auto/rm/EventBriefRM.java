package com.ecarinfo.auto.rm;
public class EventBriefRM {
	public static final String tableName="event_brief";//表名
	public static final String pk="eid";//主键
	public static final String eid="eid";//对应数据库主键,自增id
	public static final String name="name";//
	public static final String startTime="start_time";//
	public static final String endTime="end_time";//
	public static final String type="type";//
	public static final String createTime="create_time";//
	public static final String lastModify="last_modify";//
	public static final String status="status";//
	public static final String depId="dep_id";//
	public static final String userId="user_id";//用户ID
	public static final String isPublic="is_public";//是否部门公开，1：是，0：否
	public static final String eventType="event_type";//事件类型，1我的事件，2行业政策，3其它品牌，4其它事件
	public static final String eventDescription="event_description";//事件描述
	public static final String picPath="pic_path";//图片地址
}
