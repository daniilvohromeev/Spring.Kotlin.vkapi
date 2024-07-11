FROM maven:3.8.6-openjdk-21 AS build

WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=build /app/target/spring-vkapi-0.0.1-SNAPSHOT.jar /app/spring-vkapi.jar

ENTRYPOINT ["java", "-jar", "/app/spring-vkapi.jar"]
