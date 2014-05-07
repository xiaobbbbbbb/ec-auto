package com.ecarinfo.auto.service;

import java.util.List;

import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.CloudVO;
import com.ecarinfo.persist.paging.ECPage;

public interface CloudService {
	
	List<CloudVO> findHotClouds(Integer maxRows,String ctx);
	
	ECPage<ArticleSimpleListVO> findArticlesByCloudId(Long cloudId,Integer pageNo,Integer pageSize);
	
}
