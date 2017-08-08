# springBootMVC
Simple Spring Boot MVC application to try out some different stuff ^^

## Getting Started
Checkout: https://github.com/IamFriberg/springBootMVC

### Prerequisites
* MySQL version 5.6 or better. If you have docker installed it might be useful to run the database as a container.
* A favorite text editor or IDE
* JDK 1.8 or later
* Maven 3.0+
* GIT
* Lombok Plugin
* Make sure you have enable annotation processing

### Installing
Change the DB-configuration in: application.properties to point to your MySQL database.
Set up the schema and tables needed with the queries in db/setup.sql

To run the application from commandline:
```
mvn package && java -jar target/springmvc-0.0.1-SNAPSHOT.jar
```


## Running the tests

Tests are found in: /tests
Integration tests have IT in their name and needs have the application running locally.


To run them from commandline:
```
mvn test
```



