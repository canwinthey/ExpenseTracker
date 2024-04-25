package com.xoriant.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationDto {

    private Long locationId;
    private Long stateId;
    private String location;
    private String state;

    public LocationDto(Long locationId){
        this.locationId = locationId;
    }
    public LocationDto(Long stateId, Long locationId){
        this.stateId = stateId;
        this.locationId = locationId;
    }
    public LocationDto(Long stateId, Long locationId, String state, String location){
        this.stateId = stateId;
        this.locationId = locationId;
        this.state = state;
        this.location = location;
    }

}
