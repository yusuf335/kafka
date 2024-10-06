package io.conduktor.demos.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemoCooperative {

    private static final Logger log = LoggerFactory.getLogger(ConsumerDemoCooperative.class.getSimpleName());

    public static void main(String[] args) {

        String groupId = "my-java-application";
        String topic = "demo_java";

        // Create Producer Properties
        Properties properties = new Properties();

        //  Connect to localhost
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        // Consumer configs
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", groupId);
        properties.setProperty("auto.offset.reset", "earliest");
        properties.setProperty("partition.assignment.strategy", CooperativeStickyAssignor.class.getName());

        //   create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // get a reference to the main thread
        final Thread mainThread = Thread.currentThread();

        // adding shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                log.info("Detected a shutdown");
                consumer.wakeup();

                // Joining the main thread to allow the execution of the code in the main thread
                try{
                    mainThread.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        try {
            // Subscribe to a topic
            consumer.subscribe(Arrays.asList(topic));

            // Poll data
            while (true) {
                // log.info("Polling");

                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records) {
                    log.info("key: " + record.key() + ", Value: " + record.value());
                    log.info("Partition: " + record.partition() + ", offset: " + record.offset());
                }

            }
        } catch (WakeupException e){
            log.info("Consumer is starting to shut down");
        } catch (Exception e){
            log.info("Unexpected exception in the consumer", e);
        } finally {
            consumer.close(); // Close the consumer
            log.info("Consumer is shutdown gracefully");
        }

    }
}
