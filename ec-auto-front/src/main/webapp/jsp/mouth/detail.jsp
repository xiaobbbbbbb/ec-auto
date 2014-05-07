<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户口碑详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index.css" type="text/css" rel="stylesheet" />

<link rel="stylesheet" type="text/css" href="${ctx}/media/js/paginate/css/style.css" media="screen"/>
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>



</head>
<body>
	<center>
		<jsp:include page="../common/head.jsp" />
		<div class="all">
			<div class="detail_center">
				<span class="detail_center_tag"><a href="${ctx }/mouth/index/">用户口碑</a> 
				&gt; <span class="a_title"></span></span>
				
				<div class="detail_center_content" >
					<div class="content_div_title">
						<div class="a_title"></div>
						<div class="a_other">
						来源<span class="a_mediaName" ></span>
						<span class="a_ptime" ></span>
						<a class="a_original_url" href="javascript:void(0);" target="_blank">查看原网页</a>
						</div>
					</div>
					<div class="a_content"></div>
					<a class="a_original_url_more" href="javascript:void(0);" target="_blank" >[查看更多]</a>
				</div>
				
				<div class="detail_center_else" >
					<a id="content_sum"  class="else_title else_title_current" >
						相关信息(0)
					</a>
					<a id="comment_sum"  class="else_title" style="margin-left: 5px" >
						评论列表(0)
					</a>
					<div class="clear"></div>
					<div id="one" class="page_content_div">
						<div class="page_content">
							<table cellspacing="0" cellpadding="0" >
								<tbody id="articleDataTable">
								</tbody>
							</table>
							<input type="hidden" id="atlPage"/>
						</div>
						<div class="demo">
								<div id="_articles"></div>	
						</div>
					</div>
					
					<div id="two"  class="page_content_div">
						<div class="page_content">
							<table cellspacing="0" cellpadding="0" >
								<tbody id="commonDataTable">
								</tbody>
							</table>
							<input type="hidden" id="cmsPage"/>
			            </div>
			            <div class="demo">
			                <div id="_commons"></div>
			            </div>
		            </div>
		            
				</div>
				
			</div>
			<div class="clear"></div>
		</div>

		
		<jsp:include page="../common/footer.jsp" />
	</center>
	
</body>
</html>
<script type="text/javascript">
	var a_id = '${aid}';
	
	function loadArticleById() {
		if(!a_id) {
			return false;
		}
		var url = '${ctx}/mouth/article?aid='+a_id+'&cpage='+getArticlePage();
		$.getJSON(url, function(data) {
			var sp ="&nbsp;&nbsp;";
			$('.a_title').text(data.title);
			$('.a_mediaName').html(sp+(data.mediaName?data.mediaName:"暂无")+sp+sp+sp);
			$('.a_ptime').html(data.showPtime+sp+sp+sp);
			$('.a_content').html(sp+sp+sp+data.content);
			$(".a_original_url,.a_original_url_more").attr("href", data.url);
			loadComments(data,true);
			loadArticles(data.id);
			 
		});
	}
	
	function loadComments(article,first) {
		var url = '${ctx}/discuss/comments?aid='+article.id+'&cpage='+getCommonsPage();
		$.getJSON(url, function(data) {
			var tbody = $('#commonDataTable');
			tbody.empty();
			var tb = '';
			if(data.list){
				for (var i = 0; i < data.list.length; i++) {
					var obj = data.list[i];
					tb += '<tr><td class="td_border td_first">' + obj.content + '</td>';
					tb += '<td class="td_border">' + article.mediaName + '</td>';
					tb += '<td class="td_border">' + obj.showCtime + '</td></tr>';
				}
				tbody.append(tb);
				$("#comment_sum").html("评论列表("+data.totalRows+")");
				if(data.list.length > 0) {
					$("#_commons").paginate({
						totalPage 		: data.totalPage,
						currentPage 	: data.currentPage,
						display  		: 30,
						onLoad     		: function(page){
									$('#cmsPage').val(page);
									loadComments(article,false);
									
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
	
	function getArticlePage() {
		var obj = $('#atlPage');
		if(obj && obj.val()) {
			return obj.val();
		} else {
			return 1;
		}
	}
	
	function getCommonsPage() {
		var obj = $('#cmsPage');
		if(obj && obj.val()) {
			return obj.val();
		} else {
			return 1;
		}
	}
	
	function loadArticles(aid) {
		if(!aid) {
			return false;
		}
		var url = '${ctx}/discuss/articles?aid='+aid+'&cpage='+getArticlePage();
		$.getJSON(url, function(data) { 
			var tbody = $('#articleDataTable');
			tbody.empty();
			var tb = '';
			if(data){
				for (var i = 0; i < data.list.length; i++) {
					var obj = data.list[i];
					tb += '<tr><td class="td_border td_first"><a href="${ctx}/discuss/detail/'+obj.id+'?type=${type}">' + obj.title + '</a></td>';
					tb += '<td class="td_border">' + obj.mediaName + '</td>';
					tb += '<td class="td_border">' + obj.ctime + '</td></tr>';
				}
				tbody.append(tb);
				$("#content_sum").html("相关信息("+data.totalRows+")");
				if(data.list.length > 0) {
					$("#_articles").paginate({
						totalPage	: data.totalPage,
						currentPage : data.currentPage,
						display  	: 30,
						onLoad     	: function(page){
								$('#atlPage').val(page);
								loadArticles(aid);
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
		
		var type="${type}"; //用来标识是首页的负面情报跳转过来的还是  产品评价跳转过来的
		if(type==1){
			$(".top_centent_bottom_index a:eq(1)").css("backgroundImage","url('${ctx}/media/images/top_centent_bottom_index_a_bg.gif')");
			$(".top_centent_bottom_index a:eq(2)").css("backgroundImage","none");
		}
		
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
