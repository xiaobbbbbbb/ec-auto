package com.ecarinfo.auto.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.po.NetworkMediaAnalysis;
import com.ecarinfo.auto.service.MediaAnalyzeService;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.WeiboAnalysisVO;
import com.ecarinfo.persist.paging.ECPage;


/**
 * 媒体分析
 * @author yinql
 *
 */
@Controller
@RequestMapping("/mediaanalyze")
public class MediaAnalyzeController extends BaseController {
	@Resource
	private MediaAnalyzeService mediaAnalyzeService;
	@RequestMapping(value = "/index")
	public String Index(ModelMap map, HttpServletRequest request) {
		List<NetworkMediaAnalysis> listnet= mediaAnalyzeService.getNetworkMediaTop10();
		map.put("listNet", listnet);
		List<WeiboAnalysisVO> listWeibo= mediaAnalyzeService.getWeiboAnalysisTop10();
		map.put("listWeibo", listWeibo);
		return "mediaanalyze/index";
	}
	
	@RequestMapping(value = "/weibo")
	public String weibo(ModelMap map, HttpServletRequest request) {
		return "mediaanalyze/list_weibo";
	}
	
	@RequestMapping(value = "/weibolist")
	@ResponseBody
	public ECPage<WeiboAnalysisVO> weibolist( HttpServletRequest request,Integer cpage, Integer pageSize) {
		ECPage<WeiboAnalysisVO> page =mediaAnalyzeService.getWeibolist(cpage == null ? 1 : cpage, pageSize == null ? 25 : pageSize);
		return  page;
	}
	
	@RequestMapping(value = "/media")
	public String wmediaeibo(ModelMap map, HttpServletRequest request) {
		return "mediaanalyze/list_media";
	}
	
	@RequestMapping(value = "/medialist")
	@ResponseBody
	public ECPage<NetworkMediaAnalysis> medialist( HttpServletRequest request,Integer cpage, Integer pageSize) {
		ECPage<NetworkMediaAnalysis> page =mediaAnalyzeService.getMedialist(cpage == null ? 1 : cpage, pageSize == null ? 25 : pageSize);
		return  page;
	}
}
