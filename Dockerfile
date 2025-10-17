# 🏗️ Etapa 1: Build con Maven y Java 25
FROM maven:3.9-eclipse-temurin-25 AS builder
WORKDIR /app
COPY . .
RUN mvn clean test package -DskipTests

# 🚀 Etapa 2: Imagen final con solo el .jar
FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["/bin/sh", "-c", "java -Dspring.datasource.url=$SPRING_DATASOURCE_URL -Dspring.artemis.broker-url=$ACTIVEMQ_HOST -jar app.jar"]