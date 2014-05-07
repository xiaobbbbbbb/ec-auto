<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="base.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${ctx}/media/css/common/iframe.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/online/pager.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
	//到指定的分页页面
	function topage(page) {
		var form = document.forms[0];
		form.page.value = page;
		form.submit();
	}
</script>
<input type="hidden" name="page" value="${param.page}" />
<table>
	<tbody>
		<tr>
			<td>
				<div id="page_tip">
					当前第<span id="current_page">${ECPage.currentPage}</span>页/每页<span id="rows_page">${ECPage.rowsPerPage}</span>条记录&nbsp;|&nbsp;共<span id="total_page">${ECPage.totalPage}</span>页/<span
						id="total_count">${ECPage.totalRows}</span>条记录
				</div>
			</td>
			<c:if test="${ECPage.totalPage>0}">
				<td>
					<div class="pagination" id="Pagination">
						<c:choose>
							<c:when test="${ECPage.currentPage>1}">
								<a href="javascript:topage('${ECPage.currentPage-1}');" class="prev">上一页</a>
							</c:when>
							<c:otherwise>
								<span class="current prev">上一页</span>
							</c:otherwise>
						</c:choose>
						<c:forEach begin="${ECPage.startPage}" end="${ECPage.endPage}" var="wp">
							<c:choose>
								<c:when test="${ECPage.currentPage==wp}">
									<span class="current">${wp}</span>
								</c:when>
								<c:otherwise>
									<a href="javascript:topage('${wp}');">${wp}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:choose>
							<c:when test="${ECPage.currentPage<ECPage.totalPage}">
								<a href="javascript:topage('${ECPage.currentPage+1}');" class="next"><span>下一页</span></a>
							</c:when>
							<c:otherwise>
								<span class="current next">下一页</span>
							</c:otherwise>
						</c:choose>
					</div>
				</td>
			</c:if>
		</tr>
	</tbody>
</table>


