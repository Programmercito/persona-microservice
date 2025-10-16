# 🏗️ Etapa 1: Build con Maven y Java 25
FROM maven:3.9-eclipse-temurin-25 AS builder
WORKDIR /app
COPY . .
RUN mvn clean test package -DskipTests

# 🚀 Etapa 2: Imagen final con solo el .jar
FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar","-Dspring.artemis.host=$ACTIVEMQ_HOST","-Dspring.datasource.url=$SPRING_DATASOURCE_URL"]
