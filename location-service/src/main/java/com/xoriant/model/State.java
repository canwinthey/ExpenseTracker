package com.xoriant.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="state")
public class State {
	@Id
	private Long id;
	private String state;

    public State(Long stateId, String state) {
		this.id = stateId;
		this.state = state;
    }
}
