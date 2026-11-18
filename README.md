# Corner Shop

A small Spring Boot shopping app: browse products, register, log in and out, and keep a cart.

**Stack:** Java 21, Spring Boot 4.1, Spring Security (form login), Spring Data JPA, Thymeleaf, H2 (local) / PostgreSQL (prod profile).

## Run locally

Requirements: JDK 21 and Maven 3.9+.

```bash
mvn spring-boot:run
```

Open http://localhost:8080. Register an account, log in, add items, open the cart.
Health check: http://localhost:8080/actuator/health

## Test

```bash
mvn test
```

## Package

```bash
mvn clean package
java -jar target/shop-0.0.1-SNAPSHOT.jar
```

## Push to GitHub

```bash
git init
git add .
git commit -m "Initial shopping app"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main
```

## Notes

- Local data lives in memory (H2), so it resets on every restart.
- The `prod` profile reads `DB_URL`, `DB_USER` and `DB_PASSWORD` from environment variables
  and is meant for PostgreSQL on AWS RDS. Start with `SPRING_PROFILES_ACTIVE=prod`.
- Never commit passwords or keys to the repo.
