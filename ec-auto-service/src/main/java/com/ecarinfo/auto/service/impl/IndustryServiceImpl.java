package com.ecarinfo.auto.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ecarinfo.auto.dao.IndustryArticleDao;
import com.ecarinfo.auto.po.Cloud;
import com.ecarinfo.auto.po.IndustryArticle;
import com.ecarinfo.auto.po.IndustryComment;
import com.ecarinfo.auto.po.IndustryKeyword;
import com.ecarinfo.auto.po.MediaInfo;
import com.ecarinfo.auto.rm.IndustryArticleRM;
import com.ecarinfo.auto.rm.IndustryCommentRM;
import com.ecarinfo.auto.rm.IndustryKeywordRM;
import com.ecarinfo.auto.service.IndustryService;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.TempIAVO;
import com.ecarinfo.auto.vo.TempIndustryVO;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.Paginable;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.util.RowMapperUtils;
@Service
public class IndustryServiceImpl implements IndustryService{
	private static final Logger logger = LoggerFactory.getLogger(IndustryServiceImpl.class);
	
	//空对象，不要往里放值，只供没有查询到值时使用
	private static final ECPage<ArticleSimpleListVO> tempArticleSimpleListVO = new ECPage<ArticleSimpleListVO>();	

	@Resource
	private GenericDao genericDao;
	
	@Resource
	private IndustryArticleDao industryArticleDao;
	
	@Override
	public ArticleSimpleListVO getArticleByPk(Long articleId) {
		IndustryArticle article = genericDao.findByPK(IndustryArticle.class,articleId);
		if(article == null) {
			return null;
		}
		MediaInfo mediaInfo = genericDao.findByPK(MediaInfo.class, article.getMediaId());
		ArticleSimpleListVO vo = new ArticleSimpleListVO();
		vo.setId(article.getId());
		if(mediaInfo != null) {
			vo.setMediaName(mediaInfo.getName());
		}
		vo.setPubDate(article.getPubTime());
		vo.setUrl(article.getUrl());
		vo.setContent(article.getSimpleContent());
		vo.setTitle(article.getTitle());
		return vo;
	}
	
	/**
	 * @laochen index
	 * 获取最新的行业动态
	 * @param limit
	 * @return List<ArticleSimpleListVO>
	 */
	@Override
	public List<IndustryArticle> getLastArticlesByCarBrandId(int pageSize) {
		Criteria whereBy = new Criteria();
		whereBy.eq(IndustryArticleRM.status, 1);
		whereBy.orderBy(IndustryArticleRM.pubTime, OrderType.DESC);
		if(pageSize>0){
			whereBy.setPage(1, pageSize);
		}
		return this.genericDao.findList(IndustryArticle.class,whereBy);
	}

	/**
	 * TODO 根据关键字获取行业动态文章列表 需要调用Solr
	 */
	@Override
	public ECPage<ArticleSimpleListVO> getArticles(String keyword, int offset,int pageSize) {		
		return null;
	}
	
	/**
	 * TODO 根据多个关键字获取行业动态文章列表 需要调用Solr
	 */
	@Override
	public ECPage<ArticleSimpleListVO> getArticles(String[] keyword,int offset, int limit) {
		return null;
	}
	
	/**
	 * @laochen industry
	 * 根据文章ID获取具有相似性的文章列表
	 * @param offset
	 * @param limit
	 * @param articleId
	 * @return ECPage<ArticleSimpleListVO>
	 */
	@Override
	public ECPage<ArticleSimpleListVO> getRelatedArticles(long articleId,int pageNo, int pageSize) {
		final IndustryKeyword key = genericDao.findOneByAttr(IndustryKeyword.class, IndustryKeywordRM.industryArticleId, articleId);
		if(key == null) {
			return tempArticleSimpleListVO;
		}		
		ECPage<ArticleSimpleListVO> page = PagingManager.list(new Paginable<ArticleSimpleListVO>(pageNo,pageSize) {
			@Override
			public List<ArticleSimpleListVO> findList() {
				return RowMapperUtils.map2List(industryArticleDao.findIndustryArticleByKeywordId(key.getKeywordId(),this.getOffset(),this.getRowsPerPage()), ArticleSimpleListVO.class);
			}
			@Override
			public Long count() {
				Long c = industryArticleDao.countIndustryArticleByKeywordId(key.getKeywordId());
				return c == null?0:c;
			}
		});
		return page;
	}
	
	/**
	 * @laochen industry
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
	@Override
	public ECPage<ArticleSimpleListVO> getIndustryArticleList(Date startDate,
			Date endDate, Integer mediaId, String title, String content,
			int pageNo, int pageSize) {
		try {

			final Criteria whereBy = new Criteria();
			whereBy.eq(" a."+IndustryArticleRM.status, 1);
			
			if (startDate != null && endDate != null) {
				whereBy.greateThenOrEquals(" a."+IndustryArticleRM.pubTime, DateUtils.dateToString(startDate, TimeFormatter.FORMATTER1));
				whereBy.lessThenOrEquals(" a."+IndustryArticleRM.pubTime, DateUtils.dateToString(endDate, TimeFormatter.FORMATTER1));
			} else if (startDate != null) {
				whereBy.eq(" a."+IndustryArticleRM.pubTime, DateUtils.dateToString(startDate, TimeFormatter.FORMATTER1));
			} else if (endDate != null) {
				whereBy.eq(" a."+IndustryArticleRM.pubTime, DateUtils.dateToString(endDate, TimeFormatter.FORMATTER1));
			}
			
			if(mediaId != null){
				whereBy.eq(" a."+IndustryArticleRM.mediaId, mediaId, CondtionSeparator.AND);
			}
			
			if(StringUtils.isNotBlank(content)){
				whereBy.like(" a."+IndustryArticleRM.simpleContent, "%"+content+"%"+ CondtionSeparator.AND);
			}
			
			if(StringUtils.isNotBlank(title)){
				whereBy.like(" a."+IndustryArticleRM.title, "%"+title+"%"+ CondtionSeparator.AND);
			}
			
			if(pageNo <=0 ) {
				pageNo = 1;
			}
			if(pageSize <=0) {
				pageSize = ECPage.DEFAULT_SIZE;
			}
			whereBy.orderBy("a."+IndustryArticleRM.pubTime, OrderType.DESC);

			ECPage<ArticleSimpleListVO> page = PagingManager.list(new Paginable<ArticleSimpleListVO>(pageNo,pageSize) {

				@Override
				public List<ArticleSimpleListVO> findList() {
					whereBy.setPage(this.getPageNo(), this.getRowsPerPage());
					return RowMapperUtils.map2List(industryArticleDao.findIndustryArticleByCriteria(whereBy), ArticleSimpleListVO.class);
				}

				@Override
				public Long count() {
					whereBy.setEnableLimit(false);
					Long c = industryArticleDao.findIndustryArticleCountsByCriteria(whereBy);
					whereBy.setEnableLimit(true);
					return c == null?0:c;
				}
			});
			
			return page;
		} catch (Exception e) {
			logger.error("查询列表出错", e);
		}
		return tempArticleSimpleListVO;
	}

	@Override
	public List<Cloud> getClouds(int pageSize) {
		Criteria whereBy = new Criteria();
		if(pageSize>0){
			whereBy.setPage(1, pageSize);
		}
		return this.genericDao.findList(Cloud.class, whereBy);
	}

	/**
	 * @laochen industry
	 * 根据文章ID获取评论列表
	 * @param articleId
	 * @param pageNo
	 * @param pageSize
	 * @return ECPage<IndustryComment>
	 */
	@Override
	public ECPage<IndustryComment> getArticleCommentsByArticleId(long articleId, int pageNo, int pageSize) {
		ECPage<IndustryComment> page = PagingManager.list(genericDao, IndustryComment.class, new Criteria()
		.eq(IndustryCommentRM.industryArticleId, articleId)
		.setPage(pageNo, pageSize));
		return page;		
	}

	/**
	 * 生成重点新闻
	 */
	@Override
	public List<TempIAVO> listIndustryArticleForJob() {
		List<Map<String, Object>> mlist=industryArticleDao.findIndustryArticleForJob();
		List<TempIAVO> list = RowMapperUtils.map2List(mlist,TempIAVO.class);
		return list;
	}

}
