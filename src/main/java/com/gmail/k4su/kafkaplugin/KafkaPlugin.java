package com.gmail.k4su.kafkaplugin;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import java.util.Properties;


@Plugin(id = "kafka-plugin")
public class KafkaPlugin {

    private static final String topicName = "events";

    private static final String zookeeperUrl = "localhost:9092";

    private final Producer<String, String> producer;

    public KafkaPlugin() {
        final Properties properties = new Properties();
        properties.put("bootstrap.servers", zookeeperUrl);
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(properties);
    }

    @Listener
    public void onInit(final GameInitializationEvent event) {
        Sponge.getEventManager().registerListener(this, Event.class, new AllEventListener(
                this::processEvent
        ));
    }

    public void processEvent(final Event event) {
        final String key = event.getClass().getSimpleName();
        final String value = event.toString();
        producer.send(new ProducerRecord<>(
                topicName,
                key,
                value
        ));
    }
}
