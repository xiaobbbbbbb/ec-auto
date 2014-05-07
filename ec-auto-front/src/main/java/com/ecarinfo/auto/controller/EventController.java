package com.ecarinfo.auto.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import casia.isiteam.plgroup.solr.commons.DataType;
import casia.isiteam.plgroup.solr.commons.FieldOccurs;
import casia.isiteam.plgroup.solr.commons.SortOrder;
import casia.isiteam.plgroup.solr.search.SearchClient;

import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.po.EventBrief;
import com.ecarinfo.auto.po.EventKeyWords;
import com.ecarinfo.auto.po.EventNewsRef;
import com.ecarinfo.auto.po.EventPr;
import com.ecarinfo.auto.po.EventThreadsRef;
import com.ecarinfo.auto.po.EventTwitterRef;
import com.ecarinfo.auto.po.NetworkMediaAnalysis;
import com.ecarinfo.auto.po.WeiboAnalysis;
import com.ecarinfo.auto.rm.EventBriefRM;
import com.ecarinfo.auto.rm.EventKeyWordsRM;
import com.ecarinfo.auto.rm.EventNewsRefRM;
import com.ecarinfo.auto.rm.EventPrRM;
import com.ecarinfo.auto.rm.EventThreadsRefRM;
import com.ecarinfo.auto.rm.EventTwitterRefRM;
import com.ecarinfo.auto.rm.WeiboAnalysisRM;
import com.ecarinfo.auto.util.DtoUtil;
import com.ecarinfo.auto.util.PageHelper;
import com.ecarinfo.auto.vo.AjaxJson;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.common.utils.JSONUtil;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

/**
 * 事件
 */

@Controller
@RequestMapping("/event")
public class EventController extends BaseController {

	final private static Logger logger = LoggerFactory
			.getLogger(EventController.class);

	// @Resource
	// EventService eventService;

	private String indexFirstParam = "/searchConf/bjga_index.properties";

	/*
	 * 主页
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap map, HttpServletRequest request) {
		return "event/index";
	}

	/**
	 * 首页界面查询
	 * 
	 * @param map
	 * @param event_type
	 * @param searchStr
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/queryIndexEventData")
	@ResponseBody
	public ECPage<EventBrief> queryIndexEventData(ModelMap map,
			Integer event_type, String searchStr, Integer pageNo,
			Integer rowsPerPage) throws UnsupportedEncodingException {
		Criteria c = new Criteria();
		c.eq(EventBriefRM.eventType, event_type);
		long counts = this.genericService.count(EventBrief.class, c); // 总数
		c.setPage(pageNo, rowsPerPage); // 加入了分页对象
		if (searchStr != null && searchStr.trim() != "") {
			searchStr = DtoUtil.incode(searchStr);
			c.like(EventBriefRM.name, "%" + searchStr.trim() + "%");
			// map.put("searchStr", searchStr.trim());
		}
		c.orderBy(EventBriefRM.createTime, OrderType.DESC);
		List<EventBrief> ebList = genericService.findList(EventBrief.class, c);
		ECPage<EventBrief> page = PageHelper.list(counts, ebList, c);
		return page;
	}

	/*
	 * 添加事件配置
	 */
	@RequestMapping(value = "/addEventBrief")
	@ResponseBody
	public AjaxJson addEventBrief(String eventName, String eventKeywords,
			String stime, Integer eventType, String eventDescription,
			String picPath, Integer eid) {
		AjaxJson ajaxJson = new AjaxJson();
		// 处理开始结束时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startTime = null;
		try {
			startTime = sdf.parse(stime);
		} catch (ParseException e) {
			logger.error("添加失败!",e);
		}
		// 处理关键词
		String[] kwArray = eventKeywords.trim().split(";");
		if (eid == null || eid == 0) {// 添加
			if (existsEventBrief(eventType, eventName)) {// 该类别中，事件名称已经存在
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("对不起，该类别中，事件主题已存在。");
			} else {
				EventBrief eventBrief = new EventBrief();
				eventBrief.setName(eventName);
				// --------------->>>>>>>>>>>>>>添加参数 开始

				eventBrief.setStartTime(startTime);
				eventBrief.setType(0);// 这个字段好像没用，暂时这么写
				eventBrief.setCreateTime(new Date());
				eventBrief.setLastModify(new Date());
				eventBrief.setStatus(1);
				eventBrief.setDepId(1);// 本系统中不区分用户部门，所以全写1
				eventBrief.setUserId(1);// 本系统中不区分用户，所以全写1
				eventBrief.setIsPublic(true);// 本系统中不区分是否公开，所以全写true
				eventBrief.setEventType(eventType);
				eventBrief.setEventDescription(eventDescription);
				eventBrief.setPicPath(picPath);
				// ---------------<<<<<<<<<<<<<添加参数 结束
				genericService.save(eventBrief);// 保存事件主题
				Integer newEid = eventBrief.getEid();
				if (newEid != null && newEid > 0) {// 保存事件主题成功后，保存关键词
					if (kwArray.length > 0) {
						for (int i = 0; i < kwArray.length; i++) {
							if (!kwArray[i].trim().equals("")) {
								EventKeyWords tempKeyWords = new EventKeyWords();
								tempKeyWords.setEid(newEid);
								tempKeyWords.setKeywords(kwArray[i].trim());
								tempKeyWords.setRelation(i);
								genericService.save(tempKeyWords);
							}
						}
					}
				}
				ajaxJson.setSuccess(true);
			}
		} else {// 编辑
			Criteria c = new Criteria();
			c.eq(EventBriefRM.eid, eid);
			EventBrief sourceEventBrief = genericService.findOne(
					EventBrief.class, c);
			if (!eventName.trim().equals(sourceEventBrief.getName().trim())) {// 如果修改了名字
				if (existsEventBrief(eventType, eventName)) {// 该类别中，事件名称已经存在
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("对不起，该类别中，事件主题已存在。");
				}
			} else {
				sourceEventBrief.setName(eventName);
				// --------------->>>>>>>>>>>>>>添加参数 开始
				sourceEventBrief.setStartTime(startTime);
				sourceEventBrief.setType(0);// 这个字段好像没用，暂时这么写
				sourceEventBrief.setCreateTime(new Date());
				sourceEventBrief.setLastModify(new Date());
				sourceEventBrief.setStatus(1);
				sourceEventBrief.setDepId(1);// 本系统中不区分用户部门，所以全写1
				sourceEventBrief.setUserId(1);// 本系统中不区分用户，所以全写1
				sourceEventBrief.setIsPublic(true);// 本系统中不区分是否公开，所以全写true
				sourceEventBrief.setEventType(eventType);
				sourceEventBrief.setEventDescription(eventDescription);
				sourceEventBrief.setPicPath(picPath);
				// ---------------<<<<<<<<<<<<<添加参数 结束
				genericService.update(sourceEventBrief);// 保存修改好的事件主题
				// 删除原来配置的关键词
				Criteria cEventKeywords = new Criteria();
				cEventKeywords.eq(EventKeyWordsRM.eid, eid);
				genericService.delete(EventKeyWords.class, cEventKeywords);
				// 保存关键词
				if (kwArray.length > 0) {
					for (int i = 0; i < kwArray.length; i++) {
						if (!kwArray[i].trim().equals("")) {
							EventKeyWords tempKeyWords = new EventKeyWords();
							tempKeyWords.setEid(sourceEventBrief.getEid());
							tempKeyWords.setKeywords(kwArray[i].trim());
							tempKeyWords.setRelation(i);
							genericService.save(tempKeyWords);
						}
					}
				}
				ajaxJson.setSuccess(true);
			}
		}
		return ajaxJson;
	}

	/*
	 * 切换事件状态，1开启，2停止
	 */
	@RequestMapping(value = "/switchStatus")
	@ResponseBody
	public AjaxJson switchStatus(Integer eid, Integer st) {
		AjaxJson ajaxJson = new AjaxJson();
		EventBrief eb = genericService.findByPK(EventBrief.class,eid);
		if (eb != null) {
			if (st == 1) {
				eb.setStatus(0);
			} else if (st == 0) {
				eb.setStatus(1);
			}
			genericService.update(eb);
			ajaxJson.setSuccess(true);
		} else {
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("对不起，该事件不存在。");
		}
		return ajaxJson;
	}

	/**
	 * 验证该类别中，该事件名称是否存在
	 * 
	 * @return
	 */
	private boolean existsEventBrief(Integer eventType, String eventName) {
		Criteria c = new Criteria();
		c.eq(EventBriefRM.eventType, eventType);
		c.eq(EventBriefRM.name, eventName);
		EventBrief eventBrief = genericService.findOne(EventBrief.class, c);
		if (eventBrief == null) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * 跳转到事件主题添加页面
	 */
	@RequestMapping(value = "/add")
	public String add(ModelMap map, HttpServletRequest request, String title)
			throws UnsupportedEncodingException {
		if (title != null && title.length() > 0) {
			title = DtoUtil.incode(title);
			map.put("title", title);
		}
		return "event/add";
	}

	/**
	 * 跳转修改界面
	 * 
	 * @param map
	 * @param request
	 * @param eid
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(ModelMap map, HttpServletRequest request, Integer eid) {
		if (eid != null && eid > 0) {
			EventBrief eventBrief = genericService.findByPK(EventBrief.class,
					eid);
			map.put("eventBrief", eventBrief);
			EventKeyWords eventKeyWords = genericService.findOneByAttr(
					EventKeyWords.class, "eid", eid);
			map.put("eventKeyWords", eventKeyWords);
		}
		return "event/add";
	}

	/**
	 * 传播分析
	 * 
	 * @param map
	 * @param request
	 * @param eid
	 * @param propagationNodeType
	 *            传播节点类型：1新闻，2论坛，3微博，4平媒
	 * @param alarmInfoPageNum
	 *            预警历史——页码
	 * @param alarmInfoPageSize
	 *            预警历史——页容量
	 * @return
	 */
	@RequestMapping(value = "/propagationAnalysis")
	public String propagationAnalysis(ModelMap map, HttpServletRequest request,
			Integer eid, Integer propagationNodeType, Integer alarmInfoPageNum,
			Integer alarmInfoPageSize) {
		map.put("eid", eid);
		// 该主题相关信息
		List<EventBrief> ebList = genericService.findByAttr(EventBrief.class,
				"eid", eid);
		EventBrief eb = null;
		if (ebList != null && ebList.size() > 0) {
			eb = ebList.get(0);
		}
		if (eb != null) {
			map.put("eventBrief", eb);
		}
		// 传播起源
		String path = request.getSession().getServletContext().getRealPath("/");// 获取工程的根路径
		System.out.println("zdj---------------!!!!!!!!!!!!!-----------------"
				+ path);
		SearchClient sc = new SearchClient(path + indexFirstParam,
				DataType.EVENT_MAIN);
		sc.addPrimitveTermQuery("eid", eid + "");
		sc.addSortField("pubtime2", SortOrder.asc);// 精确时分秒
		sc.setStart(0);
		sc.setRow(1);
		sc.execute(new String[] { "it", "id" });
		List<String[]> fisrtIdList = sc.getResults(new String[] { "it", "id" });
		if (fisrtIdList != null && fisrtIdList.size() > 0) {
			// 事件起源：{站点，发布时间，标题，作者，原网页地址}
			List<Object[]> lobj = queryEventAlarmList(fisrtIdList, eid, 4);
			Object[] propagationSourceObj = null;
			if (lobj != null && lobj.size() > 0) {
				propagationSourceObj = lobj.get(0);
			}
			map.put("propagationSource", propagationSourceObj);
		}
		// //新闻渠道 queryPropagationNodeList
		// if (propagationNodeType==null) {
		// propagationNodeType = 1;
		// }
		// //新闻渠道类型，1新闻，2论坛，3微博，4平媒
		// map.put("propagationNodeType", propagationNodeType);
		// //！！！！！！！！！！！-----------------------------节点待查
		// map.put("propagationNodeList", new ArrayList<Object[]>());//数组格式：
		return "event/propagationAnalysis";
	}

	/**
	 * 传播分析————报道量数据图
	 * 
	 * @param eId
	 * @param eventInfoType
	 * @return
	 */
	@RequestMapping(value = "/infoCountChart")
	@ResponseBody
	public List<ChartCoreDataVO> infoCountChart(HttpServletRequest request,
			Integer eId, Integer eventInfoType) {
		if (null == eventInfoType) {
			eventInfoType = 0;
		}
		// 索引查询
		SearchClient sc = null;
		String path = request.getSession().getServletContext().getRealPath("/");// 获取工程的根路径
		System.out.println("zdj---------------!!!!!!!!!!!!!-----------------"
				+ path);
		if (eventInfoType == 0) {// 全部event数据类型
			sc = new SearchClient(path + indexFirstParam, DataType.EVENT_MAIN);
		} else if (eventInfoType == 1) {// event_news新闻
			sc = new SearchClient(path + indexFirstParam, DataType.EVENT_NEWS);
		} else if (eventInfoType == 2) {// event_threads论坛
			sc = new SearchClient(path + indexFirstParam,
					DataType.EVENT_MAIN_THREAD);
		} else if (eventInfoType == 3) {// event_twitter微博
			sc = new SearchClient(path + indexFirstParam,
					DataType.EVENT_TWITTER);
		}
		List<ChartCoreDataVO> cccvList = new ArrayList<ChartCoreDataVO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfForShow = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < 15; i++) {
			Date d = DateUtils.getDateByDay(i - 14);
			String dateStr = sdf.format(d);
			Long count = queryInfoCountByDay(sc, eId, dateStr);
			ChartCoreDataVO ccdv = new ChartCoreDataVO();
			ccdv.setName(sdfForShow.format(d));
			ccdv.setValue(count.intValue());
			cccvList.add(ccdv);
		}
		return cccvList;
	}

	/**
	 * 网友情感————负面报道量
	 * 
	 * @param eid
	 * @return
	 */
	@RequestMapping(value = "/netizenEmotions")
	@ResponseBody
	public List<ChartCoreDataVO> netizenEmotions(HttpServletRequest request,
			Integer eid) {
		// 索引查询
		String path = request.getSession().getServletContext().getRealPath("/");// 获取工程的根路径
		System.out.println("zdj---------------!!!!!!!!!!!!!-----------------"
				+ path);
		SearchClient sc = new SearchClient(path + indexFirstParam,
				DataType.EVENT_MAIN);
		;
		List<ChartCoreDataVO> cccvList = new ArrayList<ChartCoreDataVO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat show = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < 15; i++) {
			String dateStr = sdf.format(DateUtils.getDateByDay(i - 14));
			String showStr = show.format(DateUtils.getDateByDay(i - 14));
			Long count = queryNegativeInfoSum(sc, eid, dateStr);
			ChartCoreDataVO ccdv = new ChartCoreDataVO();
			ccdv.setName(showStr);
			ccdv.setValue(count.intValue());
			cccvList.add(ccdv);
		}
		return cccvList;

		// 模拟数据
		// return getTmepChar();
	}

	/*
	 * 网友情感————负面报道量————查询每天的数据 support_level : 1 正面、2 中立、3 负面
	 */
	public long queryNegativeInfoSum(SearchClient sc, Integer eId,
			String dateStr) {
		sc.reset();
		sc.addPrimitveTermQuery("eid", eId + "");
		sc.addPrimitveTermQuery("support_level", "3");
		sc.addRangeTerms("pubtime2", dateStr + "000000", dateStr + "235959");
		sc.setStart(0);
		sc.setRow(1);
		sc.executeItPlusIdVersion();
		return sc.getTotal();
	}

	/*
	 * 传播分析————报道量数据图————查询每天的数据
	 */
	public long queryInfoCountByDay(SearchClient sc, Integer eId, String dateStr) {
		sc.reset();
		sc.addPrimitveTermQuery("eid", eId + "");
		sc.addRangeTerms("pubtime2", dateStr + "000000", dateStr + "235959");
		sc.setStart(0);
		sc.setRow(1);
		sc.execute();
		return sc.getTotal();
	}

	/*
	 * 根据从索引中查询到的id，查询具体数据，封装后返回
	 * 
	 * objFormat数据格式 1 {Date发布时间，Integer预警级别，String标题，Integer该条数据id，String源地址} 2
	 * {String标题，Integer转发数，Integer评论数，String来源，Date发布时间， String 源地址} 3 {String内容，Date发布时间}
	 * 4 {String站点，Date发布时间，String标题，String作者，String源地址} 5 {String标题，Date发布时间，
	 * String源地址}
	 */
	public List<Object[]> queryEventAlarmList(List<String[]> eaIdList,
			Integer eid, Integer objFormat) {
		List<Object[]> resultList = null;
		if (eaIdList != null && eaIdList.size() > 0) {
			resultList = new ArrayList<Object[]>();
			for (String[] oneId : eaIdList) {
				if (oneId[0].equals(DataType.EVENT_NEWS.getPrefix())) {// event_news新闻，到时候再根据命名规则改
					Criteria c = new Criteria();
					c.eq(EventNewsRefRM.id, Integer.parseInt(oneId[1]));
					c.eq(EventNewsRefRM.eid, eid);
					EventNewsRef eventNewsRef = genericService.findOne(
							EventNewsRef.class, c);
					if (eventNewsRef != null) {
						Object[] obj = null;
						if (objFormat == 1) {
							obj = new Object[] { eventNewsRef.getPubtime(),
									eventNewsRef.getAlarmLevel(),
									eventNewsRef.getTitle(),
									eventNewsRef.getId(), eventNewsRef.getUrl() };
						} else if (objFormat == 2) {
							obj = new Object[] { eventNewsRef.getTitle(),
									eventNewsRef.getDupCount(),
									eventNewsRef.getCmtCount(),
									eventNewsRef.getSite(),
									eventNewsRef.getPubtime(),
									eventNewsRef.getUrl(),};
						} else if (objFormat == 3) {
							obj = new Object[] { eventNewsRef.getSummary(),
									eventNewsRef.getPubtime() };
						} else if (objFormat == 4) {
							obj = new Object[] { eventNewsRef.getSite(),
									eventNewsRef.getPubdate(),
									eventNewsRef.getTitle(),
									eventNewsRef.getAuthor(),
									eventNewsRef.getUrl() };
						} else if (objFormat == 5) {
							obj = new Object[] { eventNewsRef.getTitle(),
									eventNewsRef.getPubtime(),
									eventNewsRef.getUrl() };
						}
						resultList.add(obj);
					}
				} else if (oneId[0].equals(DataType.EVENT_MAIN_THREAD
						.getPrefix())) {// event_threads论坛
					Criteria c = new Criteria();
					c.eq(EventThreadsRefRM.id, Integer.parseInt(oneId[1]));
					c.eq(EventThreadsRefRM.eid, eid);
					EventThreadsRef eventThreadsRef = genericService.findOne(
							EventThreadsRef.class, c);
					if (eventThreadsRef != null) {
						Object[] obj = null;
						if (objFormat == 1) {
							obj = new Object[] { eventThreadsRef.getPubtime(),
									eventThreadsRef.getAlarmLevel(),
									eventThreadsRef.getTitle(),
									eventThreadsRef.getId(),
									eventThreadsRef.getUrl() };
						} else if (objFormat == 2) {
							obj = new Object[] { eventThreadsRef.getTitle(), 0,
									eventThreadsRef.getCmtCount(),
									eventThreadsRef.getSite(),
									eventThreadsRef.getPubtime(),
									eventThreadsRef.getUrl()};
						} else if (objFormat == 3) {
							obj = new Object[] { eventThreadsRef.getSummary(),
									eventThreadsRef.getPubtime() };
						} else if (objFormat == 4) {
							obj = new Object[] { eventThreadsRef.getSite(),
									eventThreadsRef.getPubdate(),
									eventThreadsRef.getTitle(),
									eventThreadsRef.getAuthor(),
									eventThreadsRef.getUrl() };
						} else if (objFormat == 5) {
							obj = new Object[] { eventThreadsRef.getTitle(),
									eventThreadsRef.getPubtime(),
									eventThreadsRef.getUrl() };
						}
						resultList.add(obj);
					}
				} else if (oneId[0].equals(DataType.EVENT_TWITTER.getPrefix())) {// event_twitter微博
					Criteria c = new Criteria();
					c.eq(EventTwitterRefRM.id, Integer.parseInt(oneId[1]));
					c.eq(EventTwitterRefRM.eid, eid);
					EventTwitterRef eventTwitterRef = genericService.findOne(
							EventTwitterRef.class, c);
					if (eventTwitterRef != null) {
						Object[] obj = null;
						if (objFormat == 1) {
							obj = new Object[] { eventTwitterRef.getPubtime(),
									eventTwitterRef.getAlarmLevel(),
									eventTwitterRef.getSummary(),
									eventTwitterRef.getId(),
									eventTwitterRef.getUrl() };
						} else if (objFormat == 2) {
							obj = new Object[] { eventTwitterRef.getSummary(),
									0, eventTwitterRef.getCmtCount(),
									eventTwitterRef.getSite(),
									eventTwitterRef.getPubtime(),
									eventTwitterRef.getUrl()};
						} else if (objFormat == 3) {
							obj = new Object[] { eventTwitterRef.getSummary(),
									eventTwitterRef.getPubtime() };
						} else if (objFormat == 4) {
							obj = new Object[] { eventTwitterRef.getSite(),
									eventTwitterRef.getPubdate(),
									eventTwitterRef.getSummary(),
									eventTwitterRef.getAuthor(),
									eventTwitterRef.getUrl() };
						} else if (objFormat == 5) {
							obj = new Object[] { eventTwitterRef.getSummary(),
									eventTwitterRef.getPubtime(),
									eventTwitterRef.getUrl() };
						}
						resultList.add(obj);
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * 网友情感
	 * 
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail2")
	public String detail2(ModelMap map, HttpServletRequest request, Integer eid) {
		map.put("eid", eid);
		String path = request.getSession().getServletContext().getRealPath("/");// 获取工程的根路径
		System.out.println("zdj---------------!!!!!!!!!!!!!-----------------"
				+ path);
		SearchClient sc = new SearchClient(path + indexFirstParam,
				DataType.EVENT_MAIN);
		Object[] pObj = queryEventInfoByPN(sc, eid, 1, 1, 1);
		Object[] nObj = queryEventInfoByPN(sc, eid, 3, 1, 1);
		// map.put("positiveNum", pObj[1]);//正面评论数
		// map.put("negativeNum", nObj[1]);//负面评论数

		// 模拟 此处只需要两个评论的总数 其他列表数据使用ajax请求 正面的queryPositiveList 负面的
		// queryNegativeList
		map.put("positiveNum", pObj[1]);// 正面评论数
		map.put("negativeNum", nObj[1]);// 负面评论数
		return "event/detail2";
	}

	/*
	 * 网友情感————查询正负面数据 pn:1正面,3负面,2中立
	 */
	public Object[] queryEventInfoByPN(SearchClient sc, Integer eid,
			Integer pn, Integer pageNum, Integer pageSize) {
		sc.reset();
		sc.addPrimitveTermQuery("eid", eid + "");
		sc.addSortField("pubtime2", SortOrder.desc);// 精确时分秒
		sc.addPrimitveTermQuery("support_level", pn + "");
		sc.setStart((pageNum - 1) * pageSize);
		sc.setRow(pageSize);
//		sc.executeItPlusIdVersion();
		sc.execute(new String[] { "it", "id" });
		// List<String> eaIdList = sc.getResultsItPlusIdVersion();
		List<String[]> eaIdList = sc.getResults(new String[] { "it", "id" });
		List<Object[]> resultList = queryEventAlarmList(eaIdList, eid, 5);
		return new Object[] { resultList, sc.getTotal() };
		// return new Object[]{null, sc.getTotal()};
	}

	/**
	 * 相关文章
	 * 
	 * @param map
	 * @param request
	 * @param eid
	 * @param eventInfoType
	 *            ！！！！！！！！！！！！！！！！！！！！！！！！！！！！---------------------缺少分页，无索引查询，
	 *            做sql分页即可
	 * @return
	 */
	@RequestMapping(value = "/detail3")
	public String detail3(ModelMap map, HttpServletRequest request,
			Integer eid, Integer eventInfoType) {
		if (eventInfoType == null) {
			eventInfoType = 1;
		}
		map.put("eid", eid);
		map.put("eventInfoType", eventInfoType);// 数据类型，1新闻，2论坛，3微博，4平媒

		return "event/detail3";
	}

	/*
	 * 查询事件信息 根据事件主题eid，数据类型eventInfoType
	 * 实际数据，格式{String来源，String文字标题，Integer情感，Integer转发数，Integer评论数,Date发布时间}
	 */
	public ECPage<Object[]> queryRelatedEventInfo(Integer eid,
			Integer eventInfoType, String title, Integer pageNo,
			Integer rowsPerPage) throws UnsupportedEncodingException {
		if (title != null) {
			title = DtoUtil.incode(title);
		}
		ECPage<Object[]> page = null;
		List<Object[]> resultList = null;
		if (eventInfoType != null) {
			resultList = new ArrayList<Object[]>();
			if (eventInfoType == 1) {// 新闻
				Criteria c = new Criteria();
				c.eq(EventNewsRefRM.eid, eid);
				if (title != null && title.length() > 0) {
					c.like(EventNewsRefRM.title, "%" + title + "%",
							CondtionSeparator.AND);
				}
				c.orderBy(EventNewsRefRM.pubtime, OrderType.DESC);
				long counts = this.genericService.count(EventNewsRef.class, c);
				c.setPage(pageNo, rowsPerPage); // 加入了分页对象
				List<EventNewsRef> enrList = genericService.findList(
						EventNewsRef.class, c);
				if (enrList != null && enrList.size() > 0) {
					for (EventNewsRef eventNewsRef : enrList) {
						Object[] obj = { eventNewsRef.getSite(),
								eventNewsRef.getTitle(),
								eventNewsRef.getSupportLevel(),
								eventNewsRef.getDupCount(),
								eventNewsRef.getCmtCount(),
								eventNewsRef.getPubdate(),
								eventNewsRef.getUrl() };
						resultList.add(obj);
					}
				}
				page = PageHelper.list(counts, resultList, c);

			} else if (eventInfoType == 2) {// 论坛
				Criteria c = new Criteria();
				c.eq(EventThreadsRefRM.eid, eid);
				if (title != null && title.length() > 0) {
					c.like(EventThreadsRefRM.title, "%" + title + "%",
							CondtionSeparator.AND);
				}
				c.orderBy(EventThreadsRefRM.pubtime, OrderType.DESC);
				long counts = this.genericService.count(EventThreadsRef.class,
						c);
				c.setPage(pageNo, rowsPerPage); // 加入了分页对象
				List<EventThreadsRef> etrList = genericService.findList(
						EventThreadsRef.class, c);
				if (etrList != null && etrList.size() > 0) {
					for (EventThreadsRef eventThreadsRef : etrList) {
						Object[] obj = { eventThreadsRef.getSite(),
								eventThreadsRef.getTitle(),
								eventThreadsRef.getSupportLevel(), 0,
								eventThreadsRef.getCmtCount(),
								eventThreadsRef.getPubdate(),
								eventThreadsRef.getUrl() };
						resultList.add(obj);
					}
				}
				page = PageHelper.list(counts, resultList, c);
			} else if (eventInfoType == 3) {// 微博
				Criteria c = new Criteria();
				c.eq(EventTwitterRefRM.eid, eid);
				if (title != null && title.length() > 0) {
					c.like(EventTwitterRefRM.summary, "%" + title + "%",
							CondtionSeparator.AND);
				}
				c.orderBy(EventTwitterRefRM.pubtime, OrderType.DESC);
				long counts = this.genericService.count(EventTwitterRef.class,
						c);
				c.setPage(pageNo, rowsPerPage);
				List<EventTwitterRef> etwrList = genericService.findList(
						EventTwitterRef.class, c);
				if (etwrList != null && etwrList.size() > 0) {
					for (EventTwitterRef eventTwitterRef : etwrList) {
						Object[] obj = { eventTwitterRef.getSite(),
								eventTwitterRef.getSummary(),
								eventTwitterRef.getSupportLevel(), 0,
								eventTwitterRef.getCmtCount(),
								eventTwitterRef.getPubdate(),
								eventTwitterRef.getUrl() };
						resultList.add(obj);
					}
				}
				page = PageHelper.list(counts, resultList, c);
			} else if (eventInfoType == 4) {// 平媒
				/*
				 * //为了调试，先注释掉 Criteria c = new Criteria();
				 * c.eq(EventPaperRefRM.eid, eid); c.like(EventPaperRefRM.title,
				 * "%"+title+"%"); c.orderBy(EventPaperRefRM.pubtime,
				 * OrderType.DESC); long counts =
				 * this.genericService.count(EventPaperRef.class, c);
				 * c.setPage(pageNo, rowsPerPage); List<EventPaperRef> eprList =
				 * genericService.findList(EventPaperRef.class, c); if
				 * (eprList!=null && eprList.size()>0) { for (EventPaperRef
				 * eventPaperRef : eprList) { Object[] obj =
				 * {eventPaperRef.getSite(), eventPaperRef.getTitle(),
				 * eventPaperRef.getSupportLevel(), 0,
				 * eventPaperRef.getCmtCount()}; resultList.add(obj); } } page =
				 * PageHelper.list(counts,resultList, c);
				 */

			}
		}
		return page;
	}

	/**
	 * 事件公关
	 * 
	 * @param map
	 * @param request
	 * @param dateStr
	 *            选择的公关时间
	 * @param eId
	 * @param pageNum
	 * @param startDate
	 *            统计开始日期，默认15天内
	 * @return
	 */
	@RequestMapping(value = "/detail4")
	public String detail4(ModelMap map, HttpServletRequest request,
			String dateStr, Integer eid, Integer pageNum, String startDate) {
		map.put("eid", eid);
		// 公关的时间list 默认最近15天的
		Criteria whereBy = new Criteria();
		whereBy.eq(EventPrRM.eventId, eid);
		Date defaultDate1 = DateUtils.getDateByDay(-14);
		whereBy.greateThenOrEquals(EventPrRM.prDate,
				DateUtils.dateToString(defaultDate1, TimeFormatter.FORMATTER2),
				CondtionSeparator.AND);
		whereBy.orderBy(EventPrRM.prDate, OrderType.DESC);
		List<EventPr> listPr = this.genericService.findList(EventPr.class,
				whereBy);
		map.put("listPr", listPr);
		// //公关后的负面信息------------------------------------------------begin begin
		// begin begin begin
		// //处理时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (dateStr != null) {
			Date defaultDate = DateUtils.getDateByDay(-7);
			dateStr = sdf.format(defaultDate);
		}
		if (startDate == null) {
			startDate = sdf.format(DateUtils.getDateByDay(-14));
		}
		String nowDateStr = sdf.format(new Date());
		// 处理当前页码
		if (pageNum == null) {
			pageNum = 0;
		}
		map.put("pageNum", pageNum);
		// //索引查询
		String path = request.getSession().getServletContext().getRealPath("/");// 获取工程的根路径
		System.out.println("zdj---------------!!!!!!!!!!!!!-----------------"
				+ path);
		SearchClient sc = new SearchClient(path + indexFirstParam,
				DataType.EVENT_MAIN);
		sc.addPrimitveTermQuery("eid", eid + "");
		sc.addSortField("pubtime2", SortOrder.desc);// 精确时分秒
		sc.addRangeTerms("support_level", "\"-100\"", "\"-1\"");
		sc.addRangeTerms("pubtime", dateStr, nowDateStr);
		sc.setStart(0);
		sc.setRow(5);
//		sc.executeItPlusIdVersion();
		sc.execute(new String[] { "it", "id" });
		// List<String> eaIdList = sc.getResultsItPlusIdVersion();
		List<String[]> eaIdList = sc.getResults(new String[] { "it", "id" });
		List<Object[]> negitiveStillConcern = queryEventAlarmList(eaIdList,
				eid, Integer.valueOf(2));
		map.put("concernNegativeList", negitiveStillConcern);// 仍需关注的负面信息，格式{String标题，Integer转发数，Integer评论数，String来源，Date发布时间}
		// 加入仍需关注的负面总信息数
		map.put("negativeInfoTotal", sc.getTotal());

		// 模拟条数
		// map.put("negativeInfoTotal", 20);

		// //公关后，负面信息--------------------------------------------------end end
		// end end end end
		return "event/detail4";
	}

	/**
	 * 
	 * 正负面面评论数据
	 * 
	 * @param type
	 *            正1负3
	 * @param eid
	 * @param totalRows
	 *            总页数
	 * @param pageNum
	 *            当前页
	 * @param rowsPerPage
	 *            每页的条数
	 * @return
	 */
	@RequestMapping(value = "/queryPositiveOrNegativeList")
	// 正面评论数据，数据格式{String评论内容，Date发布时间}
	@ResponseBody
	public ECPage<Object[]> queryPositiveOrNegativeList(
			HttpServletRequest request, ModelMap map, Integer type,
			Integer eid, Integer totalRows, Integer pageNum) {
		map.put("eid", eid);
		String path = request.getSession().getServletContext().getRealPath("/");// 获取工程的根路径
		System.out.println("zdj---------------!!!!!!!!!!!!!-----------------"
				+ path);
		SearchClient sc = new SearchClient(path + indexFirstParam,
				DataType.EVENT_MAIN);
		Object[] objData = queryEventInfoByPN(sc, eid, type, pageNum, 10);
		ECPage<Object[]> page = new ECPage<Object[]>();
		page.setCurrentPage(pageNum); // 当前页

		page.setList((List<Object[]>) objData[0]);

		int totalPageNum = totalRows / 10;
		if (totalRows % 10 > 0) {
			totalPageNum = totalRows / 10 + 1;
		}
		page.setTotalPage(totalPageNum);
		page.setRowsPerPage(10); // 每页条数
		page.setTotalRows(totalRows);
		return page;
	}

	/**
	 * 相关文章
	 * 
	 * @param map
	 * @param totalRows
	 *            总页数
	 * @param pageNo
	 *            当前页
	 * @param type
	 *            类型 1新闻，2论坛，3微博，4平媒
	 * @param title
	 *            //实际数据，格式{String来源，String文字标题，Integer情感，Integer转发数，Integer评论数,
	 *            Date发布时间}
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/queryEventInfoList")
	@ResponseBody
	public ECPage<Object[]> queryEventInfoList(ModelMap map, Integer eid,
			Integer pageNo, Integer eventInfoType, String title)
			throws UnsupportedEncodingException {
		return queryRelatedEventInfo(eid, eventInfoType, title, pageNo, 25);
	}

	/**
	 * 预警历史
	 * 
	 * @param map
	 * @param eid
	 * @param pageNo
	 * @param eventInfoType
	 * @return
	 */
	@RequestMapping(value = "/queryAlarmInfoList")
	@ResponseBody
	public ECPage<Object[]> queryAlarmInfoList(HttpServletRequest request,
			ModelMap map, Integer eid, Integer pageNum) {
		// 预警历史
		String path = request.getSession().getServletContext().getRealPath("/");// 获取工程的根路径
		System.out.println("zdj---------------!!!!!!!!!!!!!-----------------"
				+ path);
		SearchClient sc = new SearchClient(path + indexFirstParam,
				DataType.EVENT_MAIN);
		sc.addPrimitveTermQuery("eid", eid + "");
		sc.addSortField("pubtime2", SortOrder.desc);// 精确时分秒
		// 页面中，级别1、2算一般，级别3算严重，现在只查严重的
		// sc.addRangeTerms("alarm", "1", "3");//预警级别
		sc.addPrimitveTermQuery("alarm", "3");
		if (pageNum == null) {
			pageNum = 1;
		}
		sc.setStart((pageNum - 1) * 5);
		sc.setRow(5);
//		sc.executeItPlusIdVersion();
		sc.execute(new String[] { "it", "id" });
		List<String[]> eaIdList = sc.getResults(new String[] { "it", "id" });
		if (eaIdList != null && eaIdList.size() > 0) {
			List<Object[]> eaList = queryEventAlarmList(eaIdList, eid, 1);
			ECPage<Object[]> page = new ECPage<Object[]>();
			page.setCurrentPage(pageNum); // 当前页
			page.setList(eaList);
			Integer totalRows = (int) sc.getTotal();
			int totalPageNum = totalRows / 5;
			if (totalRows % 5 > 0) {
				totalPageNum = totalRows / 5 + 1;
			}
			page.setTotalPage(totalPageNum);
			page.setRowsPerPage(5); // 每页条数
			page.setTotalRows(sc.getTotal());
			return page;
		}
		return null;
	}

	/**
	 * 新闻渠道列表数据
	 * @param map
	 * @param eid
	 * @param pageNum  页码数
	 * @param propagationNodeType 新闻渠道类型，1新闻，2论坛，3微博，4平媒
	 * 返回的数据格式	{网站名称，报道量，评论数，转发数，影响力排名}
	 * @return
	 */
	@RequestMapping(value = "/queryPropagationNodeList")  
	@ResponseBody
	public ECPage<Object[]> queryPropagationNodeList(HttpServletRequest request, ModelMap map,Integer eid,Integer pageNum,Integer propagationNodeType) {
		if (propagationNodeType==null) {
			propagationNodeType = 1;
		}
		//新闻渠道类型，1新闻，2论坛，3微博，4平媒
		//返回数据格式：String 网站名称，Integer 报道量，Integer 评论数，Integer 转发数，Integer 影响力排名
		if (eid!=null) {
			//根据eid，从event_brief中找到相应的数据
			Criteria c = new Criteria();
			c.eq(EventBriefRM.eid, eid);
			EventBrief eventBrief = genericService.findOne(EventBrief.class, c);
			String tableName = null;
			if (eventBrief != null) {
				SearchClient sc = null;
				if (propagationNodeType!=null) {
					String path = request.getSession().getServletContext().getRealPath("/");//获取工程的根路径
					if (propagationNodeType==1) {//新闻
						sc = new SearchClient(path+indexFirstParam, DataType.EVENT_NEWS);
						tableName = "event_news_ref";
					} else if (propagationNodeType==2) {//论坛
						sc = new SearchClient(path+indexFirstParam, DataType.EVENT_MAIN_THREAD);
						tableName = "event_threads_ref";
					} else if (propagationNodeType==3) {//微博
						sc = new SearchClient(path+indexFirstParam, DataType.EVENT_TWITTER);
						tableName = "event_twitter_ref";
					}
				}
				//把时间处理成字符串
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat sdfForSql = new SimpleDateFormat("yyyy-MM-dd");
				String startTime = sdf.format(eventBrief.getStartTime());
				String endTime = sdf.format(eventBrief.getEndTime());
				String startTimeForSql = sdfForSql.format(eventBrief.getStartTime());
				String endTimeForSql = sdfForSql.format(eventBrief.getEndTime());
				sc.addRangeTerms("pubtime", startTime, endTime);
				sc.addPrimitveTermQuery("eid", eid + "", FieldOccurs.MUST);
				//微博、（新闻、论坛）需求不同，区分开
				String facedStr = "site";
				if (propagationNodeType==3) {
					facedStr = "author_url";
				} 
				sc.setFacetField(facedStr,100);//按照报道量倒排，取前100个
				sc.execute();
				Map<String, Long> resultMap = sc.getFacetValues(facedStr);//这样得到的是报道量
				List<Object[]> pageListAll = new ArrayList<Object[]>();//把查询到的所有数据转存到list中方便做分页
				List<Object[]> pageList = new ArrayList<Object[]>();//存放当前页的数据
				Set<String> keySet = resultMap.keySet();
				for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			    	String s = (String) it.next();
			        pageListAll.add(new Object[]{s, resultMap.get(s)});
			    }
				int eventPageSize = 10;//默认pageSize为10
				Integer pageStart = (pageNum-1)*eventPageSize;//分页开始的记录数量
				Integer pageEnd = pageNum*eventPageSize;//分页结束的记录数量
				if (pageEnd > resultMap.size()-1) {//处理数据不足整页的情况
					pageEnd = resultMap.size()-1;
				}
				//微博和（新闻、论坛）需求不同，分开处理
				if (propagationNodeType==3) {
					for (int i = pageStart; i < pageEnd; i++) {
						Object[] obj = pageListAll.get(i);
						Criteria c4Weibo = new Criteria();
						c4Weibo.eq(WeiboAnalysisRM.url, obj[0]);
						WeiboAnalysis oneWeibo = genericService.findOne(WeiboAnalysis.class, c4Weibo);
						List<Map<String, Object>> sumList = null;
						if (oneWeibo!=null) {
							String sumSql = "select sum(cmt_count) scc,sum(cus_score) sdc from " + tableName + " where author_address=" + obj[0] + " and eid="+ eid + " and pubtime between '" +startTimeForSql+ "' and '" +endTimeForSql+ "' ";
							sumList = genericService.findListBySql(sumSql);
							//最后传回的数据格式{String博主名称，Integer博文数，Integer评论数，Integer转发数， Integer粉丝数，String博主URL}
							pageList.add(new Object[]{oneWeibo.getName(), obj[1], sumList.get(0).get("scc"), sumList.get(0).get("sdc")==null?0:sumList.get(0).get("sdc"), oneWeibo.getFansCount(), obj[0]});
						}
					}
				} else {
					//这里需要根据站点site，事件eid，从数据库表中sum评论数和转发数
					//取出全部的alax排名
					List<NetworkMediaAnalysis> allAlaxSite = genericService.findAll(NetworkMediaAnalysis.class);
					for (int i = pageStart; i < pageEnd; i++) {
						Object[] obj = pageListAll.get(i);
						String sumSql = "select sum(cmt_count) scc,sum(cus_score) sdc from " + tableName + " where url like '%" + obj[0] + "%' and eid="+ eid + " and pubtime between '" +startTimeForSql+ "' and '" +endTimeForSql+ "' ";
						List<Map<String, Object>> sumList = genericService.findListBySql(sumSql);
						//查询alax影响力排名
						String siteAlaxScore = "未知";
						String siteAlaxUrl = "none";
						String siteAlaxName = obj[0].toString();
						for (NetworkMediaAnalysis oneAlaxSite : allAlaxSite){
							String alaxSite = oneAlaxSite.getUrl().replace("http://www.123cha.com/alexa/", "");
							if (obj[0].toString().contains(alaxSite)) {
								siteAlaxScore = oneAlaxSite.getSynthesisRanking()+"";
								siteAlaxUrl = oneAlaxSite.getUrl();
								siteAlaxName = oneAlaxSite.getName();
								break;
							}
						}
						//数组格式：String 网站名称，Integer 报道量，Integer 评论数，Integer 转发数，Integer 影响力排名，String alax网站排名地址
						if (sumList!=null && sumList.size()>0) {
							pageList.add(new Object[]{siteAlaxName, obj[1], sumList.get(0).get("scc"), sumList.get(0).get("sdc")==null?0:sumList.get(0).get("sdc"), siteAlaxScore, siteAlaxUrl});
						}
					}
				}
				//分页
				ECPage<Object[]> page = new ECPage<Object[]>();
				page.setCurrentPage(pageNum);  //当前页
				page.setList(pageList);
			    Integer totalRows = resultMap.size();
				int totalPageNum = totalRows/10;
		        if (totalRows%10 > 0 ){
		           totalPageNum = totalRows/10 + 1;
		        }
		        page.setTotalPage(totalPageNum);
				page.setRowsPerPage(10);   //每页条数
				page.setTotalRows(totalRows);
				return page;
			}
		}
		return null;
	}

	/**
	 * 仍需要关注的负面信息
	 * 
	 * @param map
	 * @param eid
	 * @param dateStr
	 * @param startDate
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/queryConcernNegativeList")
	@ResponseBody
	public ECPage<Object[]> queryConcernNegativeList(
			HttpServletRequest request, ModelMap map, Integer eid,
			String dateStr, Integer pageNum) {
		// //公关后的负面信息------------------------------------------------begin begin
		// begin begin begin
		// //处理时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (dateStr == null) {
			Date defaultDate = DateUtils.getDateByDay(-7);
			dateStr = sdf.format(defaultDate);
		} else {
			try {
				dateStr = sdf.format(new SimpleDateFormat("yyyy-MM-dd")
						.parse(dateStr));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String nowDateStr = sdf.format(new Date());

		// 索引查询
		String path = request.getSession().getServletContext().getRealPath("/");// 获取工程的根路径
		System.out.println("zdj---------------!!!!!!!!!!!!!-----------------"
				+ path);
		SearchClient sc = new SearchClient(path + indexFirstParam,
				DataType.EVENT_MAIN);
		sc.addPrimitveTermQuery("eid", eid + "");
		sc.addSortField("pubtime2", SortOrder.desc);// 精确时分秒
		sc.addPrimitveTermQuery("support_level", "3");
		sc.addRangeTerms("pubtime", dateStr, nowDateStr);
		sc.setStart((pageNum - 1) * 10);
		sc.setRow(10);
//		sc.executeItPlusIdVersion();
		sc.execute(new String[] { "it", "id" });
		//
		// List<String> eaIdList = sc.getResultsItPlusIdVersion();
		List<String[]> eaIdList = sc.getResults(new String[] { "it", "id" });
		List<Object[]> negitiveStillConcern = queryEventAlarmList(eaIdList,
				eid, Integer.valueOf(2)); // 仍需关注的负面信息，格式{String标题，Integer转发数，Integer评论数，String来源，Date发布时间}
		// 处理当前页码
		if (pageNum == null) {
			pageNum = 1;
		}
		// map.put("pageNum", pageNum);
		// map.put("concernNegativeList", negitiveStillConcern);
		// 加入仍需关注的负面总信息数
		// map.put("negativeInfoTotal", sc.getTotal());

		ECPage<Object[]> page = new ECPage<Object[]>();
		page.setCurrentPage(pageNum); // 当前页
		page.setList(negitiveStillConcern);
		Integer totalRows = (int) sc.getTotal();
		int totalPageNum = totalRows / 10;
		if (totalRows % 10 > 0) {
			totalPageNum = totalRows / 10 + 1;
		}
		page.setTotalPage(totalPageNum);
		page.setRowsPerPage(10); // 每页条数
		page.setTotalRows(totalRows);
		return page;

	}

	/**
	 * 首页界面查询
	 * 
	 * @param map
	 * @param searchStr
	 * @param pageNo
	 * @param rowsPerPage
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/eventSearch")
	public String eventSearch(ModelMap map, String searchStr, Integer pageNo)
			throws UnsupportedEncodingException {
		Criteria c = new Criteria();
		if (searchStr != null && searchStr.trim() != "") {
			searchStr = DtoUtil.incode(searchStr);
			c.like(EventBriefRM.name, "%" + searchStr.trim() + "%");
			map.put("searchStr", searchStr.trim());
		}
		long counts = this.genericService.count(EventBrief.class, c); // 总数
		c.setPage(pageNo, 25); // 加入了分页对象
		c.orderBy(EventBriefRM.createTime, OrderType.ASC);
		List<EventBrief> ebList = genericService.findList(EventBrief.class, c);
		ECPage<EventBrief> page = PageHelper.list(counts, ebList, c);
		map.put("page", page);
		return "event/search";
	}

	/**
	 * 事件检索界面检索(二级界面)
	 * 
	 * @param map
	 * @param searchStr
	 * @param pageNo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/secondSearch")
	@ResponseBody
	public ECPage<EventBrief> secondSearch(ModelMap map, String searchStr,
			Integer pageNo) throws UnsupportedEncodingException {
		Criteria c = new Criteria();
		if (searchStr != null && searchStr.trim() != "") {
			searchStr = DtoUtil.incode(searchStr);
			c.like(EventBriefRM.name, "%" + searchStr.trim() + "%");
		}
		long counts = this.genericService.count(EventBrief.class, c); // 总数
		c.setPage(pageNo, 25); // 加入了分页对象
		c.orderBy(EventBriefRM.createTime, OrderType.ASC);
		List<EventBrief> ebList = genericService.findList(EventBrief.class, c);
		ECPage<EventBrief> page = PageHelper.list(counts, ebList, c);
		return page;
	}

	/**
	 * 公关效果图————数据图 默认是最近15天
	 * 
	 * @param eId
	 * @param eventInfoType
	 * @return
	 */
	@RequestMapping(value = "/prChart")
	@ResponseBody
	public List<ChartCoreDataVO> prChart(HttpServletRequest request,
			Integer eid, String sdate, String edate) {
		if (StringUtils.isBlank(sdate) && StringUtils.isBlank(edate)) {
			sdate = DateUtils.dateToString(DateUtils.getDateByDay(-14),
					TimeFormatter.FORMATTER2);
			edate = DateUtils
					.dateToString(new Date(), TimeFormatter.FORMATTER2);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dsd = null;// 开始date
		Date ded = null;// 结束date
		try {
			dsd = sdf.parse(sdate);
			ded = sdf.parse(edate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 计算开始、结束时间之间的天数
		Long dateCount = (ded.getTime() - dsd.getTime())
				/ (1000 * 60 * 60 * 24);
		// 索引查询
		String path = request.getSession().getServletContext().getRealPath("/");// 获取工程的根路径
		System.out.println("zdj---------------!!!!!!!!!!!!!-----------------"
				+ path);
		SearchClient sc = new SearchClient(path + indexFirstParam,
				DataType.EVENT_MAIN);
		List<ChartCoreDataVO> cccvList = new ArrayList<ChartCoreDataVO>();
		SimpleDateFormat sdfForIndex = new SimpleDateFormat("yyyyMMdd");
		Calendar ca = Calendar.getInstance();
		ca.setTime(dsd);
		Date d = null;
		for (int i = 0; i <= dateCount; i++) {
			if (i > 0) {
				ca.add(Calendar.DAY_OF_YEAR, 1);
			}
			d = ca.getTime();
			String dateStrForIndex = sdfForIndex.format(d);
			String dateStrForP = sdf.format(d);
			Long count = queryInfoCountByDay(sc, eid, dateStrForIndex);
			ChartCoreDataVO ccdv = new ChartCoreDataVO();
			ccdv.setName(dateStrForP);
			ccdv.setValue(count.intValue());
			cccvList.add(ccdv);
		}

		Subject su = SecurityUtils.getSubject();
		System.out.println(su.isPermitted("/mouth/export"));

		return cccvList;
		// 模拟数据
		// return getTmepChar();
	}

	/**
	 * 公关时间节点 默认是最近15天
	 * 
	 * @param eid
	 * @param sdate
	 * @param edate
	 * @return
	 */
	@RequestMapping(value = "/prDate")
	@ResponseBody
	public List<EventPr> prDate(Integer eid, String sdate, String edate) {
		Criteria whereBy = new Criteria();
		whereBy.eq(EventPrRM.eventId, eid);
		if (StringUtils.isBlank(sdate) && StringUtils.isBlank(edate)) {
			sdate = DateUtils.dateToString(DateUtils.getDateByDay(-14),
					TimeFormatter.FORMATTER2);
			whereBy.greateThenOrEquals(EventPrRM.prDate, sdate,
					CondtionSeparator.AND);
		}
		if (StringUtils.isNotBlank(sdate)) {
			whereBy.greateThenOrEquals(EventPrRM.prDate, sdate,
					CondtionSeparator.AND);
		}
		if (StringUtils.isNotBlank(edate)) {
			whereBy.lessThenOrEquals(EventPrRM.prDate, edate,
					CondtionSeparator.AND);
		}
		whereBy.orderBy(EventPrRM.prDate, OrderType.DESC);
		List<EventPr> listPr = this.genericService.findList(EventPr.class,
				whereBy);
		// 模拟数据
		return listPr;

	}

	/**
	 * 公关时间公关———数据图
	 * 
	 * @param eId
	 * @param prDate
	 * @return
	 */
	@RequestMapping(value = "/getPrData")
	@ResponseBody
	public Object[] getPrData(HttpServletRequest request, Integer eid,
			String prDate) {
		// 公关时间，把yyyy-MM-dd格式转换成yyyyMMdd格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowDateStr = sdf.format(new Date());// 当前时间
		String prDateAfter = null;
		try {
			if (prDate != null) {
				prDateAfter = sdf.format(new SimpleDateFormat("yyyy-MM-dd")
						.parse(prDate));
			} else {
				return null;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 公关后，正面信息数量
		String path = request.getSession().getServletContext().getRealPath("/");// 获取工程的根路径
		System.out.println("zdj---------------!!!!!!!!!!!!!-----------------"
				+ path);
		SearchClient sc = new SearchClient(path + indexFirstParam,
				DataType.EVENT_MAIN);
		sc.addPrimitveTermQuery("eid", eid + "");
		sc.addPrimitveTermQuery("support_level", "1");
		sc.addRangeTerms("pubtime2", prDateAfter + "000000", nowDateStr
				+ "235959");
		sc.setStart(0);
		sc.setRow(1);
		sc.executeItPlusIdVersion();
		long positiveInfoTotal = sc.getTotal();
		// 公关后，负面信息数量
		sc.reset();
		sc.addPrimitveTermQuery("eid", eid + "");
		sc.addPrimitveTermQuery("support_level", "3");
		sc.addRangeTerms("pubtime2", prDateAfter + "000000", nowDateStr
				+ "235959");
		sc.setStart(0);
		sc.setRow(1);
		sc.executeItPlusIdVersion();
		long negativeInfoTotal = sc.getTotal();
		Object[] obj = new Object[4];
		obj[0] = positiveInfoTotal + negativeInfoTotal;// 新增总数
		obj[1] = positiveInfoTotal;// 新增正面评价数量
		obj[2] = negativeInfoTotal;// 新增负面评价数量
		obj[3] = Math
				.round(((double) negativeInfoTotal / ((double) positiveInfoTotal + (double) negativeInfoTotal)) * 100)
				+ "%"; // 公关后负面占比
		return obj;
	}

	/**
	 * 公关时间公关———数据图
	 * 
	 * @param eId
	 * @param prDate
	 * @return
	 */
	@RequestMapping(value = "/addPrdate")
	@ResponseBody
	public AjaxJson addPrdate(Integer eid, String prDate) {
		AjaxJson json = new AjaxJson();
		try {

			EventPr pr = this.genericService.findOne(
					EventPr.class,
					new Criteria().eq(EventPrRM.eventId, eid).eq(
							EventPrRM.prDate, prDate, CondtionSeparator.AND));
			if (null != pr) {

				json.setMsg("此公关结点已存在!");
				json.setSuccess(false);
			} else {// 创建
				EventPr po = new EventPr();
				po.setEventId(eid);
				po.setCtime(new Date());
				po.setPrDate(DateUtils.stringToDate(prDate,
						TimeFormatter.FORMATTER2));
				com.ecarinfo.ralasafe.po.RalUser user = EcUtil.getCurrentUser();
				po.setUserId(user.getUserId());
				this.genericService.save(po);
				json.setMsg("添加成功！");
				json.setSuccess(true);
				json.setObj(po);
			}
		} catch (Exception e) {
			json.setMsg("设置公关结点失败！");
			json.setSuccess(false);
			logger.error("设置公关结点失败！", e);

		}
		return json;
	}

	/**
	 * 删除事件
	 * 
	 * @param eid
	 * @return
	 */
	@RequestMapping(value = "/delEventBrief")
	@ResponseBody
	public AjaxJson delEventBrief(Integer eid) {
		AjaxJson json = new AjaxJson();

		if (eid != null && eid > 0) {
			try {
				this.genericService.delete(EventKeyWords.class,
						new Criteria().eq(EventKeyWordsRM.eid, eid));
				this.genericService.deleteByPK(EventBrief.class, eid);
				json.setMsg("删除成功！");
				json.setSuccess(true);
			} catch (Exception e) {
				json.setMsg("删除失败！");
				json.setSuccess(false);
				logger.error("删除失败！", e);
			}
		} else {
			json.setMsg("删除失败！");
			json.setSuccess(false);
		}

		return json;
	}

	@RequestMapping("/upimg")
	public void upimg(HttpServletRequest request,HttpServletResponse response) {
		AjaxJson json = new AjaxJson();
		try {
			// 转型为MultipartHttpRequest(重点的所在)
			 MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
			// 获得第1张图片（根据前台的name名称得到上传的文件）
			 Iterator<String> iter = multipartRequest.getFileNames();
					MultipartFile file = multipartRequest.getFile((String) iter
							.next());
				
//			MultipartFile file = multipartRequest.getFile("myfile");

			// 获得文件名：
			String filename = file.getOriginalFilename();

			String imgtype = filename.substring(filename.lastIndexOf("."));
			
			System.out.println("文件原名: " + filename);
            System.out.println("文件名称: " + file.getName());
            System.out.println("文件长度: " + file.getSize());
            System.out.println("文件类型: " + file.getContentType());
			// 获取路径 request.getSession().getServletContext().getRealPath("/");

			String ctxPath = request.getSession().getServletContext().getRealPath("/")+ "/upload/images/";
			// System.out.println("classpath:"+ctxPath1);

			// 创建文件
			File dirPath = new File(ctxPath);
			if(!dirPath.getParentFile().exists()) {
				dirPath.getParentFile().mkdirs();
			}
			if (dirPath.exists()) {
				dirPath.delete();
			}
			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
			String fileName = DateUtils.dateToString(new Date(),TimeFormatter.FORMATTER11);
			File uploadFile = new File(ctxPath + fileName + imgtype);

			FileCopyUtils.copy(file.getBytes(), uploadFile);

			System.out.println("********************imgFile1******");
			json.setSuccess(true);
			json.setObj("/upload/images/"+fileName+imgtype);
			printAjax(response, JSONUtil.toJson(json));

		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("图片上传失败！");
			logger.error("图片上传失败", e);
			printAjax(response, JSONUtil.toJson(json));
		}
	}
	
	/**
	 * 内容输出
	 */
	public static void printAjax(HttpServletResponse response, String content) {
		try {
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error("printAjax内容输出异常");
		}
	}
}