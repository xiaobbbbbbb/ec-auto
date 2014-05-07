package com.ecarinfo.auto.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.auto.po.IndustryCloud;

public interface IndustryCloudDao extends ECDao<IndustryCloud> {

	Long countIndustryArticleByCloudId(@Param("cloudId") Long cloudId);
	
	List<Map<String, Object>> findIndustryArticleByCloudId(@Param("cloudId") Long cloudId,@Param("offset") Integer offset,@Param("rowsPerPage") Integer rowsPerPage);
	
}
