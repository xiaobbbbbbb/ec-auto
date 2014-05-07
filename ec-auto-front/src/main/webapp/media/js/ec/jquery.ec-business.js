// 创建一个闭包  
(function($) {
	// 公共的参数
	var basePath=$(window.top.document).find("#basePath").val();  //系统上下文
	art.dialog.removeData('existDepVal_'); //删除存在部门数据共享接口
	art.dialog.removeData('existRoleVal_'); //删除存在角色数据共享接口
	art.dialog.removeData('existBrandVal_'); //删除存在厂牌数据共享接口
	art.dialog.removeData('existSerialGroupVal_'); //删除存在车系分类数据共享接口
	art.dialog.removeData('existSerialVal_'); //删除存在车系数据共享接口
	art.dialog.removeData('existGroupVal_'); //删除存在集团数据共享接口
	art.dialog.removeData('existOrgVal_'); //删除存在服务机构数据共享接口
	art.dialog.removeData('existRalUserVal_'); //删除存在用户数据共享接口
	art.dialog.removeData('existOrgIds_');  //删除已经选择的发布服务机构
	
	
    //内部和外部都可以访问
	$.extend({
		//移除所有的选中
		removeCheck:function(options) {        
			var settings = $.extend({
				eachObj : $("input:checkbox")   //查找每一个对象
			},options||{});
			var objs=settings.eachObj;
			objs.each(function () {
				$(this).attr("checked", false);//选中或者取消选中
			}); 
		}
    });
	
	$.ec = {
		//下载
		download : function(options) {
			var settings = $.extend({
				url : '',        //下载地址
				param : null,      //下载中的参数
				formIdName : 'searchForm'  //检索的表单的id名称
			}, options || {}),
			urlParam=settings.param;
			if(urlParam==null)
			{
				var jsonInfo = $('#'+settings.formIdName).serialize(),
				urlParam = decodeURIComponent(jsonInfo,true),
		     	urlParam = jsonInfo.substring(1,jsonInfo.length-1);
			}
	     	var url = settings.url+"?"+urlParam;
	 		window.location.href=url;
		},
		//搜索页面
		searchUI : function(options) {
			var settings = $.extend({
				urlUI : '',    //打开UI的界面的地址
				urlList : '',  //搜索完成后查询加载的地址
				width : 480,
				height : 320,
				title : '',
				okVal : '立即检索',
				formName : 'form'  //序列化表单的ID名称
			}, options || {});
			var urlList=settings.urlList;
			art.dialog.open(settings.urlUI,{
				width : settings.width,
				id:10000,
				height : settings.height,
				title : settings.title,
				okVal : settings.okVal,
				ok : function() {
                    var $form = $(this.iframe.contentWindow.document);
					jsonInfo = $.toJSON($form.find('#'+settings.formName).serialize()), 
					urlParam = decodeURIComponent(jsonInfo,true),
					urlParam = urlParam.substring(1, urlParam.length - 1),
					url=urlList+"?"+urlParam;
					window.location.href = url;
				},
				cancel : true
			});
		},
		//选择部门页面
		selectDepUI : function(options) {
			var settings = $.extend({
				depId : 'depId',
				depName : 'depName'
			}, options || {}),
			$depId=$("#"+settings.depId).val(),
			$depName=$("#"+settings.depName).val(),
			url=basePath+"/department/selectDep";
			if($depId.length>0)
			{
				var dep= new Object();
				dep.id = $depId;
				dep.name = $depName;
				art.dialog.data('existDepVal_',dep);  //共享存在的部门数据
			}
			art.dialog.open(url, {
			width: 500,
			height: 340,
	        title: '选择用户所属部门',
		    ok: function () {
		    	var $checkedOption=$(this.iframe.contentWindow.document),
				$potion = $checkedOption.find("#checkedOption ul li"),
				select_text = "",
		    	select_val = "";
		    	$potion.each(function(){
		    		select_val=$(this).find(".pid").val();
		    		select_text=$(this).text();
				});
		    	if($.trim(select_text).length==0)
		    	{
		    		art.dialog.alert('请选择所属部门!');
		    		return false;
		    	}
		    	else
		        {
		    		$("#"+settings.depId).val(select_val);
			    	$("#"+settings.depName).val(select_text);
			    	return true;
		        }
		    },
		    cancel: true
	        });
		},
		//选择角色页面
		selectRoleUI : function(options) {
			var settings = $.extend({}, options || {}),
			$roleIds=$("#roleIds").val(),
			url=basePath+"/role/selectRole";
			if($roleIds.length>0)
			{
				art.dialog.data('existRoleVal_',$roleIds);  //共享存在的角色数据
			}
			art.dialog.open(url, {
			width: 500,
			height: 340,
            title: '选择用户角色',
		    ok: function () {
		    	var $checkedOption=$(this.iframe.contentWindow.document),
				$potion = $checkedOption.find("#checkedOption ul li"),
				select_text = "",
		    	select_val = "";
		    	$potion.each(function(){
		    		select_val +=$(this).find(".pid").val()+",";
		    		select_text +=$(this).text()+",";
				});
		    	if($.trim(select_text).length==0)
		    	{
		    		art.dialog.alert('请选择用户角色!');
		    		return false;
		    	}
		    	else
		        {
		    		select_text=select_text.substr(0,select_text.length-1);
		    		select_val=select_val.substr(0,select_val.length-1);
		    		$("#roleIds").val(select_val);
			    	$("#roleNames").val(select_text);
			    	return true;
		        }
		    },
		    cancel: true
            });
		},
		//选择用户页面
		selectUserUI : function(options) {
			var settings = $.extend({
				userId : 'userId',
				userName : 'userName'
			}, options || {}),
			$userId=$("#"+settings.userId).val(),
			$userName=$("#"+settings.userName).val(),
			url=basePath+"/user/selectUser";
			if($userId.length>0)
			{
				var user= new Object();
				user.id = $userId;
				user.name = $userName;
				art.dialog.data('existRalUserVal_',user);  //共享存在的用户数据
			}
			art.dialog.open(url, {
			width: 500,
			height: 340,
	        title: '选择用户',
		    ok: function () {
		    	var $checkedOption=$(this.iframe.contentWindow.document),
				$potion = $checkedOption.find("#checkedOption ul li"),
				select_text = "",
		    	select_val = "";
		    	$potion.each(function(){
		    		select_val=$(this).find(".pid").val();
		    		select_text=$(this).text();
				});
		    	if($.trim(select_text).length==0)
		    	{
		    		art.dialog.alert('请选择用户!');
		    		return false;
		    	}
		    	else
		        {
		    		$("#"+settings.userId).val(select_val);
			    	$("#"+settings.userName).val(select_text);
			    	return true;
		        }
		    },
		    cancel: true
	        });
		},
		//选择厂牌页面
		selectBrandUI : function(options) {
			var settings = $.extend({
				brandId : 'brandId',
				brandName : 'brandName',
				groupId : 'groupId',        //车系分类的名称，如果选择了厂牌则需要根据厂牌，检索车系分类名称
				groupName : 'groupName'
			}, options || {}),
			$brandId=$("#"+settings.brandId).val(),
			$brandName=$("#"+settings.brandName).val(),
			url=basePath+"/baseBrand/selectBrand";
			if($brandId.length>0)
			{
				var brand= new Object();
				brand.id = $brandId;
				brand.name = $brandName;
				art.dialog.data('existBrandVal_',brand);  //共享存在的厂牌数据
			}
			art.dialog.open(url, {
			width: 500,
			height: 340,
	        title: '选择所属车牌',
		    ok: function () {
		    	var $checkedOption=$(this.iframe.contentWindow.document),
				$potion = $checkedOption.find("#checkedOption ul li"),
				select_text = "",
		    	select_val = "";
		    	$potion.each(function(){
		    		select_val=$(this).find(".pid").val();
		    		select_text=$(this).text();
				});
		    	if($.trim(select_text).length==0)
		    	{
		    		art.dialog.alert('请选择所属车牌!');
		    		return false;
		    	}
		    	else
		        {
		    		$("#"+settings.brandId).val(select_val);
			    	$("#"+settings.brandName).val(select_text);
			    				
			    	if($brandId!=select_val)
			    	{
			    		if($("#"+settings.groupId).length>0)
			    		{
			    			$("#"+settings.groupId).val("");
					    	$("#"+settings.groupName).val("");
			    		}
			    	}
			    	return true;
		        }
		    },
		    cancel: true
	        });
		},
		//选择所属车系分类
		selectSerialGroupUI : function(options) {
			var settings = $.extend({
				brandId : 'brandId',     //如果有厂牌，则车系分类需要根据厂牌来检索
				serialGroupId : 'groupId',
				serialGroupName : 'groupName',
				serialId : 'serialId',    //车系的名称，如果选择了车系分类则需要根据车系分类，检索车系名称
				serialName : 'serialName'
			}, options || {}),
			$serialGroupId=$("#"+settings.serialGroupId).val(),
			$serialGroupName=$("#"+settings.serialGroupName).val(),
			url=basePath+"/baseSerialGroup/selectSerialGroup";
			if($serialGroupId.length>0)
			{
				var serialGroup= new Object();
				serialGroup.id = $serialGroupId;
				serialGroup.name = $serialGroupName;
				art.dialog.data('existSerialGroupVal_',serialGroup);  //共享存在的车系分类数据
			}
			var $brandId=$("#"+settings.brandId),
			    $brandIdVal=$brandId.val();
			if($brandId.length>0)
    		{
				if($brandIdVal.length==0)
				{
					art.dialog.alert('请先选择所属厂牌!');
		    		return false;
				}
				url+="?brandId="+$brandId.val();
    		}
			art.dialog.open(url, {
			width: 500,
			height: 340,
	        title: '选择所属车系分类',
		    ok: function () {
		    	var $checkedOption=$(this.iframe.contentWindow.document),
				$potion = $checkedOption.find("#checkedOption ul li"),
				select_text = "",
		    	select_val = "";
		    	$potion.each(function(){
		    		select_val=$(this).find(".pid").val();
		    		select_text=$(this).text();
				});
		    	if($.trim(select_text).length==0)
		    	{
		    		art.dialog.alert('请选择所属车系分类!');
		    		return false;
		    	}
		    	else
		        {
		    		$("#"+settings.serialGroupId).val(select_val);
			    	$("#"+settings.serialGroupName).val(select_text);
			    	
			    	if($serialGroupId!=select_val)
			    	{
			    		if($("#"+settings.serialId).length>0)
			    		{
			    			$("#"+settings.serialId).val("");
					    	$("#"+settings.serialName).val("");
			    		}
			    	}
			    	return true;
		        }
		    },
		    cancel: true
	        });
		},
		//选择所属车系
		selectSerialUI : function(options) {
			var settings = $.extend({
				groupId : 'groupId',  //如果有车系分类，则车系需要根据车系分类来检索
				serialId : 'serialId',
				serialName : 'serialName'
			}, options || {}),
			$serialId=$("#"+settings.serialId).val(),
			$serialName=$("#"+settings.serialName).val(),
			url=basePath+"/baseSerial/selectSerial";
			if($serialId.length>0)
			{
				var serial= new Object();
				serial.id = $serialId;
				serial.name = $serialName;
				art.dialog.data('existSerialVal_',serial);  //共享存在的部门数据
			}
			var $groupId=$("#"+settings.groupId),
		    $groupIdVal=$groupId.val();
			if($groupId.length>0)
			{
				if($groupIdVal.length==0)
				{
					art.dialog.alert('请先选择所属车系分类!');
		    		return false;
				}
				url+="?groupId="+$groupId.val();
			}
			art.dialog.open(url, {
			width: 500,
			height: 340,
	        title: '选择所属车系',
		    ok: function () {
		    	var $checkedOption=$(this.iframe.contentWindow.document),
				$potion = $checkedOption.find("#checkedOption ul li"),
				select_text = "",
		    	select_val = "";
		    	$potion.each(function(){
		    		select_val=$(this).find(".pid").val();
		    		select_text=$(this).text();
				});
		    	if($.trim(select_text).length==0)
		    	{
		    		art.dialog.alert('请选择所属车系!');
		    		return false;
		    	}
		    	else
		        {
		    		$("#"+settings.serialId).val(select_val);
			    	$("#"+settings.serialName).val(select_text);
			    	return true;
		        }
		    },
		    cancel: true
	        });
		},
		//选择车辆条件
		selectCarCriteriasUI : function(options) {
			var settings = $.extend({}, options || {}),
			url=basePath+"/baseModel/selectModel";
			art.dialog.open(url, {
			width: 600,
			height: 460,
	        title: '选择发布条件',
		    ok: function () {
		    	var iframeObj = this.iframe.contentWindow,
		    	treeObj=iframeObj.getZtreeData();	    	
		    	if(treeObj.brandIds.length==0)
		    	{
		    		art.dialog.alert('请选择发布条件!');
		    		return false;
		    	}
		    	else
		        {
		    		$("#carCriterias").val(treeObj.carCriterias);
			    	$("#brandIds").val(treeObj.brandIds);
			     	$("#serialGroupIds").val(treeObj.serialGroupIds);
			     	$("#serialIds").val(treeObj.serialIds);
			     	$("#modelIds").val(treeObj.modelIds);
			    	return true;
		        }
		    },
		    cancel: true
	        });
		},
		//选择所属集团
		selectGroupUI : function(options) {
			var settings = $.extend({
				groupId : 'groupId',    
				groupName : 'groupName',
				groupCode : 'groupCode',	
				orgId : 'orgId',          
				orgName : 'orgName',
				orgCode : 'orgCode'
			}, options || {}),
			$groupId=$("#"+settings.groupId).val(),
			$groupName=$("#"+settings.groupName).val(),
			$groupCode=$("#"+settings.groupCode).val(),
			url=basePath+"/group/selectGroup";
			if($groupId.length>0)
			{
				var group= new Object();
				group.id = $groupId;
				group.name = $groupName;
				group.code = $groupCode;
				art.dialog.data('existGroupVal_',group);  //共享存在的集团数据
			}
			art.dialog.open(url, {
			width: 500,
			height: 340,
	        title: '选择所属集团',
		    ok: function () {
		    	var $checkedOption=$(this.iframe.contentWindow.document),
				$potion = $checkedOption.find("#checkedOption ul li"),
				select_text = "",
		    	select_val = "",
		    	select_pcode = "";
		    	$potion.each(function(){
		    		select_val=$(this).find(".pid").val();
		    		select_pcode=$.trim($(this).find(".pcode").val());
		    		select_text=$(this).text();
				});
		    	if($.trim(select_text).length==0)
		    	{
		    		art.dialog.alert('请选择所属集团!');
		    		return false;
		    	}
		    	else
		        {
		    		$("#"+settings.groupId).val(select_val);
			    	$("#"+settings.groupName).val(select_text);
			    	$("#"+settings.groupCode).val(select_pcode);
			    				
			    	if($groupId!=select_val)
			    	{
			    		if($("#"+settings.orgId).length>0)
			    		{
			    			$("#"+settings.orgId).val("");
					    	$("#"+settings.orgName).val("");
					    	$("#"+settings.orgCode).val("");
			    		}
			    	}
			    	return true;
		        }
		    },
		    cancel: true
	        });
		},
		//选择所属服务机构
		selectOrgUI : function(options) {
			var settings = $.extend({
				multipleSelect : false,  //是否容许选中多个服务机构
				selectFlag : false,  //是否容许选择为空
				groupId : 'groupId',     //如果有集团，则车系需要根据集团来检索
				orgId : 'orgId',
				orgName : 'orgName',
				orgCode : 'orgCode'
			}, options || {}),
			$orgId=$("#"+settings.orgId).val(),
			$orgName=$("#"+settings.orgName).val(),
			$orgCode=$("#"+settings.orgCode).val(),
			url=basePath+"/serviceOrg/selectOrg?multipleSelect="+settings.multipleSelect;
			if($orgId.length>0)
			{
				var orgs = new Object(),
				orgIds_ = new Array(),
				orgNames_ = new Array(),
				orgCodes_ = new Array();
				orgIds_=$orgId.split(',');//注split可以用字符或字符串分割
				orgNames_=$orgName.split(',');//注split可以用字符或字符串分割
				orgCodes_=$orgCode.split(',');//注split可以用字符或字符串分割
								
				orgs.ids = orgIds_;
				orgs.names = orgNames_;
				orgs.codes = orgCodes_;
				art.dialog.data('existOrgVal_',orgs);  //共享存在的服务机构数据
			}
			var $groupId=$("#"+settings.groupId),
		    $groupIdVal=$groupId.val();
			if($groupId.length>0)
			{
				if($groupIdVal.length==0)
				{
					art.dialog.alert('请先选择所属集团!');
		    		return false;
				}
				url+="&groupId="+$groupId.val();
			}
			art.dialog.open(url, {
			width: 500,
			height: 340,
	        title: '选择所属服务机构',
		    ok: function () {
		    	var $checkedOption=$(this.iframe.contentWindow.document),
				$potion = $checkedOption.find("#checkedOption ul li"),
		    	orgIds = new Array(),
		    	orgNames = new Array(),
		    	orgCodes = new Array();
		    	$potion.each(function(){
		    		orgIds.push($(this).find(".pid").val()); 
		    		orgNames.push($(this).text()); 
		    		orgCodes.push($(this).find(".pcode").val()); 
				});
		    	if(orgIds.length==0)
		    	{
		    		if(settings.selectFlag)
		    		{
		    			$("#"+settings.orgId).val("");
				    	$("#"+settings.orgName).val("");
				    	$("#"+settings.orgCode).val("");
				    	art.dialog.removeData('existOrgVal_'); //删除存在的服务机构数据
		    			return true;
		    		}
		    		else
		    		{
		    			art.dialog.alert('请选择所属服务机构!');
			    		return false;
		    		}
		    	}
		    	else
		        {
		    		art.dialog.data('existOrgIds_',orgIds);  //共享已经选择的发布服务机构
		    		$("#"+settings.orgId).val(orgIds);
			    	$("#"+settings.orgName).val(orgNames);
			    	$("#"+settings.orgCode).val(orgCodes);
			    	return true;
		        }
		    },
		    cancel: true
	        });
		},
		//导航栏当前位置
		thisPosition : function(options) {
			var settings = $.extend({
				menuDispaly: true,
				text : '',
				url : '',
				idName : '',
				positionObj: parent.topFrame.document,
				currentPositionId : 'currentPosition',
				mainFrame : window.parent.mainFrame
			}, options || {});
			$.loadOpen();
			if(settings.menuDispaly)
			{
				$("#menu_btn div").removeClass("menu_hover");
				$("#"+settings.idName+"_btn").addClass("menu_hover");
				loadShowMenu(settings.idName);
			}
			$(settings.positionObj).find("#"+settings.currentPositionId).html("当前位置：" + settings.text);
			settings.mainFrame.location.href = settings.url;
		},
		//首页的左边导航按钮的样式
		menuHover: function(options)
		{
			var settings = $.extend({
				clickObj : ".menu_item div",       //点击的对象
				currentClass : "item_current",     //当前的样式
				hoverClass : "item_hover",         //鼠标进过时的样式
				callback: null                     //点击时的回调
			}, options || {});
			$(settings.clickObj).click(function(){
				$(settings.clickObj).removeClass(settings.currentClass);
				$(this).addClass(settings.currentClass);
				if (settings.callback) {
					settings.callback();
				}
			});
			if(settings.hoverClass!=null)
			{
				$(settings.clickObj).hover(function(){
					$(this).addClass(settings.hoverClass);
				}, function() {
					$(this).removeClass(settings.hoverClass);
				});
			}
		},
		//提交表单(多用于添加和修改的表单提交)
		ajaxSubmitForm : function(options) {
			var settings = $.extend({
				url : null,         //提交的地址
				callback : reloadFrame,    //提交成功后的回调
			    data : $.toJSON($('#form').serializeObject())   //提交的数据
			}, options || {});
			$.ecAjax.submit({
				url : settings.url,
				type : 'POST',
				data : settings.data,
				callback : settings.callback
			});
		},
		//删除数据
		deleteData : function(options) {
			var settings = $.extend({
				checkUrl : null,         //检查数据的地址,如果该地址为空,则表示删除数据前不做删除数据的验证
				deleteUrl : null,        //删除数据的地址
			    param : null,            //提交的参数
				deleteMsg : null,        //删除数据失败后的提示
			    callback : reloadFrame,  //提交成功后的回调
			    isArray : false          //是否要验证 是否选中了一条记录,因为在zTree中不需要验证,该种情况只在树的时候使用
			}, options || {});
			var array=$("input[name='cid']:checkbox").selectItems(),
		    ids = array.toString();
		    if(array.length>0 || settings.isArray)
		    {
		    	art.dialog.confirm("确实要将选中的数据删除吗?", function(){
		    		var param=null,
		    		flag=false;
		    		if(settings.param!=null)
		    		{
		    			param=settings.param;
		    		}
		    		else
		    		{
		    			param={"ids":ids};
		    		}
		    		if(settings.checkUrl!=null)
		    		{
						flag=$.ecAjax.getReturn({
							url : settings.checkUrl,
							data : param
						});
		    		}
					if(!flag)
                    {
			    		$.ecAjax.submit({
							url : settings.deleteUrl,
							type : 'POST',
							data : param,
							contentType : 'application/x-www-form-urlencoded',   //请求的数据格式
							callback : settings.callback
						});
                    }
					else
					{
						art.dialog.alert(settings.deleteMsg);
					}
			    });
		    }
		    else
		    {
		    	art.dialog.alert("请至少选择一条记录!");
		    }
		},
		//导入数据
		importData : function(options) {
			var settings = $.extend({
				checkUrl : null,         //检查数据的地址,如果该地址为空,则表示删除数据前不做删除数据的验证
				importUrl : null,        //删除数据的地址
			    param : null,            //提交的参数
				deleteMsg : null,        //删除数据失败后的提示
			    callback : reloadFrame,  //提交成功后的回调
			    isArray : false          //是否要验证 是否选中了一条记录,因为在zTree中不需要验证,该种情况只在树的时候使用
			}, options || {});
			var array=$("input[name='cid']:checkbox").selectItems(),
		    ids = array.toString();
		    if(array.length>0 || settings.isArray)
		    {
		    	art.dialog.confirm("确定要导入为正式数据吗?", function(){
		    		var param=null,
		    		flag=false;
		    		if(settings.param!=null)
		    		{
		    			param=settings.param;
		    		}
		    		else
		    		{
		    			param={"ids":ids};
		    		}
		    		if(settings.checkUrl!=null)
		    		{
						flag=$.ecAjax.getReturn({
							url : settings.checkUrl,
							data : param
						});
		    		}
					if(!flag)
                    {
			    		$.ecAjax.submit({
							url : settings.importUrl,
							type : 'POST',
							data : param,
							contentType : 'application/x-www-form-urlencoded',   //请求的数据格式
							callback : settings.callback
						});
                    }
					else
					{
						art.dialog.alert(settings.deleteMsg);
					}
			    });
		    }
		    else
		    {
		    	art.dialog.alert("请至少选择一条记录!");
		    }
		},
		//请求数据
		ajaxFormData : function(options) {
			var settings = $.extend({
				contentType : 'application/x-www-form-urlencoded',   //请求的数据格式
				url : null,               //请求数据的地址
			    param : null,             //提交的参数
			    callback : reloadFrame    //提交成功后的回调
			}, options || {});
			$.ecAjax.submit({
				url : settings.url,
				type : 'POST',
				data : settings.param,
				dataType : 'json',
				callback : settings.callback,
				contentType : settings.contentType   //请求的数据格式
			});
		}
	};

	//刷新当前页面
	function reloadFrame()
	{
		window.location.reload();
	}
	
	//根据HTML来显示相应的菜单项
	function loadShowMenu(idName)
	{
		var $dom=$(window.parent.leftFrame.document);
		//隐藏所有菜单
		$dom.find("#item_panl .menu_item").css("display","none");
		//给id加上特殊字符，复制id重复
		var pwdIdName=idName+"_menu";
		//显示当前的菜单
		$dom.find("#"+pwdIdName).css("display","block");
		$dom.find("div").removeClass("item_current");
		$dom.find("#"+pwdIdName).find("div:first").addClass("item_current");
	}
	
	//上传文件
	$.fn.uploadFile = function(options) {
		var settings = $.extend({
			auto          : true,                  //是否自动提交
			fileTypeDesc  : '请选择文件',
            fileTypeExts  : '*',
            uploader      : null,                  //提交的地址,
            onDialogClose : null,
            onDialogOpen  : null,
            onUploadSuccess : null,                 //上传成功后的回调
            onCancel  : null,                       //当点击文件队列中文件的关闭按钮或点击取消上传时触发            
            onSelect  :null                         //选择上传文件后调用
		}, options || {}),
		$this=$(this);
		$this.uploadify(
		{
	    	 'buttonText'    : '选择文件',
	    	 'auto'          : settings.auto,
	    	 'height'        : 20,
	    	 'width'         : 80,
	    	 'removeTimeout' : 1,
	    	 'queueSizeLimit': 1,   //队列中允许的最大文件数目
	    	 'queueID'       : 'fileQueue',
	    	 'multi'         : false,
	    	 'fileTypeDesc'  : settings.fileTypeDesc,
             'fileTypeExts'  : settings.fileTypeExts, 
	    	 'swf'           : basePath+'/js/uploadify3.1/uploadify.swf',
		     'uploader'      : settings.uploader, 
		     'onDialogClose' : function(queueData) {
		    	   if (settings.onDialogClose) {
						settings.onDialogClose(queueData);
				   }
		     },
		     'onDialogOpen'  : function(queueData) {
		    	   if (settings.onDialogOpen) {
						settings.onDialogOpen(queueData);
				   }
		     },
             'onUploadSuccess' : function(file, data, response){
            	   if (settings.onUploadSuccess) {
                	    data=eval("("+data+")");
						settings.onUploadSuccess(data);
				   }
	         },
	         'onSelect' : function(file){
		      	   if (settings.onSelect) {
						settings.onSelect(file);
				   }
	         },
	         'onCancel' : function(event,ID,fileObj,data){
		      	   if (settings.onCancel) {
						settings.onCancel(event,ID,fileObj,data);
				   }
	         }
		 });
	};
	
	// 调用的方法 $("#startTime,#endTime").datepickerStyle();  单只有一个对象时则默认不比较时间
	$.fn.datepickerStyle = function(options) {
		var settings = $.extend({
			startYear : 30,
		    endYear : 30,
		    dateFormat :'yy-mm-dd'
		},options||{}),
		$startYear = new Date().getFullYear() - settings.startYear,
		$endYear = new Date().getFullYear() + settings.endYear,
		$this=$(this);
		$this.each(function(i){
			$this.eq(i).datepicker({
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
					yearRange : $startYear + ':' + $endYear,//年份范围
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
						if($this.size()>1)  //有两个比较的时间
						{
							$this.eq(1).datepicker("option", "minDate",selectedDate);
							$this.eq(0).datepicker("option", "maxDate",selectedDate);
						}
					}
		       });
		});
			
	};


	//上传文件
	$.fn.uploadFile = function(options) {
		var settings = $.extend({
			auto          : true,                  //是否自动提交
			fileTypeDesc  : '请选择文件',
            fileTypeExts  : '*',
            uploader      : null,                  //提交的地址,
            onDialogClose : null,
            onDialogOpen  : null,
            onUploadSuccess : null,                 //上传成功后的回调
            onCancel  : null,                       //当点击文件队列中文件的关闭按钮或点击取消上传时触发            
            onSelect  :null                         //选择上传文件后调用
		}, options || {}),
		$this=$(this);
		$this.uploadify(
		{
	    	 'buttonText'    : '选择文件',
	    	 'auto'          : settings.auto,
	    	 'height'        : 20,
	    	 'width'         : 80,
	    	 'removeTimeout' : 1,
	    	 'queueSizeLimit': 1,   //队列中允许的最大文件数目
	    	 'queueID'       : 'fileQueue',
	    	 'multi'         : false,
	    	 'fileTypeDesc'  : settings.fileTypeDesc,
             'fileTypeExts'  : settings.fileTypeExts, 
	    	 'swf'           : basePath+'/media/js/uploadify3.1/uploadify.swf',
		     'uploader'      : settings.uploader, 
		     'onDialogClose' : function(queueData) {
		    	   if (settings.onDialogClose) {
						settings.onDialogClose(queueData);
				   }
		     },
		     'onDialogOpen'  : function(queueData) {
		    	   if (settings.onDialogOpen) {
						settings.onDialogOpen(queueData);
				   }
		     },
             'onUploadSuccess' : function(file, data, response){
            	   if (settings.onUploadSuccess) {
                	    data=eval("("+data+")");
						settings.onUploadSuccess(data);
				   }
	         },
	         'onSelect' : function(file){
		      	   if (settings.onSelect) {
						settings.onSelect(file);
				   }
	         },
	         'onCancel' : function(event,ID,fileObj,data){
		      	   if (settings.onCancel) {
						settings.onCancel(event,ID,fileObj,data);
				   }
	         },
	         'onSelectError': function(file, errorCode, errorMsg) {
                 switch (errorCode) {
                    case - 100 : 
                      alert("上传的文件数量已经超出系统限制的" + $('#update_${dto.fieldName}').uploadify('settings', 'queueSizeLimit') + "个文件！");
                      break;
                    case - 110 : 
                      alert("文件 [" + file.name + "] 大小超出系统限制的" + $('#update_${dto.fieldName}').uploadify('settings', 'fileSizeLimit') + "大小！");
                      break;
                    case - 120 : 
                      alert("文件 [" + file.name + "] 大小异常！");
                      break;
                    case - 130 : 
                      tip("文件 [" + file.name + "] 类型不正确！");
                      break;
                 }
             }

		 });
	};
	// 闭包结束
})(jQuery);
