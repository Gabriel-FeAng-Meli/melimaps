package io.meli.melimaps.enums;

import io.meli.melimaps.decorator.AccessibilityDecorator;
import io.meli.melimaps.decorator.BaseDecorator;
import io.meli.melimaps.decorator.BudgetDecorator;
import io.meli.melimaps.decorator.EcoDecorator;
import io.meli.melimaps.decorator.TimeDecorator;

public enum EnumDecoration {
    DISTANCE,
    ECO,
    BUDGET,
    TIME,
    ACCESSIBILITY;

    public BaseDecorator chooseDecorator(EnumTransport t) {

        return switch (this) {
            case ECO -> new EcoDecorator(t);
            case ACCESSIBILITY -> new AccessibilityDecorator(t);
            case BUDGET -> new BudgetDecorator(t);
            case TIME -> new TimeDecorator(t);
            default -> null;
        };
    }

}
