package technical.test.api.facade;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.mapper.AirportMapper;
import technical.test.api.mapper.FlightMapper;
import technical.test.api.record.AirportRecord;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.services.AirportService;
import technical.test.api.services.FlightService;

@Component
@RequiredArgsConstructor
public class FlightFacade {
    private final FlightService flightService;
    private final AirportService airportService;
    private final FlightMapper flightMapper;
    private final AirportMapper airportMapper;

    public Flux<FlightRepresentation> getAllFlights(String sortBy) {
        Flux<FlightRepresentation> flights = flightService.getAllFlights()
                .flatMap(flightRecord -> airportService.findByIataCode(flightRecord.getOrigin())
                        .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
                        .flatMap(tuple -> {
                            AirportRecord origin = tuple.getT1();
                            AirportRecord destination = tuple.getT2();
                            FlightRepresentation flightRepresentation = this.flightMapper.convert(flightRecord);
                            flightRepresentation.setOrigin(this.airportMapper.convert(origin));
                            flightRepresentation.setDestination(this.airportMapper.convert(destination));
                            return Mono.just(flightRepresentation);
                        }));
        if ("price".equalsIgnoreCase(sortBy)) {
            return flights.sort(Comparator.comparingDouble(FlightRepresentation::getPrice));
        } else if ("location".equalsIgnoreCase(sortBy)) {
            return flights.sort(Comparator.comparing(f -> f.getOrigin().getIata()));
        } else {
            return flights;
        }
    }
    
    public Mono<FlightRepresentation> createFlight(FlightRepresentation representation) {
        return flightService.createFlight(flightMapper.convert(representation))
                .flatMap(savedRecord -> airportService.findByIataCode(savedRecord.getOrigin())
                        .zipWith(airportService.findByIataCode(savedRecord.getDestination()))
                        .map(tuple -> {
                            AirportRecord origin = tuple.getT1();
                            AirportRecord destination = tuple.getT2();

                            FlightRepresentation response = flightMapper.convert(savedRecord);
                            response.setOrigin(airportMapper.convert(origin));
                            response.setDestination(airportMapper.convert(destination));
                            return response;
                        })
                );
    }
    
}
