# First stage: Build with Maven
FROM ubuntu:latest AS build

# Install dependencies
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

# Set the working directory in the container
WORKDIR /app

# Copy the local project files to the container
COPY . .

# Build the application with Maven
RUN mvn clean install

# Second stage: Run the app
FROM openjdk:17-jdk-slim

# Expose the port
EXPOSE 8080

# Copy the built JAR file from the build stage
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]