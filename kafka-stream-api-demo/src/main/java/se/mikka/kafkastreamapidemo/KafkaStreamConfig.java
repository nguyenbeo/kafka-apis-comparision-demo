package se.mikka.kafkastreamapidemo;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamConfig {

  private static final Logger logger = LoggerFactory.getLogger(KafkaStreamConfig.class);

  @Value("${kafka.topic.input}")
  private String inputTopic;

  @Value("${kafka.topic.output}")
  private String outputTopic;

  /*@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
  public KafkaStreamsConfiguration kafkaStreamsConfigs(KafkaProperties kafkaProperties) {
    Map<String, Object> config = new HashMap<>();
    config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaProperties.getClientId());
    config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
    return new KafkaStreamsConfiguration(config);
  }*/

  @Bean
  public KStream<String, Long> kStream(StreamsBuilder kStreamBuilder) {
    KStream<String, Long> stream = kStreamBuilder.stream(inputTopic);
    stream.filter((k, v) -> v % 2 == 0)
        .mapValues(v -> {
            logger.info("Processing :: {}", v);
            return v * v;
          })
        .to(outputTopic);

    return stream;
  }
}
