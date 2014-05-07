var data = new Array();
data.push("/index/");
data.push("/mouth/index");
data.push("/question/index");
data.push("#");
data.push("/advert/index");
data.push("/positive/index");
data.push("#");
data.push("/important/index");



jQuery(document.body).ready(function(){
	var url =jQuery("#hidden").val();
//	for(var i=0;i<data.length;i++){
//		var dataStrs=data[i].split(",");
//		for(var j=0;j<dataStrs.length;j++){
//			if(window.location.href.indexOf(dataStrs[j])!=-1){
//			    $(".top_centent_bottom_index a").css("backgroundImage","none");
//				$(".top_centent_bottom_index a:eq("+i+")").css("backgroundColor","#005c8d");
//				break;
//			}
	 
			$(".top_centent_bottom_index a").each(function(){
				if(window.location.href.indexOf($(this).attr("href"))!=-1){
					$(this).css("backgroundColor","#005c8d");
					return false;
				}else{
					//这里还需要判断二级菜单的导航位置
					
					if(window.location.href.indexOf($(this).attr("title"))!=-1){
						$(this).css("backgroundColor","#005c8d");
						return false;
					}
				}
			});;
//		}
//	}
	
	
	
	 jQuery("#absoulte_close").click(function(){
		 jQuery(".top_centent_absoulte").hide();
		 document.cookie="flag=0; path=/";
	 });
	
	 jQuery("#message").click(function(){
		 setTimeout(messageOpen, 200);
	 });
	 
	 jQuery("#report").click(function(){
		 setTimeout(reportOpen, 200);
	 });
	 
	 jQuery("#set").click(function(){
		 setTimeout(setOpen, 200);
	 });
	 
	 
	 jQuery("body").click(function(){
		 if(jQuery(".top_centent_set").css("display")=="block"){
			 jQuery(".top_centent_set").hide();
		 }else if(jQuery(".top_centent_ranking").css("display")=="block"){
			 jQuery(".top_centent_ranking").hide();
		 }
	 });
	 
	 
	 
	
	 
	 $.post(url+"/index/ranking",{},function(list){
		 $(list).each(function(i){
			 var name =list[i].name;
			 var div =jQuery("<div>").addClass("ranking_title_td").click(function(){
				 window.open(" http://top.baidu.com/detail?b=176&w="+name+"&c=18");
			 });
			 div.append(jQuery("<div>").addClass("thead_td_1"));
			 div.append(jQuery("<div>").addClass("thead_td_2").html(list[i].name));
			 div.append(jQuery("<div>").addClass("thead_td_3").html(list[i].num));
			 div.append(jQuery("<div>").addClass("clear"));
			 jQuery(".top_centent_ranking").append(div);
			
		 });
	  });
	 
	 $.post(url+"/index/alarm",{},function(list){
		 $(list).each(function(i){
			 var a =jQuery("<a>");
			 a.attr("href",url+"/discuss/detail/"+list[i].id);
			 a.attr("target","_blank");
			 a.addClass("top_centent_absoulte_a");
			 a.html(list[i].title);
			 jQuery(".top_centent_absoulte").append(a);
			 
		 });
		 if(list.length>0 && getCookie("flag")==1){
			 jQuery(".top_centent_absoulte").show();
		 }
	  });
});


function getCookie(name){
	var strCookie=document.cookie;
	var arrCookie=strCookie.split("; ");
	for(var i=0;i<arrCookie.length;i++){
	var arr=arrCookie[i].split("=");
	if(arr[0]==name)
	return arr[1];
}
}	
	
function messageOpen(){
	jQuery(".top_centent_absoulte").toggle();
	jQuery(".top_centent_ranking").hide();
	jQuery(".top_centent_set").hide();
	document.cookie="flag=1; path=/";
}


function reportOpen(){
	jQuery(".top_centent_ranking").show();
	jQuery(".top_centent_absoulte").hide();
	jQuery(".top_centent_set").hide();
}

function setOpen(){
	jQuery(".top_centent_set").show();
	jQuery(".top_centent_absoulte").hide();
	jQuery(".top_centent_ranking").hide();
}


function updatePassWd(){
	
	jQuery(".top_centent_set").toggle();
	
	var div="<div class='passwd_div'>" +
			"<input id='passwd_old' type='password' />" +
			"<input id='passwd_new'  type='password' />" +
			"<input id='passwd_new_too'  type='password' />" +
			"<a id='passwd_submit' href='javascript:update();'></a>" +
			"<a id='passwd_cancel' href='javascript:cancel();' ></a>" +
			"</div>";
	 
	
	 var div_without=jQuery("<div class='update_passwd' >");
	 div_without.height(document.body.scrollHeight);
	 div_without.html(div);
	 div_without.appendTo(jQuery("body"));
	 
}

function moveLeft(){
	
	jQuery("#passwd_div").css("left",(document.body.clientWidth-376)/2);
}


function update(){
	
	if(jQuery("#passwd_old").val()==""){
		art.dialog.alert("请输入原始密码");
	}else if(jQuery("#passwd_new").val()==""){
		art.dialog.alert("请输入密码");
	}else if(jQuery("#passwd_new").val()!=jQuery("#passwd_new_too").val()){
		art.dialog.alert("两次输入密码必须一致");
	}else{
		var url =jQuery("#hidden").val();
		 $.post(url+"/index/repasswd",{"oldPasswd":jQuery("#passwd_old").val(),"newPasswd":jQuery("#passwd_new").val()},function(json){
			 art.dialog.alert(json.msg);
			 if(json.success){
				 cancel();
			 }
		  });
	}
}


function cancel(){
	
	jQuery(".update_passwd").html("");
	jQuery(".update_passwd").hide();
}

function removeCookie(){//删除cookie
	reCookie("myserialId");
	reCookie("mybrandId");
	reCookie("otherserialId");
	reCookie("mySerial");
	reCookie("otherbrandId");
	reCookie("otherSerial");
		
} 

function reCookie(name){//删除cookie
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";path=/;expires="+exp.toGMTString();
 
}


