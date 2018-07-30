package com.hp.contaSoft.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
public class Template extends Base{

	@JsonBackReference
	@OneToOne
	@JoinColumn(name = "taxpayer_id")
	private Taxpayer taxpayer;
	
	@Column
	private String name;
	
	@Column
	private String value;
	
	public Template() {
		
	}

	public Template(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Template [ name=" + name + ", value=" + value + "]";
	}


	
	
	
}
