FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package
FROM eclipse-temurin:21.0.2_13-jre-alpine
COPY --from=builder /app/target/techtest.jar /app/app.jar
WORKDIR /app
CMD ["java", "-jar", "app.jar"]