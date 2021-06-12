# vac-slot-finder
India COVID vaccine Slot finder using COWIN public API's.

Simple Java Spring boot implementation to find vaccination slots using COWIN public API's (via API Setu)
Uses database to store slot finds.
Note :- Rate Limit for COWIN API's : 100 API calls in span of 5 mins per IP

Features :-
a) Can query 15 pincodes in span of 55 secs. More can be added if you plan to run on different server(IP)
b) Stores slot finds in a database table for querying stats.
c) Uses Amcharts and JSP to view Graphical representation of a particular day's statistics
d) Sends alerts via Telegram. Note :- You will have to configure telegram bot and provide details in application.properties
e) Uncomment the @Scheduling annotation on WeeklySlotFinderTask.java and you would start recieving daily summary at 21:00
   The mini summary contains top 3 time slots, centers, pincodes.
  
  Preview of how the JSP Amcharts based graph
  
  
