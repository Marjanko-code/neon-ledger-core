# ⚡ NEON LEDGER | District 01 Core Banking

A professional-grade Full-stack banking application built with a focus on financial precision, secure transaction protocols, and a modern Cyberpunk-styled terminal interface.

## 🏗️ System Architecture

```mermaid
graph TD
    subgraph Frontend_React [banking-frontend]
        UI[Neon Terminal UI]
        AX[Axios Client]
    end

    subgraph Backend_Spring_Boot [core-banking]
        CTRL[Transaction Controller]
        SRV[Transaction Service]
        SEC[Spring Security]
        REPO[JPA Repositories]
    end

    subgraph Database
        H2[(H2 In-Memory DB)]
    end

    UI -->|JSON Requests| AX
    AX -->|Basic Auth| SEC
    SEC --> CTRL
    CTRL --> SRV
    SRV --> REPO
    REPO --> H2
🧠 Engineering Decisions (ADR)
Atomic Transactions: Implemented the @Transactional annotation in the Service layer to ensure ACID properties. Money is never deducted from one account without being credited to the other (all-or-nothing principle).

Financial Precision: BigDecimal was chosen over Double or Float to prevent floating-point inaccuracies during currency calculations.

Security: Integrated Spring Security with Basic Authentication to simulate secure node-to-node communication protocols used in private banking networks.

API Documentation: The entire backend is documented via OpenAPI / Swagger UI, allowing for seamless frontend-backend integration and testing.

🚀 Key Technical Implementations
CI/CD Pipeline: Automated testing suite triggered via GitHub Actions on every push to ensure code stability.

Automated Testing: Comprehensive Unit and Integration tests using JUnit 5 and Mockito.

Containerization: Multi-stage Docker configuration for consistent environment deployment and reduced image size.

Global Exception Handling: Centralized error management system using @RestControllerAdvice for consistent API error responses.

🚦 Getting Started
Prerequisites
Java 21

Node.js (v18+)

Maven

Backend (core-banking)
Navigate to the directory: cd core-banking

Build and run: ./mvnw spring-boot:run

Swagger UI: http://localhost:8080/swagger-ui.html

H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:bankdb)

Frontend (banking-frontend)
Navigate to the directory: cd banking-frontend

Install dependencies: npm install

Start the application: npm start

Terminal UI: http://localhost:3000