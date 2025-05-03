FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .

COPY payment-service/pom.xml ./payment-service/pom.xml
COPY payment-service/src ./payment-service/src

RUN mvn -f payment-service/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/payment-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]