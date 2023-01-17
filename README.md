## About this Repository
This repository contains a simple project that was created as an opportunity to practice my newly acquired Angular skills. The project is a simple TODO list application that allows users to: 
- Register
- View
- Add Tasks
- Mark tasks as completed

![app](https://bitbucket.org/FAPVieira/simpletodo_angularspring/raw/d460b8194776bf8144004b77efafdcd830a50865/Resources/Img/demo.gif)

The main objective of this project was to gain some experience working with API endpoints, as I was previously used to work with Thymeleaf in a monolith architecture.

## Technical stack
The project was built using Angular as the frontend framework and Java(Spring) as the backend framework. The database used is MySQL.

## Getting Started
To run the application locally, you will need to have **Node.js**, **Angular CLI**, last **Java Development Kit (JDK)** and a **MySQL server** installed. Once these are installed, clone the repository and navigate to the root directory. Run the following commands:

- Frontend:
    - `npm install` to install all necessary dependencies
    - `ng serve` to start the frontend server
- Backend & Database:

    - Go to: `Backend/src/main/resources/application.properties` and connect to a DB (local or remote) by providing the relevant information:
        - `spring.datasource.url=jdbc:mysql://localhost:3306/simple_todo_sql?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`
        - `spring.datasource.username=root`
        - `spring.datasource.password=1234`
    - On the server, create a schema named ex: `simple_todo_sql`
    - Run the backend code by executing `Backend/src/main/java/com/mytodo/backend/BackendApplication.java`on an IDE
        - The database structure will be automatically created by Hibernate
    - You can create the DB structure manually and add dummy data by executing the SQL code found in: `Backend/src/main/resources/static/dummyData.sql`


Once these steps are completed, you should be able to access the application at http://localhost:4200.