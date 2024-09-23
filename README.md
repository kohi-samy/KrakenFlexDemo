KrakenFlexDemo application

This project is a demo application.
External url -> https://api.krakenflex.systems/interview-tests-mock-api/v1/

Requirements:

  Java 17 
  Maven 3.8.8
  
Project setup and dependency:

Clone this repository to your local machine:
  https://github.com/kohi-samy/KrakenFlexDemo.git

Build and run.

  Navigate to the project root directory: cd KrakenFlexDemo
  Build the application: Maven mvn clean install
  Run the application: java -jar .\target\KrakenFlexDemo-0.0.1-SNAPSHOT.jar

Swagger

  http://localhost:8080/swagger-ui/index.html#/

There are three endpoints in this file:

1. `GET /outages` which returns all outages in our system
2. `GET /site-info/norwich-pear-tree` which returns specific information about a site - norwich-pear-tree
3. `POST /site-outages/norwich-pear-tree` which expects outages for a specific site <norwich-pear-tree> to be posted to it
