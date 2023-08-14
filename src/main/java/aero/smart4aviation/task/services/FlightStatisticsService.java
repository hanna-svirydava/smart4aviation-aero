package aero.smart4aviation.task.services;

import aero.smart4aviation.task.dto.FlightWeightStatistics;
import aero.smart4aviation.task.dto.FlightsStatistics;
import aero.smart4aviation.task.entities.Weight;
import aero.smart4aviation.task.entities.*;
import aero.smart4aviation.task.entities.enums.WeightUnit;
import aero.smart4aviation.task.exceptions.BadRequestException;
import aero.smart4aviation.task.exceptions.NotFoundException;
import aero.smart4aviation.task.repositories.FlightRepository;
import aero.smart4aviation.task.repositories.FreightRepository;
import aero.smart4aviation.task.repositories.IATACodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightStatisticsService {
    private final FlightRepository flightRepository;
    private final FreightRepository freightRepository;
    private final IATACodeRepository iataCodeRepository;

    /**
     * Get flight weight statistics by Flight Number and date.
     *
     * @param flightNumber is a flight number
     * @param date         is a date
     * @return flight weight statistics object that contains cargo weight,
     * baggage weight and total weight for requested Flight.
     */
    public FlightWeightStatistics getWeightStatisticsByFlightNumberAndDate(int flightNumber,
                                                                           Instant date) {
        if (flightNumber < 1000 || flightNumber > 9999) {
            throw new BadRequestException("Flight number have to be in the following boundaries [1000; 9999].");
        }
        final Flight flight = flightRepository.findByNumberAndDate(flightNumber, date)
                .orElseThrow(() -> new NotFoundException("Flight with number " + flightNumber + " at date: " + date + " doesn't exist."));
        final Optional<Freight> optionalFreight = freightRepository.findById(flight.getFlightId());
        if (optionalFreight.isEmpty()) {
            final List<Weight> emptyData = List.of(
                    new Weight(0, WeightUnit.KG),
                    new Weight(0, WeightUnit.LB));
            return new FlightWeightStatistics(emptyData, emptyData, emptyData);
        }
        final Freight freight = optionalFreight.get();

        final List<Weight> cargoWeight = calculateWeight(freight.getCargo());
        final List<Weight> baggageWeight = calculateWeight(freight.getBaggage());
        final List<Weight> totalWeight = List.of(
                new Weight(cargoWeight.get(0).getWeight() + baggageWeight.get(0).getWeight(), WeightUnit.KG),
                new Weight(cargoWeight.get(1).getWeight() + baggageWeight.get(1).getWeight(), WeightUnit.LB));

        return new FlightWeightStatistics(cargoWeight, baggageWeight, totalWeight);
    }

    /**
     * Get flight statistics by IATA Airport Code and date.
     *
     * @param code is an IATA code
     * @param date is a date
     * @return flight statistics data that contains number of flights
     * departing from this airport, number of flights arriving to this airport,
     * total number (pieces) of baggage arriving to this airport
     * and total number (pieces) of baggage departing from this airport.
     */
    public FlightsStatistics getStatisticsByIATACodeAndDate(String code,
                                                            Instant date) {
        if (!iataCodeRepository.existByCode(code)) {
            throw new NotFoundException("IATA code: " + code + " doesn't supported.");
        }

        final List<Flight> flightsByDate = flightRepository.findByDate(date);

        int departedFlightsNumber = 0;
        int arrivedFlightsNumber = 0;
        int totalBaggagePiecesArrivedTo = 0;
        int totalBaggagePiecesDepartedFrom = 0;
        for (final Flight flight : flightsByDate) {
            final Optional<Freight> optionalFreight = freightRepository.findById(flight.getFlightId());
            if (optionalFreight.isEmpty()) {
                continue;
            }
            final Freight freight = optionalFreight.get();
            if (flight.getDepartureAirportIATACode().equals(code)) {
                departedFlightsNumber++;
                totalBaggagePiecesDepartedFrom += calculatePiecesSum(freight.getBaggage());
            }
            if (flight.getArrivalAirportIATACode().equals(code)) {
                arrivedFlightsNumber++;
                totalBaggagePiecesArrivedTo += calculatePiecesSum(freight.getBaggage());
            }
        }

        return new FlightsStatistics(
                departedFlightsNumber, arrivedFlightsNumber,
                totalBaggagePiecesArrivedTo, totalBaggagePiecesDepartedFrom
        );
    }

    private int calculatePiecesSum(List<BaggageData> data) {
        int totalPieces = 0;
        for (final BaggageData baggage : data) {
            totalPieces += baggage.getPieces();
        }
        return totalPieces;
    }

    private List<Weight> calculateWeight(List<? extends FreightData> freightData) {
        double weightInKg = 0;
        double weightInLb = 0;
        if (freightData != null) {
            for (final FreightData data : freightData) {
                if (data.getWeightUnit().equals(WeightUnit.KG)) {
                    weightInKg += data.getWeight();
                } else {
                    weightInLb += data.getWeight();
                }
            }
        }

        // Coefficient to convert kg to lbs and vice versa.
        final double COEFFICIENT = 2.20462;
        return List.of(new Weight(weightInKg + weightInLb / COEFFICIENT, WeightUnit.KG),
                new Weight(weightInLb + weightInKg * COEFFICIENT, WeightUnit.LB));
    }
}
