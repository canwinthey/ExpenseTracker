package com.xoriant.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="location")
@Getter
@Setter
@NoArgsConstructor
public class Location {
	@Id
	private Long id;
	private Long stateId;
	private String location;
}
