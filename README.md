# Warranty Project

Backend API for tracking products, receipts, product registrations, and warranties, with JWT-based authentication/authorization and scheduled email reminders.

On startup the app also fetches Danish law text (XML) and persists extracted "garanti" content.

Implementation log:
- https://dannyshayh.github.io/Portfolio/posts/warranty/

## Tech Stack

- Java 17 (see `pom.xml` and `Dockerfile`)
- Maven
- Javalin (REST API)
- Hibernate ORM + HikariCP
- PostgreSQL
- JWT (Nimbus JOSE + JWT)
- BCrypt (password hashing)
- SendGrid (email reminders)
- JUnit + Rest Assured + Mockito (tests)

## Quick Start (Local)

### Prereqs

- JDK 17
- Maven
- PostgreSQL running on `localhost:5432`

### 1) Configure DB + JWT settings

Local (non-deployed) config is read from: `src/main/resources/config.properties`

Keys used:
- `DB_NAME`
- `DB_USERNAME`
- `DB_PASSWORD`
- `ISSUER`
- `TOKEN_EXPIRE_TIME` (interpreted as seconds in code)
- `SECRET_KEY`

Server:
- `http://localhost:7070`
- API base path: `http://localhost:7070/api`

Notes:
- Hibernate is configured with `hibernate.hbm2ddl.auto=create`, so schema is recreated on startup.
- Startup runs a data populator and an external XML fetch (requires internet access).

## API Overview

Base path: `/api`

Route overview (enabled in code):
- `GET /api/routes`

Healthcheck:
- `GET /api/security/healthcheck`

Auth (open endpoints):
- `POST /api/security/register`
- `POST /api/security/login` (returns JWT)

Protected resources (require `Authorization: Bearer <token>`):
- `/product` (`POST /`, `GET /all`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`)
- `/warranty` (`POST /`, `GET /all`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`)
- `/receipt` (`POST /`, `GET /all`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`)
- `/product-registration` (`POST /`, `GET /all`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`)
- `/user` (`GET /all`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`) (roles: USER/ADMIN)

### Example cURL flow

Register:

```bash
curl -sS -X POST "http://localhost:7070/api/security/register" \
  -H "Content-Type: application/json" \
  -d '{"email":"me@example.com","password":"12345678"}'
```

Login (copy the `token` from the response):

```bash
curl -sS -X POST "http://localhost:7070/api/security/login" \
  -H "Content-Type: application/json" \
  -d '{"email":"me@example.com","password":"12345678"}'
```

Call a protected endpoint:

```bash
curl -sS "http://localhost:7070/api/product/all" \
  -H "Authorization: Bearer <token>"
```

## IntelliJ HTTP Client Requests

Request files:
- `src/main/resources/http/*.http`

Environments:
- `src/main/resources/http/http-client.env.json`

## Email Reminders (SendGrid)

On startup, the app starts a daily scheduler that checks warranties and sends reminder emails at 09:00 (server local time) for:
- 90 days left
- 60 days left
- 30 days left
- 0 days left (expired)

Environment variable used:
- `SENDGRID_API_KEY`

## Domain Model

See diagrams in:
- `docs/domain_model1.png`
- `docs/Domain_model2.png`
- `docs/domain_model3.png`
