# Use Maven base image to build the application
FROM maven:3.6-jdk-11-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the source code to the container
COPY pom.xml /app/
COPY HighwayApp.java /app/

# Download dependencies and package the application as a jar
RUN mvn clean package -DskipTests

# Use a smaller OpenJDK image for running the app
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the .jar file from the build stage into the final container
COPY --from=build /app/target/highway-app-1.0-SNAPSHOT.jar /app/highway-app.jar

# Run the .jar file when the container starts
CMD ["java", "-jar", "highway-app.jar"]
