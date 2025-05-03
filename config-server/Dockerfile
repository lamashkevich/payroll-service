FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .
COPY config-server/pom.xml ./config-server/pom.xml
COPY config-server/src ./config-server/src

RUN mvn -f config-server/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/config-server/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]