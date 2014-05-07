function loadData(url) {
	$.getJSON(
		url,
		function(voData) {
			$("#dataTable").empty();
				var html = "";
			if (voData.list.length > 0) {	
				$.each(voData.list,
						function(index, value) {
							html +="<tr><td >"+(index+1)+"</td>";
							html +="<td ><a href='"+value.url+"' target='_blank'>"+value.title+"</td>";
							html +="<td style='text-align: center;'>"+starInit(value.hotCount)+"</td>";
							html +="<td >"+value.mediaName+"</td>";
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
						var type=$("#type").val();
						var url=getUrl(type);
						loadData(url);
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
	
function enter() {
	$("#title").val("");
}

function starInit(count){
	var html="";
	var star="<img src= '"+getWebPath()+"/media/images/star.png'  />";
	if(count>=1 && count<=2){
		html=star;
	}else if(count>=3 && count<=4){
		html=star+"&nbsp;"+star;
	}else if(count>=5 && count<=7){
		html=star+"&nbsp;"+star+"&nbsp;"+star;
	}else if(count>=8 && count<=10){
		html=star+"&nbsp;"+star+"&nbsp;"+star+"&nbsp;"+star;
	}else if(count>=11){
		html=star+"&nbsp;"+star+"&nbsp;"+star+"&nbsp;"+star+"&nbsp;"+star;
	}
	return html;
} 
function getWebPath() {
	return $("#basePath").val();
}

