package com.xoriant.service;

import com.xoriant.model.Location;
import com.xoriant.model.State;
import com.xoriant.repository.LocationRepository;
import com.xoriant.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    public ResponseEntity<?> getState(Long id) {
        Optional<State> state = stateRepository.findById(id);
        return state.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
