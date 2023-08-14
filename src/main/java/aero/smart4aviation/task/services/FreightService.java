package aero.smart4aviation.task.services;

import aero.smart4aviation.task.entities.*;
import aero.smart4aviation.task.repositories.FreightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreightService {
    private final FreightRepository freightRepository;

    /**
     * Upload freights.
     *
     * @param freights are list of freight's objects
     */
    public void uploadFreights(List<Freight> freights) {
        freightRepository.save(freights);
    }
}
