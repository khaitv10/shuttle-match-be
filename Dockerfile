FROM maven:3-openjdk-17 AS build
VOLUME /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/shuttle-match-0.0.1-SNAPSHOT.war shuttlematch.war
ENTRYPOINT ["java", "-jar", "shuttlematch.jar"]
