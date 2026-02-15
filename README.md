# ⚡ NEON LEDGER | District 01 Core Banking

A professional-grade Full-stack banking application built with a focus on financial precision, secure transaction protocols, and a modern Cyberpunk interface.

## 🚀 Key Medior-Level Implementations
* **Financial Integrity:** Uses `BigDecimal` for all monetary calculations to prevent floating-point inaccuracies.
* **Secure Layered Architecture:** Implements a clean separation between Controllers, Services, and Repositories.
* **Automated Testing Suite:** Robust unit testing using **JUnit 5** and **Mockito** for business logic verification.
* **API Ecosystem:** Fully documented via **OpenAPI/Swagger UI**.
* **Security:** Integrated **Spring Security** with Basic Auth protocol for node-to-node communication.

## 🛠️ Tech Stack
- **Backend:** Java 21, Spring Boot 3.4.2, Spring Data JPA, Hibernate, Spring Security.
- **Database:** H2 In-Memory (Configured for rapid development and demo cycles).
- **Frontend:** React 18, Axios, Modern CSS Grid (Neon-Grid System).

## 📦 Project Structure
- `/core-banking`: The Spring Boot backend engine.
- `/banking-frontend`: The React-based terminal interface.

## 🚦 Getting Started

### Backend
1. Navigate to `/core-banking`.
2. Run `./mvnw spring-boot:run`.
3. API Documentation (Swagger): `http://localhost:8080/swagger-ui.html`
4. Database Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:bankdb`)

### Frontend
1. Navigate to `/banking-frontend`.
2. Run `npm install` and `npm start`.
3. Terminal access: `http://localhost:3000`

---
*Developed by Marek Jankovič as a demonstration of high-availability banking systems.*