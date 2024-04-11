package com.xoriant.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="location")
public class Location {
	@Id
	private Long id;
	private Long stateId;
	private String location;

	public Location(Long id, Long stateId, String location){
		this.id = id;
		this.stateId = stateId;
		this.location = location;
	}
}
