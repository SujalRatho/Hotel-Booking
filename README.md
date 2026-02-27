# 🏨 Hotel Booking System

A full-stack, microservices-based hotel booking application built with **Spring Boot**, **Spring Cloud**, and **React**.

Users can browse hotels, search by location, view room categories and pricing, book rooms with date selection, and view their complete booking history — all through a modern, responsive SPA frontend.

---

## 🏗 Architecture

The project follows a microservices architecture with service discovery and an API gateway for unified routing.

```
                         ┌──────────────┐
                         │   Frontend   │
                         │  (React/Vite)│
                         │  Port: 5173  │
                         └──────┬───────┘
                                │
                         ┌──────▼───────┐
                         │  API Gateway │
                         │  Port: 8765  │
                         └──────┬───────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
        ┌───────▼──────┐ ┌─────▼──────┐ ┌──────▼───────┐
        │ Auth Service │ │Hotel Service│ │Booking Service│
        │  Port: 8085  │ │ Port: 8082 │ │  Port: 8083  │
        └──────────────┘ └────────────┘ └──────────────┘
                │               │               │
                └───────────────┼───────────────┘
                                │
                      ┌─────────▼─────────┐
                      │ Service Registry  │
                      │  (Eureka Server)  │
                      │   Port: 8761      │
                      └───────────────────┘
```

### Backend Services (Spring Boot)

| Service | Directory | Port | Description |
|---|---|---|---|
| **Service Registry** | `service-registry` | `8761` | Netflix Eureka server for service registration and discovery |
| **API Gateway** | `api-gateway` | `8765` | Spring Cloud Gateway — single entry point for all client requests |
| **Auth Service** | `auth-service` | `8085` | User registration, login, and JWT token generation/validation |
| **Hotel Service** | `Hotel-service` | `8082` | Hotel catalog management — hotels, rooms, categories, pricing, and availability |
| **Booking Service** | `bookinghotel-service` | `8083` | Booking creation and management with persistent booking history |

### Frontend (React + Vite)

| Directory | Port | Description |
|---|---|---|
| `frontend` | `5173` | Modern SPA with hotel browsing, search, authentication, and booking management |

---

## ✨ Features

### 🔐 Authentication
- User registration and login with JWT-based security
- Protected routes for authenticated-only pages (e.g., My Bookings)
- Token stored client-side for persistent sessions

### 🏠 Hotel Browsing
- **Home Page** — Featured hotels with images, ratings, and locations
- **Search Page** — Search hotels by location with real-time results
- Room details including category, price, available inventory, and facilities

### 📋 Booking Management
- Book rooms with check-in / check-out date selection and total price calculation
- **My Bookings** page — Full booking history with hotel name, room category, dates, and pricing
- Booking data is enriched with hotel and room details at creation time for persistent history

### 🧭 Navigation & UI
- Responsive Navbar and Footer components
- Client-side routing via React Router
- Icons powered by Lucide React

---

## 🛠 Tech Stack

| Layer | Technologies |
|---|---|
| **Backend** | Java 17, Spring Boot, Spring Cloud (Eureka, Gateway), Spring Security (JWT), Spring Data JPA, OpenFeign |
| **Frontend** | React 18, Vite 5, React Router 7, Axios, Lucide React |
| **Database** | PostgreSQL |
| **Build** | Maven (backend), npm (frontend) |

---

## 📁 Project Structure

```
Hotel-Booking/
├── service-registry/          # Eureka Service Registry
├── api-gateway/               # Spring Cloud API Gateway
├── auth-service/              # Authentication & JWT Service
│   └── controller/UserController        (/auth/register, /auth/generate-token)
├── Hotel-service/             # Hotel & Room Management Service
│   └── controller/HotelController       (/hotels CRUD)
│   └── controller/RoomController        (/rooms CRUD)
├── bookinghotel-service/      # Booking Service
│   └── controller/BookingController     (/bookings CRUD)
├── frontend/                  # React SPA
│   └── src/
│       ├── components/        # Navbar, Footer
│       ├── context/           # AuthContext (JWT state)
│       ├── pages/             # HomePage, SearchPage, LoginPage, MyBookingsPage
│       └── services/          # API client (Axios)
└── README.md
```

---

## 🗄 Database Setup

The application uses **PostgreSQL**. Create the following databases before starting the services:

```sql
CREATE DATABASE auth_db;
CREATE DATABASE hotel_db;
CREATE DATABASE booking_db;
```

> Update the `spring.datasource.*` properties in each service's `application.properties` with your PostgreSQL credentials.

---

## 🚀 How to Run Locally

### Prerequisites
- **Java 17** or higher
- **Maven** (wrapper included)
- **Node.js** (v16+) and **npm**
- **PostgreSQL** running locally with the databases created above

### 1. Start Backend Microservices

Start services in this order (each in its own terminal):

```bash
# 1. Eureka Service Registry (start first)
cd service-registry
./mvnw spring-boot:run

# 2. API Gateway
cd api-gateway
./mvnw spring-boot:run

# 3. Auth Service
cd auth-service
./mvnw spring-boot:run

# 4. Hotel Service
cd Hotel-service
./mvnw spring-boot:run

# 5. Booking Service
cd bookinghotel-service
./mvnw spring-boot:run
```

> **Windows:** Use `.\mvnw.cmd spring-boot:run` instead of `./mvnw`.

### 2. Start the Frontend

```bash
cd frontend
npm install        # first time only
npm run dev
```

The app will be available at **[http://localhost:5173](http://localhost:5173)**.

---

## � API Endpoints (via Gateway — port 8765)

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/register` | Register a new user |
| `POST` | `/auth/generate-token` | Login and get JWT token |
| `GET` | `/hotels` | List all hotels |
| `GET` | `/hotels/{id}` | Get hotel details with rooms |
| `GET` | `/rooms/{hotelId}` | Get rooms for a hotel |
| `POST` | `/bookings` | Create a new booking |
| `GET` | `/bookings` | Get bookings for logged-in user |

---

## 📄 License

This project is for educational and portfolio purposes.
