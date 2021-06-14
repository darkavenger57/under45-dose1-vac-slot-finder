
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<style>
	   #chartdiv {
	      width: 100%;
	      height: 90%;
	    }				
	    					
	</style>
<body>
    
 	<%
    String date=request.getParameter("date");
 	String dose=request.getParameter("dose");
    %>
   
    <h3>
    	Under 45 Vaccine availability TimeSlot based Stats <br> Dose : <u><%=dose%></u> <br> Detection date :<%=date%>
    </h3>
   	
    <!-- Resources -->
    <script src="https://cdn.amcharts.com/lib/4/core.js"></script>
    <script src="https://cdn.amcharts.com/lib/4/charts.js"></script>
    <script src="https://cdn.amcharts.com/lib/4/themes/material.js"></script> 
    <script src="https://cdn.amcharts.com/lib/4/themes/frozen.js"></script>
    <script src="https://cdn.amcharts.com/lib/4/themes/animated.js"></script>
    
    <!-- Chart code -->
    <script>
    
    var jsdate="<%=date%>";
    var jsDose="<%=dose%>";
    
    am4core.ready(function() {
    
    // Themes begin
    if(jsDose=='dose1') {
    	am4core.useTheme(am4themes_material);
    } else
   	{
    	am4core.useTheme(am4themes_frozen);
   	}
    am4core.useTheme(am4themes_animated);
    var chart = am4core.create("chartdiv", am4charts.XYChart);

    /*chart.data=[{"available_date":"09-Jun-2021","center_name":"SANE GURUJI AROGYA KENDRA","age":"18","pincode":"411028","time_of_day":"12:30","dose":"495"},
    {"available_date":"08-Jun-2021","center_name":"CLOUDNINE HOSPITAL SHN","age":"18","pincode":"411005","time_of_day":"15:45","dose":"20"}];
    // Create chart instance*/

    chart.data=${data};
    
    var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
    categoryAxis.renderer.grid.template.location = 0;
    categoryAxis.dataFields.category = "time_of_day_str";
    categoryAxis.renderer.minGridDistance = 15;
   /* categoryAxis.renderer.grid.template.location = 0.5;
    categoryAxis.renderer.grid.template.strokeDasharray = "1,3";
    categoryAxis.renderer.labels.template.rotation = -90; */
    categoryAxis.renderer.labels.template.horizontalCenter = "left";
    categoryAxis.renderer.labels.template.location = 0.5;
    categoryAxis.title.text ="[bold red font-size:20px]Detected Time[/]   of   Day (24 Hr format)";

    categoryAxis.renderer.labels.template.adapter.add("dx", function(dx, target) {
        return -target.maxRight / 2;
    })

    var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
    valueAxis.tooltip.disabled = true;
    valueAxis.renderer.ticks.template.disabled = true;
    valueAxis.renderer.axisFills.template.disabled = true;
    valueAxis.title.text="[bold font-size:20px]Doses detected[/](as of "+jsdate+")";

    var series = chart.series.push(new am4charts.ColumnSeries());
    series.dataFields.categoryX = "time_of_day_str";
    series.dataFields.valueY = "dose";
    //series.tooltipText = "{center_name}\n{dose}";
    series.sequencedInterpolation = true;
    series.fillOpacity = 0;
    series.strokeOpacity = 1;
    series.strokeDashArray = "1,3";
    series.columns.template.width = 0.01;
    series.tooltip.pointerOrientation = "horizontal";

    var bullet = series.bullets.create(am4charts.CircleBullet);
    var bullethover = bullet.states.create("hover");
    bullethover.properties.scale = 1.8;
    bullet.stroke = am4core.color("#ffffxx");
    bullet.stroke.width=3;
    bullet.circle.radius = 7;
    bullet.tooltipText = "{center_name}\n{dose}";

    //chart.cursor = new am4charts.XYCursor();

    chart.scrollbarX = new am4core.Scrollbar();
    chart.scrollbarY = new am4core.Scrollbar();


    }); // end am4core.ready()
    </script>
    	

	<!-- HTML -->
    <div id="chartdiv"> </div>
</body>
</html>