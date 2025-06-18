package org.example;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;
import static org.example.UppercaseStream.SOURCE_TOPIC;

public class Producer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        producer.send(new ProducerRecord<>(SOURCE_TOPIC, "copy code"));
        producer.send(new ProducerRecord<>(SOURCE_TOPIC, "hello"));
        producer.send(new ProducerRecord<>(SOURCE_TOPIC, "kafka"));
        producer.send(new ProducerRecord<>(SOURCE_TOPIC, "streams"));

        producer.close();
    }
}
