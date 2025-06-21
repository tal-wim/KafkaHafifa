package org.example;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.AutoOffsetReset;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.common.serialization.Serdes;
import java.util.Properties;
public class KafkaConfiguration {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public final static String DESTINATION_TOPIC = "transformed-topic";
    public final static String SOURCE_TOPIC = "test-topic";
    private final static String TRANSFORMED_CONSUMERS = "transformed-consumer-group";
    private final static String AUTO_COMMIT_MS = "1000";
    private final static String AUTO_OFFSET_RESET = "earliest";
    private final static String APP_ID =  "uppercase-app";

    private static Properties getBaseProps() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);

        return props;
    }

    public static Properties getProducerProps() {
        Properties props = new Properties();
        props.putAll(getBaseProps());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return props;
    }

    public static Properties getConsumerProps() {
        Properties props = new Properties();
        props.putAll(getBaseProps());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, TRANSFORMED_CONSUMERS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, AUTO_COMMIT_MS);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET);

        return props;
    }

    public static Properties getStreamsProps() {
        Properties props = new Properties();
        props.putAll(getBaseProps());
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APP_ID);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        return props;
    }
}
