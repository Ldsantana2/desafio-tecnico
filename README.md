# Desafio Técnico

Este repositório contém os seguintes componentes:

### Backend

O backend está configurado com Spring Boot e pode ser executado com Maven.

### Frontend

O frontend é uma aplicação Next.js configurada com Tailwind CSS, React 19 e TypeScript.

### Database

A database virá povoada com 1 usuário de teste. O commando usado para criar a tabela pode ser visto no arquivo criar_tablea.sql, localizado dentro da pasta backend/database.

## Configuração do Docker

Dentro da pasta `backend/database`, há um arquivo `docker-compose.yaml`. Para subir o Docker, execute o seguinte comando:

`docker-compose up -d`

## Como Rodar a Aplicação Backend:

Navegue via terminal até a pasta desafio-tecnico/backend/api e execute os commandos:

`mvn clean install`
`mvn spring-boot:run`

## Como Rodar a Aplicação Frontend:

Navegue via terminal até a pasta desafio-tecnico/frontend/interface e execute os comandos:

`npm install`
`npm run dev`

## URLs

Você poderá navegar pelas paginas a partir da url: http://localhost:3000/login

E poderá ver o SwaggerUI a partir da url http://localhost:8080/swagger-ui/index.html
