// 创建一个闭包  
(function($) {

	// 备份AJAX方法
	var _ajax = $.ajax;
	// 重写AJAX方法,防止请求时跳过权限拦截
	$.ajax = function(opt) {
		// 备份opt中error和success方法
		var fn = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			},
			success : function(data, textStatus) {
			}
		};
		if (opt.error) {
			fn.error = opt.error;
		}
		if (opt.success) {
			fn.success = opt.success;
		}

		// 扩展增强处理
		var _opt = $.extend(opt, {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				fn.error(XMLHttpRequest, textStatus, errorThrown);
			},
			success : function(data, textStatus) {
				if (data) {
					if (!data.success && data.unauthorized) {
						art.dialog.alert("无权限访问(" + data.requestUrl + "),请重新登录！");
						window.top.location.href = data.url;
					} else {
						fn.success(data, textStatus);
					}
				}
			}
		});
		_ajax(_opt);
	};

	// 表单序列化
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	// 获取选中的的数据
	$.fn.selectItems = function(options) {
		var $this = $(this), array = new Array(); // 用于保存 选中的那一条数据的ID
		if ($this.length > 0) {
			$this.each(function() {
				if ($(this).attr("checked")) // 判断是否选中
				{
					array.push($(this).val()); // 将选中的值 添加到 array中
				}
			});
		}
		return array;
	};

	// 打开加载提示
	var $loadBg = $(window.top.document).find("#load_back,#load");
	// 内部和外部都可以访问
	$.extend({
				// 打开预加载框
				loadOpen : function() {
					$loadBg.show();
				},
				// 关闭预加载框
				loadClose : function() {
					$loadBg.hide();
				},
				// 将数组按照逗号分割成字符串
				toArray : function(data) {
					var info = "";
					for ( var i = 0; i < data.length; i++) {
						// 如果i+1等于选项长度则取值后添加空字符串，否则为逗号
						info = (info + data[i])
								+ (((i + 1) == data.length) ? '' : ',');
					}
					return info;
				},
				// 刷新当前页面
				reloadFrame : function() {
					window.location.reload();
				}
			});

	// 所有的AJAX的方法
	$.ecAjax = {
		// 请求返回true还是false
		getReturn : function(options) {
			var settings = $.extend({
				async : false
			}, $.fn.ajax, options), flag = false;
			$.ajax({
				url : settings.url,
				type : settings.type,
				contentType : settings.contentType,
				data : settings.data,
				dataType : settings.dataType,
				async : settings.async,
				success : function(data) {
					if (data.obj > 0) {
						flag = true;
					}
				}
			});
			return flag;
		},
		// 请求返回JSON
		getReturnJson : function(options) {
			var settings = $.extend({
				async : false
			}, $.fn.ajax, options), json = null;
			$.ajax({
				url : settings.url,
				type : settings.type,
				contentType : settings.contentType,
				data : settings.data,
				dataType : settings.dataType,
				async : settings.async,
				success : function(data) {
					if (data) {
						json = data;
					}
				}
			});
			return json;
		}
	};

	// 调用的方法 $("#startTime,#endTime").datepickerStyle(); 单只有一个对象时则默认不比较时间
	$.fn.datepickerStyle = function(options) {
		var settings = $.extend({
			startYear : 30,
			endYear : 30,
			dateFormat : 'yy-mm-dd'
		}, options || {}), $startYear = new Date().getFullYear()
				- settings.startYear, $endYear = new Date().getFullYear()
				+ settings.endYear, $this = $(this);
		$this.eq(0).datepicker(
				{
					showButtonPanel : true,
					firstDay : 1,
					initStatus : '请选择日期',
					clearText : '清除',
					clearStatus : '清除已选日期',
					closeText : '关闭',
					closeStatus : '不改变当前选择',
					currentText : '今天',
					currentStatus : '显示本月',
					showMonthAfterYear : true, // 月在年之后显示
					changeMonth : true, // 允许选择月份
					changeYear : true, // 允许选择年份
					dateFormat : settings.dateFormat,
					yearRange : $startYear + ':' + $endYear,// 年份范围
					prevText : '上月',
					prevStatus : '显示上月',
					nextText : '下月',
					nextStatus : '显示下月',
					monthNamesShort : [ '01月', '02月', '03月', '04月',
							'05月', '06月', '07月', '08月', '09月',
							'10月', '11月', '12月' ],
					monthNames : [ '1月', '2月', '3月', '4月', '5月',
							'6月', '7月', '8月', '9月', '10月', '11月',
							'12月' ],
					dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四',
							'星期五', '星期六' ],
					dayNamesShort : [ '周日', '周一', '周二', '周三', '周四',
							'周五', '周六' ],
					dayNamesMin : [ '日', '一', '二', '三', '四', '五',
							'六' ],
					onClose : function(selectedDate) {
						
							$this.eq(1).datepicker("option",
									"minDate", selectedDate);
					}
				});
		$this.eq(1).datepicker(
				{
					showButtonPanel : true,
					firstDay : 1,
					initStatus : '请选择日期',
					clearText : '清除',
					clearStatus : '清除已选日期',
					closeText : '关闭',
					closeStatus : '不改变当前选择',
					currentText : '今天',
					currentStatus : '显示本月',
					showMonthAfterYear : true, // 月在年之后显示
					changeMonth : true, // 允许选择月份
					changeYear : true, // 允许选择年份
					dateFormat : settings.dateFormat,
					yearRange : $startYear + ':' + $endYear,// 年份范围
					prevText : '上月',
					prevStatus : '显示上月',
					nextText : '下月',
					nextStatus : '显示下月',
					monthNamesShort : [ '01月', '02月', '03月', '04月',
							'05月', '06月', '07月', '08月', '09月',
							'10月', '11月', '12月' ],
					monthNames : [ '1月', '2月', '3月', '4月', '5月',
							'6月', '7月', '8月', '9月', '10月', '11月',
							'12月' ],
					dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四',
							'星期五', '星期六' ],
					dayNamesShort : [ '周日', '周一', '周二', '周三', '周四',
							'周五', '周六' ],
					dayNamesMin : [ '日', '一', '二', '三', '四', '五',
							'六' ],
					onClose : function(selectedDate) {
						
							$this.eq(0).datepicker("option",
									"maxDate", selectedDate);
					}
				});

	};
	
	
	//$.fn.
	// 插件公共的AJAX方法的参数
	$.fn.ajax = {
		url : '', // 请求的地址
		type : 'GET', // 请求方式 ("POST" 或 "GET"),默认为 "GET
		contentType : 'application/json;charset=utf-8', // 请求的数据格式
		data : null, // 请求的参数
		dataType : 'json' // 返回数据的形式
	};

	// 插件公共的defaults
	$.fn.defaults = {};
	
	// 闭包结束
})(jQuery);
