package io.conduktor.demos.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoKeys {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemoKeys.class.getSimpleName());

    public static void main(String[] args) throws InterruptedException {

        // Create Producer Properties
        Properties properties = new Properties();

        //  Connect to localhost
        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");

        //  Set producer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());


        //  Create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for(int j=0; j<2; j++) {
            for (int i = 0; i < 30; i++) {

                String topic = "demo_java";
                String key = "id_" + i;
                String value = "hello world " + i;

                // Create a Producer Record
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);

                //  Send  data
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        // executes every time a recod successfully sent or exception is thrown
                        if (e == null) {
                            // log the record was successfully sent
                            log.info("Key: " + key + " | Partition: " + recordMetadata.partition());

                        } else {
                            log.error("Exception", e);
                        }
                    }
                });
            }

            try {
                Thread.sleep(500);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        //  tell the producer to send all data and block until done -- synchronous
        producer.flush();

        // flush and close producer
        producer.close();
    }
}
