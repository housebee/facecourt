# facecourt
# face court, fun
#
# prototype with Spring Security, integration with Facebook, embedded H2 database.
# 
# Git Reporitory : https://github.com/housebee/facecourt.git
# 
# TheamLeaf/UI development
# Database could be split in the future with tiered architecture.
#  
#
# Note: 
#
# Spring Boot has problem with Eclipse, it cannot stop the embedded tomcat server.
# 
# Therefore, run the application with DOS command as "mvn spring-boot:run"
#
# Debug:
#   1. mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005
#   2. Remote debug from eclipse with port above - 5005
#
# Note:
#		JPA is too smart. Java side mapping use all small case, otherwise, JPA create a different column
#		For example, userName will be mapped to user_name, rather than userName.
#
#
# EGit: 
#		EGit is the plugin for EClipse IDE
#		Tutorial is http://www.vogella.com/tutorials/EclipseGit/article.html#how-to-configure-the-usage-of-git-in-eclipse
#



#========================================= deploy app to google cloud ============================================
This shows how to run a Spring Boot application on Google Cloud Platform. It uses the Google App Engine flexible environment.

Before you begin
This project assumes you have Java 8 installed.

Download Maven
These samples use the Apache Maven build system. Before getting started, be sure to download and install it. When you use Maven as described here, it will automatically download the needed client libraries.

Run application:
1. java -jar target\webapp-0.0.1.jar

Debug application:
start application in DOS console.
1. java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=5005,suspend=n -jar target\webapp-0.0.1.jar
2. eclipse remote debug with port of 5005


Create a Project in the Google Cloud Platform Console
If you haven't already created a project, create one now. Projects enable you to manage all Google Cloud Platform resources for your app, including deployment, access control, billing, and services.

Open the Cloud Platform Console.
In the drop-down menu at the top, select Create a project.
Give your project a name.
Make a note of the project ID, which might be different from the project name. The project ID is used in commands and in configurations.
Enable billing for your project.
If you haven't already enabled billing for your project, enable billing now. Enabling billing allows the application to consume billable resources such as running instances and storing data.

Install the Google Cloud SDK.
If you haven't already installed the Google Cloud SDK, install and initialize the Google Cloud SDK now. The SDK contains tools and libraries that enable you to create and manage resources on Google Cloud Platform.

Install the Google App Engine SDK for Java
gcloud components update app-engine-java
gcloud components update
Configure the app.yaml descriptor
The app.yaml descriptor is used to describe URL dispatch and resource requirements. This example sets manual_scaling to 1 to minimize possible costs. These settings should be revisited for production use.

Run the application locally
Set the correct Cloud SDK project via "gcloud config set project open-court" to the ID of your application.
Run mvn spring-boot:run
Visit http://localhost:8080

Deploy to App Engine flexible environment. The version number for appengine plugin cannot be 1.0.0, it has to be only numbers/chars, such as "1" or "dev".
without step #1, it usually has timeout error.

0. gcloud app versions stop 1
1. gcloud beta app update --split-health-checks --project
2. mvn appengine:deploy

Monitor the app server log
gcloud app logs tail

Visit http://open-court.appspot.com.
Note that deployment to the App Engine flexible environment requires the new com.google.cloud.tools:appengine-maven-plugin plugin.

Java is a registered trademark of Oracle Corporation and/or its affiliates.


