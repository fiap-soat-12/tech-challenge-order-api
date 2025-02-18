<div align="center">
  
# Tech Challenge - Order API

![GitHub Release Date](https://img.shields.io/badge/Release%20Date-Fevereiro%202025-yellowgreen)
![](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellowgreen)
<br>
![](https://img.shields.io/badge/Version-%20v1.0.0-brightgreen)
</div>

## 💻 Descrição

O **Tech Challenge - Order API** é um microserviço desenvolvido em **Java** com **Spring Boot**, seguindo os princípios da **Clean Architecture**. Ele é responsável por gerenciar os endpoints de **Criação e Identificação do Cliente** do restaurante e **Criação dos Pedidos**.

## 🛠 Tecnologias Utilizadas

![Java](https://img.shields.io/badge/java_21-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring_3-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162.svg?style=for-the-badge&logo=JUnit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-53AC56.svg?style=for-the-badge&logo=Minetest&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36.svg?style=for-the-badge&logo=Apache-Maven&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white)
![Sonnar](https://img.shields.io/badge/Sonar-FD3456.svg?style=for-the-badge&logo=Sonar&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF.svg?style=for-the-badge&logo=GitHub-Actions&logoColor=white)
![Terraform](https://img.shields.io/badge/Terraform-7B42BC?style=for-the-badge&logo=terraform&logoColor=white)

## 💫 Arquitetura

O projeto adota a **Clean Architecture**, garantindo flexibilidade, testabilidade e manutenção escalável.

## ⚙️ Configuração

### Pré-requisitos

1. É necessário executar a pipeline para criar o VPC no repositório: https://github.com/fiap-soat-12/tech-challenge-vpc
2. É necessário executar a pipeline para criar o SQS no repositório: https://github.com/fiap-soat-12/tech-challenge-queue

### Desenvolvimento

- **[Java 21](https://docs.oracle.com/en/java/javase/21/)**: Documentação oficial do Java 21.
- **[Maven 3.6.3+](https://maven.apache.org/)**: Site oficial do Maven.
- **[Docker](https://www.docker.com/)**: Site oficial do Docker.
- **[Docker Compose](https://docs.docker.com/compose/)**: Documentação oficial do Docker Compose.
- **[PostgreSQL](https://www.postgresql.org/)**: Documentação oficial do PostgreSQL.
- **[Sonarqube](https://www.sonarsource.com/products/sonarqube/)**: Site oficial do Sonarqube.
- **[Kubernetes](https://kubernetes.io/pt-br/docs/home/)**: Documentação oficial do Kubernetes.
- **[Terraform](https://www.terraform.io/)**: Site oficial do Terraform.
- **[AWS](https://aws.amazon.com/pt/)**: Site oficial da AWS.

### 🚀 Execução

### Subindo a aplicação com Docker Compose

1. Executar o comando:

```sh
docker compose up
```

3. O serviço estará disponível em `http://localhost:8357/order`

### Subindo a Order API
  Caso deseje subir a Order API, basta seguir os seguintes passos:
  
  1. Certificar que o Terraform esteja instalado executando o comando `terraform --version`;
  ![terraform-version](./assets/terraform-version.png)

  2. Certificar que o `aws cli` está instalado e configurado com as credenciais da sua conta AWS;
  ![aws-cli-version](./assets/aws-cli-version.png)

  3. Acessar a pasta `terraform` que contém os arquivos que irão criar a Order API;
  4. Inicializar o Terraform no projeto `terraform init`;
  5. Verificar que o script do Terraform é valido rodando o comando `terraform validate`;
  6. Executar o comando `terraform plan` para executar o planejamento da execução/implementação;
  7. Executar o comando `terraform apply` para criar a Order API;
  8. Após a execução do Terraform finalizar, verificar se a Order API subiu corretamente na AWS;
  ![aws-resource](./assets/aws-resource.png)

## 📄 Documentação da API

A documentação da API pode ser acessada através do Swagger:

```bash
http://localhost:8357/order/swagger-ui/index.html
```

## ✅ Cobertura de Testes

### Testes Unitarios
![unit-test](./assets/unit_test_order.png)

### Scan do Sonar
![Sonar_1](./assets/sonar_order_1.png)
![Sonar_1](./assets/sonar_order_2.png)

## 🔃 Fluxo de Execução das APIs

1. Criação do **Cliente** (POST) `/order/v1/customers`
2. Busca do **Cliente** pelo CNPJ (GET) `/order/v1/customers/{document}`
3. Busca dos **Produtos** (GET) `/order/v1/products`
4. Criação do **Pedido** (POST) `/order/v1/orders`
5. Busca do **Pedido** pelo status (GET) `/order/v1/orders/{id}`
6. Busca do **Pedido** pelo status do pagamento (GET) `/order/v1/orders/{id}/paid-status`

## 📚 Event Storming

![Event Storming](./assets/event_storming.png)

Acesso ao MIRO com o Event Storming:
[Event Storming](https://miro.com/app/board/uXjVK1ekBDM=/)
