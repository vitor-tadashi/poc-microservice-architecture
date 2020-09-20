# poc-microservice-architecture

Comando de criar topico ordem-ativo
kafka-topics --bootstrap-server "localhost:9091" --create --topic ordem-ativo --replication-factor 1 --partitions 3

Comando de criar topico ordem-ativo-precificar
kafka-topics --bootstrap-server "localhost:9091" --create --topic ordem-ativo-precificar --replication-factor 1 --partitions 3

Comando de criar topico ordem-ativo-precificado
kafka-topics --bootstrap-server "localhost:9091" --create --topic ordem-ativo-precificado --replication-factor 1 --partitions 3



curl \
-iXPOST 'http://localhost:8082/topics/ordem-ativo' \
-H 'Accept: application/vnd.kafka.v2+json' \
-H 'Content-Type: application/vnd.kafka.json.v2+json' \
-d '{ "records": [ { "value": { "ativo": "B3", "quantidade": 100, "token": "1234456789" } } ]}'


docker-compose down -v

Instalar o aws cli
https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2.html