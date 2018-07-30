package com.hp.contaSoft.excel.entities;

import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;


import org.hibernate.annotations.Formula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SystemPropertyUtils;

import com.hp.contaSoft.hibernate.dao.service.FileUtilsService;
import com.hp.contaSoft.hibernate.entities.Base;
import com.hp.contaSoft.hibernate.entities.PayBookInstance;
import com.hp.contaSoft.hibernate.entities.Taxpayer;
import com.hp.contaSoft.utils.PayBookUtils;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@Entity
@Table(name="PayBookDetails")
public class PayBookDetails {

	
	public PayBookDetails() {
		
	}
	
	@PrePersist
	public void valueColumns() {
		
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	/**
	 * Required Fields
	 */
	@Column
	@CsvBindByName(column = "RUT")
	private String rut;
	
	@Column
	@CsvBindByName(column = "CENTRO_COSTO")
	private String centroCosto;
	
	@Column
	@CsvBindByName(column = "SUELDO_BASE")
	private double sueldoBase;
	
	@Column
	@CsvBindByName(column = "DT")
	private int diasTrabajados;
	
	@Column
	@CsvBindByName(column = "PREVISION")
	private String prevision;
	
	@Column
	@CsvBindByName(column = "SALUD")
	private String salud;
	
	@Column
	@CsvBindByName(column = "SALUD_PORCENTAJE")
	private double saludPorcentaje;
	
	/**
	 * Non Required Fields
	 */
	@Column
	@CsvBindByName(column = "BONO")
	private double bonoProduccion;
	
	@Column
	private double aguinaldo;
	
	@Column
	@CsvBindByName(column = "HORAS_EXTRA")
	private double horasExtra;
	
	@Column
	@CsvBindByName(column = "ASIG_FAMILIAR")
	private int asignacionFamiliar;
	
	@Column
	private double movilizacion;
	
	@Column
	private double colacion;
	
	@Column
	private int descuentoHerramientas;
	
	@Column
	private int afc;
	
	@Column
	private int apv;
	
	@Column
	private double prestamos;
	
	@Column
	private double seguroOncologico;
	
	@Column
	private String seguroOAccidentes;
	
	@Column
	private double anticipo;
		
	/**
	 * Campos Calculados
	 */
	
	@Column
	private double sueldoMensual;
	
	@Column
	private double gratifiacion;
	
	@Column
	private double valorHora;
	
	@Column
	private double totalImponible;
	
	@Column
	private double totalHaber;
	
	@Column
	private double porcentajePrevision;
	
	//@Formula("totalImponible * porcentajePrevision")
	@Column
	private double valorPrevision;
	
	public double getPorcentajePrevision2() {
	    return this.porcentajePrevision;
	}
	
	@Column
	private double valorSalud;
	
	@Column
	private double valorAFC;
	
	@Column
	private double totalAsignacionFamiliar;
	
	@Column
	private double valorIUT;
	
	@Column
	private double valorSeguroOAccidentes;
	
	@Column
	private double rentaLiquidaImponible;
	
	@Column
	private double totalHoraExtra;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="payBookInstance_id")
	private PayBookInstance payBookInstance;

	/**
	 * Calculators
	 */
	
	public void calculateAsignacionFamiliar(Double amount) {
		//que renta se utiliza en la asignacion familiar?
		this.totalAsignacionFamiliar = this.asignacionFamiliar * amount;
	}
	
	
	public void calculateSueldoMensual() {
		this.sueldoMensual = ( this.sueldoBase / 30 ) * this.diasTrabajados;
	}
	
	public void calculateGratificacion() {
		this.gratifiacion = (( this.sueldoBase * 25) / 100 ) / (30 * this.diasTrabajados); 
	}

	public void calculateValorHora() {
		this.valorHora = (( this.sueldoBase / 30) * 7) / (45 * 1.5); 
	}
	
	public void calculateTotalImponible() {
		this.totalImponible = this.sueldoMensual +  this.gratifiacion + 
				this.bonoProduccion + this.aguinaldo + this.horasExtra; 
	}
	
	public void calculateTotalHaber() {
		this.totalHaber = this.movilizacion + this.colacion + this.descuentoHerramientas + this.totalImponible; 
	}
	
	public void calculatePrevision() {
		//this.valorPrevision = this.totalImponible * PayBookUtils.findPrevisionValue(this.prevision);
		//this.valorPrevision = this.totalImponible * fileUtilsService.findPrevisionValue(this.prevision);
		//this.valorPrevision = this.getPorcentajePrevision();
		//this.valorPrevision = getPorcentajePrevision2();
		this.valorPrevision = (this.totalImponible * this.getPorcentajePrevision()) / 100;
	
	}

	public void calculateSalud() {
		this.valorSalud = (this.totalImponible * this.saludPorcentaje)/ 100; 
	}
	
	public void calculateAfc() {
		this.valorAFC = (this.afc * 1.8) / 100; 
	}
	
	public void calculateRentaLiquidaImponible() {
		this.rentaLiquidaImponible = this.totalImponible -
				(this.getValorSalud() + this.getValorPrevision() + this.getValorAFC()); 
	}

	public void calculateSeguroAccidentes() {
		this.valorSeguroOAccidentes = 1; 
	}
	
	/**
	 * Calculo de UIT
	 */
	
	public void calculateValorUIT(double factor) {
		this.valorIUT = (this.rentaLiquidaImponible * factor); 
	}
	
	public void calculateTotalHoraExtra() {
		this.totalHoraExtra = (this.valorHora * this.horasExtra); 
	}
	
	public double getUIT(double rentaLiquidaImponible) {
		return 0.0;
	}
	
	
	
	
	private double findSaludValue(String prevision) {
		// TODO Auto-generated method stub
		return 0.1;
	}
	
	private double findAsignacionFamiliar(double sueldo, int cantidad) {
		// TODO Auto-generated method stub
		return 0.1;
	}


	public PayBookDetails(String rut, String centroCosto, double sueldoBase, int diasTrabajados, String prevision,
			String salud) {
		super();
		this.rut = rut;
		this.centroCosto = centroCosto;
		this.sueldoBase = sueldoBase;
		this.diasTrabajados = diasTrabajados;
		this.prevision = prevision;
		this.salud = salud;
	}
}
