
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
    	Under 45 Vaccine availability detected Stats <br> Dose : <u><%=dose%></u>
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
    // Themes end
    
    // Create chart instance
    var chart = am4core.create("chartdiv", am4charts.XYChart);
    chart.colors.step =3;
    
   //#chart.dataSource.url="/vaccine/availability/under45/stats?date="+jsdate+"&dose="+jsDose;
   	chart.data=${data};
        
    // Create axes
    var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
	dateAxis.dateFormatter.dateFormat = "yyyy-MM-dd";
	dateAxis.title.text="[bold font-size:20px]Availability Dates[/](as of "+jsdate+")";
	dateAxis.gridIntervals.setAll([
        { timeUnit: "day", count: 1 },
        { timeUnit: "day", count: 1 }
        ]);
	
	var valueAxis = chart.yAxes.push(new am4charts.DurationAxis());
	valueAxis.baseUnit = "minute";
	valueAxis.durationFormatter.durationFormat = "hh'h' mm'min'";
	valueAxis.title.text = "[bold red font-size:20px]Detected Time[/]   of   Day (24 Hr format)";
	
	// Create series
	var series = chart.series.push(new am4charts.LineSeries());
	series.dataFields.valueY = "time_of_day";
	series.dataFields.dateX = "available_date";
	series.strokeWidth = 0;
	series.dataFields.value = "dose";
	series.name = "Dose details";

	// Make bullets grow on hover
	var bullet = series.bullets.push(new am4charts.CircleBullet());
	var bullethover = bullet.states.create("hover");
	bullethover.properties.scale = 1.8;
	bullet.stroke = am4core.color("#ffffxx");
	bullet.stroke.width=3;
	bullet.nonScalingStroke = true;
	bullet.tooltipText = "{center_name}\n{dose}";
	bullet.circle.radius = 10;

	// Create vertical scrollbar and place it before the value axis
	chart.scrollbarY = new am4core.Scrollbar();
	chart.scrollbarY.parent = chart.leftAxesContainer;
	chart.scrollbarY.toBack();
	    
	// Create a horizontal scrollbar with previe and place it underneath the date axis
	chart.scrollbarX = new am4charts.XYChartScrollbar();
	chart.scrollbarX.parent = chart.bottomAxesContainer;

    chart.legend = new am4charts.Legend();
    
    
    }); // end am4core.ready()
    </script>

	<!-- HTML -->
    <div id="chartdiv"> </div>
</body>
</html>