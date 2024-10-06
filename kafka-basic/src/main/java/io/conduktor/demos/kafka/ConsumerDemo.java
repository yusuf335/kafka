package io.conduktor.demos.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemo {

    private static final Logger log = LoggerFactory.getLogger(ConsumerDemo.class.getSimpleName());

    public static void main(String[] args) {

        String groupId = "my-java-application";

        // Create Producer Properties
        Properties properties = new Properties();

        //  Connect to localhost
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");


        // Consumer configs
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());

        properties.setProperty("group.id", groupId);

        properties.setProperty("auto.offset.reset", "earliest");

        //   create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // Subscribe to a topic
        String topic = "demo_java";
        consumer.subscribe(Arrays.asList(topic));

        // Poll data

        while(true){
             log.info("Polling");

             ConsumerRecords<String, String> records =  consumer.poll(Duration.ofMillis(1000));

             for(ConsumerRecord<String, String> record: records){
                 log.info("key: " + record.key() + ", Value: " + record.value());
                 log.info("Partition: " + record.partition() + ", offset: " + record.offset());
             }

        }

    }
}
