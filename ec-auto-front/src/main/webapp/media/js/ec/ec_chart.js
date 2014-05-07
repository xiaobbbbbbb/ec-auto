function getWebPath() {
	return $("#basePath").val();
}

function getUTC(DateTime) {
	var yr = DateTime.substring(0, 4);
	var mo = DateTime.substring(5, 7);
	var dy = DateTime.substring(8, 10);
	return Date.UTC(yr, mo - 1, dy);
}

function judge(brandId, param) {
	var url = getWebPath() + "/index/judge?brandId=" + brandId;
	if (param != null)
		url += param;
	var json = $.ecAjax.getReturnJson({
		url : url
	});
	// 最近7天评论数
	var datas = new Array();
	$.each(json, function(index, value) {
		var obj = new Object();
		obj.name = value.name;
		var obj2 = new Array();

		$.each(value.data, function(index, value) {
			var array = new Array();
			array.push(getUTC(value.name));
			array.push(value.value);
			obj2.push(array);
		});
		obj.data = obj2;
		datas.push(obj);
	});
	var options = ({
		// 常规图表选项设置
		chart : {
			renderTo : 'kbfx_qst', // 在哪个区域呈现，对应HTML中的一个元素ID
			backgroundColor : '#f0f0f0', // 绘图区的背景颜色
			plotBorderWidth : null, // 绘图区边框宽度
			plotShadow : true
		// 绘图区是否显示阴影
		},
		tooltip : {
			formatter : function() {
				return '<b>' + Highcharts.dateFormat('%Y/%m/%d', this.x)
						+ '</b><br/>' + '<b>' + this.series.name + ":</b>"
						+ this.y;
			}
		},
		legend : {
			layout : 'horizontal',
			align : 'center',
			verticalAlign : 'bottom',
			borderWidth : 0
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
			title : {
				text : ''
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		series : datas
	});
	for ( var i = 0; i < options.series.length; i++) {
		if (i > 2) {
			options.series[i].visible = false;
		}
	}
	new Highcharts.Chart(options);
}

function judgePie(brandId, serialId, affection) {

	$(".nav3 .info").hide();
	if (!affection > 0) {
		$("#point_view").attr("class", "point_good");
		$("#point_view tr td").removeClass("selected").eq(0).addClass(
				"selected");
	}
	var json = $.ecAjax.getReturnJson({
		url : getWebPath() + "/index/judgePie?brandId=" + brandId
				+ "&serialId=" + serialId + "&affection=" + affection
	});
	if (serialId > 0) {
		var cur = $("#select_serial_" + serialId).text();
		cur = (cur != null && cur.length > 0) ? cur : $("#select_serial")
				.text();
		$("#select_serial").text(cur);
		$("#select_serial_id").val(serialId);
	}
	var dadas = new Array();
	$.each(json, function(index, value) {
		var obj = new Array();
		obj.push(value.chartName);
		obj.push(parseInt(value.chartValue));
		dadas.push(obj);
	});
	var options = ({
		chart : {
			renderTo : 'kbfx_qttj', // 在哪个区域呈现，对应HTML中的一个元素ID
			backgroundColor : '#f0f0f0', // 绘图区的背景颜色
			plotBorderWidth : null, // 绘图区边框宽度
			plotShadow : true
		// 绘图区是否显示阴影
		},
		title : {
			text : ''
		},
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
			name : '七天统计数',
			data : dadas
		} ]
	});
	if (options.series.length == 1) {
		options.series[0].size = 200;
	}
	new Highcharts.Chart(options);
}

function downside(brandId) {
	var json = $.ecAjax.getReturnJson({
		url : getWebPath() + "/index/downside?brandId=" + brandId
	});
	var datas = new Array();
	$.each(json, function(index, value) {
		var obj = new Object();
		obj.name = value.name;
		var obj2 = new Array();

		$.each(value.data, function(index, value) {
			var array = new Array();
			array.push(getUTC(value.name));
			array.push(value.value);
			obj2.push(array);
		});
		obj.data = obj2;
		datas.push(obj);
	});

	new Highcharts.Chart({
		colors : [ 'blue', 'yellow', 'red' ],
		chart : {
			renderTo : 'fmqb', // 在哪个区域呈现，对应HTML中的一个元素ID
			backgroundColor : '#f0f0f0', // 绘图区的背景颜色
			plotBorderWidth : null, // 绘图区边框宽度
			plotShadow : true
		// 绘图区是否显示阴影
		},
		title : {
			text : '（最近7天）负面情报趋势'
		},
		tooltip : {
			formatter : function() {
				return '<b>' + Highcharts.dateFormat('%Y/%m/%d', this.x)
						+ '</b><br/>' + '<b>' + this.series.name + ":</b>"
						+ this.y;
			}
		},
		xAxis : {
			type : 'datetime',
			maxPadding : 0.1,
			minPadding : 0.1,
			tickInterval : 24 * 3600 * 1000 * 1,// 1天画一个x刻度
			// tickPixelInterval : 50,
			tickWidth : 3,// 刻度的宽度
			labels : {
				formatter : function() {
					return Highcharts.dateFormat('%m/%d', this.value);
				}
			}
		},
		yAxis : {
			title : {
				text : ''
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		legend : {
			layout : 'horizontal',
			align : 'center',
			verticalAlign : 'bottom',
			borderWidth : 0
		},
		series : datas
	});
}

function statistics(param) {
	var url = getWebPath() + "/index/statistics";
	if (param != null)
		url += "?" + param;
	var json = $.ecAjax.getReturnJson({
		url : url
	});

	var dadas = new Array();
	if (json.object) {
		$.each(json.object, function(index, value) {
			var obj = new Array();
			obj.push(value.chartName);
			obj.push(parseInt(value.chartValue));
			dadas.push(obj);
		});

		$('#cbxl_right')
				.highcharts(
						{
							chart : {
								backgroundColor : '#f0f0f0',
								plotBorderWidth : null,
								plotShadow : false
							},
							title : {
								text : json.title + '媒体传播占比'
							},
							tooltip : {
								pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
							},
							plotOptions : {
								pie : {
									allowPointSelect : true,
									cursor : 'pointer',
									dataLabels : {
										enabled : true,
										color : '#000000',
										connectorColor : '#000000',
										format : '<b>{point.name}</b>: {point.percentage:.1f} %'
									}
								}
							},
							series : [ {
								type : 'pie',
								name : '传播占比',
								data : dadas
							} ]
						});
		$('#cbxl_right_table').hide();
		$('#cbxl_right').show();
	} else {
		var html = '<div class="no_data_img">暂无数据</div>';
		$('#cbxl_right').hide();
		$('#cbxl_right_table').html(html);
		$('#cbxl_right_table').show();
	}
}

function statisticsRanking(param) {
	var url = getWebPath() + "/index/statisticsRanking";
	if (param != null)
		url += "?" + param;
	var json = $.ecAjax.getReturnJson({
		url : url
	});
	var dadas = new Array();
	var categories = new Array();
	if (json.object) {
		$.each(json.object, function(index, value) {
			dadas.push(parseInt(value.data));
			categories.push(value.categories);
		});
		$('#cbxl_left').highcharts({
			chart : {
				type : 'column',
				margin : [ 50, 50, 100, 80 ],
				backgroundColor : '#f0f0f0' // 绘图区的背景颜色
			},
			title : {
				text : json.title + '媒体报道累计'
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
				reversed : true
			},
			yAxis : {
				min : 0,
				labels : {
					overflow : 'justify'
				}
			},
			legend : {
		               
				enabled : false
			},
			series : [ {
				name : '传播数',
				data : dadas,
				dataLabels : {
					enabled : true,
					color : 'black',
					rotation: -30,
					x : 1,
					y : -20,
					style : {
						fontSize : '13px',
						fontFamily : 'Verdana, sans-serif',
						textShadow : '0 0 3px #fff'
					}
				}
			} ]
			
		});
		$('#cbxl_left_table').hide();
		$('#cbxl_left').show();
	} else {
		var html = '<div class="no_data_img">暂无数据</div>';
		$('#cbxl_left').hide();
		$('#cbxl_left_table').html(html);
		$('#cbxl_left_table').show();
	}
}

// 传播趋势
function spreadTrend(brandId, param) {
	var url = getWebPath() + "/media/spreadTrend?brandId=" + brandId;
	if (param != null)
		url += param;
	var json = $.ecAjax.getReturnJson({
		url : url
	});
	// 初始化数据
	var datas = new Array();
	if (json.object && json.object.length > 0) {
		$.each(json.object, function(index, value) {
			var obj = new Object();
			obj.name = value.name;
			var obj2 = new Array();

			$.each(value.data, function(index, value) {
				var array = new Array();
				array.push(getUTC(value.name));
				array.push(value.value);
				obj2.push(array);
			});
			obj.data = obj2;
			datas.push(obj);
		});
		new Highcharts.Chart({
			// 常规图表选项设置
			chart : {
				renderTo : 'spread_trend', // 在哪个区域呈现，对应HTML中的一个元素ID
				backgroundColor : '#f0f0f0', // 绘图区的背景颜色
				plotBorderWidth : null, // 绘图区边框宽度
				plotShadow : true
			// 绘图区是否显示阴影
			},
			legend : {
				layout : 'horizontal',
				align : 'center',
				verticalAlign : 'bottom',
				borderWidth : 0
			},
			title : {
				text : json.title + '媒体传播趋势'
			},
			tooltip : {
				formatter : function() {
					return '<b>' + Highcharts.dateFormat('%Y/%m/%d', this.x)
							+ '</b><br/>' + '<b>' + this.series.name + ":</b>"
							+ this.y;
				}
			},
			xAxis : {
				type : 'datetime',
				maxPadding : 0.1,
				minPadding : 0.1,
				tickInterval : 24 * 3600 * 1000 * 1,// 1天画一个x刻度
				// tickPixelInterval : 50,
				tickWidth : 3,// 刻度的宽度
				labels : {
					formatter : function() {
						return Highcharts.dateFormat('%m/%d', this.value);
					}
				}
			},
			yAxis : {
				title : {
					text : ''
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			series : datas
		});
		$('#spread_trend_table').hide();
		$('#spread_trend').show();
	} else {
		var html = '<div class="no_data_img">暂无数据</div>';
		$('#spread_trend').hide();
		$('#spread_trend_table').html(html);
		$('#spread_trend_table').show();
	}

}

function anchart(brandId, param) {
	var url = getWebPath() + "/downside/anChart?brandId=" + brandId;

	if (param != null)
		url += param;
	var json = $.ecAjax.getReturnJson({
		url : url
	});
	var datas = new Array();
	if (json.object && json.object.length > 0) {
		$.each(json.object, function(index, value) {
			var obj = new Object();
			obj.name = value.name;
			var obj2 = new Array();

			$.each(value.data, function(index, value) {
				var array = new Array();
				array.push(getUTC(value.name));
				array.push(value.value);
				obj2.push(array);
			});
			obj.data = obj2;
			datas.push(obj);
		});
		new Highcharts.Chart({

			chart : {
				renderTo : 'fmqb', // 在哪个区域呈现，对应HTML中的一个元素ID
				backgroundColor : '#f0f0f0', // 绘图区的背景颜色
				plotBorderWidth : null, // 绘图区边框宽度
				plotShadow : true

			// 绘图区是否显示阴影
			},
			plotOptions : {
				line : {
					dataGrouping : {
						enabled : false
					}
				}
			},

			tooltip : {
				formatter : function() {
					return '<b>' + Highcharts.dateFormat('%Y/%m/%d', this.x)
							+ '</b><br/>' + '<b>' + this.series.name + ":</b>"
							+ this.y;
				}
			},
			title : {
				text : json.title + '负面情报趋势'
			},
			xAxis : {
				type : 'datetime',
				maxPadding : 0.1,
				minPadding : 0.1,
				tickInterval : 24 * 3600 * 1000 * 1,// 1天画一个x刻度
				formatter : function() {
					return Highcharts.dateFormat('%m/%d', this.value);
				},
				// tickPixelInterval : 50,
				tickWidth : 3,// 刻度的宽度
				labels : {
					formatter : function() {
						return Highcharts.dateFormat('%m/%d', this.value);
					}
				}
			},

			yAxis : {
				title : {
					text : ''
				},
				minorGridLineWidth : 1,
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			legend : {
				layout : 'horizontal',
				align : 'center',
				verticalAlign : 'bottom',
				borderWidth : 0
			},
			series : datas
		});
		$('#fmqb_table').hide();
		$('#fmqb').show();
	} else {
		var html = '<div class="no_data_img">暂无数据</div>';
		$('#fmqb').hide();
		$('#fmqb_table').html(html);
		$('#fmqb_table').show();
	}
}

function discussJudge(brandId, param) {
	var url = getWebPath() + "/discuss/judge?brandId=" + brandId;
	if (param != null)
		url += param;
	var json = $.ecAjax.getReturnJson({
		url : url
	});
	// 最近7天评论数
	var datas = new Array();
	if (json.object && json.object.length > 0) {
		$.each(json.object, function(index, value) {
			var obj = new Object();
			obj.name = value.name;
			var obj2 = new Array();

			$.each(value.data, function(index, value) {
				var array = new Array();
				array.push(getUTC(value.name));
				array.push(value.value);
				obj2.push(array);
			});
			obj.data = obj2;
			datas.push(obj);
		});
		new Highcharts.Chart({
			// 常规图表选项设置
			chart : {
				renderTo : 'kbfx_qst', // 在哪个区域呈现，对应HTML中的一个元素ID
				backgroundColor : '#f0f0f0', // 绘图区的背景颜色
				plotBorderWidth : null, // 绘图区边框宽度
				plotShadow : true
			// 绘图区是否显示阴影
			},
			legend : {
				layout : 'horizontal',
				align : 'center',
				verticalAlign : 'bottom',
				borderWidth : 0
			},
			title : {
				text : json.title + '品牌对比趋势'
			},
			tooltip : {
				formatter : function() {
					return '<b>' + Highcharts.dateFormat('%Y/%m/%d', this.x)
							+ '</b><br/>' + '<b>' + this.series.name + ":</b>"
							+ this.y;
				}
			},
			xAxis : {
				type : 'datetime',
				maxPadding : 0.1,
				minPadding : 0.1,
				tickInterval : 24 * 3600 * 1000 * 1,// 1天画一个x刻度
				// tickPixelInterval : 50,
				tickWidth : 3,// 刻度的宽度
				labels : {
					formatter : function() {
						return Highcharts.dateFormat('%m/%d', this.value);
					}
				}
			},
			yAxis : {
				title : {
					text : ''
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			series : datas
		});
		$('#kbfx_qst_table').hide();
		$('#kbfx_qst').show();
	} else {
		var html = '<div class="no_data_img">暂无数据</div>';
		$('#kbfx_qst').hide();
		$('#kbfx_qst_table').html(html);
		$('#kbfx_qst_table').show();
	}
}
	// 用车询问
	function askChart(param) {
		var url = getWebPath() + "/question/chart?serialId="+param;
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
//					backgroundColor : '#f0f0f0' // 绘图区的背景颜色
				},
				title:{
					text:""
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
					title:"",
					labels : {
					    enabled:false,
						overflow : 'justify'
					}
				},
				legend : {
			               
					enabled : false
				},
				series : [ {
					name : '传播数',
					data : dadas,
					dataLabels : {
						enabled : true,
						color : 'black',
						rotation: -30,
						x : 1,
						y : -20,
						style : {
							fontSize : '13px',
							fontFamily : 'Verdana, sans-serif',
							textShadow : '0 0 3px #fff'
						}
					}
				} ]
				
			});
			$('#ask_chart_table').hide();
			$('#ask_chart').show();
		} else {
			var html = '<table><tr><td colspan="1" class="td_border">暂无数据</td></tr></table>';
			$('#ask_chart').hide();
			$('#ask_chart_table').html(html);
			$('#ask_chart_table').show();
		}
	}
