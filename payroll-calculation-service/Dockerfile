FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .
COPY payroll-calculation-service/pom.xml ./payroll-calculation-service/pom.xml
COPY payroll-calculation-service/src ./payroll-calculation-service/src

RUN mvn -f payroll-calculation-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/payroll-calculation-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]