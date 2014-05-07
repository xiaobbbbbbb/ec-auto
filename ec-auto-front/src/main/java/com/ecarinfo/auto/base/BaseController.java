package com.ecarinfo.auto.base;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecarinfo.auto.front.helper.CommonDataCache;
import com.ecarinfo.auto.po.UserInfo;
import com.ecarinfo.auto.service.ArticleCommentService;
import com.ecarinfo.auto.service.ArticleService;
import com.ecarinfo.auto.service.CloudService;
import com.ecarinfo.auto.service.CommonService;
import com.ecarinfo.auto.service.IndustryService;
import com.ecarinfo.auto.service.MouthService;
import com.ecarinfo.auto.service.QuestionService;
import com.ecarinfo.auto.service.ViewpointService;
import com.ecarinfo.auto.util.CommonCache;
import com.ecarinfo.persist.service.GenericService;

/**
 * 基础控制器，其他控制器需extends此控制器获得initBinder自动转换的功能
 */

@Controller
@RequestMapping("/base")
public class BaseController {
	@Resource
	protected GenericService genericService;
	
	//每页显示数量
	protected final Integer globalRowsPerPage = 25;

	@Resource
	protected CommonService commonService;

	@Resource
	protected ArticleService articleService;

	@Resource
	protected ViewpointService viewpointService;
	
	@Resource
	protected IndustryService industryService;
	
	@Resource
	protected CloudService cloudService;
	
	@Resource
	protected ArticleCommentService articleCommentService;
	
	@Resource
	protected CommonDataCache commonDataCache;
	
	@Resource
	protected QuestionService questionService;
	
	@Resource
	protected MouthService mouthService;
	
	@Resource
	protected CommonCache commonCache;
	
	// 重定向
	public static String REDIRECT = "redirect:";

	// 请求转发
	public static String FORWARD = "forward:";

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 内容输出
	 * @throws IOException
	 */
	public static void printContent(HttpServletResponse response, String content) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.getWriter().write(content);
		response.getWriter().flush();
	}
	
	public UserInfo getUser(HttpServletRequest request) {
		return (UserInfo)request.getSession().getAttribute("user");
	}
	
	
}
