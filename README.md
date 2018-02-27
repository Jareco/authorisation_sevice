# Authorisation service

It's an authorisation service in Java. For developing this server-application were used such technologies:

-RestFul (Framework - Jersey)
-Hiberante for working with a mysql databank.
-Using mysql data bank.
-Json Web Token.

##### Quick tutorial (How to use):
-Bevor using you should edit "hibernate.cfg.xml" and "AutService". The first file is used for working
with a data bank, so you should change the file accoriding to your data bank. In the file "AutService"
you should change at least the ip-adresses of services, which your service would communicate with. 
-You should use Maven for creating of "DSE.war" file.
-Do not forget to create a table in your data bank. Use a "user.sql" for this.
-Deploy "DSE.war" for example using Tomcat.
-You can test the functionality of microservice using a "Postman".
