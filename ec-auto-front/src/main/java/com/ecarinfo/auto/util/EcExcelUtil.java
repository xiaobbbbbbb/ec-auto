package com.ecarinfo.auto.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecarinfo.utils.poi.ExcelUtil;

public class EcExcelUtil {

	/**
	 * excel根据模板文件下载
	 * 
	 * @param: fileName 下载文件的名称 如：用户信息表.xls
	 * @param: templateFileName 模板文件的名称 如：user.xls
	 * @param: datas 要填充的数据map
	 * @param: objs 数据集合
	 * @param: clazz 对象名称
	 */
	public static void excelDownload(String fileName, String templateFileName,
			Map<String, String> datas, List<?> objs, Class<?> clazz,
			HttpServletResponse response, HttpServletRequest request) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		OutputStream os = null;
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			os = response.getOutputStream();
			String path = request
					.getSession()
					.getServletContext()
					.getRealPath(
							File.separator + "template" + File.separator
									+ templateFileName);
			ExcelUtil.getInstance().exportObjToExcelByTemplate(datas, path, os,
					objs, clazz, false);
		} catch (Exception e) {
		}
	}

	public static void excelDownloadEx(String fileName,
			String templateFileName, Map<String, String> datas,
			List<String> fieldNames, List<String> titleNames, List<?> objs,
			Class<?> clazz, HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		OutputStream os = null;
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			os = response.getOutputStream();
			String path = request
					.getSession()
					.getServletContext()
					.getRealPath(
							File.separator + "template" + File.separator
									+ templateFileName);
			ExcelUtil.getInstance().exportObjToExcelByTemplateEx(datas, path,
					os, fieldNames, titleNames, objs, clazz, false);
		} catch (Exception e) {
		}
	}

	// 普通文件下载
	public static void fileDownload(File file, HttpServletResponse response) {
		try {
			if (file.exists()) {
				InputStream fis = new BufferedInputStream(new FileInputStream(
						file.getPath()));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				// 清空response
				response.reset();
				// 设置response的Header
				response.setHeader("Content-Disposition",
						"attachment;filename="
								+ new String(file.getName().getBytes("gb2312"),
										"ISO8859-1"));
				response.addHeader("Content-Length", "" + file.length());
				OutputStream toClient = new BufferedOutputStream(
						response.getOutputStream());
				response.setContentType("application/octet-stream");
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		
		}
	}

	public static void downloadLocal(HttpServletResponse response, File file)
			throws FileNotFoundException {
		try{
		// 下载本地文件
		String fileName = file.getName().toString(); // 文件的默认保存名
		// 读到流中
		InputStream inStream = new FileInputStream(file); // 文件的存放路径
		// 设置输出的格式
		response.reset();
//		response.setContentType(" bin ");
//		response.addHeader(" Content-Disposition ", "attachment;filename=\""
//				+ fileName + "\"");
		// 设置response的编码方式    
		response.setContentType("application/x-msdownload");
		// 写明要下载的文件的大小    
		response.setContentLength((int)file.length());

// 解决中文乱码    
	response.setHeader("Content-Disposition","attachment;filename="
			+ new String(fileName.getBytes("UTF-8"),
					"iso8859-1"));
		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len;
			while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
