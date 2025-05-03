FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .
COPY eureka-server/pom.xml ./eureka-server/pom.xml
COPY eureka-server/src ./eureka-server/src

RUN mvn -f eureka-server/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/eureka-server/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]