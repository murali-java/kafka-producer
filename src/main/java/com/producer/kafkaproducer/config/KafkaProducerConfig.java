package com.producer.kafkaproducer.config;

import com.producer.kafkaproducer.model.Employee;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    public static final String TOPIC_STRING = "topicString";
    public static final String TOPIC_JAVA_CLASS = "topicJavaClass";

    @Bean
    @Qualifier("topicString")
    public NewTopic topicString() {
        return TopicBuilder.name(TOPIC_STRING)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    @Qualifier("topicJavaClass")
    public NewTopic topicJavaClass() {
        return TopicBuilder.name(TOPIC_JAVA_CLASS)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaProducer<String, Employee> kafkaProducer(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG , "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG , StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG , JsonSerializer.class);
        return new KafkaProducer<String, Employee>(props);
    }
}
