package technical.test.api.endpoints;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import technical.test.api.facade.FlightFacade;
import technical.test.api.representation.FlightRepresentation;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightEndpoint {
    private final FlightFacade flightFacade;

    @GetMapping
    public Flux<FlightRepresentation> getAllFlights(
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return flightFacade.getAllFlights(sortBy, page);
    }

    
    @PostMapping
    public Mono<FlightRepresentation> createFlight(@RequestBody FlightRepresentation flight) {
        return flightFacade.createFlight(flight);
    }
    

}
