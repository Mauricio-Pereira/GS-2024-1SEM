
# Projeto Blue Horizon Market

Este projeto é uma implementação de um sistema de gerenciamento de usuários, empresas, produtos, pedidos, itens de pedidos, ONGs e doações. A aplicação utiliza uma arquitetura baseada em recursos RESTful e é construída usando JAX-RS.  
Essa aplicação foi desenvolvida como parte do projeto da criação de um E-commerce em que uma parte da taxa de venda de produtos é revertida em doações para ONGs e outra parte é revertida para a empresa, para a manutenção do sistema. 

## Estrutura do Projeto

O projeto está organizado nas seguintes camadas principais:

- **Entities**: Classes que representam as entidades do sistema, como User, Company, Product, Order, OrderItem, Ngo, Donation.
- **Repositories**: Classes que interagem com o banco de dados para CRUD (Create, Read, Update, Delete) operações.
- **Services**: Classes que contêm a lógica de negócios.
- **Resources**: Classes que expõem os endpoints RESTful.
- **Infrastructure**: Classes que lidam com APIs externas e outras operações de infraestrutura.
- **Connection**: Classe que gerencia a conexão com o banco de dados.
- **Utils**: Classes utilitárias.
- **Annotations**: Classe para o uso de querys personalizadas.

## Funcionalidades

### Usuários

- Criação de usuários com endereço.
- Autenticação de usuários (login).
- Atualização de dados do usuário.
- Exclusão de usuários.
- Listagem de todos os usuários.
- Recuperação de usuário por ID.
- Recuperação de ID do usuário pelo email.

### Endereços

- Criação de endereços.
- Atualização de endereços.
- Exclusão de endereços.
- Listagem de todos os endereços.
- Recuperação de endereço por ID.

### Empresas

- Criação de empresas.
- Atualização de empresas.
- Exclusão de empresas.
- Listagem de todas as empresas.
- Recuperação de empresa por ID.

### Produtos

- Criação de produtos.
- Atualização de produtos.
- Exclusão de produtos.
- Listagem de todos os produtos.
- Recuperação de produto por ID.

### Pedidos

- Criação de pedidos.
- Atualização de pedidos.
- Exclusão de pedidos.
- Listagem de todos os pedidos.
- Recuperação de pedido por ID.

### Itens do Pedido

- Criação de itens do pedido.
- Atualização de itens do pedido.
- Exclusão de itens do pedido.
- Listagem de todos os itens do pedido.
- Recuperação de item do pedido por ID.

### ONGs

- Criação de ONGs.
- Atualização de ONGs.
- Exclusão de ONGs.
- Listagem de todas as ONGs.
- Recuperação de ONG por ID.

### Doações

- Criação de doações automaticamente ao criar um pedido.
- Atualização de doações ao atualizar um pedido.
- Exclusão de doações ao excluir um pedido.

## Instalação

1. Clone o repositório para sua máquina local.
2. Importe o projeto em sua IDE preferida.
3. Configure a conexão com o banco de dados no arquivo `DatabaseConnection.java`.
4. Execute o arquivo 'fiap.sql' para apagar tabelas que já existem.
5. Execute o arquivo 'Test.java' para criar as tabelas.
6. Execute a aplicação.
7. Execute o arquivo 'BLUE-HORIZON-MARKET.postman_collection.json' no Postman para testar os endpoints.

## Uso

### Endpoints

Os endpoints da API podem ser testados usando ferramentas como Postman. Um arquivo de coleção do Postman está incluído no projeto, que contém exemplos de requisições para todos os endpoints.




## Repositório

1. [GitHub - Projeto completo](https://github.com/Mauricio-Pereira/GS-2024-1SEM)
2. [GitHub - Projeto Back-End Java](https://github.com/Mauricio-Pereira/GS-2024-1SEM/tree/main/DDD/GS_2024_BLUE_HORIZON_MARKET)

## Autores
- [Mauricio Pereira](https://github.com/Mauricio-Pereira)
- [Luiz Otávio Leitão](https://github.com/Luiz1614)
- [Vitor Onofre Ramos](https://github.com/VitorOnofreRamos)
