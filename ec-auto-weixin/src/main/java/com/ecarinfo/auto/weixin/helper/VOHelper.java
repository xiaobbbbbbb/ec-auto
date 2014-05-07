package com.ecarinfo.auto.weixin.helper;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecarinfo.persist.service.GenericService;

@Component("voHelper")
public class VOHelper {
	@Resource
	private GenericService baseService;
	@Resource 
	private CommonDataCache cache;
	private static Map<Integer, String> articleGradeMap = new HashMap<Integer, String>();
	private static Map<Integer, String> articleAffectionMap = new HashMap<Integer, String>();
	private static Map<Integer, String> articleDesTargetMap = new HashMap<Integer, String>();
	private static Map<Integer, String> articleDesContentMap = new HashMap<Integer, String>();
	private static Map<Integer, String> articleMediaTypeMap = new HashMap<Integer, String>();
	private static Map<Integer, String> articleArticleTypeMap = new HashMap<Integer, String>();
	
	private static Map<Integer, String> statusMap = new HashMap<Integer, String>();
	static {
		articleGradeMap.put(1, "1级");
		articleGradeMap.put(2, "2级");
		articleGradeMap.put(3, "3级");
		
		articleAffectionMap.put(3, "正面");
		articleAffectionMap.put(2, "中性");
		articleAffectionMap.put(1, "负面");
		
		articleDesTargetMap.put(1, "主机厂");
		articleDesTargetMap.put(2, "4s店");
		articleDesTargetMap.put(3, "其它");
		
		articleDesContentMap.put(1, "质量");
		articleDesContentMap.put(2, "价格");
		articleDesContentMap.put(3, "服务");
		articleDesContentMap.put(4, "其他");
		
		
		articleMediaTypeMap.put(1, "新闻");
		articleMediaTypeMap.put(2, "论坛");
		articleMediaTypeMap.put(3, "微博");
		
		articleArticleTypeMap.put(1, "口碑");
		articleArticleTypeMap.put(2, "负面情报");
		articleArticleTypeMap.put(3, "其他");
		
		statusMap.put(0, "初始");
		statusMap.put(1, "有效");
		statusMap.put(2, "无效");
	}
}
