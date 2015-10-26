Socials
======================
This sample web application for social networks integration consists of two projects. Every project is run on is own web server.

socials-spring-rest
---------------------------------
 * Used as REST interface for handling authorization and simple social networks operations (receiving and posting statuses, as well as previewing basic user data) on Facebook, Twitter and Instagram
 * Spring boot application

socials-angular-client
---------------------------------
 * Single page web application for demonstration purposes of REST interface
 * Client designed using AngularJS framework
 * Spring boot application

![Socials screenshot](https://c2.staticflickr.com/6/5734/22313513028_4566182cfd_z.jpg)


Step 1: Register your application
---------------------------------
Before you can run the application (projects), you'll need to obtain application credentials from Facebook, Twitter, and Instagram by registering the application with each of the service providers:

 * Facebook: https://developers.facebook.com/apps
 * Twitter: https://apps.twitter.com/
 * Instagram: https://instagram.com/developer

Step 2: Edit socials-rest.properties
-----------------------------------
Once you have registered the application, you'll need to edit socials-spring-rest/src/main/resources/socials-rest.properties, adding the credentials to the appropriate properties.

Step 3: Run projects
---------------------------
To run, import the project into your IDE and run Application classes (Spring boot - embedded Tomcat web server) inside both projects.