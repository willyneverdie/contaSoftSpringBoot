package com.hp.contaSoft.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Address extends Base{

	@Column
	private String name;
	
	@Column
	private String number;
	
	@Column
	private String province;
	
	@Column
	private String comuna;
	
	@Column
	private String city;
	
	@Column
	private String postalcode;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="taxpayer_id")
	private Taxpayer taxpayer;

	public Address() {
		
	}
	
	public Address(String name) {
		super();
		this.name = name;
	
	}
	
	public Address(String name, String number) {
		super();
		this.name = name;
		this.number = number;
	
	}
	
	public Address(String name, String number,Taxpayer taxpayer) {
		super();
		this.name = name;
		this.number = number;
		this.taxpayer = taxpayer;
	}
	
	public Address(String name, String number, String province, String comuna, String city, Taxpayer taxpayer) {
		super();
		this.name = name;
		this.number = number;
		this.province = province;
		this.comuna = comuna;
		this.city = city;
		this.taxpayer = taxpayer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getComuna() {
		return comuna;
	}

	public void setComuna(String comuna) {
		this.comuna = comuna;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Taxpayer getTaxpayer() {
		return taxpayer;
	}

	public void setTaxpayer(Taxpayer taxpayer) {
		this.taxpayer = taxpayer;
	}

	@Override
	public String toString() {
		return "Address [name=" + name + ", number=" + number + ", province=" + province + ", comuna=" + comuna
				+ ", city=" + city + ", id=" + this.id + "]";
	}
	
	
	
	
	
}
