(function($) {
		$.fn.paginate = function(options) {
			var opts = $.extend({}, $.fn.paginate.defaults, options);
			return this.each(function() {
				$this = $(this);
				var o = $.meta ? $.extend({}, opts, $this.data()) : opts;
				$.fn.draw(o,$this,o.currentPage,o.totalPage,o.display);	
			});
		};
		
		$.fn.paginate.defaults = {
				currentPage : 1,
				totalPage   : 10,
				display  	: 10,
				onLoad		: function(){return false;}
		};
		
		$.fn.draw = function(o,obj,currentPage,totalPage,display){
			
			var diff=0;
		
			var diffStr=currentPage.toString().length;
			if(diffStr==2){
				diff=1;
			}else if(diffStr>2){
				diff=diffStr-2;
				diff=diff*parseInt(display/4);
			}
			
			obj.empty();
			var ul=$("<ul class='ec-page-ul' >");
			obj.append(ul);
			display=display-1-diff;
			var half=  parseInt(display/2);
			var beginPage = currentPage-half;
			var engPage = currentPage+half;
			if(beginPage <= 0) {
				beginPage = 1;
			}
			if(engPage > totalPage) {
				engPage = totalPage;
			}
			if(engPage-beginPage<display && display<totalPage){
				for(var i=1;i<display;i++){
					beginPage = beginPage-1;
					if(beginPage <= 0)beginPage = 1;
					if(engPage-beginPage==display)
						break;
					engPage = engPage+1;
					if(engPage > totalPage) engPage=totalPage;
					if(engPage-beginPage==display)
						break;
				}
			}else if(totalPage<=display){
				beginPage=1;
				engPage=totalPage;
			}
			
			
			
			for(var i=beginPage;i<=engPage;i++) {	
				var cls="";
				if(i==currentPage) {
					cls =' ec-page-li-current';
				}
				ul.append('<li class="ec-page-li'+cls+'"><a id="'+(i)+'" >'+(i)+'</a></li>');
			}
			
			if(totalPage!=1){
				ul.prepend('<li class="ec-page-li"><a id="1">首页</a></li>');
				ul.append('<li class="ec-page-li"><a id="'+totalPage+'" >尾页</a></li>');
			}
		
			ul.find('li').click(function(){
				o.onLoad($(this).find("a").attr("id"));
			});
			
			ul.find('li').hover(function(){
				$(this).addClass("ec-page-li-current");
				 
			},function (){
				if($(this).find("a").text()!=currentPage){
					$(this).removeClass("ec-page-li-current");
				}
			});
		};
		
	})(jQuery);