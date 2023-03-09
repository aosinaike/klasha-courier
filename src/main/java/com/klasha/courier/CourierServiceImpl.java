package com.klasha.courier;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourierServiceImpl implements CourierService{
    List<LocationDTO> locations = new ArrayList<LocationDTO>();
    double costPerKm = 1.00;
    @Override
    public Flux<LocationDTO> getLocations() {
        return Flux.fromIterable(locations);
    }

    @Override
    public Flux<LocationDTO> AddLocation(LocationDTO location) {
        locations.add(location);
        return Flux.fromIterable(locations);
    }

    @Override
    public Flux<LocationDTO> updateLocation(LocationDTO location) {
        locations.stream()
                .filter(loc -> loc.getName().equals(location.getName()))
                .findFirst().ifPresent(loc -> {
                    loc.setOrigin(location.getOrigin());
                    loc.setDistance(location.getDistance());
                    loc.setDestination(location.getOrigin());
                });
        return Flux.fromIterable(locations);
    }

    @Override
    public Flux<LocationDTO> removeLocationByName(String locationName) {
        locations.stream()
                .filter(loc -> loc.getName().equals(locationName)).collect(Collectors.toList());
        return Flux.fromIterable(locations);
    }

    public Mono<HashMap<Double, LocationDTO>> findRoute(String destination){
       LocationDTO existingLocation = locations.stream()
                .filter(loc -> loc.getName().equals(destination))
               .min(Comparator.comparing(LocationDTO::getDistance))
               .orElseThrow(NoSuchElementException::new);
       double totalCost = existingLocation.getDistance() * costPerKm;
        HashMap<Double, LocationDTO> result = new HashMap<Double, LocationDTO>();
        result.put(totalCost, existingLocation);
        return Mono.just(result);
    }
}
