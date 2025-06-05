# kafka-group4

Projet Kafka pour le groupe 4 — Master Informatique  
Objectif : Mettre en place une infrastructure complète de traitement de données avec **Kafka**, **Zookeeper** et **Kafka Connect** via Docker.

---

##  Infrastructure

Ce projet comprend :

- **Zookeeper** : pour la coordination
- **Kafka** : pour la gestion des topics et messages
- **Kafka Connect** : pour connecter des sources ou sinks externes
- **Docker Compose** : pour tout lancer facilement

---

##  Lancement de l’infrastructure

Dans le dossier `infra`, exécute :

```bash
docker-compose up -d --build
```

Puis vérifie que tout est bien lancé :

```bash
docker ps
```

---

##  Utilisation

###  Connexion au conteneur Kafka

```bash
docker exec -it infra_kafka_1 bash
```

###  Création d’un topic

```bash
kafka-topics.sh --create \
  --topic test-topic \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1
```

###  Vérifier les topics existants

```bash
kafka-topics.sh --list --bootstrap-server localhost:9092
```

###  Consommer les messages

```bash
kafka-console-consumer.sh --topic test-topic \
  --from-beginning \
  --bootstrap-server localhost:9092
```

###  Produire un message

Dans un autre terminal :

```bash
kafka-console-producer.sh --topic test-topic \
  --bootstrap-server localhost:9092
```

Puis tape des messages et valide avec `Entrée`.

---

##  Kafka Connect

L’interface REST est exposée sur :

```
http://localhost:8083
```

Tu peux tester avec :

```bash
curl http://localhost:8083/
```

---

## Nettoyer

Pour tout arrêter proprement :

```bash
docker-compose down --volumes
```

---

### Pour arrêter et nettoyer (y compris les volumes Docker)
```bash
docker-compose -f infra/docker-compose.yml down --volumes
```

### Pour relancer proprement
```bash
docker-compose -f infra/docker-compose.yml up -d --force-recreate
```

---

## Vérifications

### Liste des conteneurs actifs
```bash
docker ps
```

### Tester l'accès à Kafka Connect
```bash
curl http://localhost:8083/
```

---

##  Utilisation dans le projet

L'architecture est utilisée pour :

- Collecter les événements depuis une API ou un producteur Kafka
- Streamer les données vers des consommateurs ou un connecteur Sink
- Visualiser les métriques via Prometheus et Grafana (dans les prochaines étapes)

---

## Outils

- Kafka 7.3.0
- Zookeeper
- Kafka Connect
- Docker / Docker Compose

##  Auteurs

- Ilyass Beaila
- Oussama Skalli
- Thibaut François
- Alexis Pinchon
- Groupe 4 - Master Informatique
