# Payroll System
Microservice application for salary calculation and payment processing.  
Created as a practice project for implementing microservice architecture.

## Technologies
- Java 21
- Spring Boot 3.4
- Spring Cloud 2024
- PostgreSQL
- Liquibase
- Docker
- Testcontainers
- Kafka
- Keycloak

## Profiles
- `docker` - Default profile for containerized environment (auto-activated in Docker)
- `dev` - Local development profile

## Deployment
### 1. **Clone the repository:**

```bash
git clone https://github.com/lamashkevich/payroll-service.git
cd payroll-service
```

### 2. **Configure environment variables:**
Create .env file in the project root:
```
POSTGRES_PASSWORD=root
POSTGRES_USER=postgres
KC_DB_USER=keycloak
KC_DB_PASSWORD=root
CLIENT_SECRET=my-secret-key
```

### 3. **Run in Docker:**
```bash
docker-compose build --no-cache
docker-compose up -d
```
## Endpoints
- Payroll Service `http://localhost:8088`
- Eureka Dashboard `http://localhost:8761`
- Config Server `http://localhost:8888`
- Keycloak Admin Console `http://localhost:8080`
- Swagger UI `http://localhost:8088/swagger-ui.html`

## License
Distributed under the MIT License. See `LICENSE` for more information.

## Contacts
- Yauheni Lamashkevich - [@lamashkevich](https://github.com/lamashkevich)
- Project Link: [https://github.com/lamashkevich/payroll-service](https://github.com/lamashkevich/payroll-service)
