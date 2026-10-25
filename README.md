# Sistema de Gerenciamento de Incidentes de Segurança da Informação

Aplicação web para registrar e gerenciar incidentes de segurança da informação, ativos e usuários. O projeto foi desenvolvido para a disciplina de Programação Orientada a Objetos para Web.

**Status:** Em desenvolvimento.

## Sobre o projeto

O sistema organiza o registro e a consulta de incidentes, associando ocorrências e ativos aos usuários. Também oferece dados estatísticos para relatórios.

A aplicação utiliza Java, Spring Boot, JSP e PostgreSQL, com responsabilidades organizadas entre controllers, serviços, repositórios e modelos.

## Objetivos

- Desenvolver uma aplicação web para gerenciamento de incidentes.
- Aplicar conceitos de Programação Orientada a Objetos.
- Separar responsabilidades usando uma arquitetura baseada em MVC.
- Integrar a aplicação a um banco de dados relacional.
- Documentar os endpoints da aplicação.

## Tecnologias utilizadas

- **Java 17**
- **Spring Boot 4.0.6**
- **Spring MVC**
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway**
- **Maven**
- **JSP**
- **Springdoc OpenAPI / Swagger UI**

## Funcionalidades

- [x] Cadastro, consulta, atualização e exclusão de incidentes.
- [x] Cadastro e gerenciamento de ativos.
- [x] Cadastro e gerenciamento de usuários.
- [x] Endpoint de autenticação de usuários.
- [x] Associação de incidentes e ativos aos usuários.
- [x] Consulta de estatísticas por status e relevância.
- [x] Geração de dados para relatórios por usuário.
- [x] Validação de dados com Jakarta Validation.
- [x] Documentação interativa dos endpoints com Swagger UI.
- [ ] Implementar envio de notificações.
- [ ] Exportar relatórios para PDF e CSV.
- [ ] Adicionar testes automatizados.

## Arquitetura

A aplicação separa responsabilidades entre:

- **Controllers:** recebem as requisições e definem as respostas.
- **Services:** concentram as regras de negócio.
- **Repositories/DAO:** acessam os dados usando Spring Data JPA.
- **Models:** representam usuários, incidentes e ativos.
- **Views:** páginas JSP armazenadas em `src/main/webapp/WEB-INF/pages`.

As migrações do banco de dados ficam em `src/main/resources/db/migration` e são gerenciadas pelo Flyway.

## Pré-requisitos

Antes de executar o projeto, instale:

- JDK 17.
- PostgreSQL.
- Maven ou o Maven Wrapper incluído no repositório.
- Git.

## Como executar

### 1. Clonar o repositório

```bash
git clone https://github.com/dudapzzz/Sistema-de-Gerenciamento-de-Incidentes.git
cd Sistema-de-Gerenciamento-de-Incidentes
```

### 2. Criar o banco de dados

Crie no PostgreSQL um banco de dados chamado `trabalhoincidentes1`.

### 3. Configurar a conexão

Confira e ajuste as propriedades abaixo em `src/main/resources/application.properties` para corresponder ao seu ambiente:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/trabalhoincidentes1
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

**Importante:** substitua `sua_senha` pela senha do seu ambiente local. Não publique credenciais reais no repositório.

O Flyway executa as migrações disponíveis em `src/main/resources/db/migration` durante a inicialização.

### 4. Executar a aplicação

Com Maven:

```bash
mvn spring-boot:run
```

No Windows, também é possível usar o Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

A aplicação utiliza a porta `8080` e o contexto `/incidentes`.

## Acessos

Após iniciar a aplicação, acesse:

- **Interface web:** http://localhost:8080/incidentes/
- **Swagger UI:** http://localhost:8080/incidentes/swagger-ui.html
- **Especificação OpenAPI:** http://localhost:8080/incidentes/api-docs

Os endpoints REST estão organizados sob `/usuarios`, `/login`, `/incidente`, `/ativo` e `/relatorios`.

Consulte o Swagger UI para verificar os parâmetros, os formatos esperados e as respostas dos endpoints.

## Testes

O projeto ainda não possui testes automatizados no diretório `src/test`.

Quando forem adicionados, poderão ser executados com:

```bash
mvn test
```

## Documentação complementar

- **Relatório do projeto:** [https://github.com/dudapzzz/Sistema-de-Gerenciamento-de-Incidentes/blob/main/docs/Relat%C3%B3rio%20POO%20II%20(1).pdf].

## Aprendizados

O desenvolvimento do projeto proporciona prática com:

- Programação Orientada a Objetos em Java.
- Desenvolvimento de aplicações web com Spring Boot.
- Separação de responsabilidades.
- Persistência de dados com JPA e PostgreSQL.
- Migrações de banco de dados com Flyway.
- Criação e documentação de endpoints REST.

## Melhorias futuras

- [ ] Implementar notificações de incidentes.
- [ ] Adicionar exportação de relatórios em PDF e CSV.
- [ ] Criar testes automatizados para controllers e serviços.
- [ ] Aprimorar a documentação de configuração e uso.
- [ ] Revisar melhorias de segurança e controle de acesso.

---
Projeto acadêmico desenvolvido para a disciplina de Programação Orientada a Objetos para Web.
