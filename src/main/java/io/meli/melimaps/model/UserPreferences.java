package io.meli.melimaps.model;

public record UserPreferences(String transport, Boolean preferLowCO2EmissionTransport, Boolean preferFasterTransport, Boolean preferAccessibleTransport, Boolean preferLowCostTransport) {
    
}
