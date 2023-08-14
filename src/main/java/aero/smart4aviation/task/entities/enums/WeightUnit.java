package aero.smart4aviation.task.entities.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WeightUnit {
    KG("kg"),
    LB("lb");

    private final String unit;

    WeightUnit(String unit) {
        this.unit = unit;
    }

    @JsonValue
    public String getUnit() {
        return unit;
    }
}
