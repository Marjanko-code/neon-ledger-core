# NEON LEDGER: District 01 Banking Core
**High-performance, secure transaction engine built with Spring Boot and React.**

---

## 🏗 System Architecture
The project follows a **Modular Monolith** pattern with a clear separation between layers.



### Key Design Decisions:
* **Precision Handling:** Used `BigDecimal` for all monetary calculations to prevent floating-point errors.
* **Atomic Transactions:** Implemented `@Transactional` at the service layer to ensure data integrity (Atomicity).
* **Security:** Leveraged **Spring Security** with Basic Auth for protected endpoints.
* **Performance:** Utilized **HikariCP** and **H2 In-Memory Database** for high-speed processing.

---

## 🛠 Tech Stack
* **Backend:** Java 24, Spring Boot 3.4.2, Spring Data JPA, Spring Security.
* **Frontend:** React 18, Axios, Cyberpunk-themed CSS3.
* **Database:** H2 (In-memory SQL).
* **Documentation:** SpringDoc OpenAPI (Swagger UI).
* **DevOps:** Docker (Multi-stage builds).

---

## 🧪 Testing Strategy (Medior Grade)
* **Integration Tests:** Using `@SpringBootTest` and `MockMvc` to verify the full API flow.
* **Data Consistency:** Automated checks to prevent negative balances.
* **Security Tests:** Verifying `401 Unauthorized` for unauthenticated requests.

---

## 🚀 Getting Started

### Prerequisites
* Node.js (v18+)
* JDK 21 or 24
* Maven 3.9+
* Docker (Optional)

### Running with Docker (Recommended)
```bash
docker-compose up --build
Manual Setup
Backend:

Bash

cd core-banking
mvn spring-boot:run
Frontend:

Bash

cd banking-frontend
npm install && npm start
📡 API Documentation
Access the interactive API docs at:
http://localhost:8080/swagger-ui/index.html

🔒 Security Compliance
CORS Configuration: Limited to http://localhost:3000.

Authentication: Basic Auth required for all /api/** endpoints.

Validation: Server-side validation for transaction parameters.
