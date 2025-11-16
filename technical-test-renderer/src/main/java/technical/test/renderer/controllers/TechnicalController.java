package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import reactor.core.publisher.Mono;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.FlightViewModel;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {

    @Autowired
    private FlightFacade flightFacade;

    @GetMapping
    public Mono<String> getMarketPlaceReturnCouponPage(
            final Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String sortBy) {

        return this.flightFacade.getFlights(page, size, sortBy)
                .collectList()
                .map(flights -> {
                    model.addAttribute("flights", flights);
                    model.addAttribute("currentPage", page);
                    model.addAttribute("size", size);
                    model.addAttribute("sortBy", sortBy);
                    return "pages/index";
                });
    }
    @GetMapping("/flight/{id}")
    public Mono<String> getFlightDetailPage(
            final Model model,
            @PathVariable String id,
            @RequestParam(required = false) String sortBy) {

        return flightFacade.getFlightById(id)
                .map(flight -> {
                    model.addAttribute("flight", flight);
                    model.addAttribute("sortBy", sortBy); 
                    return "pages/flight-details";
                });
    }


    @GetMapping("/flight/create")
    public String createFlightPage() {
        return "pages/create-flight";
    }
    
    @PostMapping("/flight/create")
    public Mono<String> submitCreateFlightForm(FlightViewModel flight) {
        return flightFacade.createFlight(flight)
                .then(Mono.just("redirect:/"));
    }

}
