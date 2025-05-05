# 🛒 E-commerce Backend

Este é um projeto de backend para um sistema de e-commerce, desenvolvido em Java com arquitetura modularizada. Ele oferece funcionalidades como gerenciamento de produtos, controle de estoque, segurança de dados, validações robustas e mapeamento eficiente de DTOs para uma aplicação escalável e de fácil manutenção.

## 📝 Descrição Estendida

Este projeto é uma aplicação backend desenvolvida com **Java e Spring Boot**, projetada para atender às necessidades de um sistema de **e-commerce** moderno. Ele foi estruturado com base em princípios de **arquitetura limpa**, separando responsabilidades por camadas (API, domínio, infraestrutura e configurações centrais), o que facilita a manutenção, testes e evolução do sistema.

Entre as funcionalidades oferecidas, estão:

- ✅ **Gestão de Produtos**: Cadastro, edição, listagem e remoção de produtos com suporte a DTOs.
- 📦 **Controle de Estoque**: Atualização e consulta de quantidades disponíveis para cada item, com implementação em repositórios específicos.
- 🔐 **Segurança**: Módulo dedicado à criptografia e segurança dos dados sensíveis, com possibilidade de integração futura com autenticação/autorização.
- ✅ **Validações**: Regras de validação robustas para garantir integridade e consistência dos dados recebidos nas requisições.
- 🔄 **Mapeamento de DTOs**: Uso de ModelMapper para conversão automática entre entidades e DTOs, promovendo desacoplamento entre as camadas.
- 🛠 **Migrações de Banco de Dados**: Utilização do Flyway para versionamento e controle do schema do banco de dados relacional.
- 🧪 **Suporte a Testes**: Estrutura dedicada para dados de teste e fácil integração com frameworks de testes unitários e de integração.

O objetivo do projeto é fornecer uma **base sólida e extensível** para aplicações de comércio eletrônico, podendo ser integrada facilmente com frontends ou sistemas externos, e adaptada conforme as regras de negócio evoluam.

## 🧱 Estrutura do Projeto

```
src
├── java
│   └── com.backend.ecommerce
│       ├── api                        # Camada de entrada (controladores, DTOs, etc.)
│       │   ├── assemblerDTO
│       │   ├── controller
│       │   ├── exeptionHandler
│       │   └── modelDTO
│       ├── core                       # Configurações e funcionalidades auxiliares
│       │   ├── security.crypto
│       │   ├── storage
│       │   ├── validation
│       │   ├── ModelMapperConfig.java
│       │   └── ProdutoModelDTOConfig.java
│       ├── domain                     # Regras de negócio e modelo de domínio
│       │   ├── exception
│       │   ├── model
│       │   ├── repository
│       │   └── service
│       ├── infrastructure.repository  # Implementações específicas de repositórios
│       │   ├── service.storage
│       │   ├── EstoqueRepositoryImpl.java
│       │   └── ProdutoRepositoryImpl.java
│       └── EcommerceApplication.java  # Classe principal
├── resources
│   ├── db
│   │   ├── migration
│   │   ├── migration_antigo
│   │   └── testdata
│   ├── application.properties         # Configurações da aplicação
│   └── bd-commerce-v01-83b1c10551a8.json
```

## 🔧 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- JPA / Hibernate
- ModelMapper
- Flyway (para versionamento de banco de dados)
- Banco de dados relacional (ex: PostgreSQL)
- Testes com JUnit

## 🚀 Como Rodar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/dhonataborges/ecommerce-backend-v01.git
   cd ecommerce-backend-v01
   ```

2. **Configure o banco de dados** no arquivo `application.properties`.

3. **Execute a aplicação:**
   - Via sua IDE (classe `EcommerceApplication.java`)
   - Ou via terminal:
     ```bash
     ./mvnw spring-boot:run
     ```

4. **Acesse a API:**  
   A aplicação estará disponível em `http://localhost:8080`.

## 🧪 Testes

Os testes estão localizados em `resources/db/testdata` e provavelmente incluem dados para testes unitários/integrados. Recomenda-se configurar um perfil `test` para isolamento dos dados.

## 📌 Observações

- O projeto está modularizado, facilitando a manutenção e escalabilidade.
- Utilize o Flyway para controlar versões do schema do banco de dados.
- Verifique o arquivo `.json` presente na pasta `resources` para possíveis configurações iniciais.

🚧 Projeto em Desenvolvimento
Este projeto ainda está em desenvolvimento ativo. Funcionalidades podem estar incompletas ou sujeitas a alterações frequentes.
