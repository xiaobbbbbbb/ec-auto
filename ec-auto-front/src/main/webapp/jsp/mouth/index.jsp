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
<script type="text/javascript" src="${ctx}/media/js/ec/ec_mouth.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_search.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>

<script type="text/javascript">
	$(function() {
		checkCookie();
		loadData();
		$("#startTime,#endTime").datepickerStyle();
		var myserialId=$("#my_serial_id").val();
		var otherserialId=$("#other_serial_id").val();
		pie(myserialId,'my_pie');
		chart(myserialId,1,'my_chart');//默认好评
		pie(otherserialId,'other_pie');
		chart(otherserialId,1,'other_chart');
		setCookies();
		$(".q_search_a").click(function(){
			$("#cpage").val(1);
			loadData();
		});
		$(".q_content_no_border a").click(function(){
			var id = $(this).attr("id");
			if(id.split("_")[0]=="myaffection"){
				var myserialId=$("#my_serial_id").val();
				chart(myserialId,id.split("_")[1],'my_chart');
			}else{
				var otherserialId=$("#other_serial_id").val();
				chart(otherserialId,id.split("_")[1],'other_chart');
			}
		});
		
	}); 


	function loadData() {
		var myserialId=$("#my_serial_id").val();
		var otherserialId=$("#other_serial_id").val();
		var array = new Array();
		array.push(myserialId);
		array.push(otherserialId);
		var param= getSearchUrl();
		var url = getWebPath() + "/mouth/queryList?serialIds="+ $.toArray(array);
		if (param != null) {
			url += param;
		}
		$.getJSON(
			url,
			function(voData) {
				var html = "";
				var type=$(".type .current").text();
				if (voData.list.length > 0) {	
					$.each(voData.list,
							function(index, value) {
								while(value.content.indexOf(" ")!=-1){
									value.content=value.content.replace(" ","");
									}
								html +="<tr><td title='"+value.content+"' ><a target='_blank' href='"+getWebPath()+"/mouth/detail/"+value.id+"'>"+value.content.substr(0,20)+"..."+"</td>";
								html +="<td >"+type+"</td>";
								if(value.affection==3){
									value.affection="差评";
								}
								if(value.affection==2){
									value.affection="中评";
								}
								if(value.affection==1){
									value.affection="好评";
								}if(!value.affection){
									value.affection="";
								}
								html +="<td >"+value.affection+"</td>";
								html +="<td >"+value.serialName+"</td>";
								if(!value.cityName){
									value.cityName="未知";
								}
								html +="<td >"+value.cityName+"</td>";
								html +="<td >"+value.ptime+"</td>";
								html += "</tr>";
							});
					$("#dataTable").empty().append(html);
					$("#_articles").paginate({
						totalPage : voData.totalPage,
						currentPage : voData.currentPage,
						display		: 22,
						onLoad : function(page) {
							$('#cpage').val(page);
							loadData();
						}
					});
				}
				else{
					html += '<tr><td colspan="6" class="td_border">暂无数据</td></tr>';
					$("#dataTable").empty().append(html);
					$("#_articles").empty();
				}

			});
	}
	
	function showDialog(e,type){	
		if(art.dialog.list[123]){
			art.dialog.list[123].close();
		}
		var title="";
		
		var serialId="";
		var brandId="";
		if(e=='my'){
			serialId=$("#my_serial_id").val();
			brandId= $("#my_brand_id").val();
			title="关注车系";
		}
		if(e=='other'){
			serialId=$("#other_serial_id").val();
			brandId= $("#other_brand_id").val();
			title="对比车系";
		}
		var url="${ctx}/question/select?serialId="+serialId+"&brandId="+brandId;
		if(e=='my'){
			url+="&type=1&otherSerialId="+$("#other_serial_id").val();
		}
		if(e=='other'){
			url+="&type=2&otherSerialId="+$("#my_serial_id").val();
		}
		art.dialog.open(url, {
			id: 123,
		    width: 440,
		    height: 210,
	        title:'选择'+title,
		    ok: function(){
		    	var $form=$(this.iframe.contentWindow.document);
		    	var $serial = $form.find("#serials");
		    	var $brand = $form.find("#brands");
		    	if(!$serial.val()){
		    		art.dialog.alert("请选择车系");
		    		return false;
		    	}
		    	var brand_name = $brand.find("option:selected").text();
		    	var serial_name = $serial.find("option:selected").text();
		    	if(e=='my'){
					if($serial.val()==$("#other_serial_id").val()){
						art.dialog.alert('已存在对比车系,请选择其它车系');	
						return false;
					}
		    	 	$("#my_serial").html(brand_name+"&nbsp;"+serial_name);
			    	$("#my_serial_id").val($serial.val());
			    	$("#my_brand_id").val($brand.val());
			    	pie($serial.val(),'my_pie');
			    	var affection = $("#my_affection").find(" .current_a").attr('id').split("_")[1];
			    	chart($serial.val(),affection,'my_chart');
			    	$(".s_car span:eq(0)").html($("#my_serial").text());
			    	setCookies();
		    	}
		    	if(e=='other'){
		    		if($serial.val()==$("#my_serial_id").val()){
		    			art.dialog.alert('已存在关注车系,请选择其它车系');	
						return false;
					}
		    		$("#other_serial").html(brand_name+"&nbsp;"+serial_name);
			    	$("#other_serial_id").val($serial.val());
			    	$("#other_brand_id").val($brand.val());
			    	pie($serial.val(),'other_pie');
			    	var affection = $("#other_affection").find(" .current_a").attr('id').split("_")[1];
			    	chart($serial.val(),affection,'other_chart');
			    	$(".s_car span:eq(1)").html($("#other_serial").text());
			    	setCookies();
		    	}
		    },
		    cancel: true
	    });
	}
	
	
	//验证cookie中是否有厂牌和车系的信息
	function checkCookie(){
		var mySerial=unescape(getCookie("mySerial"));
		var mybrandId=getCookie("mybrandId");
		var myserialId=getCookie("myserialId");
		var otherserialId =getCookie("otherserialId");
		var otherbrandId =unescape(getCookie("otherbrandId"));
		var otherSerial =unescape(getCookie("otherSerial"));
		if(mySerial&&myserialId){
			$("#my_serial").html(mySerial);
	    	$("#my_serial_id").val(myserialId);
	    	$("#my_brand_id").val(mybrandId);
	    	$(".s_car span:eq(0)").html($("#my_serial").text());
		}
		if(otherserialId&&otherSerial){
			$("#other_serial").html(otherSerial);
	    	$("#other_serial_id").val(otherserialId);
	    	$("#other_brand_id").val(otherbrandId);
	    	$(".s_car span:eq(1)").html($("#other_serial").text());
		}
	}
	
	function setCookies(){
		var myserialId = $("#my_serial_id").val();
		var mybrandId = $("#my_brand_id").val();
		var otherserialId = $("#other_serial_id").val();
		var mySerial = $("#my_serial").html();
		var otherbrandId = $("#other_brand_id").val(); 
		var otherSerial = $("#other_serial").html(); 
		addCookie("myserialId",myserialId,0);
		addCookie("otherserialId",otherserialId,0);
		addCookie("mySerial",mySerial,0);
		addCookie("otherSerial",otherSerial,0);
		addCookie("mybrandId",mybrandId,0);
		addCookie("otherbrandId",otherbrandId,0);
	}
	
	function export_char(){
		var url="${ctx}/mouth/export";
		var myserialId = $("#my_serial_id").val();
		var otherserialId = $("#other_serial_id").val();
		var mySerial = $("#my_serial").html();
		var otherSerial = $("#other_serial").html();
		var myAffection = $("#my_affection").find(" .current_a").attr('id').split("_")[1];
		var otherAffection = $("#other_affection").find(" .current_a").attr('id').split("_")[1];
		url +="?myserialId="+myserialId+"&otherserialId="+otherserialId;
		url +="&myAffection="+myAffection+"&otherAffection="+otherAffection;
		url +="&mySerial="+mySerial+"&otherSerial="+otherSerial;
		window.location.href=url;
	}
</script>
</head>
<body>
	<center>
		<jsp:include page="../common/head.jsp" />
		<div class="all">
			<div class="q_title">
				<div class="q_title_node" >关注车系</div>
				<div class="q_title_car_type" onclick="showDialog('my')" >
					<span  id="my_serial">${myBrand.name} ${mySerial.name}</span>
					<input type="hidden" id= "my_serial_id" value="${mySerial.id }"/>
					<input type="hidden" id= "my_brand_id" value="${myBrand.id }"/>
					<input type="hidden" id= "my_goodCounts" value=""/>
					<input type="hidden" id= "my_middleCounts" value=""/>
					<input type="hidden" id= "my_badCounts" value=""/>
					<input type="hidden" id= "my_allCounts" value=""/>
					<img src="${ctx}/media/images/q_select.png"  />
					<div class="clear" ></div>
				</div>
				<shiro:hasPermission name="/mouth/export">
					<div class="q_title_export" onclick="export_char();">
						<img src="${ctx}/media/images/q_title_export.png"  />
						<span>导出</span>
					</div>
				</shiro:hasPermission>
		        <div class="clear" ></div>
				<div class="q_line" ></div>
			</div>
			<div class="q_content_img" style="width:260px;">
				<div id="my_pie" style="height:250px">
				</div>
			</div>
			<div class="q_content_img" style="float:left;">
				<div id="my_chart" style="height:300px">
				</div>
			</div>
			<div class="q_content_no_border">
				<div class="q_content_absolute" id="my_affection">
					 <a class="absolute_a current_a" id="myaffection_1">好评</a>
					 <a class="absolute_a m" id="myaffection_2">中评</a>
					 <a class="absolute_a" id="myaffection_3">差评</a>
				</div>
				<div class="q_content_long_img">
					 
				</div>
				<div class="clear" ></div>
			</div>
			
			
			<div class="q_title">
				<div class="q_title_node" >对比车系</div>
				<div class="q_title_car_type" onclick="showDialog('other')">
					<span  id="other_serial">${otherBrand.name} ${otherSerial.name}</span>
					<input type="hidden" id= "other_serial_id" value="${otherSerial.id }"/>
					<input type="hidden" id= "other_brand_id" value="${otherBrand.id }"/>
					<input type="hidden" id= "other_goodCounts" value=""/>
					<input type="hidden" id= "other_middleCounts" value=""/>
					<input type="hidden" id= "other_badCounts" value=""/>
					<input type="hidden" id= "other_allCounts" value=""/>
					<img src="${ctx}/media/images/q_select.png"  />
					<div class="clear" ></div>
				</div>
		        <div class="clear" ></div>
				<div class="q_line" ></div>
			</div>
			<div class="q_content_img" style="width:260px;">
				<div id="other_pie" style="height:250px">
				</div>
			</div>
			<div class="q_content_img" style="float:left;">
				<div id="other_chart" style="height:300px">
				</div>
			</div>
			<div class="q_content_no_border">
				<div class="q_content_absolute" id="other_affection">
					 <a class="absolute_a current_a" id="otaffection_1">好评</a>
					 <a class="absolute_a m" id="otaffection_2">中评</a>
					 <a class="absolute_a" id="otaffection_3">差评</a>
				</div>
				<div class="q_content_long_img">
					 
				</div>
				<div class="clear" ></div>
			</div>
			
			
			<div class="q_line" ></div>
			
			 
			
			<div class="q_content">
				<div class="q_content_title">
					<span>用户评论筛选 &gt;</span>
					<div class="q_content_more">
						<img src="${ctx}/media/images/q_more_no.png"  />
						<div>收起</div>
					</div>
				</div>
				<div class="q_content_border bottom_border">
					<div class="q_content_search">
					 	 <div class="search_node">分类：</div>
					 	 <div class="search_content type">
					 	 	<a id="affection" class="current"> 综合</a>
					 	 	<a id="appearance" >外观</a>
					 	 	<a id="interior">内饰</a>
					 	 	<a id="space">空间</a>
					 	 	<a id="operate">操控</a>
					 	 	<a id="power">动力</a>
					 	 	<a id="oil">油耗</a>
					 	 	<a id="comfort">舒适性</a>
					 	 	<a id="configure">配置</a>
					 	 	<a id="price">价格</a>
					 	 	<a id="quality">质量</a>
					 	 	<a id="cost">性价比</a>
					 	 	<a id="maintenance">维修保养</a>
					 	 	<a id="facility">配套设施</a>
					 	 	<a id="stafflevel">人员水平</a>
					 	 	<a id="attitude">服务态度</a>
					 	 	<a id="other">其它</a>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 
					 <div class="q_content_search">
					 	 <div class="search_node">属性：</div>
					 	 <div class="search_content attr">
					 	 	<a id="0"  class="current" href="javascript:void(0);" >全部</a>
					 	 		<a id="1">好评</a>
					 	 		<a id="2">中评</a>
					 	 		<a id="3">差评</a>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
				
					 <div class="q_content_search">
					 	 <div class="search_node">时间：</div>
					 	 <div class="search_content">
					 	 	<input type="text" readonly="readonly" name="startTime" id="startTime" />
					 	 	&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
					 	 	<input type="text" readonly="readonly" name="endTime" id="endTime" />
							<input type="hidden" id="time" value="0" /></td>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	 <div class="search_node">区域：</div>
					 	 <div class="search_content area">
					 	 	<div  id="0"  class="provinces_div current">
					 	 		<div class="provinces_name">全部</div>
					 	 	</div>
					 	 	<c:forEach items="${provinces}" var="dto" varStatus="sn">
					 	 		<div class="provinces_div" id="${dto.id}">
					 	 			<div class="provinces_name">${dto.name}</div>
					 	 			<div class="city_div" >
						 	 			<div style="color: #ffffff" id="0" class="city_current">全省</div>
					 	 			</div>
					 	 		</div>
							</c:forEach>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	<div class="search_node">已选条件：</div>
					 	<div class="search_content">
					 		<div class="s">
					 			车系：<span class="s_node s_car" ><span onclick='showDialog("my")'>${myBrand.name}&nbsp;${mySerial.name}</span></span>
					 		</div>
					 		<div class="s">
					 			分类：<span class="s_node s_type" >综合</span>
					 		</div>
					 		<div class="s">
					 			属性：<span class="s_node s_attr" >全部</span>
					 		</div>
					 		<div class="s">
					 			区域：<span class="s_node s_area" >全部</span>
					 		</div>
					 		<div class="clear"></div>
					 	</div>
					 	<div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	<a class="q_search_a" href="javascript:void(0);" >开始筛选</a>
					 </div>
				</div>
			</div>
			<div class="q_table">
				<table cellspacing="0" cellpadding="0" >
					<thead>
				 		<tr>
							<th>主题</th>
							<th>分类</th>
							<th>属性</th>
							<th>车型</th>
							<th>区域</th>
							<th>时间</th>
						</tr>
				 	</thead>
					<tbody id="dataTable">
					
					</tbody>
				</table>
				<div class="demo">
					<div id="_articles"></div>
				</div>
				<input type="hidden" id="cpage" value=1 />
			</div>
		<jsp:include page="../common/footer.jsp" />
	</div>
	</center>
</body>
</html>