# aluraflix-api

API desenvolvida durante a Edição #1: Desafios Back-End da [Alura Challenges.](https://www.alura.com.br/challenges/back-end/) 

Após alguns testes com protótipos feitos pelo time de UX de uma empresa, foi requisitada a primeira versão de uma plataforma para compartilhamento de vídeos. A plataforma deve permitir ao usuário montar playlists com links para seus vídeos preferidos, separados por categorias.

Os times de frontend e UI já estão trabalhando no layout e nas telas. Para o backend, as principais funcionalidades a serem implementadas são:

1. **API com rotas implementadas segundo o padrão REST**;
2. **Validações feitas conforme as regras de negócio**;
3. **Implementação de base de dados para persistência das informações**;
4. **Serviço de autenticação para acesso às rotas GET, POST, PUT e DELETE**.

## Tecnologias utizadas

1. Java 17
2. Spring 
3. Banco de dados PostgreSQL
4. JPA / Hibernate
5. Maven
6. JUnit e Postman para testes 
7. Swagger para documentação
8. Heroku para deploy

## Como utilizar
### Heroku deploy
API disponível em: https://aluraflix-apirest.herokuapp.com/swagger-ui/index.html

1. Crie um novo usuário
2. Faça a autenticação 
3. Copie e cole o token retornado no campo 'Authorize' para liberar as demais rotas.

### Postman

Importar a collection disponível em: https://www.postman.com/collections/e7423d9251e57776fde6

## Autor
Projeto completo desenvolvido por [Yuri Italo](https://www.linkedin.com/in/yuri-italo/)

