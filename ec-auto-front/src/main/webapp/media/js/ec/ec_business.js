$(function() {
	search();
	pointView();
	
});

function pointView() {
	$(".select_point").bind("click", function() {
		$("#point_view").attr("class", $(this).attr("id"));
		$("#point_view tr td").removeClass("selected");
		$(this).addClass("selected");
		var select_serial_id=$("#select_serial_id").val();
		var affection = $(this).find("input").val();
		judgePie(0, select_serial_id, affection);
	});
}

function search() {
	$("#search_table span").bind("click",function() {
		var pid = $(this).parent().attr("id");
		if (pid == "a_time") {
			 
			$("#startTime").val("");
			$("#endTime").val("");
			var clazz = $(this).attr("class");
			if (clazz == "custom" || clazz == "custom search_a") {
				$("#search_time").toggle();
			}else{
				$("#search_time").hide();
			}
			$("#time").val($(this).find("input").val());
		} else if (pid == "a_qu") {
			$("#area").val($(this).find("input").val());
		} else if (pid == "a_sear") {
			var clazz = $(this).attr("class");
			if (clazz == "custom") {
				$("#search_province").css("display", "block");
				$("#a_qu").css("display", "none");
				$("#area").val(0);
			} else {
				$("#search_province").css("display", "none");
				$("#provinceId").val(0);
				$("#a_qu").css("display", "block");
				$("#a_qu span").removeClass("search_a").eq(0).addClass(
						"search_a");
			}
		} else if (pid == "search_province") {
			$("#provinceId").val($(this).find("input").val());
		} else if (pid == "a_brand") {
			$("#seacrh_tr").show();
			var brandId = $(this).find("input").val();
			var json = $.ecAjax.getReturnJson({
				url : getWebPath() + "/media/getSerials?brandId="
						+ brandId
			});
			var html = "";
			$.each(json, function(index, value) {
				html += "<span onclick='addSerial(" + value.id + ",\""
						+ value.name + "\");'>" + value.name
						+ "<input type='hidden' value=" + value.id
						+ " /></span>";
			});
			$("#a_serials").empty().append(html);
		} else if (pid == "a_affection") {
			$("#affectionId").val($(this).find("input").val());
		}
		$("#" + pid + " span").removeClass("search_a");
		$(this).addClass("search_a");

	});

	$("#search_brand_more").toggle(function() {
		$(".search_brand_item_displey").css("display", "block");
		$(this).text("-更多");
		$("[name='product']").removeAttr("checked");
	}, function() {
		$(".search_brand_item_displey").css("display", "none");
		$("[name='product']").removeAttr("checked");
		$(this).text("+更多");
	});

}

function removeSerial(obj) {
	$("." + obj).remove();
}

function addSerial(id, name) {
	var obj = "serial_" + id;
	var flag = $("div").hasClass(obj);
	if (!flag) {
		var html = '<div class="search_brand_item '
				+ obj
				+ '"><table class="select_serial">'
				+ '<tbody><tr><td><div style="padding-top: 5px; padding-bottom: 5px;" class="brandName">'
				+ name
				+ '<input type="hidden" name="product" value="'
				+ id
				+ '" /></div></td>'
				+ '<td><div onclick="removeSerial(\''
				+ obj
				+ '\');" class="select_d">X</div></td></tr></tbody></table></div>';

		$("#select_ser").append(html);
	}
}

function getSearchUrl() {
	var area = $("#area").val();
	var time = $("#time").val();
	var url = "";
	if (area > 0)
		url += "&areaId=" + area;
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var provinceId = $("#provinceId").val();
	if (time > 0 && startTime.length > 0 && endTime.length > 0) {
		validateDate(startTime,endTime);
		url += "&startTime=" + startTime + "&endTime=" + endTime;
	} else {
		time = time > 0 ? 0 : time;
		url += "&time=" + time;
	}
	var array = new Array();
	$("input[name='product']").each(function() {
		array.push($(this).val());
	});
	if (array.length > 0) {
		url += "&serialIds=" + $.toArray(array);
	}
	if(array.length<1){
		art.dialog.alert("请至少选择一个产品");
		return false;
	}
	if (provinceId > 0) {
		url += "&provinceId=" + provinceId;
	}
	return url;
}

//对比
function getDiscuss(affection)
{
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if (startTime.length > 0 && endTime.length > 0) {
		if(validateDate(startTime,endTime)){
			return ;
		}
	}
	var json = $.ecAjax.getReturnJson({
		url : getWebPath()
				+ "/discuss/viewpoints?affection="
				+ affection + getSearchUrl()
	});
	var html =getData(json);
	$(".detail_right_content_div").remove();
	$("#productDiscuss").append(html);
	
	
	if($("#select_ser .select_serial").size()>3){
		$("#productDiscuss_more").show();
	}else{
		$("#productDiscuss_more").hide();
	}
}

function productDiscussMore(){
	
	var json = $.ecAjax.getReturnJson({
		url : getWebPath()
				+ "/discuss/viewpointsMore?affection="
				+ getAffection() + getSearchUrl()
	});
	var html =getData(json);
	$("#productDiscuss").append(html);
	$("#productDiscuss_more").hide();
}

function getData(json){
	
	var html = "";
	$.each(json,function(index, value) {
		var div = '<div class="detail_right_content_div"><span class="search_tags">'
				+ value.serialName
				+ '</span><div class="clear"></div>'
				+ '<table cellspacing="0" cellpadding="0"><tr><th>观点</th><th>评价数</th><th>占比</th></tr>';
		var tr = '';
		if(value.viewpoint==""){
			tr="<tr><td colspan='3' >暂无数据</td></tr>";
		}
		var carSerialId=value.carSerialId;
		
		$.each(value.viewpoint,function(ind, val) {
			 
			tr += '<tr><td>'
					+ val.name
					+ '</td><td class="td_border td_border_a"><a id="discuss_list_info" href="javascript:discussList('+val.id+','+carSerialId+');">'
					+ val.articleNums
					+ '</a></td><td>'
					+ (val.rate != null ? val.rate
							: '')
					+ '</td></tr>';
		});
		div += tr;
		div += '</table></div>';
		if((index+1)%3==0){
			div += '<div class="clear" ></div>';
		}
		
		html += div;
	});
	html += '<div class="clear"></div>';
	return html;
}

function productDiscuss() {
	$(".detail_right_content_a")
			.bind(
					"click",
					function() {
						getDiscuss(getAffection(this));
					});
}

function getAffection(note){
	if(note){
		$(".detail_right_content_a").removeClass("select_a");
		$(note).addClass("select_a");
	}
	var affection = 3;
	var id = $('.detail_right_content .select_a').attr('id');
	if (id == "good") {
		affection = 3;
	} else if (id == "middle") {
		affection = 2;
	} else if (id == "bad") {
		affection = 1;
	}
	return affection;
}

function cpfx(brandId) {
	$(".nav1 .info").hide();
	judge(brandId);
	judgePie(brandId,0,0);
	getSerials(brandId);
}

function aegativeArticles(brandId) {
	$(".nav2 .info").hide();
	downside(brandId);
	var json = $.ecAjax.getReturnJson({
		url : getWebPath() + "/index/aegativeArticles?brandId=" + brandId
	});
	var html = "";
	if(json){
		$.each(json, function(index, value) {
			html += '<div class="news_line"><a class="news_title" href="'+getWebPath()+'/discuss/detail/'+value.id+'?type=0">'
					+ value.title + '</a><span class="news_date">'
					+ timeStampString(value.pubDate)
					+ '</span><div class="clear"></div></div>';
		});
	}
	else{
		html+='<div class="no_data" >暂无数据</div>';
	}
	$("#aegativeArticles").empty().append(html);
}

function timeStampString(time) {
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1)
			: datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime
			.getDate();
	return year + "-" + month + "-" + date;
}

function getSerials(brandId) {
	if (brandId > 0) {
		var json = $.ecAjax.getReturnJson({
			url : getWebPath() + "/index/getSerials?brandId=" + brandId
		});

		var serialName = "";
		var select_serial_id = "";
		var html = "";
		$.each(json, function(index, value) {
			if (index == 0) {
				serialName = value.name;
				select_serial_id = value.id;
			}
			html += '<span onclick="judgePie(0,' + value.id
					+ ',0);" id="select_serial_' + value.id + '" class="info">'
					+ value.name + '</span>';
		});
		$(".nav3 span").remove();
		$("#select_serial").text(serialName);
		$("#select_serial_id").val(select_serial_id);
		$(".nav3").append(html);
		$(".nav3 span:last").css("border-bottom", "1px solid #2980c4");
		$(".nav3 span:first").css("border-top", "1px solid #2980c4");
	}
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

