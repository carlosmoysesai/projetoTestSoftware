# Estágio de build
FROM gradle:8.8.0-jdk17 AS build
WORKDIR /app
USER root
COPY . .
RUN gradle clean build -x test

# Estágio de runtime
FROM openjdk:17.0.2-slim-buster
USER root
EXPOSE 8081
COPY --from=build /app/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
