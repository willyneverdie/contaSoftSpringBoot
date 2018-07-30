package com.hp.contaSoft.hibernate.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Profesor {
	
	public Profesor() {
	}
	
	@Id
	private int id;

	@Column(name="edad")
	private String name;
	
	@OneToMany(mappedBy="profesor", 
			cascade=CascadeType.ALL)
	private List<Alumno> alumnos = new ArrayList<>();

	public Profesor(int id, String name, List<Alumno> alumnos) {
		super();
		this.id = id;
		this.name = name;
		this.alumnos = alumnos;
	}
	
	
}
