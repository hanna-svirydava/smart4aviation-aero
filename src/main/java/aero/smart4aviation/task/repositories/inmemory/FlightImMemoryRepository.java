package aero.smart4aviation.task.repositories.inmemory;

import aero.smart4aviation.task.entities.*;
import aero.smart4aviation.task.repositories.FlightRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Repository
public class FlightImMemoryRepository implements FlightRepository {
    private final ConcurrentMap<Instant, Map<Integer, Flight>> flights = new ConcurrentHashMap<>();

    public void save(List<Flight> flightList) {
        flights.putAll(flightList.stream()
                .collect(groupingBy(
                        Flight::getDepartureDate,
                        Collectors.toMap(Flight::getFlightNumber, flight -> flight))
                )
        );
    }

    public Optional<Flight> findByNumberAndDate(int flightNumber, Instant date) {
        final Map<Integer, Flight> flightsByDate = findFlightMapByDate(date);

        final Flight flight = flightsByDate.get(flightNumber);
        return Optional.ofNullable(flight);
    }

    private Map<Integer, Flight> findFlightMapByDate(Instant date) {
        return flights.getOrDefault(date, Collections.emptyMap());
    }

    public List<Flight> findByDate(Instant date) {
        return new ArrayList<>(findFlightMapByDate(date).values());
    }
}
