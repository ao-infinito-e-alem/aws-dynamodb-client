# AWS DynamoDB Client

Essa aplicação é uma POC (Proof of Concept) do [consumo do DynamoDB em uma aplicação Java com o SDK](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.Java.html) disponibilizado
pela AWS.

## Arquitetura

O projeto foi construído baseado no conceito de [Clean Architeture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).

## Conexão com o DynamoDB

### Local - DynamoDB como um container Docker

No diretório [.docker](https://github.com/ao-infinito-e-alem/aws-dynamodb-client/tree/main/.docker) há um docker-compose
configurado para rodar uma instância do DynamoDB para desenvolvimento local. Ele é disponibilizado na porta 8000.

Para que a aplicação se conecte na instância local, basta inicia-la com o profile `local-dinamodb`. O profile `default`
também se conecta ao DynamoDB local pois ativa o profile `local-dynamodb`.

Nesse modo não é necessária nenhuma configuração adicional.

### AWS - DynamoDB "real"

Para que a aplicação se conecte com o DynamoDB na cloud é preciso iniciar a aplicação como profile `aws-dynamodb`.
Será necessário ter um usuário no [IAM](https://docs.aws.amazon.com/IAM/latest/UserGuide/introduction.html?icmpid=docs_iam_console)
com permissão de acesso ao DynamoDB (AmazonDynamoDBFullAccess). Com esse usuário criado, informar as credenciais nas variáveis de ambiente abaixo:

`AWS_DYNAMODB_ACCESS_KEY` - ID da chave de acesso  
`AWS_DYNAMODB_SECRET_KEY` - Chave de acesso

## Execução

Veremos algumas formas para executar a aplicação.

### IDE

Para executar a aplicação na IDE basta importar o projeto e executar a classe com.github.paulosalonso.aws.dynamodb.DynamodbClientApplication como uma aplicação Java.

### Maven

> mvn spring-boot:run

### java -jar
> mvn clean package \
> java -jar target/research.jar

## Consumo

### Postman

Pra consumir a API importe a coleção do diretório [.postman](https://github.com/ao-infinito-e-alem/aws-dynamodb-client/tree/main/.postman) no Postman.

### Swagger

Com a aplicação rodando, acesse a URL abaixo:

http://localhost:8080/swagger-ui/index.html  