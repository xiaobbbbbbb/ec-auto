<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/media/js/paginate/css/style.css" media="screen"/>
<link href="${ctx}/media/css/ask.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>
 
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"></script>

<script type="text/javascript" src="${ctx}/media/js/ec/ec_business.js"></script>

<script type="text/javascript">
	 
</script>

</head>
<body>
	<center>
		<jsp:include page="../common/head.jsp" />

		<div class="all">
			<div class="detail_left">
				<div class="detail_left_index">
					<a href="javascript:void();" style="color: #ffffff; background-image: url('../media/images/detail_left_as.png');">用车询问</a>
				</div>
			</div>
			<div class="detail_right">	
				<div class="detail_right_search">			
					<table id="search_table">
						<tr>
							<td class="td_border" colspan="2" ><div class="search_tag">筛选</div></td>
						</tr>
						<tr>
							<td class="td_no_border_right" valign="top"  style="width: 100px;"><div class="search_tag">时间：</div></td>
							<td class="td_border_left" id="a_time">
								<span>上周<input type="hidden" value="0" /></span> 
								<span class="search_a">本周<input type="hidden" value="-1" /></span> 
								<span>上月<input 	type="hidden" value="7" /></span>
								<span>本月<input type="hidden" value="15" /></span> 
								<span>本年<input type="hidden" value="30" /></span> 
								<span class="custom">自定义时间<input type="hidden" value="1" /></span>
								<div class="clear"></div>
								<div id="search_time" style="display: none;">
									开始时间：<input type="text" readonly="readonly" name="startTime" id="startTime" />&nbsp;&nbsp;&nbsp;结束时间：<input type="text" readonly="readonly"
										name="endTime" id="endTime" />
								</div> 
								<input type="hidden" id="time" value="7" />
								<input type="hidden" id="offset" value="1" />
								<input type="hidden" id="provinceId" />
								<input type="hidden" id="area" />
							</td>
						</tr>
						<tr>
							<td class="td_no_border_right" valign="top"  ><div class="search_tag">品牌：</div></td>
							<td class="td_border_left" id="a_brand">
								<span title="全部"  class="search_a">全部<input type="hidden" value="0" /></span>
								<span title="东风悦达起亚">东风悦达起亚<input type="hidden" value="0" /></span>
								<span title="东风日产">东风日产<input type="hidden" value="0" /></span>
								<span title="北京现代">北京现代<input type="hidden" value="0" /></span>
								<span title="一汽丰田">一汽丰田<input type="hidden" value="0" /></span>
								<span title="广汽丰田">广汽丰田<input type="hidden" value="0" /></span>
								<div class="clear"></div>
							</td>
						</tr>
						<tr>
							<td class="td_border" colspan="2" ><div id="search_btn">立即筛选</div></td>
						</tr>
					</table>
				</div>
				
				<div class="detail_right_content_boder">
					<div class="detail_right_title">
						<span class="search_title">问题分析</span>
					</div>
					<div class="detail_right_content_img" style="height: 290px">
					 	<div id="ask_temp_left" class="ask_kbfx_qst">
						   <img src="../media/images/ask_bt_char.png" alt="" width="295px" height="295px" />
						</div>
						<div class="ask_kbfx_qttj">
							<table cellpadding="0" cellspacing="0" width="100%" >
								<tr>
									<th class="ask_th_border" >问题分类</th>
									<th class="ask_th_border" >东风悦达起亚</th>
									<th class="ask_th_border" >东风日产</th>
									<th class="ask_th_border" >北京现代</th>
									<th class="ask_th_border" >一汽丰田</th>
									<th class="ask_th_border" >广汽本田</th>
								</tr>
								<tbody>
									<tr>
										<td class="ask_td_border" >机油使用</td>
										<td class="ask_td_border" >28</td>
										<td class="ask_td_border" >23</td>
										<td class="ask_td_border" >18</td>
										<td class="ask_td_border" >14</td>
										<td class="ask_td_border" >18</td>
									</tr>
									<tr>
										<td class="ask_td_border" >车辆油耗</td>
										<td class="ask_td_border" >34</td>
										<td class="ask_td_border" >23</td>
										<td class="ask_td_border" >38</td>
										<td class="ask_td_border" >23</td>
										<td class="ask_td_border" >28</td>
									</tr>
									<tr>
										<td class="ask_td_border" >车辆保养</td>
										<td class="ask_td_border" >21</td>
										<td class="ask_td_border" >21</td>
										<td class="ask_td_border" >23</td>
										<td class="ask_td_border" >34</td>
										<td class="ask_td_border" >38</td>
									</tr>
									<tr>	
										<td class="ask_td_border" >异响抖动</td>
										<td class="ask_td_border" >19</td>
										<td class="ask_td_border" >24</td>
										<td class="ask_td_border" >31</td>
										<td class="ask_td_border" >21</td>
										<td class="ask_td_border" >10</td>
									</tr>
									<tr>
										<td class="ask_td_border" >车辆改装</td>
										<td class="ask_td_border" >7</td>
										<td class="ask_td_border" >6</td>
										<td class="ask_td_border" >5</td>
										<td class="ask_td_border" >8</td>
										<td class="ask_td_border" >9</td>
									</tr>
									<tr>
										<td class="ask_td_border" >车色选择</td>
										<td class="ask_td_border" >6</td>
										<td class="ask_td_border" >5</td>
										<td class="ask_td_border" >3</td>
										<td class="ask_td_border" >6</td>
										<td class="ask_td_border" >4</td>
									</tr>
									<tr>
										<td class="ask_td_border" >其他</td>
										<td class="ask_td_border" >29</td>
										<td class="ask_td_border" >32</td>
										<td class="ask_td_border" >18</td>
										<td class="ask_td_border" >12</td>
										<td class="ask_td_border" >32</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="clear"></div>
					</div>
				</div>
				
				<div class="detail_right_content_table">
					<table >
						<tr>
							<td class="ask_table_action_blue">&lt;&lt;</td>
							<td class="ask_table_checked_oragin_title">车辆保养</td>
							<td class="ask_table_normal_title">机油使用</td>
							<td class="ask_table_normal_title">异响抖动</td>
							<td class="ask_table_normal_title">车辆油耗</td>
							<td class="ask_table_normal_title">车色选择</td>
							<td class="ask_table_normal_title">车辆改装</td>
							<td class="ask_table_action_blue ask_table_action_blues" >&gt;&gt;</td>
						</tr>
					</table>
				</div>
				<div class="detail_right_content_table">
					<table cellpadding="0" cellspacing="0" >
						<tr>
							<th class="th_border" style="background-color: #fcf8f9" >序号</th>
							<th class="th_border" style="background-color: #fcf8f9" >问题</th>
							<th class="th_border" style="background-color: #fcf8f9" >厂牌	</th>
							<th class="th_border" style="background-color: #fcf8f9" >产品</th>
						</tr>
						<tbody>
							<tr>
								<td class="td_border" >1</td>
								<td class="td_border" >深圳哪里保养比较便宜啊？</td>
								<td class="td_border" >东风悦达起亚</td>
								<td class="td_border" >智跑</td>
							</tr>
							<tr>
								<td class="td_border" >2</td>
								<td class="td_border">如何节约保养费用？</td>
								<td class="td_border" >北京现代</td>
								<td class="td_border" >索纳塔</td>
							</tr>
							<tr>
								<td class="td_border" >3</td>
								<td class="td_border">过来保修期，还需要去4S店保养吗？</td>
								<td class="td_border" >一汽丰田</td>
								<td class="td_border" >锐志</td>
							</tr>							
							<tr>
								<td class="td_border" >4</td>
								<td class="td_border">关于油耗及保养费用，请大神来赐教！</td>
								<td class="td_border" >东风日产</td>
								<td class="td_border" >骐达</td>
							</tr>
							<tr>
								<td class="td_border" >5</td>
								<td class="td_border">每年保养费用大概多少？</td>
								<td class="td_border" >一汽丰田</td>
								<td class="td_border" >卡罗拉</td>
							</tr>
							<tr>
								<td class="td_border" >6</td>
								<td class="td_border">想买K5，每次保养贵不贵？</td>
								<td class="td_border" >东风悦达起亚</td>
								<td class="td_border" >K5</td>
							</tr>
							<tr>
								<td class="td_border" >7</td>
								<td class="td_border">今天去4S店二次保养，求各地车保养费用比较</td>
								<td class="td_border" >北京现代</td>
								<td class="td_border" >瑞纳</td>
							</tr>
							<tr>
								<td class="td_border" >8</td>
								<td class="td_border">车辆保养都做什么项目？需要多少钱？</td>
								<td class="td_border" >东风日产</td>
								<td class="td_border" >天籁</td>
							</tr>
							<tr>
								<td class="td_border" >9</td>
								<td class="td_border">请教2.0 油耗 保养费用问题	北京现代	悦动</td>
								<td class="td_border" >北京现代</td>
								<td class="td_border" >悦动</td>
							</tr>
							<tr>
								<td class="td_border" >10</td>
								<td class="td_border">关于汽车保养费用省钱！</td>
								<td class="td_border" >广汽本田</td>
								<td class="td_border" >雅阁</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="clear"></div>
				<div>
					<div class="demo">
		                <div id="_articles">
							<ul class="ec-page-ul" >
								<li  class="ec-page-li" ><a >首页</a></li>
								<li  class="ec-page-li ec-page-li-current" ><a >1</a></li>
								<li  class="ec-page-li" ><a >2</a></li>
								<li  class="ec-page-li" ><a >3</a></li>
								<li  class="ec-page-li" ><a >4</a></li>
								<li  class="ec-page-li" ><a >5</a></li>
								<li  class="ec-page-li" ><a >6</a></li>
								<li  class="ec-page-li" ><a >7</a></li>
								<li  class="ec-page-li" ><a >8</a></li>
								<li  class="ec-page-li" ><a >9</a></li>
								<li  class="ec-page-li" ><a >10</a></li>
								<li  class="ec-page-li" ><a >11</a></li>
								<li  class="ec-page-li" ><a >尾页</a></li>
							</ul>
						</div>
		            </div>
				</div>
			</div>
			<div class="clear"></div>
						
		</div>
		<jsp:include page="../common/footer.jsp" />

	</center>
</body>
</html>
