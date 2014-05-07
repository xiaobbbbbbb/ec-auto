
function getWebPath() {
	return $("#basePath").val();
}

function getCounts(serialId){
	var json = $.ecAjax.getReturnJson({
		url : getWebPath() + "/mouth/pie?serialId=" + serialId
	});
	return json;
}
function pie(serialId,divId) {
	var json = getCounts(serialId);
	var dadas = new Array();
	var obj1 = new Array();
	var obj2 = new Array();
	var obj3 = new Array();
	obj1.push('好评');
	obj1.push(json.goodCounts);
	obj2.push('中评');
	obj2.push(json.middleCounts);
	obj3.push('差评');
	obj3.push(json.badCounts);
	dadas.push(obj1);
	dadas.push(obj2);
	dadas.push(obj3);
	var options = ({
		chart : {
			renderTo : divId, // 在哪个区域呈现，对应HTML中的一个元素ID
//			backgroundColor : '#f0f0f0', // 绘图区的背景颜色
			plotBorderWidth : null, // 绘图区边框宽度
			plotShadow : true
		// 绘图区是否显示阴影
		},
		title : {
			text : ''
		},
		colors : [
					'#057dbc',
					'#e53b3c',
					'#15c30c',
					'#494949',
					'#ff6600',
					'#64099e'
				],
		tooltip : {
			pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : false
				},
				showInLegend : true
			}

		},
		series : [ {
			type : 'pie',
			name :'占比' ,
			data : dadas
		} ]
	});
	if (options.series.length == 1) {
		options.series[0].size = 200;
	}
	new Highcharts.Chart(options);
}

function chart(serialId,affection,divId) {
	var count = getCounts(serialId);
	var url = getWebPath() + "/mouth/chart?serialId=" + serialId+"&affection="+affection;
	var json = $.ecAjax.getReturnJson({
		url : url
	});
	var dadas = new Array();
	var categories = new Array();
	var html="";
	if(affection==3){
		html+="(好评数:"+count.goodCounts+" 中评数："+count.middleCounts+"<span style='color:#3CC0B5'> 差评数:"+count.badCounts+"</span>)";
	}
	if(affection==2){
		html+="(好评数:"+count.goodCounts+" <span style='color:#3CC0B5'>中评数："+count.middleCounts+"</span> 差评数:"+count.badCounts+")";
	}
	if(affection==1){
		html+="(<span style='color:#3CC0B5'>好评数:"+count.goodCounts+"</span> 中评数："+count.middleCounts+" 差评数:"+count.badCounts+")";
	}
	if (json) {
		$.each(json, function(index, value) {
			dadas.push(parseInt(value.value));
			categories.push(value.name);
		});
		var options =({
			chart : {
				renderTo : divId, 
				type : 'column',
				margin : [ 50, 50, 100, 80 ],
			// backgroundColor : '#f0f0f0' // 绘图区的背景颜色
			},
			plotOptions: {
			    column: {
			        pointPadding: 0.2,
			        borderWidth: 0,
			        pointWidth: 30
			    }
			},
			title : {
				text : "总数评论数："+(count.goodCounts+count.middleCounts+count.badCounts),
				style: {
					 color:'#ff6600',
					 fontSize:'16px'
					}
			},
			subtitle:{
				useHTML:true,
				text:html
			},
			xAxis : {
				categories : categories,
				labels : {
					rotation : -45,
					align : 'right',
					style : {
						fontSize : '13px',
						fontFamily : 'Verdana, sans-serif'
					}
				},
				reversed : false
			},
			yAxis : {
				min : 0,
				title : "",
				labels : {
					enabled : false,
					overflow : 'justify'
				}
			},
			legend : {

				enabled : false
			},

			series : [ {
				name : "数量",
				data : dadas,
				dataLabels : {
					enabled : true,
					color : 'black',
					rotation : 1,
					x : 1,
					y : -5,
					style : {
						fontSize : '13px',
						fontFamily : 'Verdana, sans-serif',
						textShadow : '0 0 3px #fff'
					}
				}
			} ]

		});
		new Highcharts.Chart(options);
	}else{
		$("#"+divId).html("暂无数据!");
	}
}

function getCookie(name){
	var strCookie=document.cookie;
	var arrCookie=strCookie.split("; ");
	for(var i=0;i<arrCookie.length;i++){
		var arr=arrCookie[i].split("=");
		if(arr[0]==name)
		return arr[1];
	}
}

function addCookie(objName,objValue,objHours){    //添加cookie
	delCookie(objName);
    var str = objName + "=" + escape(objValue)+";path=/";
    if(objHours > 0){                               //为时不设定过期时间，浏览器关闭时cookie自动消失
        var date = new Date();
        var ms = objHours*3600*1000;
        date.setTime(date.getTime() + ms);
        str += "; expires=" + date.toGMTString();
    }
   document.cookie = str;
}

function delCookie(name){//删除cookie
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
 
}
 

