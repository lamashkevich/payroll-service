FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .
COPY employee-service/pom.xml ./employee-service/pom.xml
COPY employee-service/src ./employee-service/src

RUN mvn -f employee-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/employee-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]