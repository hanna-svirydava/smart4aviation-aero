package aero.smart4aviation.task.dto;

import aero.smart4aviation.task.entities.Weight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightWeightStatistics {
    private List<Weight> cargoWeight = new ArrayList<>();
    private List<Weight> baggageWeight = new ArrayList<>();
    private List<Weight> totalWeight = new ArrayList<>();
}
