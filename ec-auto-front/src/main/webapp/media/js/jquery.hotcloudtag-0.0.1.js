(function($) {
    $.fn.hotCloudTag = function(options, tags) {
        var defaults = {
            radius: 120,
            size: 300,
            speed: 10,
            depth: 300,
            howElliptical: 1,
            fontsize: 40,
			checkbar: true
        },
        param = $.extend({}, defaults, options || {}),
        selector = $(this).selector,
        warp = $(this),
        dtr = Math.PI / 180,
        itemPosList = [],
        active = true,
        lasta = 1,
        lastb = 1,
        distr = true,
        mouseX = 0,
        mouseY = 0,
        sb, sa, sc, ca, cb, cc, oItem, oEvent, si;
		
		
		var maxWeight = 1;
		$(tags).each(function() {
			if (this.weight>maxWeight) {
				maxWeight = this.weight;
			}
		});
		var styles = ['#8B29CC', '#0080FF', '#5C5C4A', '#DF7401', '#FE2E2E'];
		var partWeight = Math.ceil(maxWeight/5);
		
		var tagsHtml = "";
		var index=0;
		$(tags).each(function() {
			var i = getStyleIndexByWeight(this.weight);
		 
			var fontsize = Math.ceil(param.fontsize-(5-i)*2.5);
			
			tagsHtml += ('<a index="' + index + '" href="' +  this.url + '" weight="' + this.weight + '" key="' + styles[i] + '" style="color:' +styles[i] + '; font-size:' + fontsize + 'px;">' + this.text + '</a>');
			index++;
		});
		if (param.checkbar) {
			var tableHtml = '<table style="margin-left:10px;width:160px;height:90px;font-size:1px;text-align:center;" cellpadding="0" cellspacing="0"><tbody><tr><td><input class="check" key="#FE2E2E" type="checkbox" checked="checked"></td><td><div style="background-color: #FE2E2E;width:100px;height:12px;margin:0px auto;"></div></td><td style="color:#FE2E2E;font-size:14px;">最热</td></tr><tr><td><input class="check" key="#DF7401" type="checkbox" checked="checked"></td><td><div style="background-color: #DF7401;width:80px;height:12px;margin:0px auto;"></div></td><td rowspan="3"  valign="middle" style="color:#DF7401; ">&nbsp;</td></tr><tr><td><input class="check" key="#5C5C4A" type="checkbox" checked="checked"></td><td><div style="background-color: #5C5C4A;width:60px;height:12px;margin:0px auto;"></div></td></tr><tr><td><input class="check" key="#0080FF" type="checkbox" checked="checked"></td><td><div style="background-color: #0080FF;width:40px;height:12px;margin:0px auto;"></div></td></tr><tr><td><input class="check" key="#8B29CC" type="checkbox" checked="checked"></td><td><div style="background-color: #8B29CC;width:20px;height:12px;margin:0px auto;"></div></td><td style="color:#8B29CC;font-size:12px;">常态</td></tr></tbody></table>';
			warp.append(tableHtml);
		}
		
		warp.append(tagsHtml);
		
		var items = $(selector + ' a');
		
		
        items.each(function(index) {
            oItem = {};
            oItem.width = $(this).width();
            oItem.height = $(this).height();
            oItem.move = true;
            oItem.index = index;
            itemPosList.push(oItem);
            $(this).hover(function(){
				
            	var i = parseInt($(this).attr("index"));
            	itemPosList[i].move = false;
            },function(){
            	var i = parseInt($(this).attr("index"));
            	itemPosList[i].move = true;
            });
        });
        

		if (param.checkbar) {
			var checks = $(selector + ' input');
			checks.click(function() {
				var key = $(this).attr("key");
				if (this.checked) {
					items.each(function() {
						if ($(this).attr("key")==key) {
							$(this).show();
						}
					});
				} else {
					items.each(function() {
						if ($(this).attr("key")==key) {
							$(this).hide();
						}
					});
				}
			});
		}	
		
        init();

        setInterval(setPosition, 100);

		function getStyleIndexByWeight(weight) {
			var i = Math.floor(weight/partWeight);
			if (i==5) {
				i = 4;
			}
			return i;
		}
		
        function init() {
            var phi = 0,
            theta = 0,
            max = itemPosList.length;
            sineCosine(0, 0, 0);
            items.sort(function() {
                return Math.random() < 0.5 ? 1 : -1;
            });

            items.each(function(i) {
                if (distr) {
                    phi = Math.acos(-1 + (2 * i) / max);
                    theta = Math.sqrt(max * Math.PI) * phi;
                }
                else {
                    phi = Math.random() * (Math.PI);
                    theta = Math.random() * (2 * Math.PI);
                }
                itemPosList[i].cx = param.radius * Math.cos(theta) * Math.sin(phi);
                itemPosList[i].cy = param.radius * Math.sin(theta) * Math.sin(phi);
                itemPosList[i].cz = param.radius * Math.cos(phi);
                $(this).css('left', itemPosList[i].cx + warp.width() / 2 - itemPosList[i].width / 2 + 'px');
                $(this).css('top', itemPosList[i].cy + warp.height() / 2 - itemPosList[i].height / 2 + 'px');
            });
        };
		
		function getRandomXOrY(max) {
			return Math.random()*max;
		}
        function sineCosine(a, b, c) {
            sa = Math.sin(a * dtr);
            ca = Math.cos(a * dtr);
            sb = Math.sin(b * dtr);
            cb = Math.cos(b * dtr);
            sc = Math.sin(c * dtr);
            cc = Math.cos(c * dtr);
        };

        function setPosition() {
            var a, b, c = 0, j, rx1, ry1, rz1, rx2, ry2, rz2, rx3, ry3, rz3, l = warp.width() / 2, t = warp.height() / 2;

            if (active) {
				a = 1 * param.speed;
				b = -1 * param.speed;
            }
            else {
                a = lasta * 0.98;
                b = lastb * 0.98;
            }
            lasta = a;
            lastb = b;

            if (Math.abs(a) <= 0.01 && Math.abs(b) <= 0.01) {
                return;
            }
            sineCosine(a, b, c);
            for (j = 0; j < itemPosList.length; j++) {
                rx1 = itemPosList[j].cx;
                ry1 = itemPosList[j].cy * ca + itemPosList[j].cz * (-sa);
                rz1 = itemPosList[j].cy * sa + itemPosList[j].cz * ca;

                rx2 = rx1 * cb + rz1 * sb;
                ry2 = ry1;
                rz2 = rx1 * (-sb) + rz1 * cb;

                rx3 = rx2 * cc + ry2 * (-sc);
                ry3 = rx2 * sc + ry2 * cc;
                rz3 = rz2;

                itemPosList[j].cx = rx3;
                itemPosList[j].cy = ry3;
                itemPosList[j].cz = rz3;

                per = param.depth / (param.depth + rz3);

                itemPosList[j].x = (param.howElliptical * rx3 * per) - (param.howElliptical * 2);
                itemPosList[j].y = ry3 * per;
                itemPosList[j].scale = per;
                itemPosList[j].alpha = per;

                itemPosList[j].alpha = (itemPosList[j].alpha - 0.6) * (10 / 6);
            }

            items.each(function() {
				var i = parseInt($(this).attr("index"));
            	if (itemPosList[i].move) {
            		 $(this).css('left', itemPosList[i].cx + l - itemPosList[i].width / 2 + 'px');
                     $(this).css('top', itemPosList[i].cy + t - itemPosList[i].height / 2 + 'px');
                     $(this).css("z-index", i);
            	} else {
            		$(this).css("z-index", 9999);
            	}               
				
            	$(this).css('opacity', itemPosList[i].alpha);
//                if(navigator.appVersion.indexOf('MSIE ') == -1){
//                	$(this).css('opacity', itemPosList[i].alpha);
//                }else{                      
//                	$(this).css('filter', "alpha(opacity=" + (100 * itemPosList[i].alpha) + ")");
//                }
            });
        }
    };
})(jQuery);

