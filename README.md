# 🚗 RideShare Backend Application

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=spring-boot)
![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-47A248?style=for-the-badge&logo=mongodb)
![JWT](https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=json-web-tokens)
![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk)

**A modern, secure ride-sharing platform backend powered by Spring Boot, MongoDB Atlas, and JWT authentication**

[Overview](#-overview) • [Core Features](#-core-features) • [Installation Guide](#-installation-guide) • [API Reference](#-api-reference) • [Security Details](#-security-details)

</div>

---

## 📖 Overview

This RideShare backend system provides a comprehensive solution for connecting passengers with drivers through a secure, scalable RESTful API. Built on enterprise-grade technologies, it handles everything from user authentication to complete ride lifecycle management with MongoDB Atlas for cloud data persistence and JWT for stateless authentication.

---

## 📑 Table of Contents

- [Overview](#-overview)
- [Core Features](#-core-features)
- [Technology Ecosystem](#-technology-ecosystem)
- [System Architecture](#-system-architecture)
- [Installation Guide](#-installation-guide)
- [Environment Setup](#-environment-setup)
- [API Reference](#-api-reference)
- [Data Models](#-data-models)
- [Security Details](#-security-details)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🎯 Core Features

### 🔐 User Authentication & Authorization
- Secure user onboarding with **BCrypt password hashing**
- Token-based authentication using **JWT (JSON Web Tokens)**
- Custom authentication service implementation
- Multi-role support: `USER` and `DRIVER` roles
- JWT-protected API endpoints with automatic validation

### 🚖 Comprehensive Ride Operations
- Seamless ride booking by passengers with location details
- Real-time visibility of available ride requests for drivers
- One-click ride acceptance by available drivers
- End-to-end ride tracking with status management
- Automated ride lifecycle updates

### 📈 Ride Lifecycle States
```
REQUESTED → ACCEPTED → ONGOING → COMPLETED
                    ↘
                  CANCELLED
```

### 💾 Cloud Database Integration
- MongoDB Atlas cloud database connectivity
- Optimized document collections for scalability
- Query performance enhancement through indexing
- Automated backup and recovery

---

## 🔧 Technology Ecosystem

| Layer | Technology Stack |
|-------|-----------------|
| **Backend Framework** | Spring Boot 3.x |
| **Programming Language** | Java 17+ |
| **Cloud Database** | MongoDB Atlas |
| **Security Layer** | Spring Security + JWT (HS256) |
| **Password Hashing** | BCrypt Algorithm |
| **Dependency Management** | Maven |
| **API Development** | Spring Web MVC |

---

## 🏛 System Architecture

```
┌──────────────────────┐
│   REST Controllers   │  ← HTTP Requests/Responses
│   (API Endpoints)    │
└──────────┬───────────┘
           │
┌──────────▼───────────┐
│  Business Services   │  ← Core Logic & Validation
│   (Service Layer)    │
└──────────┬───────────┘
           │
┌──────────▼───────────┐
│  Data Repositories   │  ← Database Operations
│  (Repository Layer)  │
└──────────┬───────────┘
           │
┌──────────▼───────────┐
│  MongoDB Atlas DB    │  ← Cloud Data Storage
│   (NoSQL Database)   │
└──────────────────────┘
```

**Key Architectural Principles:**
- Separation of concerns across layers
- Dependency injection for loose coupling
- Repository pattern for data access
- Service layer for business logic encapsulation

---

## 🚀 Installation Guide

### System Requirements

Ensure your development environment has:

- **Java Development Kit (JDK)** - Version 17 or newer
- **Apache Maven** - Version 3.6 or higher
- **MongoDB Atlas Account** - Free tier available
- **Git** - For version control
- **IDE** - IntelliJ IDEA, Eclipse, or VS Code recommended

### Step-by-Step Setup

**1. Repository Cloning**
```bash
git clone https://github.com/Adhyayan2107/RideShare-Assignment.git
cd RideShare-Assignment
```

**2. MongoDB Atlas Configuration**

Create a MongoDB cluster on [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) and configure the connection in `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: rideshare-backend
  data:
    mongodb:
      uri: mongodb+srv://<your-username>:<your-password>@cluster0.mongodb.net/rideshare_db
```

**3. JWT Secret Configuration**

Define your JWT secret key in the configuration file:

```yaml
jwt:
  secret: YourSecure256BitSecretKeyForJWTTokenGeneration12345
  expiration-ms: 86400000  # Token validity: 24 hours
```

**4. Dependency Installation**
```bash
./mvnw clean install
```

**5. Application Launch**
```bash
./mvnw spring-boot:run
```

✅ **Success!** The application will be accessible at `http://localhost:8080`

---

## ⚙️ Environment Setup

### Primary Configuration File

**Location:** `src/main/resources/application.yml`

```yaml
spring:
  application:
    name: rideshare-backend
  data:
    mongodb:
      uri: mongodb+srv://<username>:<password>@cluster0.mongodb.net/rideshare_db
      database: rideshare_db

jwt:
  secret: YourSecure256BitSecretKeyHere
  expiration-ms: 86400000

server:
  port: 8080
  servlet:
    context-path: /api
```

### Alternative: Environment Variables

Set these as system environment variables:

```bash
export MONGODB_URI="mongodb+srv://<username>:<password>@cluster0.mongodb.net/rideshare_db"
export JWT_SECRET="YourSecure256BitSecretKeyHere"
export JWT_EXPIRATION="86400000"
export SERVER_PORT="8080"
```

---

## 📘 API Reference

### API Base Configuration
```
Base URL: http://localhost:8080/api
Content-Type: application/json
Authorization: Bearer <JWT-TOKEN> (for protected routes)
```

---

### 🔑 Authentication APIs

#### Register New User
Create a new passenger account in the system.

**Endpoint:** `POST /auth/register`

**Request Body:**
```json
{
  "username": "passenger_john",
  "password": "securePassword123",
  "role": "USER"
}
```

**Success Response (201):**
```json
{
  "id": "6751f2a4b8c9e1234567890a",
  "username": "passenger_john",
  "role": "USER",
  "createdAt": "2024-12-07T10:30:00.000Z"
}
```

---

#### Register New Driver
Create a new driver account with appropriate permissions.

**Endpoint:** `POST /auth/register`

**Request Body:**
```json
{
  "username": "driver_sarah",
  "password": "securePassword456",
  "role": "DRIVER"
}
```

**Success Response (201):**
```json
{
  "id": "6751f2a4b8c9e1234567890b",
  "username": "driver_sarah",
  "role": "DRIVER",
  "createdAt": "2024-12-07T10:35:00.000Z"
}
```

---

#### User Login
Authenticate user credentials and receive JWT token.

**Endpoint:** `POST /auth/login`

**Request Body:**
```json
{
  "username": "passenger_john",
  "password": "securePassword123"
}
```

**Success Response (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2NzUxZjJhNGI4YzllMTIzNDU2Nzg5MGEiLCJ1c2VybmFtZSI6InBhc3Nlbmdlcl9qb2huIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3MzM1Nzg4MDAsImV4cCI6MTczMzY2NTIwMH0.signature",
  "type": "Bearer",
  "expiresIn": 86400000
}
```

---

### 🚕 Passenger APIs

#### Book a New Ride
Submit a ride request with pickup and destination details.

**Endpoint:** `POST /rides/request`  
**Authorization:** Required (Bearer Token)

**Request Headers:**
```
Authorization: Bearer <your-jwt-token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "pickupLocation": "Koramangala 5th Block",
  "dropLocation": "Indiranagar Metro Station"
}
```

**Success Response (201):**
```json
{
  "id": "6751f3d5c8b9e1234567890c",
  "userId": "6751f2a4b8c9e1234567890a",
  "pickupLocation": "Koramangala 5th Block",
  "dropLocation": "Indiranagar Metro Station",
  "status": "REQUESTED",
  "fare": null,
  "driverId": null,
  "createdAt": "2024-12-07T11:00:00.000Z"
}
```

---

#### View Ride History
Retrieve all rides associated with the authenticated user.

**Endpoint:** `GET /user/rides`  
**Authorization:** Required (Bearer Token)

**Request Headers:**
```
Authorization: Bearer <your-jwt-token>
```

**Success Response (200):**
```json
[
  {
    "id": "6751f3d5c8b9e1234567890c",
    "pickupLocation": "Koramangala 5th Block",
    "dropLocation": "Indiranagar Metro Station",
    "status": "COMPLETED",
    "fare": 185.50,
    "driverId": "6751f2a4b8c9e1234567890b",
    "createdAt": "2024-12-07T11:00:00.000Z",
    "acceptedAt": "2024-12-07T11:02:30.000Z",
    "completedAt": "2024-12-07T11:25:15.000Z"
  },
  {
    "id": "6751f4e6d9c0e1234567890d",
    "pickupLocation": "MG Road",
    "dropLocation": "Electronic City",
    "status": "REQUESTED",
    "fare": null,
    "driverId": null,
    "createdAt": "2024-12-07T12:15:00.000Z"
  }
]
```

---

### 🚗 Driver APIs

#### View Available Ride Requests
Fetch all pending ride requests awaiting driver acceptance.

**Endpoint:** `GET /driver/rides/requests`  
**Authorization:** Required (Bearer Token - Driver Role)

**Request Headers:**
```
Authorization: Bearer <driver-jwt-token>
```

**Success Response (200):**
```json
[
  {
    "id": "6751f4e6d9c0e1234567890d",
    "userId": "6751f2a4b8c9e1234567890a",
    "pickupLocation": "MG Road",
    "dropLocation": "Electronic City",
    "status": "REQUESTED",
    "estimatedFare": 320.00,
    "createdAt": "2024-12-07T12:15:00.000Z"
  },
  {
    "id": "6751f5f7e0d1e1234567890e",
    "userId": "6751f3a5c8b9e1234567890f",
    "pickupLocation": "Whitefield",
    "dropLocation": "Hebbal",
    "status": "REQUESTED",
    "estimatedFare": 275.50,
    "createdAt": "2024-12-07T12:30:00.000Z"
  }
]
```

---

#### Accept Ride Request
Claim a ride request and start the journey.

**Endpoint:** `POST /driver/rides/{rideId}/accept`  
**Authorization:** Required (Bearer Token - Driver Role)

**Path Parameters:**
- `rideId` - The unique identifier of the ride to accept

**Request Headers:**
```
Authorization: Bearer <driver-jwt-token>
```

**Success Response (200):**
```json
{
  "id": "6751f4e6d9c0e1234567890d",
  "userId": "6751f2a4b8c9e1234567890a",
  "driverId": "6751f2a4b8c9e1234567890b",
  "pickupLocation": "MG Road",
  "dropLocation": "Electronic City",
  "status": "ACCEPTED",
  "fare": 320.00,
  "acceptedAt": "2024-12-07T12:32:45.000Z"
}
```

---

### ✅ Ride Completion API

#### Mark Ride as Complete
Finalize the ride and update the status to completed.

**Endpoint:** `POST /rides/{rideId}/complete`  
**Authorization:** Required (Bearer Token - Driver Role)

**Path Parameters:**
- `rideId` - The unique identifier of the active ride

**Request Headers:**
```
Authorization: Bearer <driver-jwt-token>
```

**Success Response (200):**
```json
{
  "id": "6751f4e6d9c0e1234567890d",
  "userId": "6751f2a4b8c9e1234567890a",
  "driverId": "6751f2a4b8c9e1234567890b",
  "pickupLocation": "MG Road",
  "dropLocation": "Electronic City",
  "status": "COMPLETED",
  "fare": 320.00,
  "acceptedAt": "2024-12-07T12:32:45.000Z",
  "completedAt": "2024-12-07T13:15:30.000Z"
}
```

---

## 🗂 Data Models

### User Document Schema

```javascript
{
  "_id": ObjectId,                    // Unique user identifier
  "username": String,                 // Unique username (indexed)
  "password": String,                 // BCrypt hashed password
  "role": String,                     // Enum: ["USER", "DRIVER"]
  "createdAt": ISODate,              // Account creation timestamp
  "updatedAt": ISODate               // Last modification timestamp
}
```

**Indexes:**
- `username` - Unique index for fast lookups
- `role` - Index for role-based queries

---

### Ride Document Schema

```javascript
{
  "_id": ObjectId,                    // Unique ride identifier
  "userId": ObjectId,                 // Reference to User collection
  "driverId": ObjectId,               // Reference to Driver (nullable)
  "pickupLocation": String,           // Starting point address
  "dropLocation": String,             // Destination address
  "status": String,                   // Enum: ["REQUESTED", "ACCEPTED", "ONGOING", "COMPLETED", "CANCELLED"]
  "fare": Number,                     // Final ride cost (nullable until completion)
  "createdAt": ISODate,              // Ride request timestamp
  "acceptedAt": ISODate,             // Driver acceptance timestamp (nullable)
  "completedAt": ISODate,            // Ride completion timestamp (nullable)
  "cancelledAt": ISODate             // Cancellation timestamp (nullable)
}
```

**Indexes:**
- `userId` - For user ride history queries
- `driverId` - For driver ride history queries
- `status` - For status-based filtering
- Compound index: `(status, createdAt)` - For pending rides ordering

---

## 🔒 Security Details

### Authentication Mechanism

**Multi-Step Secure Flow:**

1. **User Registration**
   - User provides username and password
   - Password is hashed using BCrypt (10 salt rounds)
   - Hashed credentials stored in MongoDB

2. **User Login**
   - Credentials validated against stored hash
   - JWT token generated upon successful authentication
   - Token contains user ID, username, and role claims

3. **Request Authorization**
   - Client includes JWT in Authorization header
   - Spring Security validates token signature and expiration
   - User context extracted from valid tokens
   - Role-based access control applied to endpoints

### Password Security Standards

- **Algorithm:** BCrypt with adaptive hashing
- **Salt Rounds:** 10 (configurable)
- **Storage:** Only hashed passwords persisted
- **Protection:** Resistant to rainbow table and brute-force attacks

### JWT Token Specifications

- **Signing Algorithm:** HMAC-SHA256 (HS256)
- **Token Lifespan:** 24 hours (86400000 milliseconds)
- **Payload Claims:**
  - `sub` - User ID (subject)
  - `username` - User's username
  - `role` - User's role (USER/DRIVER)
  - `iat` - Token issued at timestamp
  - `exp` - Token expiration timestamp
- **Validation:** Automatic signature and expiration verification on each request

### Role-Based Access Control

| Endpoint Pattern | Required Role | Description |
|-----------------|---------------|-------------|
| `/auth/**` | PUBLIC | Authentication endpoints |
| `/rides/request` | USER | Ride booking |
| `/user/rides` | USER | User ride history |
| `/driver/rides/**` | DRIVER | Driver operations |
| `/rides/{id}/complete` | DRIVER | Ride completion |

---

## 🧪 Testing Guide

### Testing Workflow Example

**Step 1:** Register a passenger
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test_user","password":"pass123","role":"USER"}'
```

**Step 2:** Register a driver
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test_driver","password":"pass123","role":"DRIVER"}'
```

**Step 3:** Login as passenger and save token
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test_user","password":"pass123"}'
```

**Step 4:** Request a ride using the passenger token
```bash
curl -X POST http://localhost:8080/api/rides/request \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <passenger-token>" \
  -d '{"pickupLocation":"Location A","dropLocation":"Location B"}'
```

**Step 5:** Login as driver and accept the ride
```bash
curl -X POST http://localhost:8080/api/driver/rides/<ride-id>/accept \
  -H "Authorization: Bearer <driver-token>"
```

---

## 👨‍💻 Author

**Adhyayan**

- GitHub: [@Adhyayan2107](https://github.com/Adhyayan2107)
- Project: [RideShare-Assignment](https://github.com/Adhyayan2107/RideShare-Assignment)
