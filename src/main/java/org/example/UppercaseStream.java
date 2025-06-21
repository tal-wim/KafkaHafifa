package org.example;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.KafkaConfiguration.*;

public class UppercaseStream {
    private static final Logger logger = LoggerFactory.getLogger(UppercaseStream.class);

    public static void main(String[] args) {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> sourceStream = builder.stream(SOURCE_TOPIC);
        KStream<String, String> upperStream = sourceStream.mapValues(value -> value.toUpperCase());
        upperStream.to(DESTINATION_TOPIC);

        try {
            KafkaStreams streams = new KafkaStreams(builder.build(), getStreamsProps());
            streams.start();
        } catch (Exception exception) {
            logger.error("Invalid Kafka config: " + exception.getMessage());
        }
    }
}
