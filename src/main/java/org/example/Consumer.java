package org.example;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

import static org.example.KafkaConfiguration.DESTINATION_TOPIC;
import static org.example.KafkaConfiguration.getConsumerProps;

public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    public static void main(String[] args) {
        try {
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getConsumerProps());
            consumer.subscribe(Pattern.compile(".*"));

            logger.info("Reading from " + DESTINATION_TOPIC + "...");

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String, String> record : records) {
                        logger.info("Received: key=" + record.key() + ", value=" + record.value() +
                                ", partition=" + record.partition() + ", offset=" + record.offset());
                }
                consumer.commitSync();
            }
        } catch (Exception e) {
            logger.error("Invalid Kafka config: " + e.getMessage());
        }
    }
}
