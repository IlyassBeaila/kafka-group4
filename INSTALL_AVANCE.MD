# INSTALL\_AVANCE.MD

##  Objectif

Déployer l’architecture avancée du projet **Kafka Weather Monitoring** avec :

* API OpenWeatherMap
* Producteur API (`ApiWeatherProducer`) remplaçant Kafka Connect
* Kafka Broker & Zookeeper
* Consommateur (`ApiDataConsumer`)
* Monitoring avec Prometheus & Grafana

---

##  Structure du projet

Voici la répartition des dossiers dans le projet :

* **infra/** : Contient les fichiers Docker Compose et toutes les configurations nécessaires à l’infrastructure (Kafka, Zookeeper, Prometheus, Grafana, etc.).
* **connectors/** : (Optionnel) Dossier dédié aux connecteurs Kafka supplémentaires si besoin.
* **src/main/java/com/group4/kafka/** : Code source Java principal.

    * **producer/** : Contient le producteur Java `ApiWeatherProducer` chargé de récupérer et publier les données météo dans Kafka.
    * **consumer/** : Contient le consommateur Java `ApiDataConsumer` chargé de lire Kafka et d’exposer les métriques à Prometheus.
* **monitoring/** : Partie monitoring du projet.

    * **prometheus/** : Contient la configuration de Prometheus (scraping, jobs, etc.).
    * **grafana/** : Dashboards et datasources pour Grafana (fichiers de provisioning).

---


##  Prérequis

* Docker & Docker Compose installés.
* Clé API OpenWeatherMap valide.
* Java 17+ (pour exécuter les producteurs/consommateurs).

---

## Étapes de déploiement

### 1️ - Cloner le projet

```bash
 git clone git@github.com:IlyassBeaila/kafka-group4.git
 cd kafka-group4
```

### 2️ - Configurer les variables

Vérifier la clé API dans ApiWeatherProducer :

```java
String apiKey = "YOUR_OPENWEATHER_API_KEY";
```

### 3️ - Lancer l’infrastructure

Dans infra/ :

```bash
  docker-compose up -d
```

Services démarrés :

* Zookeeper : 2181
* Kafka Broker : 9092
* Prometheus : 9090
* Grafana : 3000

### 4️ - Compiler les modules Java

```bash
 cd exportToHTML  # ou src/main/java selon ton IDE
 mvn clean package
```

### 5 - Démarrer le producteur & consommateur

Ouvre deux terminaux :

#### Producteur

```bash
java -cp target/<your-jar>.jar com.group4.kafka.producer.ApiWeatherProducer
```

#### Consommateur

```bash
java -cp target/<your-jar>.jar com.group4.kafka.consumer.ApiDataConsumer
```

### 6️ - Vérifier Kafka

Vérifie que le topic api-data reçoit bien les messages :

```bash
  docker exec -it kafka bash
  kafka-topics --bootstrap-server localhost:9092 --list
```

##  Accès Monitoring

* Prometheus : [http://localhost:9090](http://localhost:9090)
* Grafana : [http://localhost:3000](http://localhost:3000)

    * Login par défaut : admin / admin

Les métriques exposées :

* Producteur & Consommateur exposent des gauges via JMX ou HTTPServer.
* Prometheus scrape ApiDataConsumer.

---

🔍 **Notes**

* Le ApiWeatherProducer remplace Kafka Connect à cause de limitations Windows.
* Tu peux ajouter un connecteur plus tard (connectors/).
* Les dashboards Grafana sont provisionnés depuis monitoring/grafana/provisioning.

##  Auteurs

- Ilyass Beaila
- Oussama Skalli
- Thibaut François
- Alexi Pinchon
- Groupe - Master Informatique
