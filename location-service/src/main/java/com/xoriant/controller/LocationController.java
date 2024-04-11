package com.xoriant.controller;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import com.xoriant.model.LocationDto;
import com.xoriant.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xoriant.model.Location;

@CrossOrigin("*")
@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<List<Location>> Location() {
        return locationService.getAllLocation();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id) {
        return locationService.getLocation(id);
    }

    @GetMapping("/{stateId}/{id}")
    public ResponseEntity<LocationDto> getLocationByStateIdAndId(@PathVariable Long stateId,
                                                @PathVariable Long id) {
        return locationService.getLocationByStateIdAndId(stateId, id);
    }
    @GetMapping("state/{stateId}")
    public ResponseEntity<List<Location>> getLocationByStateId(@PathVariable Long stateId){
        return locationService.getLocationByStateId(stateId);
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@Valid @RequestBody Location Location) throws URISyntaxException {
        return locationService.createLocation(Location);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@Valid @RequestBody Location Location) {
        return locationService.updateLocation(Location);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Location> deleteLocation(@PathVariable Long id) {
        return locationService.deleteLocation(id);
    }
}

