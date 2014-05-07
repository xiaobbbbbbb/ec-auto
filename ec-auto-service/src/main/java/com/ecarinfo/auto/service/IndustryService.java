/**
 * 
 */
package com.ecarinfo.auto.service;

import java.util.Date;
import java.util.List;

import com.ecarinfo.auto.po.Cloud;
import com.ecarinfo.auto.po.IndustryArticle;
import com.ecarinfo.auto.po.IndustryComment;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.TempIAVO;
import com.ecarinfo.auto.vo.TempIndustryVO;
import com.ecarinfo.persist.paging.ECPage;

/**
 * @author zb
 * 行业
 */
public interface IndustryService {
	
	/**
	 * 根据行业文章ID获取文章的详细信息
	 * @param articleId
	 * @return IndustryArticle
	 */
	public ArticleSimpleListVO getArticleByPk(Long articleId);
	

	/**
	 * 获取最新的行业动态
	 * @param limit
	 * @return List<ArticleSimpleListVO>
	 */
	public List<IndustryArticle> getLastArticlesByCarBrandId(int pageSize);
	
	/**
	 * 根据关键字获取资讯信息
	 * @param offset
	 * @param limit
	 * @param keyword
	 * @return ECPage<ArticleSimpleListVO>
	 */
	public ECPage<ArticleSimpleListVO> getArticles(String keyword,int pageNo,int pageSize);
	
	
	/**
	 * 根据多个关键字获取资讯信息，以分页升形式返回
	 * @param offset
	 * @param limit
	 * @param keyword
	 * @return ECPage<ArticleSimpleListVO>
	 */
	public ECPage<ArticleSimpleListVO> getArticles(String[] keyword,int pageNo,int pageSize);
	
	/**
	 * 根据文章ID获取具有相似性的文章列表
	 * @param offset
	 * @param limit
	 * @param articleId
	 * @return ECPage<ArticleSimpleListVO>
	 */
	public ECPage<ArticleSimpleListVO> getRelatedArticles(long articleId,int pageNo,int pageSize);
	
	
	/**
	 * 根据文章ID获取评论列表
	 * @param articleId
	 * @param pageNo
	 * @param pageSize
	 * @return ECPage<IndustryComment>
	 */
	
	public ECPage<IndustryComment> getArticleCommentsByArticleId(long articleId,int pageNo,int pageSize);
		
	/**
	 * 按条件检索行业动态信息
	 * @param startDate
	 * @param endDate
	 * @param mediaId
	 * @param title
	 * @param content
	 * @param pageNo
	 * @param pageSize
	 * @return ECPage<ArticleSimpleListVO>
	 */
	public ECPage<ArticleSimpleListVO> getIndustryArticleList(Date startDate,Date endDate,Integer mediaId,String title,String content,int pageNo,int pageSize);
	
	/**
	 * 获取行业热点列表
	 * @limit
	 */
	public List<Cloud> getClouds(int pageSize);
	
	
	public List<TempIAVO> listIndustryArticleForJob();
}
