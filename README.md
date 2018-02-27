# Authorisation service

It's an authorisation service in Java. For developing this server-application were used such technologies:

-RestFul (Framework - Jersey)<br>
-Hiberante for working with a mysql databank.<br>
-Using mysql data bank.<br>
-Json Web Token.<br>

##### Quick tutorial (How to use):
-Bevor using you should edit "hibernate.cfg.xml" and "AutService". The first file is used for working
with a data bank, so you should change the file accoriding to your data bank. In the file "AutService"
you should change at least the ip-adresses of services, which your service would communicate with. 
-You should use Maven for creating of "DSE.war" file.<br>
-Do not forget to create a table in your data bank. Use a "user.sql" for this.<br>
-Deploy "DSE.war" for example using Tomcat.<br>
-You can test the functionality of microservice using a "Postman".<br>
