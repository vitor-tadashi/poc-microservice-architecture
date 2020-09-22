# Desafio técnico B3

## Problema e solução de arquitetura
O problema proposto pode ser visualizado no arquivo [challenge.md](challenge.md) e a solução proposta pode ser visualizada no arquivo [solution-architecture.md](solution-architecture.md)

## Pré-requisitos
Você precisará instalar o [docker-desktop](https://www.docker.com/products/docker-desktop) e o [docker-compose CLI](https://docs.docker.com/compose/install/), todas as aplicações foram configuradas para serem executadas através do docker-compose. 

O arquivo de configuração se encontra na pasta raiz do repositório.. `docker-compose.yml`

## Estrutura de pasta
```
├── packages/services (local onde estão localizados os serviços)
    ├── agendador (Consumer que irá persistir as ordens de compra)
    ├── cliente (API de consulta de volume financeiro do cliente)
    ├── financeiro (Consumer que irá contabilizar e persistir o volume total diário de ordens de cada cliente)
    ├── precificador (Consumer que irá obter precificar, calcular o total da ordem e enviar o evento de precificado)
    ├── processador (Consumer que irá obter o código de precificação e enviar o evento de precificar)
```

## Executar o código
Os comandos a seguir devem ser executadas na pasta raiz do repositório onde se encontra o arquivo `docker-compose.yml`

Caso seja a sua primeira vez executando o código:
```
docker-compose down -v
docker-compose build
docker-compose up
```

A partir da segunda vez:
```
docker-compose down -v
docker-compose up
```

Caso você prefira rodar as aplicações em background coloque o `-d` no final do comando:
```
docker-compose up -d
```

O `down -v` é necessário para remover o volume `postgres-data` sempre antes de uma nova execução do `docker-compose up`

## Testar a aplicação
Nessa aplicação está sendo executado o componente `rest-proxy`, o Confluent Rest Proxy provê uma interface RESTful para produzir e consumir mensagens no Kafka Cluster. Em nosso caso só foi configurado o `Producer`, e será por ela que enviaremos a nossa ordem de compra de ativo.

Para enviar a ordem de compra:
```
curl \
-iXPOST 'http://localhost:8082/topics/ordem-ativo' \
-H 'Accept: application/vnd.kafka.v2+json' \
-H 'Content-Type: application/vnd.kafka.json.v2+json' \
-d '{ "records": [ { "value": { "ativo": "B3SA3", "quantidade": 100, "token": "rico" } } ]}'
```

Você consegue encontrar os códigos de ativo com código de precificação no arquivo `packages/services/processador/src/main/resources/informacao-cadastral.csv`, ele são carregados sempre que a aplicação é executada. Caso queira alterar o código do ativo altere o atributo `ativo` do payload de sua chamada.

O `token` é apenas uma `String` para identificar o cliente, altere para o que for mais conveniente, isso será utilizado no teste de consulta dos valores consolidados por cliente.

Para consultar o valor consolidado por cliente:
```
curl http://localhost:9090/api/v1/clientes/rico/volume/diario
```

O exemplo a seguir mostra qual parte da URL deve ser alterada para consultar o valor de outro cliente:
```
.../api/v1/clientes/{token}/volume/diario
```

O `{token}` deve ser alterado para o token de consulta de sua preferência.

## Banco de dados
Para esse teste foi criado apenas um banco de dados, na solução foram propostos outros bancos de dados mas para facilidade de desenvolvimento e menos consumo de memória foi criado uma instância do Postgres. 

O banco de dados pode ser acessado com a seguinte configuração:
```
url: jdbc:postgresql://localhost:5432/agendamento
username: developer
password: development
```

Lá é possível consultar duas tabelas, `ordem_ativo` e `volume_cliente`
```
select * from ordem_ativo;
select * from volume_cliente;
```

Vocês conseguirão conferir os dados enviados por vocês diretamente nessas tabelas.


## Se por algum motivo os tópicos não forem criados no Kafka
Os tópico estão sendo criados porque foram configurados no `docker-compose` na variável `KAFKA_CREATE_TOPICS` na linha 25 do arquivo `docker-compose.yml`, mas não realizei muitos testes em volta dessa feature. Então caso os tópicos não forem criados vocês poderão criar os tópicos manualmente.

### 1. Pré requisito
Instalar ou executar diretamente o [kafka cli](https://kafka.apache.org/quickstart) 

### 2. Criar tópicos
Comando para criar tópico `ordem-ativo`
```
bin/kafka-topics.sh --bootstrap-server "localhost:9091" --create --topic ordem-ativo --replication-factor 1 --partitions 3
```

Comando para criar tópico `ordem-ativo-precificar`
```
ordem-ativo-precificar
bin/kafka-topics.sh --bootstrap-server "localhost:9091" --create --topic ordem-ativo-precificar --replication-factor 1 --partitions 3
```

Comando para criar tópico `ordem-ativo-precificado`
```
ordem-ativo-precificado
bin/kafka-topics.sh --bootstrap-server "localhost:9091" --create --topic ordem-ativo-precificado --replication-factor 1 --partitions 3
```

## Consumir as mensagens nos tópicos
Caso você queira monitorar as mensagens que estão sendo enviadas nos tópicos será necessário instalar ou executar o [kafka cli](https://kafka.apache.org/quickstart).

Para consumir as mensagens enviadas no tópico `ordem-ativo`
```
bin/kafka-console-consumer.sh --bootstrap-server "localhost:9091" --from-beginning --topic ordem-ativo
```

Para consumir as mensagens enviadas no tópico `ordem-ativo-precificar`
```
bin/kafka-console-consumer.sh --bootstrap-server "localhost:9091" --from-beginning --topic ordem-ativo-precificar
```

Para consumir as mensagens enviadas no tópico `ordem-ativo-precificado`
```
bin/kafka-console-consumer.sh --bootstrap-server "localhost:9091" --from-beginning --topic ordem-ativo-precificado
```