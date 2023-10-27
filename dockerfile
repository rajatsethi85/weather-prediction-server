# Use an official OpenJDK runtime as a base image
FROM openjdk:11

# Set the working directory in the container
WORKDIR /weather-prediction-server

# Copy the JAR file from the host machine to the container
COPY target/weather-prediction-server.jar /weather-prediction-server/weather-prediction-server.jar

# Expose the port your Spring Boot application is running on
EXPOSE 8081

# Command to run the application
CMD ["java", "-jar", "weather-prediction-server.jar"]

