package aero.smart4aviation.task.entities;

import lombok.Data;

import java.util.List;

@Data
public class Freight {
    private Integer flightId;
    private List<BaggageData> baggage;
    private List<CargoData> cargo;
}
