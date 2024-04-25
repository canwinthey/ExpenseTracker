package com.xoriant.service;

import com.xoriant.model.LocationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "location-service")
public interface LocationServiceProxy {

	@GetMapping("/location/{id}")
	public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id);

}