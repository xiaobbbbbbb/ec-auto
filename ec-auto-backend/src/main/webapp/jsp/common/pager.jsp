<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">	
		function loadPage(pager, pageNumber, pageSize) {
			if (!pageNumber) {
				pageNumber = pager.data("pagination").options.pageNumber;
			}
			if (!pageSize) {
				pageSize = pager.data("pagination").options.pageSize;
			}			
			var param = {};
        	param = $.extend(FormUtils.formParams2Json($("#search_form")),{page:pageNumber,rows:pageSize});
        	$(pager).pagination('loading');
			$.ajax({type: "post", 
                url: $("#pager_params").attr("url"), 
                dataType: "json",
                data: param,
                success: function (json) { 
                	 if (!json.rows) {
                		 json.rows = [];
                	 }
                	 $('#table').datagrid('loadData', json);
                	 $(pager).pagination('refresh',{
                			total: json.total,
                			pageNumber: pageNumber
                		});
                	 $(pager).pagination('loaded');
                	 
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                	$.messager.alert("提示", textStatus, "error");
                	$(pager).pagination('loaded');
                } 
        	}); 
		}
		
		$(function(){
			var pager = $('#table').datagrid('getPager');
			$(pager).pagination({
		        onSelectPage:function(pageNumber, pageSize){
		        	loadPage(pager, pageNumber, pageSize); 	        	
		        },
		        onBeforeRefresh:function(pageNumber,pageSize){
		            $(this).pagination('loading');
		        },
		        onRefresh:function(){
		            $(this).pagination('loaded');
		        }
		    });
			
			loadPage(pager);
		});
</script>