
function getWebPath() {
	return $("#basePath").val();
}


function judge() {
	
	var eid= $("#eid").val();
	var sdate=$("#sdate").val();
	var edate= $("#edate").val();
	var url = getWebPath() + "/event/prChart?eid="+eid+"&sdate="+sdate+"&edate="+edate;
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
		plotOptions: {
			line:{
				dataLabels: {
                    enabled: true
                },
				events :{
					click:function(event){//传event参数进来兼容火狐等浏览器
//						var event = event || window.event;
						var date = new Date(parseInt(event.point.x));
						var dateStr = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
						
						artDialog({	
					            content:'确认设置公关结点吗？',
					            lock:true,
					            style:'succeed noClose'
					        },
					        function(){
					            var rdata=  $.ecAjax.getReturnJson({
								 	url:getWebPath()+"/event/addPrdate?eid="+eid+"&prDate="+dateStr
									 });
									if(rdata.success){var sdate=$("#sdate").val();
								var edate= $("#edate").val();
								var url1 = getWebPath() + "/event/prDate?eid="+eid+"&sdate="+sdate+"&edate="+edate;
								$.getJSON(url1,
									function(voData) {
											html='<b id="select_prDate" >公关时间</b><input id="prDate" type="hidden" value="0"/>';
											if(voData){
												$.each(voData,
													function(index, value) {	
															html+='<span class="info" id="prDate_'+value.id+' ">'+dateTostr(value.prDate)+'</span>';
													});
												
											}
											$(".frame-menu").empty().append(html);
											$(".nav3 span:last").css("border-bottom","1px solid #2980c4");
											$(".nav3 span").click(function(){
													$(".nav3 .info").hide();
													var eid = $("#eid").val();
													var cur = $(this).text();
													cur = (cur != null && cur.length > 0) ? cur : $("#select_prDate").text();
													$("#select_prDate").text(cur);
													$(".s_car").text(cur);
													url =  getWebPath() + "/event/getPrData?eid="+eid+"&prDate="+cur;
													var json = $.ecAjax.getReturnJson({
														url : url
													});
													$("#data1").text(json[0]);
													$("#data2").text(json[1]);
													$("#data3").text(json[2]);
													$("#data4").text(json[3]);
											});
								});
							}
									art.dialog.alert(rdata.msg);
					        },
					        function(){ }
						);
						 
					}
				}
			}
		},
		tooltip : {
//			useHTML:true,
			crosshairs: true,
			formatter : function() {
				var html= "";
				html+= "点击设置公关结点<br>评论:"+this.y;
				return html;
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
//	options.plotOptions[1].marker.symbol='square';
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

function dateTostr(dateTime){
	var date = new Date(dateTime);
	var month = "";
	var day = "";
	if(date.getMonth()<9){
		month = "0"+(date.getMonth()+1);
	}
	if(date.getDate()<10){
		day = "0"+date.getDate();
	}else{
		day = date.getDate();
	}
	return date.getFullYear()+"-"+month+"-"+day;
}

function changeData(id){
	$(".nav3 .info").hide();
	var eid = $("#eid").val();
	var cur = $("#prDate_" + id).text();
	cur = (cur != null && cur.length > 0) ? cur : $("#select_prDate").text();
	$("#select_prDate").text(cur);
	$("#prDate").val(cur);
	$(".s_car").text(cur);
	url =  getWebPath() + "/event/getPrData?eid="+eid+"&prDate="+cur;
	var json = $.ecAjax.getReturnJson({
		url : url
	});
	$("#data1").text(json[0]);
	$("#data2").text(json[1]);
	$("#data3").text(json[2]);
	$("#data4").text(json[3]);
 
	var url = getWebPath() + "/event/queryConcernNegativeList?eid="+eid+"&pageNum=1&dateStr="+cur;
	queryConcernNegativeList(url);
}

function sss(e){
	alert(e);
}

//仍需要关注的负面信息
function queryConcernNegativeList(url) {
		$.getJSON(
			url,
			function(voData) {
					var html = "";
				if (voData.list.length > 0) {	
					$.each(voData.list,
							function(index, value) {
								html +="<tr><td >"+(index+1)+"</td>";
								if(value[0].length>35){
									var substr=value[0].substring(0,35)+"...";
									html +="<td title="+value[0]+"><a target='_blank' href='"+value[5]+"' >"+substr+"</td>";
								}else{
									html +="<td ><a target='_blank' href='"+value[5]+"' >"+value[0]+"</a></td>";							
								}
								
								html +="<td >"+value[1]+"</td>";
								html +="<td >"+value[2]+"</td>";
								html +="<td >"+value[3]+"</td>";
								html +="<td >"+new Date(value[4]).format("yyyy年MM月dd日")+"</td>";
								html += "</tr>";
							});
					$("#concernNegativeDt").empty().append(html);
					$("#_articles").paginate({
						totalPage : voData.totalPage,
						currentPage : voData.currentPage,
						display		: 22,
						onLoad : function(page) {
							$('#pageNum').val(page);
							var eid = $("#eid").val();
							var cur = $("#prDate").val();
							var url = getWebPath() + "/event/queryConcernNegativeList?eid="+eid+"&pageNum="+page+"&dateStr="+cur;
							queryConcernNegativeList(url);
						}
					});
				}else{
					html += '<tr><td colspan="6" class="td_border">暂无数据</td></tr>';
					$("#concernNegativeDt").empty().append(html);
					$("#_articles").empty();
				}
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
	
	function validateDate(startTime,endTime){
		var tmp = startTime.split("-");
		var date1 = new Date(tmp[0],tmp[1]-1,tmp[2]);
		tmp = endTime.split("-");

		var date2 = new Date(tmp[0],tmp[1]-1,tmp[2]);
		if(date2.getTime() - date1.getTime() > 15 * 24 * 60 * 60 * 1000){
		        art.dialog.alert("时间间隔不能超过15天");
		        return true;
		}
		return false;
	}
