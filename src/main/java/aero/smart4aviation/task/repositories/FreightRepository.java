package aero.smart4aviation.task.repositories;

import aero.smart4aviation.task.entities.Freight;

import java.util.List;
import java.util.Optional;

public interface FreightRepository {
    /**
     * Save freights.
     *
     * @param freightList is a list of freights
     */
    void save(List<Freight> freightList);

    /**
     * Get freight by flight ID.
     *
     * @param flightId is a flight id
     * @return freight object
     */
    Optional<Freight> findById(int flightId);
}
