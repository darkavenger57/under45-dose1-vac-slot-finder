# under45-dose1-vac-slot-finder
Under 45 Dose 1 COVID vaccine Slot finder using COWIN public API's.

Simple Java Spring boot implementation to find vaccination slots using COWIN public API's (via API Setu)
Uses database to store slot finds.
Note :- Rate Limit for COWIN API's : 100 API calls in span of 5 mins per IP

<b>Features</b> :- <br>
a) Can query 15 pincodes in span of 55 secs. More can be added if you plan to run on different server(IP) <br>
b) Stores slot find information in a Postgre database table for querying stats.<br>
   [ You can think of storing it in HSQLDB also ]
c) Uses Amcharts and JSP to view Graphical representation of a particular day's statistics<br>
d) Sends alerts via Telegram. Note :- You will have to configure telegram bot and provide details in application.properties<br>
e) Uncomment the @Scheduling annotation on WeeklySlotFinderTask.java and you would start recieving daily summary at 21:00<br>
   The mini summary contains top 3 time slots, centers, pincodes.<br>
  
  Preview of how the JSP Amcharts based graph<br>
  
  <img width="1427" alt="Screenshot 2021-06-11 at 8 05 35 PM" src="https://user-images.githubusercontent.com/12975575/121778285-e6669880-cbb3-11eb-9a91-6cb7ba80cb6d.png">

