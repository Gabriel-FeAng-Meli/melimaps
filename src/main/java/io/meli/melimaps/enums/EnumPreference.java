package io.meli.melimaps.enums;

import io.meli.melimaps.decorator.AccessibilityDecorator;
import io.meli.melimaps.decorator.BudgetDecorator;
import io.meli.melimaps.decorator.EcoDecorator;
import io.meli.melimaps.decorator.TimeDecorator;
import io.meli.melimaps.interfaces.TransportStrategy;

public enum EnumPreference {
    DISTANCE,
    ECO,
    BUDGET,
    TIME,
    ACCESSIBILITY;

    public TransportStrategy chooseDecorator(TransportStrategy strategy) {

        return switch (this) {
            case ECO -> new EcoDecorator(strategy);
            case ACCESSIBILITY -> new AccessibilityDecorator(strategy);
            case BUDGET -> new BudgetDecorator(strategy);
            case TIME -> new TimeDecorator(strategy);
            default -> strategy;
        };
    }

}
