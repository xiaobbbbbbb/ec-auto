var data = new Array();
data.push("/rgroup/list");
data.push("/rorg/list");
data.push("/ruser/list");
data.push("/rrole/list");
data.push("/carbrand/list");
data.push("/carserial/list");


jQuery(document).ready(function(){
	for(var i=0;i<data.length;i++){
		var dataStrs=data[i].split(",");
		for(var j=0;j<dataStrs.length;j++){
			if(window.location.href.indexOf(dataStrs[j])!=-1){
				$(".left_dot:eq("+i+")").addClass("left_a_hover");
				$(".left_dot:eq("+i+")").parent().show();
				break;
			}
		}
	}
	
	$("#user_role_a").click(function(){
		$("#user_role_div").slideToggle("slow");
		if($("#carbrand_carserial_div").css("display")=="block"){
			$("#carbrand_carserial_div").slideUp();
		}
	});
	
	
	$("#carbrand_carserial_id").click(function(){
		$("#carbrand_carserial_div").slideToggle("slow");
		
		if($("#user_role_div").css("display")=="block"){
			$("#user_role_div").slideUp();
		}
	});
});