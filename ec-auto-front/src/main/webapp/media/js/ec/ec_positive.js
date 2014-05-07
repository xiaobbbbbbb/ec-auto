
function getWebPath() {
	return $("#basePath").val();
}

function loadTopData(){
	var serialId=$("#serial_id").val();
	var url = getWebPath() + "/positive/getData?serialId="+serialId;
	
	$.getJSON(
			url,
			function(data) {
				$("#allnums").html(data.lowNums+data.highNums);
				var lowNumsVal=data.lowNums; 
				$("#lownums").html(lowNumsVal);
				$("#highnums").html(data.highNums);
				//动态设置字体大小
				if(lowNumsVal > 999 && lowNumsVal < 9999){
					$("#lownums").removeClass();
					$("#lownums").addClass("p_content_l_blues_font24");
					$("#lownums").html(lowNumsVal);
					
					$("#highnums").removeClass();
					$("#highnums").addClass("p_content_l_reds_font24");
					$("#highnums").html(data.highNums);
				}else if(lowNumsVal >= 9999 && lowNumsVal < 99999){
					$("#lownums").removeClass();
					$("#lownums").addClass("p_content_l_blues_font18");
					$("#lownums").html(lowNumsVal);
					
					$("#highnums").removeClass();
					$("#highnums").addClass("p_content_l_reds_font18");
					$("#highnums").html(data.highNums);
				}else if(lowNumsVal >= 99999){
					$("#lownums").removeClass();
					$("#lownums").addClass("p_content_l_blues_font15");
					$("#lownums").html(lowNumsVal);
					
					$("#highnums").removeClass();
					$("#highnums").addClass("p_content_l_reds_font15");
					$("#highnums").html(data.highNums);
				}
				  
				  
				if (data.list.length > 0) {	
					html='<span class="content_l_title" >分类</span>';
					$.each(data.list,
							function(index, value) {
								
				 		 		
				 		 		if(value.name==1){
				 		 			html+='<div class="content_l_div" >';
				 		 			html+='产品：<span class="content_l_blue">'+value.value +'</span>条</div>';
				 		 		}
				 		 		if(value.name==2){
				 		 			html+='<div class="content_l_div" >';
				 		 			html+='服务：<span class="content_l_blue">'+value.value +'</span>条</div>';
				 		 		}
				 		 		if(value.name==3){
				 		 			html+='<div class="content_l_div" >';
				 		 			html+='其它：<span class="content_l_blue">'+value.value +'</span>条</div>';
				 		 		}
				 		 		 
							});
					html+="<div class='clear' ></div>";
					$("#viewpoint_type").empty().append(html);
				}

			});
}

function judge() {
	var serialId=$("#serial_id").val();
	var url = getWebPath() + "/positive/chart?serialId=" + serialId;
	var json = $.ecAjax.getReturnJson({
		url : url
	});
	var datas = new Array();
		var obj = new Object();
		var obj2 = new Array();
		$.each(json, function(index, value) {
			var array = new Array();
			array.push(getUTC(value.name));
			array.push(value.value);
			obj2.push(array);
		});
		obj.data = obj2;
		datas.push(obj);
	
	var options = ({
		// 常规图表选项设置
		chart : {
			renderTo : 'chart', // 在哪个区域呈现，对应HTML中的一个元素ID
//			backgroundColor : '#f0f0f0', // 绘图区的背景颜色
			plotBorderWidth : null, // 绘图区边框宽度
			plotShadow : true
		// 绘图区是否显示阴影
		},
		tooltip : {
			formatter : function() {
				return this.y;
			}
		},
		legend : {
			enabled:false   //不显示线条name
		},
		title : {
			text : ''
		},
		xAxis : {
			type : 'datetime',
			maxPadding : 0.1,
			minPadding : 0.1,
			tickInterval : 24 * 3600 * 1000 * 1,// 两天画一个x刻度
			// tickPixelInterval : 50,
			tickWidth : 3,// 刻度的宽度
			labels : {
				formatter : function() {
					return Highcharts.dateFormat('%m/%d', this.value);
				}

			}
		},
		yAxis : {
			min: 0, 
			title : {
				text : ''
			},
			labels : {
//				enabled : false,
				overflow : 'justify'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		series :datas
			
	});
	for ( var i = 0; i < options.series.length; i++) {
		if (i > 2) {
			options.series[i].visible = false;
		}
	}
	new Highcharts.Chart(options);
}

function getUTC(DateTime) {
	var yr = DateTime.substring(0, 4);
	var mo = DateTime.substring(5, 7);
	var dy = DateTime.substring(8, 10);
	return Date.UTC(yr, mo - 1, dy);
}

$.datepicker._gotoToday = function (id) {
	var target = $(id);
	var inst = this._getInst(target[0]);
	if (this._get(inst, 'gotoCurrent') && inst.currentDay) {
	inst.selectedDay = inst.currentDay;
	inst.drawMonth = inst.selectedMonth = inst.currentMonth;
	inst.drawYear = inst.selectedYear = inst.currentYear;
	}
	else {
	var date = new Date();
	inst.selectedDay = date.getDate();
	inst.drawMonth = inst.selectedMonth = date.getMonth();
	inst.drawYear = inst.selectedYear = date.getFullYear();
	this._setDateDatepicker(target, date);
	this._selectDate(id, this._getDateDatepicker(target));
	}
	this._notifyChange(inst);
	this._adjustDate(target);
} ;