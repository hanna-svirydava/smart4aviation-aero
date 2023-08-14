package aero.smart4aviation.task.repositories.inmemory;

import aero.smart4aviation.task.entities.*;
import aero.smart4aviation.task.repositories.FreightRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Repository
public class FreightImMemoryRepository implements FreightRepository {
    private final ConcurrentMap<Integer, Freight> freights = new ConcurrentHashMap<>();

    public void save(List<Freight> freightList) {
        freights.putAll(freightList.stream()
                .collect(
                        Collectors.toMap(Freight::getFlightId, freight -> freight)
                )
        );
    }

    public Optional<Freight> findById(int flightId) {
        return Optional.ofNullable(freights.get(flightId));
    }
}
