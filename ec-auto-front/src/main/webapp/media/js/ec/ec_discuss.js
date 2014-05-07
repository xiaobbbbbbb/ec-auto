function getWebPath() {
	return $("#basePath").val();
}


function discussList(viewPointId, carSerialId) {
	var viewPointAffection =getAffection();
	var time = $('#time').val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(validateDate(startTime,endTime)){
		return;
	}
	var url=getWebPath()+'/discuss/discuss_list?viewPointId='+viewPointId+'&carSerialId='+carSerialId
		+'&viewPointAffection='+viewPointAffection;
	if (time > 0 && startTime.length > 0 && endTime.length > 0) {
		url += "&startTime=" + startTime + "&endTime=" + endTime;
	}else {
		time = time > 0 ? 0 : time;
		url += "&time=" + time;
	}
	window.location.href=url;
}


/**
*	收集参数
*/
function getURIStr() {	
	var viewPointId = $('#viewPointId').val();  //观点属性
	var carSerialId = $('#carSerialId').val();
	var viewPointAffection = $('#viewPointAffection').val();
	var offset = $('#offset').val();
	if(!offset || offset <= 0) {
		offset = 1;
	}
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var url=getWebPath()+'/discuss/queryDiscussList?viewPointId='+viewPointId+'&carSerialId='+carSerialId
		+'&viewPointAffection='+viewPointAffection+'&startTime='+startDate+'&endTime='+endDate+'&offset='+offset;
	return url;
}

function loadData() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	if(validateDate(startDate,endDate)){
		return;
	}
	var url = getURIStr();
	$.getJSON(url, function(data) {
		var tbody = $('#articleDataInfo');
		tbody.empty();
		var tb = '';
		for (var i = 0; i < data.list.length; i++) {
			var obj = data.list[i];
			var mediaName=obj.mediaName;
			tb += '<tr><td class="td_border"> <a href="'+getWebPath()+'/discuss/detail/'+obj.id+'?type=1">' + obj.title + '</a></td>';
			tb += '<td class="td_border">' + (mediaName?mediaName:'') + '</td>';
			tb += '<td class="td_border">' + (obj.ctime?obj.ctime:'') + '</td></tr>';
		}
		if(data.list.length==0){
			tb = '<tr><td colspan="3" class="td_border">暂无数据</td></tr>';
			tbody.append(tb);
			$("#_articles").empty();
			return;
		}
		tbody.append(tb);
		$("#_articles").paginate({
			totalPage 	: data.totalPage,
			currentPage : data.currentPage,
			display:22,
			onLoad    : function(page){
							$('#offset').val(page);
							loadData();
						  }
		});
	});
}

function validateDate(startTime,endTime){
	var tmp = startTime.split("-");
	var date1 = new Date(tmp[0],tmp[1]-1,tmp[2]);
	tmp = endTime.split("-");

	var date2 = new Date(tmp[0],tmp[1]-2,tmp[2]);
	if(date2.getTime() - date1.getTime() > 30 * 24 * 60 * 60 * 1000){
	        art.dialog.alert("时间间隔不能超过30天");
	        return true;
	}
	return false;
}


