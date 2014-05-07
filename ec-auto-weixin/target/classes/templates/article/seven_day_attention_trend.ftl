<!DOCTYPE html>
<html>
<head>
<title>近7天关注趋势</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.0/jquery.mobile-1.4.0.min.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.0/jquery.mobile-1.4.0.min.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<#include "../common/header_script.ftl">
<script type="text/javascript">
$(function () {
    $('#hchart').highcharts({
        title: {
            text: '${yearMonth}',
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: [<#list dateNames as dateName>  <#if dateName_index gt 0>,</#if>'${dateName}' </#list>]
        },
        yAxis: {
            title: {
                text: ''
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '篇'
        },
        legend: {
        	layout : 'horizontal',
			align : 'center',
			verticalAlign : 'bottom',
            borderWidth: 0
        },
        series: [<#list trends as trend>
        <#if trend_index gt 0>,</#if>
        {
            name: '${trend.serialName}',
            data: [<#list trend.articleCounts as count>  
            		<#if count_index gt 0>,</#if>${count?c}
            	</#list>]
        }
        </#list>]       
    });
});
</script>
</head>
<body>

	<div data-role="page" id="index_page">
		<div data-role="header" data-theme="c">
			<h1></h1>
		</div>

		<div data-role="content">
			<a data-mini="true" href="#search_page" data-role="button">${brandName}</a>
			<div id="hchart">
			
			</div>
		</div>

		<#include "../common/footer.ftl">
	</div>
	
	<#include "seven_day_attention_trend_search.ftl">
</body>
</html>