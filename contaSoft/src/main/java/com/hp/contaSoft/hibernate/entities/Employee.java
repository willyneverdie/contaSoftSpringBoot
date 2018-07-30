package com.hp.contaSoft.hibernate.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Employee extends Base{

	@Column
	private String name;
	@Column
	private String lastname;
	@Column
	private String rut;
	
	@OneToOne(cascade= {CascadeType.ALL})
	@JoinColumn(name="address_id")
	private Address home_address;
	
	@Column
	private LocalDate birthday;
	
	public Employee() {
		
	}
	
	public Employee(String name, String lastname, String rut, Address home_address, LocalDate birthday) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.rut = rut;
		this.home_address = home_address;
		this.birthday = birthday;
	}

	public Employee(String name, String lastname, String rut,  Address home_address) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.rut = rut;
		this.home_address = home_address;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public Address getHome_address() {
		return home_address;
	}

	public void setHome_address(Address home_address) {
		this.home_address = home_address;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", lastname=" + lastname + ", rut=" + rut + ", home_address=" + home_address
				+ ", birthday=" + birthday + "]";
	} 

	
	
	
}
