# Presentation Management System 

A Spring Boot-based RESTful API for managing student presentations with role-based access for students and admins. This backend-only project supports secure login, presentation assignment, scoring, rating, and status tracking — ready to be consumed by any frontend or API testing tools like Postman.

---

## Tech Stack

- **Backend Framework:** Spring Boot
- **ORM:** Hibernate + Spring Data JPA
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **API Testing:** Postman

---

## Key Features

-  **Authentication**
  - Register and login functionality for students and admins
-  **Role-Based Access**
  - Admin and Student have different access levels
-  **Presentation Management**
  - Students can upload/view their presentations
  - Admins can assign, score, and update presentation statuses
-  **Rating System**
  - Admins can rate student presentations

---

##  API Endpoints

| HTTP Method | Endpoint                                             | Role       | Description                                 |
|-------------|------------------------------------------------------|------------|---------------------------------------------|
| POST        | `/register`                                          | All        | Register a new user (student/admin)         |
| POST        | `/login`                                             | All        | Login and get authenticated                 |
| GET         | `/details/{id}`                                      | All        | Get user details by ID                      |
| GET         | `/admin/students/{id}`                               | Admin      | Get student details by admin ID             |
| POST        | `/assign/{adminId}/{studentId}`                      | Admin      | Assign a presentation to a student          |
| POST        | `/adminRate/{adminId}/{studentId}/{presentationId}` | Admin      | Admin rates a presentation                  |
| GET         | `/getRating`                                         | All        | Get overall ratings                         |
| PUT         | `/updateStatus/{adminId}/{studentId}`               | Admin      | Update student status                       |
| GET         | `/fetchPresentation/{id}`                            | All        | Get presentation details by ID              |
| GET         | `/getpresbyStudId/{id}`                              | Student    | Get presentations by student ID             |
| PUT         | `/updatePresentationStatus/{studentId}/{pid}`        | Student    | Update presentation status                  |

> 🧪 Use Postman to test the APIs. You can send JSON payloads to each endpoint as needed.

---

## Setup Instructions

1. **Clone the Repository**
- git clone https://github.com/Nikita-Adepu/PresentationApp.git
- cd PresentationApp

2. Configure the Database
- Create a PostgreSQL database named presentationdb
- Update the application.properties file with your PostgreSQL credentials:
  - spring.datasource.url=jdbc:postgresql://localhost:5432/presentationdb
  - spring.datasource.username=your_db_username
  - spring.datasource.password=your_db_password
  - spring.jpa.hibernate.ddl-auto=update

3. Build and Run the App
- ./mvnw spring-boot:run
The backend will start on: http://localhost:8080

## API Testing with Postman
You can use Postman to test all API endpoints. Here are some suggestions:
- Set Content-Type: application/json in headers
- Use proper JSON payloads for login, register, and POST/PUT APIs
- Capture tokens if authentication is implemented

## Author
Nikita Adepu
📧 adepunikita@gmail.com
🔗 https://linkedin.com/in/nikita-adep
