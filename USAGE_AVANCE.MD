# USAGE\_AVANCE.MD

##  Objectif

Montrer comment :
* Produire et consommer les données météo.
* Monitorer les flux temps réel.
* Visualiser dans Grafana.

---

##  Architecture

Voici le cheminement des données dans l’architecture du projet :

* Les données météo sont récupérées depuis l’API **OpenWeatherMap** par le producteur Java (**ApiWeatherProducer**).
* Ce producteur publie les données dans le topic Kafka `api-data`.
* Le consommateur Java (**ApiDataConsumer**) lit ces données depuis Kafka, extrait les mesures utiles (température, humidité, vent) et les expose au format Prometheus.
* **Prometheus** récupère automatiquement ces métriques sur le consommateur.
* **Grafana** permet de visualiser, filtrer et créer des alertes à partir des données collectées par Prometheus.

---

##  Comment ça marche

1 - **Producteur**

* Récupère les données OpenWeatherMap toutes les X secondes.
* Écrit les données JSON dans le topic Kafka `api-data`.

2 - **Kafka Broker**

* Répartit les messages aux partitions du topic.
* Assure la persistance.

3 - **Consommateur**

* Lit le topic `api-data`.
* Parse JSON → extrait Température, Humidité, Vitesse du vent.
* Expose ces valeurs sous forme de `Gauge` Prometheus via `HTTPServer`.

4 - **Prometheus**

* Scrape le consommateur sur `:8080`.
* Stocke les séries temporelles.

5️ - **Grafana**

* Affiche les dashboards : Température, Humidité, Vitesse du vent.
* Permet de filtrer, exporter, alerter.

---

##  Points clés

 **Pourquoi pas Kafka Connect**

* Limite Windows → contournée par un Producteur Java custom.
* Possibilité de revenir à Connect ultérieurement.

 **Monitoring**

* `ApiDataConsumer` expose directement des métriques Prometheus.
* Pas besoin de JMX Exporter supplémentaire.

 **Alerting**

* Possible via Grafana : seuils de température, humidité, etc.

---

##  Commandes utiles

* Vérifier topics :

  ```bash
  docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --list
  ```

* Voir les messages :

  ```bash
  docker exec -it kafka kafka-console-consumer \
    --bootstrap-server localhost:9092 \
    --topic api-data \
    --from-beginning
  ```

---

 **Bonnes pratiques**

* Sécuriser l’accès Prometheus & Grafana (auth, réseau).
* Documenter la rotation de la clé API.
* Penser à externaliser la config dans un fichier .env.

 **Contacts**
En cas de questions: contactez l’équipe.

