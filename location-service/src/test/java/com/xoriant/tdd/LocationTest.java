package com.xoriant.tdd;

import com.xoriant.model.Location;
import com.xoriant.model.LocationDto;
import com.xoriant.model.State;
import com.xoriant.repository.LocationRepository;
import com.xoriant.repository.StateRepository;
import com.xoriant.service.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LocationTest {

	@Mock
	private LocationRepository locationRepository;

	@Mock
	private StateRepository stateRepository;

	@InjectMocks
	private LocationService locationService;

	@Test
	void testGetAllLocation_HappyPath() {
		List<Location> locations = new ArrayList<>();
		locations.add(new Location(1L, 2L, "Mumbai"));
		locations.add(new Location(3L, 4L, "Bangalore"));
		when(locationRepository.findAll()).thenReturn(locations);

		ResponseEntity<List<Location>> response = locationService.getAllLocation();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(locations.get(0).getLocation(), response.getBody().get(0).getLocation());
	}

	@Test
	void testGetAllLocation_Empty() {
		when(locationRepository.findAll()).thenReturn(new ArrayList<>());

		ResponseEntity<List<Location>> response = locationService.getAllLocation();

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(0, response.getBody().size());
	}

	@Test
	void testGetLocation_ExistingId() {
		Long id = 1L;
		Location location = new Location();
		location.setId(id);
		location.setLocation("Mumbai");
		location.setStateId(2L);

		State state = new State(2L, "Maharashtra");

		when(locationRepository.findById(id)).thenReturn(Optional.of(location));
		when(stateRepository.findById(state.getId())).thenReturn(Optional.of(state));

		ResponseEntity<LocationDto> response = locationService.getLocation(id);
		System.out.println("testGetLocation_ExistingId: " + response.getBody().toString());

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Maharashtra", response.getBody().getState());
		assertEquals("Mumbai", response.getBody().getLocation());
	}

	@Test
	void testGetLocation_NonExistingId() {
		Long id = 1L;
		when(locationRepository.findById(id)).thenReturn(Optional.empty());

		ResponseEntity<LocationDto> response = locationService.getLocation(id);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody().getLocation());
	}

	@Test
	void testGetLocationByStateIdAndId_ExistingIds() {
		Long stateId = 1L;
		Long id = 1L;
		Location location = new Location();
		location.setId(id);
		location.setLocation("Mumbai");
		location.setStateId(stateId);

		when(locationRepository.findLocationByStateIdAndId(stateId, id)).thenReturn(Optional.of(location));
		when(stateRepository.findById(stateId)).thenReturn(Optional.of(new State(stateId, "Maharashtra")));

		ResponseEntity<LocationDto> response = locationService.getLocationByStateIdAndId(stateId, id);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(id, response.getBody().getLocationId());
		assertEquals("Mumbai", response.getBody().getLocation());
		assertEquals(stateId, response.getBody().getStateId());
		assertEquals("Maharashtra", response.getBody().getState());
	}

	@Test
	void testGetLocationByStateIdAndId_NonExistingIds() {
		Long stateId = 1L;
		Long id = 1L;
		when(locationRepository.findLocationByStateIdAndId(stateId, id)).thenReturn(Optional.empty());

		ResponseEntity<LocationDto> response = locationService.getLocationByStateIdAndId(stateId, id);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody().getLocation());
	}

	@Test
	void testGetLocationByStateId_ExistingStateId() {
		Long stateId = 1L;
		List<Location> locations = new ArrayList<>();
		locations.add(new Location());
		when(locationRepository.findLocationByStateId(stateId)).thenReturn(Optional.of(locations));

		ResponseEntity<List<Location>> response = locationService.getLocationByStateId(stateId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(locations, response.getBody());
	}

	@Test
	void testGetLocationByStateId_NonExistingStateId() {
		Long stateId = 1L;
		when(locationRepository.findLocationByStateId(stateId)).thenReturn(Optional.empty());

		ResponseEntity<List<Location>> response = locationService.getLocationByStateId(stateId);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(new ArrayList<>(), response.getBody());
	}

}

