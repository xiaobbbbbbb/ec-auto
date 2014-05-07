/*************************************************************************************************
 * @author zhanglu
 * 	该js分两部分:
 * 1，utils工具模块 (@util代表工具模块）
 * 2，业务功能模块 (@module代表功能模块）
 **************************************************************************************************/

/**
 * ===============================================================================================
 * @util 文本框文本域提示文字的自动显示与隐藏
 * ===============================================================================================
 */
(function($) {
	$.fn.textRemindAuto = function(options) {
		options = options || {};
		var defaults = {
			blurColor : "#999",
			focusColor : "#ffffff",
			auto : true,
			chgClass : ""
		};
		var settings = $.extend(defaults, options);
		$(this).each(function() {
			if (defaults.auto) {
				$(this).css("color", settings.blurColor);
			}
			var v = $.trim($(this).val());
			if (v) {
				$(this).focus(function() {
					if ($.trim($(this).val()) === v) {
						$(this).val("");
					}
					$(this).css("color", settings.focusColor);
					if (settings.chgClass) {
						$(this).toggleClass(settings.chgClass);
					}
				}).blur(function() {
					if ($.trim($(this).val()) === "") {
						$(this).val(v);
					}
					$(this).css("color", settings.blurColor);
					if (settings.chgClass) {
						$(this).toggleClass(settings.chgClass);
					}
				});
			}
		});
	};
})(jQuery);

/**
 * ===============================================================================================
 * @util 表单模块
 * ===============================================================================================
 */
var FormUtils = (function() {
	return {
		checkLength: function (str, minLen, maxLen) {
			 var len = str.length;
			 if (len>=minLen && len<=maxLen) {
				 return true;
			 } else {
				 return false;
			 }
		},
		checkLengthNotIgnoreCh: function (str, minLen, maxLen) {
			 var len = str.replace(/[^\x00-\xff]/g,"**").length;
			 alert(len);
			 if (len>=2*minLen && len<=2*maxLen) {
				 return true;
			 } else {
				 return false;
			 }
		},
		isPhoneNoEx: function(str) {
			var reg = /^\d(\d{3,11})\d$/;
			return reg.test(str);
		},
		isMobilePhone: function(str) {
			var reg = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
			return reg.test(str);
		},
		isTelPhone: function (str) {
			var reg = /^\d(\d{9}|\d{10})\d$/;
			return reg.test(str);
		},
		formParams2Json: function ($form) {
			var paramsArray = $form.serializeArray();
			var params = {};
			for (index in paramsArray) {
				params[paramsArray[index].name] = paramsArray[index].value;
			}
			return params;
		},

		formParams2Url: function ($form) {
			var paramsArray = $form.serializeArray();
			var params = "";
			for (index in paramsArray) {
				if (paramsArray[index].name && paramsArray[index].name != 'undefined') {
					params += (paramsArray[index].name + "=" + paramsArray[index].value + "&");
				};
			}
			return params.substring(0, params.length);
		},
		chromeYellowBgBugFix: function ($input) {
			$input.css("backgroundColor", "transparent");
			setTimeout(function() {
				$input.val('');
		  		try {  			   			
		  			 if (navigator.userAgent.toLowerCase().indexOf("chrome") >= 0) {   				 
		  				 $('input:-webkit-autofill').each(function(){
		  				   	$(this).after(this.outerHTML).remove();
		  				 });
		  			}
		  		} catch (e) {
		  			
		  		}   		
					
		  		$input.css("backgroundColor", "transparent");
		  	}, 50);
			setTimeout(function() {
				$input.val('');
				try {  			   			
					 if (navigator.userAgent.toLowerCase().indexOf("chrome") >= 0) {   				 
						 $('input:-webkit-autofill').each(function(){
						   	$(this).after(this.outerHTML).remove();
						 });
					}
				} catch (e) {
					
				}   		
				
				$input.css("backgroundColor", "transparent");
			}, 1000);	
		}
	};
	
})();

/**
 * ===============================================================================================
 * @utils Ajax模块
 * ===============================================================================================
 */
var AjaxUtils = (function () {
	return {
		ajaxFormSubmit: function ($form, postUrl, callback, errorCallback) {
			var paramsArray = $form.serializeArray();
			var params = {};
			for (index in paramsArray) {
				params[paramsArray[index].name] = paramsArray[index].value;
			}
			this.ajaxPost(postUrl, params, callback, errorCallback);
		},

		ajaxPost: function (url, params, callback, errorCallback) {
			$.post(url, params, function(data) {
				var json;
				try {
					json = $.parseJSON(data);					
				} catch (e) {
					if (errorCallback && typeof errorCallback == 'function') {
						errorCallback();
					} else {
						alert("系统错误");
					}
					$("#fivesaas_error_info").html(data + e);
				};
				if (json) {
					callback(json);
				}				
			});
		},
		
		ajaxHtml: function (url, params, callback) {
			$.post(url, params, function(data) {
				if (data.indexOf("ajaxErrorStatus")>0) {
					window.location.href = "/";
				} else {
					callback(data);
				}		
			});
		}
	};
})();

/**
 * ===============================================================================================
 * 数组扩展
 * ===============================================================================================
 */
Array.prototype.contains = function(elem) {
	for ( var i = 0; i < this.length; i++) {
		if (this[i] == elem) {
			return true;
		}
	}
	return false;
};
Array.prototype.posOf = function(o) {
	for ( var i = 0; i < this.length; i++) {
		if (this[i] == o) {
			return i;
		}
		return -1;
	}
};
Array.prototype.del = function(o) {
	var index = this.posOf(o);
	if (index != -1) {
		this.splice(index, 1);
	}
	return this;
};

/**
 * ===============================================================================================
 * date对象扩展
 * ===============================================================================================
 */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds() // millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};


 //取当前时间，格式为,yyyy-mm-dd hh:mm:ss
function GetDateYyyy_mm_dd() {
  var d,s;
  d = new Date();
  s = d.getFullYear() + "-";             //取年份
  var m = d.getMonth() + 1; 
  if (m<10) m = "0"+m; 
  s = s + (m) + "-";//取月份
  
  var date = d.getDate();         //取日期
  if (date<10) date = "0"+date;
  s = s+date;
 /* s += d.getHours() + ":";       //取小时
  s += d.getMinutes() + ":";    //取分
  s += d.getSeconds();         //取秒
*/  
  return s;
 } 

/**
 * 下载链接
 */
function appendSearchParamsBeforeDownload(downloadOnly) {	
	if (downloadOnly) {
		var paramUrl = FormUtils.formParams2Url($("#search_form"));
		var $download_link = $("#excel_download_link");
		var url = $download_link.attr("href") + "&" + paramUrl;
		$download_link.attr("href", url);		
	} else {
		var params = FormUtils.formParams2Json($("#search_form"));
		var $download_link = $("#excel_download_link");
		var url = $download_link.attr("href");
		AjaxUtils.ajaxPost(url, params, function(json) {
			$("#common_download_link").attr("href", "/stat/download_excel?excel_url=" + json.jsonData.download_link);
			$("#common_download_link").get(0).click();
		}, function() {
			alert("下载失败");
		});		
	}
}