
# 🚀 Servix – Service Marketplace API

A production-ready REST API for a **service marketplace platform** that connects customers with verified service providers. Built using **Spring Boot**, **Java**, and **PostgreSQL**, the project follows a layered architecture and a normalized relational database design to provide scalable, secure, and efficient backend services.

---

## ✨ Features

* 👤 Customer registration and authentication
* 🛠️ Service provider management
* 📅 Service booking and scheduling
* ⭐ Ratings and reviews
* 📂 Service category management
* 🔍 Search and filtering
* 👨‍💼 Admin operations
* 🔐 Secure RESTful APIs
* ⚡ Global exception handling
* 🗄️ PostgreSQL database with **23 BCNF-normalized relational tables**
* 🔗 **19 REST API endpoints** covering all major marketplace operations

---

## 🏆 Key Achievements

* Designed a **23-table BCNF-normalized PostgreSQL database** to ensure high data integrity and minimal redundancy.
* Developed **19 RESTful API endpoints** for customers, service providers, bookings, reviews, authentication, and administration.
* Followed a clean **Controller → Service → Repository** architecture for maintainability and scalability.
* Implemented optimized SQL queries, centralized exception handling, and request validation.

---

## 🏗️ Tech Stack

**Backend**

* Java 21
* Spring Boot
* Spring JDBC
* Maven

**Database**

* PostgreSQL

**Tools**

* Docker
* Docker Compose
* Git
* Postman

---

## 📂 Project Structure

```text
src/
 ├── controller/
 ├── service/
 ├── repository/
 ├── config/
 └── resources/

sql/
 ├── servix_ddl.sql
 ├── servix_data.sql
 └── servix_queries.sql

docs/
 ├── ERD
 ├── Schema
 └── Normalization Proof
```

---

## 🚀 Getting Started

### Prerequisites

* Java 21+
* PostgreSQL
* Maven
* Docker (Optional)

### Installation

```bash
git clone https://github.com/KrIsH-1206/SERVIX-API.git

cd SERVIX-API

mvn clean install

mvn spring-boot:run
```

The API will start on:

```
http://localhost:8080
```

---

## 🗄️ Database

The project uses a **BCNF-normalized PostgreSQL database** consisting of multiple relational tables designed to minimize redundancy while maintaining efficient querying.

Database scripts included:

* Database Schema (DDL)
* Sample Data
* SQL Queries
* ER Diagram
* Normalization Proof

---

## 🏛️ Architecture

The project follows a layered architecture:

```
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
PostgreSQL Database
```

---

## 📌 Highlights

* RESTful API architecture
* Clean layered design
* PostgreSQL relational database
* SQL query optimization
* Dockerized deployment
* Centralized exception handling
* API documentation using Swagger
* Production-ready backend structure

---

## 👨‍💻 Author

**Krish Vamja**
