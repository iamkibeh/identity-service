# Build stage

FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
ARG CONTAINER_PORT
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests


#Runtime stage

FROM amazoncorretto:17

ARG APP_VERSION=1.0.2

WORKDIR /app
COPY --from=build /build/target/identity-service-*.jar /app/
EXPOSE ${CONTAINER_PORT}
ENV JAR_VERSION=${APP_VERSION}
CMD java -jar identity-service-${JAR_VERSION}.jar

