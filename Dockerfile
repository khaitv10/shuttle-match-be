# Build stage
FROM maven:3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/shuttle-match-0.0.1-SNAPSHOT.jar shuttlematch.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "shuttlematch.jar"]
