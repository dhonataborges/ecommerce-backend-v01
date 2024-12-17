# =========================
# Etapa 1: Build do JAR (Build Stage)
# =========================
FROM maven:3.8.6-eclipse-temurin-17-slim AS build

# Definir o diretório de trabalho no contêiner
WORKDIR /app

# Copiar apenas os arquivos necessários para o build
COPY pom.xml ./

# Baixar dependências do Maven para usar o cache
RUN mvn dependency:go-offline -B

# Copiar o código-fonte do projeto
COPY src ./src

# Build da aplicação com Maven, sem executar os testes
RUN mvn clean package -DskipTests

# =========================
# Etapa 2: Imagem Final (Runtime Stage)
# =========================
FROM eclipse-temurin:17-jre-alpine

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o JAR gerado na etapa anterior
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar /app/app.jar

# Copiar o arquivo de credenciais para o Google Drive
COPY src/main/resources/ecommerce-linda-cosmeticos-b5a4a6905b9c.json /app/resources/ecommerce-linda-cosmeticos-b5a4a6905b9c.json

# Expor a porta 8080 para a aplicação
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
