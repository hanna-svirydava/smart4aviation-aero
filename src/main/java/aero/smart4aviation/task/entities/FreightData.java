package aero.smart4aviation.task.entities;

import aero.smart4aviation.task.entities.enums.WeightUnit;
import lombok.Data;

@Data
public class FreightData {
    private int id;
    private int weight;
    private WeightUnit weightUnit;
    private int pieces;
}
