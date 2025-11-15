package technical.test.renderer.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.properties.TechnicalApiProperties;
import technical.test.renderer.viewmodels.AirportViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

@Component
@Slf4j
public class TechnicalApiClient {

    private final TechnicalApiProperties technicalApiProperties;
    private final WebClient webClient;

    public TechnicalApiClient(TechnicalApiProperties technicalApiProperties, final WebClient.Builder webClientBuilder) {
        this.technicalApiProperties = technicalApiProperties;
        this.webClient = webClientBuilder.build();
    }

    public Flux<FlightViewModel> getFlights(int page, int size, String sortBy) {
        return webClient
                .get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(8086)
                            .path("/flight")
                            .queryParam("page", page)
                            .queryParam("size", size);

                    if (sortBy != null && !sortBy.isBlank()) {
                        builder.queryParam("sortBy", sortBy);
                    }

                    return builder.build();
                })
                .retrieve()
                .bodyToFlux(FlightViewModel.class);
    }
    
    public Mono<FlightViewModel> getFlightById(String id) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8086)
                        .path("/flight/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(FlightViewModel.class)
                .doOnError(error -> log.error("Erreur lors de la récupération du vol avec id {}", id, error));
    }

    public Mono<Void> createFlight(FlightViewModel flight) {
        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8086)
                        .path("/flight")
                        .build())
                .bodyValue(flight)
                .retrieve()
                .bodyToMono(Void.class);
    }


}
