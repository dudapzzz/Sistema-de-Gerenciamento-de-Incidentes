FROM maven:3.9-eclipse-temurin-21 AS build

LABEL maintainer="Eduarda <eduarda.zorzo@acad.ufsm.br>"

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

RUN mkdir -p /logs/incidentes

COPY --from=build /app/target/*.jar app.jar

VOLUME ["/logs/incidentes"]

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]