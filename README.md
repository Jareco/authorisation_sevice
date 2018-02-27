# Authorisation service

It's an authorisation service in Java. For developing this server-application were used such technologies:
1.RestFul (Framework - Jersey)
2.Hiberante for working with a mysql databank.
3.Using mysql data bank.
4.Json Web Token.

Quick tutorial (How to use):
1.Bevor using you should edit "hibernate.cfg.xml" and "AutService". The first file is used for working
with a data bank, so you should change the file accoriding to your data bank. In the file "AutService"
you should change at least the ip-adresses of services, which your service would communicate with. 
2.You should use Maven for creating of "DSE.war" file.
3.Do not forget to create a table in your data bank. Use a "user.sql" for this.
4.Deploy "DSE.war" for example using Tomcat.
5.You can test the functionality of microservice using a "Postman".
