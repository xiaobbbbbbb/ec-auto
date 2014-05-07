<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="base.jsp"%>
<input type="hidden" id="basePath" value="${ctx}"/>
<link href="${ctx}/media/css/style.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}/media/js/top.js"></script>
<div class="top">
<input type="hidden" id="hidden" value="${ctx}" />
	<div class="top_head">
		<img src="${ctx}/media/images/logo_index.png" />
		<div class="top_head_right">
			<a id="set" href="javascript:void(0);"></a>
			<a id="message" href="javascript:void(0);"></a>
			<a id="report" href="javascript:void(0);"></a>
			<a id="user_name" >
				欢迎您，${user.name}
			</a>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>

	<div class="top_centent">
		<div class="top_centent_top">
			<div class="top_centent_top_bg">
				<div class="clear"></div>
				<div class="top_centent_absoulte_div">
					<div class="top_centent_absoulte">
						<div class="absoulte_title_div">
							<div id="absoulte_title" >
							<img src="${ctx}/media/images/bj_logo.gif" />
							情报预警</div>
							<div id="absoulte_close" ></div>
							<div class="clear" ></div>
						</div>
					</div>

					<div class="top_centent_ranking">
						<div class="ranking_title_br"></div>
						<div class="ranking_title_div">
							<div class="ranking_title" >热搜汽车风云榜</div>
							<a target="_blank" href="http://top.baidu.com/category?c=18" class="ranking_more" >更多>></a>
							<div class="clear" ></div>
						</div>
						<div class="ranking_title_thead">
							<div class="thead_td_1" >排名</div>
							<div class="thead_td_2" >品牌</div>
							<div class="thead_td_3" >搜索指数</div>
							<div class="clear" ></div>
						</div>
						
					</div>
					
					<div class="top_centent_set">
						<div class="top_centent_set_a_title_br"></div>
						<a class="top_centent_set_a" href="javascript:updatePassWd();" >
							修改密码
						</a>
						<shiro:hasPermission name="/backend/back_index">
			 				<a  class="top_centent_set_a" target="_blank"  href="${ctx}/rgroup/list" >
							后台管理
							</a>
                		</shiro:hasPermission>
						
						<a class="top_centent_set_a" href="${ctx}/eclogin/exit" >
							退出登录
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="top_centent_bottom">
			<div class="top_centent_bottom_index">
				<a href="${ctx}/index_new/">首页</a>
				<shiro:hasPermission name="/mouth/index">
			 		<a href="${ctx}/mouth/index">用户口碑</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="/question/index">
			 		<a href="${ctx}/question/index">需求研究</a>
                </shiro:hasPermission>
				<a title="mediaanalyze" href="${ctx}/mediaanalyze/index">媒体分析</a>
				<a  >广宣监督</a>
				<a href="${ctx}/positive/index">负面信息</a>
				<a title="event" href="${ctx}/event/index">事件追踪</a>
				<a href="${ctx}/important/index">重点新闻</a>
				<div class="clear"></div>
			</div>
		</div>
	</div>
</div>

