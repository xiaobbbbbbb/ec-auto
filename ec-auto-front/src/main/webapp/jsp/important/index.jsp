<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index-new.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/js/paginate/css/style.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${ctx}/media/css/question.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_question.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_search.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_important.js"></script>
<script type="text/javascript" src="${ctx}/media/js/a_hover.js"></script>
<script type="text/javascript">
	$(function() {
		typeSearch(0);
	}); 
	
	function getUrl(type){
		var title = $('#title').val();  //观点属性
		if(title=="请输入关键字进行检索"){
			title="";
		}
		var cpage=$("#cpage").val();
		return  getWebPath() + "/important/list?date=-1&title="+title+"&type="+type+"&pageNo="+cpage;
	}
	
	
    //根据类型查找
	function typeSearch(type){
		$("#cpage").val(1);
		var url=getUrl(type);
		$(".title_imp_a_hover").removeClass("title_imp_a_hover");
		$(".title_imp_a :eq("+type+")").addClass("title_imp_a_hover");
		$("#type").val(type);
		loadData(url);
	} 
	
	
	
	function search(){
		var type=$("#type").val();
		var url=getUrl(type);
		loadData(url);
	}
	 
	
	
</script>
</head>
<body>
	<center> 
		<jsp:include page="../common/head.jsp" />
		<div class="all">
			<div class="q_title no_q_title">
				<div class="q_title_node" >重点新闻</div>
				<div class="q_title_input" >
					<a href="javascript:search();" >搜索</a>
					<input value="请输入关键字进行检索" id="title" name="title" onfocus="enter()"/>
				</div>
		        <div class="clear" ></div>
			</div>
			<div class="q_content no_q_content">
				<div class="q_content_title">
					<input name="type" id="type" type="hidden" value="0" />
					<a class="title_imp_a title_imp_a_hover" href="javascript:typeSearch(0);" >全部</a>
					<a class="title_imp_a" href="javascript:typeSearch(1);"  >与我相关</a>
					<a class="title_imp_a" href="javascript:typeSearch(2);"  >与竞品相关</a>
					<div class="clear" ></div>
				</div>
				<div class="q_table no_top">
					<table cellspacing="0" cellpadding="0" >
						<thead>
					 		<tr>
								<th width="50px">序号</th>
								<th>标题</th>
								<th width="120px">热度</th>
								<th  width="140px">来源</th>
								<th  width="140px">时间</th>
							</tr>
					 	</thead>
						<tbody id="dataTable">
							<tr>
								<td colspan="5" >暂无数据</td>
							</tr>
						</tbody>
					</table>
				<div class="demo">
					<div id="_articles"></div>
				</div>
				<input type="hidden" id="cpage" value="1" />
			</div>
			</div>
			
		<jsp:include page="../common/footer.jsp" />
	</div>
	</center>
</body>
</html>