# Use a lightweight Java runtime base image
FROM eclipse-temurin:21-jdk-alpine

# Set a working directory
WORKDIR /app

# Copy the built jar from target/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose port if needed (e.g., 8080)
EXPOSE 9090

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
