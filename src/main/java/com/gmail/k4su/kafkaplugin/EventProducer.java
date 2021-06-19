package com.gmail.k4su.kafkaplugin;

import org.spongepowered.api.event.Event;

import java.io.IOException;


public interface EventProducer {

    void produce(Event event);
}
