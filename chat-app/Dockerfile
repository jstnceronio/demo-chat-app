# Use ubuntu:latest as the build image
FROM ubuntu:latest AS build

# Install OpenJDK-17
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

# Use Maven to package the application
RUN mvn clean package -DskipTests

# Use openjdk:17-jdk-slim for the runtime image
FROM openjdk:17-jdk-slim

# Expose port 8080
EXPOSE 8080

# Copy the JAR file from the build stage to the /app directory of the runtime container
COPY --from=build /app/target/chat-app-0.0.1-SNAPSHOT.jar chat-app.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "chat-app.jar"]
