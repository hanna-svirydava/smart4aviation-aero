package aero.smart4aviation.task.repositories;

import aero.smart4aviation.task.entities.Flight;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface FlightRepository {
    /**
     * Save flights to memory.
     *
     * @param flightList is list of flights
     */
    void save(List<Flight> flightList);

    /**
     * Get flight by flight number and date.
     *
     * @param flightNumber is a flight number
     * @param date         is a date
     * @return flight object
     */
    Optional<Flight> findByNumberAndDate(int flightNumber, Instant date);

    /**
     * Get flights by date.
     *
     * @param date is a date
     * @return list of flight objects
     */
    List<Flight> findByDate(Instant date);
}
