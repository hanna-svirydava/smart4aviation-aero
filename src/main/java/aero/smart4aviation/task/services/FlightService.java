package aero.smart4aviation.task.services;

import aero.smart4aviation.task.entities.Flight;
import aero.smart4aviation.task.repositories.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    /**
     * Upload flights.
     *
     * @param flights are list of flight's objects
     */
    public void uploadFlights(List<Flight> flights) {
        flightRepository.save(flights);
    }
}
