package aero.smart4aviation.task.entities;

import aero.smart4aviation.task.entities.enums.WeightUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Weight {
    private double weight;
    private WeightUnit unit;
}
