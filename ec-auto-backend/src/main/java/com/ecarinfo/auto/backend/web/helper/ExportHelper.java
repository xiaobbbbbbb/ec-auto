package com.ecarinfo.auto.backend.web.helper;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.ecarinfo.auto.po.UserInfo;
import com.ecarinfo.persist.service.GenericService;


@Component
public class ExportHelper {
	public static final String UPLOAD_TEMP_DIR = "/upload/temp/";
	public static final String UPLOAD_DIR = "/upload/images/";
	public static final String EXCEL_DIR = "/download/excels/";
	public static final String EXCEL_AFFIX = ".xls";
	public static final int UPLOAD_MAX_BYTES = 1*1024*1024;
	
	public static long TIME_SIX_DAY = 6l*24*3600*1000;
	public static long TIME_TWENTY_NINE_DAY = 29l*24*3600*1000;
	public static long TIME_SEVEN_DAY = 7l*24*3600*1000;
	public static long TIME_FIFTEEN_SECOND = 15*60*1000;	
	@Resource
	GenericService baseService;
	
	/**
	 * 下载活动列表
	 * @return 
	 */
/*	public String activityListDownload(List<?> dtos, HttpServletRequest request, HttpServletResponse response) {
		if (CollectionUtils.isEmpty(dtos)) {
			return null;
		}		
		String templateFileName = "activity.xls";
	
		Map<String, String> datas = new LinkedHashMap<String, String>();
		datas.put("title", "车信活动信息表" + "(" + dtos.size() + "条记录)");
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		datas.put("date", f.format(new Date()));
		datas.put("name", "深圳奥创科技有限公司");
		String[] titleNames = new String[]{"序号", "发布日期", "开始时间", "截止时间", "标题", "内容", "分类", "点击数", "发布者", "状态"};
		String[] fieldNames = new String[]{"sqId", "showCtime", "showBtime", "showEtime", "title", "content", "typeName", "clickNums", "cusername", "statusName"};

		ExcelTemplate template = EcExcelUtil.excelDownloadEx(templateFileName, datas, fieldNames, titleNames, dtos, ActivityVO.class, response, request);
		storeExcelFile(template, request, response, ExportType.ACTIVITY);
		return null;
	}*/


	public enum ExportType {
		DEMO("全部车辆"),
		;
		private String showName;
		private ExportType(String showName) {
			this.showName = showName;
		}
		public String getShowName() {
			return showName;
		}
				
		public String getName() {
			return this.name().toLowerCase();
		}
	}

	public String userListDownload(List<UserInfo> list,
			HttpServletRequest request, HttpServletResponse response) {
		
		return null;
	}
	
}
