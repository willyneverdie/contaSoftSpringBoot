package com.hp.contaSoft.hibernate.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name="Alumno")
public class Alumno {

	public Alumno(){
		
	}
	
	@Id
	private int id;
	
	@Column(name="edad")
	private int edad;
	
	@Column(name="casillero")
	private int casillero;
	
	@ManyToOne
	@JoinColumn(name="profesor_id")
	private Profesor profesor;
	
	@Formula( value = "edad + casillero")
	private int suma;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public int getCasillero() {
		return casillero;
	}
	public void setCasillero(int casillero) {
		this.casillero = casillero;
	}
	public int getSuma() {
		return suma;
	}
	public void setSuma(int suma) {
		this.suma = suma;
	}
	public Alumno(int id, int edad, int casillero) {
		super();
		this.id = id;
		this.edad = edad;
		this.casillero = casillero;
	}
	@Override
	public String toString() {
		return "Alumno [id=" + id + ", edad=" + edad + ", casillero=" + casillero + ", suma=" + suma + "]";
	}
	
	
	
	
	
}
