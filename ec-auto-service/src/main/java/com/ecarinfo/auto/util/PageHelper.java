package com.ecarinfo.auto.util;

import java.util.List;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;

public class PageHelper {

	/**
	 * 按给定条件分页查找(多表) 组装分页结果
	 */
	public static <T> ECPage<T> list(Long counts, List<T> list, Criteria criteria) {
		// 分页查询
		ECPage<T> page = new ECPage<T>();
		if (counts == null) {
			counts = 0l;
		}
		int rowsPerPage = criteria.getRowsPerPage();
		if (rowsPerPage == 0) {
			rowsPerPage = ECPage.DEFAULT_SIZE;
		}
		long pageNum = counts / rowsPerPage;
		if (counts % rowsPerPage == 0) {
			page.setTotalPage(pageNum);
		} else {
			page.setTotalPage(pageNum + 1);
		}
		page.setCurrentPage(criteria.getCurrentPage());
		page.setTotalRows(counts);
		page.setList(list);
		page.setRowsPerPage(rowsPerPage);
		return page;
	}
}
