
	<div data-role="page" id="search_page">
		<div data-role="header" data-theme="c">
			<h1></h1>
			<a href="#index_page" data-role="button" class="ui-btn-right" data-theme="c" onclick="$('#search_status').val('cancel'); return true;">取消</a>
		</div>

		<div data-role="content">
			<form id="search_form">
			<select name="brandId" id="brandId" brandId="${brandId?c}" onchange="$('.serialsFieldset').hide(); $('.serialsFieldset input').removeAttr('checked').checkboxradio('refresh'); $('#brand' + $(this).val()).show();">
					<#list brands as brand>
						<#if brandId=brand.id>
						  <option value="${brand.id?c}" selected="selected">${brand.name} </option>
						<#else>
						  <option value="${brand.id?c}">${brand.name} </option>
						</#if>  					
					</#list>
			</select> 
			
			
			<#list searchOptions as opt>
			
			<fieldset class="serialsFieldset" id="brand${opt.brandId}" data-role="controlgroup" <#if opt.select=false> style="display:none;" </#if>>
				<#list opt.serials as serial>
			    <label for="serials${serial.id}">${serial.name}</label>
			    <input type="checkbox" id="serials${serial.id}" value="${serial.id}" name="serials${serial.brandId}" <#if serial.select=true> checked="checked" </#if> >
				</#list>
			</fieldset>
			</#list>
			<input id="search_status" type="hidden" value="cancel">		
			<a href="javascript:void(0);"  data-role="button" data-theme="c"  onclick="searchRecord(); return false;">确定</a>
			</form>
			
		</div>
		<script type="text/javascript">
			
			function searchRecord() {				
				var brandId = $("#brandId").val();
				var selectedSerialIds = "";
				var sCount = 0;
				$("#brand"+brandId + " input").each(function() {
					if (this.checked) {
						if (selectedSerialIds!="") {
							selectedSerialIds += ",";
						}
						selectedSerialIds += $(this).val();
						sCount++;
					}
				});
				if (selectedSerialIds=='') {
					message("您未选择任何产品");
					return;
				}
				if (sCount>3) {
					message("最多只能选择3个产品");
					return;
				}
				var url = '/article/seven_day_attention_trend?userId=${userId?c}&brandId=' + parseInt($('#brandId').val());
				url += "&serialsStr=" + selectedSerialIds;		
				window.location.href = url;
			}
			</script>
		<#include "../common/footer.ftl">
		<#include "../common/message.ftl">
	</div>