package aero.smart4aviation.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightsStatistics {
    private int departedFlightsNumber;
    private int arrivedFlightsNumber;
    private int totalBaggagePiecesArrivedTo;
    private int totalBaggagePiecesDepartedFrom;
}
