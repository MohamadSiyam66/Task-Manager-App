# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (for better caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy full source and package the app
COPY src ./src
RUN mvn package -DskipTests

# ---------- Run Stage ----------
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Command to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
