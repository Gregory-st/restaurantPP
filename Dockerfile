FROM maven:3.8.6-amazoncorretto-17 AS build
COPY pom.xml /release/
WORKDIR /release
RUN mvn dependency:go-offline
COPY src /release/src/
RUN mvn package -DskipTests

FROM openjdk:17-alpine
ARG JAR_FILE=/release/target/*.jar
COPY --from=build $JAR_FILE /opt/docker-test/app.jar
ENTRYPOINT ["java", "-jar", "/opt/docker-test/app.jar"]