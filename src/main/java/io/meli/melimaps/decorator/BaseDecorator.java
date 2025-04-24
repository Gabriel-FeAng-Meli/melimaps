package io.meli.melimaps.decorator;

import io.meli.melimaps.interfaces.TransportStrategy;

public abstract class BaseDecorator implements TransportStrategy {

    protected TransportStrategy strategyConsideringPreferences;

    protected BaseDecorator(TransportStrategy strategy) {
        this.strategyConsideringPreferences = strategy;
    }
    
}
