package com.ecarinfo.auto.rm;
public class ViewpointRM {
	public static final String tableName="viewpoint";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String name="name";//观点名称
	public static final String isManual="is_manual";//0: 系统自动 1:手工添加
	public static final String affection="affection";//情感说明 3:正面 2:中性 1:负面
	public static final String isValid="is_valid";//1:有效 0:无效
	public static final String ctime="ctime";//
	public static final String status="status";//状态
	public static final String viewpointTypeId="viewpoint_type_id";//观点分类ID
}
