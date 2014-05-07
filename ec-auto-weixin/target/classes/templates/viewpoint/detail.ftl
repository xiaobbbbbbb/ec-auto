<!DOCTYPE html>
<html>
<head>
<title>观点相关文章列表</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.0/jquery.mobile-1.4.0.min.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.0/jquery.mobile-1.4.0.min.js"></script>
<#include "../common/header_script.ftl">
</head>
<body>

	<div data-role="page" id="home">
		<div data-role="header" data-theme="c">
			<h1></h1>
			<a data-rel="back" href="#" data-role="button" data-icon="arrow-l" class="ui-btn-left" data-theme="c">返回</a>	
		</div>

		<div data-role="content">
			
			<#if page.list??> 
			<ul data-role="listview" data-inset="true" data-divider-theme="c">
				<li data-role="list-divider">${viewpoint.name} <span class="ui-li-count">${articlePercent*100}%</span></li>

				<#list page.list as dto>
			    <li><a href="/article/detail?userId=${userId}&articleId=${dto.id?c}">${dto.title}</a></li>
			    </#list> 
				<li>
					<div data-role="controlgroup" data-type="horizontal" id="pager" pageSize="${page.pageSize?c}" data-mini="true">
					    <a href="/viewpoint/detail?viewpointId=${viewpoint.id?c}&userId=${userId?c}&brandId=${brandId}&searchDateType=${searchDateType}&searchBdate=${searchBdate}&searchEdate=${searchEdate}&articlePercent=${articlePercent}&page=${page.prePage}" data-role="button" data-icon="arrow-l" data-iconpos="left">上一页</a>
					    <a href="/viewpoint/detail?viewpointId=${viewpoint.id?c}&userId=${userId?c}&brandId=${brandId}&searchDateType=${searchDateType}&searchBdate=${searchBdate}&searchEdate=${searchEdate}&articlePercent=${articlePercent}&page=${page.nextPage}" data-role="button" data-icon="arrow-r" data-iconpos="right">下一页</a>					    				         
					</div>
					页次${page.page?c}/${page.totalPage?c}
				</li>
			</ul>
			</#if>
		</div>

		<#include "../common/footer.ftl">
		<#include "../common/message.ftl">
	</div>
</body>
</html>