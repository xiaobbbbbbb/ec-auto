package com.ecarinfo.auto.service;

import com.ecarinfo.auto.po.ArticleComment;
import com.ecarinfo.persist.paging.ECPage;

/**
 * 资讯评论
 * @author laochen
 *
 */
public interface ArticleCommentService {
	
	/**
	 * 根据资讯ID获取其评论列表
	 * 注意：该 文章一定是要附带评论功能
	 * @param articleId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ECPage<ArticleComment> getArticleCommentByArticleId(long articleId,int pageNo,int pageSize);
}