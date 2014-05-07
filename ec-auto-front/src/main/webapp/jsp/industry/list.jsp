<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/js/paginate/css/style.css" rel="stylesheet" type="text/css"  media="screen"/>
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>

<script type="text/javascript">
	var a_id = '${aid}';
	
	function loadArticleById() {
		if(!a_id) {
			return false;
		}
		var url = '${ctx}/industry/article?aid='+a_id+'&cpage=1';
		$.getJSON(url, function(data) {
			var sp ="&nbsp;&nbsp;";
			$('.a_title').text(data.title);
			$('.a_mediaName').html(sp+(data.mediaName?data.mediaName:'暂无')+sp+sp+sp);
			$('.a_ptime').html(data.ptime+sp+sp+sp);
			$('.a_content').html(sp+sp+sp+data.content);
			$(".a_original_url,.a_original_url_more").attr("href", data.url);
			
			loadComments(data,1,true);
			loadArticles(data.id,1);
		});
	}
	
	function loadComments(article,cpage,first) {
		var url = '${ctx}/industry/comments?aid='+article.id+'&cpage='+cpage;
		$.getJSON(url, function(data) {
			var tbody = $('#commonDataTable');
			tbody.empty();
			var tb = '';
			
			if(data.list.length){
				for (var i = 0; i < data.list.length; i++) {
					var obj = data.list[i];
					tb += '<tr><td class="td_border td_first">' + obj.content + '</td>';
					tb += '<td class="td_border">' + (article.mediaName?article.mediaName:'') + '</td>';
					tb += '<td class="td_border">' + obj.createTime + '</td></tr>';
				}
				tbody.append(tb);
				$("#comment_sum").html("评论列表("+data.totalRows+")");
				if(data.list.length > 0) {
					$("#_commons").paginate({
						totalPage 	: data.totalPage,
						currentPage : data.currentPage,
						display  	: 30,
						onLoad     	: function(page){
									loadComments(article,page,false);
								  }
					});
					
				}
			}else{
				 
				tb = '<tr><td colspan="3" class="td_border">暂无数据</td></tr>';
				tbody.append(tb);
				$("#_commons").empty();
				$("#comment_sum").html("评论列表(0)");
			}
			if(first){
				$("#two").hide();
			}
		});
	}
	
	function loadArticles(aid,cpage) {
		 
		if(!aid) {
			return false;
		}
		var url = '${ctx}/industry/articles?aid='+aid+'&cpage='+cpage;
	
		$.getJSON(url, function(data) { 
			
			
			var tbody = $('#articleDataTable');
			tbody.empty();
			var tb = '';
			if(data.list){
				for (var i = 0; i < data.list.length; i++) {
					var obj = data.list[i];
					tb += '<tr><td class="td_border td_first"><a href="${ctx}/industry/listArticles/'+obj.id+'">' + obj.title + '</a></td>';
					tb += '<td class="td_border">' + (obj.mediaName?obj.mediaName:'') + '</td>';
					tb += '<td class="td_border">' + obj.ptime + '</td></tr>';
				}
				tbody.append(tb);
				$("#content_sum").html("相关信息("+data.totalRows+")");
				if(data.list.length > 0) {
					$("#_articles").paginate({
						totalPage 	: data.totalPage,
						currentPage : data.currentPage,
						display  	: 30,
						onLoad    : function(page){
								loadArticles(aid,page);
							  }
					});
				}
			}else{
				
				tb = '<tr><td colspan="3" class="td_border">暂无数据</td></tr>';
				tbody.append(tb);
				$("#_articles").empty();
				$("#content_sum").html("相关信息(0)");
			}
			
		});
	}
	
	$(function() {
		loadArticleById();
		$(".else_title").click(function(){
			if($(this).attr("class").indexOf("else_title_current")!=-1){
				return;
			}
			$(".else_title").removeClass("else_title_current");
			$(this).addClass("else_title_current");
			$("#one,#two").toggle();
		});
	});
</script>

</head>
<body>
	<center>
		<jsp:include page="../common/head.jsp" />
		<div class="all">
			<div class="detail_center">
				<span class="detail_center_tag"><a href="${ctx }/index/">首页</a>    &gt; <a href="${ctx }/industry/index">动态新闻</a>  &gt; <span class="a_title"></span></span>
					
				<div class="detail_center_content" >
					<div class="content_div_title">
						<div class="a_title"></div>
						<div class="a_other">
						来源<span class="a_mediaName" ></span>
						<span class="a_ptime" ></span>
						<a class="a_original_url" href="javascript:void();" target="_blank" >查看原网页</a>
						</div>
					</div>
					<div class="a_content"></div>
					<a class="a_original_url_more" href="javascript:void(0);" target="_blank">查看更多</a>
				</div>
				
				<div class="detail_center_else" >

					<a id="content_sum" class="else_title else_title_current" >
						相关信息(0)
					</a>

					<a id="comment_sum" class="else_title" style="margin-left: 5px" >
						评论列表(0)
					</a>
					<div class="clear"></div>
					<div id="one" class="page_content_div">
						<div class="page_content">
							<table cellspacing="0" cellpadding="0" >
								<tbody id="articleDataTable">
								</tbody>
							</table>
						</div>
						<div class="demo">
								<div id="_articles"></div>	
						</div>
					</div>
					
					<div id="two"  class="page_content_div ">
						<div class="page_content">
							<table cellspacing="0" cellpadding="0" >
								<tbody id="commonDataTable">
								</tbody>
							</table>
			            </div>
			            <div class="demo">
			                <div id="_commons"></div>
			            </div>
		            </div>
		            
		            
		            	
				</div>
				
				
				
				
					
				
			</div>
		</div>

		<div class="clear"></div>
		<jsp:include page="../common/footer.jsp" />
	</center>
	
</body>
</html>
