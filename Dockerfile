# Etapa 1: Construir a aplicação
FROM ubuntu:latest AS build

# Atualizar o sistema e instalar o JDK, Maven e curl (para baixar o Cloud SQL Proxy)
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven curl

# Baixar o Cloud SQL Proxy e torná-lo executável
RUN curl -o /usr/local/bin/cloud_sql_proxy https://storage.googleapis.com/cloudsql-proxy/v1.27.1/cloud_sql_proxy.linux.amd64 && \
    chmod +x /usr/local/bin/cloud_sql_proxy

# Configurar o diretório de trabalho
WORKDIR /app

# Copiar os arquivos necessários para o diretório de trabalho
COPY src src
COPY pom.xml .

# Copiar o arquivo de credenciais para o local correto
COPY src/main/resources/ecommerce-linda-cosmeticos-b5a4a6905b9c.json src/main/resources/ecommerce-linda-cosmeticos-b5a4a6905b9c.json

# Copiar a imagem generica
COPY img/image-generica.jpeg img/image-generica.jpeg

# Baixar as dependências do Maven e construir o projeto
RUN mvn clean package -DskipTests

# Etapa 2: Criar uma imagem mais leve com apenas o JAR
FROM openjdk:17-jdk-slim

# Configurar o diretório de trabalho
WORKDIR /app

# Copiar o JAR da primeira etapa para a segunda
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Copiar o arquivo de credenciais para o diretório correto na segunda etapa
COPY --from=build /app/src/main/resources/ecommerce-linda-cosmeticos-b5a4a6905b9c.json src/main/resources/ecommerce-linda-cosmeticos-b5a4a6905b9c.json

# Copiar a imagem generica para o contêiner
COPY --from=build /app/img/image-generica.jpeg img/image-generica.jpeg

# Expor a porta 8080 (se necessário)
EXPOSE 8080

# Configurar o arquivo de credenciais no ambiente, caso necessário
ENV GOOGLE_APPLICATION_CREDENTIALS=/app/src/main/resources/ecommerce-linda-cosmeticos-b5a4a6905b9c.json

# Definir o comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]

# Exemplo de como iniciar o Cloud SQL Proxy na inicialização do contêiner
CMD ["/bin/bash", "-c", "cloud_sql_proxy -dir=/cloudsql & java -jar app.jar"]