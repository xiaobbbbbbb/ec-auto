<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<div id="pager_params" url="/article/list"></div>
	<table id="table" class="easyui-datagrid" style=""
			title="车友评价列表" iconCls="icon-save"
			data-options="pageSize:20,pagination:true,singleSelect:false, method:'get',toolbar:'#table_toolbar'">
		<thead>
			<tr>
				<th data-options="field:'id',checkbox:true"></th>
				<th field="sqId"></th>
				<th field="title" width="200">标题</th>
				<th field="showAffection" width="100">情感说明</th>
				<th field="showGrade" width="100">情感等级</th>
				<th field="showBrandId" width="100">品牌</th>
				<th field="showSerialId" width="100">产品</th>
				<th field="showUrl" width="100">地址</th>		
				<th field="showViewpoints" width="100">观点</th>		
				<th field="showDesTarget" width="100">描述对象</th>
				<th field="showDesContent" width="100">描述内容</th>
				<th field="showMediaType" width="100">传媒分类</th>
				<th field="showMediaId" width="100">站点</th>
				<th field="showAreaId" width="100">地区</th>
				<th field="showProvinceId" width="100">省份</th>
				<th field="showCityId" width="100">城市</th>
				<th field="showArticleCtime" width="100">发布时间</th>
				
				<th field="hasComment" width="100">是否有评论</th>
				<th field="hasViewpoint" width="100">是否有观点</th>
				<th field="showStatus" width="100">状态</th>
				<th field="showCtime" width="100">导入时间</th>
				
				<th field="affection" hidden="true"></th>
				<th field="grade" hidden="true"></th>
				<th field="desTarget" hidden="true"></th>
				<th field="desContent" hidden="true"></th>
				<th field="mediaType" hidden="true"></th>
				<th field="mediaId" hidden="true"></th>
				<th field="brandId" hidden="true"></th>
				<th field="serialId" hidden="true"></th>
				<th field="status" hidden="true"></th>
				<th field="url" hidden="true"></th>
				<th field="cityId" hidden="true"></th>
				<th field="provinceId" hidden="true"></th> <!-- sb命名 -->
				<th field="areaId" hidden="true"></th>
			</tr>
		</thead>		
	</table>
	
 	<div id="table_toolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" title="编辑" plain="true" onclick="edit();return false;"></a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" title="作废" plain="true" onclick="discardRecord(true);return false;"></a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" title="启用" plain="true" onclick="discardRecord(false);return false;"></a>
		</div>
		<div>
			<form id="search_form">
			标题:
	    	<input class="search-field" style="width:100px;" type="text" name="title"></input>
			情感说明: 
			<select class="easyui-combobox" name="affection" style="width:80px;">
	    					<option value="-1">全部</option>
	    					<option value="3">正面</option>
	    					<option value="2">中性</option>
	    					<option value="1">负面</option>
	    				</select>
	    	情感等级:
	    	<select class="easyui-combobox" name="grade" style="width:80px;">
	    					<option value="-1">全部</option>
	    					<option value="1">1级</option>
	    					<option value="2">2级</option>
	    					<option value="3">3级</option>
	    				</select>
	    	品牌:
	    	<select name="brandId" id="search_brandId" style="width:100px;" onchange="onSearchChangeBrand();">
	    					<option value="-1">全部</option>
	    					<c:forEach var="brand" items="${brands }">
	    						<option value="${brand.id }">${brand.name }</option>
	    					</c:forEach>
	    				</select>
	    	产品:
	    	<select id="search_serials" name="serialId" style="width:100px;">

	    				</select>
	    	状态:
	    	<select class="easyui-combobox" name="status" style="width:80px;">
	    					<option value="-1">全部</option>
	    					<option value="0" selected="selected">初始</option>
	    					<option value="1">有效</option>
	    					<option value="2">无效</option>
	    				</select>
	    	站点:
	    	<select class="easyui-combobox" name="mediaId" style="width:80px;">
	    					<option value="-1">全部</option>
	    					<c:forEach var="media" items="${medias }">
	    						<option value="${media.id }">${media.name }</option>
	    					</c:forEach>
	    				</select>
	    	<br/>
			传媒分类:
			<select class="easyui-combobox" name="mediaType" style="width:80px;">
	    					<option value="-1">全部</option>		
	    					<c:forEach var="mediaTypes" items="${mediaTypes }">
	    						<option value="${mediaTypes.id }">${mediaTypes.name }</option>
	    					</c:forEach>			
	    				</select>
	    	地区:
			<select class="search-field" id="search_areas" name="areaId" style="width:80px;"  onchange="onSearchChangeArea();">
	    					<option value="-1">全部</option>
	    					<c:forEach var="area" items="${areas }">
	    						<option value="${area.id }">${area.name }</option>
	    					</c:forEach>
	    				</select>
	    	省份:
			<select class="search-field" id="search_provinces" name="provinceId" style="width:80px;" onchange="onSearchChangeProvince();">
	    					<option value="-1">全部</option>
	    					<c:forEach var="province" items="${provinces }">
	    						<option value="${province.id }">${province.name }</option>
	    					</c:forEach>
	    				</select>
	    	城市:
			<select class="search-field" id="search_citys" name="cityId" style="width:80px;">
	    					<option value="-1">全部</option>
	    					<c:forEach var="city" items="${citys }">
	    						<option value="${city.id }">${city.name }</option>
	    					</c:forEach>
	    				</select>
	    	描述对象:
	    	<select class="easyui-combobox" name="desTarget" style="width:80px;">
	    					<option value="-1">全部</option>
	    					<option value="1">主机厂</option>
	    					<option value="2">4s店</option>
	    					<option value="3">其它</option>
	    				</select>	    	
	    	发布时间段:
	    	<input id="search_pub_time_begin" style="width:90px;" class="easyui-datebox" name="search_pub_time_begin" value="${todayDate }"> — 
	    	<input id="search_pub_time_end" style="width:90px;"  class="easyui-datebox" name="search_pub_time_end" value="${todayDate }">  
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"  onclick="searchRecord();return false;">Search</a>
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
		function onSearchChangeBrand() {
			var brandId = FormUtils.formParams2Json($("#search_form")).brandId;
			$.ajax({type: "post", 
                url: "/article/serials?brandId=" + brandId, 
                dataType: "json", 
                success: function (json) { 
                	var html = "";
                	html += '<option value="-1">全部</option>';
                	$(json).each(function(){ 
                		html += '<option value="' + this.id + '">' + this.name + '</option>';
                	}); 
                    $("#search_serials").html(html);
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                	$.messager.alert("操作提示", textStatus, "error");
                } 
        	});
		}
		
		function onSearchChangeArea() {
			var areaId = FormUtils.formParams2Json($("#search_form")).areaId;
			$.ajax({type: "post", 
                url: "/article/provinces?areaId=" + areaId, 
                dataType: "json", 
                success: function (json) { 
                	var html = "";
                	html += '<option value="-1">全部</option>';
                	$(json).each(function(){ 
                		html += '<option value="' + this.id + '">' + this.name + '</option>';
                	}); 
                    $("#search_provinces").html(html);
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                	$.messager.alert("操作提示", textStatus, "error");
                } 
        	});
		}
		
		function onSearchChangeProvince() {
			var provinceId = FormUtils.formParams2Json($("#search_form")).provinceId;
			$.ajax({type: "post", 
                url: "/article/citys?provinceId=" + provinceId, 
                dataType: "json", 
                success: function (json) { 
                	var html = "";
                	html += '<option value="-1">全部</option>';
                	$(json).each(function(){ 
                		html += '<option value="' + this.id + '">' + this.name + '</option>';
                	}); 
                    $("#search_citys").html(html);
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                	$.messager.alert("操作提示", textStatus, "error");
                } 
        	});
		}
		function edit(){
            var rows = $("#table").datagrid("getSelections");    // 获取所有选中的行
			if(rows.length>1){
				$.messager.alert("操作提示", "请选择一条记录", "info");
			}else{
				var row = $('#table').datagrid('getSelected');
				if (row){
					renderArticleViewPoints(row.id);
	            	renderSerials(row.brandId, row.serialId);
	            	renderProvincesAndCitys(row.areaId, row.provinceId, row.cityId);
	            	$('#edit_dlg').dialog('open');
	                $('#edit_form').form('load',row); 
				} else {
					$.messager.alert("操作提示", "请选择一条记录", "info");
				}
			}
            
		}
		
		function searchRecord() {
			 loadPage($('#table').datagrid('getPager'), 1);
		}
		
		function discardRecord(discard) {
			var rows = $("#table").datagrid("getSelections");    // 获取所有选中的行
			if(rows.length<=0){
	        	$.messager.alert("操作提示", "请选择至少一条记录", "info");
	        }else{
				var ids = [];
				var idsStr;
				for (var i = 0; i < rows.length; i++) {
				    var row = rows[i];
					ids.push(row.id);
				}
				$.messager.confirm("操作提示", "您确定要执行该操作吗？", function (data) {
		            if (data) {
		            	batchDiscardArticle(ids, discard);
		            }
		        });
	        }
		}
		
		function renderSerials(brandId, serialId) {
			$.ajax({type: "post", 
                url: "/article/serials?brandId=" + brandId, 
                dataType: "json", 
                success: function (json) { 
                	var html = "";
                	html += '<option value="-1">全部</option>';
                	$(json).each(function(){ 
                		html += '<option value="' + this.id + '"' + (this.id==serialId?' selected="selected"':'') +'>' + this.name + '</option>';
                	}); 
                    $("#edit_serials").html(html);
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                	$.messager.alert("操作提示", textStatus, "error");
                } 
        	});
		}
		
		function renderProvincesAndCitys(areaId, provinceId, cityId) {
			$.ajax({type: "post", 
                url: "/article/provinces?areaId=" + areaId, 
                dataType: "json", 
                success: function (json) { 
                	var html = "";
                	html += '<option value="-1">全部</option>';
                	$(json).each(function(){ 
                		html += '<option value="' + this.id + '"' + (this.id==provinceId?' selected="selected"':'') +'>' + this.name + '</option>';
                	}); 
                    $("#edit_provinces").html(html);
                      
                    renderCitys(provinceId, cityId);
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                	$.messager.alert("操作提示", textStatus, "error");
                } 
        	});
		}
		
		function renderCitys(provinceId, cityId) {
			$.ajax({type: "post", 
                url: "/article/citys?provinceId=" + provinceId, 
                dataType: "json", 
                success: function (json) { 
                	var html = "";
                	html += '<option value="-1">全部</option>';
                	$(json).each(function(){ 
                		html += '<option value="' + this.id + '"' + (this.id==cityId?' selected="selected"':'') +'>' + this.name + '</option>';
                	}); 
                    $("#edit_citys").html(html);
                    
                    
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                	$.messager.alert("操作提示", textStatus, "error");
                } 
        	});
		}
		
		function renderArticleViewPoints(articleId) {
			$.ajax({type: "post", 
                url: "/article/viewpoints?articleId=" + articleId, 
                dataType: "json", 
                success: function (json) { 
                	var html = "";
                	
                	html += "<div style='max-height:80px;overflow: auto;background-color:#7FFFD4;'>";
                	$(json).each(function(){ 
                		if (this.affection==3)
                		html += '<input type="checkbox" value="' + this.id + '"' + (this.select?' checked="checked"':'') + '>' + this.name + '</input>';
                	}); 
                	html += "</div>";
                	
                	html += "<div style='max-height:80px;overflow: auto;background-color:yellow;'>";
                	$(json).each(function(){ 
                		if (this.affection==2)
                		html += '<input type="checkbox" value="' + this.id + '"' + (this.select?' checked="checked"':'') + '>' + this.name + '</input>';
                	}); 
                	html += "</div>";
                	
                	html += "<div style='max-height:80px;overflow: auto;background-color:#FF95CA;'>";
                	$(json).each(function(){ 
                		if (this.affection==1)
                		html += '<input type="checkbox" value="' + this.id + '"' + (this.select?' checked="checked"':'') + '>' + this.name + '</input>';
                	}); 
                	html += "</div>";
                    $("#article_viewpoint_container").html(html);
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                	$.messager.alert("操作提示", textStatus, "error");
                } 
        	});
		}
		
		function discardArticle(id, discard) {
			AjaxUtils.ajaxPost("/article/discard", {"articleId":id, "discard":discard}, function(json) {
   				if (json.succ=='Y') {
   					loadPage($('#table').datagrid('getPager'));	
   				} else {
   					$.messager.alert("操作提示", json.desc, "error");
   				}
   		 	});
		}
		function batchDiscardArticle(ids, discard) {
			AjaxUtils.ajaxPost("/article/batch/discard", {"articleIds":ids, "discard":discard}, function(json) {
   				if (json.succ=='Y') {
   					loadPage($('#table').datagrid('getPager'));	
   				} else {
   					$.messager.alert("操作提示", json.desc, "error");
   				}
   		 	});
		}
		$(function() {
			$("#table").datagrid('getPager').pagination({
                layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh']
            });
		});
		
		
	</script>
	<jsp:include page="edit_article.jsp"></jsp:include>
	<jsp:include page="add_viewpoint.jsp"></jsp:include>
	<jsp:include page="../../common/pager.jsp"></jsp:include>