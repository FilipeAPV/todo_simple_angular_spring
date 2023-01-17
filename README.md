## About this Repository
This repository contains a simple project that was created as an opportunity to practice my newly acquired Angular skills. The project is a simple TODO list application that allows users to: 
    - Register
    - View
    - Add Tasks
    - Mark tasks as completed

![app](https://bitbucket.org/FAPVieira/simpletodo_angularspring/raw/468c9d900744276d5944dbf3df254d813a01b6d0/test.gif)

One of the main objectives of this project was to gain experience working with API endpoints, as I was previously used to working with Thymeleaf in a monolith architecture.

## Technical stack
The project was built using Angular as the frontend framework and Java(Spring) as the backend framework. The database used is MySQL.

## Getting Started
To run the application locally, you will need to have Node.js and Angular CLI installed. Once these are installed, clone the repository and navigate to the root directory. Run the following commands:

npm install to install all necessary dependencies
ng serve to start the frontend server
In addition, you will need to have a local MySQL server running and create a database called "todo". You can then import the provided "todo.sql" file to create the necessary tables.

Finally, you will need to update the application.properties file in the backend with your MySQL server credentials. Once these steps are completed, you should be able to access the application at http://localhost:4200.