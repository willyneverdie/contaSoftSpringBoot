package com.hp.contaSoft.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class _AFamiliar extends Base{

	public _AFamiliar() {
		
	}
	
	@Column
	private String tramo;
	@Column
	private double desde;
	@Column
	private double hasta;
	@Column
	private double amount;
	
	public _AFamiliar(String tramo, double desde, double hasta, double amount) {
		super();
		this.tramo = tramo;
		this.desde = desde;
		this.hasta = hasta;
		this.amount = amount;
	}
	
	
	
	
}
