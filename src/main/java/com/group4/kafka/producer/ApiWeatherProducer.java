import org.apache.kafka.clients.producer.*;
import java.net.*;
import java.io.*;
import java.util.Properties;

public class ApiWeatherProducer {
    public static void main(String[] args) throws Exception {
        String apiKey = "6e392382eeff027797eb8c2372a12701";
        String city = "Amiens";
        String urlStr = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        // Kafka configuration
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // API call
        URL url = new URL(urlStr);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine, content = "";
        while ((inputLine = in.readLine()) != null)
            content += inputLine;
        in.close();

        //  DEBUG: Print data before sending
        System.out.println(" Donnée récupérée depuis l'API :");
        System.out.println(content);

        // Envoi dans Kafka
        ProducerRecord<String, String> record = new ProducerRecord<>("api-data", content);
        producer.send(record);
        producer.close();

        System.out.println(" Donnée météo envoyée au topic Kafka.");
    }
}
