# Use OpenJDK 21 as the base image for building
FROM openjdk:21-jdk-slim AS build

# Install Maven manually
RUN apt update && apt install -y maven

# Set the working directory
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use OpenJDK 21 to run the application
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/PasteBackend-0.0.1-SNAPSHOT.jar .

# Expose port 8080
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/PasteBackend-0.0.1-SNAPSHOT.jar"]
