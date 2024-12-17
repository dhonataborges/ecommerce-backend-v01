# Primeira etapa: Build com Maven
FROM ubuntu:latest AS build

# Instalar dependências
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

# Verificar as instalações
RUN java -version
RUN mvn -version

# Definir o diretório de trabalho no contêiner
WORKDIR /app

# Copiar os arquivos locais do projeto para o contêiner
COPY . .

# Listar os arquivos para verificar se tudo foi copiado corretamente
RUN ls -la

# Build da aplicação com Maven
RUN mvn clean install

# Segunda etapa: Executar a aplicação
FROM openjdk:17-jdk-slim

# Expor a porta
EXPOSE 8080

# Copiar o JAR gerado da etapa de build
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Executar o JAR quando o contêiner iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]