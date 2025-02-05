FROM openjdk:11-jre-slim
COPY target/highway-0.0.1-SNAPSHOT.jar /app/highway.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "highway.jar"]
