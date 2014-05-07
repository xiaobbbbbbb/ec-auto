
	<div data-role="page" id="search_page">
		<div data-role="header"  data-theme="c">
			<h1></h1>
			<a href="#index_page" data-role="button" class="ui-btn-right" data-theme="c" onclick="$('#search_status').val('cancel'); return true;">取消</a>
		</div>

		<div data-role="content">
			<form id="search_form">
			评价程度
			<select id="search_level" name="searchLevel">		
				<option value="-1" <#if searchLevel=-1> selected="selected" </#if>>不限 </option>		
				<option value="3" <#if searchLevel=3> selected="selected" </#if>>好评 </option>		
				<option value="2" <#if searchLevel=2> selected="selected" </#if>>中评 </option>		
				<option value="1" <#if searchLevel=1> selected="selected" </#if>>差评  </option>						
			</select> 
			日期
			<select id="search_date_type" name="searchDateType" onchange="if ($('#search_date_type').val()=='3') { $('#dategroup').show(); } else { $('#dategroup').hide(); }">
					<option value="-1" <#if searchDateType=-1> selected="selected" </#if>>不限 </option>
					<option value="1" <#if searchDateType=1> selected="selected" </#if>>近7天 </option>
					<option value="2" <#if searchDateType=2> selected="selected" </#if>>近30天 </option>
					<option value="3" <#if searchDateType=3> selected="selected" </#if>>自定义 </option>			
			</select> 
			
			<fieldset data-role="controlgroup" id="dategroup" <#if searchDateType=-1> style="display:none;" </#if> >
			<label for="search_bdate">开始时间:</label> 
			<input type="text" id="search_bdate" name="searchBdate"  data-role="datebox" value="${searchBdate}"> 
			<label for="search_edate">结束时间:</label> 
			<input type="text" id="search_edate" name="searchEdate" data-role="datebox" value="${searchEdate}"> 
			</fieldset>
			
			<input id="search_status" type="hidden" value="cancel">		
			<a href="javascript:void(0);"  data-role="button" data-theme="c"  onclick="searchRecord(); return false;">确定</a>
			</form>
			
		</div>
		<script type="text/javascript">
			$(function() {
				var opt = {
				    preset: 'date', //日期
				    //theme: 'jqm', //皮肤样式
				    display: 'modal', //显示方式 
				   // mode: 'clickpick', //日期选择模式
				    dateFormat: 'yy-mm-dd', // 日期格式
				    setText: '确定', //确认按钮名称
				    cancelText: '取消',//取消按钮名籍我
				    dateOrder: 'yymmdd', //面板中日期排列格式
				    dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字
				    endYear:2020 //结束年份
				};			
				$("#search_bdate,#search_edate").mobiscroll(opt);
			});
			function searchRecord() {
				var paramsArray = $("#search_form").serializeArray();
				var params = {};
				for (index in paramsArray) {
					params[paramsArray[index].name] = paramsArray[index].value;
				}
				var url = '/viewpoint/index?page=1&userId=${userId?c}&brandId=' + parseInt($('#brandId').val());
				url += "&searchLevel=" + params.searchLevel;
				url += "&searchDateType=" + params.searchDateType;
				url += "&searchBdate=" + params.searchBdate;
				url += "&searchEdate=" + params.searchEdate;		
				window.location.href = url;
			}
			</script>
		<#include "../common/footer.ftl">
	</div>