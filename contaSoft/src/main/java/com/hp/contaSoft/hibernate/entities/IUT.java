package com.hp.contaSoft.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class IUT extends Base{

	public IUT() {
		
	}
	
	@Column
	private String timeType;
	@Column
	private double desde;
	@Column
	private double hasta;
	@Column
	private double factor;
	@Column
	private double quantity;
	@Column
	private double maxRate;
	


	public IUT(String type, double from, double to, double factor, double quantity, double maxRate) {
		super();
		this.timeType = type;
		this.desde = from;
		this.hasta = to;
		this.factor = factor;
		this.quantity = quantity;
		this.maxRate = maxRate;
	}
	
	
	
	
}
