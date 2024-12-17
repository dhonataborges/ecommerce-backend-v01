FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar /app/api.jar

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]



##FROM maven:3.9.8-openjdk-17-jdk as build
#RUN mkdir -p /app
#WORKDIR /app
#ADD . /app
#RUN mvn package

#FROM eclipse-temurin:17-jdk
#RUN mkdir -p /app
#WORKDIR /app
#COPY target/*.jar /app/ecommerce-backend.jar
#CMD ["java","-jar","/app/ecommerce-backend.jar"]###
