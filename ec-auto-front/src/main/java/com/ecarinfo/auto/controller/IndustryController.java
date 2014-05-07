package com.ecarinfo.auto.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.front.util.SolrUtils;
import com.ecarinfo.auto.po.Cloud;
import com.ecarinfo.auto.po.IndustryComment;
import com.ecarinfo.auto.po.MediaInfo;
import com.ecarinfo.auto.service.CloudService;
import com.ecarinfo.auto.service.CommonService;
import com.ecarinfo.auto.service.IndustryService;
import com.ecarinfo.auto.util.CommonCache;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.IndustryCommentVO;
import com.ecarinfo.common.utils.BeanUtils;
import com.ecarinfo.common.utils.CharsetUtil;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.service.GenericService;


/**
 * 新闻动态
 */

@Controller
@RequestMapping("/industry")
public class IndustryController extends BaseController {
	private static final Logger logger = Logger.getLogger(IndustryController.class);
	@Resource
	private IndustryService industryService;
	
	@Resource
	private CloudService cloudService;
	
	@Resource
	private GenericService genericService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private CommonCache commonCache;
	
	/**
	 * 动态新闻首页
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap map) {
		List<MediaInfo> mediaInfos = commonCache.getMediaInfos();
		map.put("mediaInfos", mediaInfos);
		return "/industry/index";
	}
	
	/**
	 * 文章详细首页
	 * @param map
	 * @param aid
	 * @return
	 */
	@RequestMapping(value = "/listArticles/{aid}")
	public String listArticles(ModelMap map,@PathVariable Long aid) {
		map.put("aid", aid);
		return "/industry/list";
	}
	
	/**
	 * 根据文章id查找所有相似文章
	 * @param map
	 * @param aid
	 * @param cpage
	 * @param rowsPerPage
	 * @return
	 */
	@RequestMapping(value = "/articles")
	@ResponseBody
	public Object articles(ModelMap map,Long aid,Integer cpage,Integer rowsPerPage) {
		ECPage<ArticleSimpleListVO> page = industryService.getRelatedArticles(aid, cpage == null? 1:cpage, rowsPerPage== null? globalRowsPerPage:rowsPerPage);
		return page;
	}
	
	/**
	 * 根据文章id查找所有评论
	 * @param map
	 * @param aid
	 * @param cpage
	 * @param rowsPerPage
	 * @return
	 */
	@RequestMapping(value = "/comments")
	@ResponseBody
	public Object comments(ModelMap map,Long aid,Integer cpage,Integer rowsPerPage) {
		ECPage<IndustryComment> page = industryService.getArticleCommentsByArticleId(aid, cpage == null? 1:cpage, rowsPerPage== null? globalRowsPerPage:rowsPerPage);
		return init(page,null);
	}
	
	
	private ECPage<IndustryCommentVO> init(ECPage<IndustryComment> p,ECPage<IndustryCommentVO> v) {
		if(p == null) {
			return null;
		}
		if(v == null) {
			v = new ECPage<IndustryCommentVO>();
		}
		v.setCurrentPage(p.getCurrentPage());
		
		v.setRowsPerPage(p.getRowsPerPage());
		v.setTotalPage(p.getTotalPage());
		v.setTotalRows(p.getTotalRows());
		v.setVisualSize(p.getVisualSize());
		List<IndustryCommentVO> voList = new ArrayList<IndustryCommentVO>();
		for(IndustryComment c:p.getList()) {
			voList.add(init(c,null));
		}
		v.setList(voList);
		return v;
	}
	
	private IndustryCommentVO init(IndustryComment p,IndustryCommentVO v) {
		if(p == null) {
			return null;
		}
		if(v == null) {
			v = new IndustryCommentVO();
		}
		v.setAffection(p.getAffection());
		v.setContent(p.getContent());
		v.setCtime(p.getCtime());
		v.setId(p.getId());
		v.setIndustryArticleId(p.getIndustryArticleId());
		return v;
	}
	
	/**
	 * 根据id查找文章
	 * @param map
	 * @param aid
	 * @return
	 */
	@RequestMapping(value = "/article")
	@ResponseBody
	public Object article(ModelMap map,Long aid) {
		return industryService.getArticleByPk(aid);
	}
	
	/**
	 * 多条件检索文章
	 * @param map
	 * @param startDate
	 * @param endDate
	 * @param mediaId
	 * @param title
	 * @param content
	 * @param cpage
	 * @param rowsPerPage
	 * @param frommedia
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(ModelMap map,String startDate,String endDate,Integer mediaId,String title,String content,Integer cpage,Integer rowsPerPage,String frommedia) {
		Date sDate = null;//DateUtils.stringToDate(startDate, TimeFormatter.FORMATTER2);
		Date eDate = null;//DateUtils.stringToDate(endDate, TimeFormatter.FORMATTER2);
		if(StringUtils.hasText(startDate) && startDate.length() < 3) {
			int type = Integer.parseInt(startDate.trim());
			Date today = DateUtils.getToday(0, 0, 0).toDate();
			switch (type) {
			case -1:
				sDate = DateUtils.plusDays(today, -1);
				eDate = DateUtils.plusDays(today, 0);
				break;
			case 1:
				sDate = DateUtils.plusDays(today, 0);
				eDate = DateUtils.plusDays(today, 1);
				break;
			case 7:
				sDate = DateUtils.plusDays(today, -7);
				eDate = DateUtils.plusDays(today, 1);
				break;
			case 15:
				sDate = DateUtils.plusDays(today, -15);
				eDate = DateUtils.plusDays(today, 1);
				break;
			case 30:
				sDate = DateUtils.plusDays(today, -30);
				eDate = DateUtils.plusDays(today, 1);
				break;
			}
		} else {
			sDate = DateUtils.stringToDate(startDate, TimeFormatter.FORMATTER2);
			eDate = DateUtils.stringToDate(endDate, TimeFormatter.FORMATTER2);
			eDate = DateUtils.plusDays(eDate, 1);
		}
		if(mediaId == -1) {
			mediaId = null;
		}
		ECPage<ArticleSimpleListVO> page = industryService.getIndustryArticleList(sDate, eDate, mediaId, title, content, cpage==null?1:cpage, rowsPerPage==null?globalRowsPerPage:rowsPerPage);
		return page;
	}
	
	/**
	 * 关键字搜索 solr
	 * @param map
	 * @param keyword
	 * @param cpage
	 * @return
	 */
	@RequestMapping(value = "/search")
	@ResponseBody
	public Object solrSearch(ModelMap map,String keyword,String mediaName,String startDate,String endDate,Integer cpage) {
		Integer rows = globalRowsPerPage;
		Integer start = (cpage-1)*rows;
		keyword = CharsetUtil.decodeFromISO8859(keyword);
		mediaName = CharsetUtil.decodeFromISO8859(mediaName);
		ECPage<ArticleSimpleListVO> page = new ECPage<ArticleSimpleListVO>();
		List<ArticleSimpleListVO> voList = new ArrayList<ArticleSimpleListVO>();
		page.setList(voList);
		HttpSolrServer server = SolrUtils.getSolrServer("industry_article");
		if(server == null) {
			return page;
		}
		
		//处理起止时间
		Date sDate = null;//DateUtils.stringToDate(startDate, TimeFormatter.FORMATTER2);
		Date eDate = null;//DateUtils.stringToDate(endDate, TimeFormatter.FORMATTER2);
		if(StringUtils.hasText(startDate) && startDate.length() < 3) {
			int type = Integer.parseInt(startDate.trim());
			Date today = DateUtils.getToday(0, 0, 0).toDate();
			switch (type) {
			case -1:
				sDate = DateUtils.plusDays(today, -1);
				eDate = DateUtils.plusDays(today, 0);
				break;
			case 1:
				sDate = DateUtils.plusDays(today, 0);
				eDate = DateUtils.plusDays(today, 1);
				break;
			case 7:
				sDate = DateUtils.plusDays(today, -7);
				eDate = DateUtils.plusDays(today, 1);
				break;
			case 15:
				sDate = DateUtils.plusDays(today, -15);
				eDate = DateUtils.plusDays(today, 1);
				break;
			case 30:
				sDate = DateUtils.plusDays(today, -30);
				eDate = DateUtils.plusDays(today, 1);
				break;
			}
		} else {
			sDate = DateUtils.stringToDate(startDate, TimeFormatter.FORMATTER2);
			eDate = DateUtils.stringToDate(endDate, TimeFormatter.FORMATTER2);
			eDate = DateUtils.plusDays(eDate, 1);
		}
		String startTime = DateUtils.dateToString(sDate, "yyyy-MM-dd'T'")+"00:00:00Z";
		String endTime = DateUtils.dateToString(eDate, "yyyy-MM-dd'T")+"00:00:00Z";
		
		//solr查询
		SolrQuery query = new SolrQuery();
		// 主查询
		keyword = keyword.replace("　", " ");
		query.set("q",
				"title:" + keyword.replace(" ", " OR title:"),
				"keyword:" + keyword.replace(" ", " OR keyword:")
				);
		query.set("wt","json");
		if(StringUtils.hasText(mediaName) && !mediaName.equals("全部")) {
			query.addFilterQuery("pubTime:["+startTime+" TO "+endTime+"}","mediaName:" + mediaName);
		} else {
			query.addFilterQuery("pubTime:["+startTime+" TO "+endTime+"}");
		}
		// 添加排序
		query.addSort("pubTime", ORDER.desc);
		// 分页返回结果
		query.setStart(start);
		query.setRows(rows);
		QueryResponse response;
		try {
			response = server.query(query);
			//初始化page
			initPageFromQueryResponse(page,response);
		} catch (SolrServerException e) {
			logger.error("",e);
		}
		System.err.println(BeanUtils.toString(page));
		return page;
	}
	
	private void initPageFromQueryResponse(ECPage<ArticleSimpleListVO> page,QueryResponse response) {
		SolrDocumentList list = response.getResults();
        page.setTotalRows(list.getNumFound());
        page.setRowsPerPage(globalRowsPerPage);
        
        page.setCurrentPage(1+list.getStart()/globalRowsPerPage+(list.getStart()%globalRowsPerPage == 0?0:1));
        page.setTotalPage(list.getNumFound()/globalRowsPerPage+(list.getNumFound()%globalRowsPerPage == 0?0:1));
        
        for(int i=0;i<list.size();i++) {
			SolrDocument doc = list.get(i);
			ArticleSimpleListVO vo = new ArticleSimpleListVO();
			vo.setId(Long.parseLong((String)doc.getFieldValue("id")));
			vo.setTitle((String)doc.getFieldValue("title"));
			vo.setUrl((String)doc.getFieldValue("url"));
			vo.setMediaName((String)doc.getFieldValue("mediaName"));
			vo.setPubDate((Date)doc.getFieldValue("pubTime"));
			page.getList().add(vo);
		}
	}
	
	/**
	 * 热点云数据　
	 * @param maxRows
	 * @return
	 */
	@RequestMapping(value = "/hotClouds")
	@ResponseBody
	public Object hotClouds(Integer maxRows,HttpServletRequest request) {
		return cloudService.findHotClouds(maxRows,request.getContextPath());
	}

	/**
	 * 热点云首页
	 * @return
	 */
	@RequestMapping(value = "/cloud")
	public String cloud() {
		return "/industry/cloud";
	}
	
	/**
	 * 热点云相关文章列表
	 * @return
	 */
	@RequestMapping(value = "/listCloud")
	public String listCloud(ModelMap map,Long cloudId) {
		Cloud cloud = genericService.findByPK(Cloud.class, cloudId);
		map.put("cloud", cloud);
		return "/industry/cloud_list";
	}
	
	/**
	 * 查找热点云相关的所有文章
	 * @param maxRows
	 * @return
	 */
	@RequestMapping(value = "/clouds")
	@ResponseBody
	public Object clouds(String keywords,Integer cpage) {
//		return cloudService.findArticlesByCloudId(cloudId, cpage==null?1:cpage, rowsPerPage==null?globalRowsPerPage:rowsPerPage);
		Integer rows = globalRowsPerPage;
		Integer start = (cpage-1)*rows;
		keywords = CharsetUtil.decodeFromISO8859(keywords);
//		keywords = keywords.replace(",", "");
		ECPage<ArticleSimpleListVO> page = new ECPage<ArticleSimpleListVO>();
		List<ArticleSimpleListVO> voList = new ArrayList<ArticleSimpleListVO>();
		page.setList(voList);
		HttpSolrServer server = SolrUtils.getSolrServer("industry_article");
		if(server == null) {
			return page;
		}
		
		//solr查询
		SolrQuery query = new SolrQuery();
		// 主查询
		query.set("q",
//				"title:" + keywords.replace(",", " OR title:"),
				"keyword:" + keywords.replace(",", " OR keyword:")
				);
		query.set("wt","json");
		// 添加排序
		query.addSort("pubTime", ORDER.desc);
		// 分页返回结果
		query.setStart(start);
		query.setRows(rows);
		QueryResponse response;
		try {
			response = server.query(query);
			//初始化page
			initPageFromQueryResponse(page,response);
		} catch (SolrServerException e) {
			logger.error("",e);
		}
		System.err.println(BeanUtils.toString(page));
		return page;
	}
	
}
