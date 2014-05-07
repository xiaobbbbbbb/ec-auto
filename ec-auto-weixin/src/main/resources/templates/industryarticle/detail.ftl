<!DOCTYPE html>
<html>
<head>
<title>行业新闻详情</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.0/jquery.mobile-1.4.0.min.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.0/jquery.mobile-1.4.0.min.js"></script>
<script type="text/javascript">
</script>
</head>
<body>

	<div data-role="page" id="home">
		<div data-role="header" data-theme="c">
			<h1></h1>
			<a data-rel="back" href="#" data-role="button" data-icon="arrow-l" class="ui-btn-left" data-theme="c">返回</a>
		</div>

		<div data-role="content">
			<div>
				<h2>${article.title}</h2>
				<p>${article.ctime?date}</p>
				<p>${article.simpleContent}</p>
				<p><a href="${article.url}">查看原文</a></p>
			</div>
			
			<#if page.list??> 
			<ul data-role="listview" data-inset="true" data-divider-theme="b">
				<li data-role="list-divider">相关动态(${page.total?c})</li>

				<#list page.list as dto>
			    <li><a href="/industry_article/detail?userId=${userId}&articleId=${dto.id?c}">${dto.title}</a></li>
			    </#list> 
				<li>
					<div data-role="controlgroup" data-type="horizontal" id="pager" pageSize="${page.pageSize?c}" data-mini="true">
					    <a href="/industry_article/detail?articleId=${article.id?c}&page=${page.prePage?c}&userId=${userId?c}" data-role="button" data-icon="arrow-l" data-iconpos="left">上一页</a>
					    <a href="/industry_article/detail?articleId=${article.id?c}&page=${page.nextPage?c}&userId=${userId?c}" data-role="button" data-icon="arrow-r" data-iconpos="right">下一页</a>        
					</div>
					页次${page.page?c}/${page.totalPage?c}
				</li>
			</ul>
			</#if>
		</div>

		<#include "../common/footer.ftl">
	</div>
</body>
</html>