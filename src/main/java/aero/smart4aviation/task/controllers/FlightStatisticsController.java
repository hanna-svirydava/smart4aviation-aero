package aero.smart4aviation.task.controllers;

import aero.smart4aviation.task.dto.FlightWeightStatistics;
import aero.smart4aviation.task.dto.FlightsStatistics;
import aero.smart4aviation.task.services.FlightStatisticsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class FlightStatisticsController {
    private final FlightStatisticsService flightStatisticsService;

    /**
     * Get flight weight statistics by Flight Number and date.
     * All weights return values in both units (It means weights contain common sums in different units).
     *
     * @param flightNumber is a flight number
     * @param date         is a date
     * @return flight weight statistics object that contains cargo weight,
     * baggage weight and total weight for requested Flight.
     */
    @ApiResponse(responseCode = "404", description = "If flight with flightNumber and date doesn't exist")
    @ApiResponse(responseCode = "400", description = "If request params are invalid")
    @GetMapping("/flight")
    public FlightWeightStatistics getWeightStatisticsByFlightNumberAndDate(@RequestParam int flightNumber,
                                                                           @RequestParam Instant date) {
        return flightStatisticsService.getWeightStatisticsByFlightNumberAndDate(flightNumber, date);
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
    @ApiResponse(responseCode = "404", description = "If flight with IATA code and date doesn't exist")
    @ApiResponse(responseCode = "400", description = "If request params are invalid")
    @GetMapping("/IATACode")
    public FlightsStatistics getFlightsStatisticsByIATACodeAndDate(@RequestParam String code,
                                                                   @RequestParam Instant date) {
        return flightStatisticsService.getStatisticsByIATACodeAndDate(code, date);
    }
}
