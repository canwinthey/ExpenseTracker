package com.xoriant.service;

import com.xoriant.model.Location;
import com.xoriant.model.LocationDto;
import com.xoriant.model.State;
import com.xoriant.repository.LocationRepository;
import com.xoriant.repository.StateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private StateRepository stateRepository;

    public ResponseEntity<List<Location>> getAllLocation() {
        ResponseEntity<List<Location>> responseEntity = null;
        List<Location> list = locationRepository.findAll();
        if(CollectionUtils.isEmpty(list)){
            responseEntity = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
        }
        return responseEntity;
    }

    public ResponseEntity<LocationDto> getLocation(Long id) {
        ResponseEntity<LocationDto> responseEntity = null;
        LocationDto locationDto = new LocationDto(id);
        Location location = new Location();
        Optional<Location> locationOpt = locationRepository.findById(id);
        if(locationOpt.isPresent()){
            locationDto.setLocation(locationOpt.get().getLocation());
            Optional<State> stateOpt = stateRepository.findById(locationOpt.get().getStateId());
            if (stateOpt.isPresent()){
                locationDto.setStateId(stateOpt.get().getId());
                locationDto.setState(stateOpt.get().getState());
            }
            responseEntity = new ResponseEntity<>(locationDto, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(locationDto, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    public ResponseEntity<LocationDto> getLocationByStateIdAndId(Long stateId, Long id) {
        ResponseEntity<LocationDto> responseEntity = null;
        LocationDto locationDto = new LocationDto(id, stateId);
        Optional<Location> locationOpt = locationRepository.findLocationByStateIdAndId(stateId, id);

        if(locationOpt.isPresent()){
            locationDto.setLocation(locationOpt.get().getLocation());
            Optional<State> stateOpt = stateRepository.findById(stateId);
            if (stateOpt.isPresent()){
                locationDto.setState(stateOpt.get().getState());
            }
            responseEntity = new ResponseEntity<>(locationDto, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(locationDto, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    public ResponseEntity<List<Location>> getLocationByStateId(Long stateId) {
        ResponseEntity<List<Location>> responseEntity = null;
        List<Location> list = new ArrayList<>();
        Optional<List<Location>> optList = locationRepository.findLocationByStateId(stateId);
        if(optList.isPresent()){
            list = optList.get();
            for (Location location : list) {
                LocationDto locationDto = new LocationDto();
                BeanUtils.copyProperties(location, locationDto);

            }
            responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    public ResponseEntity<Location> createLocation(Location Location) throws URISyntaxException {
        Location result = locationRepository.save(Location);
        return ResponseEntity.created(new URI("/Location" + result.getId())).body(result);
    }

    public ResponseEntity<Location> updateLocation(Location Location) {
        Location result = locationRepository.save(Location);
        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity<Location> deleteLocation(Long id) {
        locationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
