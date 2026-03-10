## DevOps Pull Request Update
This update was added to demonstrate pull request workflow.
# Waste Recycling Tracker – Spring Boot Backend

This is the **Spring Boot backend** for the Waste Recycling Tracker project.
It provides REST APIs for managing recyclable waste entries submitted by families and handled by recycling centers.

The backend is built using **Spring Boot, JPA, and Hibernate**, and uses an **H2 in-memory database** for development and testing.

---
Updated backend documentation
## Features

* RESTful API for waste recycling management
* H2 in-memory database for quick development
* JPA and Hibernate ORM
* Role-based access (Family and Center)
* CRUD operations for waste entries
* Waste status tracking (Pending → Processing → Recycled)
* CORS enabled for React frontend
* Simple security configuration
* SonarCloud code quality integration

---

## Tech Stack

* Java 17
* Spring Boot 3.2
* Spring Data JPA
* Hibernate
* H2 Database
* Maven

---

## Project Structure

```
src/main/java/com/wasterecyclingtracker/

├── WasteRecyclingTrackerApplication.java   # Main Spring Boot application
│
├── config/
│   └── SecurityConfig.java                 # Security and CORS configuration
│
├── controller/
│   ├── FamilyController.java               # Family waste APIs
│   └── CenterController.java               # Center management APIs
│
├── service/
│   ├── FamilyWasteService.java             # Family waste operations
│   └── CenterService.java                  # Center operations
│
├── repository/
│   ├── FamilyWasteRepository.java          # Waste JPA repository
│   └── UserRepository.java                 # User JPA repository
│
└── entity/
    ├── FamilyWaste.java                    # Waste entity
    └── User.java                           # User entity
```

---

## Prerequisites

Before running the project, make sure you have:

* Java 17 or higher
* Maven 3.6 or higher
* Spring Boot 3.2+

---

## Running the Backend

### 1. Navigate to backend folder

```
cd backend
```

### 2. Install dependencies

```
mvn clean install
```

### 3. Run the application

```
mvn spring-boot:run
```

The server will start at:

```
http://localhost:8080
```

---

## API Endpoints

### Family APIs

| Method | Endpoint                    | Description              |
| ------ | --------------------------- | ------------------------ |
| POST   | /api/family                 | Add new waste entry      |
| GET    | /api/family/{familyName}    | Get entries for a family |
| GET    | /api/family/entry/{id}      | Get specific waste entry |
| PUT    | /api/family/{id}            | Update waste entry       |
| DELETE | /api/family/{id}            | Delete waste entry       |
| GET    | /api/family/status/{status} | Get entries by status    |

---

### Center APIs

| Method | Endpoint                        | Description           |
| ------ | ------------------------------- | --------------------- |
| GET    | /api/center                     | Get all waste entries |
| GET    | /api/center/status/{status}     | Get entries by status |
| GET    | /api/center/family/{familyName} | Get entries by family |
| PUT    | /api/center/{id}                | Update waste status   |
| DELETE | /api/center/{id}                | Delete recycled entry |

---

## Waste Status Values

The system supports three waste processing states:

* **PENDING** – Waste entry created and waiting for processing
* **PROCESSING** – Waste is currently being processed
* **RECYCLED** – Waste has been successfully recycled

---

## Database Schema

### FamilyWaste Table

```
CREATE TABLE family_waste (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  family_name VARCHAR(255) NOT NULL,
  waste_type VARCHAR(255) NOT NULL,
  quantity DOUBLE NOT NULL,
  status VARCHAR(50) NOT NULL,
  created_at BIGINT
);
```

### Users Table

```
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL
);
```

---

## H2 Database Console

You can access the H2 console at:

```
http://localhost:8080/h2-console
```

Default settings:

JDBC URL

```
jdbc:h2:mem:testdb
```

Username

```
sa
```

Password

```
(empty)
```

---

## Example API Requests

### Add Waste Entry

```
curl -X POST http://localhost:8080/api/family \
-H "Content-Type: application/json" \
-d '{
  "familyName": "Smith Family",
  "wasteType": "Plastic",
  "quantity": 5.5
}'
```

### Update Waste Status

```
curl -X PUT http://localhost:8080/api/center/1 \
-H "Content-Type: application/json" \
-d '{
  "status": "PROCESSING"
}'
```

### Get All Entries

```
curl http://localhost:8080/api/center
```

---

## Configuration

You can modify the backend configuration in:

```
src/main/resources/application.properties
```

Common settings include:

* Server port
* Database configuration
* JPA settings
* Logging level

---

## Frontend Integration

This backend is configured to work with the React frontend running at:

```
http://localhost:5173
```

CORS is enabled so the frontend can access backend APIs without issues.

---

## Development Mode

For development with faster reload:

```
mvn spring-boot:run -Dspring-boot.run.fork=false
```

---

## Notes

* The H2 database is **in-memory**, so data resets when the application restarts.
* Security is simplified for development purposes.
* In production, authentication should use **JWT tokens** and **BCrypt password encryption**.

---

## Code Quality

This project includes **SonarCloud configuration** for code quality analysis and continuous inspection.

---

## Author

Developed by **Jeynisha E**
BCA Student | Aspiring Full Stack Developer


