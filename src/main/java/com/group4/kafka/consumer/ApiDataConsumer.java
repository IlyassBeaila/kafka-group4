package com.group4.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;
import org.apache.kafka.clients.consumer.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ApiDataConsumer {

    // Déclaration des métriques Prometheus
    static final Gauge temperatureGauge = Gauge.build()
            .name("weather_temperature_C")
            .help("Température en Kelvin")
            .register();

    static final Gauge humidityGauge = Gauge.build()
            .name("weather_humidity_percent")
            .help("Humidité en %")
            .register();

    static final Gauge windSpeedGauge = Gauge.build()
            .name("weather_wind_speed")
            .help("Vitesse du vent en m/s")
            .register();

    public static void main(String[] args) throws IOException {
        // Lancement du serveur HTTP Prometheus sur le port 8080
        HTTPServer server = new HTTPServer(8080);

        // Configuration Kafka
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");
        props.put("group.id", "weather-consumer-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("api-data"));

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Consumer démarré, en attente de messages...");

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    try {
                        String json = record.value();
                        WeatherData weather = mapper.readValue(json, WeatherData.class);
                        System.out.println("Données météo reçues : " + weather);

                        // Mise à jour des métriques Prometheus
                        if (weather.main != null) {
                            temperatureGauge.set(weather.main.getTempCelsius());
                            humidityGauge.set(weather.main.humidity);
                        }
                        if (weather.wind != null) {
                            windSpeedGauge.set(weather.wind.speed);
                        }

                    } catch (Exception e) {
                        System.err.println("Erreur de parsing JSON : " + e.getMessage());
                    }
                }
            }
        } finally {
            consumer.close();
            server.stop();
        }
    }
}

/*package com.group4.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.*;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ApiDataConsumer {
    public static void main(String[] args) {
        // Configuration Kafka
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");
        props.put("group.id", "test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");
        props.put("enable.auto.commit", "true");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("api-data"));

        ObjectMapper mapper = new ObjectMapper();

        // Lancer le serveur Prometheus sur le port 8081
        try {
            HTTPServer prometheusServer = new HTTPServer(8081);
        } catch (Exception e) {
            System.err.println("Erreur lors du démarrage du serveur Prometheus : " + e.getMessage());
        }

        // Définir les métriques Prometheus
        Gauge temperatureGauge = Gauge.build()
                .name("weather_temperature_celsius")
                .help("Température mesurée en degrés Celsius")
                .register();

        Gauge humidityGauge = Gauge.build()
                .name("weather_humidity_percent")
                .help("Humidité mesurée en pourcentage")
                .register();

        System.out.println("Consumer démarré, en attente de messages...");

        try {
            while (true) {
                System.out.println("Polling Kafka...");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                System.out.println("Nombre de messages reçus : " + records.count());

                for (ConsumerRecord<String, String> record : records) {
                    try {
                        String json = record.value();
                        WeatherData weather = mapper.readValue(json, WeatherData.class);
                        System.out.println("Données météo reçues : " + weather);

                        // Mettre à jour les métriques Prometheus
                        temperatureGauge.set(weather.getTemperature());
                        humidityGauge.set(weather.getHumidity());

                    } catch (Exception e) {
                        System.err.println("Erreur de parsing JSON : " + e.getMessage());
                    }
                }
            }
        } finally {
            consumer.close();
        }
    }
}
*/