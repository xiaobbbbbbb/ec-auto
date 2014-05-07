<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="edit_dlg" class="easyui-dialog" title="编辑" style="padding:10px;width:900px;"
			data-options="modal:true, closed:true, iconCls: 'icon-save',buttons:edit_buttons">
		
	    <form id="edit_form" method="post" style="display:block;">
	    	<table>
	    		<tr>
	    			<td>标题:</td>
	    			<td><input class="easyui-validatebox" style="width:350px;" type="text" name="title" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>内容摘要:</td>
	    			<td><textarea class="easyui-validatebox" style="width:550px;height: 60px;" name="simpleContent" data-options="required:true"></textarea></td>
	    		</tr>
	    		<tr>
	    			<td>
	    				文章观点:
	    				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" title="新增观点" plain="true"  onclick="add();return false;"></a>
	    			</td>
	    			<td>
	    				<div id="article_viewpoint_container" style="width:800px;border: 1 solid green;">
	    				</div>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>情感说明:</td>
	    			<td>
	    				<select class="easyui-combobox" name="affection" style="width:150px;">
	    					<option value="3">正面</option>
	    					<option value="2">中性</option>
	    					<option value="1">负面</option>
	    				</select>
	    			</td>
	    		</tr>	    		
	    		<tr>
	    			<td>情感等级:</td>
	    			<td>
	    				<select class="easyui-combobox" name="grade" style="width:150px;">
	    					<option value="1">1级</option>
	    					<option value="2">2级</option>
	    					<option value="3">3级</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>品牌:</td>
	    			<td>
	    				<select name="brandId" style="width:100px;" id="edit_brandId" onchange="onEditChangeBrand();">
	    					<option value="-1">全部</option>
	    					<c:forEach var="brand" items="${brands }">
	    						<option value="${brand.id }">${brand.name }</option>
	    					</c:forEach>
	    				</select>
	    				&nbsp;&nbsp;
	    				产品:
	    				<select id="edit_serials" name="serialId" style="width:100px;">
	    					
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>地址:</td>
	    			<td><input class="easyui-validatebox"  style="width:350px;" type="text" name="url"  data-options="required:true"></input><a href="javascript:void(0);" onclick="var url=$(this).parent().find('input').val(); window.open(url, '_blank');">查看</a></td>
	    		</tr>
	    		<tr>
	    			<td>描述对象:</td>
	    			<td>
	    				<select class="easyui-combobox" name="desTarget" style="width:150px;">
	    					<option value="1">主机厂</option>
	    					<option value="2">4s店</option>
	    					<option value="3">其它</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>描述内容:</td>
	    			<td>
	    				<select class="easyui-combobox" name="desContent" style="width:150px;">
	    					<option value="1">质量</option>
	    					<option value="2">价格</option>
	    					<option value="3">服务</option>
	    					<option value="4">其它</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>传媒分类:</td>
	    			<td>
	    				<select class="easyui-combobox" name="mediaType" style="width:150px;">
	    					<c:forEach var="mediaTypes" items="${mediaTypes }">
	    						<option value="${mediaTypes.id }">${mediaTypes.name }</option>
	    					</c:forEach>	
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>地区:</td>
	    			<td>
	    				<select class="search-field" id="edit_areas" name="areaId" style="width:80px;"  onchange="onEditChangeArea();">
	    					<option value="-1">全部</option>
	    					<c:forEach var="area" items="${areas }">
	    						<option value="${area.id }">${area.name }</option>
	    					</c:forEach>
	    				</select>
	    				&nbsp;&nbsp;
	    				省份:
	    				<select class="search-field" id="edit_provinces" name="provinceId" style="width:80px;" onchange="onEditChangeProvince();">
	    					<option value="-1">全部</option>
	    					<c:forEach var="province" items="${provinces }">
	    						<option value="${province.id }">${province.name }</option>
	    					</c:forEach>
	    				</select>
	    				&nbsp;&nbsp;
	    				城市:
	    				<select class="search-field" id="edit_citys" name="cityId" style="width:80px;">
	    					<option value="-1">全部</option>
	    					<c:forEach var="city" items="${citys }">
	    						<option value="${city.id }">${city.name }</option>
	    					</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>站点:</td>
	    			<td>
	    				<select class="easyui-combobox" name="mediaId" style="width:150px;">
	    					<c:forEach var="media" items="${medias }">
	    						<option value="${media.id }">${media.name }</option>
	    					</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    	</table>
	    	<input type="hidden" name="id"/>
	    	<input type="hidden" id="articleViewpointIds" name="articleViewpointIds" />
	    </form>
	</div>
</div>
<script type="text/javascript">
		function onEditChangeBrand() {
			var brandId = FormUtils.formParams2Json($("#edit_form")).brandId;
			$.ajax({type: "post", 
		        url: "/article/serials?brandId=" + brandId, 
		        dataType: "json", 
		        success: function (json) { 
		        	var html = "";
		        	html += '<option value="-1">全部</option>';
		        	$(json).each(function(){ 
		        		html += '<option value="' + this.id + '">' + this.name + '</option>';
		        	}); 
		            $("#edit_serials").html(html);
		        }, 
		        error: function (XMLHttpRequest, textStatus, errorThrown) { 
		        	$.messager.alert("操作提示", textStatus, "error");
		        } 
			});
		}
		
		function onEditChangeArea() {
			var areaId = FormUtils.formParams2Json($("#edit_form")).areaId;
			$.ajax({type: "post", 
                url: "/article/provinces?areaId=" + areaId, 
                dataType: "json", 
                success: function (json) { 
                	var html = "";
                	html += '<option value="-1">全部</option>';
                	$(json).each(function(){ 
                		html += '<option value="' + this.id + '">' + this.name + '</option>';
                	}); 
                    $("#edit_provinces").html(html);
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                	$.messager.alert("操作提示", textStatus, "error");
                } 
        	});
		}
		
		function onEditChangeProvince() {
			var provinceId = FormUtils.formParams2Json($("#edit_form")).provinceId; //sb命名
			$.ajax({type: "post", 
                url: "/article/citys?provinceId=" + provinceId, 
                dataType: "json", 
                success: function (json) { 
                	var html = "";
                	html += '<option value="-1">全部</option>';
                	$(json).each(function(){ 
                		html += '<option value="' + this.id + '">' + this.name + '</option>';
                	}); 
                    $("#edit_citys").html(html);
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                	$.messager.alert("操作提示", textStatus, "error");
                } 
        	});
		}
		var edit_buttons = [{
			text:'Ok',
			iconCls:'icon-ok',
			handler:function(){
				doEdit();
			}
		},{
			text:'Cancel',
			handler:function(){
				$("#edit_dlg").dialog('close');
			}
		}];
		
		function doEdit () {			
			 var $form = $("#edit_form");
			 if ($form.attr("submiting") == "true") {
				return; 
			 }
			 
			 $form.attr("submiting", "true");		
			 var viewpointIds = "";
			 $("#article_viewpoint_container input").each(function(){		 
				 if (this.checked) {
					 if (viewpointIds!="") {
						 viewpointIds += ",";
					 }
					 viewpointIds += $(this).val();
				 }
			 });
			 $("#articleViewpointIds").val(viewpointIds);
			 AjaxUtils.ajaxFormSubmit($form, "/article/doedit", function(json) {
					if (json.succ=='Y') {
						$("#edit_dlg").dialog('close');
						loadPage($('#table').datagrid('getPager'));						
					} else {
						alert("提交失败");
					}
					$form.attr("submiting", "false");
			},function () {
				alert("提交失败");
				$form.attr("submiting", "false");
			});
		}

		function add(){
			$('#add_dlg').dialog('open');
		}
	</script>	
	
