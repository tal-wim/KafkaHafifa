package org.example;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import static org.example.KafkaConfiguration.SOURCE_TOPIC;
import static org.example.KafkaConfiguration.getProducerProps;

public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) {
        try {
            KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerProps());
            ArrayList<String> messages = new ArrayList<>(Arrays.asList("copy code", "hello", "kafka", "streams"));

            for (int messagesNumber = 0; messagesNumber < messages.size(); messagesNumber++) {
                ProducerRecord<String, String> record = new ProducerRecord<>(SOURCE_TOPIC, String.valueOf(messagesNumber), messages.get(messagesNumber));
                producer.send(record, (metadata, exception) -> {
                    if (exception != null) {
                        logger.error("Send failed: " + exception.getMessage());
                    }
                });
                logger.info("message was sent: " + messages.get(messagesNumber));
            }

            producer.close();
        } catch (Exception exception) {
            logger.error("Invalid Kafka config: " + exception.getMessage());
        }
    }
}
