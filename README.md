# Voting Application (Spring Boot)

A simple REST API for managing elections and casting votes.  
This project was prepared as a recruitment assignment.

## Tech Stack
- Java 17
- Spring Boot 3.5.9 (Web, Validation, Data JPA)
- PostgreSQL (Docker) + H2 (local/dev & tests)
- Maven

Docker is required **only** to run the application with PostgreSQL.
If Docker is not available, the application can be run locally using the H2 in-memory database.

---

## Quick Start

### Option A: Run without Docker (H2)


This option is intended for quick local development and manual testing.
No Docker or external services are required.

```bash
SPRING_PROFILES_ACTIVE=h2 ./mvnw spring-boot:run
```

Open:

* [http://localhost:8080](http://localhost:8080) (static landing page)

### Option B: Run with Docker (PostgreSQL)

This option requires Docker and Docker Compose.
It provides a production-like environment using PostgreSQL.

```bash
docker compose up --build
```

Services:

* API: [http://localhost:8080](http://localhost:8080)
* PostgreSQL: localhost:5432

Configuration is provided via `.env` (see `.env.example`).

---

## Configuration Profiles

* `h2` – local development / quick manual testing (in-memory)
* `postgres` – Docker / production-like environment
* `test` – integration tests (H2)

---

## API Endpoints (high level)

* `POST /api/voters` – create voter
* `GET /api/voters/{id}` – get voter
* `PATCH /api/voters/{id}/status` – activate/deactivate voter
* `POST /api/elections` – create election
* `GET /api/elections/{id}` – get election
* `POST /api/elections/{id}/options` – add option
* `GET /api/elections/{id}/options` – list options
* `POST /api/votes` – cast vote

---

## Business Rules

* A voter must be active to cast a vote (otherwise `403`).
* A voter can vote only once per election (`409` on duplicate vote).
* Enforced both in service logic and by a DB unique constraint.

---

## Testing

### Run all tests

```bash
./mvnw test
```

Test types:

* Unit tests (Mockito) for service layer
* One integration test (Spring Boot + MockMvc + H2 profile `test`) verifying the "vote only once per election" rule

---

## Docker

### Scripts (optional)

```bash
./scripts/run-h2.sh
./scripts/run-tests.sh
./scripts/docker-up.sh
./scripts/docker-down.sh
```

---

## Architecture Decisions

- Controllers are kept thin; business logic resides in service layer.
- DTOs and explicit mappers are used to keep API contracts clear.
- Unidirectional JPA relations are preferred to avoid serialization and fetch issues.
- Spring profiles are used to isolate environments (h2 / postgres / test).
- Database constraints act as a final line of defense for business rules.

---

## Notes

* This project intentionally does not provide a full UI.
  `src/main/resources/static/index.html` is a minimal landing page to confirm the API is running.