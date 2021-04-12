package com.producer.kafkaproducer.api;

import com.producer.kafkaproducer.config.KafkaProducerConfig;
import com.producer.kafkaproducer.model.Employee;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class ProducerApi {

    public static final String SOURCE_TOPIC = "TextLinesTopic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaProducer<String, Employee> kafkaProducer;

    @GetMapping("/pushString")
    public String pushString(){
        kafkaTemplate.send(KafkaProducerConfig.TOPIC_STRING, "hello kafka"+new Random().nextInt(100));
        return "string msg is published";
    }

    @GetMapping("/pushJavaClass")
    public String pushJavaClass(){
        Employee e = new Employee(new Random().nextInt(100), "Name"+new Random().nextInt(100), 1200L);
        ProducerRecord<String, Employee> rec = new ProducerRecord<>(KafkaProducerConfig.TOPIC_JAVA_CLASS, "key", e);
        kafkaProducer.send(rec, null);
        return "JavaClass msg is published";
    }

    @GetMapping("/pushKafkaStreams")
    public String pushKafkaStreams(@RequestParam String data){
        kafkaTemplate.send(SOURCE_TOPIC, data);
        return "string msg is published to kafka topic where streams read from that topic";
    }
}
