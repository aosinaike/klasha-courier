package com.klasha.courier;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;

public interface CourierService {
    Flux<LocationDTO> getLocations();
    Flux<LocationDTO> AddLocation(LocationDTO location);
    Flux<LocationDTO> updateLocation(LocationDTO location);
    Flux<LocationDTO> removeLocationByName(String locationName);
    Mono<HashMap<Double, LocationDTO>> findRoute(String destination);

}
