package com.xoriant.controller;

import com.xoriant.model.State;
import com.xoriant.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/state")
public class StateController {
    @Autowired
    private StateService stateService;

    @GetMapping
    public ResponseEntity<List<State>> getAllStates() {
        return new ResponseEntity<>(stateService.getAllStates(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getState(@PathVariable Long id) {
        return stateService.getState(id);
    }

}

