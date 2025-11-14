package technical.test.api.facade;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.UUID;

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
    private static final int PAGE_SIZE = 6;

    public Flux<FlightRepresentation> getAllFlights(String sortBy, int page) {
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

        // Appliquer le tri sur le Flux retourné
        if ("price".equalsIgnoreCase(sortBy)) {
            flights = flights.sort(Comparator.comparingDouble(FlightRepresentation::getPrice));
        } else if ("location".equalsIgnoreCase(sortBy)) {
            flights = flights.sort(Comparator.comparing(f -> f.getOrigin().getIata()));
        }

        return flights.skip(page * PAGE_SIZE).take(PAGE_SIZE);
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
    
    public Mono<FlightRepresentation> getFlightById(UUID id) {
        return flightService.getFlightById(id)
                .flatMap(flightRecord -> airportService.findByIataCode(flightRecord.getOrigin())
                        .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
                        .map(tuple -> {
                            AirportRecord origin = tuple.getT1();
                            AirportRecord destination = tuple.getT2();

                            FlightRepresentation response = flightMapper.convert(flightRecord);
                            response.setOrigin(airportMapper.convert(origin));
                            response.setDestination(airportMapper.convert(destination));
                            return response;
                        })
                );
    }


    
}
