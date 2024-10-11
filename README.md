# API de cadastro de pessoas

A API de Cadastro de Pessoas permite gerenciar pessoas, questões e respostas. Através dela, os usuários podem criar, listar e deletar questões e respostas associadas a cada pessoa.

## Tecnologias utilizadas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring-Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Postgres](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

## URL da API
A API está disponível em [https://sistema-de-cadastros.fly.dev/](https://sistema-de-cadastros.fly.dev/).

## Documentação da API
A Documentação da API está disponível em [https://sistema-de-cadastros.fly.dev/doc](https://sistema-de-cadastros.fly.dev/doc).

## Deploy da Aplicação

Para realizar o deploy da API, você pode utilizar o Docker. Siga os passos abaixo para configurar e rodar a aplicação:

1. **Construa a Imagem Docker**:
   ```bash
   docker build -t sistema_de_cadastros .
   ```

2. **Execute o Contêiner Docker**:
   Inicie o contêiner com o mapeamento de porta e as variáveis de ambiente necessárias para a conexão com o banco de dados. O comando abaixo inicia o container:

   ```bash
   docker run -d -p 8080:8080 --name nome-do-container \
     -e DB_HOST="nome-da-host" \
     -e DB_NAME="nome-da-database" \
     -e DB_USER="nome-de-usuario-da-database" \
     -e DB_PASSWORD="senha-da-database" \
   sistema_de_cadastros

   ```
   Certifique-se de substituir `nome-do-container`, `nome-da-host`, `nome-da-database`, `nome-de-usuario-da-database`, e `senha-da-database` pelos valores corretos da sua configuração.

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir um problema ou enviar um pull request.
