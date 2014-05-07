jQuery(document.body).ready(function(){
	
	jQuery(".kbfx .title_car_model").click(function(){
		jQuery(".kbfx .title_car_model").each(function(v,c){
			if(jQuery(this).css("backgroundImage").indexOf("index_a_bg")!=-1){
				jQuery(this).css("color","#656565").css("backgroundImage","none");
			}
		});
		jQuery(this).css("color","#ffffff").css("backgroundImage","url('../media/images/index_a_bg.png')");
	});
	
	jQuery(".fmqb .title_car_model").click(function(){
		jQuery(".fmqb .title_car_model").each(function(v,c){
			if(jQuery(this).css("backgroundImage").indexOf("index_a_bg")!=-1){
				jQuery(this).css("color","#656565").css("backgroundImage","none");
			}
		});
		jQuery(this).css("color","#ffffff").css("backgroundImage","url('../media/images/index_a_bg.png')");
	});
	
	jQuery(".title_car_model").hover(function(){
		jQuery(this).css("color","#ffffff").css("backgroundImage","url('../media/images/index_a_bg.png')");
	},function(){
		var size=0;
		jQuery(this).parent().find(".title_car_model").each(function(v,c){
			if(jQuery(this).css("backgroundImage").indexOf("index_a_bg")!=-1){
				size++;
				
			}
		});
		
		if(size>1){
			jQuery(this).css("color","#ffffff").css("color","#656565").css("backgroundImage","none");
		}

	});
	
});





