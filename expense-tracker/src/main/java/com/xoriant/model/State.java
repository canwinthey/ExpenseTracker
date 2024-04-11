package com.xoriant.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="state")

@NoArgsConstructor
@Getter
@Setter
public class State {
	@Id
	private Long id;
	private String state;
}
