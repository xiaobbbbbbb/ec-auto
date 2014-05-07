package com.ecarinfo.auto.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecarinfo.auto.po.ArticleComment;
import com.ecarinfo.auto.rm.ArticleCommentRM;
import com.ecarinfo.auto.service.ArticleCommentService;
import com.ecarinfo.auto.util.PageHelper;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
@Service
public class ArticleCommentServiceImpl implements ArticleCommentService {
	private static final Logger logger = Logger.getLogger(ArticleCommentServiceImpl.class);
	
	@Autowired
	private GenericDao genericDao;
	
	/**
	 * @laochen discuss
	 * 根据资讯ID获取其评论列表
	 * 注意：该 文章一定是要附带评论功能
	 * @param articleId
	 * @param pageNo
	 * @param pageSize
	 * @return ECPage<ArticleComment> 
	 */
	@Override
	public ECPage<ArticleComment> getArticleCommentByArticleId(long articleId,
			int pageNo, int pageSize) {
		logger.debug("getArticleCommentByArticleId articleId="+articleId);
		
		ECPage<ArticleComment> articleCommentPage = null;
		
		if(articleId>0){
			Criteria criteria= new Criteria();
			criteria.eq(ArticleCommentRM.articleId, articleId);
			
			long counts = this.genericDao.count(ArticleComment.class,criteria);
			
			if(counts>0){
				criteria.setPage(pageNo, pageSize);
				List<ArticleComment> articleList = this.genericDao.findList(ArticleComment.class, criteria);				
				articleCommentPage = PageHelper.list(counts, articleList, criteria);
			}
		}
		return articleCommentPage;
	}
}
