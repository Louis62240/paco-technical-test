package technical.test.renderer.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.clients.TechnicalApiClient;
import technical.test.renderer.viewmodels.FlightViewModel;

@Service
public class FlightService {
    private final TechnicalApiClient technicalApiClient;

    public FlightService(TechnicalApiClient technicalApiClient) {
        this.technicalApiClient = technicalApiClient;
    }

    public Flux<FlightViewModel> getFlights(int page, int size, String sortBy) {
        return this.technicalApiClient.getFlights(page, size, sortBy);
    }
    
    public Mono<FlightViewModel> getFlightById(String id) {
        return this.technicalApiClient.getFlightById(id);
    }

    public Mono<Void> createFlight(FlightViewModel flight) {
        return this.technicalApiClient.createFlight(flight);
    }

}
