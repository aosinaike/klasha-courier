package com.klasha.courier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
@RequestMapping("courier")
public class CourierController {
//    View a list of delivery locations
//3. Add | remove | update delivery locations (minimum of 3 locations must exist before deliveries
//            can start)

    @Autowired
    CourierService service;
    @GetMapping("/locations")
    public Mono<ResponseEntity> viewDeliveryLocations(){
        Flux<LocationDTO> locationDTOFlux = service.getLocations();
        locationDTOFlux.collectList().flatMapMany(Flux::just)
                .map(locationDTOList-> ResponseEntity.status(HttpStatus.OK).body(locationDTOList));
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/locations/add")
    public Mono<ResponseEntity> addLocation(@RequestBody LocationDTO location){
        Flux<LocationDTO> locationDTOFlux = service.AddLocation(location);
        locationDTOFlux.collectList().flatMapMany(Flux::just)
                .map(locationDTOList-> ResponseEntity.status(HttpStatus.OK).body(locationDTOList));
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/locations/remove")
    public Mono<ResponseEntity> removeLocation(@RequestBody LocationDTO location){
        Flux<LocationDTO> locationDTOFlux = service.removeLocationByName(location.getName());
        locationDTOFlux.collectList().flatMapMany(Flux::just)
                .map(locationDTOList-> ResponseEntity.status(HttpStatus.OK).body(locationDTOList));
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/locations/update")
    public Mono<ResponseEntity> updateLocation(@RequestBody LocationDTO location){
        Flux<LocationDTO> locationDTOFlux = service.updateLocation(location);
        locationDTOFlux.collectList().flatMapMany(Flux::just)
                .map(locationDTOList-> ResponseEntity.status(HttpStatus.OK).body(locationDTOList));
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/locations/route")
    public Mono<ResponseEntity> findBestRoute(@RequestParam("destination") String destination){
        Mono<HashMap<Double, LocationDTO>> locationCost = service.findRoute(destination);
        return locationCost.map(loc->{
            if (!loc.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(locationCost);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        });
    }

}
