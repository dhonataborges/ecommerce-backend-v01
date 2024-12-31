<<<<<<< HEAD
# Etapa 1: Build
FROM maven:3.8.6-openjdk-11-slim AS build

# Definindo o diretório de trabalho dentro da imagem
WORKDIR /app

# Copiar o código fonte do projeto para a imagem
COPY pom.xml .
COPY src ./src

# Rodar o Maven para compilar o projeto e gerar o arquivo JAR
RUN mvn clean package -DskipTests

# Etapa 2: Execução
FROM openjdk:11-jre-slim

# Definindo o diretório de trabalho dentro da imagem
WORKDIR /app

# Copiar o arquivo JAR gerado na etapa de build para a imagem
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que o Spring Boot irá rodar
EXPOSE 8080

# Comando para rodar a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
=======
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /target/*.jar app.jar

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
>>>>>>> 5a0117c1167eb18b7375e5ecfbd70ebc151869b8
