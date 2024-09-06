Airline Reservation System - Backend API

This project is a RESTful Airline Reservation System that provides an API for managing flights, customers, agents, and administrative tasks. The system consists of three primary roles: Admin, Agent, and Customer. The system allows airlines to create and manage flights, customers to book tickets, and administrators to control the overall operation.

Key Features

Admin can manage cities, airports, companies, agents, customers, and flights.
Agents can manage flight schedules, update flight details, and notify customers of important changes.
Customers can search for flights, book and cancel tickets, and receive notifications about changes in their flights.
Automatic email notifications sent to customers when flight schedules or details are updated.
Technologies Used
RESTful API built with Java.
Swagger for API documentation and testing.
Spring Boot for server configuration.
JSON/YAML format for flight data.
API Documentation
The API is divided into three main groups, each serving a different role:

1. Admin API
Admins have the ability to:

Manage cities and airports.
Add or remove airlines (companies).
Create, delete, or manage agents.
Block/unblock agents and customers.
View and control all system activities.
2. Agent API
Agents represent a company in specific airports and have the ability to:

Add new flights (weekly or monthly schedules).
Update flight times, ticket prices, and availability.
Notify customers about flight changes (via email).
3. Customer API
Customers can:

Register and log in to the system.
Select their city and desired airport.
Browse available flights, view details such as prices and ticket availability.
Book and cancel tickets.
Receive email notifications when there are flight changes.
System Flow
Admin creates cities, airports, companies, and assigns agents to companies for each airport.
Agent registers new flights or updates existing ones in the system. The flight information is provided in a JSON or YAML file format.
Customer searches for flights based on their location and books tickets.
If a flight is updated (e.g., time change, price adjustment), an email notification is sent to all customers who booked that flight.
Email Notifications
Customers who have booked tickets will automatically receive email notifications (via Gmail) whenever:

Flight schedules are changed.
Flight prices are updated.
