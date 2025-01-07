# Primeira etapa: Construir a aplicação
FROM ubuntu:latest AS build

# Atualizar o sistema e instalar o JDK, Maven e utilitários
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven wget

# Baixar e instalar o Cloud SQL Proxy
RUN wget https://dl.google.com/cloudsql/cloud_sql_proxy.linux.amd64 -O /usr/local/bin/cloud_sql_proxy && \
    chmod +x /usr/local/bin/cloud_sql_proxy

# Configurar o diretório de trabalho
WORKDIR /app

# Copiar os arquivos necessários para o diretório de trabalho
COPY src src
COPY pom.xml .

# Copiar o arquivo de credenciais para o local correto
COPY src/main/resources/ecommerce-linda-cosmeticos-b5a4a6905b9c.json src/main/resources/ecommerce-linda-cosmeticos-b5a4a6905b9c.json

# Copiar a imagem genérica
COPY img/image-generica.jpeg img/image-generica.jpeg

# Baixar as dependências do Maven e construir o projeto
RUN mvn clean package -DskipTests

# Segunda etapa: Criar uma imagem mais leve com apenas o JAR
FROM openjdk:17-jdk-slim

# Configurar o diretório de trabalho
WORKDIR /app

# Copiar o JAR da primeira etapa para a segunda
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Copiar o arquivo de credenciais para o diretório correto na segunda etapa
COPY --from=build /app/src/main/resources/ecommerce-linda-cosmeticos-b5a4a6905b9c.json src/main/resources/ecommerce-linda-cosmeticos-b5a4a6905b9c.json

# Copiar a imagem genérica para o contêiner
COPY --from=build /app/img/image-generica.jpeg img/image-generica.jpeg

# Expor a porta 8080 (se necessário)
EXPOSE 8080

# Configurar o arquivo de credenciais no ambiente, caso necessário
ENV GOOGLE_APPLICATION_CREDENTIALS=/app/src/main/resources/ecommerce-linda-cosmeticos-b5a4a6905b9c.json

# Configurar as variáveis de ambiente para o Cloud SQL Proxy
ENV CLOUD_SQL_CONNECTION_NAME=bd-commerce-v01:southamerica-east1:id-db-ecommerce-v01
ENV DB_USER=seu_usuario
ENV DB_PASS=admin2025
ENV DB_NAME=postgres

# Rodar o Cloud SQL Proxy em segundo plano e iniciar a aplicação Spring Boot
CMD /usr/local/bin/cloud_sql_proxy -dir=/cloudsql -instances=$CLOUD_SQL_CONNECTION_NAME & \
    java -jar app.jar