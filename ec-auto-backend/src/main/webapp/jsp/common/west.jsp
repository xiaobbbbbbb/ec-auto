<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div data-options="region:'west',split:true,title:'菜单导航'" style="width:150px;">
			<div id="menus" class="easyui-accordion" data-options="fit:true,border:false">
				<div title="前台用户" style="padding:10px;" ${menu_prefix=='user'?'data-options="selected:true"':'' }>
					<a href="/user/index">用户列表</a>
				</div>
				<div title="车友评价" style="padding:10px;" ${menu_prefix=='article'?'data-options="selected:true"':'' }>
					<a href="/article/index">车友评价列表</a>
				</div>
				<div title="动态新闻" style="padding:10px" ${menu_prefix=='industry_article'?'data-options="selected:true"':'' }>
					<a href="/industry_article/index">动态新闻列表</a>
				</div>
				<div title="数据字典" style="padding:10px" ${menu_prefix=='dict'?'data-options="selected:true"':'' }>
					<ul>
						<li><a href="/viewpoint/index">观点字典</a></li>
						<li><a href="/carbrand/index">厂牌字典</a></li>
						<li><a href="/carserial/index">车系字典</a></li>
					</ul>
					<!--  
					<a href="/dict/area/index">区域</a>
					<a href="/dict/city/index">城市</a>
					<a href="/dict/province/index">省份</a>
					<a href="/dict/car_brand/index">车牌</a>
					<a href="/dict/car_serial/index">车系</a>
					-->
				</div>
			</div>
</div>
