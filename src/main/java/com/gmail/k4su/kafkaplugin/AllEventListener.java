package com.gmail.k4su.kafkaplugin;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.EventListener;

import javax.annotation.Nonnull;


public class AllEventListener implements EventListener<Event> {

    private final EventProducer eventProducer;

    public AllEventListener(final EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @Override
    public void handle(@Nonnull final Event event) {
        eventProducer.produce(event);
    }
}
