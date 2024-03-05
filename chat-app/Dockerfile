# Use ubuntu:latest as the build image
FROM ubuntu:latest AS build

# Install OpenJDK-17 and Maven
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

# Set the working directory in the build image. Not strictly necessary now, but good practice.
WORKDIR /build

# Copy your project files into the build container. Since there's no subfolder, copy everything from the current directory.
# The context of the build is the root of your repository, so the '.' here refers to that root.
COPY . /build/

# Use Maven to package the application. This command now works directly because pom.xml is in the current directory.
RUN mvn clean package -DskipTests

# Use openjdk:17-jdk-slim for the runtime image
FROM openjdk:17-jdk-slim

# Expose port 8080
EXPOSE 8080

# Copy the JAR file from the build stage to the runtime container
# Adjust the JAR file name as necessary to match your project's output.
COPY --from=build /build/target/*.jar /app.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "/app.jar"]