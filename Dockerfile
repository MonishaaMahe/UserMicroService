# Use a lightweight Java runtime base image
FROM eclipse-temurin:21-jdk-alpine

# Set a working directory
WORKDIR /app

# Copy the built jar from target directory to the image as app.jar
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 9096

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
