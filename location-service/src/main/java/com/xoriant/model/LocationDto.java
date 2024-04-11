package com.xoriant.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class LocationDto {

    private Long locationId;
    private Long stateId;
    private String location;
    private String state;

    public LocationDto(Long locationId) {
        this.locationId = locationId;
    }

    public LocationDto(Long locationId, Long stateId) {
        this.locationId = locationId;
        this.stateId = stateId;
    }

}
