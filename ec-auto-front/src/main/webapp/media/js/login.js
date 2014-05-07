jQuery(document.body).ready(function(){
	document.cookie="flag=1; path=/";
	if(jQuery("#name").val()!=""){
		jQuery("#name_text").html("");
	}
	if(jQuery("#passwd").val()!=""){
		jQuery("#passwd_text").html("");
	}
	
	jQuery("#name").focus(function(){
		if(jQuery("#name_text").html()=="请输入用户名"){
			jQuery("#name_text").html("");
		}
	});
	jQuery("#passwd").focus(function(){
		if(jQuery("#passwd_text").html()=="请输入密码"){
			jQuery("#passwd_text").html("");
		}
	});
	
	jQuery("#name").blur(function(){	
		if(jQuery("#name").val()==""){
			jQuery("#name_text").html("请输入用户名");
		}		
	});
	jQuery("#passwd").blur(function(){
		if(jQuery("#passwd").val()==""){
			jQuery("#passwd_text").html("请输入密码");
		}
	});
	
	
	jQuery("#passwd_text").click(function(e){
		jQuery("#passwd").focus();
	});
	jQuery("#name_text").click(function(e){
		jQuery("#name").focus();
	});
	
	 
	
	jQuery("#submit").click(function(){
		if(jQuery("#name").val()==""){
			art.dialog.alert("请输入用户名");
		}else if(jQuery("#passwd").val()==""){
			art.dialog.alert("请输入密码");
		}else{
			commit();
		}

	});
	
	document.onkeydown=function(event){
		e = event ? event :(window.event ? window.event : null);
		if(e.keyCode==13){
			if(jQuery("#name").val()!="" && jQuery("#passwd").val()!=""){
				commit();
			}
		}
	 };
	
	
});


function commit(){
	var url =jQuery("#hidden").val();
	$.post(url+"/eclogin/dologin",{"loginName":jQuery("#name").val(),"passwd":jQuery("#passwd").val()},function(json){
		 if(json.success){
			window.location.href=url+"/index_new/";
		 }else{
			 art.dialog.alert(json.msg);
		 }
	});
}




