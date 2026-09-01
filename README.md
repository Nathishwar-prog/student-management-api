# Student Management REST API

A backend REST API for managing students, courses, enrollments, and academic records. The application is built using **Java, Spring Boot, Spring MVC, Spring Data JPA, MySQL, Spring Security, and JWT**.

The project follows a **layered MVC architecture** with separate Controller, Service, Repository, Entity, DTO, Security, and Exception layers.

---

## Features

* Student CRUD operations
* Course CRUD operations
* Student course enrollment management
* Academic grade management
* RESTful API design
* DTO-based request and response handling
* Request validation using Jakarta Bean Validation
* Global exception handling
* Proper HTTP status codes
* MySQL relational database integration
* JPA/Hibernate ORM
* JWT-based authentication
* Role-based authorization
* Admin and User roles
* Postman API testing
* Git/GitHub version control

---

## Technologies Used

| Technology              | Purpose                          |
| ----------------------- | -------------------------------- |
| Java 17+                | Programming language             |
| Spring Boot             | Backend framework                |
| Spring MVC              | REST API development             |
| Spring Data JPA         | Database access                  |
| Hibernate               | ORM                              |
| MySQL                   | Relational database              |
| Spring Security         | Authentication and authorization |
| JWT                     | Stateless authentication         |
| Jakarta Bean Validation | Request validation               |
| Maven                   | Dependency management            |
| Postman                 | API testing                      |
| Git/GitHub              | Version control                  |

---

## Architecture

The project follows a layered architecture based on Spring MVC.

```text
                         Client
                    Postman / Frontend
                           |
                           | HTTP Request
                           ↓
                  ┌──────────────────┐
                  │   Controller     │
                  │   Spring MVC     │
                  └────────┬─────────┘
                           ↓
                  ┌──────────────────┐
                  │     Service      │
                  │  Business Logic  │
                  └────────┬─────────┘
                           ↓
                  ┌──────────────────┐
                  │    Repository    │
                  │   Data Access    │
                  └────────┬─────────┘
                           ↓
                  ┌──────────────────┐
                  │   JPA/Hibernate  │
                  └────────┬─────────┘
                           ↓
                       MySQL
```

Security is applied before protected controller endpoints:

```text
Client
   |
   | Authorization: Bearer <JWT>
   ↓
JWT Authentication Filter
   |
   ↓
Spring Security
   |
   ↓
Controller
```

---

## Project Structure

```text
student-management-api/
│
├── pom.xml
├── README.md
│
├── postman/
│   └── Student-Management.postman_collection.json
│
└── src/
    └── main/
        ├── java/
        │   └── com/example/studentmanagement/
        │
        │       ├── StudentManagementApplication.java
        │       │
        │       ├── config/
        │       │   ├── SecurityConfig.java
        │       │   └── DataInitializer.java
        │       │
        │       ├── controller/
        │       │   ├── AuthController.java
        │       │   ├── StudentController.java
        │       │   ├── CourseController.java
        │       │   └── EnrollmentController.java
        │       │
        │       ├── dto/
        │       │   ├── AuthRequest.java
        │       │   ├── AuthResponse.java
        │       │   ├── StudentRequest.java
        │       │   ├── StudentResponse.java
        │       │   ├── CourseRequest.java
        │       │   ├── CourseResponse.java
        │       │   ├── EnrollmentRequest.java
        │       │   └── EnrollmentResponse.java
        │       │
        │       ├── entity/
        │       │   ├── AppUser.java
        │       │   ├── Role.java
        │       │   ├── Student.java
        │       │   ├── Course.java
        │       │   └── Enrollment.java
        │       │
        │       ├── exception/
        │       │   ├── ApiError.java
        │       │   ├── BadRequestException.java
        │       │   ├── ResourceNotFoundException.java
        │       │   └── GlobalExceptionHandler.java
        │       │
        │       ├── repository/
        │       │   ├── AppUserRepository.java
        │       │   ├── StudentRepository.java
        │       │   ├── CourseRepository.java
        │       │   └── EnrollmentRepository.java
        │       │
        │       ├── security/
        │       │   ├── JwtService.java
        │       │   ├── JwtAuthenticationFilter.java
        │       │   └── CustomUserDetailsService.java
        │       │
        │       └── service/
        │           ├── AuthService.java
        │           ├── StudentService.java
        │           ├── CourseService.java
        │           └── EnrollmentService.java
        │
        └── resources/
            └── application.properties
```

---

# Database Design

The application uses a relational MySQL database.

### Main tables

```text
users
students
courses
enrollments
```

### Entity relationship

```text
        ┌───────────────┐
        │   students    │
        │───────────────│
        │ id            │
        │ name          │
        │ email         │
        │ phone         │
        │ department    │
        └───────┬───────┘
                │
                │ 1
                │
                │ *
        ┌───────▼────────┐
        │  enrollments   │
        │────────────────│
        │ id             │
        │ student_id     │
        │ course_id      │
        │ grade          │
        └───────┬────────┘
                │
                │ *
                │
                │ 1
        ┌───────▼────────┐
        │    courses     │
        │────────────────│
        │ id             │
        │ code           │
        │ name           │
        │ credits        │
        └────────────────┘
```

An enrollment connects a student with a course and stores the student's grade.

---

# Prerequisites

Install the following before running the application:

* Java 17 or newer
* Maven
* MySQL 8+
* IntelliJ IDEA / Eclipse / VS Code
* Postman

---

# Database Setup

Create the database in MySQL:

```sql
CREATE DATABASE student_management;
```

Select the database:

```sql
USE student_management;
```

The application uses Hibernate's schema generation to create/update the tables.

You do not need to manually create the tables.

---

# Configuration

Open:

```text
src/main/resources/application.properties
```

Configure your MySQL username and password:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true

spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

The application runs on:

```text
http://localhost:8080
```

---

# Running the Application

Clone the repository:

```bash
git clone <your-github-repository-url>
```

Move into the project:

```bash
cd student-management-api
```

Build the project:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

Or run:

```text
StudentManagementApplication.java
```

from IntelliJ IDEA.

When the application starts successfully, the console should show:

```text
Tomcat started on port 8080
Started StudentManagementApplication
```

---

# Authentication

The application uses JWT-based authentication.

Two development users are initialized automatically.

### Admin

```text
Username: admin
Password: admin123
Role: ROLE_ADMIN
```

### Normal User

```text
Username: user
Password: user123
Role: ROLE_USER
```

These credentials are intended only for local development/testing.

---

# Login API

### Request

```http
POST /api/auth/login
```

Full URL:

```text
http://localhost:8080/api/auth/login
```

Request body:

```json
{
    "username": "admin",
    "password": "admin123"
}
```

Response:

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "username": "admin",
    "role": "ROLE_ADMIN"
}
```

Copy the JWT token and use it for protected APIs.

Add the following HTTP header:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# REST API Endpoints

## Authentication

| Method | Endpoint          | Description                        |
| ------ | ----------------- | ---------------------------------- |
| POST   | `/api/auth/login` | Authenticate user and generate JWT |

---

## Student APIs

| Method | Endpoint             | Description       | Access        |
| ------ | -------------------- | ----------------- | ------------- |
| GET    | `/api/students`      | Get all students  | Authenticated |
| GET    | `/api/students/{id}` | Get student by ID | Authenticated |
| POST   | `/api/students`      | Create student    | ADMIN         |
| PUT    | `/api/students/{id}` | Update student    | ADMIN         |
| DELETE | `/api/students/{id}` | Delete student    | ADMIN         |

---

## Course APIs

| Method | Endpoint            | Description      | Access        |
| ------ | ------------------- | ---------------- | ------------- |
| GET    | `/api/courses`      | Get all courses  | Authenticated |
| GET    | `/api/courses/{id}` | Get course by ID | Authenticated |
| POST   | `/api/courses`      | Create course    | ADMIN         |
| PUT    | `/api/courses/{id}` | Update course    | ADMIN         |
| DELETE | `/api/courses/{id}` | Delete course    | ADMIN         |

---

## Enrollment APIs

| Method | Endpoint                               | Description               | Access        |
| ------ | -------------------------------------- | ------------------------- | ------------- |
| GET    | `/api/enrollments`                     | Get all enrollments       | Authenticated |
| GET    | `/api/enrollments/{id}`                | Get enrollment by ID      | Authenticated |
| GET    | `/api/enrollments/student/{studentId}` | Get student's enrollments | Authenticated |
| GET    | `/api/enrollments/course/{courseId}`   | Get course enrollments    | Authenticated |
| POST   | `/api/enrollments`                     | Create enrollment         | ADMIN         |
| PUT    | `/api/enrollments/{id}`                | Update enrollment         | ADMIN         |
| DELETE | `/api/enrollments/{id}`                | Delete enrollment         | ADMIN         |

---

# Example Requests

## Create Student

```http
POST /api/students
```

Request:

```json
{
    "name": "Arun Kumar",
    "email": "arun@example.com",
    "phone": "9876543210",
    "department": "Computer Science"
}
```

Response:

```json
{
    "id": 1,
    "name": "Arun Kumar",
    "email": "arun@example.com",
    "phone": "9876543210",
    "department": "Computer Science"
}
```

---

## Create Course

```http
POST /api/courses
```

Request:

```json
{
    "code": "CS101",
    "name": "Java Programming",
    "credits": 4
}
```

---

## Create Enrollment

```http
POST /api/enrollments
```

Request:

```json
{
    "studentId": 1,
    "courseId": 1,
    "grade": "A"
}
```

Response:

```json
{
    "id": 1,
    "studentId": 1,
    "studentName": "Arun Kumar",
    "courseId": 1,
    "courseCode": "CS101",
    "courseName": "Java Programming",
    "grade": "A"
}
```

---

# Validation

The API validates incoming request data using Jakarta Bean Validation.

Examples:

```java
@NotBlank
@Email
@NotNull
@Size
@Min
@Pattern
```

For example, an invalid student request:

```json
{
    "name": "",
    "email": "invalid-email",
    "phone": "abc",
    "department": ""
}
```

returns:

```text
400 Bad Request
```

with validation error information.

---

# Exception Handling

The application uses a global exception handler:

```java
@RestControllerAdvice
```

Common responses include:

```text
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
409 Conflict
```

Example:

```json
{
    "timestamp": "2026-09-01T10:00:00",
    "status": 404,
    "error": "Not Found",
    "message": "Student not found: 999",
    "validationErrors": null
}
```

---

# Role-Based Authorization

The application has two roles:

```text
ROLE_ADMIN
ROLE_USER
```

### ADMIN

Can:

```text
Create
Read
Update
Delete
```

### USER

Can:

```text
Read
```

For example:

```java
@PreAuthorize("hasRole('ADMIN')")
```

protects administrative operations.

Therefore:

```text
USER
  |
  | POST /api/students
  ↓
403 Forbidden
```

while:

```text
ADMIN
  |
  | POST /api/students
  ↓
201 Created
```

---

# HTTP Status Codes

The API follows standard HTTP status codes.

| Status | Meaning                         |
| ------ | ------------------------------- |
| 200    | Request successful              |
| 201    | Resource created                |
| 204    | Resource deleted successfully   |
| 400    | Invalid request                 |
| 401    | Authentication required/invalid |
| 403    | Insufficient permissions        |
| 404    | Resource not found              |
| 409    | Resource conflict               |

---

# Postman Testing

The project includes a Postman collection:

```text
postman/
└── Student-Management.postman_collection.json
```

Import the collection into Postman.

Recommended testing sequence:

```text
1. Login - Admin
        ↓
2. Create Student
        ↓
3. Get All Students
        ↓
4. Get Student By ID
        ↓
5. Update Student
        ↓
6. Create Course
        ↓
7. Get All Courses
        ↓
8. Create Enrollment
        ↓
9. Get All Enrollments
        ↓
10. Update Enrollment
        ↓
11. Delete Enrollment
        ↓
12. Login - User
        ↓
13. USER Get Students → 200
        ↓
14. USER Create Student → 403
        ↓
15. Request Without Token → 401
```

---

# Project Flow

A typical create-student request follows this flow:

```text
POST /api/students
        |
        ↓
JwtAuthenticationFilter
        |
        ↓
Spring Security
        |
        ↓
StudentController
        |
        ↓
StudentRequest DTO
        |
        ↓
Validation
        |
        ↓
StudentService
        |
        ↓
StudentRepository
        |
        ↓
JPA / Hibernate
        |
        ↓
MySQL
        |
        ↓
StudentResponse DTO
        |
        ↓
HTTP Response
```

---

# Key Concepts Demonstrated

This project demonstrates the following backend development concepts:

### Spring Boot

Application configuration and dependency injection.

### Spring MVC

REST controllers and HTTP request handling.

### REST API

CRUD endpoints using:

```text
GET
POST
PUT
DELETE
```

### Spring Data JPA

Repository-based database access.

### Hibernate

Object-relational mapping between Java entities and database tables.

### DTO

Separate API request/response models from database entities.

### Validation

Validate incoming client requests before processing them.

### Exception Handling

Centralized API error handling using `@RestControllerAdvice`.

### Spring Security

Authentication and authorization.

### JWT

Stateless authentication using JSON Web Tokens.

### Role-Based Authorization

Restrict operations based on user roles.

### MySQL

Normalized relational database design.

### Postman

API testing and verification.

### Git/GitHub

Source-code management and project version control.

---

# Future Improvements

Possible future enhancements include:

* Pagination and sorting
* Student search and filtering
* Course search
* Attendance management
* Examination and marks management
* GPA calculation
* Refresh token support
* API documentation using Swagger/OpenAPI
* Unit and integration testing
* Docker containerization
* Production database migrations using Flyway
* Frontend integration with React

---

# Author

**Nathishwar**

Java | Spring Boot | REST APIs | SQL | AI/ML

---

## License

This project is created for educational and portfolio purposes.
