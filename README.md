# kafka-group4


Ilyass
# Infrastructure Kafka avec Docker

## Lancer Kafka et Zookeeper
```bash

cd infra
docker-compose up -d
```
## Entrer dans le conteneur kafka
```bash

docker exec -it kafka bash
```

## Entrer dans le conteneur kafka
```bash-

kafka-topics.sh --bootstrap-server localhost:9092 --list