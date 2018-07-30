package com.hp.contaSoft.hibernate.entities;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Base {

	public Base(){
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;
	
	@Column
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column
	private String create_by;
	
	@Column
	private String type;
	
	
}
