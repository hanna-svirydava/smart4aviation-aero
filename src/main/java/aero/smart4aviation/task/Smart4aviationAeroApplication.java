package aero.smart4aviation.task;

import aero.smart4aviation.task.entities.Freight;
import aero.smart4aviation.task.entities.Flight;
import aero.smart4aviation.task.services.FlightService;
import aero.smart4aviation.task.services.FreightService;
import aero.smart4aviation.task.services.IATACodeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class Smart4aviationAeroApplication {

    public static void main(String[] args) {
        SpringApplication.run(Smart4aviationAeroApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(FlightService flightService, FreightService freightService,
                                    IATACodeService iataCodeService) {
        return args -> {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            final TypeReference<List<Flight>> flightsTypeReference = new TypeReference<>() {};
            final TypeReference<List<Freight>> cargostypeReference = new TypeReference<>() {};
            final InputStream flightsInputStream = TypeReference.class.getResourceAsStream("/flights.json");
            final InputStream cargosInputStream = TypeReference.class.getResourceAsStream("/freights.json");

            try {
                final List<Flight> flights = mapper.readValue(flightsInputStream, flightsTypeReference);
                flightService.uploadFlights(flights);
            } catch (IOException e) {
                throw new IllegalArgumentException("Unable to load flights.", e);
            }

            try {
                final List<Freight> freights = mapper.readValue(cargosInputStream, cargostypeReference);
                freightService.uploadFreights(freights);
            } catch (IOException e) {
                throw new IllegalArgumentException("Unable to load freights.", e);
            }

            iataCodeService.upload(List.of("SEA","YYZ","YYT","ANC","LAX","MIT","LEW","GDN","KRK","PPX"));
        };
    }
}
