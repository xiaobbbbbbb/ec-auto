jQuery(document).ready(function(){
	var path=getWebPath();
	$(".search_content a,.provinces_div").click(function(){
		$(this).parent().find(".current").removeClass("current");
		$(this).addClass("current");
		var way= $(this).parent().attr("class").replace("search_content ","");
 
		if($(this).attr("class").indexOf("provinces_div")!=-1){
			$(".search_content .s .s_"+way).html($(this).find(".provinces_name").html());
			$(".city_div").hide();
			showCity(this);
		}else{
			$(".search_content .s .s_"+way).html($(this).html());
		}
	});
	
	$(".city_div div").click(function(event){
		event.stopPropagation(); 
		clickCity(this);
		
	});
	
	$(".city_div div").hover(function(){
		if(!$(this).attr("class"))
			$(this).css({backgroundColor:"#047ebc",color:"#ffffff"});
	},function(){
		if(!$(this).attr("class"))
			$(this).css({backgroundColor:"transparent",color:"#333333"});
	});
	
	$(".provinces_div").hover(function(){
		if($(this).attr("class").indexOf("current")!=-1){
			showCity(this);
		}else{
			$(this).addClass("current");
		}
	},function(){
		if($(this).parent().find(".current").size()>1)
			$(this).removeClass("current");
		
		$(".city_div").hide();
	});
	
	$(".q_content_more").click(function(){
		
		if($(this).find("div").html()=="收起"){
			$(this).find("div").html("展开");
			$(this).find("img").attr("src",path+"/media/images/q_more.png");
		}else{
			$(this).find("div").html("收起");
			$(this).find("img").attr("src",path+"/media/images/q_more_no.png");
		}
		$(this).parent().parent().find(".q_content_border").slideToggle("slow");
		
	});
	$(".q_content_more_else").click(function(){
		
		if($(this).find("div").html()=="收起"){
			$(this).find("div").html("展开");
			$(this).find("img").attr("src",path+"/media/images/q_more__.png");
		}else{
			$(this).find("div").html("收起");
			$(this).find("img").attr("src",path+"/media/images/q_more__no.png");
		}
		$(this).parent().parent().find(".q_table").slideToggle("slow");
		
	});
	
	
	$(".q_content_no_border a").click(function(){
		$(this).parent().find(".current_a").removeClass("current_a");
		$(this).addClass("current_a");
		
	});
	
	$(".q_content_more").click();
});

function getWebPath() {
	return $("#basePath").val();
}


function showCity(obj){
	var provinces_name=$(obj).find(".provinces_name").html();
	if(!(provinces_name=="北京" || provinces_name=="天津" || provinces_name=="上海" || provinces_name=="重庆"
	  || provinces_name=="香港" || provinces_name=="澳门")){
		var provinceId=$(obj).attr("id");
		var city_div=$(obj).find(".city_div");
		
		if(city_div.children().size()<2){
			var url = getWebPath() + "/question/city?provinceId="+provinceId;
			$.getJSON( url, function(data) {
				$.each(data, function(index, d) {
					var div=$("<div>").attr("id",d.id).html(d.name);
					div.click(function(event){
						event.stopPropagation(); 
						clickCity(this);
					});
					div.hover(function(){
						if(!div.attr("class"))
							div.css({backgroundColor:"#047ebc",color:"#ffffff"});
					},function(){
						if(!div.attr("class"))
							div.css({backgroundColor:"transparent",color:"#333333"});
					});
					city_div.append(div);
				});
			});
		}
		city_div.show();
		city_div.click(function(event){
			event.stopPropagation(); 
		});
	}
}

function getSearchUrl(){
	var url="";
//	var serialId = $("#serial_id").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	
	var questionId=$(".question .current").attr("id"); //问题id
	var provinceId=$(".area .current").attr("id");//区域省 id
	var cityId=$(".area .current .city_current").attr("id");//区域市id
	var veiwpointTypeId=$(".type .current").attr("id");//分类id
	var affection=$(".attr .current").attr("id");//属性id
	var grade=$(".grade .current").attr("id");//等级id
	
	var cpage = $('#cpage').val();
	if(!cpage || cpage <= 0) {
		cpage = 1;
	}
//	url+="?serialId="+serialId;
	if (startTime && endTime) {
		url += "&startTime=" + startTime + "&endTime=" + endTime;
	}if(questionId){
		url +="&questionId="+questionId;
	}if(provinceId){
		url+="&provinceId="+provinceId;
	}if(cityId){
		url+="&cityId="+cityId;
	}if(veiwpointTypeId){
		url+="&veiwpointTypeId="+veiwpointTypeId;
	}if(affection){
		url+="&affection="+affection;
	}
	if(cpage>0){
		url += "&cpage=" + cpage;
	}
		return url;
}
function clickCity(obj){
	 
	
	$(obj).parent().find(".city_current").removeClass("city_current").css({backgroundColor:"transparent",color:"#333333"});
	if($(obj).parent().find("div").css("backgroundColor")=="047ebc"){
		$(obj).css({backgroundColor:"transparent",color:"#333333"});
	};
	$(obj).addClass("city_current").css({backgroundColor:"#047ebc",color:"#ffffff"});

	$(".search_content .s .s_area").html($(".area .current .provinces_name").html()+"-"+$(obj).html());
	$(".city_div").hide();
}