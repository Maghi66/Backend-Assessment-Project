# Backend-Assessment-Project


## Purpose  
This project is a backend REST API to manage employee leaves and expense claims. It provides endpoints for managing departments, employees, leave types, leaves, expense types, and expense claims. The API is built with Spring Boot and uses PostgreSQL as the database.

## Objectives
- Build a REST API with standard CRUD operations
- Implement business logic for leave and expense tracking
- Ensure code quality, security, and test coverage
- Provide Swagger UI documentation for easy API exploration

## Technologies Used
- Visual Studio Code (VS Code) – Development Environment
- Java 20
- Spring Boot Framework
- Spring Web
- Spring Security
- Spring Data JPA
- Hibernate ORM
- JUnit 5
- Maven
- PostgreSQL Database
- Lombok
- Swagger / OpenAPI
- DTOs (Data Transfer Objects)

## Installation  
-Clone the repository and cd into correct folder 

-The application was developed using Spring Boot and runs locally using the Maven Wrapper. To start the application, run:
```bash
./mvnw spring-boot:run
```
-Access the application: navigate to http://localhost:8081/swagger-ui/index.html

## Testing
Run the test cases using JUnit for testing all the controllers:
```bash
./mvnw -Dtest=DepartmentControllerTest test
```
```bash
./mvnw -Dtest=EmployeeControllerTest test
```
```bash
./mvnw -Dtest=LeaveControllerTest test
```
```bash
./mvnw -Dtest=ExpenseControllerTest test
```
## PostgreSQL Database
This project uses a PostgreSQL database as its backend, managed through pgAdmin. The database schema includes all necessary tables required for the application's functionality. A complete SQL backup file, **data_backup.sql**, was generated using pgAdmin and is included in the project repository under the **db-migration/** folder.


