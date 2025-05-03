FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .
COPY tax-service/pom.xml ./tax-service/pom.xml
COPY tax-service/src ./tax-service/src

RUN mvn -f tax-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/tax-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]