$(function(){
	$(".title_imp_a").hover(function(){
		if($(this).attr("class").indexOf("title_imp_a_hover")==-1){
			$(this).css({backgroundColor:"#ffffff",color:"#047ebc"});
		}
	},function(){
		if($(this).attr("class").indexOf("title_imp_a_hover")==-1){
			$(this).css({backgroundColor:"transparent",color:"#ffffff"});
		}
	});
	
})