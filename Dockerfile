# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy


WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY api.yaml ./
RUN chmod +x ./mvnw
RUN ./mvnw dependency:resolve

COPY src ./src
EXPOSE 9009
CMD ["./mvnw", "spring-boot:run"]
