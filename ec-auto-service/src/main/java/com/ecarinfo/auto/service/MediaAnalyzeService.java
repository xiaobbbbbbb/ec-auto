/**
 * 
 */
package com.ecarinfo.auto.service;

import java.util.Date;
import java.util.List;

import com.ecarinfo.auto.po.NetworkMediaAnalysis;
import com.ecarinfo.auto.po.WeiboAnalysis;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.PositiveViewVO;
import com.ecarinfo.auto.vo.WeiboAnalysisVO;
import com.ecarinfo.persist.paging.ECPage;


/**
 * 网媒分析
 * @author yinql
 *
 */
public interface MediaAnalyzeService {
	/**
	 * 网站访问量排名前10
	 */
	public List<NetworkMediaAnalysis> getNetworkMediaTop10();
	
	/**
	 * 微博前10
	 * @return
	 */
	public List<WeiboAnalysisVO> getWeiboAnalysisTop10();
	
	/**
	 * 微博查看更多
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ECPage<WeiboAnalysisVO> getWeibolist(int pageNo,int pageSize);
	
	/**
	 * 所有媒体排行数据
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ECPage<NetworkMediaAnalysis> getMedialist(int pageNo,int pageSize);
	
}
