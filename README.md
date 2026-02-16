# ⚡ NEON LEDGER | District 01 Core Banking

A professional-grade Full-stack banking application built with a focus on financial precision, secure transaction protocols, and a modern Cyberpunk-styled terminal interface.

### 🏗️ System Architecture

```mermaid

graph TD
    subgraph Frontend_React [banking-frontend]
        UI[Neon Terminal UI]
        AX[Axios Client]
    end

    subgraph Backend_Spring_Boot [core-banking/core-banking]
        CTRL[Transaction Controller]
        SRV[Transaction Service]
        SEC[Spring Security]
        REPO[JPA Repositories]
    end

    subgraph Database
        DB[(PostgreSQL - Railway)]
    end

    UI -->|JSON Requests| AX
    AX -->|Basic Auth| SEC
    SEC --> CTRL
    CTRL --> SRV
    SRV --> REPO
    REPO --> DB


🧠 Engineering Decisions (ADR)
Atomic Transactions: Implemented the @Transactional annotation in the Service layer to ensure ACID properties. Money is never deducted from one account without being credited to the other (all-or-nothing principle).

Financial Precision: BigDecimal was chosen over Double or Float to prevent floating-point inaccuracies during currency calculations.

Security: Integrated Spring Security with Basic Authentication to simulate secure node-to-node communication protocols used in private banking networks.

API Documentation: The entire backend is documented via OpenAPI / Swagger UI, allowing for seamless frontend-backend integration and testing.

🚀 Key Technical Implementations
CI/CD Pipeline: Automated testing suite triggered via GitHub Actions on every push to ensure code stability.

Automated Testing: Comprehensive Unit and Integration tests using JUnit 5 and Mockito.

Production Deployment: Hosted on Railway using Java 21 and managed PostgreSQL.

Global Exception Handling: Centralized error management system using @RestControllerAdvice for consistent API error responses.

🚦 Getting Started
Prerequisites
Java 21 (LTS)

Node.js (v18+)

Maven

Backend (core-banking)
Navigate to the directory:

Bash

cd core-banking/core-banking
Build and run:

Bash

./mvnw spring-boot:run
Swagger UI: http://localhost:8080/swagger-ui.html

Production API: https://your-railway-backend-url.up.railway.app

Frontend (banking-frontend)
Navigate to the directory:

Bash

cd banking-frontend
Install dependencies:

Bash

npm install
Start the application:

Bash

npm start
Terminal UI: http://localhost:3000

Created by Marek Jankovič as part of the District 01 Core Banking project.
