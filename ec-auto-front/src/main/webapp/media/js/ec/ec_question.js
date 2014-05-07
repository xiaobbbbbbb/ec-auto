
function getWebPath() {
	return $("#basePath").val();
}

// 买车关注
function buyChart(serialId) {
	var url = getWebPath() + "/question/chart?type=2&serialId=" + serialId;
	var json = $.ecAjax.getReturnJson({
		url : url
	});
	var dadas = new Array();
	var categories = new Array();
	if (json) {
		$.each(json, function(index, value) {
			dadas.push(parseInt(value.value));
			categories.push(value.name);
		});
		$('#buy_chart').highcharts({
			chart : {
				type : 'column',
				margin : [ 50, 50, 100, 80 ],
				width:550
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
				text : ""
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
		$('#buy_chart').show();
	} else {
		$('#buy_chart').hide();
	}
}

//用车询问
function askChart(serialId) {
	var url = getWebPath() + "/question/chart?type=1&serialId=" + serialId;
	var json = $.ecAjax.getReturnJson({
		url : url
	});
	var dadas = new Array();
	var categories = new Array();
	if (json) {
		$.each(json, function(index, value) {
			dadas.push(parseInt(value.value));
			categories.push(value.name);
		});
		$('#ask_chart').highcharts({
			chart : {
				type : 'column',
				margin : [ 50, 50, 100, 80 ],
				width:550
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
				text : ""
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
				name : '数量',
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
		$('#ask_chart').show();
	} else {
		$('#ask_chart').hide();
	}
}

function buyTable(serialId) {
	var url = getWebPath() + "/question/census?type=2&serialId="+serialId;
	var url2 = getWebPath() + "/question/allcount?type=2&serialId="+serialId;
	$("#buy_tr").empty();
	$("#buy_table").empty();
	$.getJSON(url2, function(voData) {
		var html = "";
		html += "<td class='td_border'>全国</td>";
		html += "<td class='td_border'>" + voData+"</td>";
		$("#buy_tr").empty().append(html);
	});
	$.getJSON(url, function(voData) {
		if (voData.length > 0) {
			var html = "";
			$.each(voData,function(index, value) {
				if(!value.provinceName){
					value.provinceName="";
				}
				if(!value.cityName){
					value.cityName="";
				}
				html += "<tr><td class='td_border'>" + value.provinceName+ value.cityName+"</td>";
				html += "<td class='td_border '>" + value.counts + "</td>";
				html += "</tr>";
			});
			$("#buy_table").empty().append(html);
		}
	});
	
}

function askTable(serialId) {
	var url = getWebPath() + "/question/census?type=1&serialId="+serialId;
	var url2 = getWebPath() + "/question/allcount?type=1&serialId="+serialId;
	$("#ask_tr").empty();
	$("#ask_table").empty();
	$.getJSON(url2, function(voData) {
		var html = "";
		html += "<td class='td_border'>全国</td>";
		html += "<td class='td_border'>" + voData+"</td>";
		$("#ask_tr").empty().append(html);
	});
	$.getJSON(url, function(voData) {
		if (voData.length > 0) {
			var html = "";
			$.each(voData,
					function(index, value) {
				if(!value.provinceName){
					value.provinceName="";
				}
				if(!value.cityName){
					value.cityName="";
				}
						html += "<tr><td class='td_border'>" + value.provinceName+ value.cityName+"</td>";
						html += "<td class='td_border '>" + value.counts + "</td>";
						html += "</tr>";
						
					});
			$("#ask_table").empty().append(html);
		};
	});
	
}

function changeBrand() {
	changeBrand();
	var text = $(".select_serial .brandNames").text();
	text = text.trim();
	$(".select_serial .brandNames").attr("title", "我的" + text);
	$(".select_serial .brandNames").html(
			"我的" + $(".select_serial .brandNames").html());

}

/**
 * 饼图 
 * @param elementId 元素id
 * @param type 1买车关注 2用车询问
 * @param serialId
 */
function chartPie(elementId,type,serialId){
	var dataArray = [];
		
			var url = getWebPath() + "/question/chart?type="+ type +"&serialId=" + serialId;
			var json = $.ecAjax.getReturnJson({
				url : url
			});
			$.each(json, function(index, value) {
				var array = new Array();
				array.push(value.name,value.value);
				dataArray.push(array);
			});
	
		 $('#' + elementId).highcharts({
			 colors : [
						'#057dbc',
						'#e53b3c',
						'#15c30c',
						'#494949',
						'#ff6600',
						'#64099e'
					],
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
		            width:350
		        },
		        title: {
		            text: ''
		        },
		        tooltip: {
		    	    pointFormat: '<b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: '',
		            data: dataArray
		        }]
		    });
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
