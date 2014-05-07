<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="base.jsp"%>
<table id="search_table">
					<tr>
						<td><div class="search_tag">筛选</div></td>
						<td></td>
					</tr>
					<tr>
						<td valign="top" style="width: 100px;"><div class="search_tag">时间：</div></td>
						<td id="a_time"><span class="search_a">今天<input type="hidden" value="0" /></span> <span>昨天<input type="hidden" value="-1" /></span> <span>最近7天<input
								type="hidden" value="-7" /></span> <span>最近15天<input type="hidden" value="-15" /></span> <span>最近30天<input type="hidden" value="-30" /></span> <span
							class="custom">自定义时间<input type="hidden" value="1" /></span>
							<div class="clear"></div>
							<div id="search_time" style="display: none;">
								开始时间：<input type="text" readonly="readonly" name="startTime" id="startTime" />&nbsp;&nbsp;&nbsp;结束时间：<input type="text" readonly="readonly"
									name="endTime" id="endTime" />
							</div> <input type="hidden" id="time" value="0" /></td>
					</tr>
					<tr>
						<td valign="top" ><div class="search_tag">品牌：</div></td>
						<td id="a_brand"><c:forEach items="${brands }" var="dto" varStatus="sn">
								<c:choose>
									<c:when test="${sn.index==0}">
										<span class="search_a">${dto.name}<input type="hidden" value="${dto.id}" /></span>
									</c:when>
									<c:otherwise>
										<span>${dto.name}<input type="hidden" value="${dto.id}" /></span>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<div class="clear"></div></td>
					</tr>
					<tr>
						<td><div class="search_tag"></div></td>
						<td id="a_serials"></td>
					</tr>
					<tr>
						<td><div class="search_tag">选中产品：</div></td>
						<td>
							<table width="100%" id="clean_border1">
								<tr>
									<td width="595px" style="padding-bottom: 5px;" id="select_ser"><c:forEach items="${serials }" var="dto" varStatus="sn">
											<div class="search_brand_item serial_${dto.id}">
														<table class="select_serial">
															<tr>
																<td><div class="brandName" style="padding-top: 5px; padding-bottom: 5px;">${dto.name}<input type="hidden" name="product" value="${dto.id}" /></div></td>															<td><div class="select_d" onclick="removeSerial('serial_${dto.id}');">X</div></td>
															</tr>
														</table>
													</div>
										</c:forEach></td>
									<%-- <td valign="top"><c:if test="${serials.size()>6}">
											<div id="search_brand_more">+更多</div>
										</c:if></td> --%>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td valign="top"><div class="search_tag">区域：</div></td>
						<td>
							<table width="100%" id="clean_border">
								<tr>
									<td id="a_sear"><span class="search_a">大区</span> <span class="custom">省份地区</span>
										<div class="clear"></div>
										<div id="search_province" style="display: none;">
											<c:forEach items="${provinces }" var="dto" varStatus="sn">
												<span>${dto.name}<input type="hidden" value="${dto.id}" /></span>
											</c:forEach>
										</div>
										<div class="clear"></div> <input type="hidden" id="provinceId" value="0" /></td>
								</tr>
								<tr>
									<td id="a_qu"><span class="search_a">全国<input type="hidden" value="0" /></span> <c:forEach items="${areas }" var="dto" varStatus="sn">
											<span>${dto.name}<input type="hidden" value="${dto.id}" /></span>
										</c:forEach>
										<div class="clear"></div> <input type="hidden" id="area" value="0" /></td>
								</tr>
							</table>

						</td>
					</tr>
					<tr>
						<td colspan="2" align="center"><div id="search_btn">立即筛选</div></td>
					</tr>
				</table>