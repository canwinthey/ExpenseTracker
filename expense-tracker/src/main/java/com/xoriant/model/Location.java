package com.xoriant.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Location {
	private Long id;
	private Long stateId;
	private String location;
}
