## Prerequisites

Make sure the following are installed:

* Java 25
* Docker
* Docker Compose

## 1. Build the Application into a JAR

Build the Spring Boot application using the Maven Wrapper.

```bash
./mvnw clean package -DskipTests
```

## 2. Start the Application with Docker Compose

After successfully building the JAR, start the application:

```bash
docker compose up -d --build
```

The `--build` option ensures that the backend Docker image is rebuilt using the newly generated JAR.

The application consists of:

* **Backend** — Spring Boot application
* **Database** — PostgreSQL
* **pgAdmin** — PostgreSQL administration interface

## 3. Check Running Containers

Run:

```bash
docker compose ps
```

or Check Docker Desktop

These services should be running:

```text
acmparcel-dev_db
acmparcel-pgadmin
acmparcel-backend
```

## 4. Access the Application

### Backend

The Spring Boot backend is available at:

```text
http://localhost:8080
```

### pgAdmin

pgAdmin is available at:

```text
http://localhost:5050
```

Use the credentials configured in your `.env` file.

## 5. Database Configuration

The PostgreSQL configuration is controlled through environment variables.

Create a `.env` file in the project root:

```env
POSTGRES_DB=shipping_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres

PGADMIN_EMAIL=admin@example.com
PGADMIN_PASSWORD=admin
```

The backend connects to PostgreSQL through the Docker service name:

```text
db:5432
```

The relevant Docker configuration is:

```yaml
environment:
  SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/${POSTGRES_DB}
  SPRING_DATASOURCE_USERNAME: ${POSTGRES_USER}
  SPRING_DATASOURCE_PASSWORD: ${POSTGRES_PASSWORD}
```

## 6. Database Persistence

PostgreSQL data is stored in the Docker volume:

```text
postgres_data
```

The volume is mounted to:

```text
/var/lib/postgresql
```

Therefore, stopping or recreating the containers does not delete the database data.

To stop the application:

```bash
docker compose down
```

Then start it again:

```bash
docker compose up -d
```

The existing PostgreSQL data will be preserved.

## 7. Rebuild the Application

Whenever Java source code changes, rebuild the JAR:

### Windows

```bash
.\mvnw.cmd clean package -DskipTests
```

### Linux / macOS

```bash
./mvnw clean package -DskipTests
```

Then rebuild and restart the backend:

```bash
docker compose up -d --build backend
```

## 8. Stop the Application

Stop all services:

```bash
docker compose down
```

This removes the containers but **preserves the PostgreSQL and pgAdmin volumes**.

To start the application again:

```bash
docker compose up -d
```

## Quick Start

For normal development, the complete workflow is:

```bash
# 1. Build the Spring Boot JAR
.\mvnw.cmd clean package -DskipTests

# 2. Build the Docker image and start all services
docker compose up -d --build

# 3. Check the containers
docker compose ps

# 4. View backend logs if needed
docker compose logs -f backend
```

Application:

```text
Backend: http://localhost:8080
pgAdmin: http://localhost:5050
```
