$(function() {
	
	search();
	
});

function changeBrand(){
	changeBrand();
	var text=$(".select_serial .brandNames").text();
	text=text.trim();
	$(".select_serial .brandNames").attr("title","我的"+text);
	$(".select_serial .brandNames").html("我的"+$(".select_serial .brandNames").html());
	
}

function search() {
	$("#search_table span").bind(
			"click",
			function() {
				var pid = $(this).parent().attr("id");
				if (pid == "a_time") {
					$("#startTime").val("");
					$("#endTime").val("");
					var clazz = $(this).attr("class");
					if (clazz == "custom" || clazz == "custom search_a") {
						$("#search_time").toggle();
						$("#startTime").val();
						$("#endTime").val();
						$("#startTime,#endTime").datepickerStyle();
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
					var brandId = $(this).find("input").val();
					addBrand(brandId, $(this).text().length>4?($(this).text().substr(0,4)+".."):$(this).text(),$(this).text());
					
				}
				$("#" + pid + " span").removeClass("search_a");
				$(this).addClass("search_a");

			});

	$("#search_brand_more").toggle(function() {
		$(".search_brand_item_displey").css("display", "block");
	removeBrandtext("-更多");
		$("[name='product']").removeAttr("checked");
	}, function() {
		$(".search_brand_item_displey").css("display", "none");
		$("[name='product']").removeAttr("checked");
		$(this).text("+更多");
	});

}

function removeBrand(obj) {
	$("." + obj).remove();
}

function addBrand(id, name,fullname) {
	var obj = "serial_" + id;
	var flag = $("div").hasClass(obj);
	if (!flag) {
		var html = '<div class="search_brand_item '
				+ obj
				+ '"><table class="select_serial">'
				+ '<tbody><tr><td><div title="'+fullname+'" style="padding-top: 5px; padding-bottom: 5px;" class="brandNames">'
				+ name
				+ '<input type="hidden" name="product" value="'
				+ id
				+ '" /></div></td>'
				+ '<td><div onclick="removeBrand(\''
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
	var grade =$("input[name='grade']:checked").val();
	var cpage = $('#cpage').val();
	var des_content =$("input[name='des_content']:checked").val();
	if(!cpage || cpage <= 0) {
		cpage = 1;
	}
	if(!des_content||des_content<0){
		des_content=0;
	}
	if (startTime.length > 0 && endTime.length > 0) {
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
		url += "&brandIds=" + $.toArray(array);
	}
	if(array.length<1){
		art.dialog.alert("请至少选择一个品牌");
		return false;
	}
	if (provinceId > 0) {
		url += "&provinceId=" + provinceId;
	}
	if(grade>0){
		url += "&grade=" + grade;
		$("#grade_hidden").val(grade);
	}
	if(cpage>0){
		url += "&cpage=" + cpage;
	}
	url += "&des_content=" + des_content;
	return url;
}


function negativeTable(param)
{
	anchart(1,param);
	var url= getWebPath() + "/downside/query?brandId=1";
	if (param != null){
	 url += param;
	}
	var json = $.ecAjax.getReturnJson({
		url :url
	});
	var html="";
	html+="<table cellspacing='0' cellpadding='0' style='width: 700px;'>"+
	"<tr><th>时间</th>";
	$.each(json.data.split(","), function(index, value) {
		if(index>0){
			html+="<th>"+value.split("_")[1]+"</th>";
		}
	});
	html+"</tr>";

	$.each(json.page.list, function(index, value) {
		html+="<tr>";
		$.each(value.data.split(","), function(indexs, va) {
			html+="<td>"+va+"</td>";
		});
		html+="</tr>";
	});
	html+="</table>";
	$("#tableList").empty().append(html);
}

function negativeList(param)
{
	var url= getWebPath() + "/downside/queryList?brandId=1";
	if (param != null){
	 url += param;
	}
	var json = $.ecAjax.getReturnJson({
		url :url
	});
	var html="<tr><th>品牌</th><th>标题</th><th>责任对象</th><th>来源媒体</th><th>时间</th><th>其它</th></tr>";

	$.each(json, function(indexs, va) {
		html+="<tr><td>"+va.brand_name+"</td><td>"+va.title+"</td><td>";
		if(va.desTarget==1){
			html+="主机厂";
		}
		 if(va.desTarget==2){
			html+="4S店";
		}if(va.desTarget==null||va.desTarget==""){
			html+="其它";
		}
		html+="</td><td>"+va.mediaName+"</td>";
		html+="</td><td>"+va.createTime+"</td><td></td></tr>";
	});
	$("#table_list").empty().append(html);
}
validateDate = function(startTime,endTime){
	var tmp = startTime.split("-");
	var date1 = new Date(tmp[0],tmp[1]-1,tmp[2]);
	tmp = endTime.split("-");

	var date2 = new Date(tmp[0],tmp[1]-2,tmp[2]);
	if(date2.getTime() - date1.getTime() > 30 * 24 * 60 * 60 * 1000){
	        art.dialog.alert("时间间隔不能超过30天");
	        return true;
	}
	return false;
};


