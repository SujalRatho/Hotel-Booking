# Hotel Booking System

A full-stack, microservices-based hotel booking application built with Spring Boot and React.

## 🏗 Architecture

The project follows a microservices architecture, decoupling different domains into independent services.

### Backend Services (Spring Boot)
- **Service Registry (`service-registry`)**: Uses Netflix Eureka for service registration and discovery. Acts as a directory for all running microservices. (Port: `8761`)
- **API Gateway (`api-gateway`)**: Built with Spring Cloud Gateway. Serves as the single entry point for all frontend client requests, routing them to the appropriately matched underlying microservices. (Port: `8765`)
- **Auth Service (`auth-service`)**: Centralized service handling user registration, secure authentication, and JWT token generation/validation. (Port: `8085`)
- **Hotel Service (`Hotel-service`)**: Manages the core hotel catalog, including hotel details, categories, and inventory. (Port: `8082`)
- **Booking Service (`bookinghotel-service`)**: Handles the business logic for creating, managing, and persisting user hotel bookings. (Port: `8083`)

### Frontend (React)
- **Frontend (`frontend`)**: A modern Single Page Application (SPA) built with React and Vite. It provides an intuitive interface for users to search for hotels, authenticate, and manage their bookings. (Port: `5173`)

---

## 🛠 Prerequisites

To run this project locally, you will need:
- **Java 17** or higher
- **Maven** (Wrapper is included in the project)
- **Node.js** (v16+ recommended) and **npm**
- **Database** (e.g., MySQL) properly configured as per the `application.properties` of the respective microservices.

---

## 🚀 How to Run Locally

### 1. Start the Backend Microservices
Because of inter-service dependencies, services should ideally be started in a specific order. Ensure your local database server is running.

Open multiple terminal windows or use a terminal multiplexer to start the services:

```bash
# 1. Start Eureka Service Registry (IMPORTANT: Start this first)
cd service-registry
./mvnw spring-boot:run

# 2. Start API Gateway
cd api-gateway
./mvnw spring-boot:run

# 3. Start Authentication Service
cd auth-service
./mvnw spring-boot:run

# 4. Start Hotel Service
cd Hotel-service
./mvnw spring-boot:run

# 5. Start Booking Service
cd bookinghotel-service
./mvnw spring-boot:run
```

*Note: On Windows, use `.\mvnw.cmd spring-boot:run` instead of `./mvnw`.*

### 2. Start the Frontend Application
Open a new terminal window, navigate to the `frontend` directory, install the required dependencies, and launch the development server.

```bash
# Navigate to the frontend directory
cd frontend

# Install dependencies (only required the first time or when package.json changes)
npm install

# Start the Vite development server
npm run dev
```

The frontend application will now be running and accessible at [http://localhost:5173](http://localhost:5173).

---

## 💻 Technologies Used

- **Backend ecosystem**: Java, Spring Boot, Spring Cloud (Eureka server, API Gateway), Spring Security (JWT), Spring Data JPA, Lombok.
- **Frontend ecosystem**: React, Vite, HTML/CSS, JavaScript/JSX.
- **Database**: Relational Database Management System (RDBMS).
